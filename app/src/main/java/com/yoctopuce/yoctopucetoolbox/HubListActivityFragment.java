package com.yoctopuce.yoctopucetoolbox;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yoctopuce.yoctopucetoolbox.hub.Hub;
import com.yoctopuce.yoctopucetoolbox.hub.HubStorage;
import com.yoctopuce.yoctopucetoolbox.misc.DividerItemDecoration;
import com.yoctopuce.yoctopucetoolbox.misc.HubViewHolder;
import com.yoctopuce.yoctopucetoolbox.misc.MiscHelper;

import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * A placeholder fragment containing a simple view.
 */
public class HubListActivityFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{
    private static final long REFRESH_DELAY_MS = 5000;
    private RecyclerView _hubRecyclerView;
    private HubAdapter _adapter;
    private HubStorage _hubStorage;
    private List<Hub> _hubList;
    private Map<UUID, Hub> _hubMap;
    private Handler _handler;

    Runnable _refreshUIRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            final ArrayList<Hub> hubs = new ArrayList<>(_hubList);
            new RefreshHubState().execute(hubs);

        }
    };
    private SwipeRefreshLayout _swipeLayout;
    private boolean _inFront;


    private class RefreshHubState extends AsyncTask<ArrayList<Hub>, JSONObject, String>
    {


        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        protected String doInBackground(ArrayList<Hub>... params)
        {
            ArrayList<Hub> hubs = params[0];
            for (Hub hub : hubs) {
                if (hub.isUSB()) {
                    continue;
                }
                final String moduleUrl = "http://" + hub.getUrl() + "/api.json";
                JSONObject jsonObject = MiscHelper.requestJson(moduleUrl);
                if (jsonObject == null) {
                    jsonObject = new JSONObject();
                }
                try {
                    jsonObject.put("uuid", hub.getUuid());
                } catch (JSONException e) {
                    continue;
                }
                publishProgress(jsonObject);
            }
            return "";
        }

        @Override
        protected void onProgressUpdate(JSONObject... values)
        {
            for (JSONObject jsonObject : values) {
                UUID uuid = (UUID) jsonObject.opt("uuid");
                Hub hub = _hubMap.get(uuid);
                if (hub != null) {
                    boolean needRefresh = MiscHelper.updateHubFromJson(jsonObject, hub, _hubStorage);
                    if (needRefresh) {
                        int index = _hubList.indexOf(hub);
                        _adapter.notifyItemChanged(index);
                    }
                }
            }
        }

        protected void onPostExecute(String result)
        {
            _swipeLayout.setRefreshing(false);
            if (_inFront) {
                _handler.postDelayed(_refreshUIRunnable, REFRESH_DELAY_MS);
            }
        }
    }

    public HubListActivityFragment()
    {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        _handler = new Handler();
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

        _swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        _swipeLayout.setOnRefreshListener(this);
        setupUI();

        return view;
    }

    @Override
    public void onRefresh()
    {
        // remove potential delayed refresh
        _handler.removeCallbacks(_refreshUIRunnable);
        _handler.post(_refreshUIRunnable);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        setupUI();
        _inFront = true;
        _handler.post(_refreshUIRunnable);
    }

    @Override
    public void onPause()
    {
        _inFront = false;
        _handler.removeCallbacks(_refreshUIRunnable);
        super.onPause();
    }

    private void setupUI()
    {
        _hubStorage = HubStorage.get(getActivity());
        _hubList = _hubStorage.getHubs();
        _hubMap = new HashMap<>(_hubList.size());
        for (Hub hub : _hubList) {
            _hubMap.put(hub.getUuid(), hub);
        }
        _adapter = new HubAdapter(_hubList);
        _hubRecyclerView.setAdapter(_adapter);
    }



    private class HubAdapter extends RecyclerView.Adapter<HubViewHolder> implements HubViewHolder.OnSelectListener, HubViewHolder.OnEditListener, HubViewHolder.OnDeleteListener
    {

        private List<Hub> _hubs;

        HubAdapter(List<Hub> hubs)
        {
            this._hubs = hubs;
        }

        @Override
        public HubViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_hub, parent, false);
            return new HubViewHolder(view);
        }

        @Override
        public void onBindViewHolder(HubViewHolder holder, int position)
        {
            Hub hub = _hubs.get(position);
            if (hub.isUSB()){
                holder.bindHub(hub, this, null, null);
            } else {
                final HubViewHolder.OnEditListener editListener = this;
                final HubViewHolder.OnDeleteListener hubAdapter = this;
                holder.bindHub(hub, this, editListener, hubAdapter);
            }
        }

        @Override
        public int getItemCount()
        {
            return _hubs.size();
        }

        @Override
        public boolean onSelect(Hub hub)
        {
            Intent intent = ModuleListActivity.intentWithParams(getContext(), hub.getUuid());
            //nice: do not use hardcoded request code but move this code in activity
            getActivity().startActivityForResult(intent, 1);
            return true;
        }

        @Override
        public boolean onEdit(Hub hub)
        {
            final Intent intent = HubDetailActivity.intentWithParams(getContext(), hub.getUuid());
            startActivity(intent);
            return true;
        }

        @Override
        public boolean onDelete(final Hub hub)
        {
            Snackbar.make(_hubRecyclerView, String.format(getActivity().getString(R.string.forget_hub_), hub.getSerial()), Snackbar.LENGTH_LONG)
                    .setAction(R.string.Yes, new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            int index = _hubList.indexOf(hub);
                            _hubList.remove(index);
                            _hubStorage.remove(hub.getUuid());
                            _adapter.notifyItemRemoved(index);

                        }
                    }).show();
            return true;
        }
    }
}
