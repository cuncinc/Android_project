package com.cc.database.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import java.util.Random;

import android.widget.TextView;

/*
* 必应词典api：  http://xtk.azurewebsites.net/BingDictService.aspx?Word=welcome
*               http://xtk.azurewebsites.net/BingDictService.aspx?Word=想要的单词
*               https://github.com/jokermonn/-Api/blob/master/BingDic.md
*               https://zhuanlan.zhihu.com/p/22421123
*
*
*卡片堆叠效果     https://www.ctolib.com/article/compares/258
* */
import com.cc.database.R;

import static com.cc.database.activity.MainActivity.cursor;
import static com.cc.database.activity.MainActivity.dbHelper;

public class RemenberOld extends AppCompatActivity implements View.OnClickListener
{
    public String word;

    private String command_find = "SELECT * FROM WordList WHERE IncorrectCount > 0";
    private String command_update;
    private String word_pnonetic;
    private String word_chinese;
    private Button B_unknow;
    private Button B_list;
    private Button B_know;
    private Button B_chinese;

    Random random = new Random();
    int random_num;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remenber_new);

        //绑定按钮
        B_unknow = (Button) findViewById(R.id.button_unknow);
        B_list = (Button) findViewById(R.id.button_list);
        B_know = (Button) findViewById(R.id.button_know);
        B_chinese = (Button) findViewById(R.id.button_chinese);

        //设置监听器
        B_unknow.setOnClickListener(this);
        B_list.setOnClickListener(this);
        B_know.setOnClickListener(this);
        B_chinese.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        TextView tv_word = (TextView)findViewById(R.id.textview_word);
        TextView tv_phonetic_us = (TextView)findViewById(R.id.textview_phonetic_us);
        TextView tv_phonetic_uk = (TextView)findViewById(R.id.textview_phonetic_uk);
        TextView tv_definition = (TextView)findViewById(R.id.textview_definition);

        cursor = dbHelper.findDatabase(command_find);

        if (cursor.getColumnCount() < 1)
        {
            tv_definition.setText("没有要记忆的单词");
        }

        switch (v.getId())
        {
            case R.id.button_list:
            {
                cursor.moveToPosition(0);
                Intent intent = new Intent(RemenberOld.this, WordList.class);
                startActivity(intent);

                break;
            }

            case R.id.button_chinese:
            {
                if (cursor.getColumnCount() < 1)
                {
                    tv_definition.setText("没有要记忆的单词");
                    break;
                }
                tv_definition.setText(word_chinese);
                break;
            }

            case R.id.button_unknow:
            {
                if (cursor.getColumnCount() < 1)
                {
                    tv_definition.setText("没有要记忆的单词");
                    break;
                }

                command_update = "UPDATE WordList " +
                        "SET IncorrectCount = incorrectCount + 1 " +
                        "WHERE HeadWord = \"" + word + "\"";
                dbHelper.updateDatabase(command_update);

                random_num = random.nextInt(cursor.getColumnCount());
                cursor.moveToPosition(random_num);
                word = cursor.getString(cursor.getColumnIndex("HeadWord"));
                word_pnonetic = cursor.getString(cursor.getColumnIndex("Phonetic"));
                word_chinese = cursor.getString(cursor.getColumnIndex("QuickDefinition"));

                tv_definition.setText("");
                tv_phonetic_us.setText("");
                tv_phonetic_uk.setText("");

                tv_word.setText(word);
                if (word_pnonetic.indexOf("#") > 0)
                {
                    if (word_pnonetic.indexOf("S:") > 0)
                    {
                        tv_phonetic_us.setText("US [" + word_pnonetic.substring(word_pnonetic.indexOf("US:") + 4, word_pnonetic.indexOf("#")) + "]");       //显示音标
                    }
                    if (word_pnonetic.indexOf("K:") > 0)
                    {
                        tv_phonetic_uk.setText("UK [" + word_pnonetic.substring(word_pnonetic.indexOf("UK:") + 4, word_pnonetic.length()-1) + "]");
                    }
                }

                break;
            }


            case R.id.button_know:
            {
                if (cursor.getColumnCount() < 1)
                {
                    tv_definition.setText("没有要记忆的单词");
                    break;
                }

                command_update = "UPDATE WordList " +
                        "SET CorrectCount = 1 " +
                        "WHERE HeadWord = \"" + word + "\"";
                dbHelper.updateDatabase(command_update);

                command_update = "UPDATE WordList " +
                        "SET IncorrectCount = 0 " +
                        "WHERE HeadWord = \"" + word + "\"";
                dbHelper.updateDatabase(command_update);

                random_num = random.nextInt(cursor.getColumnCount());
                cursor.moveToPosition(random_num);
                word = cursor.getString(cursor.getColumnIndex("HeadWord"));
                word_pnonetic = cursor.getString(cursor.getColumnIndex("Phonetic"));
                word_chinese = cursor.getString(cursor.getColumnIndex("QuickDefinition"));

                tv_definition.setText("");
                tv_phonetic_us.setText("");
                tv_phonetic_uk.setText("");

                tv_word.setText(word);
                if (word_pnonetic.indexOf("#") > 0)
                {
                    if (word_pnonetic.indexOf("S:") > 0)
                    {
                        tv_phonetic_us.setText("US [" + word_pnonetic.substring(word_pnonetic.indexOf("US:") + 4, word_pnonetic.indexOf("#")) + "]");       //显示音标
                    }
                    if (word_pnonetic.indexOf("K:") > 0)
                    {
                        tv_phonetic_uk.setText("UK [" + word_pnonetic.substring(word_pnonetic.indexOf("UK:") + 4, word_pnonetic.length()-1) + "]");
                    }
                }
                break;
            }

            default:
                break;
        }
    }
}