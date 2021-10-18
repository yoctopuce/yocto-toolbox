/*********************************************************************
 *
 * $Id: PwmOutput.java 46698 2021-10-01 06:31:31Z web $
 *
 * Implements PwmOutput wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YPwmOutput;

//--- (YPwmOutput class start)
/**
 * YPwmOutput Class: PwmOutput function interface
 *
 * The Yoctopuce application programming interface allows you to configure, start, and stop the PWM.
 */
 @SuppressWarnings("UnusedDeclaration")
public class PwmOutput extends Function
{
// valueCallbackPwmOutput
    protected int _enabled =  YPwmOutput.ENABLED_INVALID;
    protected double _frequency =  YPwmOutput.FREQUENCY_INVALID;
    protected double _period =  YPwmOutput.PERIOD_INVALID;
    protected double _dutyCycle =  YPwmOutput.DUTYCYCLE_INVALID;
    protected double _pulseDuration =  YPwmOutput.PULSEDURATION_INVALID;
    protected String _pwmTransition =  YPwmOutput.PWMTRANSITION_INVALID;
    protected int _enabledAtPowerOn =  YPwmOutput.ENABLEDATPOWERON_INVALID;
    protected double _dutyCycleAtPowerOn =  YPwmOutput.DUTYCYCLEATPOWERON_INVALID;
    protected YPwmOutput _ypwmoutput;

    public PwmOutput(YPwmOutput yfunc)
    {
       super(yfunc);
       _ypwmoutput = yfunc;
    }

