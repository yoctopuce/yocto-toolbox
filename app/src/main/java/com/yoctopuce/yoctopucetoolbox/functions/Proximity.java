/*********************************************************************
 *
 * $Id: pic24config.php 26169 2016-12-12 01:36:34Z mvuilleu $
 *
 * Implements Proximity wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YProximity;

//--- (YProximity class start)
/**
 * YProximity Class: Proximity function interface
 *
 * The Yoctopuce class YProximity allows you to use and configure Yoctopuce proximity
 * sensors. It inherits from YSensor class the core functions to read measurements,
 * register callback functions, access to the autonomous datalogger.
 * This class adds the ability to easily perform a one-point linear calibration
 * to compensate the effect of a glass or filter placed in front of the sensor.
 */
 @SuppressWarnings("UnusedDeclaration")
public class Proximity extends Sensor
{
// valueCallbackProximity
// timedReportCallbackProximity
    protected int _detectionThreshold =  YProximity.DETECTIONTHRESHOLD_INVALID;
    protected int _isPresent =  YProximity.ISPRESENT_INVALID;
    protected long _lastTimeApproached =  YProximity.LASTTIMEAPPROACHED_INVALID;
    protected long _lastTimeRemoved =  YProximity.LASTTIMEREMOVED_INVALID;
    protected long _pulseCounter =  YProximity.PULSECOUNTER_INVALID;
    protected long _pulseTimer =  YProximity.PULSETIMER_INVALID;
    protected YProximity _yproximity;

    public Proximity(YProximity yfunc)
    {
       super(yfunc);
       _yproximity = yfunc;
    }

    public Proximity(String hwid)
    {
       super(hwid);
       _yproximity = YProximity.FindProximity(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _detectionThreshold = _yproximity.get_detectionThreshold();
        _isPresent = _yproximity.get_isPresent();
        _lastTimeApproached = _yproximity.get_lastTimeApproached();
        _lastTimeRemoved = _yproximity.get_lastTimeRemoved();
        _pulseCounter = _yproximity.get_pulseCounter();
        _pulseTimer = _yproximity.get_pulseTimer();
    }
    /**
     * Returns the threshold used to determine the logical state of the proximity sensor, when considered
     * as a binary input (on/off).
     *
     * @return an integer corresponding to the threshold used to determine the logical state of the
     * proximity sensor, when considered
     *         as a binary input (on/off)
     *
     * On failure, throws an exception or returns Y_DETECTIONTHRESHOLD_INVALID.
     */
    public int getDetectionThreshold()
    {
        return _detectionThreshold;
    }

    /**
     * Changes the threshold used to determine the logical state of the proximity sensor, when considered
     * as a binary input (on/off).
     *
     * @param newval : an integer corresponding to the threshold used to determine the logical state of
     * the proximity sensor, when considered
     *         as a binary input (on/off)
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setDetectionThresholdBg(int newval) throws YAPI_Exception
    {
        _detectionThreshold = newval;
        _yproximity.set_detectionThreshold(newval);
    }

    /**
     * Returns true if the input (considered as binary) is active (detection value is smaller than the
     * specified threshold), and false otherwise.
     *
     * @return either Y_ISPRESENT_FALSE or Y_ISPRESENT_TRUE, according to true if the input (considered as
     * binary) is active (detection value is smaller than the specified threshold), and false otherwise
     *
     * On failure, throws an exception or returns Y_ISPRESENT_INVALID.
     */
    public int getIsPresent()
    {
        return _isPresent;
    }

    /**
     * Returns the number of elapsed milliseconds between the module power on and the last observed
     * detection (the input contact transitioned from absent to present).
     *
     * @return an integer corresponding to the number of elapsed milliseconds between the module power on
     * and the last observed
     *         detection (the input contact transitioned from absent to present)
     *
     * On failure, throws an exception or returns Y_LASTTIMEAPPROACHED_INVALID.
     */
    public long getLastTimeApproached()
    {
        return _lastTimeApproached;
    }

    /**
     * Returns the number of elapsed milliseconds between the module power on and the last observed
     * detection (the input contact transitioned from present to absent).
     *
     * @return an integer corresponding to the number of elapsed milliseconds between the module power on
     * and the last observed
     *         detection (the input contact transitioned from present to absent)
     *
     * On failure, throws an exception or returns Y_LASTTIMEREMOVED_INVALID.
     */
    public long getLastTimeRemoved()
    {
        return _lastTimeRemoved;
    }

    /**
     * Returns the pulse counter value. The value is a 32 bit integer. In case
     * of overflow (>=2^32), the counter will wrap. To reset the counter, just
     * call the resetCounter() method.
     *
     * @return an integer corresponding to the pulse counter value
     *
     * On failure, throws an exception or returns Y_PULSECOUNTER_INVALID.
     */
    public long getPulseCounter()
    {
        return _pulseCounter;
    }

    public void setPulseCounterBg(long newval) throws YAPI_Exception
    {
        _pulseCounter = newval;
        _yproximity.set_pulseCounter(newval);
    }

    /**
     * Returns the timer of the pulses counter (ms).
     *
     * @return an integer corresponding to the timer of the pulses counter (ms)
     *
     * On failure, throws an exception or returns Y_PULSETIMER_INVALID.
     */
    public long getPulseTimer()
    {
        return _pulseTimer;
    }

    public static YProximity FindProximity(String func)
    {
        return YProximity.FindProximity(func);
    }

    public int resetCounter() throws YAPI_Exception
    {
        return _yproximity.resetCounter();
    }

//--- (end of YProximity class start)
}

