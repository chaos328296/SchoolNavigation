package com.example.schoolnavigation;

import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;
import com.amap.api.maps.model.LatLng;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.angmarch.views.NiceSpinner;

public class InformationActivity extends AppCompatActivity {
    WebView webView;
    double x ,y; //起点
    double x1 ,y1; //终点

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        //控件引用
        NiceSpinner myplace = (NiceSpinner) findViewById(R.id.myplace);
        NiceSpinner place = (NiceSpinner) findViewById(R.id.place);
        webView = (WebView) findViewById(R.id.mywebview);
        Button button = (Button) findViewById(R.id.button_sure);


        //加载网页 景点介绍
        webView.loadUrl("https://baike.baidu.com/item/黑龙江大学/175307?fr=aladdin");
        WebSettings wSet = webView.getSettings();//解决网页无法播放声音
        webView.setWebViewClient(new WebViewClient());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)  {
//                                    wSet.setMediaPlaybackRequiresUserGesture(false);
            wSet.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //获取定位坐标
        location(1,getIntent().getDoubleExtra("x", x),getIntent().getDoubleExtra("y", y));
        location(2,getIntent().getDoubleExtra("x", x),getIntent().getDoubleExtra("y", y));
//        x = getIntent().getDoubleExtra("x", x);
//        y = getIntent().getDoubleExtra("y", y);
//        x1 = getIntent().getDoubleExtra("x", x);
//        y1 = getIntent().getDoubleExtra("y", y);

        final Graph graph = new Graph();
        graph.hashadd();

        //下拉框适配
        final List<String> dataList = new ArrayList<>();
        dataList.add("我的位置");
        dataList.add("大门");
        dataList.add("主楼");
        dataList.add("联通广场");
        dataList.add("第一图书馆");
        dataList.add("1号教学楼");
        dataList.add("校医院");
        dataList.add("实验楼");
        dataList.add("体育场");
        dataList.add("汇文楼");
        dataList.add("综合楼");
        dataList.add("3号教学楼");
        dataList.add("4号教学楼");
        dataList.add("C区游泳馆");
        dataList.add("艺术楼");

        dataList.add("B区超市");
        dataList.add("排球场");
        dataList.add("B食堂");
        dataList.add("化学楼");
        dataList.add("生命楼");
        dataList.add("农学楼");
        dataList.add("A食堂");
        dataList.add("新图书馆");
        dataList.add("博物馆");
        dataList.add("网球场");

        dataList.add("体育馆");
        dataList.add("第一游泳馆");
        dataList.add("二号教学楼");

        //下拉框填充
        myplace.attachDataSource(dataList);

        place.attachDataSource(dataList);

        //按钮点击事件
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (x != x1 && y != y1) {
                    Intent intent = new Intent(InformationActivity.this, MainActivity.class);
                    List<LatLng> points = new ArrayList<LatLng>();

                    //画线位置点
//                    points.add(new LatLng(x, y));
//                    points.add(new LatLng(x1, y1));

                    points = graph.UDG(x,y,x1,y1);

                    intent.putExtra("points", (Serializable) points);
                    startActivity(intent);
                    if (webView != null) {
                        webView = null;
                    }
                    finish();
                } else
                    Toast.makeText(InformationActivity.this, "您已在目标地点附近", Toast.LENGTH_SHORT).show();
            }
        });


        //我的位置点击事件
        myplace.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        location(1,getIntent().getDoubleExtra("x", x),getIntent().getDoubleExtra("y", y));
                        break;
                    case 1:
                        x = 45.708027;//(126.613903,45.708027);
                        y = 126.613903;
                        break;
                    case 2:
                        x = 45.708196; //(126.614702,45.708196);
                        y = 126.614702;
                        break;
                    case 3:
                        x = 45.707589;//(126.617492,45.707589);
                        y = 126.617492;
                        break;
                    case 4:
                        x = 45.708421;//(126.618672,45.708421);
                        y = 126.618672;
                        break;
                    case 5:
                        x = 45.707848;//(126.619026,45.707848);
                        y = 126.619026;
                        break;
                    case 6:
                        x = 45.707683;//(126.619133,45.707683);
                        y = 126.619133;
                        break;
                    case 7:
                        x = 45.707294;//(45.707294,126.619456);
                        y = 126.619456;
                        break;
                    case 8:
                        x = 45.708342;//(126.619935,45.708342);
                        y = 126.619935;
                        break;
                    case 9:
                        x = 45.709361;//(126.622395,45.709361);
                        y = 126.622395;
                        break;
                    case 10:
                        x = 45.706896;//(126.623291,45.706896);
                        y = 126.623291;
                        break;
                    case 11:
                        x = 45.706784;//(126.624616,45.706784);
                        y = 126.624616;
                        break;
                    case 12:
                        x = 45.707226;//(126.624578,45.707226);
                        y = 126.624578;
                        break;
                    case 13:
                        x = 45.707173;//(126.627234,45.707173);
                        y = 126.627234;
                        break;
                    case 14:
                        x = 45.707387;//(126.628135,45.707387);
                        y = 126.628135;
                        break;
                    case 15:
                        x = 45.706451;//45.706451,126.622653;
                        y = 126.622653;
                        break;
                    case 16:
                        x = 45.706268;//45.706268,126.621644;
                        y = 126.621644;
                        break;
                    case 17:
                        x = 45.707159;//(45.707159,126.621483);
                        y = 126.621483;
                        break;
                    case 18:
                        x = 45.707189;//45.707189,126.620453;
                        y = 126.620453;
                        break;
                    case 19:
                        x = 45.705503;//45.705503,126.620845
                        y = 126.620845;
                        break;
                    case 20:
                        x = 45.705458;//45.705458,126.620201;
                        y = 126.620201;
                        break;
                    case 21:
                        x = 45.706743;//(45.706743,126.619611
                        y = 126.619611;
                        break;
                    case 22:
                        x = 45.706301;//45.706301,126.618613;
                        y = 126.618613;
                        break;
                    case 23:
                        x = 45.705867;//(45.705867,126.617557
                        y = 126.617557;
                        break;
                    case 24:
                        x = 45.706316;//45.706316,126.615534
                        y = 126.615534;
                        break;
                    case 25:
                        x = 45.708971;//45.708971,126.614532;
                        y = 126.614532;
                        break;
                    case 26:
                        x = 45.709908;//45.709908,126.616174
                        y = 126.616174;
                        break;
                    case 27:
                        x = 45.708328;//45.708328,126.616521
                        y = 126.616521;
                        break;
                }
            }
        });

        //目的位置点击事件
        place.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        //webView.loadUrl(graph.find("0"));
