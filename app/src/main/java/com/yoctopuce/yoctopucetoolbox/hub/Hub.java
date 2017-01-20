package com.yoctopuce.yoctopucetoolbox.hub;

import java.util.Locale;
import java.util.UUID;

public class Hub
{
    private final UUID _uuid;
    private final boolean _isUSB;
    private String _name;
    private String _host;
    private int _port;
    private String _user;
    private String _pass;
    private String _serial;
    private boolean _beacon = false;
    private boolean _online = false;


    public Hub(boolean isUSB)
    {
        _isUSB = isUSB;
        _name = "";
        if (_isUSB) {
            _serial = "USB";
            _host = "usb";
        } else {
            _port = 4444;
            _host = "";
            _user = "";
            _pass = "";
            _serial = "";
        }
        _uuid = UUID.randomUUID();
    }

    public boolean isUSB()
    {
        return _isUSB;
    }

    public Hub(String serial, String name, boolean beacon, String url)
    {
        _isUSB = false;
        _uuid = UUID.randomUUID();
        _name = name;
        _beacon = beacon;
        _serial = serial;
        if (url.startsWith("http://")) {
            url = url.substring(7);
        } else if (url.startsWith("ws://")) {
            url = url.substring(5);
        }
        int pos = url.indexOf('/');
        if (pos >= 0) {
            url = url.substring(0, pos);
        }
        pos = url.indexOf('@');
        if (pos <= 0) {
            _user = "";
            _pass = "";
        } else {
            int pass_pos = url.indexOf(':');
            if (pass_pos < 0 || pass_pos > pos) {
                _user = url.substring(0, pos);
                _pass = "";
            } else {
                _user = url.substring(0, pass_pos);
                _pass = "";
            }
        }
        pos = url.indexOf(':');
        if (pos < 0) {
            _host = url;
        } else {
            _host = url.substring(0, pos);
            String substring = url.substring(pos + 1);
            try {
                _port = Integer.parseInt(substring);
            } catch (NumberFormatException ex) {
                _port = 4444;
            }
        }
    }

    public Hub(UUID uuid, String serial, String name, String host, int port, String user, String pass)
    {
        _uuid = uuid;
        _serial = serial;
        _name = name;
        _host = host;
        _port = port;
        _user = user;
        _pass = pass;
        _isUSB = false;
    }

    public UUID getUuid()
    {
        return _uuid;
    }

    public String getHost()
    {
        return _host;
    }

    public void setHost(String host)
    {
        _host = host;
    }

    public int getPort()
    {
        return _port;
    }

    public void setPort(int port)
    {
        _port = port;
    }

    public String getUser()
    {
        return _user;
    }

    public void setUser(String user)
    {
        _user = user;
    }

    public String getPass()
    {
        return _pass;
    }

    public void setPass(String pass)
    {
        _pass = pass;
    }

    public String getSerial()
    {
        return _serial;
    }

    public String getDescription()
    {
        if (_name.length() == 0) {
            return _serial;
        } else {
            return String.format(Locale.US, "%s (%s)", _name, _serial);
        }
    }


    public String getUrl()
    {
        if (_isUSB) {
            return "usb";
        }
        if (_user.length() > 0) {
            return String.format(Locale.US, "%s:%s@%s:%d", _user, _pass, _host, _port);
        } else {
            return String.format(Locale.US, "%s:%d", _host, _port);

        }
    }

    public void setSerial(String serial)
    {
        _serial = serial;
    }

    public boolean isBeacon()
    {
        return _beacon;
    }

    public void setBeacon(boolean beacon)
    {
        _beacon = beacon;
    }

    public boolean isOnline()
    {
        return _online;
    }

    public void setOnline(boolean online)
    {
        _online = online;
    }

    public String getName()
    {
        return _name;
    }

    public void setName(String name)
    {
        _name = name;
    }
}
