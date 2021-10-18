package com.yoctopuce.yoctopucetoolbox.configure_fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YModule;
import com.yoctopuce.yoctopucetoolbox.R;
import com.yoctopuce.yoctopucetoolbox.details_fragments.DetailGenericModuleFragment;
import com.yoctopuce.yoctopucetoolbox.functions.Wireless;
import com.yoctopuce.yoctopucetoolbox.misc.DividerItemDecoration;
import com.yoctopuce.yoctopucetoolbox.misc.TaskRunner;
import com.yoctopuce.yoctopucetoolbox.service.UseHubFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EnterWifiSettingsFragment extends UseHubFragment
{
    //todo: factorise common code with EnterLANSettingsFragment
    private String _argSerial;

    private TextView _wlanDetectTextView;
    private Button _refresButton;
    private View _manualParmRadioGroup;
    private RadioButton _wpaSecRadioButton;
    private EditText _ssid;
    private RadioGroup _securtity_type;
    private EditText _key;

    private Wireless _wireless;
    private RadioButton _detectedWlanInfraManRadioGroup;
    private RadioButton _detectedWlanSoftAPRadioGroup;
    private ArrayList<WLAN> _dicoveredWLAN;
    private EnterWifiSettingsFragment.WlanAdapter _adapter;
    private RecyclerView _hubRecyclerView;
    private RadioButton _wepSecRadioButton;
    private RadioButton _openSecRadioButton;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            _argSerial = getArguments().getString(DetailGenericModuleFragment.ARG_SERIAL);
            setTitle("WLAN Configuration");
        }
    }

    //fixme: move to a subclass
    private void setTitle(String title)
    {
        Activity activity = this.requireActivity();
        CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(title);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.configure_wifi, container, false);

        _wireless = new Wireless(_argSerial + ".wireless");
        _wlanDetectTextView = rootView.findViewById(R.id.wlan_detect);
        _detectedWlanInfraManRadioGroup = rootView.findViewById(R.id.infra_man);
        _detectedWlanSoftAPRadioGroup = rootView.findViewById(R.id.sofap);

        _hubRecyclerView = rootView.findViewById(R.id.wlan_list_recycler_view);
        _hubRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL_LIST);
        _hubRecyclerView.addItemDecoration(itemDecoration);
        _dicoveredWLAN = new ArrayList<>();
        _adapter = new EnterWifiSettingsFragment.WlanAdapter(_dicoveredWLAN);
        _hubRecyclerView.setAdapter(_adapter);


        _manualParmRadioGroup = rootView.findViewById(R.id.manual_parameter);
        _refresButton = rootView.findViewById(R.id.refresh);
        _refresButton.setOnClickListener(view -> TriggerWlanDetection());


        RadioGroup wifiModeGroup = rootView.findViewById(R.id.wifimode);
        wifiModeGroup.setOnCheckedChangeListener((radioGroup, id) -> {
            if (id == R.id.infra_man) {
                modeConfigChange(1);
            } else if (id == R.id.sofap) {
                modeConfigChange(2);
            } else {
                modeConfigChange(0);
            }
        });
        _wpaSecRadioButton = rootView.findViewById(R.id.wpa_security);
        _wepSecRadioButton = rootView.findViewById(R.id.wep_security);
        _openSecRadioButton = rootView.findViewById(R.id.open_security);

        _ssid = rootView.findViewById(R.id.ssid);
        _securtity_type = rootView.findViewById(R.id.wlan_security);
        _key = rootView.findViewById(R.id.key);
        CheckBox showKey = rootView.findViewById(R.id.show_key);
        showKey.setOnClickListener(view -> {
            if (showKey.isChecked()) {
                _key.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                _key.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });
        Button apply = rootView.findViewById(R.id.apply);
        apply.setOnClickListener(view -> applySettings());
        new TaskRunner().executeAsync(() -> {
            _wireless.reloadBg();
            return getWlanJsonBg();
        }, this::refreshUI);


        return rootView;
    }

    private void TriggerWlanDetection()
    {
        new TaskRunner().executeAsync(this::getWlanJsonBg, this::parsWlanJson);
    }

    @NonNull
    private String getWlanJsonBg() throws YAPI_Exception
    {
        YModule module = YModule.FindModule(_argSerial + ".module");
        byte[] content = module.download("wlan.json");
        return new String(content);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void parsWlanJson(String result)
    {
        try {
            JSONObject jObject = new JSONObject(result);

            JSONArray wifis = jObject.getJSONArray("byName");
            int j;
            for (j = 0; j < _dicoveredWLAN.size(); j++) {
                _dicoveredWLAN.get(j).setRSSI(0);
            }
            for (int i = 0; i < wifis.length(); i++) {
                JSONObject wifi = wifis.getJSONObject(i);
                String ssid = wifi.getString("ssid");
                int channel = wifi.getInt("channel");
                int rssi = wifi.getInt("rssi");
                String sec = wifi.getString("sec");
                if (ssid.equals("")) {
                    continue;
                }
                boolean found = false;
                WLAN wlan = null;
                for (j = 0; j < _dicoveredWLAN.size(); j++) {
                    wlan = _dicoveredWLAN.get(j);
                    if (ssid.equals(wlan.getSSID())) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    wlan = new WLAN();
                    wlan.setSSID(ssid);
                    _dicoveredWLAN.add(wlan);
                    j = _dicoveredWLAN.size() - 1;
                }
                if (ssid.equals(_ssid.getText().toString())) {
                    wlan.setSelected(true);

                }
                wlan.setChannel(channel);
                wlan.setRSSI(rssi);
                wlan.setSec(sec);
            }
            _adapter.notifyDataSetChanged();
            ;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void applySettings()
    {

        String newconf = null;
        /*
        if (wdg('off').checked) {
            newconf = "OFF:\\";
            if (newconf != currconf) {
                wlan.set_wlanConfig(newconf);
                currconf = newconf;
            }
        }
        */
        String ssid = _ssid.getText().toString();
        String k = _key.getText().toString();
        if (_detectedWlanSoftAPRadioGroup.isChecked()) {
            if (k.length() >= 44 && k.length() <= 60) {//obfuscated key
                newconf = "SOFTAP:" + ssid + '\\' + k;
            } else { //plain key
                if (_openSecRadioButton.isChecked()) {
                    k = "";
                } else {
                    /*
                    fixme: implement this check
                    if (len != 13 && len != 5) {
                        if (len != 26 && len != 10 || !^[0-9A-F]*$/i.test(k)) {
                            yoctoAlert('Not a valid WEP security key !');
                            return;
                        }
                    }
                     */
                    if (_wpaSecRadioButton.isChecked()) {
                        k = "\\WPA:" + k;
                    } else if (_wepSecRadioButton.isChecked()) {
                        k = "\\WEP:" + k;
                    }
                }
                newconf = "SOFTAP:" + ssid + k;
            }
        } else {
            if (k.length() >= 44 && k.length() <= 60) {//obfuscated key
                newconf = "INFRA:" + ssid + '\\' + k;
            } else {//plain key
                if (_openSecRadioButton.isChecked()) {
                    k = "";
                } else if (k.length() > 0) {
                    if (_argSerial.startsWith("YHUBWLN4")) {
                        if (_wpaSecRadioButton.isChecked()) {
                            k = "\\WPA:" + k;
                        } else if (_wepSecRadioButton.isChecked()) {
                            k = "\\WEP:" + k;
                        }
                    } else {
                        k = "\\" + k;
                    }
                }
                newconf = "INFRA:" + ssid + k;
            }
        }


        String finalNewconf = newconf;
        new TaskRunner().executeAsync(() -> {
            _wireless.setWlanConfigBg(finalNewconf);
            return "";
        }, result -> {
            getParentFragmentManager().popBackStack();
        });
    }

    private void refreshUI(String wlanjson)
    {
        String config = _wireless.getWlanConfig();
        String[] c = config.split("[:\\\\]");
        if (c[0].equals("INFRA")) {
            _detectedWlanInfraManRadioGroup.setChecked(true);
            _ssid.setText(c.length > 1 ? c[1] : "");
            _key.setText(c.length > 2 ? c[2] : "");
        } else if (c[0].equals("OFF")) {
            //fixme: handle OFF settings for Wirelesswdg('off').checked = true;
        } else {
            _detectedWlanSoftAPRadioGroup.setChecked(true);
            _ssid.setText(c.length > 1 ? c[1] : "");
            _key.setText(c.length > 2 ? c[2] : "");
        }
        parsWlanJson(wlanjson);
    }

    private void updatedWlanSelected(WLAN wlan)
    {
        _ssid.setText(wlan.getSSID());
        switch (wlan.getSec()) {
            case "WPA":
            case "WPA2":
                _wpaSecRadioButton.setChecked(true);
                break;
            case "WEP":
                _wepSecRadioButton.setChecked(true);
                break;
            default:
                _openSecRadioButton.setChecked(true);
                break;
        }
        modeConfigChange(1);
    }

    /**
     * @param mode 0=infrastruture auto, 1=infra manual, 2=sofap
     */
    public void modeConfigChange(int mode)
    {
        _wlanDetectTextView.setText(mode > 1 ? "WiFi network to create" : "WLAN to join");
        if (mode == 0) {
            _hubRecyclerView.setVisibility(View.VISIBLE);
            _refresButton.setVisibility(View.VISIBLE);
            _manualParmRadioGroup.setVisibility(View.GONE);
            TriggerWlanDetection();
        } else {
            _hubRecyclerView.setVisibility(View.GONE);
            _refresButton.setVisibility(View.GONE);
            _manualParmRadioGroup.setVisibility(View.VISIBLE);
            _wpaSecRadioButton.setVisibility(mode > 1 ? View.GONE : View.VISIBLE);
        }
    }


    public static class WLAN
    {

        private String _SSID;
        private int _channel;
        private int _RSSI;
        private String _sec;
        private boolean _selected = false;

        public void setSSID(String ssid)
        {
            _SSID = ssid;
        }

        public String getSSID()
        {
            return _SSID;
        }

        public void setChannel(int channel)
        {
            _channel = channel;
        }

        public int getChannel()
        {
            return _channel;
        }

        public void setRSSI(int rssi)
        {
            _RSSI = rssi;
        }

        public int getRSSI()
        {
            return _RSSI;
        }

        public void setSec(String sec)
        {
            _sec = sec;
        }

        public String getSec()
        {
            return _sec;
        }

        public boolean isSelected()
        {
            return _selected;
        }

        public void setSelected(boolean selected)
        {
            _selected = selected;
        }
    }


    private class WlanAdapter extends RecyclerView.Adapter<WLANViewHolder>
    {

        private final List<WLAN> _items;

        WlanAdapter(List<WLAN> _hubs)
        {
            this._items = _hubs;
        }

        @NonNull
        @Override
        public WLANViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_wlan, parent, false);
            return new WLANViewHolder(view);
        }

        @Override
        public void onBindViewHolder(WLANViewHolder holder, int position)
        {
            WLAN wlan = _items.get(position);
            //for default check in first item


            holder.bindHub(wlan, wlan1 -> {
                if (wlan1.isSelected()) {
                    updatedWlanSelected(wlan1);
                    for (WLAN w : _items) {
                        if (w != wlan1) {
                            w.setSelected(false);
                        }
                    }
                    this.notifyDataSetChanged();
                }
                return true;
            });
        }

        @Override
        public int getItemCount()
        {
            return _items.size();
        }
    }


    private static class WLANViewHolder extends RecyclerView.ViewHolder
    {

        private final CheckBox _radio;
        private final TextView _firstTextView;
        private final TextView _secTextView;

        public WLANViewHolder(View itemView)
        {
            super(itemView);
            _radio = itemView.findViewById(R.id.chkbox);
            _firstTextView = itemView.findViewById(R.id.wlan_line1);
            _secTextView = itemView.findViewById(R.id.hub_line2);
        }

        public void bindHub(final WLAN wlan, final WLANViewHolder.OnSelectListener selectListener)
        {
            _firstTextView.setText(wlan.getSSID());
            _secTextView.setText(String.format("%s %d%%", wlan.getSec(), wlan.getRSSI()));
            _radio.setChecked(wlan.isSelected());
            if (selectListener != null) {
                _radio.setOnClickListener(view -> {
                    wlan.setSelected(_radio.isChecked());
                    selectListener.onSelectChange(wlan);
                });
            } else {
                itemView.setOnClickListener(null);
            }

        }

        public interface OnSelectListener
        {
            boolean onSelectChange(WLAN wlan);
        }


    }
}
