package com.yoctopuce.yoctopucetoolbox;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yoctopuce.YoctoAPI.YAPIContext;
import com.yoctopuce.yoctopucetoolbox.hub.Hub;
import com.yoctopuce.yoctopucetoolbox.hub.HubStorage;

import java.util.Locale;
import java.util.UUID;

/**
 * A placeholder fragment containing a simple view.
 */
public class HubDetailActivityFragment extends Fragment
{

    public static final String ARG_UUID = "uuid";

    private EditText _hostnameEditText;
    private EditText _usernameEditText;
    private EditText _passwordEditText;
    private EditText _portEditText;
    private Button _testButton;
    private boolean _isNewHub;
    private Hub _hub;
    private ProgressBar _progress;
    private TextView _result;
    private View _restultLayout;
    private HubStorage _hubStorage;

    public static HubDetailActivityFragment getFragment(UUID hubUUID)
    {
        Bundle args = new Bundle();
        args.putString(ARG_UUID, hubUUID.toString());
        final HubDetailActivityFragment fragment = new HubDetailActivityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static HubDetailActivityFragment getFragment()
    {
        Bundle args = new Bundle();
        final HubDetailActivityFragment fragment = new HubDetailActivityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            final String uuid_str = getArguments().getString(ARG_UUID);
            _hubStorage = HubStorage.get(getContext());
            _isNewHub = uuid_str == null;
            if (_isNewHub) {
                _hub = new Hub(false);
            } else {
                UUID uuid = UUID.fromString(uuid_str);
                _hub = _hubStorage.getHub(uuid);
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_hub_detail, container, false);
        _hostnameEditText = (EditText) view.findViewById(R.id.hostname);
        _portEditText = (EditText) view.findViewById(R.id.port);
        _usernameEditText = (EditText) view.findViewById(R.id.username);
        _passwordEditText = (EditText) view.findViewById(R.id.password);
        _progress = (ProgressBar) view.findViewById(R.id.test_progress);
        _restultLayout = view.findViewById(R.id.result_layout);
        _result = (TextView) view.findViewById(R.id.result);
        _testButton = (Button) view.findViewById(R.id.test_button);
        _testButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                validateHubData();
                _restultLayout.setVisibility(View.VISIBLE);
                _testButton.setEnabled(false);
                _progress.setVisibility(View.VISIBLE);
                _progress.setProgress(1);
                _result.setText("");
                new TestHub().execute(_hub.getUrl());
            }
        });

        _hostnameEditText.setText(_hub.getHost());
        _portEditText.setText(String.format(Locale.US, "%d", _hub.getPort()));
        _usernameEditText.setText(_hub.getUser());
        _passwordEditText.setText(_hub.getPass());
        return view;
    }

    private boolean validateHubData()
    {
        _hub.setHost(_hostnameEditText.getText().toString());
        final String port_str = _portEditText.getText().toString();
        try {
            final Integer port = Integer.valueOf(port_str);
            _hub.setPort(port);
        } catch (NumberFormatException ignored) {
            return false;
        }
        _hub.setUser(_usernameEditText.getText().toString());
        _hub.setPass(_passwordEditText.getText().toString());
        return true;
    }

    class TestHub extends AsyncTask<String, Void, String>
    {

        @Override
        protected String doInBackground(String... strings)
        {
            String url = strings[0];
            final YAPIContext yctx = new YAPIContext();
            try {
                yctx.TestHub(url, 5000);
            } catch (Exception e) {
                return e.getLocalizedMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String error)
        {
            _testButton.setEnabled(true);
            _progress.setProgress(100);
            _progress.setVisibility(View.GONE);
            if (error != null) {
                _result.setText(error);
            } else {
                _result.setText(R.string.success);
            }
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_save_only, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.save_menu:
                final boolean isValid = validateHubData();
                if (isValid) {
                    if (_isNewHub) {
                        _hubStorage.addNewHub(_hub);
                    }else{
                        _hubStorage.updateHub(_hub);
                    }
                    getActivity().finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
