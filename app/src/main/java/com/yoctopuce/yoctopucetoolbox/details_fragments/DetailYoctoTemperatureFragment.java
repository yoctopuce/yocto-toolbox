package com.yoctopuce.yoctopucetoolbox.details_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.yoctopucetoolbox.R;
import com.yoctopuce.yoctopucetoolbox.functions.Temperature;

public class DetailYoctoTemperatureFragment extends DetailGenericModuleFragment
{
    private Temperature _temperature1;
    private TextView _minTextView;
    private TextView _currentTextView;
    private TextView _maxTextView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail_yocto_single_temperature, container, false);
        setupUI(rootView);
        return rootView;
    }


    @Override
    protected void reloadDataInBG() throws YAPI_Exception
    {
        super.reloadDataInBG();
        _temperature1.reloadBg();
    }

    @Override
    protected void setupUI(View rootView)
    {
        super.setupUI(rootView);
        _temperature1 = new Temperature(_argSerial + ".temperature");
        _currentTextView = (TextView) rootView.findViewById(R.id.current_value);
        _maxTextView = (TextView) rootView.findViewById(R.id.max_value);
        _minTextView = (TextView) rootView.findViewById(R.id.min_value);
        String substring = _argSerial.substring(0, FragmentChooser.YOCTO_BASE_SERIAL_LEN);
        if (substring.equals("PT100MK1") || substring.equals("PT100MK2")) {
            TextView messageTextView = (TextView) rootView.findViewById(R.id.message);
            messageTextView.setText(R.string.make_sure_your_device_is_configured_according_to_your_pt100_type);
        }
    }


    @Override
    protected void updateUI(boolean firstUpdate)
    {
        super.updateUI(firstUpdate);
        com.yoctopuce.yoctopucetoolbox.misc.MiscHelper.updateUI_temperature(_temperature1, _currentTextView, _maxTextView, _minTextView);
    }

}
