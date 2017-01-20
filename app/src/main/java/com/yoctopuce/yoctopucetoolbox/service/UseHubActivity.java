package com.yoctopuce.yoctopucetoolbox.service;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.yoctopuce.yoctopucetoolbox.ActivityWithMenu;
import com.yoctopuce.yoctopucetoolbox.hub.Hub;
import com.yoctopuce.yoctopucetoolbox.hub.HubStorage;

import java.util.UUID;

@SuppressLint("Registered")
public class UseHubActivity extends ActivityWithMenu
{
    public static final String ERRMSG = "ERRMSG";
    public static final String SENDER = "SENDER";

    protected static final String HUB_UUID = "HUB_UUID";

    protected Hub _hub;
    protected final BroadcastReceiver _broadcastReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            if (WorkerThread.ACTION_IO_ERROR.equals(intent.getAction())) {
                String message = intent.getStringExtra(WorkerThread.EXTRA_ERROR_MESSAGE);
                finishActivityWithError("YAPI", message);
            }
        }
    };

    protected void finishActivityWithError(String sender, String message)
    {
        Intent data = new Intent();
        data.putExtra(SENDER, sender);
        data.putExtra(ERRMSG, message);
        setResult(RESULT_CANCELED, data);
        finish();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString(HUB_UUID, _hub.getUuid().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        String uuidString;
        if (savedInstanceState != null) {
            uuidString = savedInstanceState.getString(HUB_UUID);
        } else {
            Intent intent = this.getIntent();
            uuidString = intent.getStringExtra(HUB_UUID);
        }
        final UUID uuid = UUID.fromString(uuidString);
        _hub = HubStorage.get(this).getHub(uuid);
    }


    @Override
    protected void onStart()
    {
        super.onStart();
        registerReceiver(_broadcastReceiver, new IntentFilter(WorkerThread.ACTION_IO_ERROR));
        WorkerThread.StartUsage(this, _hub.getUrl());
    }

    @Override
    protected void onStop()
    {
        unregisterReceiver(_broadcastReceiver);
        WorkerThread.StopUsage();

        super.onStop();
    }
}
