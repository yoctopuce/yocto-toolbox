package com.yoctopuce.yoctopucetoolbox;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yoctopuce.YoctoAPI.YAPI;
import com.yoctopuce.YoctoAPI.YAPIContext;
import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.yoctopucetoolbox.hub.Hub;
import com.yoctopuce.yoctopucetoolbox.hub.HubStorage;
import com.yoctopuce.yoctopucetoolbox.misc.DividerItemDecoration;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A placeholder fragment containing a simple view.
 */
public class HubDiscoveryFragment extends Fragment
{

    private RecyclerView _hubRecyclerView;
    private ArrayList<Hub> _dicoverdHubs;
    private HubAdapter _adapter;
    private YAPIContext _yapi;
    private HubStorage _hubStorage;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_hub_discovery, container, false);
        _hubRecyclerView = (RecyclerView) view.findViewById(R.id.hub_list_recycler_view);
        _hubRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        _hubRecyclerView.addItemDecoration(itemDecoration);
        _hubStorage = HubStorage.get(getContext());
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


    protected static final String NEW_HUB_SERIAL = "new_hub_serial";
    protected static final String NEW_HUB_URL = "new_hub_url";

    private Handler mMainHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            String serial = msg.getData().getString(NEW_HUB_SERIAL);
            if (_hubStorage.contain(serial)){
                return;
            }
            String url = msg.getData().getString(NEW_HUB_URL);
            for (Hub h : _dicoverdHubs) {
                if (h.getSerial().equals(serial)) {
                    h.setUrl(url);
                    int pos = _dicoverdHubs.indexOf(h);
                    _adapter.notifyItemChanged(pos);
                    return;
                }

            }
            int pos = _dicoverdHubs.size();
            _dicoverdHubs.add(new Hub(serial, url));
            _adapter.notifyItemInserted(pos);
        }
    };

    private YAPI.HubDiscoveryCallback mNewHub = new YAPI.HubDiscoveryCallback()
    {
        @Override
        public void yHubDiscoveryCallback(String serial, String url)
        {
            Message myMessage = mMainHandler.obtainMessage();
            Bundle messageBundle = new Bundle();
            messageBundle.putString(NEW_HUB_SERIAL, serial);
            messageBundle.putString(NEW_HUB_URL, url);
            myMessage.setData(messageBundle);
            mMainHandler.sendMessage(myMessage);
        }
    };


    @Override
    public void onStop()
    {
        super.onStop();
        _yapi.FreeAPI();
    }


    private class HubHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView _urlTextView;
        public TextView _productTextView;
        private Hub _bindedHub;

        public HubHolder(View itemView)
        {
            super(itemView);
            itemView.setOnClickListener(this);
            _productTextView = (TextView) itemView.findViewById(R.id.hub_line1);
            _urlTextView = (TextView) itemView.findViewById(R.id.hub_line2);
            Assert.assertNotNull(_productTextView);
            Assert.assertNotNull(_urlTextView);
        }


        void bindHub(Hub hub)
        {
            _bindedHub = hub;
            _productTextView.setText(hub.getSerial());
            _urlTextView.setText(String.format(Locale.US, "%s:%d", hub.getHost(), hub.getPort()));
        }

        @Override
        public void onClick(View v) {
            _hubStorage.addNewHub(_bindedHub);
            getActivity().finish();
        }
    }

    private class HubAdapter extends RecyclerView.Adapter<HubHolder>
    {

        private List<Hub> _hubs;

        public HubAdapter(List<Hub> _hubs)
        {
            this._hubs = _hubs;
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
