/*********************************************************************
 *
 * $Id: Power.java 26014 2016-11-24 13:52:08Z seb $
 *
 * Implements Power wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YPower;

//--- (YPower class start)
/**
 * YPower Class: Power function interface
 *
 * The Yoctopuce class YPower allows you to read and configure Yoctopuce power
 * sensors. It inherits from YSensor class the core functions to read measurements,
 * register callback functions, access to the autonomous datalogger.
 * This class adds the ability to access the energy counter and the power factor.
 */
 @SuppressWarnings("UnusedDeclaration")
public class Power extends Sensor
{
// valueCallbackPower
// timedReportCallbackPower
    protected double _cosPhi =  YPower.COSPHI_INVALID;
    protected double _meter =  YPower.METER_INVALID;
    protected int _meterTimer =  YPower.METERTIMER_INVALID;
    protected YPower _ypower;

    public Power(YPower yfunc)
    {
       super(yfunc);
       _ypower = yfunc;
    }

    public Power(String hwid)
    {
       super(hwid);
       _ypower = YPower.FindPower(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _cosPhi = _ypower.get_cosPhi();
        _meter = _ypower.get_meter();
        _meterTimer = _ypower.get_meterTimer();
    }
    /**
     * Returns the power factor (the ratio between the real power consumed,
     * measured in W, and the apparent power provided, measured in VA).
     *
     * @return a floating point number corresponding to the power factor (the ratio between the real power consumed,
     *         measured in W, and the apparent power provided, measured in VA)
     *
     * On failure, throws an exception or returns Y_COSPHI_INVALID.
     */
    public double getCosPhi()
    {
        return _cosPhi;
    }

    public void setMeterBg(double newval) throws YAPI_Exception
    {
        _meter = newval;
        _ypower.set_meter(newval);
    }

    /**
     * Returns the energy counter, maintained by the wattmeter by integrating the power consumption over time.
     * Note that this counter is reset at each start of the device.
     *
     * @return a floating point number corresponding to the energy counter, maintained by the wattmeter by
     * integrating the power consumption over time
     *
     * On failure, throws an exception or returns Y_METER_INVALID.
     */
    public double getMeter()
    {
        return _meter;
    }

    /**
     * Returns the elapsed time since last energy counter reset, in seconds.
     *
     * @return an integer corresponding to the elapsed time since last energy counter reset, in seconds
     *
     * On failure, throws an exception or returns Y_METERTIMER_INVALID.
     */
    public int getMeterTimer()
    {
        return _meterTimer;
    }

//--- (end of YPower class start)
}

