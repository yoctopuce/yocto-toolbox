package com.yoctopuce.yoctopucetoolbox.details_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.yoctopucetoolbox.R;
import com.yoctopuce.yoctopucetoolbox.functions.Voc;

import java.util.Locale;

/**
 * Created by seb on 22.11.2016.
 */
public class DetailYoctoVOCFragment extends DetailGenericModuleFragment
{
    private Voc _voc;
    private TextView _minTextView;
    private TextView _currentTextView;
    private TextView _maxTextView;
    private View _vocInfo;
    private View _preheatInfo;
    private TextView _preheatTime;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail_yocto_voc, container, false);
        setupUI(rootView);
        return rootView;
    }


    @Override
    protected void reloadDataInBG() throws YAPI_Exception
    {
        super.reloadDataInBG();
        _voc.reloadBg();
    }

    @Override
    protected void setupUI(View rootView)
    {
        super.setupUI(rootView);
        _voc = new Voc(_argSerial + ".voc");
        _currentTextView = (TextView) rootView.findViewById(R.id.current_value);
        _maxTextView = (TextView) rootView.findViewById(R.id.max_value);
        _minTextView = (TextView) rootView.findViewById(R.id.min_value);
        _vocInfo = rootView.findViewById(R.id.voc_info);
        _preheatInfo = rootView.findViewById(R.id.preheat_info);
        _preheatTime = (TextView) rootView.findViewById(R.id.preheat_time);
    }


    @Override
    protected void updateUI(boolean firstUpdate)
    {
        super.updateUI(firstUpdate);

        long upTime = _module.getUpTime() / 1000;
        Locale locale = Locale.US;
        if (upTime >= 900) {
            String unit = _voc.getUnit();
            _currentTextView.setText(String.format(locale, "%s %s", Double.toString(_voc.getCurrentValue()), unit));
            _maxTextView.setText(String.format(locale, "%s %s", Double.toString(_voc.getHighestValue()), unit));
            _minTextView.setText(String.format(locale, "%s %s", Double.toString(_voc.getLowestValue()), unit));
            _preheatInfo.setVisibility(View.GONE);
            _vocInfo.setVisibility(View.VISIBLE);
        } else {
            long sec = 900 - upTime;
            long m = sec / 60;
            sec = sec % 60;
            String text;
            if (m > 0) {
                text = String.format(locale, "( %dm %ds )", m, sec);
            } else {
                text = String.format(locale, "( %ds )", sec);
            }
            _preheatTime.setText(text);
            _preheatInfo.setVisibility(View.VISIBLE);
            _vocInfo.setVisibility(View.GONE);
        }
    }

}