    public PwmOutput(String hwid)
    {
       super(hwid);
       _ypwmoutput = YPwmOutput.FindPwmOutput(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _enabled = _ypwmoutput.get_enabled();
        _frequency = _ypwmoutput.get_frequency();
        _period = _ypwmoutput.get_period();
        _dutyCycle = _ypwmoutput.get_dutyCycle();
        _pulseDuration = _ypwmoutput.get_pulseDuration();
        _pwmTransition = _ypwmoutput.get_pwmTransition();
        _enabledAtPowerOn = _ypwmoutput.get_enabledAtPowerOn();
        _dutyCycleAtPowerOn = _ypwmoutput.get_dutyCycleAtPowerOn();
    }
    /**
     * Returns the state of the PWMs.
     *
     * @return either Y_ENABLED_FALSE or Y_ENABLED_TRUE, according to the state of the PWMs
     *
     * On failure, throws an exception or returns Y_ENABLED_INVALID.
     */
    public int getEnabled()
    {
        return _enabled;
    }

    /**
     * Stops or starts the PWM.
     *
     * @param newval : either Y_ENABLED_FALSE or Y_ENABLED_TRUE
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setEnabledBg(int newval) throws YAPI_Exception
    {
        _enabled = newval;
        _ypwmoutput.set_enabled(newval);
    }

    /**
     * Changes the PWM frequency. The duty cycle is kept unchanged thanks to an
     * automatic pulse width change.
     *
     * @param newval : a floating point number corresponding to the PWM frequency
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setFrequencyBg(double newval) throws YAPI_Exception
    {
        _frequency = newval;
        _ypwmoutput.set_frequency(newval);
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
     * Changes the PWM period in milliseconds.
     *
     * @param newval : a floating point number corresponding to the PWM period in milliseconds
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setPeriodBg(double newval) throws YAPI_Exception
    {
        _period = newval;
        _ypwmoutput.set_period(newval);
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
     * Changes the PWM duty cycle, in per cents.
     *
     * @param newval : a floating point number corresponding to the PWM duty cycle, in per cents
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setDutyCycleBg(double newval) throws YAPI_Exception
    {
        _dutyCycle = newval;
        _ypwmoutput.set_dutyCycle(newval);
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
     * Changes the PWM pulse length, in milliseconds. A pulse length cannot be longer than period,
     * otherwise it is truncated.
     *
     * @param newval : a floating point number corresponding to the PWM pulse length, in milliseconds
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setPulseDurationBg(double newval) throws YAPI_Exception
    {
        _pulseDuration = newval;
        _ypwmoutput.set_pulseDuration(newval);
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

    public String getPwmTransition()
    {
        return _pwmTransition;
    }

    public void setPwmTransitionBg(String newval) throws YAPI_Exception
    {
        _pwmTransition = newval;
        _ypwmoutput.set_pwmTransition(newval);
    }

    /**
     * Returns the state of the PWM at device power on.
     *
     * @return either Y_ENABLEDATPOWERON_FALSE or Y_ENABLEDATPOWERON_TRUE, according to the state of the
     * PWM at device power on
     *
     * On failure, throws an exception or returns Y_ENABLEDATPOWERON_INVALID.
     */
    public int getEnabledAtPowerOn()
    {
        return _enabledAtPowerOn;
    }

    /**
     * Changes the state of the PWM at device power on. Remember to call the matching module saveToFlash()
     * method, otherwise this call will have no effect.
     *
     * @param newval : either Y_ENABLEDATPOWERON_FALSE or Y_ENABLEDATPOWERON_TRUE, according to the state
     * of the PWM at device power on
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setEnabledAtPowerOnBg(int newval) throws YAPI_Exception
    {
        _enabledAtPowerOn = newval;
        _ypwmoutput.set_enabledAtPowerOn(newval);
    }

    /**
     * Changes the PWM duty cycle at device power on. Remember to call the matching
     * module saveToFlash() method, otherwise this call will have no effect.
     *
     * @param newval : a floating point number corresponding to the PWM duty cycle at device power on
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setDutyCycleAtPowerOnBg(double newval) throws YAPI_Exception
    {
        _dutyCycleAtPowerOn = newval;
        _ypwmoutput.set_dutyCycleAtPowerOn(newval);
    }

    /**
     * Returns the PWMs duty cycle at device power on as a floating point number between 0 and 100.
     *
     * @return a floating point number corresponding to the PWMs duty cycle at device power on as a
     * floating point number between 0 and 100
     *
     * On failure, throws an exception or returns Y_DUTYCYCLEATPOWERON_INVALID.
     */
    public double getDutyCycleAtPowerOn()
    {
        return _dutyCycleAtPowerOn;
    }

    public static YPwmOutput FindPwmOutput(String func)
    {
        return YPwmOutput.FindPwmOutput(func);
    }

    public int pulseDurationMove(double ms_target, int ms_duration) throws YAPI_Exception
    {
        return _ypwmoutput.pulseDurationMove(ms_target, ms_duration);
    }

    public int dutyCycleMove(double target, int ms_duration) throws YAPI_Exception
    {
        return _ypwmoutput.dutyCycleMove(target, ms_duration);
    }

    public int frequencyMove(double target, int ms_duration) throws YAPI_Exception
    {
        return _ypwmoutput.frequencyMove(target, ms_duration);
    }

    public int phaseMove(double target, int ms_duration) throws YAPI_Exception
    {
        return _ypwmoutput.phaseMove(target, ms_duration);
    }

    public int triggerPulsesByDuration(double ms_target, int n_pulses) throws YAPI_Exception
    {
        return _ypwmoutput.triggerPulsesByDuration(ms_target, n_pulses);
    }

    public int triggerPulsesByDutyCycle(double target, int n_pulses) throws YAPI_Exception
    {
        return _ypwmoutput.triggerPulsesByDutyCycle(target, n_pulses);
    }

    public int triggerPulsesByFrequency(double target, int n_pulses) throws YAPI_Exception
    {
        return _ypwmoutput.triggerPulsesByFrequency(target, n_pulses);
    }

    public int markForRepeat() throws YAPI_Exception
    {
        return _ypwmoutput.markForRepeat();
    }

    public int repeatFromMark() throws YAPI_Exception
    {
        return _ypwmoutput.repeatFromMark();
    }

//--- (end of YPwmOutput class start)
}

