package com.yoctopuce.yoctopucetoolbox.service;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.yoctopucetoolbox.ActivityWithMenu;
import com.yoctopuce.yoctopucetoolbox.R;
import com.yoctopuce.yoctopucetoolbox.hub.Hub;
import com.yoctopuce.yoctopucetoolbox.hub.HubStorage;

import java.lang.ref.WeakReference;
import java.util.UUID;

@SuppressLint("Registered")
public abstract class UseHubActivity extends ActivityWithMenu implements UseHubAPI, BGHandler.BgErrorHandler{

    private Handler _uiHandler;
    private BGHandler _bgHandler;
    private HandlerThread _bgHandlerThread;


    protected static final String HUB_UUID = "HUB_UUID";
    protected Hub _hub;
    protected final BroadcastReceiver _broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (WorkerThread.ACTION_IO_ERROR.equals(intent.getAction())) {
                String message = intent.getStringExtra(WorkerThread.EXTRA_ERROR_MESSAGE);
                onBGError(new YAPI_Exception(message));
            }
        }
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(HUB_UUID, _hub.getUuid().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
    protected void onStart() {
        super.onStart();
        _bgHandlerThread = new HandlerThread("ConfigHandlerThread");
        Log.d("YYY", String.format("%s -> %s start", this, _bgHandlerThread));
        _bgHandlerThread.start();
        _uiHandler = new UIHandler(this);
        _bgHandler = new BGHandler(this, _bgHandlerThread.getLooper());
        //nice: merge HandlerThread and WorkerThread
        registerReceiver(_broadcastReceiver, new IntentFilter(WorkerThread.ACTION_IO_ERROR));
        WorkerThread.StartUsage(this, _hub.getUrl(true));

    }

    @Override
    protected void onStop() {
        unregisterReceiver(_broadcastReceiver);
        WorkerThread.StopUsage();
        _bgHandlerThread.quit();
        _bgHandlerThread.interrupt();
        Log.d("YYY", String.format("%s -> %s stop", this, _bgHandlerThread));
        super.onStop();
    }


    /**
     * Instances of static inner classes do not hold an implicit
     * reference to their outer class.
     */
    private static class UIHandler extends Handler {
        private final WeakReference<UseHubActivity> _weakReference;

        UIHandler(UseHubActivity activity) {
            _weakReference = new WeakReference<>(activity);
        }
    }


    public void postBg(BgRunnable bgRunnable) {
        _bgHandler.post(bgRunnable);
    }

    public void postDelayedBg(BgRunnable bgRunnable, long delayMs) {
        _bgHandler.postDelayed(bgRunnable, delayMs);
    }

    public void postUI(Runnable fgRunnable) {
        _uiHandler.post(fgRunnable);
    }

    public void postDelayedUI(Runnable fgRunnable, long delayMs) {
        _uiHandler.postDelayed(fgRunnable, delayMs);

    }

    public abstract void onBGError(YAPI_Exception e);


}
