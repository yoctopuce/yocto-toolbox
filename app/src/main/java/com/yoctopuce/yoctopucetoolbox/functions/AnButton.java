/*********************************************************************
 *
 * $Id: pic24config.php 26169 2016-12-12 01:36:34Z mvuilleu $
 *
 * Implements AnButton wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YAnButton;

//--- (YAnButton class start)
/**
 * YAnButton Class: AnButton function interface
 *
 * Yoctopuce application programming interface allows you to measure the state
 * of a simple button as well as to read an analog potentiometer (variable resistance).
 * This can be use for instance with a continuous rotating knob, a throttle grip
 * or a joystick. The module is capable to calibrate itself on min and max values,
 * in order to compute a calibrated value that varies proportionally with the
 * potentiometer position, regardless of its total resistance.
 */
 @SuppressWarnings("UnusedDeclaration")
public class AnButton extends Function
{
// valueCallbackAnButton
    protected int _calibratedValue =  YAnButton.CALIBRATEDVALUE_INVALID;
    protected int _rawValue =  YAnButton.RAWVALUE_INVALID;
    protected int _analogCalibration =  YAnButton.ANALOGCALIBRATION_INVALID;
    protected int _calibrationMax =  YAnButton.CALIBRATIONMAX_INVALID;
    protected int _calibrationMin =  YAnButton.CALIBRATIONMIN_INVALID;
    protected int _sensitivity =  YAnButton.SENSITIVITY_INVALID;
    protected int _isPressed =  YAnButton.ISPRESSED_INVALID;
    protected long _lastTimePressed =  YAnButton.LASTTIMEPRESSED_INVALID;
    protected long _lastTimeReleased =  YAnButton.LASTTIMERELEASED_INVALID;
    protected long _pulseCounter =  YAnButton.PULSECOUNTER_INVALID;
    protected long _pulseTimer =  YAnButton.PULSETIMER_INVALID;
    protected YAnButton _yanbutton;

    public AnButton(YAnButton yfunc)
    {
       super(yfunc);
       _yanbutton = yfunc;
    }

