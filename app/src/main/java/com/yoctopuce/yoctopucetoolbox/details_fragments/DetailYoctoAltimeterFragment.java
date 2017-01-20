package com.yoctopuce.yoctopucetoolbox.details_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.yoctopucetoolbox.R;
import com.yoctopuce.yoctopucetoolbox.functions.Altitude;
import com.yoctopuce.yoctopucetoolbox.functions.Pressure;
import com.yoctopuce.yoctopucetoolbox.functions.Temperature;

import java.util.Locale;

/**
 * Created by seb on 22.11.2016.
 */
public class DetailYoctoAltimeterFragment extends DetailGenericModuleFragment
{
    private Temperature _temperature;
    private Altitude _altitude;
    private Pressure _pressure;
    private TextView _tempMinTextView;
    private TextView _tempCurTextView;
    private TextView _tempMaxTextView;
    private TextView _altCurTextView;
    private TextView _altMaxTextView;
    private TextView _altMinTextView;
    private TextView _presCurTextView;
    private TextView _presMaxTextView;
    private TextView _presMinTextView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail_yocto_altimeter, container, false);
        setupUI(rootView);
        return rootView;
    }


    @Override
    protected void reloadDataInBG() throws YAPI_Exception
    {
        super.reloadDataInBG();
        _temperature.reloadBg();
        _altitude.reloadBg();
        _pressure.reloadBg();
    }

    @Override
    protected void setupUI(View rootView)
    {
        super.setupUI(rootView);
        _temperature = new Temperature(_argSerial + ".temperature");
        _altitude = new Altitude(_argSerial + ".altitude");
        _pressure = new Pressure(_argSerial + ".pressure");
        _tempCurTextView = (TextView) rootView.findViewById(R.id.temp_cur);
        _tempMaxTextView = (TextView) rootView.findViewById(R.id.temp_max);
        _tempMinTextView = (TextView) rootView.findViewById(R.id.temp_min);
        _altCurTextView = (TextView) rootView.findViewById(R.id.alt_cur);
        _altMaxTextView = (TextView) rootView.findViewById(R.id.alt_max);
        _altMinTextView = (TextView) rootView.findViewById(R.id.alt_min);
        _presCurTextView = (TextView) rootView.findViewById(R.id.pres_cur);
        _presMaxTextView = (TextView) rootView.findViewById(R.id.pres_max);
        _presMinTextView = (TextView) rootView.findViewById(R.id.pres_min);
    }


    @Override
    protected void updateUI(boolean firstUpdate)
    {
        super.updateUI(firstUpdate);
        Locale locale = Locale.US;
        com.yoctopuce.yoctopucetoolbox.misc.MiscHelper.updateUI_temperature(_temperature, _tempCurTextView, _tempMaxTextView, _tempMinTextView);
        _altCurTextView.setText(String.format(locale, "%s m", Double.toString(_altitude.getCurrentValue())));
        _altMaxTextView.setText(String.format(locale, "%s m", Double.toString(_altitude.getHighestValue())));
        _altMinTextView.setText(String.format(locale, "%s m", Double.toString(_altitude.getLowestValue())));
        _presCurTextView.setText(String.format(locale, "%s mbar", Double.toString(_pressure.getCurrentValue())));
        _presMaxTextView.setText(String.format(locale, "%s mbar", Double.toString(_pressure.getHighestValue())));
        _presMinTextView.setText(String.format(locale, "%s mbar", Double.toString(_pressure.getLowestValue())));
    }

}
