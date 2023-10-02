package ggc.core;

import java.io.Serializable;

public class Date implements Serializable{
    private int _days;

    public Date(int dias){
        _days = dias;
    }

    public Date increaseDays(int valor){
        _days += valor;
        return this;
    }

    public int getDays(){
        return _days;
    }

    public Date getDate(){
        return this;
    }

    public int getDiference(Date day){
        return this.getDays() - day.getDays();
    }
}
