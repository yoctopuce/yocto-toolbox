/*********************************************************************
 *
 * $Id: Servo.java 26014 2016-11-24 13:52:08Z seb $
 *
 * Implements Servo wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YServo;

//--- (YServo class start)
/**
 * YServo Class: Servo function interface
 *
 * Yoctopuce application programming interface allows you not only to move
 * a servo to a given position, but also to specify the time interval
 * in which the move should be performed. This makes it possible to
 * synchronize two servos involved in a same move.
 */
 @SuppressWarnings("UnusedDeclaration")
public class Servo extends Function
{
// valueCallbackServo
    protected int _position =  YServo.POSITION_INVALID;
    protected int _enabled =  YServo.ENABLED_INVALID;
    protected int _range =  YServo.RANGE_INVALID;
    protected int _neutral =  YServo.NEUTRAL_INVALID;
    protected YServo.YMove _move = new YServo.YMove();
    protected int _positionAtPowerOn =  YServo.POSITIONATPOWERON_INVALID;
    protected int _enabledAtPowerOn =  YServo.ENABLEDATPOWERON_INVALID;
    protected YServo _yservo;

    public Servo(YServo yfunc)
    {
       super(yfunc);
       _yservo = yfunc;
    }

    public Servo(String hwid)
    {
       super(hwid);
       _yservo = YServo.FindServo(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _position = _yservo.get_position();
        _enabled = _yservo.get_enabled();
        _range = _yservo.get_range();
        _neutral = _yservo.get_neutral();
        _move = _yservo.get_move();
        _positionAtPowerOn = _yservo.get_positionAtPowerOn();
        _enabledAtPowerOn = _yservo.get_enabledAtPowerOn();
    }
    /**
     * Returns the current servo position.
     *
     * @return an integer corresponding to the current servo position
     *
     * On failure, throws an exception or returns Y_POSITION_INVALID.
     */
    public int getPosition()
    {
        return _position;
    }

    /**
     * Changes immediately the servo driving position.
     *
     * @param newval : an integer corresponding to immediately the servo driving position
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setPositionBg(int newval) throws YAPI_Exception
    {
        _position = newval;
        _yservo.set_position(newval);
    }

    /**
     * Returns the state of the servos.
     *
     * @return either Y_ENABLED_FALSE or Y_ENABLED_TRUE, according to the state of the servos
     *
     * On failure, throws an exception or returns Y_ENABLED_INVALID.
     */
    public int getEnabled()
    {
        return _enabled;
    }

    /**
     * Stops or starts the servo.
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
        _yservo.set_enabled(newval);
    }

    /**
     * Returns the current range of use of the servo.
     *
     * @return an integer corresponding to the current range of use of the servo
     *
     * On failure, throws an exception or returns Y_RANGE_INVALID.
     */
    public int getRange()
    {
        return _range;
    }

    /**
     * Changes the range of use of the servo, specified in per cents.
     * A range of 100% corresponds to a standard control signal, that varies
     * from 1 [ms] to 2 [ms], When using a servo that supports a double range,
     * from 0.5 [ms] to 2.5 [ms], you can select a range of 200%.
     * Be aware that using a range higher than what is supported by the servo
     * is likely to damage the servo. Remember to call the matching module
     * saveToFlash() method, otherwise this call will have no effect.
     *
     * @param newval : an integer corresponding to the range of use of the servo, specified in per cents
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setRangeBg(int newval) throws YAPI_Exception
    {
        _range = newval;
        _yservo.set_range(newval);
    }

    /**
     * Returns the duration in microseconds of a neutral pulse for the servo.
     *
     * @return an integer corresponding to the duration in microseconds of a neutral pulse for the servo
     *
     * On failure, throws an exception or returns Y_NEUTRAL_INVALID.
     */
    public int getNeutral()
    {
        return _neutral;
    }

    /**
     * Changes the duration of the pulse corresponding to the neutral position of the servo.
     * The duration is specified in microseconds, and the standard value is 1500 [us].
     * This setting makes it possible to shift the range of use of the servo.
     * Be aware that using a range higher than what is supported by the servo is
     * likely to damage the servo. Remember to call the matching module
     * saveToFlash() method, otherwise this call will have no effect.
     *
     * @param newval : an integer corresponding to the duration of the pulse corresponding to the neutral
     * position of the servo
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setNeutralBg(int newval) throws YAPI_Exception
    {
        _neutral = newval;
        _yservo.set_neutral(newval);
    }

    public YServo.YMove getMove()
    {
        return _move;
    }

    public void setMoveBg(YServo.YMove newval) throws YAPI_Exception
    {
        _move = newval;
        _yservo.set_move(newval);
    }

    /**
     * Returns the servo position at device power up.
     *
     * @return an integer corresponding to the servo position at device power up
     *
     * On failure, throws an exception or returns Y_POSITIONATPOWERON_INVALID.
     */
    public int getPositionAtPowerOn()
    {
        return _positionAtPowerOn;
    }

    /**
     * Configure the servo position at device power up. Remember to call the matching
     * module saveToFlash() method, otherwise this call will have no effect.
     *
     * @param newval : an integer
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setPositionAtPowerOnBg(int newval) throws YAPI_Exception
    {
        _positionAtPowerOn = newval;
        _yservo.set_positionAtPowerOn(newval);
    }

    /**
     * Returns the servo signal generator state at power up.
     *
     * @return either Y_ENABLEDATPOWERON_FALSE or Y_ENABLEDATPOWERON_TRUE, according to the servo signal
     * generator state at power up
     *
     * On failure, throws an exception or returns Y_ENABLEDATPOWERON_INVALID.
     */
    public int getEnabledAtPowerOn()
    {
        return _enabledAtPowerOn;
    }

    /**
     * Configure the servo signal generator state at power up. Remember to call the matching module saveToFlash()
     * method, otherwise this call will have no effect.
     *
     * @param newval : either Y_ENABLEDATPOWERON_FALSE or Y_ENABLEDATPOWERON_TRUE
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setEnabledAtPowerOnBg(int newval) throws YAPI_Exception
    {
        _enabledAtPowerOn = newval;
        _yservo.set_enabledAtPowerOn(newval);
    }

//--- (end of YServo class start)
}

