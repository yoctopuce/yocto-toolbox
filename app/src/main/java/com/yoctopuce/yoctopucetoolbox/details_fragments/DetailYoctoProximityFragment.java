package com.yoctopuce.yoctopucetoolbox.details_fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.yoctopucetoolbox.R;
import com.yoctopuce.yoctopucetoolbox.functions.LightSensor;
import com.yoctopuce.yoctopucetoolbox.functions.Proximity;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailYoctoProximityFragment extends DetailGenericModuleFragment
{

    private Proximity _proximity;
    private LightSensor _light;
    private LightSensor _ir;
    private TextView _proximityTextView;
    private TextView _lightTextView;
    private TextView _irTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail_yocto_proximity, container, false);
        setupUI(rootView);
        return rootView;
    }


    @Override
    protected void reloadDataInBG() throws YAPI_Exception
    {
        super.reloadDataInBG();
        _proximity.reloadBg();
        _light.reloadBg();
        _ir.reloadBg();
    }

    @Override
    protected void setupUI(View rootView)
    {
        super.setupUI(rootView);
        _proximity = new Proximity(_argSerial + ".proximity1");
        _light = new LightSensor(_argSerial + ".lightSensor1");
        _ir = new LightSensor(_argSerial + ".lightSensor2");
        _proximityTextView = (TextView) rootView.findViewById(R.id.proximity_value);
        _lightTextView = (TextView) rootView.findViewById(R.id.light_value);
        _irTextView = (TextView) rootView.findViewById(R.id.ir_value);
    }


    @Override
    protected void updateUI(boolean firstUpdate)
    {
        super.updateUI(firstUpdate);
        Locale locale = Locale.US;
        _proximityTextView.setText(String.format(locale, "%.0f", _proximity.getCurrentValue()));
        _lightTextView.setText(String.format(locale, "%.0f", _light.getCurrentValue()));
        _irTextView.setText(String.format(locale, "%.0f", _ir.getCurrentValue()));
    }
}