//                        webView.loadUrl("https://baike.baidu.com/item/黑龙江大学/175307?fr=aladdin");//我的位置
//                        x1 = getIntent().getDoubleExtra("x", x);
//                        y1 = getIntent().getDoubleExtra("y", y);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                WebSettings wSet = webView.getSettings();
                                wSet.setJavaScriptEnabled(true);
                                webView.loadUrl(graph.find("0"));

                            }
                        });
                        location(2,getIntent().getDoubleExtra("x", x),getIntent().getDoubleExtra("y", y));
                        break;
                    case 1:
//                        webView.loadUrl(graph.find("1"));
                        //webView.loadUrl("https://baike.baidu.com/item/黑龙江大学/175307?fr=aladdin");//大门
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                WebSettings wSet = webView.getSettings();
                                wSet.setJavaScriptEnabled(true);
                                webView.loadUrl(graph.find("1"));

                            }
                        });
                        x1 = 45.708027;//(126.613903,45.708027);
                        y1 = 126.613903;
                        break;
                    case 2:
                        //                       webView.loadUrl(graph.find("2"));// webView.loadUrl("https://baike.baidu.com/item/黑龙江大学主楼/5797367?fr=aladdin");//主楼
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                WebSettings wSet = webView.getSettings();
                                wSet.setJavaScriptEnabled(true);
                                webView.loadUrl(graph.find("2"));

                            }
                        });
                        x1 = 45.708196; //(126.614702,45.708196);
                        y1 = 126.614702;
                        break;
                    case 3:
                        webView.loadUrl(graph.find("3"));// webView.loadUrl("http://www.hlju.edu.cn/info/1051/1078.htm");//联通广场
                        x1 = 45.707589;//(126.617492,45.707589);
                        y1 = 126.617492;
                        break;
                    case 4:
//                        webView.loadUrl(graph.find("4"));// webView.loadUrl("https://baike.baidu.com/item/黑龙江大学图书馆");//第一图书馆
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                WebSettings wSet = webView.getSettings();
                                wSet.setJavaScriptEnabled(true);
                                webView.loadUrl(graph.find("4"));

                            }
                        });
                        x1 = 45.708421;//(126.618672,45.708421);
                        y1 = 126.618672;
                        break;
                    case 5:
                        webView.loadUrl(graph.find("5"));// webView.loadUrl("https://baike.baidu.com/item/黑龙江大学/175307?fr=aladdin");//1号教学楼
                        x1 = 45.707848;//(126.619026,45.707848);
                        y1 = 126.619026;
                        break;
                    case 6:
                        webView.loadUrl(graph.find("6"));//webView.loadUrl("https://baike.baidu.com/item/黑龙江大学/175307?fr=aladdin");//校医院
                        x1 = 45.707683;//(126.619133,45.707683);
                        y1 = 126.619133;
                        break;
                    case 7:
