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
*卡片堆叠效果     https://www.ctolib.com/article/compares/258
* */

public class MainActivity extends AppCompatActivity
{
    public static DBManager dbHelper;
    public static Cursor cursor;   //操作数据库的游标

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        dbHelper = new DBManager(this);
        dbHelper.openDatabase();//首次执行导入.db文件
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView()
    {
        Button B_search = (Button) findViewById(R.id.button_search);
        Button B_card = (Button) findViewById(R.id.button_card);
        Button rawWordsButton = (Button) findViewById(R.id.button_raw_words);

        B_search.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        B_card.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, CardViewActivity.class);
                startActivity(intent);
            }
        });
        rawWordsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, ListRawWordsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        dbHelper.closeDatabase();
    }
}