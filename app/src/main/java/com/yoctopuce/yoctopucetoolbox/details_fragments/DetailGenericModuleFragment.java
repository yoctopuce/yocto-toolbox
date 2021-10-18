package com.yoctopuce.yoctopucetoolbox.details_fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;

import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YModule;
import com.yoctopuce.yoctopucetoolbox.ModuleDetailActivity;
import com.yoctopuce.yoctopucetoolbox.ModuleListActivity;
import com.yoctopuce.yoctopucetoolbox.R;
import com.yoctopuce.yoctopucetoolbox.functions.Module;
import com.yoctopuce.yoctopucetoolbox.service.BgRunnable;
import com.yoctopuce.yoctopucetoolbox.service.ModulesCache;
import com.yoctopuce.yoctopucetoolbox.service.UseHubFragment;
import com.yoctopuce.yoctopucetoolbox.widget.CustomCompoundButton;

import java.util.Locale;


/**
 * A fragment representing a single Module detail screen.
 * This fragment is either contained in a {@link ModuleListActivity}
 * in two-pane mode (on tablets) or a {@link ModuleDetailActivity}
 * on handsets.
 */
public class DetailGenericModuleFragment extends UseHubFragment
{
    public static final String ARG_SERIAL = "serial";
    private static final int REFRESH_DELAY_MS = 500;
    protected String _argSerial;
    Module _module;
    private boolean _fistUpdateUI = true;
    private TextView _serialTextView;
    private TextView _productTextView;
    private TextView _logicalNameTextView;
    private TextView _firmwareTextView;
    private TextView _consumptionTextView;
    private CustomCompoundButton _customBeaconSwitch;
    private TextView _luminosityTextView;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            _argSerial = getArguments().getString(DetailGenericModuleFragment.ARG_SERIAL);
            ModulesCache modulesCache = ModulesCache.getInstance();
            _module = modulesCache.getFromSerial(_argSerial);
            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(_module.getProductName());
            }
        }
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.fragment_details_generic, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle item selection
        int id = item.getItemId();
        if (id == R.id.configure) {
            _callbacks.goToConfiguration(_argSerial);
            return true;
        } else if (id == R.id.show_doc) {
            _callbacks.goToDocumentation(_argSerial);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onResume()
    {
        super.onResume();
        triggerUIRefresh(0);
        // Schedule the first execution
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_detail_generic_module, container, false);
        setupUI(rootView);
        return rootView;
    }

    public abstract static class BgSwitchListener implements CustomCompoundButton.CustomSwitchListener
    {
        @Override
        public void onPreChangedFg(boolean isChecked)
        {

        }

        @Override
        public void onPostChangedDoneFg(boolean isChecked)
        {

        }

    }


    protected void triggerUIRefresh(long delayMs)
    {
        postDelayedBg(new BgRunnable()
        {
            @Override
            public void runBg() throws YAPI_Exception
            {
                reloadDataInBG(_fistUpdateUI);
                postUI(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (getContext() == null) {
                            // fragment is not attached to an activity skip the call
                            return;
                        }
                        updateUI(_fistUpdateUI);
                        _fistUpdateUI = false;
                        triggerUIRefresh(REFRESH_DELAY_MS);
                    }
                });
            }
        }, delayMs);
    }


    protected void reloadDataInBG(boolean firstReload) throws YAPI_Exception
    {
        _module.reloadBg();
    }


    protected void setupUI(View rootView)
    {
        _serialTextView = rootView.findViewById(R.id.serial_number);
        _productTextView = rootView.findViewById(R.id.product_name);
        _logicalNameTextView = rootView.findViewById(R.id.logical_name);
        _firmwareTextView = rootView.findViewById(R.id.firmware);
        _consumptionTextView = rootView.findViewById(R.id.consumption);
        SwitchCompat beaconSwitch = rootView.findViewById(R.id.beacon_switch);
        _customBeaconSwitch = new CustomCompoundButton(beaconSwitch, this, new BgSwitchListener()
        {
            @Override
            public void onPreChangedFg(boolean isChecked)
            {

            }

            @Override
            public void onPostChangedDoneFg(boolean isChecked)
            {

            }

            public void onCheckedChangedBg(int id, boolean isChecked) throws YAPI_Exception
            {
                if (_module != null) {
                    _module.setBeaconBg(isChecked ? YModule.BEACON_ON : YModule.BEACON_OFF);
                }
            }
        });

        _luminosityTextView = rootView.findViewById(R.id.luminosity);
        _fistUpdateUI = true;
    }


    protected void updateUI(boolean firstUpdate)
    {
        // Show the dummy content as text in a TextView.
        if (_module != null) {
            if (firstUpdate) {
                _serialTextView.setText(_module.getSerialNumber());
                _productTextView.setText(_module.getProductName());
            }
            _logicalNameTextView.setText(_module.getLogicalName());
            _firmwareTextView.setText(_module.getFirmwareRelease());
            _consumptionTextView.setText(getString(R.string.int_value_and_unit, _module.getUsbCurrent(), "mA"));
            boolean checked = _module.getBeacon() == YModule.BEACON_ON;
            _customBeaconSwitch.setCheckedNoNotify(checked);
            _luminosityTextView.setText(String.format(Locale.US, "%d%%", _module.getLuminosity()));
        }
    }


}
