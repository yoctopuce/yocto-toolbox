package com.yoctopuce.yoctopucetoolbox;

import android.app.Activity;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;

import com.yoctopuce.YoctoAPI.YAPI;

public class USBPlugActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Get the intent that started this activity
        Intent intent = getIntent();
        if (intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_ATTACHED)) {
            UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
            // ignore bootloaders
            if (device.getProductId() == YAPI.YOCTO_DEVID_BOOTLOADER) {
                finish();
                return;
            }
            Intent usbListIntent = ModuleListActivity.intentWithParams(this, "usb");
            startActivity(usbListIntent);

        }
    }
}
