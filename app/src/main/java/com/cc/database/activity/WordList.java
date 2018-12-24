package com.cc.database.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cc.database.R;
import com.cc.database.datasource.WordClass;

import java.util.List;


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

