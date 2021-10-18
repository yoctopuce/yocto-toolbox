package com.yoctopuce.yoctopucetoolbox.details_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.yoctopucetoolbox.R;
import com.yoctopuce.yoctopucetoolbox.functions.CarbonDioxide;

import java.util.Locale;

public class DetailYoctoCO2Fragment extends DetailGenericModuleFragment
{
    private CarbonDioxide _carbonDioxide;
    private TextView _minTextView;
    private TextView _currentTextView;
    private TextView _maxTextView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail_yocto_co2, container, false);
        setupUI(rootView);
        return rootView;
    }


    @Override
    protected void reloadDataInBG(boolean firstReload) throws YAPI_Exception
    {
        super.reloadDataInBG(firstReload);
        _carbonDioxide.reloadBg();
    }

    @Override
    protected void setupUI(View rootView)
    {
        super.setupUI(rootView);
        _carbonDioxide = new CarbonDioxide(_argSerial + ".carbonDioxide");
        _currentTextView = rootView.findViewById(R.id.current_value);
        _maxTextView = rootView.findViewById(R.id.max_value);
        _minTextView = rootView.findViewById(R.id.min_value);
    }


    @Override
    protected void updateUI(boolean firstUpdate)
    {
        super.updateUI(firstUpdate);
        Locale locale = Locale.US;
        String unit = _carbonDioxide.getUnit();
        _currentTextView.setText(String.format(locale, "%s %s", Double.toString(_carbonDioxide.getCurrentValue()), unit));
        _maxTextView.setText(String.format(locale, "%s %s", Double.toString(_carbonDioxide.getHighestValue()), unit));
        _minTextView.setText(String.format(locale, "%s %s", Double.toString(_carbonDioxide.getLowestValue()), unit));
    }

}
