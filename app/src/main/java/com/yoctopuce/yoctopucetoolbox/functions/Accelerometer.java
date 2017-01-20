/*********************************************************************
 *
 * $Id: pic24config.php 26169 2016-12-12 01:36:34Z mvuilleu $
 *
 * Implements Accelerometer wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YAccelerometer;

//--- (YAccelerometer class start)
/**
 * YAccelerometer Class: Accelerometer function interface
 *
 * The YSensor class is the parent class for all Yoctopuce sensors. It can be
 * used to read the current value and unit of any sensor, read the min/max
 * value, configure autonomous recording frequency and access recorded data.
 * It also provide a function to register a callback invoked each time the
 * observed value changes, or at a predefined interval. Using this class rather
 * than a specific subclass makes it possible to create generic applications
 * that work with any Yoctopuce sensor, even those that do not yet exist.
 * Note: The YAnButton class is the only analog input which does not inherit
 * from YSensor.
 */
 @SuppressWarnings("UnusedDeclaration")
public class Accelerometer extends Sensor
{
// valueCallbackAccelerometer
// timedReportCallbackAccelerometer
    protected int _bandwidth =  YAccelerometer.BANDWIDTH_INVALID;
    protected double _xValue =  YAccelerometer.XVALUE_INVALID;
    protected double _yValue =  YAccelerometer.YVALUE_INVALID;
    protected double _zValue =  YAccelerometer.ZVALUE_INVALID;
    protected int _gravityCancellation =  YAccelerometer.GRAVITYCANCELLATION_INVALID;
    protected YAccelerometer _yaccelerometer;

    public Accelerometer(YAccelerometer yfunc)
    {
       super(yfunc);
       _yaccelerometer = yfunc;
    }

    public Accelerometer(String hwid)
    {
       super(hwid);
       _yaccelerometer = YAccelerometer.FindAccelerometer(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _bandwidth = _yaccelerometer.get_bandwidth();
        _xValue = _yaccelerometer.get_xValue();
        _yValue = _yaccelerometer.get_yValue();
        _zValue = _yaccelerometer.get_zValue();
        _gravityCancellation = _yaccelerometer.get_gravityCancellation();
    }
    /**
     * Returns the measure update frequency, measured in Hz (Yocto-3D-V2 only).
     *
     * @return an integer corresponding to the measure update frequency, measured in Hz (Yocto-3D-V2 only)
     *
     * On failure, throws an exception or returns Y_BANDWIDTH_INVALID.
     */
    public int getBandwidth()
    {
        return _bandwidth;
    }

    /**
     * Changes the measure update frequency, measured in Hz (Yocto-3D-V2 only). When the
     * frequency is lower, the device performs averaging.
     *
     * @param newval : an integer corresponding to the measure update frequency, measured in Hz (Yocto-3D-V2 only)
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setBandwidthBg(int newval) throws YAPI_Exception
    {
        _bandwidth = newval;
        _yaccelerometer.set_bandwidth(newval);
    }

    /**
     * Returns the X component of the acceleration, as a floating point number.
     *
     * @return a floating point number corresponding to the X component of the acceleration, as a floating point number
     *
     * On failure, throws an exception or returns Y_XVALUE_INVALID.
     */
    public double getXValue()
    {
        return _xValue;
    }

    /**
     * Returns the Y component of the acceleration, as a floating point number.
     *
     * @return a floating point number corresponding to the Y component of the acceleration, as a floating point number
     *
     * On failure, throws an exception or returns Y_YVALUE_INVALID.
     */
    public double getYValue()
    {
        return _yValue;
    }

    /**
     * Returns the Z component of the acceleration, as a floating point number.
     *
     * @return a floating point number corresponding to the Z component of the acceleration, as a floating point number
     *
     * On failure, throws an exception or returns Y_ZVALUE_INVALID.
     */
    public double getZValue()
    {
        return _zValue;
    }

    public int getGravityCancellation()
    {
        return _gravityCancellation;
    }

    public void setGravityCancellationBg(int newval) throws YAPI_Exception
    {
        _gravityCancellation = newval;
        _yaccelerometer.set_gravityCancellation(newval);
    }

    public static YAccelerometer FindAccelerometer(String func)
    {
        return YAccelerometer.FindAccelerometer(func);
    }

//--- (end of YAccelerometer class start)
}

