package com.yoctopuce.yoctopucetoolbox.configure_fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.yoctopuce.YoctoAPI.YAPI;
import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.yoctopucetoolbox.ModuleDetailActivity;
import com.yoctopuce.yoctopucetoolbox.ModuleListActivity;
import com.yoctopuce.yoctopucetoolbox.R;
import com.yoctopuce.yoctopucetoolbox.functions.Module;
import com.yoctopuce.yoctopucetoolbox.service.ModulesCache;
import com.yoctopuce.yoctopucetoolbox.service.UseHubFragment;


/**
 * A fragment representing a single Module detail screen.
 * This fragment is either contained in a {@link ModuleListActivity}
 * in two-pane mode (on tablets) or a {@link ModuleDetailActivity}
 * on handsets.
 */
public abstract class ConfigureFragment extends UseHubFragment
{
    public static final String ARG_SERIAL = "serial";
    private static final int REFRESH_DELAY_MS = 500;
    protected String _argSerial;
    Module _module;
    private boolean _fistUpdateUI = true;
    private ProgressBar _loadingBar;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            _argSerial = getArguments().getString(ConfigureFragment.ARG_SERIAL);
            ModulesCache modulesCache = ModulesCache.getInstance();
            _module = modulesCache.getFromSerial(_argSerial);
            Activity activity = this.getActivity();
            assert activity != null;
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(_module.getProductName());
            }
        }
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.fragment_configure_generic, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle item selection
        int id = item.getItemId();
        if (id == R.id.save_menu) {
            applyChangeEx(true, () -> _callbacks.back());
            return true;
        } else if (id == R.id.show_doc) {
            _callbacks.goToDocumentation(_argSerial.substring(0, YAPI.YOCTO_BASE_SERIAL_LEN));
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
        View rootView = inflater.inflate(getLayout(), container, false);
        _loadingBar = (ProgressBar) rootView.findViewById(R.id.loading);
        if (_loadingBar != null) {
            _loadingBar.setVisibility(ProgressBar.VISIBLE);
        }
        TextView intro = rootView.findViewById(R.id.textViewIntro);
        intro.setText(String.format("Edit parameters for device %s, and click on the Save button.", _argSerial));
        setupUI(rootView);
        _fistUpdateUI = true;
        return rootView;
    }


    protected void triggerUIRefresh(long delayMs)
    {
        postDelayedBg(() -> {
            reloadDataInBG(_fistUpdateUI);
            postUI(() -> {
                updateUI(_fistUpdateUI);
                if (_fistUpdateUI && _loadingBar != null) {
                    _loadingBar.setVisibility(ProgressBar.INVISIBLE);
                }
                _fistUpdateUI = false;
                triggerUIRefresh(REFRESH_DELAY_MS);
            });
        }, delayMs);
    }


    protected void reloadDataInBG(boolean firstReload) throws YAPI_Exception
    {
        _module.reloadBg();
    }


    @SuppressWarnings("SameParameterValue")
    protected void applyChangeEx(boolean saveOnDevice, Runnable endCB)
    {

        this.prepareApply();
        postBg(() -> {
            executeApplyBG();
            if (saveOnDevice) {
                _module.saveToFlash();
            }
            postUI(endCB);
        });
    }

    protected abstract void prepareApply();

    protected abstract void executeApplyBG() throws YAPI_Exception;

    protected abstract int getLayout();

    protected abstract void setupUI(View rootView);

    protected abstract void updateUI(boolean firstUpdate);

}
