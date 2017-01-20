/*********************************************************************
 *
 * $Id: pic24config.php 26169 2016-12-12 01:36:34Z mvuilleu $
 *
 * Implements PwmInput wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YPwmInput;

//--- (YPwmInput class start)
/**
 * YPwmInput Class: PwmInput function interface
 *
 * The Yoctopuce class YPwmInput allows you to read and configure Yoctopuce PWM
 * sensors. It inherits from YSensor class the core functions to read measurements,
 * register callback functions, access to the autonomous datalogger.
 * This class adds the ability to configure the signal parameter used to transmit
 * information: the duty cycle, the frequency or the pulse width.
 */
 @SuppressWarnings("UnusedDeclaration")
public class PwmInput extends Sensor
{
// valueCallbackPwmInput
// timedReportCallbackPwmInput
    protected double _dutyCycle =  YPwmInput.DUTYCYCLE_INVALID;
    protected double _pulseDuration =  YPwmInput.PULSEDURATION_INVALID;
    protected double _frequency =  YPwmInput.FREQUENCY_INVALID;
    protected double _period =  YPwmInput.PERIOD_INVALID;
    protected long _pulseCounter =  YPwmInput.PULSECOUNTER_INVALID;
    protected long _pulseTimer =  YPwmInput.PULSETIMER_INVALID;
    protected int _pwmReportMode =  YPwmInput.PWMREPORTMODE_INVALID;
    protected YPwmInput _ypwminput;

    public PwmInput(YPwmInput yfunc)
    {
       super(yfunc);
       _ypwminput = yfunc;
    }

    public PwmInput(String hwid)
    {
       super(hwid);
       _ypwminput = YPwmInput.FindPwmInput(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _dutyCycle = _ypwminput.get_dutyCycle();
        _pulseDuration = _ypwminput.get_pulseDuration();
        _frequency = _ypwminput.get_frequency();
        _period = _ypwminput.get_period();
        _pulseCounter = _ypwminput.get_pulseCounter();
        _pulseTimer = _ypwminput.get_pulseTimer();
        _pwmReportMode = _ypwminput.get_pwmReportMode();
    }
    /**
     * Returns the PWM duty cycle, in per cents.
     *
     * @return a floating point number corresponding to the PWM duty cycle, in per cents
     *
     * On failure, throws an exception or returns Y_DUTYCYCLE_INVALID.
     */
    public double getDutyCycle()
    {
        return _dutyCycle;
    }

    /**
     * Returns the PWM pulse length in milliseconds, as a floating point number.
     *
     * @return a floating point number corresponding to the PWM pulse length in milliseconds, as a
     * floating point number
     *
     * On failure, throws an exception or returns Y_PULSEDURATION_INVALID.
     */
    public double getPulseDuration()
    {
        return _pulseDuration;
    }

    /**
     * Returns the PWM frequency in Hz.
     *
     * @return a floating point number corresponding to the PWM frequency in Hz
     *
     * On failure, throws an exception or returns Y_FREQUENCY_INVALID.
     */
    public double getFrequency()
    {
        return _frequency;
    }

    /**
     * Returns the PWM period in milliseconds.
     *
     * @return a floating point number corresponding to the PWM period in milliseconds
     *
     * On failure, throws an exception or returns Y_PERIOD_INVALID.
     */
    public double getPeriod()
    {
        return _period;
    }

    /**
     * Returns the pulse counter value. Actually that
     * counter is incremented twice per period. That counter is
     * limited  to 1 billion
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
        _ypwminput.set_pulseCounter(newval);
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

    /**
     * Returns the parameter (frequency/duty cycle, pulse width, edges count) returned by the
     * get_currentValue function and callbacks. Attention
     *
     * @return a value among Y_PWMREPORTMODE_PWM_DUTYCYCLE, Y_PWMREPORTMODE_PWM_FREQUENCY,
     * Y_PWMREPORTMODE_PWM_PULSEDURATION and Y_PWMREPORTMODE_PWM_EDGECOUNT corresponding to the parameter
     * (frequency/duty cycle, pulse width, edges count) returned by the get_currentValue function and callbacks
     *
     * On failure, throws an exception or returns Y_PWMREPORTMODE_INVALID.
     */
    public int getPwmReportMode()
    {
        return _pwmReportMode;
    }

    /**
     * Modifies the  parameter  type (frequency/duty cycle, pulse width, or edge count) returned by the
     * get_currentValue function and callbacks.
     * The edge count value is limited to the 6 lowest digits. For values greater than one million, use
     * get_pulseCounter().
     *
     * @param newval : a value among Y_PWMREPORTMODE_PWM_DUTYCYCLE, Y_PWMREPORTMODE_PWM_FREQUENCY,
     * Y_PWMREPORTMODE_PWM_PULSEDURATION and Y_PWMREPORTMODE_PWM_EDGECOUNT
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setPwmReportModeBg(int newval) throws YAPI_Exception
    {
        _pwmReportMode = newval;
        _ypwminput.set_pwmReportMode(newval);
    }

    public static YPwmInput FindPwmInput(String func)
    {
        return YPwmInput.FindPwmInput(func);
    }

    public int resetCounter() throws YAPI_Exception
    {
        return _ypwminput.resetCounter();
    }

//--- (end of YPwmInput class start)
}

