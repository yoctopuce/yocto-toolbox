package com.yoctopuce.yoctopucetoolbox.details_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YHubPort;
import com.yoctopuce.YoctoAPI.YNetwork;
import com.yoctopuce.YoctoAPI.YRealTimeClock;
import com.yoctopuce.YoctoAPI.YWakeUpMonitor;
import com.yoctopuce.yoctopucetoolbox.R;
import com.yoctopuce.yoctopucetoolbox.functions.HubPort;
import com.yoctopuce.yoctopucetoolbox.functions.Network;
import com.yoctopuce.yoctopucetoolbox.functions.RealTimeClock;
import com.yoctopuce.yoctopucetoolbox.functions.WakeUpMonitor;
import com.yoctopuce.yoctopucetoolbox.functions.WakeUpSchedule;
import com.yoctopuce.yoctopucetoolbox.functions.Wireless;
import com.yoctopuce.yoctopucetoolbox.misc.MiscHelper;
import com.yoctopuce.yoctopucetoolbox.widget.CustomSwitch;

import java.util.Locale;

public class DetailYoctoHubWirelessFragment extends DetailGenericModuleFragment
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
    private WakeUpMonitor _wakeUpMonitor;
    private RealTimeClock _realTimeClock;
    private WakeUpSchedule _wakeUpSchedule1;
    private WakeUpSchedule _wakeUpSchedule2;
    private TextView _rtcTimeTextView;
    private TextView _nextWakupTextView;
    private TextView _nextWakupOc1TextView;
    private TextView _nextWakupOc2TextView;
    private TextView _powerSavingTextView;
    private Wireless _wireless;
    private TextView _networkSSIDTextView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_detail_yoctohub_wireless, container, false);
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
        _wireless = new Wireless(_serial + ".wireless");
        _wakeUpMonitor = new WakeUpMonitor(_serial + ".wakeUpMonitor");
        _realTimeClock = new RealTimeClock(_serial + ".realTimeClock");
        _wakeUpSchedule1 = new WakeUpSchedule(_serial + ".wakeUpSchedule1");
        _wakeUpSchedule2 = new WakeUpSchedule(_serial + ".wakeUpSchedule2");

        _port1TextView = (TextView) rootView.findViewById(R.id.port1_state);
        _port1Switch = (CustomSwitch) rootView.findViewById(R.id.port1_switch);
        _port1Switch.setOnCheckedChangeListener(new CustomOnCheckedChangeListener()
        {
            @Override
            void onCheckedChangedBg(boolean isChecked) throws YAPI_Exception
            {
                _hubport1.setEnabledBg(isChecked ? YHubPort.ENABLED_TRUE : YHubPort.ENABLED_FALSE);
            }
        });
        _port2TextView = (TextView) rootView.findViewById(R.id.port2_state);
        _port2Switch = (CustomSwitch) rootView.findViewById(R.id.port2_switch);
        _port2Switch.setOnCheckedChangeListener(new CustomOnCheckedChangeListener()
        {
            @Override
            void onCheckedChangedBg(boolean isChecked) throws YAPI_Exception
            {
                _hubport2.setEnabledBg(isChecked ? YHubPort.ENABLED_TRUE : YHubPort.ENABLED_FALSE);
            }
        });
        _port3TextView = (TextView) rootView.findViewById(R.id.port3_state);
        _port3Switch = (CustomSwitch) rootView.findViewById(R.id.port3_switch);
        _port3Switch.setOnCheckedChangeListener(new CustomOnCheckedChangeListener()
        {
            @Override
            void onCheckedChangedBg(boolean isChecked) throws YAPI_Exception
            {
                _hubport3.setEnabledBg(isChecked ? YHubPort.ENABLED_TRUE : YHubPort.ENABLED_FALSE);
            }
        });
        _networkSSIDTextView = (TextView) rootView.findViewById(R.id.network_ssid);
        _networkTextView = (TextView) rootView.findViewById(R.id.network_state);
        _macTextView = (TextView) rootView.findViewById(R.id.mac_addr);
        _ipTextView = (TextView) rootView.findViewById(R.id.ip_addr);
        _netNameTextView = (TextView) rootView.findViewById(R.id.device_name);
        _rtcTimeTextView = (TextView) rootView.findViewById(R.id.rtc_time);
        _nextWakupTextView = (TextView) rootView.findViewById(R.id.next_wake_up);
        _powerSavingTextView = (TextView) rootView.findViewById(R.id.power_saving);
        _nextWakupOc1TextView = (TextView) rootView.findViewById(R.id.next_wakeup_occurrence1);
        _nextWakupOc2TextView = (TextView) rootView.findViewById(R.id.next_wakeup_occurrence2);
    }

    @Override
    protected void reloadDataInBG() throws YAPI_Exception
    {
        super.reloadDataInBG();
        _hubport1.reloadBg();
        _hubport2.reloadBg();
        _hubport3.reloadBg();
        _network.reloadBg();
        _wakeUpMonitor.reloadBg();
        _realTimeClock.reloadBg();
        _wireless.reloadBg();
        _wakeUpSchedule1.reloadBg();
        _wakeUpSchedule2.reloadBg();
    }

    protected void updateUI()
    {
        super.updateUI();
        updateUI_HubPort(_hubport1, _port1TextView, _port1Switch);
        updateUI_HubPort(_hubport2, _port2TextView, _port2Switch);
        updateUI_HubPort(_hubport3, _port3TextView, _port3Switch);


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
        // todo: add sleep button
        boolean slbon = true;
        if (count > 0) {
            slst = String.format(Locale.US, getString(R.string.sleep_in_X_sec), count);
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
                msg = "0-" + _wireless.getMessage();
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
        _networkSSIDTextView.setText(_wireless.getSsid());
        _macTextView.setText(_network.getMacAddress());
        _ipTextView.setText(_network.getIpAddress());
        _netNameTextView.setText(_network.getLogicalName());
    }
}
