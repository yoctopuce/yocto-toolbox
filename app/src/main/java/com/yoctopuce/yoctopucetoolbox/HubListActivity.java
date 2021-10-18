package com.yoctopuce.yoctopucetoolbox;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import static com.yoctopuce.yoctopucetoolbox.R.id.fab_auto;

public class HubListActivity extends ActivityWithMenu implements View.OnClickListener {

    private static final int NEW_HUB_REQUEST = 2;
    private FloatingActionButton _fab;
    private FloatingActionButton _fabManual;
    private FloatingActionButton _fabAuto;
    private boolean _isFabOpen;
    private Animation _fabOpen;
    private Animation _fabClose;
    private Animation _rotateForward;
    private Animation _rotateBackward;
    private TextView _fabLabManual;
    private TextView _fabLabAuto;
    private View _hideView;
    private Animation _bgOpen;
    private Animation _bgClose;


    public void toogleFab() {


        if (_isFabOpen) {
            _fab.startAnimation(_rotateBackward);
            _fabManual.startAnimation(_fabClose);
            _fabAuto.startAnimation(_fabClose);
            _fabLabManual.startAnimation(_fabClose);
            _fabLabAuto.startAnimation(_fabClose);
            _hideView.startAnimation(_bgClose);
            _fabManual.setClickable(false);
            _fabAuto.setClickable(false);
            _isFabOpen = false;
        } else {

            _fab.startAnimation(_rotateForward);
            _fabManual.startAnimation(_fabOpen);
            _fabAuto.startAnimation(_fabOpen);
            _fabLabManual.startAnimation(_fabOpen);
            _fabLabAuto.startAnimation(_fabOpen);
            _hideView.startAnimation(_bgOpen);
            _fabManual.setClickable(true);
            _fabAuto.setClickable(true);
            _isFabOpen = true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        _isFabOpen = false;
        _fab = findViewById(R.id.fab_global);
        assert _fab != null;
        _fab.setOnClickListener(this);
        _fabManual = findViewById(R.id.fab_manual);
        assert _fabManual != null;
        _fabManual.setOnClickListener(this);
        _fabAuto = findViewById(fab_auto);
        assert _fabAuto != null;
        _fabAuto.setOnClickListener(this);
        _fabLabManual = findViewById(R.id.fab_label_manual);
        assert _fabLabManual != null;
        _fabLabAuto = findViewById(R.id.fab_label_auto);
        assert _fabLabAuto != null;
        _hideView = findViewById(R.id.hide_layer);
        assert _hideView != null;
        _fabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        _fabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        _bgOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bg_open);
        _bgClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bg_close);
        _rotateForward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        _rotateBackward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (_isFabOpen) {
            toogleFab();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_HUB_REQUEST) {
            if (resultCode == RESULT_OK) {
                Snackbar.make(_fab, R.string.saved, Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_hub, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.new_hub_manual) {
            Intent intent = HubDetailActivity.intentWithParams(this);
            startActivityForResult(intent, NEW_HUB_REQUEST);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.fab_global) {
            toogleFab();
        } else if (id == R.id.fab_manual) {
            Intent intent = HubDetailActivity.intentWithParams(this);
            startActivityForResult(intent, NEW_HUB_REQUEST);
        } else if (id == R.id.fab_auto) {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(HubListActivity.this, HubDiscoveryActivity.class);
            startActivity(detailIntent);
        }
    }
}
