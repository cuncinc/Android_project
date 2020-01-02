package com.cc.database.activity;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import com.cc.database.R;
import com.cc.database.adapter.SearchListAdapter;
import com.cc.database.datasource.Word;

import java.util.ArrayList;
import java.util.List;

import static com.cc.database.activity.MainActivity.dbHelper;

public class SearchActivity extends AppCompatActivity
{
    private SearchView searchView;
    private SearchListAdapter adapter;
    private RecyclerView recyclerView;
    private List<Word> wordList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView_search_list);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        searchView = (SearchView) findViewById(R.id.sear_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                initWordList(newText);
                adapter = new SearchListAdapter(wordList);
                recyclerView.setAdapter(adapter);
                return false;
            }
        });
    }

    private void initWordList(String w)
    {
        wordList.clear();
        if (!w.matches("^[A-Za-z]+$"))
        {
            return;
        }
        String command_find = "SELECT * FROM WordList WHERE HeadWord LIKE \'"+ w +"%'";
        Cursor cursor = dbHelper.findDatabase(command_find);
        cursor.moveToFirst();
        for (int i=0; i<cursor.getCount(); ++i)
        {
            String headWord = cursor.getString(cursor.getColumnIndex("HeadWord"));
            String def = cursor.getString(cursor.getColumnIndex("QuickDefinition"));
            Word word = new Word(headWord, null, def);
            wordList.add(word);
            cursor.moveToNext();
        }
        cursor.close();
    }
}
