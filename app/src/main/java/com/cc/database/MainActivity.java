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
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    public DBManager dbHelper;
    private Button B_renshi;
    private Button B_add;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);     //调用从父类继承过来的onCreate方法
        setContentView(R.layout.activity_main);

        //绑定按钮
        B_renshi = (Button) findViewById(R.id.button_renshi);
        B_add = (Button) findViewById(R.id.button_find);


        //设置监听器
        B_renshi.setOnClickListener(this);
        B_add.setOnClickListener(this);

        dbHelper = new DBManager(this);
        //首次执行导入.db文件
        dbHelper.openDatabase();

        //dbHelper.closeDatabase();
    }

    @Override
    public void onClick(View v)
    {
        String word;
        String command;
        Cursor cursor;

        switch (v.getId())
        {
            case R.id.button_renshi:
            {
                break;
            }
            case R.id.button_find:
            {
                command = "SELECT * FROM WordList WHERE HeadWord LIKE \'a_\'";
                cursor = dbHelper.findDatabase(command);

                while ( cursor.moveToNext() )   //就是这一句，调试了一个晚上。对Cursor类没有做好充分的了解，而没有加这行，导致程序一直崩......
                {
                    word = cursor.getString(cursor.getColumnIndex("HeadWord"));
                    Log.e("word is", word);
                }
                cursor.close();
                break;
            }
            default:
                break;
        }
    }
}