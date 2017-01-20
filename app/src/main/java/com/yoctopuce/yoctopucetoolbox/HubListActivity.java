package com.yoctopuce.yoctopucetoolbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.yoctopuce.yoctopucetoolbox.service.UseHubActivity;

public class HubListActivity extends ActivityWithMenu
{

    private static final int MODULE_LIST_REQUEST = 1;
    private static final int NEW_HUB_REQUEST = 2;
    private FloatingActionButton _fab;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        _fab = (FloatingActionButton) findViewById(R.id.fab);
        assert _fab != null;
        _fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // In single-pane mode, simply start the detail activity
                // for the selected item ID.
                Intent detailIntent = new Intent(HubListActivity.this, HubDiscoveryActivity.class);
                startActivity(detailIntent);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // Check which request we're responding to
        if (requestCode == MODULE_LIST_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_CANCELED && data != null) {
                String errmsg = data.getStringExtra(UseHubActivity.ERRMSG);
                Snackbar.make(_fab, errmsg, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        } else if (requestCode == NEW_HUB_REQUEST) {
            if (resultCode == RESULT_OK) {
                Snackbar.make(_fab, R.string.saved, Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_hub, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.new_hub_manual) {
            Intent intent = HubDetailActivity.intentWithParams(this);
            startActivityForResult(intent, NEW_HUB_REQUEST);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
