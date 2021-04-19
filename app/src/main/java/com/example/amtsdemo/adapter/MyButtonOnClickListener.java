package com.example.amtsdemo.adapter;

import android.view.View;

public class MyButtonOnClickListener implements View.OnClickListener {
    private static final String TAG = View.OnClickListener.class.getSimpleName();

    int id;
    String name;

    public MyButtonOnClickListener(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void onClick(View v) {
        // Implemented in WordListAdapter
    }}
