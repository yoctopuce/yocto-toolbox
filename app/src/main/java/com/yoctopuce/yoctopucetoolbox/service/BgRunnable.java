package com.yoctopuce.yoctopucetoolbox.service;

import com.yoctopuce.YoctoAPI.YAPI_Exception;

public interface BgRunnable
{
    void runBg() throws YAPI_Exception;
}