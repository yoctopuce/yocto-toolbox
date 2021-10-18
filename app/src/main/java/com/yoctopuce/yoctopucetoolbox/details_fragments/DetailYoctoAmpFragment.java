package com.yoctopuce.yoctopucetoolbox.details_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.yoctopucetoolbox.R;
import com.yoctopuce.yoctopucetoolbox.functions.Current;

public class DetailYoctoAmpFragment extends DetailGenericModuleFragment
{
    private TextView _curACTextView;
    private TextView _minACTextView;
    private Current _currentDC;
    private Current _currentAC;
    private TextView _maxACTextView;
    private TextView _curDCTextView;
    private TextView _minDCTextView;
    private TextView _maxDCTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail_yocto_amp, container, false);
        setupUI(rootView);
        return rootView;
    }

    @Override
    protected void reloadDataInBG(boolean firstReload) throws YAPI_Exception
    {
        super.reloadDataInBG(firstReload);
        _currentDC.reloadBg();
        _currentAC.reloadBg();
    }

    @Override
    protected void setupUI(View rootView)
    {
        super.setupUI(rootView);
        _currentDC = new Current(_argSerial + ".current1");
        _currentAC = new Current(_argSerial + ".current2");
        _curDCTextView = rootView.findViewById(R.id.current_value_dc);
        _curACTextView = rootView.findViewById(R.id.current_value_ac);
        _minDCTextView = rootView.findViewById(R.id.min_value_dc);
        _minACTextView = rootView.findViewById(R.id.min_value_ac);
        _maxDCTextView = rootView.findViewById(R.id.max_value_dc);
        _maxACTextView = rootView.findViewById(R.id.max_value_ac);
    }

    @Override
    protected void updateUI(boolean firstUpdate)
    {
        super.updateUI(firstUpdate);
        _curDCTextView.setText(getString(R.string.float_value_and_unit, _currentDC.getCurrentValue(), _currentDC.getUnit()));
        _curACTextView.setText(getString(R.string.float_value_and_unit, _currentAC.getHighestValue(), _currentAC.getUnit()));
        _minDCTextView.setText(getString(R.string.float_value_and_unit, _currentDC.getLowestValue(), _currentDC.getUnit()));
        _minACTextView.setText(getString(R.string.float_value_and_unit, _currentAC.getCurrentValue(), _currentAC.getUnit()));
        _maxDCTextView.setText(getString(R.string.float_value_and_unit, _currentDC.getHighestValue(), _currentDC.getUnit()));
        _maxACTextView.setText(getString(R.string.float_value_and_unit, _currentAC.getLowestValue(), _currentAC.getUnit()));
    }

}
