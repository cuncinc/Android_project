package com.cc.database;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static com.cc.database.MainActivity.cursor;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;


public class WordList extends AppCompatActivity
{

    private List<String> data = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);

        ListView listView = (ListView) findViewById(R.id.list_word);

        data = WordClass.getDataSource();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (WordList.this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);
    }
}

