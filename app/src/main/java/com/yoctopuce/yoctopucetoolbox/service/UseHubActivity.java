package com.yoctopuce.yoctopucetoolbox.service;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yoctopuce.yoctopucetoolbox.ActivityWithMenu;

public class UseHubActivity extends ActivityWithMenu
{
    protected static final String HUB_URL = "HUB_URL";
    protected String _hubURL;


    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString(HUB_URL, _hubURL);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            _hubURL = savedInstanceState.getString(HUB_URL);
        } else {
            Intent intent = this.getIntent();
            _hubURL = intent.getStringExtra(HUB_URL);
        }
        WorkerThread.StartUsage(this, _hubURL);
    }

    @Override
    protected void onDestroy()
    {
        WorkerThread.StopUsage();
        super.onDestroy();
    }
}
