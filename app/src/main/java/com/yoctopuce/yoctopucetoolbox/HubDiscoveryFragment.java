package com.yoctopuce.yoctopucetoolbox;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yoctopuce.YoctoAPI.YAPI;
import com.yoctopuce.YoctoAPI.YAPIContext;
import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.yoctopucetoolbox.hub.Hub;
import com.yoctopuce.yoctopucetoolbox.hub.HubStorage;
import com.yoctopuce.yoctopucetoolbox.misc.DividerItemDecoration;
import com.yoctopuce.yoctopucetoolbox.misc.HubViewHolder;
import com.yoctopuce.yoctopucetoolbox.misc.MiscHelper;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A placeholder fragment containing a simple view.
 */
public class HubDiscoveryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{

    private static final String NEW_HUB_NAME = "new_hub_name";
    private static final String NEW_HUB_SERIAL = "new_hub_serial";
    private static final String NEW_HUB_URL = "new_hub_url";
    private static final String NEW_HUB_BEACON = "new_hub_beacon";

    private RecyclerView _hubRecyclerView;
    private ArrayList<Hub> _dicoverdHubs;
    private HubAdapter _adapter;
    private YAPIContext _yapi;
    private HubStorage _hubStorage;
    private SwipeRefreshLayout _swipeLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_hub_discovery, container, false);
        _hubRecyclerView = view.findViewById(R.id.hub_list_recycler_view);
        _hubRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL_LIST);
        _hubRecyclerView.addItemDecoration(itemDecoration);
        _hubStorage = HubStorage.get(getContext());
        _swipeLayout = view.findViewById(R.id.swipeRefreshLayout);
        _swipeLayout.setOnRefreshListener(this);
        updateUI();
        return view;
    }

    private void updateUI()
    {
        _dicoverdHubs = new ArrayList<>();
        _adapter = new HubAdapter(_dicoverdHubs);
        _hubRecyclerView.setAdapter(_adapter);
    }


    @Override
    public void onStart()
    {
        super.onStart();

        AsyncTask<Integer, Integer, Integer> task = new AsyncTask<Integer, Integer, Integer>()
        {

            @Override
            protected Integer doInBackground(Integer... params)
            {
                try {
                    _yapi = new YAPIContext();
                    _yapi.RegisterLogFunction(new YAPI.LogCallback()
                    {
                        @Override
                        public void yLog(String line)
                        {
                            Log.d("HUB_Disc", "YAPI:" + line.trim());
                        }
                    });
                    _yapi.InitAPI(YAPI.DETECT_NONE);
                    _yapi.RegisterHubDiscoveryCallback(mNewHub);
                } catch (YAPI_Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        };
        task.execute(0);
    }


    private final Handler mMainHandler = new MyHandler(Looper.getMainLooper(), this);

    @Override
    public void onResume()
    {
        super.onResume();
        _swipeLayout.setRefreshing(true);
        onRefresh();
    }

    @Override
    public void onRefresh()
    {
        AsyncTask<Integer, Integer, Integer> task = new AsyncTask<Integer, Integer, Integer>()
        {

            @Override
            protected Integer doInBackground(Integer... params)
            {
                try {
                    _yapi.TriggerHubDiscovery();
                    _yapi.Sleep(10000);
                } catch (YAPI_Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }

            @Override
            protected void onPostExecute(Integer integer)
            {
                _swipeLayout.setRefreshing(false);
            }
        };
        task.execute(0);
    }

    private static class MyHandler extends Handler
    {
        private final WeakReference<HubDiscoveryFragment> _fragmentWeakReference;

        MyHandler(@NonNull Looper looper, HubDiscoveryFragment fragment)
        {
            super(looper);
            _fragmentWeakReference = new WeakReference<>(fragment);
        }

        public void handleMessage(Message msg)
        {
            HubDiscoveryFragment discoveryFragment = _fragmentWeakReference.get();
            if (discoveryFragment != null) {
                String serial = msg.getData().getString(NEW_HUB_SERIAL);
                String name = msg.getData().getString(NEW_HUB_NAME, "");
                String url = msg.getData().getString(NEW_HUB_URL);
                boolean beacon = msg.getData().getBoolean(NEW_HUB_BEACON, false);
                Hub hub = discoveryFragment._hubStorage.getHub(serial);
                if (hub != null && hub.getUrl(false).equals(url)) {
                    return;
                }
                for (Hub h : discoveryFragment._dicoverdHubs) {
                    if (h.getSerial().equals(serial)) {
                        int pos = discoveryFragment._dicoverdHubs.indexOf(h);
                        discoveryFragment._adapter.notifyItemChanged(pos);
                        return;
                    }

                }
                int pos = discoveryFragment._dicoverdHubs.size();
                if (hub == null) {
                    hub = new Hub(serial, name, beacon, url);
                } else {
                    hub.setName(name);
                    hub.setBeacon(beacon);
                    hub.setUrl(url);
                }
                hub.setOnline(true);
                discoveryFragment._dicoverdHubs.add(hub);
                discoveryFragment._adapter.notifyItemInserted(pos);
            }
        }

    }


    private final YAPI.HubDiscoveryCallback mNewHub = (serial, url) -> {
        Log.d("HUB_Disc", "Find new hub " + serial + " " + url);
        final JSONObject jsonObject = MiscHelper.requestJson("http://" + url + "/api/module.json");
        String name = null;
        boolean beacon = false;
        if (jsonObject != null) {
            name = jsonObject.optString("logicalName");
            final int beaconInt = jsonObject.optInt("beacon", 0);
            beacon = beaconInt == 1;
        }

        Message myMessage = mMainHandler.obtainMessage();
        Bundle messageBundle = new Bundle();
        messageBundle.putString(NEW_HUB_SERIAL, serial);
        messageBundle.putString(NEW_HUB_NAME, name);
        messageBundle.putBoolean(NEW_HUB_BEACON, beacon);
        messageBundle.putString(NEW_HUB_URL, url);
        myMessage.setData(messageBundle);
        mMainHandler.sendMessage(myMessage);
    };


    @Override
    public void onStop()
    {
        super.onStop();
        if (_yapi != null) {
            _yapi.FreeAPI();
        }
    }


    private class HubAdapter extends RecyclerView.Adapter<HubViewHolder>
    {

            private final List<Hub> _hubs;

        HubAdapter(List<Hub> _hubs)
        {
            this._hubs = _hubs;
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
        public void onBindViewHolder(HubViewHolder holder, int position)
        {
            Hub hub = _hubs.get(position);
            hub.setRefreshing(false);
            holder.bindHub(hub, hub1 -> {
                final String serial = hub1.getSerial();
                final Hub perviousHub = _hubStorage.getHub(serial);
                if (perviousHub != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                    builder.setMessage(String.format(Locale.US, getString(R.string.update_yoctohub_descr),
                            serial, hub1.getUrl(false)))
                            .setTitle(R.string.update_yoctohub_url);
                    builder.setPositiveButton(R.string.update, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            _hubStorage.updateHub(hub1);
                            requireActivity().finish();
                        }
                    });
                    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {

                        }
                    });
                    // 3. Get the AlertDialog from create()
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    _hubStorage.addNewHub(hub1);
                    requireActivity().finish();
                }
                return true;
            }, null, null);
        }

        @Override
        public int getItemCount()
        {
            return _hubs.size();
        }
    }
}
