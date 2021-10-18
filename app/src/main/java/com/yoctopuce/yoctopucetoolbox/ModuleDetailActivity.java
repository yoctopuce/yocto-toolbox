package com.yoctopuce.yoctopucetoolbox;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.yoctopucetoolbox.details_fragments.DetailGenericModuleFragment;
import com.yoctopuce.yoctopucetoolbox.details_fragments.FragmentChooser;
import com.yoctopuce.yoctopucetoolbox.service.FragementNavigactionCallbacks;
import com.yoctopuce.yoctopucetoolbox.service.UseHubActivity;

import java.util.UUID;

/**
 * An activity representing a single Module detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ModuleListActivity}.
 */
public class ModuleDetailActivity extends UseHubActivity implements FragementNavigactionCallbacks
{

    private RelativeLayout _fatalErrorView;
    private TextView _fatalErrorTextView;
    private Button _closeButton;

    public static Intent intentWithParams(Context context, UUID hubid, String serialNumber)
    {
        Intent intent = new Intent(context, ModuleDetailActivity.class);
        intent.putExtra(DetailGenericModuleFragment.ARG_SERIAL, serialNumber);
        intent.putExtra(HUB_UUID, hubid.toString());
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_detail);
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // get reference on Error panel
        _fatalErrorView = findViewById(R.id.fatal_pannel);
        _fatalErrorTextView = findViewById(R.id.fatal_error_msg);
        _closeButton = findViewById(R.id.close_button);


        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.

            final Intent intent = getIntent();
            String serial = intent.getStringExtra(DetailGenericModuleFragment.ARG_SERIAL);
            Fragment fragment = FragmentChooser.GetFragment(serial);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.module_detail_container, fragment)
                    .setReorderingAllowed(true)
                    .addToBackStack("detail")
                    .commit();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            this.back();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    protected void displayErrorPanel(String localizedMessage)
    {
        _fatalErrorTextView.setText(String.format(getString(R.string.device_disconnected_s), localizedMessage));
        _closeButton.setOnClickListener(view -> finish());
        _fatalErrorView.setVisibility(View.VISIBLE);
    }


    @Override
    public void onBGError(final YAPI_Exception e)
    {
        e.printStackTrace();
        postUI(() -> displayErrorPanel(e.getLocalizedMessage())

        );
    }

    @Override
    public void back()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            NavUtils.navigateUpTo(this, ModuleListActivity.intentWithParams(this, _hub.getUuid()));
        }
    }


    @Override
    public void goToDocumentation(String serial)
    {
        String url = getString(R.string.www_yoctopuce_com_dev_doc_url) + serial;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }


    @Override
    public void goToConfiguration(String serial)
    {
        Fragment fragment = FragmentChooser.GetConfigureFragment(serial);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.module_detail_container, fragment)
                .setReorderingAllowed(true)
                .addToBackStack("config")
                .commit();

    }
}
