package com.yoctopuce.yoctopucetoolbox.configure_fragments;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.yoctopucetoolbox.ModuleDetailActivity;
import com.yoctopuce.yoctopucetoolbox.ModuleListActivity;
import com.yoctopuce.yoctopucetoolbox.R;
import com.yoctopuce.yoctopucetoolbox.functions.Function;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A fragment representing a single Module detail screen.
 * This fragment is either contained in a {@link ModuleListActivity}
 * in two-pane mode (on tablets) or a {@link ModuleDetailActivity}
 * on handsets.
 */
public class ConfigureGenericModuleFragment extends ConfigureFragment
{
    // non editable views
    private TextView _serialTextView;
    private TextView _productTextView;
    private TextView _firmwareTextView;
    // editable views
    private EditText _logicalNameEditText;
    private SeekBar _luminositySeekBar;
    private TableLayout _tableLayout;
    protected final HashMap<String, EditText> _logicalNames = new HashMap<>(16);
    private final ArrayList<Function> _functions = new ArrayList<>(6);
    private String logicalnameToApply;
    private int luminosityToApply;
    private HashMap<Function, String> namesToApply;


    @Override
    protected int getLayout()
    {
        return R.layout.fragment_configure_generic_module;
    }


    protected void setupUI(View rootView)
    {

        // on some click or some loading we need to wait for...


        TextView intro = rootView.findViewById(R.id.textViewIntro);
        intro.setText(String.format("Edit parameters for device %s, and click on the Save button.", _argSerial));
        _serialTextView = rootView.findViewById(R.id.serial_number);
        _productTextView = rootView.findViewById(R.id.product_name);
        _firmwareTextView = rootView.findViewById(R.id.firmware);

        _logicalNameEditText = rootView.findViewById(R.id.logical_name);
        _tableLayout = rootView.findViewById(R.id.all_functions_rename);

        _luminositySeekBar = rootView.findViewById(R.id.luminosity);
        _luminositySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                if (_module != null) {
                    try {
                        _module.setLuminosityBg(progress);
                    } catch (YAPI_Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });
    }

    public EditText addRenameWidget(String functionId, String name)
    {


        Context context = requireContext();
        TableRow tr = new TableRow(context);
        TextView tv = new TextView(context);
        tv.setText(functionId);
        tr.addView(tv);
        EditText et = new EditText(context);
        et.setText(name);
        tr.addView(et);
        _tableLayout.addView(tr);

        return et;
    }


    @Override
    protected void reloadDataInBG(boolean firstReload) throws YAPI_Exception
    {
        super.reloadDataInBG(firstReload);
        if (firstReload) {
            int count = _module.functionCountBg();
            for (int i = 0; i < count; i++) {
                String hwid = _argSerial + "." + _module.functionIdBg(i);
                Function func = new Function(hwid);
                func.reloadBg();
                synchronized (_functions) {
                    _functions.add(func);
                }
            }

        }

    }

    @Override
    protected void prepareApply()
    {
        //todo: look for a more elegant way to cache value to set
        logicalnameToApply = _logicalNameEditText.getText().toString();
        luminosityToApply = _luminositySeekBar.getProgress();

        namesToApply = new HashMap<>(_functions.size());

        synchronized (_functions) {
            for (Function func : _functions) {
                EditText editText = _logicalNames.get(func.getHwId());
                if (editText != null) {
                    String newname = editText.getText().toString();
                    namesToApply.put(func, newname);
                }
            }
        }

    }

    @Override
    protected void executeApplyBG() throws YAPI_Exception
    {
        _module.setLogicalNameBg(logicalnameToApply);
        _module.setLuminosityBg(luminosityToApply);
        for (Function func : namesToApply.keySet()) {
            func.setLogicalNameBg(namesToApply.get(func));
        }
    }

    protected void updateUI(boolean firstUpdate)
    {
        // Show the dummy content as text in a TextView.
        if (_module != null) {
            if (firstUpdate) {

                _serialTextView.setText(_module.getSerialNumber());
                _productTextView.setText(_module.getProductName());
                _firmwareTextView.setText(_module.getFirmwareRelease());
                _logicalNameEditText.setText(_module.getLogicalName());
                _luminositySeekBar.setProgress(_module.getLuminosity());
                synchronized (_functions) {
                    for (Function func : _functions) {
                        String hwId = func.getHwId();
                        EditText editText = addRenameWidget(hwId, func.getLogicalName());
                        _logicalNames.put(hwId, editText);
                    }
                }
            }

        }

    }


}
