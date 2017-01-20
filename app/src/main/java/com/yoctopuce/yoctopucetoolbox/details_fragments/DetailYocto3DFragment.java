package com.yoctopuce.yoctopucetoolbox.details_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.yoctopucetoolbox.R;
import com.yoctopuce.yoctopucetoolbox.functions.Accelerometer;
import com.yoctopuce.yoctopucetoolbox.functions.Compass;
import com.yoctopuce.yoctopucetoolbox.functions.Gyro;
import com.yoctopuce.yoctopucetoolbox.functions.Magnetometer;
import com.yoctopuce.yoctopucetoolbox.functions.Tilt;

import java.util.Locale;

public class DetailYocto3DFragment extends DetailGenericModuleFragment
{
    private TextView _tilt_rollTextView;
    private TextView _tilt_pitchTextView;
    private TextView _compassTextView;
    private TextView _accelerometerTextView;
    private TextView _accelerometer_xTextView;
    private TextView _accelerometer_yTextView;
    private TextView _accelerometer_zTextView;
    private TextView _magnetometerTextView;
    private TextView _magnetometer_xTextView;
    private TextView _magnetometer_yTextView;
    private TextView _magnetometer_zTextView;
    private TextView _gyroTextView;
    private TextView _gyro_yTextView;
    private TextView _gyro_zTextView;
    private TextView _gyro_xTextView;
    private Tilt _tilt1;
    private Tilt _tilt2;
    private Compass _compass;
    private Accelerometer _accelerometer;
    private Gyro _gyro;
    private Magnetometer _magnetometer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail_yocto_3d, container, false);
        setupUI(rootView);
        return rootView;
    }

    @Override
    protected void reloadDataInBG() throws YAPI_Exception
    {
        super.reloadDataInBG();
        _tilt1.reloadBg();
        _tilt2.reloadBg();
        _compass.reloadBg();
        _accelerometer.reloadBg();
        _magnetometer.reloadBg();
        _gyro.reloadBg();
    }

    @Override
    protected void setupUI(View rootView)
    {
        super.setupUI(rootView);
        _tilt1 = new Tilt(_argSerial + ".tilt1");
        _tilt2 = new Tilt(_argSerial + ".tilt2");
        _compass = new Compass(_argSerial + ".compass");
        _accelerometer = new Accelerometer(_argSerial + ".accelerometer");
        _magnetometer = new Magnetometer(_argSerial + ".magnetometer");
        _gyro = new Gyro(_argSerial + ".gyro");


        _tilt_rollTextView = (TextView) rootView.findViewById(R.id.tilt_roll);
        _tilt_pitchTextView = (TextView) rootView.findViewById(R.id.tilt_pitch);
        _compassTextView = (TextView) rootView.findViewById(R.id.compass);
        _accelerometerTextView = (TextView) rootView.findViewById(R.id.accelerometer);
        _accelerometer_xTextView = (TextView) rootView.findViewById(R.id.accelerometer_x);
        _accelerometer_yTextView = (TextView) rootView.findViewById(R.id.accelerometer_y);
        _accelerometer_zTextView = (TextView) rootView.findViewById(R.id.accelerometer_z);
        _magnetometerTextView = (TextView) rootView.findViewById(R.id.magnetometer);
        _magnetometer_xTextView = (TextView) rootView.findViewById(R.id.magnetometer_x);
        _magnetometer_yTextView = (TextView) rootView.findViewById(R.id.magnetometer_y);
        _magnetometer_zTextView = (TextView) rootView.findViewById(R.id.magnetometer_z);
        _gyroTextView = (TextView) rootView.findViewById(R.id.gyro);
        _gyro_xTextView = (TextView) rootView.findViewById(R.id.gyro_x);
        _gyro_yTextView = (TextView) rootView.findViewById(R.id.gyro_y);
        _gyro_zTextView = (TextView) rootView.findViewById(R.id.gyro_z);
    }

    @Override
    protected void updateUI(boolean firstUpdate)
    {
        super.updateUI(firstUpdate);
        _tilt_rollTextView.setText(String.format(Locale.US, "%.1f °", _tilt1.getCurrentValue()));
        _tilt_pitchTextView.setText(String.format(Locale.US, "%.1f °", _tilt2.getCurrentValue()));
        _compassTextView.setText(String.format(Locale.US, "%.1f °", _compass.getCurrentValue()));
        _accelerometerTextView.setText(String.format(Locale.US, "%.3f g", _accelerometer.getCurrentValue()));
        _accelerometer_xTextView.setText(String.format(Locale.US, "%.3f g", _accelerometer.getXValue()));
        _accelerometer_yTextView.setText(String.format(Locale.US, "%.3f g", _accelerometer.getYValue()));
        _accelerometer_zTextView.setText(String.format(Locale.US, "%.3f g", _accelerometer.getYValue()));
        _magnetometerTextView.setText(String.format(Locale.US, "%.3f gauss", _magnetometer.getCurrentValue()));
        _magnetometer_xTextView.setText(String.format(Locale.US, "%.3f gauss", _magnetometer.getXValue()));
        _magnetometer_yTextView.setText(String.format(Locale.US, "%.3f gauss", _magnetometer.getYValue()));
        _magnetometer_zTextView.setText(String.format(Locale.US, "%.3f gauss", _magnetometer.getYValue()));
        _gyroTextView.setText(String.format(Locale.US, "%.1f °", _gyro.getCurrentValue()));
        _gyro_xTextView.setText(String.format(Locale.US, "%.1f °", _gyro.getXValue()));
        _gyro_yTextView.setText(String.format(Locale.US, "%.1f °", _gyro.getYValue()));
        _gyro_zTextView.setText(String.format(Locale.US, "%.1f °", _gyro.getYValue()));
    }
}
