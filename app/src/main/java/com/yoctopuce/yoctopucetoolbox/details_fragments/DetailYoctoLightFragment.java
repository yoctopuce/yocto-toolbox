package com.yoctopuce.yoctopucetoolbox.details_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.yoctopucetoolbox.R;
import com.yoctopuce.yoctopucetoolbox.functions.LightSensor;

import java.util.Locale;

/**
 * Created by seb on 22.11.2016.
 */
public class DetailYoctoLightFragment extends DetailGenericModuleFragment
{
    private LightSensor _lightSensor;
    private TextView _minTextView;
    private TextView _currentTextView;
    private TextView _maxTextView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail_yocto_light, container, false);
        setupUI(rootView);
        return rootView;
    }


    @Override
    protected void reloadDataInBG() throws YAPI_Exception
    {
        super.reloadDataInBG();
        _lightSensor.reloadBg();
    }

    @Override
    protected void setupUI(View rootView)
    {
        super.setupUI(rootView);
        _lightSensor = new LightSensor(_argSerial + ".lightSensor");
        _currentTextView = (TextView) rootView.findViewById(R.id.current_value);
        _maxTextView = (TextView) rootView.findViewById(R.id.max_value);
        _minTextView = (TextView) rootView.findViewById(R.id.min_value);
    }


    @Override
    protected void updateUI(boolean firstUpdate)
    {
        super.updateUI(firstUpdate);
        Locale locale = Locale.US;
        String unit = _lightSensor.getUnit();
        _currentTextView.setText(String.format(locale, "%s %s", Double.toString(_lightSensor.getCurrentValue()), unit));
        _maxTextView.setText(String.format(locale, "%s %s", Double.toString(_lightSensor.getHighestValue()), unit));
        _minTextView.setText(String.format(locale, "%s %s", Double.toString(_lightSensor.getLowestValue()), unit));
    }

}
