package com.yoctopuce.yoctopucetoolbox.details_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.yoctopucetoolbox.R;
import com.yoctopuce.yoctopucetoolbox.functions.DigitalIO;
import com.yoctopuce.yoctopucetoolbox.service.BgRunnable;
import com.yoctopuce.yoctopucetoolbox.widget.CustomCompoundButton;

/**
 * Created by seb on 22.11.2016.
 */
public class DetailYoctoMaxiIOFragment extends DetailGenericModuleFragment implements CustomCompoundButton.CustomSwitchListener
{
    private DigitalIO _digitalIO;
    private CustomCompoundButton _channel0;
    private ImageView _channel0Dir;
    private CustomCompoundButton _channel1;
    private ImageView _channel1Dir;
    private CustomCompoundButton _channel2;
    private ImageView _channel2Dir;
    private CustomCompoundButton _channel3;
    private ImageView _channel3Dir;
    private CustomCompoundButton _channel4;
    private ImageView _channel4Dir;
    private CustomCompoundButton _channel5;
    private ImageView _channel5Dir;
    private CustomCompoundButton _channel6;
    private ImageView _channel6Dir;
    private CustomCompoundButton _channel7;
    private ImageView _channel7Dir;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail_yocto_maxi_io, container, false);
        setupUI(rootView);
        return rootView;
    }

    @Override
    protected void reloadDataInBG(boolean firstReload) throws YAPI_Exception
    {
        super.reloadDataInBG(firstReload);
        _digitalIO.reloadBg();
    }

    @Override
    protected void setupUI(View rootView)
    {
        super.setupUI(rootView);
        _digitalIO = new DigitalIO(_argSerial + ".digitalIO");

        CheckBox checkBox = rootView.findViewById(R.id.channel0);
        _channel0 = new CustomCompoundButton(checkBox, this, this);
        _channel0Dir = rootView.findViewById(R.id.channel0_dir);

        checkBox = rootView.findViewById(R.id.channel1);
        _channel1 = new CustomCompoundButton(checkBox, this, this);
        _channel1Dir = rootView.findViewById(R.id.channel1_dir);

        checkBox = rootView.findViewById(R.id.channel2);
        _channel2 = new CustomCompoundButton(checkBox, this, this);
        _channel2Dir = rootView.findViewById(R.id.channel2_dir);

        checkBox = rootView.findViewById(R.id.channel3);
        _channel3 = new CustomCompoundButton(checkBox, this, this);
        _channel3Dir = rootView.findViewById(R.id.channel3_dir);

        checkBox = rootView.findViewById(R.id.channel4);
        _channel4 = new CustomCompoundButton(checkBox, this, this);
        _channel4Dir = rootView.findViewById(R.id.channel4_dir);

        checkBox = rootView.findViewById(R.id.channel5);
        _channel5 = new CustomCompoundButton(checkBox, this, this);
        _channel5Dir = rootView.findViewById(R.id.channel5_dir);

        checkBox = rootView.findViewById(R.id.channel6);
        _channel6 = new CustomCompoundButton(checkBox, this, this);
        _channel6Dir = rootView.findViewById(R.id.channel6_dir);

        checkBox = rootView.findViewById(R.id.channel7);
        _channel7 = new CustomCompoundButton(checkBox, this, this);
        _channel7Dir = rootView.findViewById(R.id.channel7_dir);
    }

    @Override
    protected void updateUI(boolean firstUpdate)
    {
        super.updateUI(firstUpdate);
        final int portDirection = _digitalIO.getPortDirection();
        boolean out = (portDirection & 1) != 0;
        _channel0Dir.setImageResource(out ? R.drawable.right_arrow : R.drawable.left_arrow);
        _channel0.setEnabled(out);
        out = (portDirection & 2) != 0;
        _channel1Dir.setImageResource(out ? R.drawable.right_arrow : R.drawable.left_arrow);
        _channel1.setEnabled(out);
        out = (portDirection & 4) != 0;
        _channel2Dir.setImageResource(out ? R.drawable.right_arrow : R.drawable.left_arrow);
        _channel2.setEnabled(out);
        out = (portDirection & 8) != 0;
        _channel3Dir.setImageResource(out ? R.drawable.right_arrow : R.drawable.left_arrow);
        _channel3.setEnabled(out);
        out = (portDirection & 16) != 0;
        _channel4Dir.setImageResource(out ? R.drawable.right_arrow : R.drawable.left_arrow);
        _channel4.setEnabled(out);
        out = (portDirection & 32) != 0;
        _channel5Dir.setImageResource(out ? R.drawable.right_arrow : R.drawable.left_arrow);
        _channel5.setEnabled(out);
        out = (portDirection & 64) != 0;
        _channel6Dir.setImageResource(out ? R.drawable.right_arrow : R.drawable.left_arrow);
        _channel6.setEnabled(out);
        out = (portDirection & 128) != 0;
        _channel7Dir.setImageResource(out ? R.drawable.right_arrow : R.drawable.left_arrow);
        _channel7.setEnabled(out);
        final int portState = _digitalIO.getPortState();
        _channel0.setCheckedNoNotify((portState & 1) != 0);
        _channel1.setCheckedNoNotify((portState & 2) != 0);
        _channel2.setCheckedNoNotify((portState & 4) != 0);
        _channel3.setCheckedNoNotify((portState & 8) != 0);
        _channel4.setCheckedNoNotify((portState & 16) != 0);
        _channel5.setCheckedNoNotify((portState & 32) != 0);
        _channel6.setCheckedNoNotify((portState & 64) != 0);
        _channel7.setCheckedNoNotify((portState & 128) != 0);
    }

    @Override
    public void onPreChangedFg(boolean isChecked)
    {

    }

    @Override
    public void onCheckedChangedBg(int id, boolean isChecked)
    {
        final int bitno;
        final int value = isChecked ? 1 : 0;
        if (id == R.id.channel0) {
            bitno = 0;
        } else if (id == R.id.channel1) {
            bitno = 1;
        } else if (id == R.id.channel2) {
            bitno = 2;
        } else if (id == R.id.channel3) {
            bitno = 3;
        } else if (id == R.id.channel4) {
            bitno = 4;
        } else if (id == R.id.channel5) {
            bitno = 5;
        } else if (id == R.id.channel6) {
            bitno = 6;
        } else if (id == R.id.channel7) {
            bitno = 7;
        } else {
            return;
        }
        postBg(new BgRunnable()
        {
            @Override
            public void runBg() throws YAPI_Exception
            {
                _digitalIO.set_bitState(bitno, value);

            }
        });
    }

    @Override
    public void onPostChangedDoneFg(boolean isChecked)
    {

    }


}
