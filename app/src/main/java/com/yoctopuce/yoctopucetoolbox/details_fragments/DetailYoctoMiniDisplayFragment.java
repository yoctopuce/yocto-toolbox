package com.yoctopuce.yoctopucetoolbox.details_fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YDisplayLayer;
import com.yoctopuce.yoctopucetoolbox.R;
import com.yoctopuce.yoctopucetoolbox.functions.Display;

/**
 * Created by Yoctopuce on 18.01.2017.
 */
public class DetailYoctoMiniDisplayFragment extends DetailGenericModuleFragment implements TextWatcher
{
    private Display _display;
    private ImageView _image;
    private boolean _firsttime;
    private byte[] _gifByte;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail_yocto_minidisplay, container, false);
        setupUI(rootView);
        return rootView;
    }

    @Override
    protected void reloadDataInBG() throws YAPI_Exception
    {
        super.reloadDataInBG();
        _display.reloadBg();
        _gifByte = _module.download("display.gif");

    }

    @Override
    protected void setupUI(View rootView)
    {
        super.setupUI(rootView);
        _firsttime = true;
        _display = new Display(_argSerial + ".display");
        EditText textInput = (EditText) rootView.findViewById(R.id.edit_text);
        String original_msg = getString(R.string.hello);
        new UpdateYoctoDisplayTask().execute(original_msg);
        textInput.setText(original_msg);
        textInput.addTextChangedListener(this);
        _image = (ImageView) rootView.findViewById(R.id.capture);

    }

    @Override
    protected void updateUI(boolean firstUpdate)
    {
        super.updateUI(firstUpdate);
        if (_gifByte != null) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inMutable = true;
            Bitmap bmp = BitmapFactory.decodeByteArray(_gifByte, 0, _gifByte.length, options);
            _image.setImageBitmap(bmp);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
    {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
    {

    }

    @Override
    public void afterTextChanged(Editable editable)
    {
        new UpdateYoctoDisplayTask().execute(editable.toString());
    }

    private class UpdateYoctoDisplayTask extends AsyncTask<String, Integer, String>
    {
        protected String doInBackground(String... texts)
        {
            try {
                _display.reloadBg();
                YDisplayLayer l1 = _display.get_displayLayer(1);
                int w = _display.getDisplayWidth();
                int h = _display.getDisplayHeight();
                if (_firsttime) {
                    YDisplayLayer l0 = _display.get_displayLayer(0);
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
                    _firsttime = false;
                }
                for (String text : texts) {
                    l1.clear();
                    l1.drawText(w / 2, h / 2, YDisplayLayer.ALIGN.CENTER, text);
                    _display.swapLayerContent(1, 2);
                }
            } catch (YAPI_Exception e) {
                e.printStackTrace();
                return e.getLocalizedMessage();
            }
            return null;
        }

        protected void onPostExecute(String result)
        {
        }
    }
}
