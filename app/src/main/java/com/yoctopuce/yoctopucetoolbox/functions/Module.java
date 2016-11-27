/*********************************************************************
 * $Id: Module.java 26014 2016-11-24 13:52:08Z seb $
 * <p>
 * Implements Relay wrapper for Android toolbox
 * <p>
 * - - - - - - - - - License information: - - - - - - - - -
 * <p>
 * Copyright (C) 2011 and beyond by Yoctopuce Sarl, Switzerland.
 * <p>
 * Yoctopuce Sarl (hereafter Licensor) grants to you a perpetual
 * non-exclusive license to use, modify, copy and integrate this
 * file into your software for the sole purpose of interfacing
 * with Yoctopuce products.
 * <p>
 * You may reproduce and distribute copies of this file in
 * source or object form, as long as the sole purpose of this
 * code is to interface with Yoctopuce products. You must retain
 * this notice in the distributed source file.
 * <p>
 * You should refer to Yoctopuce General Terms and Conditions
 * for additional information regarding your rights and
 * obligations.
 * <p>
 * THE SOFTWARE AND DOCUMENTATION ARE PROVIDED 'AS IS' WITHOUT
 * WARRANTY OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING
 * WITHOUT LIMITATION, ANY WARRANTY OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE, TITLE AND NON-INFRINGEMENT. IN NO
 * EVENT SHALL LICENSOR BE LIABLE FOR ANY INCIDENTAL, SPECIAL,
 * INDIRECT OR CONSEQUENTIAL DAMAGES, LOST PROFITS OR LOST DATA,
 * COST OF PROCUREMENT OF SUBSTITUTE GOODS, TECHNOLOGY OR
 * SERVICES, ANY CLAIMS BY THIRD PARTIES (INCLUDING BUT NOT
 * LIMITED TO ANY DEFENSE THEREOF), ANY CLAIMS FOR INDEMNITY OR
 * CONTRIBUTION, OR OTHER SIMILAR COSTS, WHETHER ASSERTED ON THE
 * BASIS OF CONTRACT, TORT (INCLUDING NEGLIGENCE), BREACH OF
 * WARRANTY, OR OTHERWISE.
 *********************************************************************/

package com.yoctopuce.yoctopucetoolbox.functions;

import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YModule;

//--- (generated code: YModule class start)
/**
 * YModule Class: Module control interface
 *
 * This interface is identical for all Yoctopuce USB modules.
 * It can be used to control the module global parameters, and
 * to enumerate the functions provided by each module.
 */
 @SuppressWarnings("UnusedDeclaration")
public class Module extends Function
{
// valueCallbackModule
// logCallback
    protected String _productName =  YModule.PRODUCTNAME_INVALID;
    protected String _serialNumber =  YModule.SERIALNUMBER_INVALID;
    protected int _productId =  YModule.PRODUCTID_INVALID;
    protected int _productRelease =  YModule.PRODUCTRELEASE_INVALID;
    protected String _firmwareRelease =  YModule.FIRMWARERELEASE_INVALID;
    protected int _persistentSettings =  YModule.PERSISTENTSETTINGS_INVALID;
    protected int _luminosity =  YModule.LUMINOSITY_INVALID;
    protected int _beacon =  YModule.BEACON_INVALID;
    protected long _upTime =  YModule.UPTIME_INVALID;
    protected int _usbCurrent =  YModule.USBCURRENT_INVALID;
    protected int _rebootCountdown =  YModule.REBOOTCOUNTDOWN_INVALID;
    protected int _userVar =  YModule.USERVAR_INVALID;
    protected YModule _ymodule;

    public Module(YModule yfunc)
    {
       super(yfunc);
       _ymodule = yfunc;
    }

