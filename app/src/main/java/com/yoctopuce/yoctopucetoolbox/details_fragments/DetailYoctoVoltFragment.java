package com.yoctopuce.yoctopucetoolbox.details_fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.yoctopucetoolbox.R;
import com.yoctopuce.yoctopucetoolbox.functions.Voltage;

public class DetailYoctoVoltFragment extends DetailGenericModuleFragment
{
    private TextView _curACTextView;
    private TextView _minACTextView;
    private Voltage _voltageDC;
    private Voltage _voltageAC;
    private TextView _maxACTextView;
    private TextView _curDCTextView;
    private TextView _minDCTextView;
    private TextView _maxDCTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail_yocto_volt, container, false);
        setupUI(rootView);
        return rootView;
    }

    @Override
    protected void reloadDataInBG() throws YAPI_Exception
    {
        super.reloadDataInBG();
        _voltageDC.reloadBg();
        _voltageAC.reloadBg();
    }

    @Override
    protected void setupUI(View rootView)
    {
        super.setupUI(rootView);
        _voltageDC = new Voltage(_argSerial + ".voltage1");
        _voltageAC = new Voltage(_argSerial + ".voltage2");
        _curDCTextView = (TextView) rootView.findViewById(R.id.current_value_dc);
        _curACTextView = (TextView) rootView.findViewById(R.id.current_value_ac);
        _minDCTextView = (TextView) rootView.findViewById(R.id.min_value_dc);
        _minACTextView = (TextView) rootView.findViewById(R.id.min_value_ac);
        _maxDCTextView = (TextView) rootView.findViewById(R.id.max_value_dc);
        _maxACTextView = (TextView) rootView.findViewById(R.id.max_value_ac);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void updateUI(boolean firstUpdate)
    {
        super.updateUI(firstUpdate);
        _curDCTextView.setText(Double.toString(_voltageDC.getCurrentValue()) + " V");
        _curACTextView.setText(Double.toString(_voltageAC.getHighestValue()) + " V");
        _minDCTextView.setText(Double.toString(_voltageDC.getLowestValue()) + " V");
        _minACTextView.setText(Double.toString(_voltageAC.getCurrentValue()) + " V");
        _maxDCTextView.setText(Double.toString(_voltageDC.getHighestValue()) + " V");
        _maxACTextView.setText(Double.toString(_voltageAC.getLowestValue()) + " V");
    }
}
