/*********************************************************************
 *
 * $Id: pic24config.php 26169 2016-12-12 01:36:34Z mvuilleu $
 *
 * Implements Network wrapper for Android toolbox
 *
 * - - - - - - - - - License information: - - - - - - - - - 
 *
 *  Copyright (C) 2011 and beyond by Yoctopuce Sarl, Switzerland.
 *
 *  Yoctopuce Sarl (hereafter Licensor) grants to you a perpetual
 *  non-exclusive license to use, modify, copy and integrate this
 *  file into your software for the sole purpose of interfacing
 *  with Yoctopuce products.
 *
 *  You may reproduce and distribute copies of this file in
 *  source or object form, as long as the sole purpose of this
 *  code is to interface with Yoctopuce products. You must retain
 *  this notice in the distributed source file.
 *
 *  You should refer to Yoctopuce General Terms and Conditions
 *  for additional information regarding your rights and
 *  obligations.
 *
 *  THE SOFTWARE AND DOCUMENTATION ARE PROVIDED 'AS IS' WITHOUT
 *  WARRANTY OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING 
 *  WITHOUT LIMITATION, ANY WARRANTY OF MERCHANTABILITY, FITNESS
 *  FOR A PARTICULAR PURPOSE, TITLE AND NON-INFRINGEMENT. IN NO
 *  EVENT SHALL LICENSOR BE LIABLE FOR ANY INCIDENTAL, SPECIAL,
 *  INDIRECT OR CONSEQUENTIAL DAMAGES, LOST PROFITS OR LOST DATA,
 *  COST OF PROCUREMENT OF SUBSTITUTE GOODS, TECHNOLOGY OR
 *  SERVICES, ANY CLAIMS BY THIRD PARTIES (INCLUDING BUT NOT
 *  LIMITED TO ANY DEFENSE THEREOF), ANY CLAIMS FOR INDEMNITY OR
 *  CONTRIBUTION, OR OTHER SIMILAR COSTS, WHETHER ASSERTED ON THE
 *  BASIS OF CONTRACT, TORT (INCLUDING NEGLIGENCE), BREACH OF
 *  WARRANTY, OR OTHERWISE.
 *
 *********************************************************************/

package com.yoctopuce.yoctopucetoolbox.functions;
import com.yoctopuce.YoctoAPI.YAPIContext;
import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YNetwork;

//--- (YNetwork class start)
/**
 * YNetwork Class: Network function interface
 *
 * YNetwork objects provide access to TCP/IP parameters of Yoctopuce
 * modules that include a built-in network interface.
 */
 @SuppressWarnings("UnusedDeclaration")
public class Network extends Function
{
// valueCallbackNetwork
    protected int _readiness =  YNetwork.READINESS_INVALID;
    protected String _macAddress =  YNetwork.MACADDRESS_INVALID;
    protected String _ipAddress =  YNetwork.IPADDRESS_INVALID;
    protected String _subnetMask =  YNetwork.SUBNETMASK_INVALID;
    protected String _router =  YNetwork.ROUTER_INVALID;
    protected String _ipConfig =  YNetwork.IPCONFIG_INVALID;
    protected String _primaryDNS =  YNetwork.PRIMARYDNS_INVALID;
    protected String _secondaryDNS =  YNetwork.SECONDARYDNS_INVALID;
    protected String _ntpServer =  YNetwork.NTPSERVER_INVALID;
    protected String _userPassword =  YNetwork.USERPASSWORD_INVALID;
    protected String _adminPassword =  YNetwork.ADMINPASSWORD_INVALID;
    protected int _httpPort =  YNetwork.HTTPPORT_INVALID;
    protected String _defaultPage =  YNetwork.DEFAULTPAGE_INVALID;
    protected int _discoverable =  YNetwork.DISCOVERABLE_INVALID;
    protected int _wwwWatchdogDelay =  YNetwork.WWWWATCHDOGDELAY_INVALID;
    protected String _callbackUrl =  YNetwork.CALLBACKURL_INVALID;
    protected int _callbackMethod =  YNetwork.CALLBACKMETHOD_INVALID;
    protected int _callbackEncoding =  YNetwork.CALLBACKENCODING_INVALID;
    protected String _callbackCredentials =  YNetwork.CALLBACKCREDENTIALS_INVALID;
    protected int _callbackInitialDelay =  YNetwork.CALLBACKINITIALDELAY_INVALID;
    protected int _callbackMinDelay =  YNetwork.CALLBACKMINDELAY_INVALID;
    protected int _callbackMaxDelay =  YNetwork.CALLBACKMAXDELAY_INVALID;
    protected int _poeCurrent =  YNetwork.POECURRENT_INVALID;
    protected YNetwork _ynetwork;

