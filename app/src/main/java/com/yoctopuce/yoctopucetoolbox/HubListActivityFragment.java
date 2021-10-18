package com.yoctopuce.yoctopucetoolbox;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.yoctopuce.yoctopucetoolbox.hub.Hub;
import com.yoctopuce.yoctopucetoolbox.hub.HubStorage;
import com.yoctopuce.yoctopucetoolbox.misc.DividerItemDecoration;
import com.yoctopuce.yoctopucetoolbox.misc.HubViewHolder;
import com.yoctopuce.yoctopucetoolbox.misc.MiscHelper;
import com.yoctopuce.yoctopucetoolbox.misc.TaskRunner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * A placeholder fragment containing a simple view.
 */
public class HubListActivityFragment extends Fragment
{
    private static final long REFRESH_DELAY_MS = 5000;
    private RecyclerView _hubRecyclerView;
    private HubAdapter _adapter;
    private HubStorage _hubStorage;
    private List<Hub> _hubList;
    private Map<UUID, Hub> _hubMap;

    private SwipeRefreshLayout _swipeLayout;
    private View _noHubView;
    private TaskRunner _runner;


    public HubListActivityFragment()
    {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        _runner = new TaskRunner();
    }

    private void refreshAllHub()
    {
        //todo: do not allow two refresh on the same layout. The solution is to have a refresh context
        // by hub instead of globally.
        for (UUID uuid : _hubMap.keySet()) {
            Hub hub = _hubMap.get(uuid);
            if (hub != null && !hub.isUSB()) {
                hub.setRefreshing(true);
                int index = _hubList.indexOf(hub);
                _adapter.notifyItemChanged(index);
                _runner.executeAsync(
                        () -> {
                            final String moduleUrl = "http://" + hub.getUrl(true) + "/api.json";
                            JSONObject jsonObject = MiscHelper.requestJson(moduleUrl);
                            if (jsonObject == null) {
                                jsonObject = new JSONObject();
                            }
                            try {
                                jsonObject.put("uuid", hub.getUuid());
                            } catch (JSONException e) {
                                return null;
                            }


                            return jsonObject;
                        },
                        jsonObject -> {
                            //UUID uuid = (UUID) jsonObject.opt("uuid");
                            Hub hub1 = _hubMap.get(uuid);
                            if (hub1 != null) {
                                boolean needRefresh = MiscHelper.updateHubFromJson(jsonObject, hub1, _hubStorage);
                                hub1.setRefreshing(false);
                                int index1 = _hubList.indexOf(hub1);
                                _adapter.notifyItemChanged(index1);
                            }
                        }
                );
            }
        }
        _swipeLayout.setRefreshing(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_hub_list, container, false);
        _hubRecyclerView = view.findViewById(R.id.hub_list_recycler_view);
        _hubRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        _noHubView = view.findViewById(R.id.no_hub_view);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL_LIST);
        _hubRecyclerView.addItemDecoration(itemDecoration);

        _swipeLayout = view.findViewById(R.id.swipeRefreshLayout);
        _swipeLayout.setOnRefreshListener(this::refreshAllHub);
        setupUI();

        return view;
    }


    @Override
    public void onResume()
    {
        super.onResume();
        setupUI();
        refreshAllHub();
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void setupUI()
    {
        _hubStorage = HubStorage.get(getActivity());
        _hubList = _hubStorage.getHubs();
        _hubMap = new HashMap<>(_hubList.size());
        for (Hub hub : _hubList) {
            _hubMap.put(hub.getUuid(), hub);
        }
        _adapter = new HubAdapter(_hubList);
        RecyclerView.AdapterDataObserver dataObserver = new RecyclerView.AdapterDataObserver()
        {
            void testIsEmpty()
            {
                if (_adapter.getItemCount() == 0) {
                    _noHubView.setVisibility(View.VISIBLE);
                    _hubRecyclerView.setVisibility(View.GONE);
                } else {
                    _noHubView.setVisibility(View.GONE);
                    _hubRecyclerView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onChanged()
            {
                super.onChanged();
                testIsEmpty();
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount)
            {
                testIsEmpty();
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount)
            {
                testIsEmpty();
            }
        };
        _adapter.registerAdapterDataObserver(dataObserver);
        _hubRecyclerView.setAdapter(_adapter);
        _adapter.notifyDataSetChanged();

    }


    private class HubAdapter extends RecyclerView.Adapter<HubViewHolder> implements HubViewHolder.OnSelectListener, HubViewHolder.OnEditListener, HubViewHolder.OnDeleteListener
    {

        private final List<Hub> _hubs;

        HubAdapter(List<Hub> hubs)
        {
            this._hubs = hubs;
        }

        @NonNull
        @Override
        public HubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_hub, parent, false);
            return new HubViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull HubViewHolder holder, int position)
        {
            Hub hub = _hubs.get(position);
            if (hub.isUSB()) {
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
            requireActivity().startActivity(intent);
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
            Snackbar.make(_hubRecyclerView, String.format(requireActivity().getString(R.string.forget_hub_), hub.getSerial()), Snackbar.LENGTH_LONG)
                    .setAction(R.string.Yes, v -> {
                        int index = _hubList.indexOf(hub);
                        _hubList.remove(index);
                        _hubStorage.remove(hub.getUuid());
                        _adapter.notifyItemRemoved(index);

                    }).show();
            return true;
        }
    }
}
