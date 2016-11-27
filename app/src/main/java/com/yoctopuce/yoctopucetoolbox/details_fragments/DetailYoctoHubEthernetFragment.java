package com.yoctopuce.yoctopucetoolbox.details_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YHubPort;
import com.yoctopuce.YoctoAPI.YNetwork;
import com.yoctopuce.yoctopucetoolbox.R;
import com.yoctopuce.yoctopucetoolbox.functions.HubPort;
import com.yoctopuce.yoctopucetoolbox.functions.Network;
import com.yoctopuce.yoctopucetoolbox.widget.CustomSwitch;

public class DetailYoctoHubEthernetFragment extends DetailGenericModuleFragment
{
    private HubPort _hubport3;
    private HubPort _hubport2;
    private HubPort _hubport1;
    private Network _network;
    private TextView _port1TextView;
    private TextView _port2TextView;
    private TextView _port3TextView;
    private CustomSwitch _port1Switch;
    private CustomSwitch _port2Switch;
    private CustomSwitch _port3Switch;
    private TextView _networkTextView;
    private TextView _macTextView;
    private TextView _ipTextView;
    private TextView _netNameTextView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail_yoctohub_ethernet, container, false);
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
        _network = new Network(_serial + ".network");

        _port1TextView = (TextView) rootView.findViewById(R.id.port1_state);
        _port2TextView = (TextView) rootView.findViewById(R.id.port2_state);
        _port3TextView = (TextView) rootView.findViewById(R.id.port3_state);
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
        _networkTextView = (TextView) rootView.findViewById(R.id.network_state);
        _macTextView = (TextView) rootView.findViewById(R.id.mac_addr);
        _ipTextView = (TextView) rootView.findViewById(R.id.ip_addr);
        _netNameTextView = (TextView) rootView.findViewById(R.id.device_name);
    }

    @Override
    protected void reloadDataInBG() throws YAPI_Exception
    {
        super.reloadDataInBG();
        _hubport1.reloadBg();
        _hubport2.reloadBg();
        _hubport3.reloadBg();
        _network.reloadBg();
    }

    protected void updateUI()
    {
        super.updateUI();
        updateUI_HubPort(_hubport1, _port1TextView, _port1Switch);
        updateUI_HubPort(_hubport2, _port2TextView, _port2Switch);
        updateUI_HubPort(_hubport3, _port3TextView, _port3Switch);

        int readiness = _network.getReadiness();
        int msg = R.string.invalid;
        switch (readiness) {
            case YNetwork.READINESS_DOWN:
                msg = R.string.rdy_network_down;
                break;
            case YNetwork.READINESS_EXISTS:
                msg = R.string.rdy_network_exists;
                break;
            case YNetwork.READINESS_LINKED:
                msg = R.string.rdy_network_linked;
                break;
            case YNetwork.READINESS_LAN_OK:
                msg = R.string.rdy_lan_ready;
                break;
            case YNetwork.READINESS_WWW_OK:
                msg = R.string.rdy_www_ready;
                break;
        }
        _networkTextView.setText(msg);
        _macTextView.setText(_network.getMacAddress());
        _ipTextView.setText(_network.getIpAddress());
        _netNameTextView.setText(_network.getLogicalName());
    }
}
