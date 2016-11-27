package com.yoctopuce.yoctopucetoolbox.details_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.yoctopucetoolbox.R;
import com.yoctopuce.yoctopucetoolbox.functions.GenericSensor;

import java.util.Locale;

/**
 * Created by seb on 22.11.2016.
 */
public class DetailYoctomilliVoltRxFragment extends DetailGenericModuleFragment
{


    private TextView _sens1_min;
    private TextView _sens1_cur;
    private TextView _sens1_max;
    private GenericSensor _sensor1;
    private TextView _sens1_sig;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail_yocto_single_generic_sensor, container, false);
        setupUI(rootView);
        return rootView;
    }

    @Override
    protected void reloadDataInBG() throws YAPI_Exception
    {
        super.reloadDataInBG();
        _sensor1.reloadBg();
    }

    @Override
    protected void setupUI(View rootView)
    {
        super.setupUI(rootView);
        _sensor1 = new GenericSensor(_serial + ".genericSensor1");
        _sens1_min = (TextView) rootView.findViewById(R.id.sens1_min);
        _sens1_cur = (TextView) rootView.findViewById(R.id.sens1_cur);
        _sens1_sig = (TextView) rootView.findViewById(R.id.sens1_signal);
        _sens1_max = (TextView) rootView.findViewById(R.id.sens1_max);
    }

    @Override
    protected void updateUI()
    {
        super.updateUI();
        updateUI_genericSensor(_sens1_min, _sensor1, _sens1_cur, _sens1_sig, _sens1_max);
    }


}
