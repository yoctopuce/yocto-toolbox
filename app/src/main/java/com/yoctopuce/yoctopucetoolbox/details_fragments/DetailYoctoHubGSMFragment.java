package com.yoctopuce.yoctopucetoolbox.details_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

import com.yoctopuce.YoctoAPI.YAPI;
import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YHubPort;
import com.yoctopuce.YoctoAPI.YNetwork;
import com.yoctopuce.YoctoAPI.YRealTimeClock;
import com.yoctopuce.YoctoAPI.YWakeUpMonitor;
import com.yoctopuce.yoctopucetoolbox.R;
import com.yoctopuce.yoctopucetoolbox.functions.Cellular;
import com.yoctopuce.yoctopucetoolbox.functions.HubPort;
import com.yoctopuce.yoctopucetoolbox.functions.Network;
import com.yoctopuce.yoctopucetoolbox.functions.RealTimeClock;
import com.yoctopuce.yoctopucetoolbox.functions.WakeUpMonitor;
import com.yoctopuce.yoctopucetoolbox.functions.WakeUpSchedule;
import com.yoctopuce.yoctopucetoolbox.misc.MiscHelper;
import com.yoctopuce.yoctopucetoolbox.widget.CustomCompoundButton;

public class DetailYoctoHubGSMFragment extends DetailGenericModuleFragment
{
    private HubPort _hubport3;
    private HubPort _hubport2;
    private HubPort _hubport1;
    private Network _network;
    private TextView _port1TextView;
    private TextView _port2TextView;
    private TextView _port3TextView;
    private CustomCompoundButton _port1Switch;
    private CustomCompoundButton _port2Switch;
    private CustomCompoundButton _port3Switch;
    private TextView _networkTextView;
    private TextView _ipTextView;
    private TextView _netNameTextView;
    private WakeUpMonitor _wakeUpMonitor;
    private RealTimeClock _realTimeClock;
    private Cellular _cellular;
    private WakeUpSchedule _wakeUpSchedule1;
    private WakeUpSchedule _wakeUpSchedule2;
    private TextView _rtcTimeTextView;
    private TextView _nextWakupTextView;
    private TextView _nextWakupOc1TextView;
    private TextView _nextWakupOc2TextView;
    private TextView _operatorTextView;
    private TextView _powerSavingTextView;

    public DetailYoctoHubGSMFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        int layout_id;
        switch (_argSerial.substring(0, YAPI.YOCTO_BASE_SERIAL_LEN)) {
            case "YHUBGSM1": //YoctoHub-GSM-2G
                layout_id = R.layout.fragment_detail_yoctohub_gsm_2g;
                break;
            case "YHUBGSM3": //YoctoHub-GSM-3G-EU
                layout_id = R.layout.fragment_detail_yoctohub_gsm_3g_eu;
                break;
            case "YHUBGSM4": //YoctoHub-GSM-3G-NA
            default:
                layout_id = R.layout.fragment_detail_yoctohub_gsm_3g_na;
        }
        View rootView = inflater.inflate(layout_id, container, false);
        setupUI(rootView);
        return rootView;
    }

    @Override
    protected void setupUI(View rootView)
    {
        super.setupUI(rootView);
        _hubport1 = new HubPort(_argSerial + ".hubPort1");
        _hubport2 = new HubPort(_argSerial + ".hubPort2");
        _hubport3 = new HubPort(_argSerial + ".hubPort3");
        _network = new Network(_argSerial + ".network");
        _wakeUpMonitor = new WakeUpMonitor(_argSerial + ".wakeUpMonitor");
        _realTimeClock = new RealTimeClock(_argSerial + ".realTimeClock");
        _cellular = new Cellular(_argSerial + ".cellular");
        _wakeUpSchedule1 = new WakeUpSchedule(_argSerial + ".wakeUpSchedule1");
        _wakeUpSchedule2 = new WakeUpSchedule(_argSerial + ".wakeUpSchedule2");

        _port1TextView = rootView.findViewById(R.id.port1_state);
        SwitchCompat port1Switch = rootView.findViewById(R.id.port1_switch);
        _port1Switch = new CustomCompoundButton(port1Switch, this, new BgSwitchListener()
        {
            @Override
            public void onCheckedChangedBg(int id, boolean isChecked) throws YAPI_Exception
            {
                _hubport1.setEnabledBg(isChecked ? YHubPort.ENABLED_TRUE : YHubPort.ENABLED_FALSE);
            }
        });
        _port2TextView = rootView.findViewById(R.id.port2_state);
        SwitchCompat port2Switch = rootView.findViewById(R.id.port2_switch);
        _port2Switch = new CustomCompoundButton(port2Switch, this, new BgSwitchListener()
        {
            @Override
            public void onCheckedChangedBg(int id, boolean isChecked) throws YAPI_Exception
            {
                _hubport2.setEnabledBg(isChecked ? YHubPort.ENABLED_TRUE : YHubPort.ENABLED_FALSE);
            }
        });
        _port3TextView = rootView.findViewById(R.id.port3_state);
        SwitchCompat port3Switch = rootView.findViewById(R.id.port3_switch);
        _port3Switch = new CustomCompoundButton(port3Switch, this, new BgSwitchListener()
        {
            @Override
            public void onCheckedChangedBg(int id, boolean isChecked) throws YAPI_Exception
            {
                _hubport3.setEnabledBg(isChecked ? YHubPort.ENABLED_TRUE : YHubPort.ENABLED_FALSE);
            }
        });

        _operatorTextView = rootView.findViewById(R.id.network_operator);
        _networkTextView = rootView.findViewById(R.id.network_state);
        _ipTextView = rootView.findViewById(R.id.ip_addr);
        _netNameTextView = rootView.findViewById(R.id.device_name);
        _rtcTimeTextView = rootView.findViewById(R.id.rtc_time);
        _nextWakupTextView = rootView.findViewById(R.id.next_wake_up);
        _powerSavingTextView = rootView.findViewById(R.id.power_saving);
        _nextWakupOc1TextView = rootView.findViewById(R.id.next_wakeup_occurrence1);
        _nextWakupOc2TextView = rootView.findViewById(R.id.next_wakeup_occurrence2);
    }

    @Override
    protected void reloadDataInBG(boolean firstReload) throws YAPI_Exception
    {
        super.reloadDataInBG(firstReload);
        _hubport1.reloadBg();
        _hubport2.reloadBg();
        _hubport3.reloadBg();
        _network.reloadBg();
        _wakeUpMonitor.reloadBg();
        _realTimeClock.reloadBg();
        _cellular.reloadBg();
        _wakeUpSchedule1.reloadBg();
        _wakeUpSchedule2.reloadBg();
    }

    protected void updateUI(boolean firstUpdate)
    {
        super.updateUI(firstUpdate);
        MiscHelper.updateUI_HubPort(_hubport1, _port1TextView, _port1Switch);
        MiscHelper.updateUI_HubPort(_hubport2, _port2TextView, _port2Switch);
        MiscHelper.updateUI_HubPort(_hubport3, _port3TextView, _port3Switch);


        int timeSet = _realTimeClock.getTimeSet();
        if (timeSet == YRealTimeClock.TIMESET_TRUE) {
            _rtcTimeTextView.setText(_realTimeClock.getDateTime());
        } else {
            _rtcTimeTextView.setText(R.string.not_set);
        }
        long nextWakeUp = _wakeUpMonitor.getNextWakeUp();
        if (nextWakeUp == 0) {
            _nextWakupTextView.setText(R.string.n_a);
        } else {
            String nextWake = MiscHelper.UnixTimeToHumanReadable(nextWakeUp);
            _nextWakupTextView.setText(nextWake);
        }

        int count = _wakeUpMonitor.getSleepCountdown();
        String slst = getString(R.string.power_saving_switch_is_off);
        boolean slbon = true;
        if (count > 0) {
            slst = getResources().getQuantityString(R.plurals.sleep_in_X_sec, count, count);
        } else if (_wakeUpMonitor.getWakeUpState() == YWakeUpMonitor.WAKEUPSTATE_AWAKE) {
            slst = getString(R.string.sleep_not_configured);
        } else {
            slbon = false;
        }
        _powerSavingTextView.setText(slst);
        long nextOccurence = _wakeUpSchedule1.getNextOccurence();
        if (nextOccurence == 0) {
            _nextWakupOc1TextView.setText(R.string.not_configured);
        } else {
            _nextWakupOc1TextView.setText(MiscHelper.UnixTimeToHumanReadable(nextOccurence));
        }
        nextOccurence = _wakeUpSchedule2.getNextOccurence();
        if (nextOccurence == 0) {
            _nextWakupOc2TextView.setText(R.string.not_configured);
        } else {
            _nextWakupOc2TextView.setText(MiscHelper.UnixTimeToHumanReadable(nextOccurence));
        }

        int readiness = _network.getReadiness();
        String msg = getString(R.string.invalid);
        switch (readiness) {
            case YNetwork.READINESS_DOWN:
                msg = "0-" + _cellular.getMessage();
                break;
            case YNetwork.READINESS_EXISTS:
                msg = getString(R.string.rdy_network_exists);
                break;
            case YNetwork.READINESS_LINKED:
                msg = getString(R.string.rdy_network_linked);
                break;
            case YNetwork.READINESS_LAN_OK:
                msg = getString(R.string.rdy_lan_ready);
                break;
            case YNetwork.READINESS_WWW_OK:
                msg = getString(R.string.rdy_www_ready);
                break;
        }
        _networkTextView.setText(msg);
        String cellOperator = _cellular.getCellOperator();
        if (cellOperator.endsWith("*")) {
            cellOperator = cellOperator.substring(cellOperator.length() - 1) + " (Roaming)";
        }
        if (cellOperator.length() == 0) {
            cellOperator = _cellular.getLockedOperator();
            if (cellOperator.equals("")) {
                cellOperator = "auto-select";
            }
            cellOperator += " (not connected)";
        }
        _operatorTextView.setText(cellOperator);
        _ipTextView.setText(_network.getIpAddress());
        _netNameTextView.setText(_network.getLogicalName());
    }
}
