package com.cc.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class DBManager
{
    private final int BUFFER_SIZE = 400000;
    public static final String DB_NAME = "wordlist.db"; //保存的数据库文件名
    public static final String PACKAGE_NAME = "com.cc.database";
    public static final String DB_PATH = "/data"
            + Environment.getDataDirectory().getAbsolutePath() + "/"
            + PACKAGE_NAME;  //在手机里存放数据库的位置(/data/data/com.cc.database/wordlist.db)


    private SQLiteDatabase database;
    private Context context;

    //构造函数
    public DBManager(Context context)
    {
        this.context = context;
    }

    //返回SQLiteDatabase类型的变量的函数
    public SQLiteDatabase getDatabase()
    {
        return database;
    }

    //似乎是重写了父类的这个方法
    public void setDatabase(SQLiteDatabase database)
    {
        this.database = database;
    }

    //方法
    public void openDatabase()
    {
        System.out.println(DB_PATH + "/" + DB_NAME);
        this.database = this.openDatabase(DB_PATH + "/" + DB_NAME);
    }


    //私有方法, 函数返回值
    private SQLiteDatabase openDatabase(String dbfile)
    {
        try {
            if (!(new File(dbfile).exists()))
            {
                //判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
                InputStream is = this.context.getResources().openRawResource(
                        R.raw.wordlist); //欲导入的数据库
                FileOutputStream fos = new FileOutputStream(dbfile);
                byte[] buffer = new byte[BUFFER_SIZE];
                int count = 0;
                while ((count = is.read(buffer)) > 0)
                {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }

            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbfile,null);
            return db;

        } catch (FileNotFoundException e) {
            Log.e("Database", "File not found");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Database", "IO exception");
            e.printStackTrace();
        }
        return null;
    }



    public Cursor findDatabase(String command)
    {
        //查找数据
        return ( database.rawQuery(command, null) );
    }

    public void closeDatabase()
    {
        this.database.close();
    }
}
















