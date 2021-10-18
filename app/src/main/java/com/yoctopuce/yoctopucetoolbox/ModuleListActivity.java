package com.yoctopuce.yoctopucetoolbox;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yoctopuce.YoctoAPI.YAPI_Exception;
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
public class ModuleListActivity extends UseHubActivity
{

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private SimpleItemRecyclerViewAdapter _recyclerViewAdapter;
    private CacheObserver _cacheObserver;
    private ModulesCache _modulesCache;
    private RelativeLayout _fatalErrorView;
    private TextView _fatalErrorTextView;
    private Button _closeButton;

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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert toolbar != null;
        //toolbar.setTitle(_hubURL);//
        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(_hub.getDescription(this));
        }

        // setup recycler view
        RecyclerView _recyclerView = findViewById(R.id.module_list);
        assert _recyclerView != null;
        _modulesCache = ModulesCache.getInstance();
        _recyclerViewAdapter = new ModuleListActivity.SimpleItemRecyclerViewAdapter(_modulesCache);
        _cacheObserver = new CacheObserver()
        {
            @Override
            public void onChange(String key)
            {
                runOnUiThread(() -> _recyclerViewAdapter.notifyDataSetChanged());
            }

            @Override
            public void onReload()
            {
                runOnUiThread(() -> _recyclerViewAdapter.notifyDataSetChanged());
            }
        };
        _modulesCache.registerCacheObserver(_cacheObserver);
        _recyclerView.setAdapter(_recyclerViewAdapter);


        // get reference on Error panel
        _fatalErrorView = findViewById(R.id.fatal_pannel);
        _fatalErrorTextView = findViewById(R.id.fatal_error_msg);
        _closeButton = findViewById(R.id.close_button);


        if (findViewById(R.id.module_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

        }
    }

    @Override
    public void onBGError(final YAPI_Exception e)
    {
        e.printStackTrace();
        postUI(() -> displayErrorPanel(e.getLocalizedMessage()));
    }

    protected void displayErrorPanel(String localizedMessage)
    {
        _fatalErrorTextView.setText(String.format(getString(R.string.device_disconnected_s), localizedMessage));
        _closeButton.setOnClickListener(view -> {
            //todo: differentiate fatal from recoverable error
            finish();
        });
        _fatalErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy()
    {
        _modulesCache.unregisterCacheObserver(_cacheObserver);
        super.onDestroy();
    }



    public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>
    {

        private final ModulesCache _modulesCache;

        SimpleItemRecyclerViewAdapter(ModulesCache modulesCache)
        {
            _modulesCache = modulesCache;
        }

        @NonNull
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


            holder.mView.setOnClickListener(v -> {
                if (mTwoPane) {
                    Fragment fragment = FragmentChooser.GetFragment(holder.mItem.getSerialNumber());
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.module_detail_container, fragment)
                            .commit();
                } else {
                    //Context context = v.getContext();
                    Intent intent = ModuleDetailActivity.intentWithParams(ModuleListActivity.this, _hub.getUuid(), holder.mItem.getSerialNumber());
                    startActivity(intent);
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
                mIdView = view.findViewById(R.id.id);
                mContentView = view.findViewById(R.id.content);
            }

            @NonNull
            @Override
            public String toString()
            {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }

    }


}
