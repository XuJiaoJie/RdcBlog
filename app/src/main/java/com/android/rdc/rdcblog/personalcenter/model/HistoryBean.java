package com.android.rdc.rdcblog.personalcenter.model;

import android.util.Log;

/**
 * Created by Administrator on 2016/7/28 0028.
 */
public class HistoryBean implements Comparable{
    private static final String TAG = "HistoryBean";
    String month;
    String day;

    @Override
    public int compareTo(Object o) {
        HistoryBean historyBean = (HistoryBean) o;
        int monthCmp = month.compareTo(historyBean.month);
        Log.d(TAG, "compareTo: monthCmp"+monthCmp);
        return monthCmp != 0 ? monthCmp: day.compareTo(historyBean.day);
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }


    public void setDay(String month) {
        this.month = month;
    }

    public void setMonth(String day) {
        this.day = day;
    }

}