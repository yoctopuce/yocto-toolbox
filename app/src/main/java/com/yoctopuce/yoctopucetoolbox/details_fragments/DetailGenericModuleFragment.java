package com.yoctopuce.yoctopucetoolbox.details_fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YModule;
import com.yoctopuce.yoctopucetoolbox.ModuleDetailActivity;
import com.yoctopuce.yoctopucetoolbox.ModuleListActivity;
import com.yoctopuce.yoctopucetoolbox.R;
import com.yoctopuce.yoctopucetoolbox.functions.GenericSensor;
import com.yoctopuce.yoctopucetoolbox.functions.HubPort;
import com.yoctopuce.yoctopucetoolbox.functions.Module;
import com.yoctopuce.yoctopucetoolbox.functions.Temperature;
import com.yoctopuce.yoctopucetoolbox.service.ModulesCache;
import com.yoctopuce.yoctopucetoolbox.widget.CustomSwitch;

import java.util.Locale;


/**
 * A fragment representing a single Module detail screen.
 * This fragment is either contained in a {@link ModuleListActivity}
 * in two-pane mode (on tablets) or a {@link ModuleDetailActivity}
 * on handsets.
 */
public class DetailGenericModuleFragment extends Fragment
{
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_SERIAL = "serial";
    public static final int REFRESH_DELAY_MS = 1000;
    private static final String TAG = "DetailGen";

    protected String _serial;
    protected Module _module;
    private TextView _serialTextView;
    private TextView _productTextView;
    private TextView _logicalNameTextView;
    private TextView _firmwareTextView;
    private TextView _consumptionTextView;
    private CustomSwitch _beaconSwitch;
    private TextView _luminosityTextView;
    private long _refreshUIStartTime;
    private long _lastInputTime;

    private Handler _handler;

    private AsyncTask<Void, Void, String> _refreshUIAsyncTask = null;

