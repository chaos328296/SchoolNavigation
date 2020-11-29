package com.example.schoolnavigation;

import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;



import androidx.appcompat.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity {
    private String[] mStrs = {"我的位置", "大门", "主楼", "联通广场",
            "第一图书馆", "1号教学楼", "校医院", "实验楼",
            "体育场", "汇文楼", "综合楼", "3号教学楼",
            "4号教学楼", "C区游泳馆", "艺术楼", "B区超市",
            "排球场", "B食堂", "化学楼", "生命楼",
            "农学楼", "A食堂", "新图书馆", "博物馆",
            "网球场", "体育馆", "第一游泳馆", "二号教学楼"};
    private SearchView mSearchView;
    private ListView lListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mSearchView = (SearchView) findViewById(R.id.searchView);
        lListView = (ListView) findViewById(R.id.listView);
        final ArrayAdapter arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mStrs);
        mSearchView.setSubmitButtonEnabled(true);
        lListView.setAdapter(arrayAdapter);
        lListView.setTextFilterEnabled(false);
        // 设置搜索文本监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent=new Intent(Main2Activity.this,MainActivity.class);
                startActivity(intent);
                return true;
            }
            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)){
                    android.widget.Filter filter = arrayAdapter.getFilter();
                    filter.filter(newText);//替换 listview.setFilterText();
//                    lListView.setFilterText(newText);
                }else{
                    lListView.clearTextFilter();
                }
                return false;
            }
        });
    }
}

