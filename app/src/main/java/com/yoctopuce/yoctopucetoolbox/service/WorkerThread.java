package com.yoctopuce.yoctopucetoolbox.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.yoctopuce.YoctoAPI.YAPI;
import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YModule;
import com.yoctopuce.yoctopucetoolbox.functions.Module;

/**
 * Worker thread that will do all Yoctopuce IO
 */

class WorkerThread extends Thread implements YAPI.DeviceArrivalCallback, YAPI.DeviceRemovalCallback
{
    private static final String TAG = "WThread";
    @SuppressLint("StaticFieldLeak")
    private static WorkerThread __instance;
    private static int __instanceUsage;


    synchronized static void StartUsage(Context context, String hubsURL)
    {
        if (hubsURL == null) {
            throw new IllegalArgumentException("The hub URL is null.");
        }
        if (context == null) {
            throw new IllegalArgumentException("Application context is null.");
        }

        Log.d(TAG, String.format("Start usage (count was %d)", __instanceUsage));
        if (__instance != null && !hubsURL.equals(__instance._hubURL)) {
            // todo: stop previous thread
            Log.e(TAG, "multiples url not yet supporte");
        }

        if (__instance == null) {
            __instance = new WorkerThread(context.getApplicationContext(), hubsURL);
            __instance.start();
        }
        __instanceUsage++;
    }

    synchronized static void StopUsage()
    {
        //Log.d(TAG, String.format("Stop usage (count was %d) %d", __instanceUsage, System.currentTimeMillis()));
        __instanceUsage--;
        if (__instanceUsage == 0 && __instance != null) {
            __instance._mustRun = false;
            __instance.interrupt();
            try {
                __instance.join(6000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(ex);
            }
            __instance = null;
        }
        //Log.d(TAG, String.format("exit Stop usage (count is %d) %d", __instanceUsage, System.currentTimeMillis()));
    }


    private final String _hubURL;
    private final Context _appContext;
    //private final YAPIContext _yctx;
    private final ModulesCache _modulesCache;
    private volatile boolean _mustRun;


    private WorkerThread(Context appContext, String hubURL)
    {
        super("WorkerThread");
        _hubURL = hubURL;
        _appContext = appContext;
        //_yctx = new YAPIContext();
        _mustRun = true;
        _modulesCache = ModulesCache.getInstance();
    }


    @Override
    public void run()
    {
        try {
            YAPI.EnableUSBHost(_appContext);
            //_yctx.SetUSBPacketAckMs(50);
            YAPI.RegisterHub(_hubURL);
            _modulesCache.resetCache();
            YAPI.RegisterDeviceArrivalCallback(this);
            YAPI.RegisterDeviceRemovalCallback(this);
            Log.d(TAG, "Start never ending loop");
            while (_mustRun) {
                if (Thread.interrupted()) {
                    break;
                }
                YAPI.UpdateDeviceList();
                YAPI.Sleep(1000);
            }
        } catch (YAPI_Exception e) {
            // todo: report IO error on UI thread
            e.printStackTrace();
        }
        YAPI.UnregisterHub(_hubURL);
        _modulesCache.resetCache();
    }

    @Override
    public void yDeviceArrival(YModule ymodule)
    {
        try {
            String serial = ymodule.get_serialNumber();
            Module module = new Module(ymodule);
            module.reloadBg();
            _modulesCache.add(serial, module);
        } catch (YAPI_Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void yDeviceRemoval(YModule ymodule)
    {
        try {
            String serial = ymodule.get_serialNumber();
            _modulesCache.remove(serial);
        } catch (YAPI_Exception e) {
            e.printStackTrace();
        }

    }

}
