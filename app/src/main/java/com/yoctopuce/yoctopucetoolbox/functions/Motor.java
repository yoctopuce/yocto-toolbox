/*********************************************************************
 *
 * $Id: Motor.java 46698 2021-10-01 06:31:31Z web $
 *
 * Implements Motor wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YMotor;

//--- (YMotor class start)
/**
 * YMotor Class: Motor function interface
 *
 * Yoctopuce application programming interface allows you to drive the
 * power sent to the motor to make it turn both ways, but also to drive accelerations
 * and decelerations. The motor will then accelerate automatically: you will not
 * have to monitor it. The API also allows to slow down the motor by shortening
 * its terminals: the motor will then act as an electromagnetic brake.
 */
 @SuppressWarnings("UnusedDeclaration")
public class Motor extends Function
{
// valueCallbackMotor
    protected int _motorStatus =  YMotor.MOTORSTATUS_INVALID;
    protected double _drivingForce =  YMotor.DRIVINGFORCE_INVALID;
    protected double _brakingForce =  YMotor.BRAKINGFORCE_INVALID;
    protected double _cutOffVoltage =  YMotor.CUTOFFVOLTAGE_INVALID;
    protected int _overCurrentLimit =  YMotor.OVERCURRENTLIMIT_INVALID;
    protected double _frequency =  YMotor.FREQUENCY_INVALID;
    protected int _starterTime =  YMotor.STARTERTIME_INVALID;
    protected int _failSafeTimeout =  YMotor.FAILSAFETIMEOUT_INVALID;
    protected String _command =  YMotor.COMMAND_INVALID;
    protected YMotor _ymotor;

    public Motor(YMotor yfunc)
    {
       super(yfunc);
       _ymotor = yfunc;
    }

