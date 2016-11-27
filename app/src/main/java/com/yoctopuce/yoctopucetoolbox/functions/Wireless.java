/*********************************************************************
 *
 * $Id: Wireless.java 26014 2016-11-24 13:52:08Z seb $
 *
 * Implements Wireless wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YAPI;
import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YWireless;

//--- (generated code: YWireless class start)
/**
 * YWireless Class: Wireless function interface
 *
 * YWireless functions provides control over wireless network parameters
 * and status for devices that are wireless-enabled.
 */
 @SuppressWarnings("UnusedDeclaration")
public class Wireless extends Function
{
// valueCallbackWireless
    protected int _linkQuality =  YWireless.LINKQUALITY_INVALID;
    protected String _ssid =  YWireless.SSID_INVALID;
    protected int _channel =  YWireless.CHANNEL_INVALID;
    protected int _security =  YWireless.SECURITY_INVALID;
    protected String _message =  YWireless.MESSAGE_INVALID;
    protected String _wlanConfig =  YWireless.WLANCONFIG_INVALID;
    protected YWireless _ywireless;

    public Wireless(YWireless yfunc)
    {
       super(yfunc);
       _ywireless = yfunc;
    }

    public Wireless(String hwid)
    {
       super(hwid);
       _ywireless = YWireless.FindWireless(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _linkQuality = _ywireless.get_linkQuality();
        _ssid = _ywireless.get_ssid();
        _channel = _ywireless.get_channel();
        _security = _ywireless.get_security();
        _message = _ywireless.get_message();
        _wlanConfig = _ywireless.get_wlanConfig();
    }
    /**
     * Returns the link quality, expressed in percent.
     *
     * @return an integer corresponding to the link quality, expressed in percent
     *
     * On failure, throws an exception or returns Y_LINKQUALITY_INVALID.
     */
    public int getLinkQuality()
    {
        return _linkQuality;
    }

    /**
     * Returns the wireless network name (SSID).
     *
     * @return a string corresponding to the wireless network name (SSID)
     *
     * On failure, throws an exception or returns Y_SSID_INVALID.
     */
    public String getSsid()
    {
        return _ssid;
    }

    /**
     * Returns the 802.11 channel currently used, or 0 when the selected network has not been found.
     *
     * @return an integer corresponding to the 802.11 channel currently used, or 0 when the selected
     * network has not been found
     *
     * On failure, throws an exception or returns Y_CHANNEL_INVALID.
     */
    public int getChannel()
    {
        return _channel;
    }

    /**
     * Returns the security algorithm used by the selected wireless network.
     *
     * @return a value among Y_SECURITY_UNKNOWN, Y_SECURITY_OPEN, Y_SECURITY_WEP, Y_SECURITY_WPA and
     * Y_SECURITY_WPA2 corresponding to the security algorithm used by the selected wireless network
     *
     * On failure, throws an exception or returns Y_SECURITY_INVALID.
     */
    public int getSecurity()
    {
        return _security;
    }

    /**
     * Returns the latest status message from the wireless interface.
     *
     * @return a string corresponding to the latest status message from the wireless interface
     *
     * On failure, throws an exception or returns Y_MESSAGE_INVALID.
     */
    public String getMessage()
    {
        return _message;
    }

    public String getWlanConfig()
    {
        return _wlanConfig;
    }

    public void setWlanConfigBg(String newval) throws YAPI_Exception
    {
        _wlanConfig = newval;
        _ywireless.set_wlanConfig(newval);
    }

//--- (end of generated code: YWireless class start)
}

