package com.yoctopuce.yoctopucetoolbox.details_fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;
import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.yoctopucetoolbox.R;
import com.yoctopuce.yoctopucetoolbox.functions.ColorLedCluster;

import java.util.ArrayList;


public class DetailYoctoColorV2Fragment extends DetailGenericModuleFragment
{

    private ColorLedCluster _colorLedCluster;
    private GridLayout _gridLayout;
    private int _activeLedCount = 0;
    private final Object _lock = new Object();
    private ArrayList<Integer> _rgbColorArray;
    private final ArrayList<ButtonHolder> _buttons = new ArrayList<>();

    private static class ButtonHolder
    {
        private int _rgbColor;
        private final Button _button;
        private final int _ledIndex;

        ButtonHolder(int i, Button button)
        {
            _ledIndex = i;
            _rgbColor = -1;
            this._button = button;
        }

        void setColor(int color)
        {
            color |= 0xff000000;
            if (_rgbColor != color) {
                _rgbColor = color;
                Drawable background = _button.getBackground();
                //drawable.setColor(color);
                if (background instanceof ShapeDrawable) {
                    // cast to 'ShapeDrawable'
                    ShapeDrawable shapeDrawable = (ShapeDrawable) background;
                    shapeDrawable.getPaint().setColor(color);
                } else if (background instanceof GradientDrawable) {
                    // cast to 'GradientDrawable'
                    GradientDrawable gradientDrawable = (GradientDrawable) background;
                    gradientDrawable.setColor(color);
                } else if (background instanceof ColorDrawable) {
                    // alpha value may need to be set again after this call
                    ColorDrawable colorDrawable = (ColorDrawable) background;
                    colorDrawable.setColor(color);
                }
            }
        }


        int getLedIndex()
        {
            return _ledIndex;
        }


    }


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
        final ArrayList<Integer> rgbColorArray = _colorLedCluster.get_rgbColorArray(0, _colorLedCluster.getActiveLedCount());
        synchronized (_lock) {
            _rgbColorArray = rgbColorArray;
        }
    }

    @Override
    protected void setupUI(View rootView)
    {
        super.setupUI(rootView);
        _colorLedCluster = new ColorLedCluster(_argSerial + ".colorLedCluster");
        _gridLayout = rootView.findViewById(R.id.leds_grid_layout);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
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
                final ButtonHolder buttonHolder = new ButtonHolder(i, button);
                final Drawable drawable;
                if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    drawable = requireActivity().getDrawable(R.drawable.color_button);
                } else {
                    drawable = getResources().getDrawable(R.drawable.color_button);
                }
                button.setBackground(drawable);
                buttonHolder.setColor(0);
                button.setOnClickListener(view -> {
                    // todo: initialize dialog with current color
                    final int ledIndex = buttonHolder.getLedIndex();
                    new ColorPickerDialog.Builder(requireContext())
                            .setTitle("Select a color")
                            .setPreferenceName("MyColorPickerDialog")
                            .setPositiveButton("Select",
                                    (ColorEnvelopeListener) (envelope, fromUser) -> {
                                        int rgbValue = envelope.getColor();
                                        postBg(() -> _colorLedCluster.set_rgbColor(ledIndex, 1, rgbValue));
                                    })
                            .setNegativeButton(getString(R.string.cancel),
                                    (dialogInterface, i1) -> dialogInterface.dismiss())
                            .attachAlphaSlideBar(false) // the default value is true.
                            .attachBrightnessSlideBar(true)  // the default value is true.

                            .setBottomSpace(12)// set a bottom space between the last slide bar and buttons.
                            .show();
                });

                _buttons.add(buttonHolder);
                _gridLayout.addView(button);
            }
            _activeLedCount = ledCount;
        }
        final Integer[] rgbColors = new Integer[_activeLedCount];
        synchronized (_lock) {
            _rgbColorArray.toArray(rgbColors);
        }
        for (int i = 0; i < rgbColors.length; i++) {
            final ButtonHolder buttonHolder = _buttons.get(i);
            final Integer rgbColor = rgbColors[i];
            buttonHolder.setColor(rgbColor);
        }

    }
}
