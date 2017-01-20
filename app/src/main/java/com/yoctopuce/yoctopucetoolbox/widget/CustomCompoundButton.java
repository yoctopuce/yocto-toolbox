package com.yoctopuce.yoctopucetoolbox.widget;

import android.os.Handler;
import android.widget.CompoundButton;

import com.yoctopuce.YoctoAPI.YAPI_Exception;


public class CustomCompoundButton implements CompoundButton.OnCheckedChangeListener
{
    private boolean _callListener = true;
    private Handler _bgHandler;
    private CompoundButton _compoundButton;
    private CustomSwitchListener _listener;
    private long _lastInputStartMS;
    private long _lastInputEndMS;
    private Handler _uiHandler;

    public CustomCompoundButton(CompoundButton buttonSwitch, Handler bgHandler, CustomSwitchListener listener)
    {
        _compoundButton = buttonSwitch;
        _compoundButton.setOnCheckedChangeListener(this);
        _bgHandler = bgHandler;
        _listener = listener;
        _uiHandler = new Handler();
    }


    public void setCheckedNoNotify(final boolean checked)
    {
        if (_lastInputStartMS <= _lastInputEndMS) {
            if (_compoundButton.isChecked() != checked) {
                _callListener = false;
                _compoundButton.setChecked(checked);
                _callListener = true;
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, final boolean isChecked)
    {
        if (_callListener && _listener != null) {
            _lastInputStartMS = System.currentTimeMillis();
            _listener.onPreChangedFg(isChecked);
            final int id = compoundButton.getId();
            _bgHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    try {
                        _listener.onCheckedChangedBg(id, isChecked);
                        _lastInputEndMS = System.currentTimeMillis();
                        _uiHandler.post(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                _listener.onPostChangedDoneFg(isChecked);
                            }
                        });
                    } catch (final YAPI_Exception e) {
                        e.printStackTrace();
                        _uiHandler.post(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                _listener.onErrorFg(e);
                            }
                        });
                    }
                }
            });

        }
    }

    public void setEnabled(boolean enabled)
    {
        _compoundButton.setEnabled(enabled);
    }


    public interface CustomSwitchListener
    {
        void onPreChangedFg(boolean isChecked);

        void onCheckedChangedBg(int compoundButtonId, boolean isChecked) throws YAPI_Exception;

        void onPostChangedDoneFg(boolean isChecked);

        void onErrorFg(YAPI_Exception error);
    }
}
