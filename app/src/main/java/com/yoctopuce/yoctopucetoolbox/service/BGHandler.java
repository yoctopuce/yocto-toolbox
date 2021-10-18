package com.yoctopuce.yoctopucetoolbox.service;

import android.os.Handler;
import android.os.Looper;

import com.yoctopuce.YoctoAPI.YAPI_Exception;

import java.lang.ref.WeakReference;

/**
 * Instances of static inner classes do not hold an implicit
 * reference to their outer class.
 */
class BGHandler extends Handler
{
    private final WeakReference<BgErrorHandler> _errorHandlerWeakReference;

    BGHandler(BgErrorHandler errorHandler, Looper looper)
    {
        super(looper);
        _errorHandlerWeakReference = new WeakReference<>(errorHandler);
    }


    void post(final BgRunnable bgRunnable)
    {
        post(new Runnable()
        {
            @Override
            public void run()
            {
                try {
                    bgRunnable.runBg();
                } catch (YAPI_Exception e) {
                    e.printStackTrace();
                    BgErrorHandler bgErrorHandler = _errorHandlerWeakReference.get();
                    if (bgErrorHandler != null) {
                        bgErrorHandler.onBGError(e);
                    }
                }
            }
        });
    }


    void postDelayed(final BgRunnable bgRunnable, long delayMs)
    {
        postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                try {
                    bgRunnable.runBg();
                } catch (YAPI_Exception e) {
                    e.printStackTrace();
                    BgErrorHandler bgErrorHandler = _errorHandlerWeakReference.get();
                    if (bgErrorHandler != null) {
                        bgErrorHandler.onBGError(e);
                    }
                }
            }
        }, delayMs);
    }

    interface BgErrorHandler
    {
        void onBGError(YAPI_Exception ex);
    }

}
