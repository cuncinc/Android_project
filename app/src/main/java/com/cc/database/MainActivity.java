package com.cc.database;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.loopeer.cardstack.CardStackView;

import java.util.Arrays;
import java.util.Random;




/*
* 必应词典api：  http://xtk.azurewebsites.net/BingDictService.aspx?Word=welcome
*               http://xtk.azurewebsites.net/BingDictService.aspx?Word=想要的单词
*               https://github.com/jokermonn/-Api/blob/master/BingDic.md
*               https://zhuanlan.zhihu.com/p/22421123
*
*
*卡片堆叠效果     https://www.ctolib.com/article/compares/258
* */
public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    public DBManager dbHelper;
    private Button B_add;
    private Button B_list;
    public Random random = new Random();
    public int random_num;
    public String word;
    public String command_find;    //数据库查询命令
    public String command_update;

    public static Cursor cursor;   //操作数据库的游标

    //mTestStackAdapter = new TestStackAdapter(this);


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);     //调用从父类继承过来的onCreate方法
        setContentView(R.layout.activity_main);

        //绑定按钮
        B_add = (Button) findViewById(R.id.button_find);
        B_list = (Button) findViewById(R.id.button_list);


        //设置监听器
        B_add.setOnClickListener(this);
        B_list.setOnClickListener(this);


        dbHelper = new DBManager(this);
        //首次执行导入.db文件
        dbHelper.openDatabase();

        /*
        WebView webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://songchun.farbox.com");
        */

    }

    @Override
    public void onClick(View v)
    {
        TextView tv_word = (TextView)findViewById(R.id.textview_word);
        TextView tv_phonetic = (TextView)findViewById(R.id.textview_phonetic);
        TextView tv_definition = (TextView)findViewById(R.id.textview_definition);

        command_find = "SELECT * FROM WordList";    //查询命令
        cursor = dbHelper.findDatabase(command_find);   //获取游标

        switch (v.getId())
        {
            case R.id.button_find:      //点击按钮
            {
                random_num = random.nextInt(1453);
                cursor.moveToPosition(random_num);  //就是这一句，调试了一个晚上。对Cursor类没有做好充分的了解，而没有加这行，导致程序一直崩......
                word = cursor.getString(cursor.getColumnIndex("HeadWord"));     //获取单词

                tv_word.setText(word);      //显示单词
                tv_phonetic.setText(cursor.getString(cursor.getColumnIndex("Phonetic")));       //显示音标
                tv_definition.setText(cursor.getString(cursor.getColumnIndex("QuickDefinition")));      //显示中文释义

                command_update = "UPDATE WordList " +
                        "SET CorrectCount = CorrectCount + 1 " +
                        "WHERE HeadWord = \"" + word + "\"";    //修改命令
                dbHelper.updateDatabase(command_update);

                Log.e(word + cursor.getString(7), cursor.getString(2) + cursor.getString(3));
                cursor.close();

                break;
            }

            case R.id.button_list:
            {
                cursor.moveToPosition(0);
                Intent intent = new Intent(MainActivity.this, WordList.class);
                startActivity(intent);
            }

            default:
                break;
        }
    }
}