package com.yoctopuce.yoctopucetoolbox.details_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YRelay;
import com.yoctopuce.yoctopucetoolbox.R;
import com.yoctopuce.yoctopucetoolbox.functions.Relay;
import com.yoctopuce.yoctopucetoolbox.widget.CustomSwitch;

import java.util.ArrayList;
import java.util.Locale;


public class DetailYoctoRelayFragment extends DetailGenericModuleFragment
{

    private ArrayList<RelayUIRef> _relayUIRefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail_yocto_relay, container, false);
        setupUI(rootView);
        return rootView;
    }

    @Override
    protected void reloadDataInBG() throws YAPI_Exception
    {
        super.reloadDataInBG();
        for (RelayUIRef ref : _relayUIRefs) {
            ref.reloadBg();
        }
    }

    @Override
    protected void setupUI(View rootView)
    {
        super.setupUI(rootView);
        GridLayout gridLayout = (GridLayout) rootView.findViewById(R.id.relays_grid_layout);
        _relayUIRefs = new ArrayList<>();
        String base_serial = _serial.substring(0, FragmentChooser.YOCTO_BASE_SERIAL_LEN);
        int relay_count = 1;
        boolean useAB = true;
        switch (base_serial) {
            case "RELAYLO1": //Yocto-Relay
                relay_count = 2;
                break;
            case "MXPWRRLY": //Yocto-MaxiPowerRelay
                relay_count = 5;
                useAB = false;
                break;
            case "YMXCOUPL": //Yocto-MaxiCoupler
            case "HI8PWER1": //Yocto-MaxiRelay
                relay_count = 8;
                useAB = false;
                break;
        }
        for (int i = 0; i < relay_count; i++) {
            RelayUIRef relayUIRef = new RelayUIRef(gridLayout, i + 1, useAB);
            _relayUIRefs.add(relayUIRef);
        }
    }


    @Override
    protected void updateUI()
    {
        super.updateUI();
        for (RelayUIRef ref : _relayUIRefs) {
            ref.updateUI();
        }
    }

    class RelayUIRef
    {
        final Relay _relay;
        final CustomSwitch _customSwitch;
        private final boolean _useAB;

        RelayUIRef(GridLayout gv, int i, boolean useAB)
        {
            final String hwid = _serial + ".relay" + i;
            _relay = new Relay(hwid);
            TextView tv = new TextView(getContext());
            tv.setText(String.format(Locale.US, "State of relay %d", i));
            gv.addView(tv);
            _customSwitch = new CustomSwitch(getContext());
            _useAB = useAB;
            if (_useAB) {
                _customSwitch.setTextOn("B");
                _customSwitch.setTextOff("A");
            }
            _customSwitch.setOnCheckedChangeListener(new CustomOnCheckedChangeListener()
            {
                @Override
                void onCheckedChangedBg(boolean isChecked) throws YAPI_Exception
                {
                    if (_useAB) {
                        _relay.setStateBg(isChecked ? YRelay.STATE_B : YRelay.STATE_A);
                    } else {
                        _relay.setOutputBg(isChecked ? YRelay.OUTPUT_ON : YRelay.OUTPUT_OFF);
                    }
                }
            });
            gv.addView(_customSwitch);
        }

        public void updateUI()
        {
            if (_useAB) {
                _customSwitch.setCheckedNoNotify(_relay.getOutput() == YRelay.OUTPUT_ON);
            } else {
                _customSwitch.setCheckedNoNotify(_relay.getState() == YRelay.STATE_B);
            }
        }

        public void reloadBg() throws YAPI_Exception
        {
            _relay.reloadBg();
        }
    }

}