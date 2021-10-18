package com.yoctopuce.yoctopucetoolbox.details_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YDualPower;
import com.yoctopuce.yoctopucetoolbox.R;
import com.yoctopuce.yoctopucetoolbox.functions.DualPower;
import com.yoctopuce.yoctopucetoolbox.functions.Servo;
import com.yoctopuce.yoctopucetoolbox.service.BgRunnable;

import java.util.Locale;

public class DetailYoctoServoFragment extends DetailGenericModuleFragment implements SeekBar.OnSeekBarChangeListener
{

    private Servo _servo1;
    private Servo _servo2;
    private Servo _servo3;
    private Servo _servo4;
    private Servo _servo5;
    private DualPower _dualPower;
    private TextView _power_mode;
    private TextView _extVoltage;
    private TextView _status;
    private SeekBar _servo1SeekBar;
    private SeekBar _servo2SeekBar;
    private SeekBar _servo3SeekBar;
    private SeekBar _servo4SeekBar;
    private SeekBar _servo5SeekBar;
    private long _lastchangeupdate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail_yocto_servo, container, false);
        setupUI(rootView);
        return rootView;
    }

    @Override
    protected void reloadDataInBG(boolean firstReload) throws YAPI_Exception
    {
        super.reloadDataInBG(firstReload);
        _servo1.reloadBg();
        _servo2.reloadBg();
        _servo3.reloadBg();
        _servo4.reloadBg();
        _servo5.reloadBg();
        _dualPower.reloadBg();
    }

    @Override
    protected void setupUI(View rootView)
    {
        super.setupUI(rootView);
        _servo1 = new Servo(_argSerial + ".servo1");
        _servo2 = new Servo(_argSerial + ".servo2");
        _servo3 = new Servo(_argSerial + ".servo3");
        _servo4 = new Servo(_argSerial + ".servo4");
        _servo5 = new Servo(_argSerial + ".servo5");
        _dualPower = new DualPower(_argSerial + ".dualPower");

        _power_mode = rootView.findViewById(R.id.power_mode);
        _status = rootView.findViewById(R.id.power_status);
        _extVoltage = rootView.findViewById(R.id.ext_voltage);

        _servo1SeekBar = rootView.findViewById(R.id.servo1);
        _servo1SeekBar.setOnSeekBarChangeListener(this);
        _servo2SeekBar = rootView.findViewById(R.id.servo2);
        _servo2SeekBar.setOnSeekBarChangeListener(this);
        _servo3SeekBar = rootView.findViewById(R.id.servo3);
        _servo3SeekBar.setOnSeekBarChangeListener(this);
        _servo4SeekBar = rootView.findViewById(R.id.servo4);
        _servo4SeekBar.setOnSeekBarChangeListener(this);
        _servo5SeekBar = rootView.findViewById(R.id.servo5);
        _servo5SeekBar.setOnSeekBarChangeListener(this);

    }

    @Override
    protected void updateUI(boolean firstUpdate)
    {
        super.updateUI(firstUpdate);
        if (firstUpdate) {
            _servo1SeekBar.setProgress(_servo1.getPosition() + 1000);
            _servo2SeekBar.setProgress(_servo2.getPosition() + 1000);
            _servo3SeekBar.setProgress(_servo3.getPosition() + 1000);
            _servo4SeekBar.setProgress(_servo4.getPosition() + 1000);
            _servo5SeekBar.setProgress(_servo5.getPosition() + 1000);
        }
        int pwrmodecaption = R.string.n_a;
        switch (_dualPower.getPowerControl()) {
            case YDualPower.POWERCONTROL_AUTO:
                pwrmodecaption = R.string.automatic;
                break;
            case YDualPower.POWERCONTROL_FROM_USB:
                pwrmodecaption = R.string.usb;
                break;
            case YDualPower.POWERCONTROL_FROM_EXT:
                pwrmodecaption = R.string.external;
                break;
            case YDualPower.POWERCONTROL_OFF:
                pwrmodecaption = R.string.OFF;
                break;
        }
        _power_mode.setText(pwrmodecaption);
        int statecaption = R.string.off;
        switch (_dualPower.getPowerState()) {
            case YDualPower.POWERSTATE_FROM_USB:
                statecaption = R.string.usb;
                break;
            case YDualPower.POWERSTATE_FROM_EXT:
                statecaption = R.string.external;
                break;
        }
        _status.setText(statecaption);
        final int extVoltage = _dualPower.getExtVoltage();
        _extVoltage.setText(String.format(Locale.US, "%d V", extVoltage));

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, final int i, boolean fromUser)
    {
        if (fromUser) {
            final long now = System.currentTimeMillis();
            if (now - _lastchangeupdate > 250) {
                // prevents too many updates while the user is moving the cursor
                _lastchangeupdate = now;
                final Servo servo = _getServoFromID(seekBar);
                postBg(new BgRunnable()
                {
                    @Override
                    public void runBg() throws YAPI_Exception
                    {
                        servo.setPositionBg(i - 1000);
                    }
                });
            }
        }


    }

    protected Servo _getServoFromID(SeekBar seekBar)
    {
        Servo servo;
        int id = seekBar.getId();
        if (id == R.id.servo1) {
            servo = _servo1;
        } else if (id == R.id.servo2) {
            servo = _servo2;
        } else if (id == R.id.servo3) {
            servo = _servo3;
        } else if (id == R.id.servo4) {
            servo = _servo4;
        } else if (id == R.id.servo5) {
            servo = _servo5;
        } else {
            servo = null;
        }
        return servo;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar)
    {

    }

    @Override
    public void onStopTrackingTouch(final SeekBar seekBar)
    {
        final Servo servo = _getServoFromID(seekBar);
        final int progress = seekBar.getProgress();
        postBg(new BgRunnable()
        {

            @Override
            public void runBg() throws YAPI_Exception
            {
                servo.setPositionBg(progress - 1000);
            }
        });
    }
}
