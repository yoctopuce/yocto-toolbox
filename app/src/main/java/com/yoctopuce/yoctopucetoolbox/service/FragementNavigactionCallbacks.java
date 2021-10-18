package com.yoctopuce.yoctopucetoolbox.service;

public interface FragementNavigactionCallbacks {

    void goToDocumentation(String serial);

    void goToConfiguration(String serial);

    void back();
}
