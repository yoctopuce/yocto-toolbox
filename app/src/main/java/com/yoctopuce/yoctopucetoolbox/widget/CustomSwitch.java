package com.yoctopuce.yoctopucetoolbox.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Switch;

import java.util.Locale;


public class CustomSwitch extends Switch
{
    private OnCheckedChangeListener _listener;

    public CustomSwitch(Context context)
    {
        super(context);
    }

    public CustomSwitch(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public CustomSwitch(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomSwitch(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public void setOnCheckedChangeListener(final OnCheckedChangeListener listener)
    {
        _listener = listener;
        super.setOnCheckedChangeListener(listener);
    }

    public void setCheckedNoNotify(final boolean checked)
    {
        super.setOnCheckedChangeListener(null);
        super.setChecked(checked);
        super.setOnCheckedChangeListener(_listener);
    }
}
