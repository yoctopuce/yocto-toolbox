package com.yoctopuce.yoctopucetoolbox.configure_fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YNetwork;
import com.yoctopuce.yoctopucetoolbox.ModuleDetailActivity;
import com.yoctopuce.yoctopucetoolbox.ModuleListActivity;
import com.yoctopuce.yoctopucetoolbox.R;
import com.yoctopuce.yoctopucetoolbox.details_fragments.DetailGenericModuleFragment;
import com.yoctopuce.yoctopucetoolbox.functions.Network;
import com.yoctopuce.yoctopucetoolbox.functions.Wireless;


/**
 * A fragment representing a single Module detail screen.
 * This fragment is either contained in a {@link ModuleListActivity}
 * in two-pane mode (on tablets) or a {@link ModuleDetailActivity}
 * on handsets.
 */
public class ConfigureYoctoWirelessFragment extends ConfigureGenericModuleFragment
{
    // non editable views
    private TextView _statusTextView;
    private TextView _signalTextView;
    private TextView _wlanSettingsTextView;
    private TextView _devnameTextView;
    private TextView _ipsettingsTextView;
    private TextView _ipStatusTextView;
    private Network _network;
    private Wireless _wireless;
    private boolean isEhternet = false;


    @Override
    protected int getLayout()
    {
        return R.layout.fragment_config_yocto_wireless;
    }

    @Override
    protected void setupUI(View rootView)
    {
        super.setupUI(rootView);


        Activity activity = this.requireActivity();
        CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(_argSerial);
        }

        // on some click or some loading we need to wait for...
        _statusTextView = rootView.findViewById(R.id.wifi_status);
        _signalTextView = rootView.findViewById(R.id.signal_strength);
        _wlanSettingsTextView = rootView.findViewById(R.id.wifi_settings);
        _devnameTextView = rootView.findViewById(R.id.devname);
        _ipsettingsTextView = rootView.findViewById(R.id.ipSettings);
        _ipStatusTextView = rootView.findViewById(R.id.ipdetail);
        _network = new Network(_argSerial + ".network");
        isEhternet = _argSerial.startsWith("YHUBETH1");
        if (!isEhternet) {
            _wireless = new Wireless(_argSerial + ".wireless");
        }
        Button button = rootView.findViewById(R.id.config_lan);
        button.setOnClickListener(view -> {
            startDialogFragment(new EnterLANSettingsFragment());
        });
        button = rootView.findViewById(R.id.config_wlan);
        if (!isEhternet) {

            button.setOnClickListener(view -> {
                startDialogFragment(new EnterWifiSettingsFragment());
            });
        } else {
            button.setVisibility(View.GONE);
            rootView.findViewById(R.id.wlan_settings_row).setVisibility(View.GONE);
            rootView.findViewById(R.id.signal_strength_row).setVisibility(View.GONE);
        }
    }

    private void startDialogFragment(Fragment fragment)
    {
        Bundle args = new Bundle();
        args.putString(DetailGenericModuleFragment.ARG_SERIAL, _argSerial);
        fragment.setArguments(args);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.module_detail_container, fragment, null)
                .setReorderingAllowed(true)
                .addToBackStack("wifi")
                .commit();
    }


    @Override
    protected void reloadDataInBG(boolean firstReload) throws YAPI_Exception
    {
        super.reloadDataInBG(firstReload);
        _network.reloadBg();
        if (!isEhternet) {
            _wireless.reloadBg();
        }
    }

    protected void updateUI(boolean firstUpdate)
    {
        super.updateUI(firstUpdate);
        int rdy = _network.getReadiness();
        String mod = "";
        String msg = "network down";
        if (!isEhternet) {
            msg = _wireless.getMessage();
        }
        switch (rdy) {
            case YNetwork.READINESS_DOWN:
                mod = "0- " + msg;
                break;
            case YNetwork.READINESS_EXISTS:
                mod = "1- network exists";
                break;
            case YNetwork.READINESS_LINKED:
                mod = "2- network linked";
                break;
            case YNetwork.READINESS_LAN_OK:
                mod = "3- LAN ready";
                break;
            case YNetwork.READINESS_WWW_OK:
                mod = "4- WWW ready";
                break;
        }
        _statusTextView.setText(mod);
        String[] c;
        if (!isEhternet) {
            _signalTextView.setText(String.format("%d%%", _wireless.getLinkQuality()));
            c = _wireless.getWlanConfig().split("[:\\\\]");
            if (c[0].equals("INFRA")) {
                _wlanSettingsTextView.setText(c[1]);
                //wdg('chgip').style = 'display:';
            } else if (c[0].equals("OFF")) {
                _wlanSettingsTextView.setText("");
                //shtml('wl', '');
                //shtml('ipmode', '');
                //shtml('ip', '');
                //wdg('chgip').style = 'display:none';
            } else {
                if (c.length > 1) {
                    _wlanSettingsTextView.setText(c[1] + (c[0].equals("ADHOC") ? " (adhoc)" : " (SoftAP)"));
                }
                //shtml('ipmode', 'Local address');
                //shtml('ip', '(in subnet 10.x.x.x)');
                //wdg('chgip').style = 'display:none';
            }
        }
        //let hover = wlan.get_message();
        _devnameTextView.setText(_network.getLogicalName());

        c = _network.getIpConfig().split("[:/]");
        String ip = _network.getIpAddress();
        if (c[0].equals("DHCP")) {
            _ipsettingsTextView.setText("Automatic by DHCP");
            if (c[1].equals(ip)) {
                _ipStatusTextView.setText("(using default IP: " + ip + ")");
            } else {
                _ipStatusTextView.setText("(current IP: " + ip + ")");
            }
        } else {
            _ipsettingsTextView.setText("Manual (Static IP)");
            if (c.length > 1) {
                _ipStatusTextView.setText("Fixed IP: " + c[1]);
            }
        }
    }


}