    public Network(YNetwork yfunc)
    {
       super(yfunc);
       _ynetwork = yfunc;
    }

    public Network(String hwid)
    {
       super(hwid);
       _ynetwork = YNetwork.FindNetwork(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _readiness = _ynetwork.get_readiness();
        _macAddress = _ynetwork.get_macAddress();
        _ipAddress = _ynetwork.get_ipAddress();
        _subnetMask = _ynetwork.get_subnetMask();
        _router = _ynetwork.get_router();
        _ipConfig = _ynetwork.get_ipConfig();
        _primaryDNS = _ynetwork.get_primaryDNS();
        _secondaryDNS = _ynetwork.get_secondaryDNS();
        _ntpServer = _ynetwork.get_ntpServer();
        _userPassword = _ynetwork.get_userPassword();
        _adminPassword = _ynetwork.get_adminPassword();
        _httpPort = _ynetwork.get_httpPort();
        _defaultPage = _ynetwork.get_defaultPage();
        _discoverable = _ynetwork.get_discoverable();
        _wwwWatchdogDelay = _ynetwork.get_wwwWatchdogDelay();
        _callbackUrl = _ynetwork.get_callbackUrl();
        _callbackMethod = _ynetwork.get_callbackMethod();
        _callbackEncoding = _ynetwork.get_callbackEncoding();
        _callbackCredentials = _ynetwork.get_callbackCredentials();
        _callbackInitialDelay = _ynetwork.get_callbackInitialDelay();
        _callbackMinDelay = _ynetwork.get_callbackMinDelay();
        _callbackMaxDelay = _ynetwork.get_callbackMaxDelay();
        _poeCurrent = _ynetwork.get_poeCurrent();
    }
    /**
     * Returns the current established working mode of the network interface.
     * Level zero (DOWN_0) means that no hardware link has been detected. Either there is no signal
     * on the network cable, or the selected wireless access point cannot be detected.
     * Level 1 (LIVE_1) is reached when the network is detected, but is not yet connected.
     * For a wireless network, this shows that the requested SSID is present.
     * Level 2 (LINK_2) is reached when the hardware connection is established.
     * For a wired network connection, level 2 means that the cable is attached at both ends.
     * For a connection to a wireless access point, it shows that the security parameters
     * are properly configured. For an ad-hoc wireless connection, it means that there is
     * at least one other device connected on the ad-hoc network.
     * Level 3 (DHCP_3) is reached when an IP address has been obtained using DHCP.
     * Level 4 (DNS_4) is reached when the DNS server is reachable on the network.
     * Level 5 (WWW_5) is reached when global connectivity is demonstrated by properly loading the
     * current time from an NTP server.
     *
     * @return a value among Y_READINESS_DOWN, Y_READINESS_EXISTS, Y_READINESS_LINKED, Y_READINESS_LAN_OK
     * and Y_READINESS_WWW_OK corresponding to the current established working mode of the network interface
     *
     * On failure, throws an exception or returns Y_READINESS_INVALID.
     */
    public int getReadiness()
    {
        return _readiness;
    }

    /**
     * Returns the MAC address of the network interface. The MAC address is also available on a sticker
     * on the module, in both numeric and barcode forms.
     *
     * @return a string corresponding to the MAC address of the network interface
     *
     * On failure, throws an exception or returns Y_MACADDRESS_INVALID.
     */
    public String getMacAddress()
    {
        return _macAddress;
    }

    /**
     * Returns the IP address currently in use by the device. The address may have been configured
     * statically, or provided by a DHCP server.
     *
     * @return a string corresponding to the IP address currently in use by the device
     *
     * On failure, throws an exception or returns Y_IPADDRESS_INVALID.
     */
    public String getIpAddress()
    {
        return _ipAddress;
    }

    /**
     * Returns the subnet mask currently used by the device.
     *
     * @return a string corresponding to the subnet mask currently used by the device
     *
     * On failure, throws an exception or returns Y_SUBNETMASK_INVALID.
     */
    public String getSubnetMask()
    {
        return _subnetMask;
    }

    /**
     * Returns the IP address of the router on the device subnet (default gateway).
     *
     * @return a string corresponding to the IP address of the router on the device subnet (default gateway)
     *
     * On failure, throws an exception or returns Y_ROUTER_INVALID.
     */
    public String getRouter()
    {
        return _router;
    }

    public String getIpConfig()
    {
        return _ipConfig;
    }

    public void setIpConfigBg(String newval) throws YAPI_Exception
    {
        _ipConfig = newval;
        _ynetwork.set_ipConfig(newval);
    }

    /**
     * Returns the IP address of the primary name server to be used by the module.
     *
     * @return a string corresponding to the IP address of the primary name server to be used by the module
     *
     * On failure, throws an exception or returns Y_PRIMARYDNS_INVALID.
     */
    public String getPrimaryDNS()
    {
        return _primaryDNS;
    }

    /**
     * Changes the IP address of the primary name server to be used by the module.
     * When using DHCP, if a value is specified, it overrides the value received from the DHCP server.
     * Remember to call the saveToFlash() method and then to reboot the module to apply this setting.
     *
     * @param newval : a string corresponding to the IP address of the primary name server to be used by the module
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setPrimaryDNSBg(String newval) throws YAPI_Exception
    {
        _primaryDNS = newval;
        _ynetwork.set_primaryDNS(newval);
    }

    /**
     * Returns the IP address of the secondary name server to be used by the module.
     *
     * @return a string corresponding to the IP address of the secondary name server to be used by the module
     *
     * On failure, throws an exception or returns Y_SECONDARYDNS_INVALID.
     */
    public String getSecondaryDNS()
    {
        return _secondaryDNS;
    }

    /**
     * Changes the IP address of the secondary name server to be used by the module.
     * When using DHCP, if a value is specified, it overrides the value received from the DHCP server.
     * Remember to call the saveToFlash() method and then to reboot the module to apply this setting.
     *
     * @param newval : a string corresponding to the IP address of the secondary name server to be used by the module
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setSecondaryDNSBg(String newval) throws YAPI_Exception
    {
        _secondaryDNS = newval;
        _ynetwork.set_secondaryDNS(newval);
    }

    /**
     * Returns the IP address of the NTP server to be used by the device.
     *
     * @return a string corresponding to the IP address of the NTP server to be used by the device
     *
     * On failure, throws an exception or returns Y_NTPSERVER_INVALID.
     */
    public String getNtpServer()
    {
        return _ntpServer;
    }

    /**
     * Changes the IP address of the NTP server to be used by the module.
     * Remember to call the saveToFlash() method and then to reboot the module to apply this setting.
     *
     * @param newval : a string corresponding to the IP address of the NTP server to be used by the module
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setNtpServerBg(String newval) throws YAPI_Exception
    {
        _ntpServer = newval;
        _ynetwork.set_ntpServer(newval);
    }

    /**
     * Returns a hash string if a password has been set for "user" user,
     * or an empty string otherwise.
     *
     * @return a string corresponding to a hash string if a password has been set for "user" user,
     *         or an empty string otherwise
     *
     * On failure, throws an exception or returns Y_USERPASSWORD_INVALID.
     */
    public String getUserPassword()
    {
        return _userPassword;
    }

    /**
     * Changes the password for the "user" user. This password becomes instantly required
     * to perform any use of the module. If the specified value is an
     * empty string, a password is not required anymore.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : a string corresponding to the password for the "user" user
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setUserPasswordBg(String newval) throws YAPI_Exception
    {
        _userPassword = newval;
        _ynetwork.set_userPassword(newval);
    }

    /**
     * Returns a hash string if a password has been set for user "admin",
     * or an empty string otherwise.
     *
     * @return a string corresponding to a hash string if a password has been set for user "admin",
     *         or an empty string otherwise
     *
     * On failure, throws an exception or returns Y_ADMINPASSWORD_INVALID.
     */
    public String getAdminPassword()
    {
        return _adminPassword;
    }

    /**
     * Changes the password for the "admin" user. This password becomes instantly required
     * to perform any change of the module state. If the specified value is an
     * empty string, a password is not required anymore.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : a string corresponding to the password for the "admin" user
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setAdminPasswordBg(String newval) throws YAPI_Exception
    {
        _adminPassword = newval;
        _ynetwork.set_adminPassword(newval);
    }

    /**
     * Returns the HTML page to serve for the URL "/"" of the hub.
     *
     * @return an integer corresponding to the HTML page to serve for the URL "/"" of the hub
     *
     * On failure, throws an exception or returns Y_HTTPPORT_INVALID.
     */
    public int getHttpPort()
    {
        return _httpPort;
    }

    /**
     * Changes the default HTML page returned by the hub. If not value are set the hub return
     * "index.html" which is the web interface of the hub. It is possible de change this page
     * for file that has been uploaded on the hub.
     *
     * @param newval : an integer corresponding to the default HTML page returned by the hub
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setHttpPortBg(int newval) throws YAPI_Exception
    {
        _httpPort = newval;
        _ynetwork.set_httpPort(newval);
    }

    /**
     * Returns the HTML page to serve for the URL "/"" of the hub.
     *
     * @return a string corresponding to the HTML page to serve for the URL "/"" of the hub
     *
     * On failure, throws an exception or returns Y_DEFAULTPAGE_INVALID.
     */
    public String getDefaultPage()
    {
        return _defaultPage;
    }

    /**
     * Changes the default HTML page returned by the hub. If not value are set the hub return
     * "index.html" which is the web interface of the hub. It is possible de change this page
     * for file that has been uploaded on the hub.
     *
     * @param newval : a string corresponding to the default HTML page returned by the hub
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setDefaultPageBg(String newval) throws YAPI_Exception
    {
        _defaultPage = newval;
        _ynetwork.set_defaultPage(newval);
    }

    /**
     * Returns the activation state of the multicast announce protocols to allow easy
     * discovery of the module in the network neighborhood (uPnP/Bonjour protocol).
     *
     * @return either Y_DISCOVERABLE_FALSE or Y_DISCOVERABLE_TRUE, according to the activation state of
     * the multicast announce protocols to allow easy
     *         discovery of the module in the network neighborhood (uPnP/Bonjour protocol)
     *
     * On failure, throws an exception or returns Y_DISCOVERABLE_INVALID.
     */
    public int getDiscoverable()
    {
        return _discoverable;
    }

    /**
     * Changes the activation state of the multicast announce protocols to allow easy
     * discovery of the module in the network neighborhood (uPnP/Bonjour protocol).
     *
     * @param newval : either Y_DISCOVERABLE_FALSE or Y_DISCOVERABLE_TRUE, according to the activation
     * state of the multicast announce protocols to allow easy
     *         discovery of the module in the network neighborhood (uPnP/Bonjour protocol)
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setDiscoverableBg(int newval) throws YAPI_Exception
    {
        _discoverable = newval;
        _ynetwork.set_discoverable(newval);
    }

    /**
     * Returns the allowed downtime of the WWW link (in seconds) before triggering an automated
     * reboot to try to recover Internet connectivity. A zero value disables automated reboot
     * in case of Internet connectivity loss.
     *
     * @return an integer corresponding to the allowed downtime of the WWW link (in seconds) before
     * triggering an automated
     *         reboot to try to recover Internet connectivity
     *
     * On failure, throws an exception or returns Y_WWWWATCHDOGDELAY_INVALID.
     */
    public int getWwwWatchdogDelay()
    {
        return _wwwWatchdogDelay;
    }

    /**
     * Changes the allowed downtime of the WWW link (in seconds) before triggering an automated
     * reboot to try to recover Internet connectivity. A zero value disables automated reboot
     * in case of Internet connectivity loss. The smallest valid non-zero timeout is
     * 90 seconds.
     *
     * @param newval : an integer corresponding to the allowed downtime of the WWW link (in seconds)
     * before triggering an automated
     *         reboot to try to recover Internet connectivity
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setWwwWatchdogDelayBg(int newval) throws YAPI_Exception
    {
        _wwwWatchdogDelay = newval;
        _ynetwork.set_wwwWatchdogDelay(newval);
    }

    /**
     * Returns the callback URL to notify of significant state changes.
     *
     * @return a string corresponding to the callback URL to notify of significant state changes
     *
     * On failure, throws an exception or returns Y_CALLBACKURL_INVALID.
     */
    public String getCallbackUrl()
    {
        return _callbackUrl;
    }

    /**
     * Changes the callback URL to notify significant state changes. Remember to call the
     * saveToFlash() method of the module if the modification must be kept.
     *
     * @param newval : a string corresponding to the callback URL to notify significant state changes
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setCallbackUrlBg(String newval) throws YAPI_Exception
    {
        _callbackUrl = newval;
        _ynetwork.set_callbackUrl(newval);
    }

    /**
     * Returns the HTTP method used to notify callbacks for significant state changes.
     *
     * @return a value among Y_CALLBACKMETHOD_POST, Y_CALLBACKMETHOD_GET and Y_CALLBACKMETHOD_PUT
     * corresponding to the HTTP method used to notify callbacks for significant state changes
     *
     * On failure, throws an exception or returns Y_CALLBACKMETHOD_INVALID.
     */
    public int getCallbackMethod()
    {
        return _callbackMethod;
    }

    /**
     * Changes the HTTP method used to notify callbacks for significant state changes.
     *
     * @param newval : a value among Y_CALLBACKMETHOD_POST, Y_CALLBACKMETHOD_GET and Y_CALLBACKMETHOD_PUT
     * corresponding to the HTTP method used to notify callbacks for significant state changes
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setCallbackMethodBg(int newval) throws YAPI_Exception
    {
        _callbackMethod = newval;
        _ynetwork.set_callbackMethod(newval);
    }

    /**
     * Returns the encoding standard to use for representing notification values.
     *
     * @return a value among Y_CALLBACKENCODING_FORM, Y_CALLBACKENCODING_JSON,
     * Y_CALLBACKENCODING_JSON_ARRAY, Y_CALLBACKENCODING_CSV, Y_CALLBACKENCODING_YOCTO_API,
     * Y_CALLBACKENCODING_JSON_NUM, Y_CALLBACKENCODING_EMONCMS, Y_CALLBACKENCODING_AZURE,
     * Y_CALLBACKENCODING_INFLUXDB and Y_CALLBACKENCODING_MQTT corresponding to the encoding standard to
     * use for representing notification values
     *
     * On failure, throws an exception or returns Y_CALLBACKENCODING_INVALID.
     */
    public int getCallbackEncoding()
    {
        return _callbackEncoding;
    }

    /**
     * Changes the encoding standard to use for representing notification values.
     *
     * @param newval : a value among Y_CALLBACKENCODING_FORM, Y_CALLBACKENCODING_JSON,
     * Y_CALLBACKENCODING_JSON_ARRAY, Y_CALLBACKENCODING_CSV, Y_CALLBACKENCODING_YOCTO_API,
     * Y_CALLBACKENCODING_JSON_NUM, Y_CALLBACKENCODING_EMONCMS, Y_CALLBACKENCODING_AZURE,
     * Y_CALLBACKENCODING_INFLUXDB and Y_CALLBACKENCODING_MQTT corresponding to the encoding standard to
     * use for representing notification values
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setCallbackEncodingBg(int newval) throws YAPI_Exception
    {
        _callbackEncoding = newval;
        _ynetwork.set_callbackEncoding(newval);
    }

    /**
     * Returns a hashed version of the notification callback credentials if set,
     * or an empty string otherwise.
     *
     * @return a string corresponding to a hashed version of the notification callback credentials if set,
     *         or an empty string otherwise
     *
     * On failure, throws an exception or returns Y_CALLBACKCREDENTIALS_INVALID.
     */
    public String getCallbackCredentials()
    {
        return _callbackCredentials;
    }

    /**
     * Changes the credentials required to connect to the callback address. The credentials
     * must be provided as returned by function get_callbackCredentials,
     * in the form username:hash. The method used to compute the hash varies according
     * to the the authentication scheme implemented by the callback, For Basic authentication,
     * the hash is the MD5 of the string username:password. For Digest authentication,
     * the hash is the MD5 of the string username:realm:password. For a simpler
     * way to configure callback credentials, use function callbackLogin instead.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : a string corresponding to the credentials required to connect to the callback address
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setCallbackCredentialsBg(String newval) throws YAPI_Exception
    {
        _callbackCredentials = newval;
        _ynetwork.set_callbackCredentials(newval);
    }

    /**
     * Returns the initial waiting time before first callback notifications, in seconds.
     *
     * @return an integer corresponding to the initial waiting time before first callback notifications, in seconds
     *
     * On failure, throws an exception or returns Y_CALLBACKINITIALDELAY_INVALID.
     */
    public int getCallbackInitialDelay()
    {
        return _callbackInitialDelay;
    }

    /**
     * Changes the initial waiting time before first callback notifications, in seconds.
     *
     * @param newval : an integer corresponding to the initial waiting time before first callback
     * notifications, in seconds
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setCallbackInitialDelayBg(int newval) throws YAPI_Exception
    {
        _callbackInitialDelay = newval;
        _ynetwork.set_callbackInitialDelay(newval);
    }

    /**
     * Returns the minimum waiting time between two callback notifications, in seconds.
     *
     * @return an integer corresponding to the minimum waiting time between two callback notifications, in seconds
     *
     * On failure, throws an exception or returns Y_CALLBACKMINDELAY_INVALID.
     */
    public int getCallbackMinDelay()
    {
        return _callbackMinDelay;
    }

    /**
     * Changes the minimum waiting time between two callback notifications, in seconds.
     *
     * @param newval : an integer corresponding to the minimum waiting time between two callback
     * notifications, in seconds
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setCallbackMinDelayBg(int newval) throws YAPI_Exception
    {
        _callbackMinDelay = newval;
        _ynetwork.set_callbackMinDelay(newval);
    }

    /**
     * Returns the maximum waiting time between two callback notifications, in seconds.
     *
     * @return an integer corresponding to the maximum waiting time between two callback notifications, in seconds
     *
     * On failure, throws an exception or returns Y_CALLBACKMAXDELAY_INVALID.
     */
    public int getCallbackMaxDelay()
    {
        return _callbackMaxDelay;
    }

    /**
     * Changes the maximum waiting time between two callback notifications, in seconds.
     *
     * @param newval : an integer corresponding to the maximum waiting time between two callback
     * notifications, in seconds
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setCallbackMaxDelayBg(int newval) throws YAPI_Exception
    {
        _callbackMaxDelay = newval;
        _ynetwork.set_callbackMaxDelay(newval);
    }

    /**
     * Returns the current consumed by the module from Power-over-Ethernet (PoE), in milli-amps.
     * The current consumption is measured after converting PoE source to 5 Volt, and should
     * never exceed 1800 mA.
     *
     * @return an integer corresponding to the current consumed by the module from Power-over-Ethernet
     * (PoE), in milli-amps
     *
     * On failure, throws an exception or returns Y_POECURRENT_INVALID.
     */
    public int getPoeCurrent()
    {
        return _poeCurrent;
    }

    public static YNetwork FindNetwork(String func)
    {
        return YNetwork.FindNetwork(func);
    }

    public int useDHCP(String fallbackIpAddr, int fallbackSubnetMaskLen, String fallbackRouter) throws YAPI_Exception
    {
        return _ynetwork.useDHCP(fallbackIpAddr, fallbackSubnetMaskLen, fallbackRouter);
    }

    public int useStaticIP(String ipAddress, int subnetMaskLen, String router) throws YAPI_Exception
    {
        return _ynetwork.useStaticIP(ipAddress, subnetMaskLen, router);
    }

    public String ping(String host) throws YAPI_Exception
    {
        return _ynetwork.ping(host);
    }

    public int triggerCallback() throws YAPI_Exception
    {
        return _ynetwork.triggerCallback();
    }

//--- (end of YNetwork class start)
}