//                        webView.loadUrl(graph.find("7"));//webView.loadUrl("https://baike.baidu.com/item/黑龙江大学物理科学与技术学院/7549945?fr=aladdin");//实验楼
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                WebSettings wSet = webView.getSettings();
                                wSet.setJavaScriptEnabled(true);
                                webView.loadUrl(graph.find("7"));

                            }
                        });
                        x1 = 45.707294;//(126.619402,45.707608);
                        y1 = 126.619456;
                        break;
                    case 8:
//                        webView.loadUrl(graph.find("8"));//webView.loadUrl("http://www.hlju.edu.cn/info/1051/1071.htm");//体育场
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                WebSettings wSet = webView.getSettings();
                                wSet.setJavaScriptEnabled(true);
                                webView.loadUrl(graph.find("8"));

                            }
                        });
                        x1 = 45.708342;//(126.619935,45.708342);
                        y1 = 126.619935;
                        break;
                    case 9:
//                        webView.loadUrl(graph.find("9"));//webView.loadUrl("http://www.hlju.edu.cn/info/1051/1079.htm");//汇文楼
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                WebSettings wSet = webView.getSettings();
                                wSet.setJavaScriptEnabled(true);
                                webView.loadUrl(graph.find("9"));

                            }
                        });
                        x1 = 45.709361;//(126.622395,45.709361);
                        y1 = 126.622395;
                        break;
                    case 10:
                        webView.loadUrl(graph.find("10"));//webView.loadUrl("https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E9%BB%91%E9%BE%99%E6%B1%9F%E5%A4%A7%E5%AD%A6%E6%A0%A11%E5%8F%B7%E6%95%99%E5%AD%A6%E6%A5%BC&step_word=&hs=0&pn=34&spn=0&di=150150&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=1387397235%2C2395663820&os=3149808826%2C3615253034&simid=4184741667%2C461459532&adpicid=0&lpn=0&ln=1661&fr=&fmq=1559571095901_R&fm=result&ic=&s=undefined&hd=&latest=&copyright=&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fimg.bimg.126.net%2Fphoto%2FY2Bj4uj5kfgEgLjHsUoVEQ%3D%3D%2F5749407874292430940.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fks52_z%26e3B8mn_z%26e3Bv54AzdH3F3tjktgnn0AzdH3Fks52AzdH3FfpwptvAzdH3F8ndnm9caada8a8889080cdd9AzdH3F&gsm=0&rpstart=0&rpnum=0&islist=&querylist=&force=undefined");//综合楼
                        x1 = 45.706896;//(126.623291,45.706896);
                        y1 = 126.623291;
                        break;
                    case 11:
//                        webView.loadUrl(graph.find("11"));//webView.loadUrl("http://map.baidu.com/detail?third_party=seo&qt=ninf&uid=6f792fbaf7a99817ca1b9533&detail=education");//3号教学楼
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                WebSettings wSet = webView.getSettings();
                                wSet.setJavaScriptEnabled(true);
                                webView.loadUrl(graph.find("11"));

                            }
                        });
                        x1 = 45.706784;//(126.624616,45.706784);
                        y1 = 126.624616;
                        break;
                    case 12:
//                        webView.loadUrl(graph.find("12"));//webView.loadUrl("http://www.hlju.edu.cn/info/1051/1074.htm");//4号教学楼
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                WebSettings wSet = webView.getSettings();
                                wSet.setJavaScriptEnabled(true);
                                webView.loadUrl(graph.find("12"));

                            }
                        });
                        x1 = 45.707226;//(126.624578,45.707226);
                        y1 = 126.624578;
                        break;
                    case 13:
//                        webView.loadUrl(graph.find("13"));//webView.loadUrl("https://baike.baidu.com/item/黑龙江大学/175307?fr=aladdin");//C区游泳馆
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                WebSettings wSet = webView.getSettings();
                                wSet.setJavaScriptEnabled(true);
                                webView.loadUrl(graph.find("13"));

                            }
                        });
                        x1 = 45.707173;//(126.627234,45.707173);
                        y1 = 126.627234;
                        break;
                    case 14:
                        //webView.loadUrl(graph.find("14"));// webView.loadUrl("http://www.hlju.edu.cn/info/1051/1072.htm");//艺术楼
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                WebSettings wSet = webView.getSettings();
                                wSet.setJavaScriptEnabled(true);
