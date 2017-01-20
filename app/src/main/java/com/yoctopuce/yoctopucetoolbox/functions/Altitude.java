/*********************************************************************
 *
 * $Id: pic24config.php 26169 2016-12-12 01:36:34Z mvuilleu $
 *
 * Implements Altitude wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YAltitude;

//--- (YAltitude class start)
/**
 * YAltitude Class: Altitude function interface
 *
 * The Yoctopuce class YAltitude allows you to read and configure Yoctopuce altitude
 * sensors. It inherits from the YSensor class the core functions to read measurements,
 * register callback functions, access to the autonomous datalogger.
 * This class adds the ability to configure the barometric pressure adjusted to
 * sea level (QNH) for barometric sensors.
 */
 @SuppressWarnings("UnusedDeclaration")
public class Altitude extends Sensor
{
// valueCallbackAltitude
// timedReportCallbackAltitude
    protected double _qnh =  YAltitude.QNH_INVALID;
    protected String _technology =  YAltitude.TECHNOLOGY_INVALID;
    protected YAltitude _yaltitude;

    public Altitude(YAltitude yfunc)
    {
       super(yfunc);
       _yaltitude = yfunc;
    }

    public Altitude(String hwid)
    {
       super(hwid);
       _yaltitude = YAltitude.FindAltitude(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _qnh = _yaltitude.get_qnh();
        _technology = _yaltitude.get_technology();
    }
    /**
     * Changes the current estimated altitude. This allows to compensate for
     * ambient pressure variations and to work in relative mode.
     *
     * @param newval : a floating point number corresponding to the current estimated altitude
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setCurrentValueBg(double newval) throws YAPI_Exception
    {
        _currentValue = newval;
        _yaltitude.set_currentValue(newval);
    }

    /**
     * Changes the barometric pressure adjusted to sea level used to compute
     * the altitude (QNH). This enables you to compensate for atmospheric pressure
     * changes due to weather conditions.
     *
     * @param newval : a floating point number corresponding to the barometric pressure adjusted to sea
     * level used to compute
     *         the altitude (QNH)
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setQnhBg(double newval) throws YAPI_Exception
    {
        _qnh = newval;
        _yaltitude.set_qnh(newval);
    }

    /**
     * Returns the barometric pressure adjusted to sea level used to compute
     * the altitude (QNH).
     *
     * @return a floating point number corresponding to the barometric pressure adjusted to sea level used to compute
     *         the altitude (QNH)
     *
     * On failure, throws an exception or returns Y_QNH_INVALID.
     */
    public double getQnh()
    {
        return _qnh;
    }

    /**
     * Returns the technology used by the sesnor to compute
     * altitude. Possibles values are  "barometric" and "gps"
     *
     * @return a string corresponding to the technology used by the sesnor to compute
     *         altitude
     *
     * On failure, throws an exception or returns Y_TECHNOLOGY_INVALID.
     */
    public String getTechnology()
    {
        return _technology;
    }

    public static YAltitude FindAltitude(String func)
    {
        return YAltitude.FindAltitude(func);
    }

//--- (end of YAltitude class start)
}

