/*********************************************************************
 *
 * $Id: pic24config.php 26169 2016-12-12 01:36:34Z mvuilleu $
 *
 * Implements Temperature wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YTemperature;
import java.util.ArrayList;

//--- (YTemperature class start)
/**
 * YTemperature Class: Temperature function interface
 *
 * The Yoctopuce class YTemperature allows you to read and configure Yoctopuce temperature
 * sensors. It inherits from YSensor class the core functions to read measurements,
 * register callback functions, access to the autonomous datalogger.
 * This class adds the ability to configure some specific parameters for some
 * sensors (connection type, temperature mapping table).
 */
 @SuppressWarnings("UnusedDeclaration")
public class Temperature extends Sensor
{
// valueCallbackTemperature
// timedReportCallbackTemperature
    protected int _sensorType =  YTemperature.SENSORTYPE_INVALID;
    protected double _signalValue =  YTemperature.SIGNALVALUE_INVALID;
    protected String _signalUnit =  YTemperature.SIGNALUNIT_INVALID;
    protected String _command =  YTemperature.COMMAND_INVALID;
    protected YTemperature _ytemperature;

    public Temperature(YTemperature yfunc)
    {
       super(yfunc);
       _ytemperature = yfunc;
    }

    public Temperature(String hwid)
    {
       super(hwid);
       _ytemperature = YTemperature.FindTemperature(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _sensorType = _ytemperature.get_sensorType();
        _signalValue = _ytemperature.get_signalValue();
        _signalUnit = _ytemperature.get_signalUnit();
        _command = _ytemperature.get_command();
    }
    /**
     * Changes the measuring unit for the measured temperature. That unit is a string.
     * If that strings end with the letter F all temperatures values will returned in
     * Fahrenheit degrees. If that String ends with the letter K all values will be
     * returned in Kelvin degrees. If that string ends with the letter C all values will be
     * returned in Celsius degrees.  If the string ends with any other character the
     * change will be ignored. Remember to call the
     * saveToFlash() method of the module if the modification must be kept.
     * WARNING: if a specific calibration is defined for the temperature function, a
     * unit system change will probably break it.
     *
     * @param newval : a string corresponding to the measuring unit for the measured temperature
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setUnitBg(String newval) throws YAPI_Exception
    {
        _unit = newval;
        _ytemperature.set_unit(newval);
    }

    /**
     * Returns the temperature sensor type.
     *
     * @return a value among Y_SENSORTYPE_DIGITAL, Y_SENSORTYPE_TYPE_K, Y_SENSORTYPE_TYPE_E,
     * Y_SENSORTYPE_TYPE_J, Y_SENSORTYPE_TYPE_N, Y_SENSORTYPE_TYPE_R, Y_SENSORTYPE_TYPE_S,
     * Y_SENSORTYPE_TYPE_T, Y_SENSORTYPE_PT100_4WIRES, Y_SENSORTYPE_PT100_3WIRES,
     * Y_SENSORTYPE_PT100_2WIRES, Y_SENSORTYPE_RES_OHM, Y_SENSORTYPE_RES_NTC and Y_SENSORTYPE_RES_LINEAR
     * corresponding to the temperature sensor type
     *
     * On failure, throws an exception or returns Y_SENSORTYPE_INVALID.
     */
    public int getSensorType()
    {
        return _sensorType;
    }

    /**
     * Modifies the temperature sensor type.  This function is used
     * to define the type of thermocouple (K,E...) used with the device.
     * It has no effect if module is using a digital sensor or a thermistor.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : a value among Y_SENSORTYPE_DIGITAL, Y_SENSORTYPE_TYPE_K, Y_SENSORTYPE_TYPE_E,
     * Y_SENSORTYPE_TYPE_J, Y_SENSORTYPE_TYPE_N, Y_SENSORTYPE_TYPE_R, Y_SENSORTYPE_TYPE_S,
     * Y_SENSORTYPE_TYPE_T, Y_SENSORTYPE_PT100_4WIRES, Y_SENSORTYPE_PT100_3WIRES,
     * Y_SENSORTYPE_PT100_2WIRES, Y_SENSORTYPE_RES_OHM, Y_SENSORTYPE_RES_NTC and Y_SENSORTYPE_RES_LINEAR
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setSensorTypeBg(int newval) throws YAPI_Exception
    {
        _sensorType = newval;
        _ytemperature.set_sensorType(newval);
    }

    /**
     * Returns the current value of the electrical signal measured by the sensor.
     *
     * @return a floating point number corresponding to the current value of the electrical signal
     * measured by the sensor
     *
     * On failure, throws an exception or returns Y_SIGNALVALUE_INVALID.
     */
    public double getSignalValue()
    {
        return _signalValue;
    }

    /**
     * Returns the measuring unit of the electrical signal used by the sensor.
     *
     * @return a string corresponding to the measuring unit of the electrical signal used by the sensor
     *
     * On failure, throws an exception or returns Y_SIGNALUNIT_INVALID.
     */
    public String getSignalUnit()
    {
        return _signalUnit;
    }

    public String getCommand()
    {
        return _command;
    }

    public void setCommandBg(String newval) throws YAPI_Exception
    {
        _command = newval;
        _ytemperature.set_command(newval);
    }

    public static YTemperature FindTemperature(String func)
    {
        return YTemperature.FindTemperature(func);
    }

    public int set_ntcParameters(double res25, double beta) throws YAPI_Exception
    {
        return _ytemperature.set_ntcParameters(res25, beta);
    }

    public int set_thermistorResponseTable(ArrayList<Double> tempValues, ArrayList<Double> resValues) throws YAPI_Exception
    {
        return _ytemperature.set_thermistorResponseTable(tempValues, resValues);
    }

    public int loadThermistorResponseTable(ArrayList<Double> tempValues, ArrayList<Double> resValues) throws YAPI_Exception
    {
        return _ytemperature.loadThermistorResponseTable(tempValues, resValues);
    }

//--- (end of YTemperature class start)
}

