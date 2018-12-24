package com.cc.database.datasource;

/**
 * Created by CC on 2018/12/21.
 */

public class Pronunciation
{
    private String AmE;
    private String AmEmp3;
    private String BrE;
    private String BrEmp3;

    public void setAmE(String AmE)
    {
        this.AmE = AmE;
    }
    public void setAmEmp3(String AmEmp3)
    {
        this.AmEmp3 = AmEmp3;
    }
    public void setBrE(String BrE)
    {
        this.BrE = BrE;
    }
    public void setBrEmp3(String BrEmp3)
    {
        this.BrEmp3 = BrEmp3;
    }

    public String getAmE()
    {
        return AmE;
    }
    public String getAmEmp3()
    {
        return AmEmp3;
    }
    public String getBrE()
    {
        return BrE;
    }
    public String getBrEmp3()
    {
        return BrEmp3;
    }
}