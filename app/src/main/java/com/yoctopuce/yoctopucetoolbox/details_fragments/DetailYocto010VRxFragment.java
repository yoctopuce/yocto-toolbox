package com.yoctopuce.yoctopucetoolbox.details_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.yoctopucetoolbox.R;
import com.yoctopuce.yoctopucetoolbox.functions.GenericSensor;
import com.yoctopuce.yoctopucetoolbox.misc.MiscHelper;

public class DetailYocto010VRxFragment extends DetailGenericModuleFragment
{

    private TextView _sens1_min;
    private TextView _sens1_cur;
    private TextView _sens1_max;
    private TextView _sens2_min;
    private TextView _sens2_cur;
    private TextView _sens2_max;
    private GenericSensor _sensor1;
    private GenericSensor _sensor2;
    private TextView _sens1_sig;
    private TextView _sens2_sig;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail_yocto_dual_generic_sensor, container, false);
        setupUI(rootView);
        return rootView;
    }

    @Override
    protected void reloadDataInBG(boolean firstReload) throws YAPI_Exception
    {
        super.reloadDataInBG(firstReload);
        _sensor1.reloadBg();
        _sensor2.reloadBg();
    }

    @Override
    protected void setupUI(View rootView)
    {
        super.setupUI(rootView);
        _sensor1 = new GenericSensor(_argSerial + ".genericSensor1");
        _sensor2 = new GenericSensor(_argSerial + ".genericSensor2");
        _sens1_min = rootView.findViewById(R.id.sens1_min);
        _sens1_cur = rootView.findViewById(R.id.sens1_cur);
        _sens1_sig = rootView.findViewById(R.id.sens1_signal);
        _sens1_max = rootView.findViewById(R.id.sens1_max);
        _sens2_min = rootView.findViewById(R.id.sens2_min);
        _sens2_cur = rootView.findViewById(R.id.sens2_cur);
        _sens2_sig = rootView.findViewById(R.id.sens2_signal);
        _sens2_max = rootView.findViewById(R.id.sens2_max);
    }

    @Override
    protected void updateUI(boolean firstUpdate)
    {
        super.updateUI(firstUpdate);
        com.yoctopuce.yoctopucetoolbox.misc.MiscHelper.updateUI_genericSensor(_sens1_min, _sensor1, _sens1_cur, _sens1_sig, _sens1_max);
        MiscHelper.updateUI_genericSensor(_sens2_min, _sensor2, _sens2_cur, _sens2_sig, _sens2_max);

    }

}
