package com.yoctopuce.yoctopucetoolbox.details_fragments;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.yoctopucetoolbox.R;
import com.yoctopuce.yoctopucetoolbox.functions.Humidity;
import com.yoctopuce.yoctopucetoolbox.functions.Pressure;
import com.yoctopuce.yoctopucetoolbox.functions.Temperature;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailYoctoMeteoFragment extends DetailGenericModuleFragment
{

    private Temperature _temperature;
    private Humidity _humidity;
    private Pressure _pressure;
    private TextView _tempMinTextView;
    private TextView _tempCurTextView;
    private TextView _tempMaxTextView;
    private TextView _humCurTextView;
    private TextView _humMaxTextView;
    private TextView _humMinTextView;
    private TextView _presCurTextView;
    private TextView _presMaxTextView;
    private TextView _presMinTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail_yocto_meteo, container, false);
        setupUI(rootView);
        return rootView;
    }


    @Override
    protected void reloadDataInBG(boolean firstReload) throws YAPI_Exception
    {
        super.reloadDataInBG(firstReload);
        _temperature.reloadBg();
        _humidity.reloadBg();
        _pressure.reloadBg();
    }

    @Override
    protected void setupUI(View rootView)
    {
        super.setupUI(rootView);
        _temperature = new Temperature(_argSerial + ".temperature");
        _humidity = new Humidity(_argSerial + ".humidity");
        _pressure = new Pressure(_argSerial + ".pressure");
        _tempCurTextView = rootView.findViewById(R.id.temp_cur);
        _tempMaxTextView = rootView.findViewById(R.id.temp_max);
        _tempMinTextView = rootView.findViewById(R.id.temp_min);
        _humCurTextView = rootView.findViewById(R.id.hum_cur);
        _humMaxTextView = rootView.findViewById(R.id.hum_max);
        _humMinTextView = rootView.findViewById(R.id.hum_min);
        _presCurTextView = rootView.findViewById(R.id.pres_cur);
        _presMaxTextView = rootView.findViewById(R.id.pres_max);
        _presMinTextView = rootView.findViewById(R.id.pres_min);
    }


    @Override
    protected void updateUI(boolean firstUpdate)
    {
        super.updateUI(firstUpdate);
        Locale locale = Locale.US;
        com.yoctopuce.yoctopucetoolbox.misc.MiscHelper.updateUI_temperature(_temperature, _tempCurTextView, _tempMaxTextView, _tempMinTextView);
        String unit = _humidity.getUnit();
        _humCurTextView.setText(String.format(locale, "%s %s", Double.toString(_humidity.getCurrentValue()), unit));
        _humMaxTextView.setText(String.format(locale, "%s %s", Double.toString(_humidity.getHighestValue()), unit));
        _humMinTextView.setText(String.format(locale, "%s %s", Double.toString(_humidity.getLowestValue()), unit));
        _presCurTextView.setText(String.format(locale, "%s mbar", Double.toString(_pressure.getCurrentValue())));
        _presMaxTextView.setText(String.format(locale, "%s mbar", Double.toString(_pressure.getHighestValue())));
        _presMinTextView.setText(String.format(locale, "%s mbar", Double.toString(_pressure.getLowestValue())));
    }
}
