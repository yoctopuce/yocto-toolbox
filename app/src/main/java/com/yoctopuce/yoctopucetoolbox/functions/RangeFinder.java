/*********************************************************************
 *
 * $Id: RangeFinder.java 46698 2021-10-01 06:31:31Z web $
 *
 * Implements RangeFinder wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YRangeFinder;

//--- (YRangeFinder class start)
/**
 * YRangeFinder Class: RangeFinder function interface
 *
 * The Yoctopuce class YRangeFinder allows you to use and configure Yoctopuce range finder
 * sensors. It inherits from the YSensor class the core functions to read measurements,
 * register callback functions, access the autonomous datalogger.
 * This class adds the ability to easily perform a one-point linear calibration
 * to compensate the effect of a glass or filter placed in front of the sensor.
 */
 @SuppressWarnings("UnusedDeclaration")
public class RangeFinder extends Sensor
{
// valueCallbackRangeFinder
// timedReportCallbackRangeFinder
    protected int _rangeFinderMode =  YRangeFinder.RANGEFINDERMODE_INVALID;
    protected String _hardwareCalibration =  YRangeFinder.HARDWARECALIBRATION_INVALID;
    protected double _currentTemperature =  YRangeFinder.CURRENTTEMPERATURE_INVALID;
    protected String _command =  YRangeFinder.COMMAND_INVALID;
    protected YRangeFinder _yrangefinder;

    public RangeFinder(YRangeFinder yfunc)
    {
       super(yfunc);
       _yrangefinder = yfunc;
    }

    public RangeFinder(String hwid)
    {
       super(hwid);
       _yrangefinder = YRangeFinder.FindRangeFinder(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _rangeFinderMode = _yrangefinder.get_rangeFinderMode();
        _hardwareCalibration = _yrangefinder.get_hardwareCalibration();
        _currentTemperature = _yrangefinder.get_currentTemperature();
        _command = _yrangefinder.get_command();
    }
    /**
     * Changes the measuring unit for the measured range. That unit is a string.
     * String value can be " or mm. Any other value is ignored.
     * Remember to call the saveToFlash() method of the module if the modification must be kept.
     * WARNING: if a specific calibration is defined for the rangeFinder function, a
     * unit system change will probably break it.
     *
     * @param newval : a string corresponding to the measuring unit for the measured range
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setUnitBg(String newval) throws YAPI_Exception
    {
        _unit = newval;
        _yrangefinder.set_unit(newval);
    }

    /**
     * Returns the range finder running mode. The rangefinder running mode
     * allows you to put priority on precision, speed or maximum range.
     *
     * @return a value among Y_RANGEFINDERMODE_DEFAULT, Y_RANGEFINDERMODE_LONG_RANGE,
     * Y_RANGEFINDERMODE_HIGH_ACCURACY and Y_RANGEFINDERMODE_HIGH_SPEED corresponding to the range finder running mode
     *
     * On failure, throws an exception or returns Y_RANGEFINDERMODE_INVALID.
     */
    public int getRangeFinderMode()
    {
        return _rangeFinderMode;
    }

    /**
     * Changes the rangefinder running mode, allowing you to put priority on
     * precision, speed or maximum range.
     *
     * @param newval : a value among Y_RANGEFINDERMODE_DEFAULT, Y_RANGEFINDERMODE_LONG_RANGE,
     * Y_RANGEFINDERMODE_HIGH_ACCURACY and Y_RANGEFINDERMODE_HIGH_SPEED corresponding to the rangefinder
     * running mode, allowing you to put priority on
     *         precision, speed or maximum range
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setRangeFinderModeBg(int newval) throws YAPI_Exception
    {
        _rangeFinderMode = newval;
        _yrangefinder.set_rangeFinderMode(newval);
    }

    public String getHardwareCalibration()
    {
        return _hardwareCalibration;
    }

    public void setHardwareCalibrationBg(String newval) throws YAPI_Exception
    {
        _hardwareCalibration = newval;
        _yrangefinder.set_hardwareCalibration(newval);
    }

    /**
     * Returns the current sensor temperature, as a floating point number.
     *
     * @return a floating point number corresponding to the current sensor temperature, as a floating point number
     *
     * On failure, throws an exception or returns Y_CURRENTTEMPERATURE_INVALID.
     */
    public double getCurrentTemperature()
    {
        return _currentTemperature;
    }

    public String getCommand()
    {
        return _command;
    }

    public void setCommandBg(String newval) throws YAPI_Exception
    {
        _command = newval;
        _yrangefinder.set_command(newval);
    }

    public static YRangeFinder FindRangeFinder(String func)
    {
        return YRangeFinder.FindRangeFinder(func);
    }

    public double get_hardwareCalibrationTemperature() throws YAPI_Exception
    {
        return _yrangefinder.get_hardwareCalibrationTemperature();
    }

    public int triggerTemperatureCalibration() throws YAPI_Exception
    {
        return _yrangefinder.triggerTemperatureCalibration();
    }

    public int triggerSpadCalibration() throws YAPI_Exception
    {
        return _yrangefinder.triggerSpadCalibration();
    }

    public int triggerOffsetCalibration(double targetDist) throws YAPI_Exception
    {
        return _yrangefinder.triggerOffsetCalibration(targetDist);
    }

    public int triggerXTalkCalibration(double targetDist) throws YAPI_Exception
    {
        return _yrangefinder.triggerXTalkCalibration(targetDist);
    }

    public int cancelCoverGlassCalibrations() throws YAPI_Exception
    {
        return _yrangefinder.cancelCoverGlassCalibrations();
    }

//--- (end of YRangeFinder class start)
}

