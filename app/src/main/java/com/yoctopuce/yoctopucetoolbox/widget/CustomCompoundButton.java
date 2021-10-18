package com.yoctopuce.yoctopucetoolbox.widget;

import android.widget.CompoundButton;

import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.yoctopucetoolbox.service.BgRunnable;
import com.yoctopuce.yoctopucetoolbox.service.UseHubAPI;


public class CustomCompoundButton implements CompoundButton.OnCheckedChangeListener
{
    private boolean _callListener = true;
    private final UseHubAPI _useHubAPI;
    private final CompoundButton _compoundButton;
    private final CustomSwitchListener _listener;
    private long _lastInputStartMS;
    private long _lastInputEndMS;

    public CustomCompoundButton(CompoundButton buttonSwitch, UseHubAPI useHubAPI, CustomSwitchListener listener)
    {
        _compoundButton = buttonSwitch;
        _compoundButton.setOnCheckedChangeListener(this);
        _useHubAPI = useHubAPI;
        _listener = listener;
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
            _useHubAPI.postBg(new BgRunnable()
            {
                @Override
                public void runBg() throws YAPI_Exception
                {
                    _listener.onCheckedChangedBg(id, isChecked);
                    _lastInputEndMS = System.currentTimeMillis();
                    _useHubAPI.postUI(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            _listener.onPostChangedDoneFg(isChecked);
                        }
                    });
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
    }
}
