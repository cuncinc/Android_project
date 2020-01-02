package com.cc.database.activity;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cc.database.R;
import com.cc.database.adapter.RawWordAdapter;
import com.cc.database.datasource.Word;

import java.util.ArrayList;
import java.util.List;

import static com.cc.database.activity.MainActivity.dbHelper;


public class ListRawWordsActivity extends AppCompatActivity
{
    private List<Word> words = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_raw_words);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_words);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        initView();
    }

    void initView()
    {
        initWords();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        RawWordAdapter adapter = new RawWordAdapter(words);
        recyclerView.setAdapter(adapter);
    }

    void initWords()
    {
        String command_sort = "SELECT * FROM WordList WHERE IncorrectCount > 0 " +
                "AND NotebookGuid = \"1001\"";  //只列出四级单词

        Cursor cursor = dbHelper.findDatabase(command_sort);
        cursor.moveToFirst();

        for (int i=0; cursor.moveToNext() && i<1454; ++i)
        {
            String headWord = cursor.getString( cursor.getColumnIndex("HeadWord") );
            String def = cursor.getString(cursor.getColumnIndex("QuickDefinition"));
            String phonetic = cursor.getString(cursor.getColumnIndex("Phonetic"));
            Word word = new Word(headWord,phonetic, def);
            words.add(word);
        }
    }
}