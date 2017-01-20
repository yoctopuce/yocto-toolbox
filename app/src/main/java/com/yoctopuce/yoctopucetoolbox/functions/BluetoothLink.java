/*********************************************************************
 *
 * $Id: pic24config.php 26169 2016-12-12 01:36:34Z mvuilleu $
 *
 * Implements BluetoothLink wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YBluetoothLink;

//--- (YBluetoothLink class start)
/**
 * YBluetoothLink Class: BluetoothLink function interface
 *
 * BluetoothLink function provides control over bluetooth link
 * and status for devices that are bluetooth-enabled.
 */
 @SuppressWarnings("UnusedDeclaration")
public class BluetoothLink extends Function
{
// valueCallbackBluetoothLink
    protected String _ownAddress =  YBluetoothLink.OWNADDRESS_INVALID;
    protected String _pairingPin =  YBluetoothLink.PAIRINGPIN_INVALID;
    protected String _remoteAddress =  YBluetoothLink.REMOTEADDRESS_INVALID;
    protected String _remoteName =  YBluetoothLink.REMOTENAME_INVALID;
    protected int _mute =  YBluetoothLink.MUTE_INVALID;
    protected int _preAmplifier =  YBluetoothLink.PREAMPLIFIER_INVALID;
    protected int _volume =  YBluetoothLink.VOLUME_INVALID;
    protected int _linkState =  YBluetoothLink.LINKSTATE_INVALID;
    protected int _linkQuality =  YBluetoothLink.LINKQUALITY_INVALID;
    protected String _command =  YBluetoothLink.COMMAND_INVALID;
    protected YBluetoothLink _ybluetoothlink;

    public BluetoothLink(YBluetoothLink yfunc)
    {
       super(yfunc);
       _ybluetoothlink = yfunc;
    }

