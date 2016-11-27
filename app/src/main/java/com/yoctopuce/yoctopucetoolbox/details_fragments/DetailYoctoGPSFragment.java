package com.yoctopuce.yoctopucetoolbox.details_fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YGps;
import com.yoctopuce.yoctopucetoolbox.R;
import com.yoctopuce.yoctopucetoolbox.functions.Gps;

import java.util.Locale;

public class DetailYoctoGPSFragment extends DetailGenericModuleFragment
{
    private TextView _statusTextView;
    private TextView _timeTextView;
    private TextView _latitudeTextView;
    private TextView _longitudeTextView;
    private TextView _altitudeTextView;
    private TextView _groundSpeedTextView;
    private TextView _precisionTextView;
    private Gps _gps;
    private Button _mapButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail_yocto_gps, container, false);
        setupUI(rootView);
        return rootView;
    }

    @Override
    protected void reloadDataInBG() throws YAPI_Exception
    {
        super.reloadDataInBG();
        _gps.reloadBg();
    }

    @Override
    protected void setupUI(View rootView)
    {
        super.setupUI(rootView);
        _gps = new Gps(_serial + ".gps");

        _statusTextView = (TextView) rootView.findViewById(R.id.status);
        _timeTextView = (TextView) rootView.findViewById(R.id.time);
        _latitudeTextView = (TextView) rootView.findViewById(R.id.latitude);
        _longitudeTextView = (TextView) rootView.findViewById(R.id.longitude);
        _altitudeTextView = (TextView) rootView.findViewById(R.id.altitude);
        _groundSpeedTextView = (TextView) rootView.findViewById(R.id.ground_speed);
        _precisionTextView = (TextView) rootView.findViewById(R.id.precision);
        _mapButton = (Button) rootView.findViewById(R.id.open_map_button);
        _mapButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Creates an Intent that will load a map of San Francisco
                Uri gmmIntentUri = Uri.parse(String.format(Locale.US,"geo:%s,%s",_gps.getLatitude(),_gps.getLongitude()));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void updateUI()
    {
        super.updateUI();
        if (_gps.getIsFixed() == YGps.ISFIXED_TRUE) {
            _statusTextView.setText(String.format(Locale.US, getString(R.string.x_satellites), _gps.getSatCount()));
            _timeTextView.setText(_gps.getDateTime());
            _latitudeTextView.setText(_gps.getLatitude());
            _longitudeTextView.setText(_gps.getLongitude());
            _altitudeTextView.setText(String.format(Locale.US, "%d m", Math.round(_gps.getAltitude())));
            _groundSpeedTextView.setText(String.format(Locale.US, "%.1f Km/h", _gps.getGroundSpeed()));
            _precisionTextView.setText(Double.toString(_gps.getDilution()));
            _mapButton.setEnabled(true);
        } else {
            _statusTextView.setText(R.string.fixing___);
            _timeTextView.setText(R.string.n_a);
            _latitudeTextView.setText(R.string.n_a);
            _longitudeTextView.setText(R.string.n_a);
            _altitudeTextView.setText(R.string.n_a);
            _groundSpeedTextView.setText(R.string.n_a);
            _precisionTextView.setText(R.string.n_a);
            _mapButton.setEnabled(false);

        }
    }
}
