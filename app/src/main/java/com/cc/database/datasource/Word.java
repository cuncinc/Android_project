package com.cc.database.datasource;

import static com.cc.database.activity.MainActivity.dbHelper;

public class Word
{
    private String headWord;
    private String pronun;
    private String defs;

    public Word(String word, String pronun, String defs)
    {

        this.headWord = word;
        this.pronun = pronun;
        this.defs = defs;
    }

    public String getWord()
    {
        return headWord;
    }


    public String getPronun()
    {
        return pronun;
    }


    public String getDefs()
    {
        return defs;
    }

    public void addInCreCnt()
    {
        String command_update = "UPDATE WordList SET IncorrectCount = IncorrectCount + 1 WHERE HeadWord = \""
                                + headWord + "\"";
        dbHelper.updateDatabase(command_update);
    }

    public void addCreCnt()
    {
        String command_update = "UPDATE WordList SET CorrectCount = CorrectCount + 1 WHERE " +
                "HeadWord = \"" + headWord + "\"";
        dbHelper.updateDatabase(command_update);
    }
}