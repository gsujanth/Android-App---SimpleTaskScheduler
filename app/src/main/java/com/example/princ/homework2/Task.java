package com.example.princ.homework2;

import android.support.annotation.NonNull;

import java.io.Serializable;

/*
  Author : Sujanth Babu Guntupalli
*/

public class Task implements Serializable {
    String title;
    String date;
    String time;
    String priority;

    public Task(String title, String date, String time, String priority) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

}
