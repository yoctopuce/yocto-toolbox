package com.yoctopuce.yoctopucetoolbox.service;

import com.yoctopuce.YoctoAPI.YAPI_Exception;

public interface UseHubAPI
{


    void postBg(BgRunnable bgRunnable);

    void postDelayedBg(BgRunnable bgRunnable, long delayMs);

    void postUI(Runnable fgRunnable);

    void postDelayedUI(Runnable fgRunnable, long delayMs);

    /**
     * This method is called from second thread you cannot access
     * UI form this method.
     *
     * @param e the YAPI_Exception thrown by the bg thread
     */
    void onBGError(YAPI_Exception e);
}