    public Module(String hwid)
    {
       super(hwid);
       _ymodule = YModule.FindModule(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _productName = _ymodule.get_productName();
        _serialNumber = _ymodule.get_serialNumber();
        _productId = _ymodule.get_productId();
        _productRelease = _ymodule.get_productRelease();
        _firmwareRelease = _ymodule.get_firmwareRelease();
        _persistentSettings = _ymodule.get_persistentSettings();
        _luminosity = _ymodule.get_luminosity();
        _beacon = _ymodule.get_beacon();
        _upTime = _ymodule.get_upTime();
        _usbCurrent = _ymodule.get_usbCurrent();
        _rebootCountdown = _ymodule.get_rebootCountdown();
        _userVar = _ymodule.get_userVar();
    }
    /**
     * Returns the commercial name of the module, as set by the factory.
     *
     * @return a string corresponding to the commercial name of the module, as set by the factory
     *
     * On failure, throws an exception or returns Y_PRODUCTNAME_INVALID.
     */
    public String getProductName()
    {
        return _productName;
    }

    /**
     * Returns the serial number of the module, as set by the factory.
     *
     * @return a string corresponding to the serial number of the module, as set by the factory
     *
     * On failure, throws an exception or returns Y_SERIALNUMBER_INVALID.
     */
    public String getSerialNumber()
    {
        return _serialNumber;
    }

    /**
     * Returns the USB device identifier of the module.
     *
     * @return an integer corresponding to the USB device identifier of the module
     *
     * On failure, throws an exception or returns Y_PRODUCTID_INVALID.
     */
    public int getProductId()
    {
        return _productId;
    }

    /**
     * Returns the hardware release version of the module.
     *
     * @return an integer corresponding to the hardware release version of the module
     *
     * On failure, throws an exception or returns Y_PRODUCTRELEASE_INVALID.
     */
    public int getProductRelease()
    {
        return _productRelease;
    }

    /**
     * Returns the version of the firmware embedded in the module.
     *
     * @return a string corresponding to the version of the firmware embedded in the module
     *
     * On failure, throws an exception or returns Y_FIRMWARERELEASE_INVALID.
     */
    public String getFirmwareRelease()
    {
        return _firmwareRelease;
    }

    /**
     * Returns the current state of persistent module settings.
     *
     * @return a value among Y_PERSISTENTSETTINGS_LOADED, Y_PERSISTENTSETTINGS_SAVED and
     * Y_PERSISTENTSETTINGS_MODIFIED corresponding to the current state of persistent module settings
     *
     * On failure, throws an exception or returns Y_PERSISTENTSETTINGS_INVALID.
     */
    public int getPersistentSettings()
    {
        return _persistentSettings;
    }

    public void setPersistentSettingsBg(int newval) throws YAPI_Exception
    {
        _persistentSettings = newval;
        _ymodule.set_persistentSettings(newval);
    }

    /**
     * Returns the luminosity of the  module informative leds (from 0 to 100).
     *
     * @return an integer corresponding to the luminosity of the  module informative leds (from 0 to 100)
     *
     * On failure, throws an exception or returns Y_LUMINOSITY_INVALID.
     */
    public int getLuminosity()
    {
        return _luminosity;
    }

    /**
     * Changes the luminosity of the module informative leds. The parameter is a
     * value between 0 and 100.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : an integer corresponding to the luminosity of the module informative leds
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setLuminosityBg(int newval) throws YAPI_Exception
    {
        _luminosity = newval;
        _ymodule.set_luminosity(newval);
    }

    /**
     * Returns the state of the localization beacon.
     *
     * @return either Y_BEACON_OFF or Y_BEACON_ON, according to the state of the localization beacon
     *
     * On failure, throws an exception or returns Y_BEACON_INVALID.
     */
    public int getBeacon()
    {
        return _beacon;
    }

    /**
     * Turns on or off the module localization beacon.
     *
     * @param newval : either Y_BEACON_OFF or Y_BEACON_ON
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setBeaconBg(int newval) throws YAPI_Exception
    {
        _beacon = newval;
        _ymodule.set_beacon(newval);
    }

    /**
     * Returns the number of milliseconds spent since the module was powered on.
     *
     * @return an integer corresponding to the number of milliseconds spent since the module was powered on
     *
     * On failure, throws an exception or returns Y_UPTIME_INVALID.
     */
    public long getUpTime()
    {
        return _upTime;
    }

    /**
     * Returns the current consumed by the module on the USB bus, in milli-amps.
     *
     * @return an integer corresponding to the current consumed by the module on the USB bus, in milli-amps
     *
     * On failure, throws an exception or returns Y_USBCURRENT_INVALID.
     */
    public int getUsbCurrent()
    {
        return _usbCurrent;
    }

    /**
     * Returns the remaining number of seconds before the module restarts, or zero when no
     * reboot has been scheduled.
     *
     * @return an integer corresponding to the remaining number of seconds before the module restarts, or zero when no
     *         reboot has been scheduled
     *
     * On failure, throws an exception or returns Y_REBOOTCOUNTDOWN_INVALID.
     */
    public int getRebootCountdown()
    {
        return _rebootCountdown;
    }

    public void setRebootCountdownBg(int newval) throws YAPI_Exception
    {
        _rebootCountdown = newval;
        _ymodule.set_rebootCountdown(newval);
    }

    /**
     * Returns the value previously stored in this attribute.
     * On startup and after a device reboot, the value is always reset to zero.
     *
     * @return an integer corresponding to the value previously stored in this attribute
     *
     * On failure, throws an exception or returns Y_USERVAR_INVALID.
     */
    public int getUserVar()
    {
        return _userVar;
    }

    /**
     * Returns the value previously stored in this attribute.
     * On startup and after a device reboot, the value is always reset to zero.
     *
     * @param newval : an integer
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setUserVarBg(int newval) throws YAPI_Exception
    {
        _userVar = newval;
        _ymodule.set_userVar(newval);
    }

//--- (end of generated code: YModule class start)


}
