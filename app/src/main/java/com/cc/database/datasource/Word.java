package com.cc.database.datasource;

public class Word
{
    private String word;
    private String pronun;
    private String defs;

    public Word(String word, String pronun, String defs)
    {

        this.word = word;
        this.pronun = pronun;
        this.defs = defs;
    }

    public String getWord()
    {
        return word;
    }

    public void setWord(String word)
    {
        this.word = word;
    }

    public String getPronun()
    {
        return pronun;
    }

    public void setPronun(String pronun)
    {
        this.pronun = pronun;
    }

    public String getDefs()
    {
        return defs;
    }

    public void setDefs(String defs)
    {
        this.defs = defs;
    }
}