/*********************************************************************
 *
 * $Id: GenericSensor.java 46698 2021-10-01 06:31:31Z web $
 *
 * Implements GenericSensor wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YGenericSensor;

//--- (YGenericSensor class start)
/**
 * YGenericSensor Class: GenericSensor function interface
 *
 * The YGenericSensor class allows you to read and configure Yoctopuce signal
 * transducers. It inherits from YSensor class the core functions to read measurements,
 * to register callback functions, to access the autonomous datalogger.
 * This class adds the ability to configure the automatic conversion between the
 * measured signal and the corresponding engineering unit.
 */
 @SuppressWarnings("UnusedDeclaration")
public class GenericSensor extends Sensor
{
// valueCallbackGenericSensor
// timedReportCallbackGenericSensor
    protected double _signalValue =  YGenericSensor.SIGNALVALUE_INVALID;
    protected String _signalUnit =  YGenericSensor.SIGNALUNIT_INVALID;
    protected String _signalRange =  YGenericSensor.SIGNALRANGE_INVALID;
    protected String _valueRange =  YGenericSensor.VALUERANGE_INVALID;
    protected double _signalBias =  YGenericSensor.SIGNALBIAS_INVALID;
    protected int _signalSampling =  YGenericSensor.SIGNALSAMPLING_INVALID;
    protected YGenericSensor _ygenericsensor;

    public GenericSensor(YGenericSensor yfunc)
    {
       super(yfunc);
       _ygenericsensor = yfunc;
    }

    public GenericSensor(String hwid)
    {
       super(hwid);
       _ygenericsensor = YGenericSensor.FindGenericSensor(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _signalValue = _ygenericsensor.get_signalValue();
        _signalUnit = _ygenericsensor.get_signalUnit();
        _signalRange = _ygenericsensor.get_signalRange();
        _valueRange = _ygenericsensor.get_valueRange();
        _signalBias = _ygenericsensor.get_signalBias();
        _signalSampling = _ygenericsensor.get_signalSampling();
    }
    /**
     * Changes the measuring unit for the measured value.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : a string corresponding to the measuring unit for the measured value
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setUnitBg(String newval) throws YAPI_Exception
    {
        _unit = newval;
        _ygenericsensor.set_unit(newval);
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

    /**
     * Returns the electric signal range used by the sensor.
     *
     * @return a string corresponding to the electric signal range used by the sensor
     *
     * On failure, throws an exception or returns Y_SIGNALRANGE_INVALID.
     */
    public String getSignalRange()
    {
        return _signalRange;
    }

    /**
     * Changes the electric signal range used by the sensor. Default value is "-999999.999...999999.999".
     *
     * @param newval : a string corresponding to the electric signal range used by the sensor
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setSignalRangeBg(String newval) throws YAPI_Exception
    {
        _signalRange = newval;
        _ygenericsensor.set_signalRange(newval);
    }

    /**
     * Returns the physical value range measured by the sensor.
     *
     * @return a string corresponding to the physical value range measured by the sensor
     *
     * On failure, throws an exception or returns Y_VALUERANGE_INVALID.
     */
    public String getValueRange()
    {
        return _valueRange;
    }

    /**
     * Changes the physical value range measured by the sensor. As a side effect, the range modification may
     * automatically modify the display resolution. Default value is "-999999.999...999999.999".
     *
     * @param newval : a string corresponding to the physical value range measured by the sensor
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setValueRangeBg(String newval) throws YAPI_Exception
    {
        _valueRange = newval;
        _ygenericsensor.set_valueRange(newval);
    }

    /**
     * Changes the electric signal bias for zero shift adjustment.
     * If your electric signal reads positif when it should be zero, setup
     * a positive signalBias of the same value to fix the zero shift.
     *
     * @param newval : a floating point number corresponding to the electric signal bias for zero shift adjustment
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setSignalBiasBg(double newval) throws YAPI_Exception
    {
        _signalBias = newval;
        _ygenericsensor.set_signalBias(newval);
    }

    /**
     * Returns the electric signal bias for zero shift adjustment.
     * A positive bias means that the signal is over-reporting the measure,
     * while a negative bias means that the signal is underreporting the measure.
     *
     * @return a floating point number corresponding to the electric signal bias for zero shift adjustment
     *
     * On failure, throws an exception or returns Y_SIGNALBIAS_INVALID.
     */
    public double getSignalBias()
    {
        return _signalBias;
    }

    /**
     * Returns the electric signal sampling method to use.
     * The HIGH_RATE method uses the highest sampling frequency, without any filtering.
     * The HIGH_RATE_FILTERED method adds a windowed 7-sample median filter.
     * The LOW_NOISE method uses a reduced acquisition frequency to reduce noise.
     * The LOW_NOISE_FILTERED method combines a reduced frequency with the median filter
     * to get measures as stable as possible when working on a noisy signal.
     *
     * @return a value among Y_SIGNALSAMPLING_HIGH_RATE, Y_SIGNALSAMPLING_HIGH_RATE_FILTERED,
     * Y_SIGNALSAMPLING_LOW_NOISE and Y_SIGNALSAMPLING_LOW_NOISE_FILTERED corresponding to the electric
     * signal sampling method to use
     *
     * On failure, throws an exception or returns Y_SIGNALSAMPLING_INVALID.
     */
    public int getSignalSampling()
    {
        return _signalSampling;
    }

    /**
     * Changes the electric signal sampling method to use.
     * The HIGH_RATE method uses the highest sampling frequency, without any filtering.
     * The HIGH_RATE_FILTERED method adds a windowed 7-sample median filter.
     * The LOW_NOISE method uses a reduced acquisition frequency to reduce noise.
     * The LOW_NOISE_FILTERED method combines a reduced frequency with the median filter
     * to get measures as stable as possible when working on a noisy signal.
     *
     * @param newval : a value among Y_SIGNALSAMPLING_HIGH_RATE, Y_SIGNALSAMPLING_HIGH_RATE_FILTERED,
     * Y_SIGNALSAMPLING_LOW_NOISE and Y_SIGNALSAMPLING_LOW_NOISE_FILTERED corresponding to the electric
     * signal sampling method to use
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setSignalSamplingBg(int newval) throws YAPI_Exception
    {
        _signalSampling = newval;
        _ygenericsensor.set_signalSampling(newval);
    }

    public static YGenericSensor FindGenericSensor(String func)
    {
        return YGenericSensor.FindGenericSensor(func);
    }

    public int zeroAdjust() throws YAPI_Exception
    {
        return _ygenericsensor.zeroAdjust();
    }

//--- (end of YGenericSensor class start)
}

