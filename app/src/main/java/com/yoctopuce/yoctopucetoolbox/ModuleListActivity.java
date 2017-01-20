package com.yoctopuce.yoctopucetoolbox;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yoctopuce.yoctopucetoolbox.details_fragments.DetailGenericModuleFragment;
import com.yoctopuce.yoctopucetoolbox.details_fragments.FragmentChooser;
import com.yoctopuce.yoctopucetoolbox.functions.Module;
import com.yoctopuce.yoctopucetoolbox.service.CacheObserver;
import com.yoctopuce.yoctopucetoolbox.service.ModulesCache;
import com.yoctopuce.yoctopucetoolbox.service.UseHubActivity;

import java.util.UUID;

/**
 * An activity representing a list of ModulesCache. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ModuleDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ModuleListActivity extends UseHubActivity implements DetailGenericModuleFragment.OnYoctoErrorListener
{

    private static final int DETAIL_VIEW_REQUEST = 1;
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private SimpleItemRecyclerViewAdapter _recyclerViewAdapter;
    private CacheObserver _cacheObserver;
    private ModulesCache _modulesCache;
    private RecyclerView _recyclerView;

    public static Intent intentWithParams(Context ctx, UUID hubid)
    {
        Intent intent = new Intent(ctx, ModuleListActivity.class);
        intent.putExtra(HUB_UUID, hubid.toString());
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert toolbar != null;
        //toolbar.setTitle(_hubURL);//
        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(_hub.getDescription());
        }

        // setup recycler view
        _recyclerView = (RecyclerView) findViewById(R.id.module_list);
        assert _recyclerView != null;
        _modulesCache = ModulesCache.getInstance();
        _recyclerViewAdapter = new ModuleListActivity.SimpleItemRecyclerViewAdapter(_modulesCache);
        _cacheObserver = new CacheObserver()
        {
            @Override
            public void onChange(String key)
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        _recyclerViewAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onReload()
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        _recyclerViewAdapter.notifyDataSetChanged();
                    }
                });
            }
        };
        _modulesCache.registerCacheObserver(_cacheObserver);
        _recyclerView.setAdapter(_recyclerViewAdapter);

        if (findViewById(R.id.module_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

        }
    }

    @Override
    protected void onDestroy()
    {
        _modulesCache.unregisterCacheObserver(_cacheObserver);
        super.onDestroy();
    }

    @Override
    public void onYoctoError(String sender, String errmsg)
    {
        if (sender.equals("YAPI")) {
            finishActivityWithError(sender, errmsg);
        }

        if (mTwoPane) {
            // todo: unselect module and clear fragment
            if (_modulesCache.getItemCount() > 0) {
                try {
                    Module hub_module = _modulesCache.get(0);

                    Fragment fragment = FragmentChooser.GetFragment(hub_module.getSerialNumber());
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.module_detail_container, fragment)
                            .commit();
                    Snackbar.make(_recyclerView, "Device disconnected", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                } catch (IndexOutOfBoundsException ex) {
                    finishActivityWithError("YAPI", ex.getLocalizedMessage());

                }
            } else {
                finishActivityWithError(sender, errmsg);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // Check which request we're responding to
        if (requestCode == DETAIL_VIEW_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_CANCELED) {
                String errmsg = data.getStringExtra(ERRMSG);
                String sender = data.getStringExtra(SENDER);
                onYoctoError(sender, errmsg);
            }
        }
    }


    public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>
    {

        private final ModulesCache _modulesCache;

        SimpleItemRecyclerViewAdapter(ModulesCache modulesCache)
        {
            _modulesCache = modulesCache;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.module_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position)
        {
            holder.mItem = _modulesCache.get(position);
            holder.mIdView.setText(_modulesCache.get(position).getSerialNumber());
            holder.mContentView.setText(_modulesCache.get(position).getProductName());


            holder.mView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (mTwoPane) {
                        Fragment fragment = FragmentChooser.GetFragment(holder.mItem.getSerialNumber());
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.module_detail_container, fragment)
                                .commit();
                    } else {
                        //Context context = v.getContext();
                        Intent intent = ModuleDetailActivity.intentWithParams(ModuleListActivity.this, _hub.getUuid(), holder.mItem.getSerialNumber());
                        startActivityForResult(intent, DETAIL_VIEW_REQUEST);
                    }
                }
            });
        }


        public int getItemCount()
        {
            return _modulesCache.getItemCount();
        }

        class ViewHolder extends RecyclerView.ViewHolder
        {
            final View mView;
            final TextView mIdView;
            final TextView mContentView;
            Module mItem;

            ViewHolder(View view)
            {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString()
            {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }

    }


}
