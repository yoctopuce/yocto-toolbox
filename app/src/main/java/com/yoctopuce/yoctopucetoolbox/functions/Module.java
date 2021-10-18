package com.yoctopuce.yoctopucetoolbox.functions;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YFirmwareUpdate;
import com.yoctopuce.YoctoAPI.YModule;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

//--- (generated code: YModule class start)

/**
 * YModule Class: Module control interface
 * <p>
 * This interface is identical for all Yoctopuce USB modules.
 * It can be used to control the module global parameters, and
 * to enumerate the functions provided by each module.
 */
@SuppressWarnings("UnusedDeclaration")
public class Module extends Function
{
    // valueCallbackModule
// logCallback
// confChangeCallback
// beaconCallback
    protected String _productName = YModule.PRODUCTNAME_INVALID;
    protected String _serialNumber = YModule.SERIALNUMBER_INVALID;
    protected int _productId = YModule.PRODUCTID_INVALID;
    protected int _productRelease = YModule.PRODUCTRELEASE_INVALID;
    protected String _firmwareRelease = YModule.FIRMWARERELEASE_INVALID;
    protected int _persistentSettings = YModule.PERSISTENTSETTINGS_INVALID;
    protected int _luminosity = YModule.LUMINOSITY_INVALID;
    protected int _beacon = YModule.BEACON_INVALID;
    protected long _upTime = YModule.UPTIME_INVALID;
    protected int _usbCurrent = YModule.USBCURRENT_INVALID;
    protected int _rebootCountdown = YModule.REBOOTCOUNTDOWN_INVALID;
    protected int _userVar = YModule.USERVAR_INVALID;
    protected YModule _ymodule;
    private ArrayList<Function> _functions = new ArrayList<>(3);

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

    public void reloadBg() throws YAPI_Exception
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
     * <p>
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
     * <p>
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
     * <p>
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
     * <p>
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
     * <p>
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
     * <p>
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
     * <p>
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
     * @return YAPI_SUCCESS if the call succeeds.
     * <p>
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
     * <p>
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
     * @return YAPI_SUCCESS if the call succeeds.
     * <p>
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
     * <p>
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
     * <p>
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
     * reboot has been scheduled
     * <p>
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
     * <p>
     * On failure, throws an exception or returns Y_USERVAR_INVALID.
     */
    public int getUserVar()
    {
        return _userVar;
    }

    /**
     * Stores a 32 bit value in the device RAM. This attribute is at programmer disposal,
     * should he need to store a state variable.
     * On startup and after a device reboot, the value is always reset to zero.
     *
     * @param newval : an integer
     * @return YAPI_SUCCESS if the call succeeds.
     * <p>
     * On failure, throws an exception or returns a negative error code.
     */
    public void setUserVarBg(int newval) throws YAPI_Exception
    {
        _userVar = newval;
        _ymodule.set_userVar(newval);
    }

    public static YModule FindModule(String func)
    {
        return YModule.FindModule(func);
    }

    public int saveToFlash() throws YAPI_Exception
    {
        return _ymodule.saveToFlash();
    }

    public int revertFromFlash() throws YAPI_Exception
    {
        return _ymodule.revertFromFlash();
    }

    public int reboot(int secBeforeReboot) throws YAPI_Exception
    {
        return _ymodule.reboot(secBeforeReboot);
    }

    public int triggerFirmwareUpdate(int secBeforeReboot) throws YAPI_Exception
    {
        return _ymodule.triggerFirmwareUpdate(secBeforeReboot);
    }


    public String checkFirmware(String path, boolean onlynew) throws YAPI_Exception
    {
        return _ymodule.checkFirmware(path, onlynew);
    }

    public YFirmwareUpdate updateFirmwareEx(String path, boolean force) throws YAPI_Exception
    {
        return _ymodule.updateFirmwareEx(path, force);
    }

    public YFirmwareUpdate updateFirmware(String path) throws YAPI_Exception
    {
        return _ymodule.updateFirmware(path);
    }

    public byte[] get_allSettings() throws YAPI_Exception
    {
        return _ymodule.get_allSettings();
    }

    public int loadThermistorExtra(String funcId, String jsonExtra) throws YAPI_Exception
    {
        return _ymodule.loadThermistorExtra(funcId, jsonExtra);
    }

    public int set_extraSettings(String jsonExtra) throws YAPI_Exception
    {
        return _ymodule.set_extraSettings(jsonExtra);
    }

    public int set_allSettingsAndFiles(byte[] settings) throws YAPI_Exception
    {
        return _ymodule.set_allSettingsAndFiles(settings);
    }

    public boolean hasFunction(String funcId) throws YAPI_Exception
    {
        return _ymodule.hasFunction(funcId);
    }

    public ArrayList<String> get_functionIds(String funType) throws YAPI_Exception
    {
        return _ymodule.get_functionIds(funType);
    }

    public int calibVersion(String cparams)
    {
        return _ymodule.calibVersion(cparams);
    }

    public int calibScale(String unit_name, String sensorType)
    {
        return _ymodule.calibScale(unit_name, sensorType);
    }

    public int calibOffset(String unit_name)
    {
        return _ymodule.calibOffset(unit_name);
    }

    public String calibConvert(String param, String currentFuncValue, String unit_name, String sensorType)
    {
        return _ymodule.calibConvert(param, currentFuncValue, unit_name, sensorType);
    }

    public int set_allSettings(byte[] settings) throws YAPI_Exception
    {
        return _ymodule.set_allSettings(settings);
    }

    public byte[] download(String pathname) throws YAPI_Exception
    {
        return _ymodule.download(pathname);
    }

    public byte[] get_icon2d() throws YAPI_Exception
    {
        return _ymodule.get_icon2d();
    }

    public String get_lastLogs() throws YAPI_Exception
    {
        return _ymodule.get_lastLogs();
    }

    public int log(String text) throws YAPI_Exception
    {
        return _ymodule.log(text);
    }

    public ArrayList<String> get_subDevices() throws YAPI_Exception
    {
        return _ymodule.get_subDevices();
    }

    public String get_parentHub() throws YAPI_Exception
    {
        return _ymodule.get_parentHub();
    }

    public String get_url() throws YAPI_Exception
    {
        return _ymodule.get_url();
    }

//--- (end of generated code: YModule class start)


    public int functionCountBg() throws YAPI_Exception
    {
        return _ymodule.functionCount();
    }

    public String functionIdBg(int index) throws YAPI_Exception
    {
        return _ymodule.functionId(index);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void loadAllFunctionsBG() throws YAPI_Exception
    {
        int count = _ymodule.functionCount();
        for (int i = 0; i < count; i++) {
            String hwid = _serialNumber + "." + _ymodule.functionId(i);
            String type = "com.yoctopuce.yoctopucetoolbox.functions."+_ymodule.functionType(i);
            Function func;
            try {
                Class<?> aClass = Class.forName(type);
                Constructor<?> constr = aClass.getConstructor(String.class);
                func = (Function) constr.newInstance(hwid);
            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
                func = new Function(hwid);
            }
            _functions.add(func);
        }

    }
}

