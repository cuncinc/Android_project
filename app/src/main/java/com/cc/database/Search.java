package com.cc.database;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.cc.database.MainActivity.searched_word;
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

    private Response response;
    private Request request;
    JSONArray jsonArray;
    JSONObject jsonObject;

    private String responseData;

    /*

    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder()
            .get()
            .url("http://xtk.azurewebsites.net/BingDictService.aspx?Word=")
            .url(searched_word)
            .build();

    Call call = client.newCall(request);
    Response response = call.execute(); //同步调用,返回Response,会抛出IO异常
    //Response response = client.newCall(request).execute();
    String responseData = response.body().string();
    */

/*
//异步调用,并设置回调函数
    call.enqueue(new Callback() {
    @Override
    public void onFailure(Call call, IOException e) {
        Toast.makeText(OkHttpActivity.this, "get failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {
        final String res = response.body().string();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                contentTv.setText(res);
            }
        });
    }
});

*/


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        TV_search_return = (TextView) findViewById(R.id.textview_search_return);
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
            Log.e("已点击按钮", searched_word);
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
                            .url("http://xtk.azurewebsites.net/BingDictService.aspx?Word=" + searched_word)
                            //.url(searched_word)
                            .build();

                    response = client.newCall(request).execute();
                    responseData = response.body().string();
                    TV_search_return.setText(responseData);
                    Log.e("链接已响应",responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithJSONObject(String jsonData) {
        try {
            jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
                String version = jsonObject.getString("version");
                Log.d("MainActivity", "id is " + id);
                Log.d("MainActivity", "name is " + name);
                Log.d("MainActivity", "version is " + version);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}