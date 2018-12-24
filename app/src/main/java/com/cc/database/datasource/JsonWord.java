package com.cc.database.datasource;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class JsonWord
{
    private String word;
    private Pronunciation pronunciation = new Pronunciation();
    private List<Defs> defs = new ArrayList<Defs>();
    private List<Sams> sams = new ArrayList<Sams>();


    public void setWord(String word)
    {
        this.word = word;
    }

    public String getWord()
    {
        return word;
    }
    public Pronunciation getPronunciation()
    {
        return pronunciation;
    }
    public List<Defs> getDefs()
    {
        return defs;
    }
    public List<Sams> getSams()
    {
        return sams;
    }
    public void setPronunciation(Pronunciation pronunciation)
    {
        this.pronunciation = pronunciation;
    }




    public void setDefs(List<Defs> defs)
    {
        this.defs = defs;
    }
}