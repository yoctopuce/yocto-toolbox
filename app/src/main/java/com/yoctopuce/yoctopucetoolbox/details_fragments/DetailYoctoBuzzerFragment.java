package com.yoctopuce.yoctopucetoolbox.details_fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YAnButton;
import com.yoctopuce.YoctoAPI.YLed;
import com.yoctopuce.yoctopucetoolbox.R;
import com.yoctopuce.yoctopucetoolbox.functions.AnButton;
import com.yoctopuce.yoctopucetoolbox.functions.Buzzer;
import com.yoctopuce.yoctopucetoolbox.functions.Led;
import com.yoctopuce.yoctopucetoolbox.widget.CustomCompoundButton;

import java.util.Locale;

public class DetailYoctoBuzzerFragment extends DetailGenericModuleFragment
{

    private static final String TAG = "BUZZER";
    private AnButton _anButton1;
    private AnButton _anButton2;
    private ProgressBar _progress1;
    private ProgressBar _progress2;
    private TextView _isPressed2TextView;
    private TextView _isPressed1TextView;
    private Led _ledGreen;
    private Led _ledRed;
    private Buzzer _buzzer;
    private Spinner _spinner_red;
    private Spinner _spinner_green;
    private CustomCompoundButton _greenSwitch;
    private CustomCompoundButton _redSwitch;
    private SeekBar _volumeSeekBar;
    private SeekBar _frequencySeekBar;
    private double _lastchangeupdate;
    private TextView _frequencyValue;
    private TextView _volumeValue;
    private TextView _greenTextView;
    private SeekBar _greenSeekBar;
    private SeekBar _redSeekBar;
    private TextView _redTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail_yocto_buzzer, container, false);
        setupUI(rootView);
        return rootView;
    }

    @Override
    protected void reloadDataInBG() throws YAPI_Exception
    {
        super.reloadDataInBG();
        _anButton1.reloadBg();
        _anButton2.reloadBg();
        _ledGreen.reloadBg();
        _ledRed.reloadBg();
        _buzzer.reloadBg();
    }

    @Override
    protected void setupUI(View rootView)
    {
        super.setupUI(rootView);
        _ledGreen = new Led(_argSerial + ".led1");
        _ledRed = new Led(_argSerial + ".led2");
        _anButton1 = new AnButton(_argSerial + ".anButton1");
        _anButton2 = new AnButton(_argSerial + ".anButton2");
        _buzzer = new Buzzer(_argSerial + ".buzzer");
        _frequencyValue = (TextView) rootView.findViewById(R.id.frequency);
        _frequencySeekBar = (SeekBar) rootView.findViewById(R.id.seekBarFrequency);
        _frequencySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, final int i, boolean fromUser)
            {
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
                                _buzzer.setFrequencyBg(i);
                            }
                        });
                    }
                }
                _frequencyValue.setText(String.format(Locale.US, "%d HZ", i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(final SeekBar seekBar)
            {
                _bgHandler.post(new BGHandler.BgRunnable()
                {

                    @Override
                    public void runBg() throws YAPI_Exception
                    {
                        _buzzer.setFrequencyBg(seekBar.getProgress());
                    }
                });
            }
        });
        _volumeValue = (TextView) rootView.findViewById(R.id.volume);
        _volumeSeekBar = (SeekBar) rootView.findViewById(R.id.seekBarVolume);
        _volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, final int i, boolean fromUser)
            {
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
                                _buzzer.setVolumeBg(i);
                            }
                        });
                    }
                }
                _volumeValue.setText(String.format(Locale.US, "%d %%", i));


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(final SeekBar seekBar)
            {
                _bgHandler.post(new BGHandler.BgRunnable()
                {

                    @Override
                    public void runBg() throws YAPI_Exception
                    {
                        _buzzer.setVolumeBg(seekBar.getProgress());
                    }
                });
            }
        });


        Switch greenSwitch = (Switch) rootView.findViewById(R.id.green_switch);
        _greenSwitch = new CustomCompoundButton(greenSwitch, _bgHandler, new CustomCompoundButton.CustomSwitchListener()
        {
            @Override
            public void onPreChangedFg(boolean isChecked)
            {

            }

            @Override
            public void onCheckedChangedBg(int id, boolean isChecked) throws YAPI_Exception
            {
                if (_ledGreen != null) {
                    _ledGreen.setPowerBg(isChecked ? YLed.POWER_ON : YLed.POWER_OFF);
                    _ledGreen.reloadBg();
                }

            }

            @Override
            public void onPostChangedDoneFg(boolean isChecked)
            {
                _greenSeekBar.setProgress(_ledGreen.getLuminosity());
            }

            @Override
            public void onErrorFg(YAPI_Exception error)
            {
                onIOError(error.getLocalizedMessage());
            }


        });

        _spinner_green = (Spinner) rootView.findViewById(R.id.spinner_green);
        _spinner_green.setOnItemSelectedListener(new CustomOnItemSelectedListener(_bgHandler, _ledGreen));
        _greenTextView = (TextView) rootView.findViewById(R.id.green);
        _greenSeekBar = (SeekBar) rootView.findViewById(R.id.green_luminosity);
        _greenSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, final int i, boolean fromUser)
            {
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
                                _ledGreen.setLuminosityBg(i);
                            }
                        });
                    }
                }
                _greenTextView.setText(String.format(Locale.US, "%d %%", i));


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(final SeekBar seekBar)
            {
                _greenSwitch.setCheckedNoNotify(seekBar.getProgress() > 0);
                _bgHandler.post(new BGHandler.BgRunnable()
                {

                    @Override
                    public void runBg() throws YAPI_Exception
                    {
                        _ledGreen.setLuminosityBg(seekBar.getProgress());
                    }
                });
            }
        });


        Switch redSwitch = (Switch) rootView.findViewById(R.id.red_switch);
        _redSwitch = new CustomCompoundButton(redSwitch, _bgHandler, new CustomCompoundButton.CustomSwitchListener()
        {
            @Override
            public void onPreChangedFg(boolean isChecked)
            {

            }

            @Override
            public void onCheckedChangedBg(int id, boolean isChecked) throws YAPI_Exception
            {
                if (_ledRed != null) {
                    _ledRed.setPowerBg(isChecked ? YLed.POWER_ON : YLed.POWER_OFF);
                    _ledRed.reloadBg();
                }
            }

            @Override
            public void onPostChangedDoneFg(boolean isChecked)
            {
                _redSeekBar.setProgress(_ledRed.getLuminosity());
            }

            @Override
            public void onErrorFg(YAPI_Exception error)
            {
                onIOError(error.getLocalizedMessage());
            }

        });
        _spinner_red = (Spinner) rootView.findViewById(R.id.spinner_red);
        _spinner_red.setOnItemSelectedListener(new CustomOnItemSelectedListener(_bgHandler, _ledRed));
        _redTextView = (TextView) rootView.findViewById(R.id.red);
        _redSeekBar = (SeekBar) rootView.findViewById(R.id.red_luminosity);
        _redSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, final int i, boolean fromUser)
            {
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
                                _ledRed.setLuminosityBg(i);
                            }
                        });
                    }
                }
                _redTextView.setText(String.format(Locale.US, "%d %%", i));


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(final SeekBar seekBar)
            {
                _redSwitch.setCheckedNoNotify(seekBar.getProgress() > 0);
                _bgHandler.post(new BGHandler.BgRunnable()
                {

                    @Override
                    public void runBg() throws YAPI_Exception
                    {
                        _ledRed.setLuminosityBg(seekBar.getProgress());
                    }
                });
            }
        });

        _isPressed1TextView = (TextView) rootView.findViewById(R.id.ispressed1);
        _progress1 = (ProgressBar) rootView.findViewById(R.id.progressBar1);
        _isPressed2TextView = (TextView) rootView.findViewById(R.id.ispressed2);
        _progress2 = (ProgressBar) rootView.findViewById(R.id.progressBar2);


    }

    @Override
    protected void updateUI(boolean firstUpdate)
    {
        super.updateUI(firstUpdate);
        if (firstUpdate) {
            _frequencySeekBar.setProgress((int) _buzzer.getFrequency());
            _volumeSeekBar.setProgress(_buzzer.getVolume());
            final int blinking = _ledGreen.getBlinking();
            Log.d(TAG, String.format("init %d", blinking));
            _spinner_green.setSelection(blinking);
            _greenSwitch.setCheckedNoNotify(_ledGreen.getPower() == YLed.POWER_ON);
            _greenSeekBar.setProgress(_ledGreen.getLuminosity());
            _redSwitch.setCheckedNoNotify(_ledRed.getPower() == YLed.POWER_ON);
            _redSeekBar.setProgress(_ledRed.getLuminosity());
            _spinner_red.setSelection(_ledRed.getBlinking());

        }

        _isPressed1TextView.setText(_anButton1.getIsPressed() == YAnButton.ISPRESSED_TRUE ? R.string.pressed : R.string.released);
        _progress1.setProgress(_anButton1.getCalibratedValue());
        _isPressed2TextView.setText(_anButton2.getIsPressed() == YAnButton.ISPRESSED_TRUE ? R.string.pressed : R.string.released);
        _progress2.setProgress(_anButton2.getCalibratedValue());
    }


    public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener
    {
        final BGHandler _bgHandler;
        final Led _led;

        CustomOnItemSelectedListener(BGHandler bgHandler, Led ledGreen)
        {

            _bgHandler = bgHandler;
            _led = ledGreen;
        }

        public void onItemSelected(AdapterView<?> parent, View view, final int pos, long id)
        {
            Log.d(TAG, String.format("Item_selected %d", pos));
            _bgHandler.post(new BGHandler.BgRunnable()
            {
                @Override
                public void runBg() throws YAPI_Exception
                {
                    int pattern;
                    switch (pos) {
                        case 0:
                            pattern = YLed.BLINKING_STILL;
                            break;
                        case 1:
                            pattern = YLed.BLINKING_RELAX;
                            break;
                        case 2:
                            pattern = YLed.BLINKING_AWARE;
                            break;
                        case 3:
                            pattern = YLed.BLINKING_RUN;
                            break;
                        case 4:
                            pattern = YLed.BLINKING_CALL;
                            break;
                        case 5:
                            pattern = YLed.BLINKING_PANIC;
                            break;
                        default:
                            pattern = YLed.BLINKING_INVALID;
                            break;
                    }
                    _led.setBlinkingBg(pattern);
                }
            });
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0)
        {
        }

    }

}
