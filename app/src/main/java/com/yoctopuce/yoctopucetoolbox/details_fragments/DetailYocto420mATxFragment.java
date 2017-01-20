package com.yoctopuce.yoctopucetoolbox.details_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YCurrentLoopOutput;
import com.yoctopuce.yoctopucetoolbox.R;
import com.yoctopuce.yoctopucetoolbox.functions.CurrentLoopOutput;
import com.yoctopuce.yoctopucetoolbox.misc.MiscHelper;

import java.util.Locale;

/**
 * Created by seb on 22.11.2016.
 */
public class DetailYocto420mATxFragment extends DetailGenericModuleFragment implements SeekBar.OnSeekBarChangeListener
{
    private CurrentLoopOutput _currentLoopOutput;
    private TextView _loopValue;
    private SeekBar _loopSeekBar;
    private long _lastchangeupdate;
    private TextView _loopPower;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail_yocto_4_20ma_tx, container, false);
        setupUI(rootView);
        return rootView;
    }

    @Override
    protected void reloadDataInBG() throws YAPI_Exception
    {
        super.reloadDataInBG();
        _currentLoopOutput.reloadBg();
    }

    @Override
    protected void setupUI(View rootView)
    {
        super.setupUI(rootView);
        _currentLoopOutput = new CurrentLoopOutput(_argSerial + ".currentLoopOutput");
        _loopPower = (TextView) rootView.findViewById(R.id.power_mode);
        _loopValue = (TextView) rootView.findViewById(R.id.loop_value);
        _loopSeekBar = (SeekBar) rootView.findViewById(R.id.loop);
        _loopSeekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    protected void updateUI(boolean firstUpdate)
    {
        super.updateUI(firstUpdate);
        final double current = _currentLoopOutput.getCurrent();
        _loopValue.setText(String.format(Locale.US, "%.2fmA", current));
        if (firstUpdate) {
            final int progress = value2progress(current);
            _loopSeekBar.setProgress(progress);
        }
        int text;
        switch (_currentLoopOutput.getLoopPower()) {
            case YCurrentLoopOutput.LOOPPOWER_POWEROK:
                text = R.string.powered;
                break;
            case YCurrentLoopOutput.LOOPPOWER_LOWPWR:
                text = R.string.low_power;
                break;
            default:
                text = R.string.not_powered;
                break;
        }
        _loopPower.setText(text);
    }


    static final double MIN = 3;
    static final double MAX = 21;

    private int value2progress(double current)
    {
        return (int) ((current - MIN) * 1000 / (MAX - MIN));
    }


    private double progress2value(int progress)
    {
        return ((progress * (MAX - MIN)) / 1000) + MIN;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, final int progress, boolean fromUser)
    {
        final double newVal = progress2value(progress);
        if (fromUser) {
            final long now = System.currentTimeMillis();
            if (now - _lastchangeupdate > 250) {
                // prevents too many updates while the user is moving the cursor
                _lastchangeupdate = now;
                _bgHandler.post(new BGHandler.BgRunnable()
                {
                    @Override
                    public void runBg() throws YAPI_Exception
                    {
                        _currentLoopOutput.setCurrentBg(newVal);
                    }
                });
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar)
    {

    }

    @Override
    public void onStopTrackingTouch(final SeekBar seekBar)
    {

        final double newval = progress2value(seekBar.getProgress());
        _bgHandler.post(new BGHandler.BgRunnable()
        {

            @Override
            public void runBg() throws YAPI_Exception
            {
                _currentLoopOutput.setCurrentBg(newval);
            }
        });
    }

}