    public BluetoothLink(String hwid)
    {
       super(hwid);
       _ybluetoothlink = YBluetoothLink.FindBluetoothLink(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _ownAddress = _ybluetoothlink.get_ownAddress();
        _pairingPin = _ybluetoothlink.get_pairingPin();
        _remoteAddress = _ybluetoothlink.get_remoteAddress();
        _remoteName = _ybluetoothlink.get_remoteName();
        _mute = _ybluetoothlink.get_mute();
        _preAmplifier = _ybluetoothlink.get_preAmplifier();
        _volume = _ybluetoothlink.get_volume();
        _linkState = _ybluetoothlink.get_linkState();
        _linkQuality = _ybluetoothlink.get_linkQuality();
        _command = _ybluetoothlink.get_command();
    }
    /**
     * Returns the MAC-48 address of the bluetooth interface, which is unique on the bluetooth network.
     *
     * @return a string corresponding to the MAC-48 address of the bluetooth interface, which is unique on
     * the bluetooth network
     *
     * On failure, throws an exception or returns Y_OWNADDRESS_INVALID.
     */
    public String getOwnAddress()
    {
        return _ownAddress;
    }

    /**
     * Returns an opaque string if a PIN code has been configured in the device to access
     * the SIM card, or an empty string if none has been configured or if the code provided
     * was rejected by the SIM card.
     *
     * @return a string corresponding to an opaque string if a PIN code has been configured in the device to access
     *         the SIM card, or an empty string if none has been configured or if the code provided
     *         was rejected by the SIM card
     *
     * On failure, throws an exception or returns Y_PAIRINGPIN_INVALID.
     */
    public String getPairingPin()
    {
        return _pairingPin;
    }

    /**
     * Changes the PIN code used by the module for bluetooth pairing.
     * Remember to call the saveToFlash() method of the module to save the
     * new value in the device flash.
     *
     * @param newval : a string corresponding to the PIN code used by the module for bluetooth pairing
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setPairingPinBg(String newval) throws YAPI_Exception
    {
        _pairingPin = newval;
        _ybluetoothlink.set_pairingPin(newval);
    }

    /**
     * Returns the MAC-48 address of the remote device to connect to.
     *
     * @return a string corresponding to the MAC-48 address of the remote device to connect to
     *
     * On failure, throws an exception or returns Y_REMOTEADDRESS_INVALID.
     */
    public String getRemoteAddress()
    {
        return _remoteAddress;
    }

    /**
     * Changes the MAC-48 address defining which remote device to connect to.
     *
     * @param newval : a string corresponding to the MAC-48 address defining which remote device to connect to
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setRemoteAddressBg(String newval) throws YAPI_Exception
    {
        _remoteAddress = newval;
        _ybluetoothlink.set_remoteAddress(newval);
    }

    /**
     * Returns the bluetooth name the remote device, if found on the bluetooth network.
     *
     * @return a string corresponding to the bluetooth name the remote device, if found on the bluetooth network
     *
     * On failure, throws an exception or returns Y_REMOTENAME_INVALID.
     */
    public String getRemoteName()
    {
        return _remoteName;
    }

    /**
     * Returns the state of the mute function.
     *
     * @return either Y_MUTE_FALSE or Y_MUTE_TRUE, according to the state of the mute function
     *
     * On failure, throws an exception or returns Y_MUTE_INVALID.
     */
    public int getMute()
    {
        return _mute;
    }

    /**
     * Changes the state of the mute function. Remember to call the matching module
     * saveToFlash() method to save the setting permanently.
     *
     * @param newval : either Y_MUTE_FALSE or Y_MUTE_TRUE, according to the state of the mute function
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setMuteBg(int newval) throws YAPI_Exception
    {
        _mute = newval;
        _ybluetoothlink.set_mute(newval);
    }

    /**
     * Returns the audio pre-amplifier volume, in per cents.
     *
     * @return an integer corresponding to the audio pre-amplifier volume, in per cents
     *
     * On failure, throws an exception or returns Y_PREAMPLIFIER_INVALID.
     */
    public int getPreAmplifier()
    {
        return _preAmplifier;
    }

    /**
     * Changes the audio pre-amplifier volume, in per cents.
     *
     * @param newval : an integer corresponding to the audio pre-amplifier volume, in per cents
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setPreAmplifierBg(int newval) throws YAPI_Exception
    {
        _preAmplifier = newval;
        _ybluetoothlink.set_preAmplifier(newval);
    }

    /**
     * Returns the connected headset volume, in per cents.
     *
     * @return an integer corresponding to the connected headset volume, in per cents
     *
     * On failure, throws an exception or returns Y_VOLUME_INVALID.
     */
    public int getVolume()
    {
        return _volume;
    }

    /**
     * Changes the connected headset volume, in per cents.
     *
     * @param newval : an integer corresponding to the connected headset volume, in per cents
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setVolumeBg(int newval) throws YAPI_Exception
    {
        _volume = newval;
        _ybluetoothlink.set_volume(newval);
    }

    /**
     * Returns the bluetooth link state.
     *
     * @return a value among Y_LINKSTATE_DOWN, Y_LINKSTATE_FREE, Y_LINKSTATE_SEARCH, Y_LINKSTATE_EXISTS,
     * Y_LINKSTATE_LINKED and Y_LINKSTATE_PLAY corresponding to the bluetooth link state
     *
     * On failure, throws an exception or returns Y_LINKSTATE_INVALID.
     */
    public int getLinkState()
    {
        return _linkState;
    }

    /**
     * Returns the bluetooth receiver signal strength, in pourcents, or 0 if no connection is established.
     *
     * @return an integer corresponding to the bluetooth receiver signal strength, in pourcents, or 0 if
     * no connection is established
     *
     * On failure, throws an exception or returns Y_LINKQUALITY_INVALID.
     */
    public int getLinkQuality()
    {
        return _linkQuality;
    }

    public String getCommand()
    {
        return _command;
    }

    public void setCommandBg(String newval) throws YAPI_Exception
    {
        _command = newval;
        _ybluetoothlink.set_command(newval);
    }

    public static YBluetoothLink FindBluetoothLink(String func)
    {
        return YBluetoothLink.FindBluetoothLink(func);
    }

    public int connect() throws YAPI_Exception
    {
        return _ybluetoothlink.connect();
    }

    public int disconnect() throws YAPI_Exception
    {
        return _ybluetoothlink.disconnect();
    }

//--- (end of YBluetoothLink class start)
}