    public Motor(String hwid)
    {
       super(hwid);
       _ymotor = YMotor.FindMotor(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _motorStatus = _ymotor.get_motorStatus();
        _drivingForce = _ymotor.get_drivingForce();
        _brakingForce = _ymotor.get_brakingForce();
        _cutOffVoltage = _ymotor.get_cutOffVoltage();
        _overCurrentLimit = _ymotor.get_overCurrentLimit();
        _frequency = _ymotor.get_frequency();
        _starterTime = _ymotor.get_starterTime();
        _failSafeTimeout = _ymotor.get_failSafeTimeout();
        _command = _ymotor.get_command();
    }
    /**
     * Return the controller state. Possible states are:
     * IDLE   when the motor is stopped/in free wheel, ready to start;
     * FORWD  when the controller is driving the motor forward;
     * BACKWD when the controller is driving the motor backward;
     * BRAKE  when the controller is braking;
     * LOVOLT when the controller has detected a low voltage condition;
     * HICURR when the controller has detected an overcurrent condition;
     * HIHEAT when the controller has detected an overheat condition;
     * FAILSF when the controller switched on the failsafe security.
     *
     * When an error condition occurred (LOVOLT, HICURR, HIHEAT, FAILSF), the controller
     * status must be explicitly reset using the resetStatus function.
     *
     * @return a value among Y_MOTORSTATUS_IDLE, Y_MOTORSTATUS_BRAKE, Y_MOTORSTATUS_FORWD,
     * Y_MOTORSTATUS_BACKWD, Y_MOTORSTATUS_LOVOLT, Y_MOTORSTATUS_HICURR, Y_MOTORSTATUS_HIHEAT and Y_MOTORSTATUS_FAILSF
     *
     * On failure, throws an exception or returns Y_MOTORSTATUS_INVALID.
     */
    public int getMotorStatus()
    {
        return _motorStatus;
    }

    public void setMotorStatusBg(int newval) throws YAPI_Exception
    {
        _motorStatus = newval;
        _ymotor.set_motorStatus(newval);
    }

    /**
     * Changes immediately the power sent to the motor. The value is a percentage between -100%
     * to 100%. If you want go easy on your mechanics and avoid excessive current consumption,
     * try to avoid brutal power changes. For example, immediate transition from forward full power
     * to reverse full power is a very bad idea. Each time the driving power is modified, the
     * braking power is set to zero.
     *
     * @param newval : a floating point number corresponding to immediately the power sent to the motor
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setDrivingForceBg(double newval) throws YAPI_Exception
    {
        _drivingForce = newval;
        _ymotor.set_drivingForce(newval);
    }

    /**
     * Returns the power sent to the motor, as a percentage between -100% and +100%.
     *
     * @return a floating point number corresponding to the power sent to the motor, as a percentage
     * between -100% and +100%
     *
     * On failure, throws an exception or returns Y_DRIVINGFORCE_INVALID.
     */
    public double getDrivingForce()
    {
        return _drivingForce;
    }

    /**
     * Changes immediately the braking force applied to the motor (in percents).
     * The value 0 corresponds to no braking (free wheel). When the braking force
     * is changed, the driving power is set to zero. The value is a percentage.
     *
     * @param newval : a floating point number corresponding to immediately the braking force applied to
     * the motor (in percents)
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setBrakingForceBg(double newval) throws YAPI_Exception
    {
        _brakingForce = newval;
        _ymotor.set_brakingForce(newval);
    }

    /**
     * Returns the braking force applied to the motor, as a percentage.
     * The value 0 corresponds to no braking (free wheel).
     *
     * @return a floating point number corresponding to the braking force applied to the motor, as a percentage
     *
     * On failure, throws an exception or returns Y_BRAKINGFORCE_INVALID.
     */
    public double getBrakingForce()
    {
        return _brakingForce;
    }

    /**
     * Changes the threshold voltage under which the controller automatically switches to error state
     * and prevents further current draw. This setting prevent damage to a battery that can
     * occur when drawing current from an "empty" battery.
     * Note that whatever the cutoff threshold, the controller switches to undervoltage
     * error state if the power supply goes under 3V, even for a very brief time.
     *
     * @param newval : a floating point number corresponding to the threshold voltage under which the
     * controller automatically switches to error state
     *         and prevents further current draw
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setCutOffVoltageBg(double newval) throws YAPI_Exception
    {
        _cutOffVoltage = newval;
        _ymotor.set_cutOffVoltage(newval);
    }

    /**
     * Returns the threshold voltage under which the controller automatically switches to error state
     * and prevents further current draw. This setting prevents damage to a battery that can
     * occur when drawing current from an "empty" battery.
     *
     * @return a floating point number corresponding to the threshold voltage under which the controller
     * automatically switches to error state
     *         and prevents further current draw
     *
     * On failure, throws an exception or returns Y_CUTOFFVOLTAGE_INVALID.
     */
    public double getCutOffVoltage()
    {
        return _cutOffVoltage;
    }

    /**
     * Returns the current threshold (in mA) above which the controller automatically
     * switches to error state. A zero value means that there is no limit.
     *
     * @return an integer corresponding to the current threshold (in mA) above which the controller automatically
     *         switches to error state
     *
     * On failure, throws an exception or returns Y_OVERCURRENTLIMIT_INVALID.
     */
    public int getOverCurrentLimit()
    {
        return _overCurrentLimit;
    }

    /**
     * Changes the current threshold (in mA) above which the controller automatically
     * switches to error state. A zero value means that there is no limit. Note that whatever the
     * current limit is, the controller switches to OVERCURRENT status if the current
     * goes above 32A, even for a very brief time.
     *
     * @param newval : an integer corresponding to the current threshold (in mA) above which the
     * controller automatically
     *         switches to error state
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setOverCurrentLimitBg(int newval) throws YAPI_Exception
    {
        _overCurrentLimit = newval;
        _ymotor.set_overCurrentLimit(newval);
    }

    /**
     * Changes the PWM frequency used to control the motor. Low frequency is usually
     * more efficient and may help the motor to start, but an audible noise might be
     * generated. A higher frequency reduces the noise, but more energy is converted
     * into heat.
     *
     * @param newval : a floating point number corresponding to the PWM frequency used to control the motor
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setFrequencyBg(double newval) throws YAPI_Exception
    {
        _frequency = newval;
        _ymotor.set_frequency(newval);
    }

    /**
     * Returns the PWM frequency used to control the motor.
     *
     * @return a floating point number corresponding to the PWM frequency used to control the motor
     *
     * On failure, throws an exception or returns Y_FREQUENCY_INVALID.
     */
    public double getFrequency()
    {
        return _frequency;
    }

    /**
     * Returns the duration (in ms) during which the motor is driven at low frequency to help
     * it start up.
     *
     * @return an integer corresponding to the duration (in ms) during which the motor is driven at low
     * frequency to help
     *         it start up
     *
     * On failure, throws an exception or returns Y_STARTERTIME_INVALID.
     */
    public int getStarterTime()
    {
        return _starterTime;
    }

    /**
     * Changes the duration (in ms) during which the motor is driven at low frequency to help
     * it start up.
     *
     * @param newval : an integer corresponding to the duration (in ms) during which the motor is driven
     * at low frequency to help
     *         it start up
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setStarterTimeBg(int newval) throws YAPI_Exception
    {
        _starterTime = newval;
        _ymotor.set_starterTime(newval);
    }

    /**
     * Returns the delay in milliseconds allowed for the controller to run autonomously without
     * receiving any instruction from the control process. When this delay has elapsed,
     * the controller automatically stops the motor and switches to FAILSAFE error.
     * Failsafe security is disabled when the value is zero.
     *
     * @return an integer corresponding to the delay in milliseconds allowed for the controller to run
     * autonomously without
     *         receiving any instruction from the control process
     *
     * On failure, throws an exception or returns Y_FAILSAFETIMEOUT_INVALID.
     */
    public int getFailSafeTimeout()
    {
        return _failSafeTimeout;
    }

    /**
     * Changes the delay in milliseconds allowed for the controller to run autonomously without
     * receiving any instruction from the control process. When this delay has elapsed,
     * the controller automatically stops the motor and switches to FAILSAFE error.
     * Failsafe security is disabled when the value is zero.
     *
     * @param newval : an integer corresponding to the delay in milliseconds allowed for the controller to
     * run autonomously without
     *         receiving any instruction from the control process
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setFailSafeTimeoutBg(int newval) throws YAPI_Exception
    {
        _failSafeTimeout = newval;
        _ymotor.set_failSafeTimeout(newval);
    }

    public String getCommand()
    {
        return _command;
    }

    public void setCommandBg(String newval) throws YAPI_Exception
    {
        _command = newval;
        _ymotor.set_command(newval);
    }

    public static YMotor FindMotor(String func)
    {
        return YMotor.FindMotor(func);
    }

    public int keepALive() throws YAPI_Exception
    {
        return _ymotor.keepALive();
    }

    public int resetStatus() throws YAPI_Exception
    {
        return _ymotor.resetStatus();
    }

    public int drivingForceMove(double targetPower, int delay) throws YAPI_Exception
    {
        return _ymotor.drivingForceMove(targetPower, delay);
    }

    public int brakingForceMove(double targetPower, int delay) throws YAPI_Exception
    {
        return _ymotor.brakingForceMove(targetPower, delay);
    }

//--- (end of YMotor class start)
}

