package com.yoctopuce.yoctopucetoolbox.service;

/**
 * Created by seb on 17.11.2016.
 */

public interface CacheObserver
{

    void onChange(String key);

    void onReload();

}
