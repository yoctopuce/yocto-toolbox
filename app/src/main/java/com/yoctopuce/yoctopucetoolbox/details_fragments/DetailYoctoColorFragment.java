package com.yoctopuce.yoctopucetoolbox.details_fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.yoctopucetoolbox.R;
import com.yoctopuce.yoctopucetoolbox.functions.ColorLedCluster;

/**
 * Created by seb on 22.11.2016.
 */
public class DetailYoctoColorFragment extends DetailGenericModuleFragment
{

    private ColorLedCluster _colorLedCluster;
    private GridLayout _gridLayout;
    private int _activeLedCount = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail_yocto_color, container, false);
        setupUI(rootView);
        return rootView;
    }

    @Override
    protected void reloadDataInBG(boolean firstReload) throws YAPI_Exception
    {
        super.reloadDataInBG(firstReload);
        _colorLedCluster.reloadBg();
    }

    @Override
    protected void setupUI(View rootView)
    {
        super.setupUI(rootView);
        _colorLedCluster = new ColorLedCluster(_argSerial + ".colorLedCluster");
        _gridLayout = rootView.findViewById(R.id.leds_grid_layout);
    }

    @Override
    protected void updateUI(boolean firstUpdate)
    {
        super.updateUI(firstUpdate);

        final int ledCount = _colorLedCluster.getActiveLedCount();
        if (ledCount < _activeLedCount) {
            _gridLayout.removeAllViews();
            _activeLedCount = 0;
        }
        if (ledCount > _activeLedCount) {
            final Context context = getContext();
            for (int i = _activeLedCount; i < ledCount; i++) {
                final Button button = new Button(context);
                _gridLayout.addView(button);
            }
            _activeLedCount = ledCount;
        }
    }
}
