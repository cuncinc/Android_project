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
import android.widget.TextView;

import java.util.Random;




/*
* 必应词典api：  http://xtk.azurewebsites.net/BingDictService.aspx?Word=welcome
*               http://xtk.azurewebsites.net/BingDictService.aspx?Word=想要的单词
*               https://github.com/jokermonn/-Api/blob/master/BingDic.md
* */
public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    public DBManager dbHelper;
    private Button B_add;
    Random random = new Random();
    int random_num;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);     //调用从父类继承过来的onCreate方法
        setContentView(R.layout.activity_main);

        //绑定按钮
        B_add = (Button) findViewById(R.id.button_find);


        //设置监听器
        B_add.setOnClickListener(this);


        dbHelper = new DBManager(this);
        //首次执行导入.db文件
        dbHelper.openDatabase();

    }

    @Override
    public void onClick(View v)
    {

        String word;
        String command_find;    //数据库查询命令
        String command_update;

        Cursor cursor;   //操作数据库的游标
        TextView tv_word = (TextView)findViewById(R.id.textview_word);
        TextView tv_phonetic = (TextView)findViewById(R.id.textview_phonetic);
        TextView tv_definition = (TextView)findViewById(R.id.textview_definition);

        switch (v.getId())
        {
            case R.id.button_find:      //点击按钮
            {
                random_num = random.nextInt(1453);
                command_find = "SELECT * FROM WordList";    //查询命令

                cursor = dbHelper.findDatabase(command_find);   //获取游标


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
            default:
                break;
        }
    }
}