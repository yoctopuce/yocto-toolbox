package com.yoctopuce.yoctopucetoolbox.details_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SwitchCompat;

import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YLed;
import com.yoctopuce.yoctopucetoolbox.R;
import com.yoctopuce.yoctopucetoolbox.functions.Led;
import com.yoctopuce.yoctopucetoolbox.widget.CustomCompoundButton;

/**
 * Created by seb on 22.11.2016.
 */
public class DetailYoctoDemoFragment extends DetailGenericModuleFragment
{

    private Led _led;
    private CustomCompoundButton _customLedSwitch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail_yocto_demo, container, false);
        setupUI(rootView);
        return rootView;
    }

    @Override
    protected void reloadDataInBG(boolean firstReload) throws YAPI_Exception
    {
        super.reloadDataInBG(firstReload);
        _led.reloadBg();
    }

    @Override
    protected void setupUI(View rootView)
    {
        super.setupUI(rootView);
        _led = new Led(_argSerial + ".led");
        SwitchCompat ledSwitch = rootView.findViewById(R.id.led_switch);
        _customLedSwitch = new CustomCompoundButton(ledSwitch, this, new BgSwitchListener()
        {
            public void onCheckedChangedBg(int id, boolean isChecked) throws YAPI_Exception
            {
                if (_led != null) {
                    _led.setPowerBg(isChecked ? YLed.POWER_ON : YLed.POWER_OFF);
                }
            }
        });


    }

    @Override
    protected void updateUI(boolean firstUpdate)
    {
        super.updateUI(firstUpdate);
        boolean checked = _led.getPower() == YLed.POWER_ON;
        _customLedSwitch.setCheckedNoNotify(checked);
    }
}
