package com.yoctopuce.yoctopucetoolbox;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.yoctopuce.yoctopucetoolbox.hub.HubStorage;
import com.yoctopuce.yoctopucetoolbox.misc.AboutDialog;

import java.util.UUID;

public class HubDetailActivity extends AppCompatActivity
{

    //nice: merge fragment into the activity
    private static final String ARG_HUB_UUID = "HUB_UUID";
    private UUID _hubUUID;

    public static Intent intentWithParams(Context context, UUID hubUUID)
    {
        Intent intent = new Intent(context, HubDetailActivity.class);
        intent.putExtra(ARG_HUB_UUID, hubUUID.toString());
        return intent;
    }

    public static Intent intentWithParams(Context context)
    {
        return new Intent(context, HubDetailActivity.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
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

            Fragment fragment;

            Intent intent = getIntent();
            String uuid_str = intent.getStringExtra(ARG_HUB_UUID);

            if (uuid_str != null) {
                _hubUUID = UUID.fromString(uuid_str);
                 fragment = HubDetailActivityFragment.getFragment(_hubUUID);
            } else {
                fragment = HubDetailActivityFragment.getFragment();
            }
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.hub_detail_container, fragment)
                    .commit();
        }
    }

}