//                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)  {
////                                    wSet.setMediaPlaybackRequiresUserGesture(false);
//                                    wSet.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//                                }
                                webView.loadUrl(graph.find("14"));

                            }
                        });
                        x1 = 45.707387;//(126.628135,45.707387);
                        y1 = 126.628135;
                        break;
                    case 15:
                        webView.loadUrl(graph.find("14"));// webView.loadUrl("http://www.hlju.edu.cn/info/1051/1072.htm");//艺术楼
                        x1 = 45.706451;//45.706451,126.622653;
                        y1 = 126.622653;
                        break;
                    case 16:
                        webView.loadUrl(graph.find("14"));// webView.loadUrl("http://www.hlju.edu.cn/info/1051/1072.htm");//艺术楼
                        x1 = 45.706268;//45.706268,126.621644;
                        y1 = 126.621644;
                        break;
                    case 17:
                        webView.loadUrl(graph.find("14"));// webView.loadUrl("http://www.hlju.edu.cn/info/1051/1072.htm");//艺术楼
                        x1 = 45.707159;//(45.707159,126.621483);
                        y1 = 126.621483;
                        break;
                    case 18:
                        webView.loadUrl(graph.find("14"));// webView.loadUrl("http://www.hlju.edu.cn/info/1051/1072.htm");//艺术楼
                        x1 = 45.707189;//45.707189,126.620453;
                        y1 = 126.620453;
                        break;
                    case 19:
                        webView.loadUrl(graph.find("14"));// webView.loadUrl("http://www.hlju.edu.cn/info/1051/1072.htm");//艺术楼
                        x1 = 45.705503;//45.705503,126.620845
                        y1 = 126.620845;
                        break;
                    case 20:
                        webView.loadUrl(graph.find("14"));// webView.loadUrl("http://www.hlju.edu.cn/info/1051/1072.htm");//艺术楼
                        x1 = 45.705458;//45.705458,126.620201;
                        y1 = 126.620201;
                        break;
                    case 21:
                        webView.loadUrl(graph.find("14"));// webView.loadUrl("http://www.hlju.edu.cn/info/1051/1072.htm");//艺术楼
                        x1 = 45.706743;//(45.706743,126.619611
                        y1 = 126.619611;
                        break;
                    case 22:
                        webView.loadUrl(graph.find("14"));// webView.loadUrl("http://www.hlju.edu.cn/info/1051/1072.htm");//艺术楼
                        x1 = 45.706301;//45.706301,126.618613;
                        y1 = 126.618613;
                        break;
                    case 23:
                        webView.loadUrl(graph.find("14"));// webView.loadUrl("http://www.hlju.edu.cn/info/1051/1072.htm");//艺术楼
                        x1 = 45.705867;//(45.705867,126.617557
                        y1 = 126.617557;
                        break;
                    case 24:
                        webView.loadUrl(graph.find("14"));// webView.loadUrl("http://www.hlju.edu.cn/info/1051/1072.htm");//艺术楼
                        x1 = 45.706316;//45.706316,126.615534
                        y1 = 126.615534;
                        break;
                    case 25:
                        x1 = 45.708971;//45.708971,126.614532;
                        y1 = 126.614532;
                        break;
                    case 26:
                        x1 = 45.709908;//45.709908,126.616174
                        y1 = 126.616174;
                        break;
                    case 27:
                        x1 = 45.708328;//45.708328,126.616521
                        y1 = 126.616521;
                        break;
                }
            }
        });

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK&&webView.canGoBack()){
            webView.goBack();//返回上个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);//退出整个应用程序
    }
    private void location(int who ,double x ,double y)
    {

        //4号楼
        if (x >= 45.70722 && x<=45.707775 && y>=126.623953 && y<= 126.624932){
            if (who==1){
                this.x = 45.707226;
                this.y = 126.624578;
            }else {
                x1 = 45.707226;
                y1 = 126.624578;
            }
        }

        //综合楼
        else if (x <= 45.707145 && x >= 45.706366 && y <= 126.623352 && y>= 126.622414){
            if (who==1){
                this.x = 45.706896;
                this.y = 126.623291;
            }else {
                x1 = 45.706896;
                y1 = 126.623291;
            }
        }
        else{
            if (who==1){
                this.x = 45.707226;
                this.y = 126.624578;
            }else {
                x1 = 45.707226;
                y1 = 126.624578;
            }
        }
    }
}
