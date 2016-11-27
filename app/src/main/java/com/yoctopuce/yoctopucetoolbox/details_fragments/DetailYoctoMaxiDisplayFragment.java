package com.yoctopuce.yoctopucetoolbox.details_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YAnButton;
import com.yoctopuce.yoctopucetoolbox.R;
import com.yoctopuce.yoctopucetoolbox.functions.AnButton;
import com.yoctopuce.yoctopucetoolbox.functions.Display;

/**
 * Created by seb on 22.11.2016.
 */
public class DetailYoctoMaxiDisplayFragment extends DetailGenericModuleFragment
{

    private AnButton _anButton1;
    private AnButton _anButton2;
    private AnButton _anButton3;
    private AnButton _anButton4;
    private AnButton _anButton5;
    private ProgressBar _progress1;
    private ProgressBar _progress2;
    private ProgressBar _progress3;
    private ProgressBar _progress4;
    private ProgressBar _progress5;
    private TextView _isPressed5TextView;
    private TextView _isPressed4TextView;
    private TextView _isPressed3TextView;
    private TextView _isPressed2TextView;
    private TextView _isPressed1TextView;
    private Display _display;
    private EditText _textInput;
    private ImageView _image;
    private boolean _firsttime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail_yocto_maxidisplay, container, false);
        setupUI(rootView);
        return rootView;
    }

    @Override
    protected void reloadDataInBG() throws YAPI_Exception
    {
        super.reloadDataInBG();
        _display.reloadBg();
        _anButton1.reloadBg();
        _anButton2.reloadBg();
        _anButton3.reloadBg();
        _anButton4.reloadBg();
        _anButton5.reloadBg();
    }

    @Override
    protected void setupUI(View rootView)
    {
        super.setupUI(rootView);
        _display = new Display(_serial + ".anButton1");
        _anButton1 = new AnButton(_serial + ".anButton1");
        _anButton2 = new AnButton(_serial + ".anButton2");
        _anButton3 = new AnButton(_serial + ".anButton3");
        _anButton4 = new AnButton(_serial + ".anButton4");
        _anButton5 = new AnButton(_serial + ".anButton5");

        _isPressed1TextView = (TextView) rootView.findViewById(R.id.ispressed1);
        _progress1 = (ProgressBar) rootView.findViewById(R.id.progressBar1);
        _isPressed2TextView = (TextView) rootView.findViewById(R.id.ispressed2);
        _progress2 = (ProgressBar) rootView.findViewById(R.id.progressBar2);
        _isPressed3TextView = (TextView) rootView.findViewById(R.id.ispressed3);
        _progress3 = (ProgressBar) rootView.findViewById(R.id.progressBar3);
        _isPressed4TextView = (TextView) rootView.findViewById(R.id.ispressed4);
        _progress4 = (ProgressBar) rootView.findViewById(R.id.progressBar4);
        _isPressed5TextView = (TextView) rootView.findViewById(R.id.ispressed5);
        _progress5 = (ProgressBar) rootView.findViewById(R.id.progressBar5);
        _textInput = (EditText) rootView.findViewById(R.id.edit_text);
        _image = (ImageView) rootView.findViewById(R.id.capture);
    }

    @Override
    protected void updateUI()
    {
        super.updateUI();

        _isPressed1TextView.setText(_anButton1.getIsPressed() == YAnButton.ISPRESSED_TRUE ? R.string.pressed:R.string.released);
        _progress1.setProgress(_anButton1.getCalibratedValue());
        _isPressed2TextView.setText(_anButton2.getIsPressed() == YAnButton.ISPRESSED_TRUE ? R.string.pressed:R.string.released);
        _progress2.setProgress(_anButton2.getCalibratedValue());
        _isPressed3TextView.setText(_anButton3.getIsPressed() == YAnButton.ISPRESSED_TRUE ? R.string.pressed:R.string.released);
        _progress3.setProgress(_anButton3.getCalibratedValue());
        _isPressed4TextView.setText(_anButton4.getIsPressed() == YAnButton.ISPRESSED_TRUE ? R.string.pressed:R.string.released);
        _progress4.setProgress(_anButton4.getCalibratedValue());
        _isPressed5TextView.setText(_anButton5.getIsPressed() == YAnButton.ISPRESSED_TRUE ? R.string.pressed:R.string.released);
        _progress5.setProgress(_anButton5.getCalibratedValue());

        //todo: add support for DisplayLayer

        /*

        int w;
        int h;
        if (_firsttime) {
            w = _display.getDisplayWidth();
            h = _display.getDisplayHeight();
            l0 = _display.getDisplayLayer(0);
            l1 = _display.getDisplayLayer(1);
            _display.resetAll();
            l0.hide();
            l0.moveTo(0, 5);
            l0.lineTo(0, 0);
            l0.lineTo(5, 0);
            l0.moveTo(0, h - 6);
            l0.lineTo(0, h - 1);
            l0.lineTo(5, h - 1);
            l0.moveTo(w - 1, h - 6);
            l0.lineTo(w - 1, h - 1);
            l0.lineTo(w - 6, h - 1);
            l0.moveTo(w - 1, 5);
            l0.lineTo(w - 1, 0);
            l0.lineTo(w - 6, 0);
            l0.unhide();
            l1.selectFont("Medium.yfm");
            l1.hide();
            setTimeout('refreshCapture()', 300);
            _firsttime = false;
        }

        var txt = document.getElementById('txt').value;
        if (txt != lasttext) {
            l1.clear();
            l1.drawText(w / 2, h / 2, Y_ALIGN_CENTER, txt);
            disp.swapLayerContent(1, 2);
            lasttext = txt;
            setTimeout('refreshCapture()', 100);
        }
        */
    }
}
