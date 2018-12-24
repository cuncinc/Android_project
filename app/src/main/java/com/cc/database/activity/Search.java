package com.cc.database.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.cc.database.R;
import com.cc.database.datasource.Defs;
import com.cc.database.datasource.JsonWord;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.cc.database.activity.MainActivity.searched_word;
/*
* 必应词典api：  http://xtk.azurewebsites.net/BingDictService.aspx?Word=welcome
*               http://xtk.azurewebsites.net/BingDictService.aspx?Word=想要的单词
*               https://github.com/jokermonn/-Api/blob/master/BingDic.md
*               https://zhuanlan.zhihu.com/p/22421123
* */

public class Search extends AppCompatActivity  implements View.OnClickListener
{

    private Button B_search;
    private TextView TV_search_return;
    private EditText ET_search;
    private TextView TV_1;
    private TextView TV_2;
    private TextView TV_3;
    private TextView TV_4;

    private Response response;
    private Request request;
    JSONArray jsonArray;
    JSONObject jsonObject;

    private String responseData;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        TV_search_return = (TextView) findViewById(R.id.textview_search_return);
        TV_1 = (TextView) findViewById(R.id.textview_1);
        TV_2 = (TextView) findViewById(R.id.textview_2);
        TV_3 = (TextView) findViewById(R.id.textview_3);
        TV_4 = (TextView) findViewById(R.id.textview_4);
        ET_search = (EditText)findViewById(R.id.edittext_search2);

        B_search = (Button) findViewById(R.id.button_search2);

        B_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.button_search2)
        {
            searched_word = ET_search.getText().toString();
            sendRequestWithOkHttp();
        }
    }

    private void sendRequestWithOkHttp()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    OkHttpClient client = new OkHttpClient();
                    request = new Request.Builder()
                            .url("http://xtk.azurewebsites.net/BingDictService.aspx?Word="+searched_word)
                            .build();

                    response = client.newCall(request).execute();
                    responseData = response.body().string();

                    JsonWord jsonword = JSON.parseObject(responseData, JsonWord.class);

                    TV_1.setText(jsonword.getWord()+"\n"+
                    "["+jsonword.getPronunciation().getAmE()+"]"+"\n"
                    +jsonword.getDefs().get(0).getDef()+"\n"
                    +jsonword.getDefs().get(1).getDef());
                    TV_2.setText("["+jsonword.getPronunciation().getAmE()+"]");
                    //Log.e("["+jsonword.getPronunciation().getAmE()+"]", " ");
                    //TV_3.setText(jsonword.getDefs().get(0).getDef());
                    //TV_4.setText(jsonword.getDefs().get(1).getDef());
                    //Log.e(jsonword.getDefs().get(1).getDef(), " ");
                    //TV_1.setText(jsonword.getSams().get(2).getChn());
                    //Log.e(jsonword.getDefs()+"aa", "b");
                    //TV_2.setText(jsonword.getSams().get(2).getEng());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}