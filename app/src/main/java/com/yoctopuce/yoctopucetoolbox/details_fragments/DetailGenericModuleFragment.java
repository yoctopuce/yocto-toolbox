package com.yoctopuce.yoctopucetoolbox.details_fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YModule;
import com.yoctopuce.yoctopucetoolbox.ModuleDetailActivity;
import com.yoctopuce.yoctopucetoolbox.ModuleListActivity;
import com.yoctopuce.yoctopucetoolbox.R;
import com.yoctopuce.yoctopucetoolbox.functions.Module;
import com.yoctopuce.yoctopucetoolbox.service.ModulesCache;
import com.yoctopuce.yoctopucetoolbox.widget.CustomCompoundButton;

import java.lang.ref.WeakReference;
import java.util.Locale;


/**
 * A fragment representing a single Module detail screen.
 * This fragment is either contained in a {@link ModuleListActivity}
 * in two-pane mode (on tablets) or a {@link ModuleDetailActivity}
 * on handsets.
 */
public class DetailGenericModuleFragment extends Fragment
{
    public static final String ARG_SERIAL = "serial";
    private static final int REFRESH_DELAY_MS = 500;
    private static final int REFRESH_UI = 0;
    private DetailGenericModuleFragment.OnYoctoErrorListener _onYoctoErrorListener;
    protected String _argSerial;
    Module _module;
    protected BGHandler _bgHandler;
    private Handler _uiHandler;
    private HandlerThread _bgHandlerThread;
    private boolean _fistUpdateUI = true;
    private TextView _serialTextView;
    private TextView _productTextView;
    private TextView _logicalNameTextView;
    private TextView _firmwareTextView;
    private TextView _consumptionTextView;
    private CustomCompoundButton _customBeaconSwitch;
    private TextView _luminosityTextView;

    //nice: make it more generic
    static class BGHandler extends Handler
    {
        private final WeakReference<DetailGenericModuleFragment> _fragment;

        BGHandler(DetailGenericModuleFragment service, Looper looper)
        {
            super(looper);
            _fragment = new WeakReference<>(service);
        }

        @Override
        public void handleMessage(Message msg)
        {
            DetailGenericModuleFragment service = _fragment.get();
            if (service != null) {
                service.HandleBgMessage(msg);
            }
        }

        interface BgRunnable
        {
            void runBg() throws YAPI_Exception;
        }

        void post(final BgRunnable bgRunnable)
        {
            post(new Runnable()
            {
                @Override
                public void run()
                {
                    try {
                        bgRunnable.runBg();
                    } catch (YAPI_Exception e) {
                        e.printStackTrace();
                        DetailGenericModuleFragment service = _fragment.get();
                        if (service != null) {
                            service.HandleBgIOError(e);
                        }
                    }
                }
            });

        }

    }

    private void HandleBgIOError(final YAPI_Exception e)
    {
        _uiHandler.post(new Runnable()
        {
            @Override
            public void run()
            {
                onIOError(e.getLocalizedMessage());
            }
        });
    }


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
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(_module.getProductName());
            }
        }
        _bgHandlerThread = new HandlerThread("DetailHandlerThread");
        _bgHandlerThread.start();
        _uiHandler = new UIHandler(this);
        _bgHandler = new BGHandler(this, _bgHandlerThread.getLooper());

    }

    /**
     * Instances of static inner classes do not hold an implicit
     * reference to their outer class.
     */
    private static class UIHandler extends Handler
    {
        private final WeakReference<DetailGenericModuleFragment> _fragmentWeakReference;

        public UIHandler(DetailGenericModuleFragment activity)
        {
            _fragmentWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg)
        {
            DetailGenericModuleFragment fragment = _fragmentWeakReference.get();
            if (fragment != null) {
                fragment.handleUIMessage(msg);
            }
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.fragment_details_generic, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.show_doc:
                String base_serial = _argSerial.substring(0, FragmentChooser.YOCTO_BASE_SERIAL_LEN);
                String url = getString(R.string.www_yoctopuce_com_dev_doc_url) + base_serial;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
        _bgHandlerThread.quit();
        _bgHandlerThread.interrupt();
    }


    @Override
    public void onResume()
    {
        super.onResume();
        triggerUIRefresh(0);
        // Schedule the first execution
    }

    @Override
    public void onPause()
    {
        _bgHandler.removeCallbacksAndMessages(null);
        super.onPause();
    }


    // Container Activity must implement this interface
    public interface OnYoctoErrorListener
    {
        void onYoctoError(String sender, String errmsg);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try {
            _onYoctoErrorListener = (DetailGenericModuleFragment.OnYoctoErrorListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnYoctoErrorListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_detail_generic_module, container, false);
        setupUI(rootView);
        return rootView;
    }


    protected void handleUIMessage(Message msg)
    {
        switch (msg.what) {
            case REFRESH_UI:
                updateUI(_fistUpdateUI);
                _fistUpdateUI = false;
                triggerUIRefresh(REFRESH_DELAY_MS);
                break;
        }
    }

    protected void onIOError(String errmsg)
    {
        Context activity = getActivity();
        // test if fragment is detached from activity
        if (activity != null) {
            _onYoctoErrorListener.onYoctoError(_argSerial, errmsg);
        }
    }


    public abstract class BgSwitchListener implements CustomCompoundButton.CustomSwitchListener
    {
        @Override
        public void onPreChangedFg(boolean isChecked)
        {

        }

        @Override
        public void onPostChangedDoneFg(boolean isChecked)
        {

        }

        @Override
        public void onErrorFg(YAPI_Exception error)
        {
            onIOError(error.getLocalizedMessage());
        }
    }


    protected boolean triggerUIRefresh(int delayMs)
    {
        return _bgHandler.sendEmptyMessageDelayed(REFRESH_UI, delayMs);
    }

    protected void HandleBgMessage(Message msg)
    {
        if (msg.what == REFRESH_UI) {
            try {
                reloadDataInBG();
                _uiHandler.sendEmptyMessage(REFRESH_UI);
            } catch (final YAPI_Exception e) {
                _uiHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        onIOError(e.getLocalizedMessage());
                    }
                });
            }
        }
    }


    protected void reloadDataInBG() throws YAPI_Exception
    {
        _module.reloadBg();
    }


    protected void setupUI(View rootView)
    {
        _serialTextView = (TextView) rootView.findViewById(R.id.serial_number);
        _productTextView = (TextView) rootView.findViewById(R.id.product_name);
        _logicalNameTextView = (TextView) rootView.findViewById(R.id.logical_name);
        _firmwareTextView = (TextView) rootView.findViewById(R.id.firmware);
        _consumptionTextView = (TextView) rootView.findViewById(R.id.consumption);
        Switch beaconSwitch = (Switch) rootView.findViewById(R.id.beacon_switch);
        _customBeaconSwitch = new CustomCompoundButton(beaconSwitch, _bgHandler, new BgSwitchListener()
        {
            public void onCheckedChangedBg(int id, boolean isChecked) throws YAPI_Exception
            {
                if (_module != null) {
                    _module.setBeaconBg(isChecked ? YModule.BEACON_ON : YModule.BEACON_OFF);
                }
            }
        });

        _luminosityTextView = (TextView) rootView.findViewById(R.id.luminosity);
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
            _consumptionTextView.setText(String.format(Locale.US, "%d mA", _module.getUsbCurrent()));
            boolean checked = _module.getBeacon() == YModule.BEACON_ON;
            _customBeaconSwitch.setCheckedNoNotify(checked);
            _luminosityTextView.setText(String.format(Locale.US, "%d%%", _module.getLuminosity()));
        }
    }


}
