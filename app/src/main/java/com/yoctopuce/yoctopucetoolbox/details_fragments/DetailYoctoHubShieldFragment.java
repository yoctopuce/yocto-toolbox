package com.yoctopuce.yoctopucetoolbox.details_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YHubPort;
import com.yoctopuce.yoctopucetoolbox.R;
import com.yoctopuce.yoctopucetoolbox.functions.HubPort;
import com.yoctopuce.yoctopucetoolbox.widget.CustomSwitch;

public class DetailYoctoHubShieldFragment extends DetailGenericModuleFragment
{
    private HubPort _hubport4;
    private HubPort _hubport3;
    private HubPort _hubport2;
    private HubPort _hubport1;
    private TextView _port1TextView;
    private TextView _port2TextView;
    private TextView _port3TextView;
    private TextView _port4TextView;
    private CustomSwitch _port1Switch;
    private CustomSwitch _port2Switch;
    private CustomSwitch _port3Switch;
    private CustomSwitch _port4Switch;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail_yoctohub_shield, container, false);
        setupUI(rootView);
        return rootView;
    }

    @Override
    protected void setupUI(View rootView)
    {
        super.setupUI(rootView);
        _hubport1 = new HubPort(_serial + ".hubPort1");
        _hubport2 = new HubPort(_serial + ".hubPort2");
        _hubport3 = new HubPort(_serial + ".hubPort3");
        _hubport4 = new HubPort(_serial + ".hubPort4");

        _port1TextView = (TextView) rootView.findViewById(R.id.port1_state);
        _port2TextView = (TextView) rootView.findViewById(R.id.port2_state);
        _port3TextView = (TextView) rootView.findViewById(R.id.port3_state);
        _port4TextView = (TextView) rootView.findViewById(R.id.port4_state);
        _port1Switch = (CustomSwitch) rootView.findViewById(R.id.port1_switch);
        _port1Switch.setOnCheckedChangeListener(new CustomOnCheckedChangeListener()
        {
            @Override
            void onCheckedChangedBg(boolean isChecked) throws YAPI_Exception
            {
                _hubport1.setEnabledBg(isChecked ? YHubPort.ENABLED_TRUE : YHubPort.ENABLED_FALSE);
            }

        });
        _port2Switch = (CustomSwitch) rootView.findViewById(R.id.port2_switch);
        _port2Switch.setOnCheckedChangeListener(new CustomOnCheckedChangeListener()
        {
            @Override
            void onCheckedChangedBg(boolean isChecked) throws YAPI_Exception
            {
                _hubport2.setEnabledBg(isChecked ? YHubPort.ENABLED_TRUE : YHubPort.ENABLED_FALSE);
            }

        });
        _port3Switch = (CustomSwitch) rootView.findViewById(R.id.port3_switch);
        _port3Switch.setOnCheckedChangeListener(new CustomOnCheckedChangeListener()
        {
            @Override
            void onCheckedChangedBg(boolean isChecked) throws YAPI_Exception
            {
                _hubport3.setEnabledBg(isChecked ? YHubPort.ENABLED_TRUE : YHubPort.ENABLED_FALSE);
            }

        });
        _port4Switch = (CustomSwitch) rootView.findViewById(R.id.port4_switch);
        _port4Switch.setOnCheckedChangeListener(new CustomOnCheckedChangeListener()
        {
            @Override
            void onCheckedChangedBg(boolean isChecked) throws YAPI_Exception
            {
                _hubport4.setEnabledBg(isChecked ? YHubPort.ENABLED_TRUE : YHubPort.ENABLED_FALSE);
            }

        });
    }

    @Override
    protected void reloadDataInBG() throws YAPI_Exception
    {
        super.reloadDataInBG();
        _hubport1.reloadBg();
        _hubport2.reloadBg();
        _hubport3.reloadBg();
        _hubport4.reloadBg();
    }

    protected void updateUI()
    {
        super.updateUI();
        updateUI_HubPort(_hubport1, _port1TextView, _port1Switch);
        updateUI_HubPort(_hubport2, _port2TextView, _port2Switch);
        updateUI_HubPort(_hubport3, _port3TextView, _port3Switch);
        updateUI_HubPort(_hubport4, _port4TextView, _port4Switch);

    }


}
