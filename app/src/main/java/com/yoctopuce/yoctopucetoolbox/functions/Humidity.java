/*********************************************************************
 *
 * $Id: pic24config.php 26169 2016-12-12 01:36:34Z mvuilleu $
 *
 * Implements Humidity wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YHumidity;

//--- (YHumidity class start)
/**
 * YHumidity Class: Humidity function interface
 *
 * The Yoctopuce class YHumidity allows you to read and configure Yoctopuce humidity
 * sensors. It inherits from YSensor class the core functions to read measurements,
 * register callback functions, access to the autonomous datalogger.
 */
 @SuppressWarnings("UnusedDeclaration")
public class Humidity extends Sensor
{
// valueCallbackHumidity
// timedReportCallbackHumidity
    protected double _relHum =  YHumidity.RELHUM_INVALID;
    protected double _absHum =  YHumidity.ABSHUM_INVALID;
    protected YHumidity _yhumidity;

    public Humidity(YHumidity yfunc)
    {
       super(yfunc);
       _yhumidity = yfunc;
    }

    public Humidity(String hwid)
    {
       super(hwid);
       _yhumidity = YHumidity.FindHumidity(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _relHum = _yhumidity.get_relHum();
        _absHum = _yhumidity.get_absHum();
    }
    /**
     * Changes the primary unit for measuring humidity. That unit is a string.
     * If that strings starts with the letter 'g', the primary measured value is the absolute
     * humidity, in g/m3. Otherwise, the primary measured value will be the relative humidity
     * (RH), in per cents.
     *
     * Remember to call the saveToFlash() method of the module if the modification
     * must be kept.
     *
     * @param newval : a string corresponding to the primary unit for measuring humidity
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setUnitBg(String newval) throws YAPI_Exception
    {
        _unit = newval;
        _yhumidity.set_unit(newval);
    }

    /**
     * Returns the current relative humidity, in per cents.
     *
     * @return a floating point number corresponding to the current relative humidity, in per cents
     *
     * On failure, throws an exception or returns Y_RELHUM_INVALID.
     */
    public double getRelHum()
    {
        return _relHum;
    }

    /**
     * Returns the current absolute humidity, in grams per cubic meter of air.
     *
     * @return a floating point number corresponding to the current absolute humidity, in grams per cubic meter of air
     *
     * On failure, throws an exception or returns Y_ABSHUM_INVALID.
     */
    public double getAbsHum()
    {
        return _absHum;
    }

    public static YHumidity FindHumidity(String func)
    {
        return YHumidity.FindHumidity(func);
    }

//--- (end of YHumidity class start)
}