    public AnButton(String hwid)
    {
       super(hwid);
       _yanbutton = YAnButton.FindAnButton(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _calibratedValue = _yanbutton.get_calibratedValue();
        _rawValue = _yanbutton.get_rawValue();
        _analogCalibration = _yanbutton.get_analogCalibration();
        _calibrationMax = _yanbutton.get_calibrationMax();
        _calibrationMin = _yanbutton.get_calibrationMin();
        _sensitivity = _yanbutton.get_sensitivity();
        _isPressed = _yanbutton.get_isPressed();
        _lastTimePressed = _yanbutton.get_lastTimePressed();
        _lastTimeReleased = _yanbutton.get_lastTimeReleased();
        _pulseCounter = _yanbutton.get_pulseCounter();
        _pulseTimer = _yanbutton.get_pulseTimer();
    }
    /**
     * Returns the current calibrated input value (between 0 and 1000, included).
     *
     * @return an integer corresponding to the current calibrated input value (between 0 and 1000, included)
     *
     * On failure, throws an exception or returns Y_CALIBRATEDVALUE_INVALID.
     */
    public int getCalibratedValue()
    {
        return _calibratedValue;
    }

    /**
     * Returns the current measured input value as-is (between 0 and 4095, included).
     *
     * @return an integer corresponding to the current measured input value as-is (between 0 and 4095, included)
     *
     * On failure, throws an exception or returns Y_RAWVALUE_INVALID.
     */
    public int getRawValue()
    {
        return _rawValue;
    }

    /**
     * Tells if a calibration process is currently ongoing.
     *
     * @return either Y_ANALOGCALIBRATION_OFF or Y_ANALOGCALIBRATION_ON
     *
     * On failure, throws an exception or returns Y_ANALOGCALIBRATION_INVALID.
     */
    public int getAnalogCalibration()
    {
        return _analogCalibration;
    }

    /**
     * Starts or stops the calibration process. Remember to call the saveToFlash()
     * method of the module at the end of the calibration if the modification must be kept.
     *
     * @param newval : either Y_ANALOGCALIBRATION_OFF or Y_ANALOGCALIBRATION_ON
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setAnalogCalibrationBg(int newval) throws YAPI_Exception
    {
        _analogCalibration = newval;
        _yanbutton.set_analogCalibration(newval);
    }

    /**
     * Returns the maximal value measured during the calibration (between 0 and 4095, included).
     *
     * @return an integer corresponding to the maximal value measured during the calibration (between 0
     * and 4095, included)
     *
     * On failure, throws an exception or returns Y_CALIBRATIONMAX_INVALID.
     */
    public int getCalibrationMax()
    {
        return _calibrationMax;
    }

    /**
     * Changes the maximal calibration value for the input (between 0 and 4095, included), without actually
     * starting the automated calibration.  Remember to call the saveToFlash()
     * method of the module if the modification must be kept.
     *
     * @param newval : an integer corresponding to the maximal calibration value for the input (between 0
     * and 4095, included), without actually
     *         starting the automated calibration
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setCalibrationMaxBg(int newval) throws YAPI_Exception
    {
        _calibrationMax = newval;
        _yanbutton.set_calibrationMax(newval);
    }

    /**
     * Returns the minimal value measured during the calibration (between 0 and 4095, included).
     *
     * @return an integer corresponding to the minimal value measured during the calibration (between 0
     * and 4095, included)
     *
     * On failure, throws an exception or returns Y_CALIBRATIONMIN_INVALID.
     */
    public int getCalibrationMin()
    {
        return _calibrationMin;
    }

    /**
     * Changes the minimal calibration value for the input (between 0 and 4095, included), without actually
     * starting the automated calibration.  Remember to call the saveToFlash()
     * method of the module if the modification must be kept.
     *
     * @param newval : an integer corresponding to the minimal calibration value for the input (between 0
     * and 4095, included), without actually
     *         starting the automated calibration
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setCalibrationMinBg(int newval) throws YAPI_Exception
    {
        _calibrationMin = newval;
        _yanbutton.set_calibrationMin(newval);
    }

    /**
     * Returns the sensibility for the input (between 1 and 1000) for triggering user callbacks.
     *
     * @return an integer corresponding to the sensibility for the input (between 1 and 1000) for
     * triggering user callbacks
     *
     * On failure, throws an exception or returns Y_SENSITIVITY_INVALID.
     */
    public int getSensitivity()
    {
        return _sensitivity;
    }

    /**
     * Changes the sensibility for the input (between 1 and 1000) for triggering user callbacks.
     * The sensibility is used to filter variations around a fixed value, but does not preclude the
     * transmission of events when the input value evolves constantly in the same direction.
     * Special case: when the value 1000 is used, the callback will only be thrown when the logical state
     * of the input switches from pressed to released and back.
     * Remember to call the saveToFlash() method of the module if the modification must be kept.
     *
     * @param newval : an integer corresponding to the sensibility for the input (between 1 and 1000) for
     * triggering user callbacks
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setSensitivityBg(int newval) throws YAPI_Exception
    {
        _sensitivity = newval;
        _yanbutton.set_sensitivity(newval);
    }

    /**
     * Returns true if the input (considered as binary) is active (closed contact), and false otherwise.
     *
     * @return either Y_ISPRESSED_FALSE or Y_ISPRESSED_TRUE, according to true if the input (considered as
     * binary) is active (closed contact), and false otherwise
     *
     * On failure, throws an exception or returns Y_ISPRESSED_INVALID.
     */
    public int getIsPressed()
    {
        return _isPressed;
    }

    /**
     * Returns the number of elapsed milliseconds between the module power on and the last time
     * the input button was pressed (the input contact transitioned from open to closed).
     *
     * @return an integer corresponding to the number of elapsed milliseconds between the module power on
     * and the last time
     *         the input button was pressed (the input contact transitioned from open to closed)
     *
     * On failure, throws an exception or returns Y_LASTTIMEPRESSED_INVALID.
     */
    public long getLastTimePressed()
    {
        return _lastTimePressed;
    }

    /**
     * Returns the number of elapsed milliseconds between the module power on and the last time
     * the input button was released (the input contact transitioned from closed to open).
     *
     * @return an integer corresponding to the number of elapsed milliseconds between the module power on
     * and the last time
     *         the input button was released (the input contact transitioned from closed to open)
     *
     * On failure, throws an exception or returns Y_LASTTIMERELEASED_INVALID.
     */
    public long getLastTimeReleased()
    {
        return _lastTimeReleased;
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
        _yanbutton.set_pulseCounter(newval);
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

    public static YAnButton FindAnButton(String func)
    {
        return YAnButton.FindAnButton(func);
    }

    public int resetCounter() throws YAPI_Exception
    {
        return _yanbutton.resetCounter();
    }

//--- (end of YAnButton class start)
}

