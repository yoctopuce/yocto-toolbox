package com.yoctopuce.yoctopucetoolbox.configure_fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YDataLogger;
import com.yoctopuce.yoctopucetoolbox.R;
import com.yoctopuce.yoctopucetoolbox.functions.DataLogger;
import com.yoctopuce.yoctopucetoolbox.functions.Sensor;

import java.util.ArrayList;
import java.util.HashMap;

public class ConfigureSensorFragement extends ConfigureGenericModuleFragment
{

    private TableLayout _dataloggerTableLayout;
    private SwitchCompat _recordingSwitch;
    private SwitchCompat _autostartSwitch;
    private SwitchCompat _linkBeaconSwitch;
    private DataLogger _dataLogger;
    final ArrayList<Sensor> _sensorsList = new ArrayList<>(6);
    private final HashMap<Sensor, DataloggerWidgets> _dataLoggerWidgetsList = new HashMap<>(6);
    private boolean recording;
    private boolean autostart;
    private boolean linkBeacon;
    private HashMap<Sensor, String> rfreq;
    private HashMap<Sensor, String> lfreq;


    @Override
    protected void prepareApply()
    {
        super.prepareApply();

        recording = _recordingSwitch.isChecked();
        autostart = _autostartSwitch.isChecked();
        linkBeacon = _linkBeaconSwitch.isChecked();

        rfreq = new HashMap<>(_sensorsList.size());
        lfreq = new HashMap<>(_sensorsList.size());

        for (Sensor sensor : _dataLoggerWidgetsList.keySet()) {
            DataloggerWidgets wg = _dataLoggerWidgetsList.get(sensor);
            assert wg != null;
            String freq = wg.spinner.getSelectedItem().toString();
            if (wg.logSwitch.isChecked()) {
                lfreq.put(sensor, freq);
            } else {
                lfreq.put(sensor, "OFF");
            }
            if (wg.reportSwitch.isChecked()) {
                rfreq.put(sensor, freq);
            } else {
                rfreq.put(sensor, "OFF");
            }
        }
    }

    @Override
    protected void executeApplyBG() throws YAPI_Exception
    {
        super.executeApplyBG();
        for (Sensor s : rfreq.keySet()) {
            String newval = rfreq.get(s);
            Log.d("config", "Set Report freq to " + newval + " for sensor " + s.getHwId());
            s.setReportFrequencyBg(newval);
        }
        for (Sensor s : lfreq.keySet()) {
            s.setLogFrequencyBg(lfreq.get(s));
        }
        _dataLogger.setRecordingBg(recording ? YDataLogger.RECORDING_ON : YDataLogger.RECORDING_OFF);
        _dataLogger.setAutoStartBg(autostart ? YDataLogger.AUTOSTART_ON : YDataLogger.AUTOSTART_OFF);
        _dataLogger.setBeaconDrivenBg(linkBeacon ? YDataLogger.BEACONDRIVEN_ON : YDataLogger.BEACONDRIVEN_OFF);
    }

    @Override
    protected int getLayout()
    {
        return R.layout.fragment_configure_datalogger;
    }

    @Override
    protected void setupUI(View rootView)
    {
        super.setupUI(rootView);
        _recordingSwitch = rootView.findViewById(R.id.datalogger_record);
        _autostartSwitch = rootView.findViewById(R.id.datalogger_autostart);
        _linkBeaconSwitch = rootView.findViewById(R.id.datalogger_link_beacon);
        _dataloggerTableLayout = rootView.findViewById(R.id.datalogger_functions);


    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        _dataLogger = new DataLogger(_argSerial + ".dataLogger");
    }

    @Override
    protected void reloadDataInBG(boolean firstReload) throws YAPI_Exception
    {
        super.reloadDataInBG(firstReload);
        _dataLogger.reloadBg();
        if (firstReload) {
            ArrayList<String> yFunction = _module.get_functionIds("Sensor");
            for (String funcid : yFunction) {
                String hwid = _argSerial + "." + funcid;
                Sensor sensor = new Sensor(hwid);
                _sensorsList.add(sensor);
            }
        }
        for (Sensor sensor : _sensorsList) {
            sensor.reloadBg();
        }

    }

    @Override
    protected void updateUI(boolean firstUpdate)
    {
        super.updateUI(firstUpdate);
        if (firstUpdate) {
            _recordingSwitch.setChecked(_dataLogger.getRecording() == YDataLogger.RECORDING_ON);
            _autostartSwitch.setChecked(_dataLogger.getAutoStart() == YDataLogger.AUTOSTART_ON);
            _linkBeaconSwitch.setChecked(_dataLogger.getBeaconDriven() == YDataLogger.BEACONDRIVEN_ON);
            for (Sensor sensor : _sensorsList) {
                addDataloggerWidget(sensor, sensor.getLogFrequency(), sensor.getReportFrequency());
            }
        }

    }


    private void addDataloggerWidget(Sensor sensor, String lfreq, String rfreq)
    {
        String functionId = sensor.getFunId();
        Context context = requireContext();
        TableRow tr = new TableRow(context);
        TableLayout.LayoutParams params = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
        tr.setLayoutParams(params);
        TextView tv = new TextView(context);
        tv.setText(functionId);
        tr.addView(tv);

        SwitchCompat logSwitch = new SwitchCompat(context);
        SwitchCompat reportSwitch = new SwitchCompat(context);
        Spinner spinner = new Spinner(context);
        DataloggerWidgets dataloggerWidgets = new DataloggerWidgets(spinner, logSwitch, reportSwitch);
        _dataLoggerWidgetsList.put(sensor, dataloggerWidgets);


        //Create an ArrayAdapter by using a string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.datalogger_frequency, android.R.layout.simple_spinner_item);
        //Specify the layout to use when the list of choices appear
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Now apply the adapter to spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = parent.getSelectedItem().toString();
                boolean enabled = !selectedItem.equals("OFF");
                dataloggerWidgets.reportSwitch.setEnabled(enabled);
                dataloggerWidgets.logSwitch.setEnabled(enabled);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        //fixme: look how to center the switch
        //params = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
        //params.gravity = Gravity.CENTER;

        //logSwitch.setLayoutParams(params);
        //LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //reportSwitch.setLayoutParams(layoutParams);
        //reportSwitch.setLayoutParams(params);

        String freq = "OFF";
        if (!lfreq.equals("OFF")) {
            freq = lfreq;
            logSwitch.setChecked(true);
        }
        if (!rfreq.equals("OFF")) {
            freq = rfreq;
            reportSwitch.setChecked(true);
        }
        if (freq.equals("OFF")) {
            reportSwitch.setEnabled(false);
            logSwitch.setEnabled(false);
        }

        spinner.setSelection(adapter.getPosition(freq));

        tr.addView(spinner);
        tr.addView(logSwitch);
        tr.addView(reportSwitch);
        _dataloggerTableLayout.addView(tr);


    }

    public static class DataloggerWidgets
    {

        public final Spinner spinner;
        public final SwitchCompat logSwitch;
        public final SwitchCompat reportSwitch;

        public DataloggerWidgets(Spinner spinner, SwitchCompat logSwitch, SwitchCompat reportSwitch)
        {

            this.spinner = spinner;
            this.logSwitch = logSwitch;
            this.reportSwitch = reportSwitch;
        }
    }
}
