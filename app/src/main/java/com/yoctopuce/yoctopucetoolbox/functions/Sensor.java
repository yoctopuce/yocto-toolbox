/*********************************************************************
 *
 * $Id: Sensor.java 26014 2016-11-24 13:52:08Z seb $
 *
 * Implements Sensor wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YSensor;

import java.util.ArrayList;

//--- (generated code: YSensor class start)
/**
 * YSensor Class: Sensor function interface
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
public class Sensor extends Function
{
// valueCallbackSensor
// timedReportCallbackSensor
    protected String _unit =  YSensor.UNIT_INVALID;
    protected double _currentValue =  YSensor.CURRENTVALUE_INVALID;
    protected double _lowestValue =  YSensor.LOWESTVALUE_INVALID;
    protected double _highestValue =  YSensor.HIGHESTVALUE_INVALID;
    protected double _currentRawValue =  YSensor.CURRENTRAWVALUE_INVALID;
    protected String _logFrequency =  YSensor.LOGFREQUENCY_INVALID;
    protected String _reportFrequency =  YSensor.REPORTFREQUENCY_INVALID;
    protected String _calibrationParam =  YSensor.CALIBRATIONPARAM_INVALID;
    protected double _resolution =  YSensor.RESOLUTION_INVALID;
    protected int _sensorState =  YSensor.SENSORSTATE_INVALID;
    protected double _prevTimedReport = 0;
    protected double _iresol = 0;
    protected double _offset = 0;
    protected double _scale = 0;
    protected double _decexp = 0;
    protected boolean _isScal;
    protected boolean _isScal32;
    protected int _caltyp = 0;
    protected ArrayList<Integer> _calpar = new ArrayList<Integer>();
    protected ArrayList<Double> _calraw = new ArrayList<Double>();
    protected ArrayList<Double> _calref = new ArrayList<Double>();
    protected YAPI.CalibrationHandlerCallback _calhdl;
    protected YSensor _ysensor;

    public Sensor(YSensor yfunc)
    {
       super(yfunc);
       _ysensor = yfunc;
    }

    public Sensor(String hwid)
    {
       super(hwid);
       _ysensor = YSensor.FindSensor(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _unit = _ysensor.get_unit();
        _currentValue = _ysensor.get_currentValue();
        _lowestValue = _ysensor.get_lowestValue();
        _highestValue = _ysensor.get_highestValue();
        _currentRawValue = _ysensor.get_currentRawValue();
        _logFrequency = _ysensor.get_logFrequency();
        _reportFrequency = _ysensor.get_reportFrequency();
        _calibrationParam = _ysensor.get_calibrationParam();
        _resolution = _ysensor.get_resolution();
        _sensorState = _ysensor.get_sensorState();
    }
    /**
     * Returns the measuring unit for the measure.
     *
     * @return a string corresponding to the measuring unit for the measure
     *
     * On failure, throws an exception or returns Y_UNIT_INVALID.
     */
    public String getUnit()
    {
        return _unit;
    }

    /**
     * Returns the current value of the measure, in the specified unit, as a floating point number.
     *
     * @return a floating point number corresponding to the current value of the measure, in the specified
     * unit, as a floating point number
     *
     * On failure, throws an exception or returns Y_CURRENTVALUE_INVALID.
     */
    public double getCurrentValue()
    {
        return _currentValue;
    }

    /**
     * Changes the recorded minimal value observed.
     *
     * @param newval : a floating point number corresponding to the recorded minimal value observed
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setLowestValueBg(double newval) throws YAPI_Exception
    {
        _lowestValue = newval;
        _ysensor.set_lowestValue(newval);
    }

    /**
     * Returns the minimal value observed for the measure since the device was started.
     *
     * @return a floating point number corresponding to the minimal value observed for the measure since
     * the device was started
     *
     * On failure, throws an exception or returns Y_LOWESTVALUE_INVALID.
     */
    public double getLowestValue()
    {
        return _lowestValue;
    }

    /**
     * Changes the recorded maximal value observed.
     *
     * @param newval : a floating point number corresponding to the recorded maximal value observed
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setHighestValueBg(double newval) throws YAPI_Exception
    {
        _highestValue = newval;
        _ysensor.set_highestValue(newval);
    }

    /**
     * Returns the maximal value observed for the measure since the device was started.
     *
     * @return a floating point number corresponding to the maximal value observed for the measure since
     * the device was started
     *
     * On failure, throws an exception or returns Y_HIGHESTVALUE_INVALID.
     */
    public double getHighestValue()
    {
        return _highestValue;
    }

    /**
     * Returns the uncalibrated, unrounded raw value returned by the sensor, in the specified unit, as a
     * floating point number.
     *
     * @return a floating point number corresponding to the uncalibrated, unrounded raw value returned by
     * the sensor, in the specified unit, as a floating point number
     *
     * On failure, throws an exception or returns Y_CURRENTRAWVALUE_INVALID.
     */
    public double getCurrentRawValue()
    {
        return _currentRawValue;
    }

    /**
     * Returns the datalogger recording frequency for this function, or "OFF"
     * when measures are not stored in the data logger flash memory.
     *
     * @return a string corresponding to the datalogger recording frequency for this function, or "OFF"
     *         when measures are not stored in the data logger flash memory
     *
     * On failure, throws an exception or returns Y_LOGFREQUENCY_INVALID.
     */
    public String getLogFrequency()
    {
        return _logFrequency;
    }

    /**
     * Changes the datalogger recording frequency for this function.
     * The frequency can be specified as samples per second,
     * as sample per minute (for instance "15/m") or in samples per
     * hour (eg. "4/h"). To disable recording for this function, use
     * the value "OFF".
     *
     * @param newval : a string corresponding to the datalogger recording frequency for this function
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setLogFrequencyBg(String newval) throws YAPI_Exception
    {
        _logFrequency = newval;
        _ysensor.set_logFrequency(newval);
    }

    /**
     * Returns the timed value notification frequency, or "OFF" if timed
     * value notifications are disabled for this function.
     *
     * @return a string corresponding to the timed value notification frequency, or "OFF" if timed
     *         value notifications are disabled for this function
     *
     * On failure, throws an exception or returns Y_REPORTFREQUENCY_INVALID.
     */
    public String getReportFrequency()
    {
        return _reportFrequency;
    }

    /**
     * Changes the timed value notification frequency for this function.
     * The frequency can be specified as samples per second,
     * as sample per minute (for instance "15/m") or in samples per
     * hour (eg. "4/h"). To disable timed value notifications for this
     * function, use the value "OFF".
     *
     * @param newval : a string corresponding to the timed value notification frequency for this function
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setReportFrequencyBg(String newval) throws YAPI_Exception
    {
        _reportFrequency = newval;
        _ysensor.set_reportFrequency(newval);
    }

    public String getCalibrationParam()
    {
        return _calibrationParam;
    }

    public void setCalibrationParamBg(String newval) throws YAPI_Exception
    {
        _calibrationParam = newval;
        _ysensor.set_calibrationParam(newval);
    }

    /**
     * Changes the resolution of the measured physical values. The resolution corresponds to the numerical precision
     * when displaying value. It does not change the precision of the measure itself.
     *
     * @param newval : a floating point number corresponding to the resolution of the measured physical values
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setResolutionBg(double newval) throws YAPI_Exception
    {
        _resolution = newval;
        _ysensor.set_resolution(newval);
    }

    /**
     * Returns the resolution of the measured values. The resolution corresponds to the numerical precision
     * of the measures, which is not always the same as the actual precision of the sensor.
     *
     * @return a floating point number corresponding to the resolution of the measured values
     *
     * On failure, throws an exception or returns Y_RESOLUTION_INVALID.
     */
    public double getResolution()
    {
        return _resolution;
    }

    /**
     * Returns the sensor health state code, which is zero when there is an up-to-date measure
     * available or a positive code if the sensor is not able to provide a measure right now.
     *
     * @return an integer corresponding to the sensor health state code, which is zero when there is an
     * up-to-date measure
     *         available or a positive code if the sensor is not able to provide a measure right now
     *
     * On failure, throws an exception or returns Y_SENSORSTATE_INVALID.
     */
    public int getSensorState()
    {
        return _sensorState;
    }

//--- (end of generated code: YSensor class start)
}

