package com.yoctopuce.yoctopucetoolbox.configure_fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.yoctopuce.yoctopucetoolbox.R;
import com.yoctopuce.yoctopucetoolbox.details_fragments.DetailGenericModuleFragment;
import com.yoctopuce.yoctopucetoolbox.functions.Network;
import com.yoctopuce.yoctopucetoolbox.misc.TaskRunner;

public class EnterLANSettingsFragment extends Fragment
{
    private EditText _ip;
    private EditText _netmask;
    private EditText _router;
    private EditText _dns1;
    private EditText _dns2;
    private TextView _msg;
    private Network _network;
    private String _argSerial;
    private RadioButton _dhcp;
    private RadioButton _staticip;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            _argSerial = getArguments().getString(DetailGenericModuleFragment.ARG_SERIAL);
            setTitle("Ip Configuration");
        }
    }

    private void setTitle(String title)
    {
        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(title);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.configure_ip, container, false);
        _network = new Network(_argSerial + ".network");

        _ip = rootView.findViewById(R.id.ip_addr);
        _netmask = rootView.findViewById(R.id.netmask);
        _router = rootView.findViewById(R.id.router);
        _dns1 = rootView.findViewById(R.id.dns1);
        _dns2 = rootView.findViewById(R.id.dns2);
        _msg = rootView.findViewById(R.id.ip_descr);
        _staticip = rootView.findViewById(R.id.staticip);
        _dhcp = rootView.findViewById(R.id.dhcp);
        RadioGroup radiogroup = rootView.findViewById(R.id.ip_mode);
        radiogroup.setOnCheckedChangeListener((radioGroup, id) -> {
            if (id == R.id.staticip) {
                _msg.setText("Default parameters, while no DHCP offer is received :");
            } else if (id == R.id.dhcp) {
                _msg.setText("ermanent parameters :");
            }
        });
        Button apply = rootView.findViewById(R.id.apply);
        apply.setOnClickListener(view -> {
            String ip = _ip.getText().toString();
            String netmask = _netmask.getText().toString();
            String router = _router.getText().toString();
            String dns1 = _dns1.getText().toString();
            String dns2 = _dns2.getText().toString();
            boolean dhcp = radiogroup.getCheckedRadioButtonId() != R.id.staticip;
            String[] parts = netmask.split("\\.");
            int masklen = 0;
            int i;
            int mask_part = 0;
            for (i = 0; i < parts.length && i < 4; i++) {
                mask_part = Integer.parseInt(parts[i]);
                if (mask_part < 255) break;
                masklen += 8;
            }
            if (i < parts.length && i < 4) {
                while (mask_part > 0) {
                    mask_part = (mask_part << 1) & 255;
                    masklen++;
                }
            }
            String newconf = dhcp ? "DHCP:" : "STATIC:" + ip + '/' + masklen + '/' + router;

            new TaskRunner().executeAsync(() -> {
                _network.setPrimaryDNSBg(dns1);
                _network.setSecondaryDNSBg(dns2);
                _network.setIpConfigBg(newconf);
                return "";
            }, result -> {
                getParentFragmentManager().popBackStack();
            });

        });
        new TaskRunner().executeAsync(() -> {
            _network.reloadBg();
            return "";
        }, result -> refreshUI());

        return rootView;
    }

    private void refreshUI()
    {

        String[] c = _network.getIpConfig().split("[:\\/]");
        if(c[0].equals("DHCP")){
            _dhcp.setChecked(true);
        } else{
            _staticip.setChecked(true);
        }
        int masklen = 22;
        String nm = "";
        int nn = 0;
        while (masklen > 8) {
            nm += "255.";
            nn++;
            masklen -= 8;
        }
        while (nn < 4) {
            int nm_val;
            if (masklen > 0) {
                nm_val = 255 - ((1 << (8 - masklen)) - 1);
            } else {
                nm_val = 0;
            }
            nm += String.format("%d", nm_val);
            if (++nn < 4) {
                nm += '.';
            }
            masklen -= 8;
        }
        _ip.setText(c[1]);
        _netmask.setText(nm);
        _router.setText(c[3]);
        String ad = _network.getPrimaryDNS();
        if(ad.equals("0.0.0.0")) {
            ad = "";
        }
        _dns1.setText(ad);
        ad = _network.getSecondaryDNS();
        if(ad.equals("0.0.0.0")) {
            ad = "";
        }
        _dns2.setText(ad);
    }


}
