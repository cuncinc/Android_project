package com.cc.database.activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.app.AppCompatActivity;

import com.cc.database.adapter.CardViewAdapter;
import com.cc.database.datasource.DBManager;
import com.cc.database.R;
import com.cc.database.datasource.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.yuqirong.cardswipelayout.CardConfig;
import me.yuqirong.cardswipelayout.CardItemTouchHelperCallback;
import me.yuqirong.cardswipelayout.CardLayoutManager;
import me.yuqirong.cardswipelayout.OnSwipeListener;

public class MainActivity extends AppCompatActivity
{
    public static DBManager dbHelper;
    private List<Word> wordList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBManager(this);
        dbHelper.openDatabase();//首次执行导入.db文件

        initView();
        initCardView();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        dbHelper.closeDatabase();
    }

    private void initView()
    {
        Button B_search = (Button) findViewById(R.id.button_search);
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

    private void initCardView()
    {
        initWordList();
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView_card_view);
        CardViewAdapter adapter = new CardViewAdapter(wordList);
        recyclerView.setAdapter(adapter);

        CardItemTouchHelperCallback cardCallback = new CardItemTouchHelperCallback(recyclerView.getAdapter(), wordList);
        cardCallback.setOnSwipedListener(new OnSwipeListener<Word>()
        {
            @Override
            public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction)
            {
                /**
                 * will callback when the card are swiping by user
                 * viewHolder : thee viewHolder of swiping card
                 * ratio : the ratio of swiping , you can add some animation by the ratio
                 * direction : CardConfig.SWIPING_LEFT means swiping from left；CardConfig.SWIPING_RIGHT means swiping from right
                 *             CardConfig.SWIPING_NONE means not left nor right
                 */
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, Word word, int direction)
            {
                if (CardConfig.SWIPED_LEFT == direction)    //左滑，不认识
                {
                    word.addInCreCnt();
                }
                else if (CardConfig.SWIPED_RIGHT == direction)     //右滑，认识
                {
                    word.addCreCnt();
                }
                /**
                 *  will callback when the card swiped from screen by user
                 *  you can also clean animation from the itemview of viewHolder in this method
                 *  viewHolder : the viewHolder of swiped cards
                 *  t : the data of swiped cards from dataList
                 *  direction : CardConfig.SWIPED_LEFT means swiped from left；CardConfig.SWIPED_RIGHT means swiped from right
                 */

//                // 移除之前设置的 onTouchListener, 否则触摸滑动会乱了
//                viewHolder.itemView.setOnTouchListener(null);
//                // 删除相对应的数据
//                int layoutPosition = viewHolder.getLayoutPosition();
//                wordList.remove(layoutPosition);
//                adapter.notifyDataSetChanged();
//                // 卡片滑出后回调 OnSwipeListener 监听器
//                if (mListener != null) {
//                    mListener.onSwiped(viewHolder, removeWord, direction == ItemTouchHelper.LEFT ? CardConfig.SWIPED_LEFT : CardConfig.SWIPED_RIGHT);
//                }
//                // 当没有数据时回调 OnSwipeListener 监听器
//                if (adapter.getItemCount() == 0) {
//                    if (mListener != null) {
//                        mListener.onSwipedClear();
//                    }
//                }
            }

            @Override
            public void onSwipedClear()
            {
                /**
                 *  will callback when all cards swiped clear
                 *  you can load more data
                 */
            }
        });
        ItemTouchHelper touchHelper = new ItemTouchHelper(cardCallback);
        CardLayoutManager cardLayoutManager = new CardLayoutManager(recyclerView, touchHelper);

        recyclerView.setLayoutManager(cardLayoutManager);
        touchHelper.attachToRecyclerView(recyclerView);
    }

    private void initWordList()
    {
        wordList.clear();
        Cursor cursor = dbHelper.findDatabase("SELECT * FROM WordList");

        for (int i=0; i<1000; ++i)
        {
            cursor.moveToPosition(new Random().nextInt(2000));
            String headWord = cursor.getString(cursor.getColumnIndex("HeadWord"));
            String pronounce = cursor.getString(cursor.getColumnIndex("Phonetic"));
            pronounce = pronounce.replaceAll("#", "] ");
            pronounce = pronounce.replace("US:", "美[");
            pronounce = pronounce.replace("UK:", "英[");
            String def = cursor.getString(cursor.getColumnIndex("QuickDefinition"));
            def = def.replace("n\\.", "\nn.");
            def = def.replace("adj\\.", "\nadj.");
//            def = def.replace("v\\.", "\nv.");    //把adv中的v也换行了
            Word word = new Word(headWord, pronounce, def);
            wordList.add(word);
        }
        cursor.close();
    }
}