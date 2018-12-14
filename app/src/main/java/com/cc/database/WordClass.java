package com.cc.database;

import android.util.Log;

import java.util.Date;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.List;

import static com.cc.database.MainActivity.cursor;
import static com.cc.database.MainActivity.dbHelper;


public class WordClass
{
    public WordClass()
    {
        // TODO Auto-generated constructor stub
    }

    public static List<String> getDataSource()
    {
        String command_sort = "SELECT * FROM WordList WHERE CorrectCount > 0 " +
                "AND NotebookGuid = \"1001\"";  //只列出四级单词

        cursor = dbHelper.findDatabase(command_sort);

        List<String> list = new ArrayList<String>();
        int i = 0;

        for (i=0; cursor.moveToNext() && i<1454; ++i)
        {
            list.add( cursor.getString( cursor.getColumnIndex("HeadWord") ));

        }
        return list;
    }
}
