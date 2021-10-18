package com.yoctopuce.yoctopucetoolbox.details_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import com.yoctopuce.YoctoAPI.YAPI;
import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.yoctopucetoolbox.R;
import com.yoctopuce.yoctopucetoolbox.functions.Temperature;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by seb on 22.11.2016.
 */
public class DetailYoctoThermocoupleFragment extends DetailGenericModuleFragment
{
    private final ArrayList<TemperatureUIRef> _uiRefs = new ArrayList<>(2);
    private TextView _minTextView;
    private TextView _currentTextView;
    private TextView _maxTextView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail_yocto_multi_temperature, container, false);
        setupUI(rootView);
        return rootView;
    }


    @Override
    protected void reloadDataInBG(boolean firstReload) throws YAPI_Exception
    {
        super.reloadDataInBG(firstReload);
        for (TemperatureUIRef uiRef : _uiRefs) {
            uiRef.reloadDataInBG();
        }
    }

    @Override
    protected void setupUI(View rootView)
    {
        super.setupUI(rootView);
        String base_serial = _argSerial.substring(0, YAPI.YOCTO_BASE_SERIAL_LEN);
        int nbTemp;
        String label;
        switch (base_serial) {
            case "THRMSTR2": //Yocto-MaxiThermistor
            case "THRMSTR1": //Yocto-Thermistor-C
                nbTemp = 6;
                label = getString(R.string.thermistor);
                break;
            case "THRMCPL1": //Yocto-Thermocouple
            default:
                nbTemp = 2;
                label = getString(R.string.thermocouple);
                break;
        }
        GridLayout gridLayout = rootView.findViewById(R.id.temp_grid_layout);
        for (int i = 1; i <= nbTemp; i++) {
            TemperatureUIRef uiRef = new TemperatureUIRef(label, gridLayout, i);
            _uiRefs.add(uiRef);
        }
    }


    @Override
    protected void updateUI(boolean firstUpdate)
    {
        super.updateUI(firstUpdate);
        for (TemperatureUIRef uiRef : _uiRefs) {
            uiRef.updateUI();
        }
    }

    class TemperatureUIRef
    {
        private final Temperature _temperature1;
        private final TextView _minTextView;
        private final TextView _currentTextView;
        private final TextView _maxTextView;

        TemperatureUIRef(String label, GridLayout gv, int i)
        {
            final String hwid = _argSerial + ".temperature" + i;
            _temperature1 = new Temperature(hwid);
            TextView tv = new TextView(getContext());
            tv.setText(String.format(Locale.US, "%s %d", label, i));
            gv.addView(tv);
            _minTextView = new TextView(getContext());
            gv.addView(_minTextView);
            _currentTextView = new TextView(getContext());
            gv.addView(_currentTextView);
            _maxTextView = new TextView(getContext());
            gv.addView(_maxTextView);
        }

        void updateUI()
        {
            com.yoctopuce.yoctopucetoolbox.misc.MiscHelper.updateUI_temperature(_temperature1, _currentTextView, _maxTextView, _minTextView);
        }

        void reloadDataInBG() throws YAPI_Exception
        {
            _temperature1.reloadBg();
        }

    }
}
