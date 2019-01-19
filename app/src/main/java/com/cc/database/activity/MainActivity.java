package com.cc.database.activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.app.AppCompatActivity;

import com.cc.database.datasource.DBManager;
import com.cc.database.R;

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
    public static DBManager dbHelper;
    public static Cursor cursor;   //操作数据库的游标

    private Button B_remember_new;
    private Button B_remember_old;
    private Button B_search;
    public static String searched_word;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);     //调用从父类继承过来的onCreate方法
        setContentView(R.layout.activity_main);

        //绑定按钮
        B_remember_new = (Button) findViewById(R.id.button_remenber_new);
        B_remember_old = (Button) findViewById(R.id.button_remenber_old);
        B_search = (Button) findViewById(R.id.button_search);




        //设置监听器
        B_remember_new.setOnClickListener(this);
        B_remember_old.setOnClickListener(this);
        B_search.setOnClickListener(this);

        dbHelper = new DBManager(this);
        //首次执行导入.db文件
        dbHelper.openDatabase();

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.button_remenber_new:
            {
                Intent intent = new Intent(MainActivity.this, RemenberNew.class);
                startActivity(intent);

                break;
            }

            case R.id.button_remenber_old:
            {
                Intent intent = new Intent(MainActivity.this, RemenberOld.class);
                startActivity(intent);

                break;
            }

            case R.id.button_search:
            {
                Intent intent = new Intent(MainActivity.this, Search.class);
                startActivity(intent);

                break;
            }

            default:
                break;
        }
    }
}