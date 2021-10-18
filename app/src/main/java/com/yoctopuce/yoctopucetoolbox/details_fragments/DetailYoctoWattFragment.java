package com.yoctopuce.yoctopucetoolbox.details_fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.yoctopucetoolbox.R;
import com.yoctopuce.yoctopucetoolbox.functions.Current;
import com.yoctopuce.yoctopucetoolbox.functions.Power;
import com.yoctopuce.yoctopucetoolbox.functions.Voltage;
import com.yoctopuce.yoctopucetoolbox.misc.MiscHelper;

import java.util.Locale;

/**
 * Created by seb on 22.11.2016.
 */
public class DetailYoctoWattFragment extends DetailGenericModuleFragment
{
    private TextView _voltDCTextView;
    private TextView _voltACTextView;
    private TextView _ampDCTextView;
    private TextView _ampACTextView;
    private TextView _powerTextView;
    private TextView _cosphiTextView;
    private TextView _durationTextView;
    private TextView _energy;
    private Power _power;
    private Voltage _voltageDC;
    private Voltage _voltageAC;
    private Current _currentDC;
    private Current _currentAC;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail_yocto_watt, container, false);
        setupUI(rootView);
        return rootView;
    }

    @Override
    protected void reloadDataInBG(boolean firstReload) throws YAPI_Exception
    {
        super.reloadDataInBG(firstReload);
        _power.reloadBg();
        _voltageDC.reloadBg();
        _voltageAC.reloadBg();
        _currentDC.reloadBg();
        _currentAC.reloadBg();
    }

    @Override
    protected void setupUI(View rootView)
    {
        super.setupUI(rootView);
        _power = new Power(_argSerial + ".power");
        _voltageDC = new Voltage(_argSerial + ".voltage1");
        _voltageAC = new Voltage(_argSerial + ".voltage2");
        _currentDC = new Current(_argSerial + ".current1");
        _currentAC = new Current(_argSerial + ".current2");
        _voltDCTextView = rootView.findViewById(R.id.volt_dc);
        _voltACTextView = rootView.findViewById(R.id.volt_ac);
        _ampDCTextView = rootView.findViewById(R.id.amp_dc);
        _ampACTextView = rootView.findViewById(R.id.amp_ac);
        _powerTextView = rootView.findViewById(R.id.power);
        _cosphiTextView = rootView.findViewById(R.id.cosfi);
        _durationTextView = rootView.findViewById(R.id.duration);
        _energy = rootView.findViewById(R.id.energy);
        Button resetButton = rootView.findViewById(R.id.reset_button);
        resetButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AsyncTask.execute(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try {
                            _power.setMeterBg(0);
                        } catch (YAPI_Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void updateUI(boolean firstUpdate)
    {
        super.updateUI(firstUpdate);
        Locale locale = Locale.US;

        _voltDCTextView.setText(String.format(locale, "%s %s", Double.toString(_voltageDC.getCurrentValue()), _voltageDC.getUnit()));
        _voltACTextView.setText(String.format(locale, "%s %s", Double.toString(_voltageAC.getCurrentValue()), _voltageAC.getUnit()));
        _ampDCTextView.setText(String.format(locale, "%s %s", Double.toString(_currentDC.getCurrentValue()), _currentDC.getUnit()));
        _ampACTextView.setText(String.format(locale, "%s %s", Double.toString(_currentAC.getCurrentValue()), _currentAC.getUnit()));

        double powerResolution = _power.getResolution();
        _powerTextView.setText(String.format(locale, "%s W", Double.toString(com.yoctopuce.yoctopucetoolbox.misc.MiscHelper.applyResolution(_power.getCurrentValue(), powerResolution))));
        _energy.setText(String.format(locale, "%s Wh", Double.toString(MiscHelper.applyResolution(_power.getMeter(), powerResolution))));
        int s = _power.getMeterTimer();
        String t = "";
        boolean c = false;
        if (s >= 86400) {
            t += s / 86400 + "d ";
            s %= 86400;
            c = true;
        }
        if ((s >= 3600) || c) {
            t += s / 3600 + "h ";
            s %= 3600;
            c = true;
        }
        if ((s >= 60) || c) {
            t += s / 60 + "m ";
            s %= 60;
        }
        t += s + 's';
        _durationTextView.setText(t);
        _cosphiTextView.setText(Double.toString(_power.getCosPhi()));
    }
}
