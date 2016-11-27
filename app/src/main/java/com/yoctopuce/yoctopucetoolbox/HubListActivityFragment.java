package com.yoctopuce.yoctopucetoolbox;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yoctopuce.yoctopucetoolbox.hub.Hub;
import com.yoctopuce.yoctopucetoolbox.hub.HubStorage;
import com.yoctopuce.yoctopucetoolbox.misc.DividerItemDecoration;

import junit.framework.Assert;

import java.util.List;
import java.util.Locale;

/**
 * A placeholder fragment containing a simple view.
 */
public class HubListActivityFragment extends Fragment
{
    private RecyclerView _hubRecyclerView;
    private HubAdapter _adapter;
    private HubStorage _hubStorage;
    private List<Hub> _hubList;

    public HubListActivityFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_hub_list, container, false);
        _hubRecyclerView = (RecyclerView) view.findViewById(R.id.hub_list_recycler_view);
        _hubRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        _hubRecyclerView.addItemDecoration(itemDecoration);
        updateUI();


        return view;
    }

    private boolean hasUSBHostSupport()
    {
        PackageManager packageManager = getActivity().getPackageManager();
        return packageManager != null && packageManager.hasSystemFeature(PackageManager.FEATURE_USB_HOST);
    }


    @Override
    public void onResume()
    {
        super.onResume();
        updateUI();
    }

    private void updateUI()
    {
        _hubStorage = HubStorage.get(getActivity());
        _hubList = _hubStorage.getHubs();
        if (hasUSBHostSupport()) {
            _hubList.add(new Hub());
        }
        _adapter = new HubAdapter(_hubList);
        _hubRecyclerView.setAdapter(_adapter);
    }

    private class HubHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
    {
        public TextView _urlTextView;
        public TextView _productTextView;
        private Hub _hub;

        public HubHolder(View itemView)
        {
            super(itemView);
            _productTextView = (TextView) itemView.findViewById(R.id.hub_line1);
            _urlTextView = (TextView) itemView.findViewById(R.id.hub_line2);
            Assert.assertNotNull(_productTextView);
            Assert.assertNotNull(_urlTextView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void bindHub(Hub hub)
        {
            _hub = hub;
            _productTextView.setText(hub.getSerial());
            _urlTextView.setText(String.format(_hub.getUrl()));
        }

        @Override
        public void onClick(View v)
        {
            Snackbar.make(v, "Connect to " + _hub.getSerial(), Snackbar.LENGTH_SHORT).show();
            Intent intent = ModuleListActivity.intentWithParams(getContext(), _hub.getUrl());
            startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v)
        {
            Snackbar.make(v, String.format(getActivity().getString(R.string.forget_hub_), _hub.getSerial()), Snackbar.LENGTH_LONG)
                    .setAction(R.string.Yes, new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            int index = _hubList.indexOf(_hub);
                            _hubList.remove(index);
                            _hubStorage.remove(_hub.getUuid());
                            _adapter.notifyItemRemoved(index);

                        }
                    }).show();
            return true;
        }
    }

    private class HubAdapter extends RecyclerView.Adapter<HubHolder>
    {

        private List<Hub> _hubs;

        public HubAdapter(List<Hub> hubs)
        {
            this._hubs = hubs;
        }

        @Override
        public HubHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_hub, parent, false);
            return new HubHolder(view);
        }

        @Override
        public void onBindViewHolder(HubHolder holder, int position)
        {
            Hub hub = _hubs.get(position);
            holder.bindHub(hub);
        }

        @Override
        public int getItemCount()
        {
            return _hubs.size();
        }
    }
}