    public DetailGenericModuleFragment()
    {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param serial The serial number of the device.
     * @return A new instance of fragment ModuleDetailFragment.
     */
    public static DetailGenericModuleFragment newInstance(String serial)
    {
        DetailGenericModuleFragment fragment = new DetailGenericModuleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SERIAL, serial);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            _serial = getArguments().getString(ARG_SERIAL);
            ModulesCache modulesCache = ModulesCache.getInstance();
            _module = modulesCache.getFromSerial(_serial);

            Activity activity = this.getActivity();
            _handler = new Handler();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(_module.getProductName());
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.fragment_details_generic, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.show_doc:
                String baseserial = _serial.substring(0, FragmentChooser.YOCTO_BASE_SERIAL_LEN);
                String url = getString(R.string.www_yoctopuce_com_dev_doc_url) + baseserial;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_detail_generic_module, container, false);
        setupUI(rootView);
        return rootView;
    }

    protected void setupUI(View rootView)
    {
        _serialTextView = (TextView) rootView.findViewById(R.id.serial_number);
        _productTextView = (TextView) rootView.findViewById(R.id.product_name);
        _logicalNameTextView = (TextView) rootView.findViewById(R.id.logical_name);
        _firmwareTextView = (TextView) rootView.findViewById(R.id.firmware);
        _consumptionTextView = (TextView) rootView.findViewById(R.id.consumption);
        _beaconSwitch = (CustomSwitch) rootView.findViewById(R.id.beacon_switch);
        _beaconSwitch.setOnCheckedChangeListener(new CustomOnCheckedChangeListener()
        {

            @Override
            void onCheckedChangedBg(boolean isChecked) throws YAPI_Exception
            {
                _module.setBeaconBg(isChecked ? YModule.BEACON_ON : YModule.BEACON_OFF);
            }
        });
        _luminosityTextView = (TextView) rootView.findViewById(R.id.luminosity);
    }

    Runnable _refreshUIRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            _refreshUIStartTime = System.currentTimeMillis();
            _refreshUIAsyncTask = new AsyncTask<Void, Void, String>()
            {

                @Override
                protected String doInBackground(Void... params)
                {
                    try {
                        reloadDataInBG();
                    } catch (YAPI_Exception e) {
                        e.printStackTrace();
                        return e.getLocalizedMessage();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(String s)
                {
                    Context activity = getActivity();
                    if (activity == null) {
                        // fragment is detached from activity
                        return;
                    }
                    if (s != null) {
                        // todo: better error handling
                        Toast toast = Toast.makeText(activity, s, Toast.LENGTH_SHORT);
                        if (toast != null) {
                            toast.show();
                        }
                    }
                    if (_lastInputTime <= _refreshUIStartTime) {
                        updateUI();
                    }
                    _handler.postDelayed(_refreshUIRunnable, REFRESH_DELAY_MS);
                }
            };
            _refreshUIAsyncTask.execute();
        }
    };

    public abstract class CustomOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener
    {
        public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked)
        {
            if (_refreshUIAsyncTask != null) {
                _refreshUIAsyncTask.cancel(true);
            }
            _handler.removeCallbacks(_refreshUIRunnable);
            _lastInputTime = System.currentTimeMillis();
            AsyncTask.execute(new Runnable()
            {
                @Override
                public void run()
                {
                    try {
                        CustomOnCheckedChangeListener.this.onCheckedChangedBg(isChecked);
                    } catch (YAPI_Exception e) {
                        e.printStackTrace();
                    }
                    _handler.post(_refreshUIRunnable);
                }
            });

        }

        abstract void onCheckedChangedBg(boolean isChecked) throws YAPI_Exception;
    }

    protected void reloadDataInBG() throws YAPI_Exception
    {
        _module.reloadBg();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        // Schedule the first execution
        _handler.post(_refreshUIRunnable);
    }

    @Override
    public void onPause()
    {
        _handler.removeCallbacks(_refreshUIRunnable);
        super.onPause();
    }


    protected void updateUI()
    {
        // Show the dummy content as text in a TextView.
        if (_module != null) {
            _serialTextView.setText(_module.getSerialNumber());
            _productTextView.setText(_module.getProductName());
            _logicalNameTextView.setText(_module.getLogicalName());
            _firmwareTextView.setText(_module.getFirmwareRelease());
            _consumptionTextView.setText(String.format(Locale.US, "%d mA", _module.getUsbCurrent()));
            boolean checked = _module.getBeacon() == YModule.BEACON_ON;
            _beaconSwitch.setCheckedNoNotify(checked);
            _luminosityTextView.setText(String.format(Locale.US, "%d%%", _module.getLuminosity()));
        }
    }


    protected static void updateUI_HubPort(HubPort hubPort, TextView portTextView, Switch portSwitch)
    {
        String advertisedValue = hubPort.getAdvertisedValue();
        if (advertisedValue.equals("RUN") || advertisedValue.equals("PROG")) {
            advertisedValue += " : " + hubPort.getLogicalName();
        }
        portTextView.setText(advertisedValue);
        portSwitch.setChecked(!advertisedValue.equals("OFF"));
    }


    protected static double applyResolution(double value, double resolution)
    {
        //todo: round the value to the resolution
        //var l = Math.round(Math.log(r) / Math.LN10);
        //var r = Math.pow(10, ~~l);
        return value; //Math.round(r * Math.round(v / r) * 10000) / 10000;
    }

    protected static void updateUI_temperature(Temperature tempSensor, TextView currentTextView, TextView maxTextView, TextView minTextView)
    {
        Locale locale = Locale.US;
        String unit = tempSensor.getUnit();
        unit = unit.replace('\'', '°');
        double res = tempSensor.getResolution();
        currentTextView.setText(String.format(locale, "%s %s", Double.toString(applyResolution(tempSensor.getCurrentValue(), res)), unit));
        maxTextView.setText(String.format(locale, "%s %s", Double.toString(applyResolution(tempSensor.getHighestValue(), res)), unit));
        minTextView.setText(String.format(locale, "%s %s", Double.toString(applyResolution(tempSensor.getLowestValue(), res)), unit));
    }

    protected static void updateUI_genericSensor(TextView minView, GenericSensor sensor, TextView valueView, TextView signalView, TextView maxView)
    {
        minView.setText(String.format(Locale.US, "%s %s", Double.toString(sensor.getLowestValue()), sensor.getUnit()));
        String signal = String.format(Locale.US, "%s %s", Double.toString(sensor.getSignalValue()),sensor.getSignalUnit());
        String value;
        if (sensor.getSignalRange().equals(sensor.getValueRange()) && sensor.getUnit().equals(sensor.getSignalUnit())) {
            value = signal;
            signal = "";
        } else {
            double v = sensor.getCurrentValue();
            if (v < -29998)
                value = "---";
            else if (v > 29998)
                value = "+++";
            else {
                value = String.format(Locale.US, "%s %s", Double.toString(v), sensor.getUnit());
            }
            if (signal.equals(value)) {
                signal = "";
            } else {
                signal = "(" + signal + ")";
            }
        }
        valueView.setText(value);
        signalView.setText(signal);
        maxView.setText(String.format(Locale.US, "%s %s", Double.toString(sensor.getHighestValue()), sensor.getUnit()));
    }
}
