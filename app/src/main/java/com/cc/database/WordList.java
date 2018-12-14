package com.cc.database;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import static com.cc.database.MainActivity.cursor;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;


public class WordList extends AppCompatActivity
{

    String w = cursor.getString(cursor.getColumnIndex("HeadWord"));

    private String[] data = { "Apple", "Banana", "Orange", "Watermelon",
            "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango",
            "Apple", "Banana", "Orange", "Watermelon", "Pear", "Grape",
            "Pineapple", "Strawberry", "Cherry", "Mango" };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (WordList.this, android.R.layout.simple_list_item_1, data);
        ListView listView = (ListView) findViewById(R.id.list_word);
        listView.setAdapter(adapter);
    }
}

