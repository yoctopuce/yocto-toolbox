/*********************************************************************
 *
 * $Id: Gyro.java 26331 2017-01-11 16:50:06Z seb $
 *
 * Implements Gyro wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YGyro;
import com.yoctopuce.YoctoAPI.YQt;
//--- (generated code: YGyro class start)
/**
 * YGyro Class: Gyroscope function interface
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
public class Gyro extends Sensor
{
// valueCallbackGyro
// timedReportCallbackGyro
// quatCallback
// anglesCallback
    protected int _bandwidth =  YGyro.BANDWIDTH_INVALID;
    protected double _xValue =  YGyro.XVALUE_INVALID;
    protected double _yValue =  YGyro.YVALUE_INVALID;
    protected double _zValue =  YGyro.ZVALUE_INVALID;
    protected int _qt_stamp = 0;
    protected YQt _qt_w;
    protected YQt _qt_x;
    protected YQt _qt_y;
    protected YQt _qt_z;
    protected double _w = 0;
    protected double _x = 0;
    protected double _y = 0;
    protected double _z = 0;
    protected int _angles_stamp = 0;
    protected double _head = 0;
    protected double _pitch = 0;
    protected double _roll = 0;
    protected YGyro _ygyro;

    public Gyro(YGyro yfunc)
    {
       super(yfunc);
       _ygyro = yfunc;
    }

    public Gyro(String hwid)
    {
       super(hwid);
       _ygyro = YGyro.FindGyro(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _bandwidth = _ygyro.get_bandwidth();
        _xValue = _ygyro.get_xValue();
        _yValue = _ygyro.get_yValue();
        _zValue = _ygyro.get_zValue();
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
        _ygyro.set_bandwidth(newval);
    }

    /**
     * Returns the angular velocity around the X axis of the device, as a floating point number.
     *
     * @return a floating point number corresponding to the angular velocity around the X axis of the
     * device, as a floating point number
     *
     * On failure, throws an exception or returns Y_XVALUE_INVALID.
     */
    public double getXValue()
    {
        return _xValue;
    }

    /**
     * Returns the angular velocity around the Y axis of the device, as a floating point number.
     *
     * @return a floating point number corresponding to the angular velocity around the Y axis of the
     * device, as a floating point number
     *
     * On failure, throws an exception or returns Y_YVALUE_INVALID.
     */
    public double getYValue()
    {
        return _yValue;
    }

    /**
     * Returns the angular velocity around the Z axis of the device, as a floating point number.
     *
     * @return a floating point number corresponding to the angular velocity around the Z axis of the
     * device, as a floating point number
     *
     * On failure, throws an exception or returns Y_ZVALUE_INVALID.
     */
    public double getZValue()
    {
        return _zValue;
    }

    public static YGyro FindGyro(String func)
    {
        return YGyro.FindGyro(func);
    }

    public double get_roll() throws YAPI_Exception
    {
        return _ygyro.get_roll();
    }

    public double get_pitch() throws YAPI_Exception
    {
        return _ygyro.get_pitch();
    }

    public double get_heading() throws YAPI_Exception
    {
        return _ygyro.get_heading();
    }

    public double get_quaternionW() throws YAPI_Exception
    {
        return _ygyro.get_quaternionW();
    }

    public double get_quaternionX() throws YAPI_Exception
    {
        return _ygyro.get_quaternionX();
    }

    public double get_quaternionY() throws YAPI_Exception
    {
        return _ygyro.get_quaternionY();
    }

    public double get_quaternionZ() throws YAPI_Exception
    {
        return _ygyro.get_quaternionZ();
    }

//--- (end of generated code: YGyro class start)
}

