/*********************************************************************
 *
 * $Id: pic24config.php 26169 2016-12-12 01:36:34Z mvuilleu $
 *
 * Implements StepperMotor wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YStepperMotor;

//--- (YStepperMotor class start)
/**
 * YStepperMotor Class: StepperMotor function interface
 *
 * The Yoctopuce application programming interface allows you to drive a stepper motor.
 */
 @SuppressWarnings("UnusedDeclaration")
public class StepperMotor extends Function
{
// valueCallbackStepperMotor
    protected int _motorState =  YStepperMotor.MOTORSTATE_INVALID;
    protected int _diags =  YStepperMotor.DIAGS_INVALID;
    protected double _stepPos =  YStepperMotor.STEPPOS_INVALID;
    protected double _speed =  YStepperMotor.SPEED_INVALID;
    protected double _pullinSpeed =  YStepperMotor.PULLINSPEED_INVALID;
    protected double _maxAccel =  YStepperMotor.MAXACCEL_INVALID;
    protected double _maxSpeed =  YStepperMotor.MAXSPEED_INVALID;
    protected int _stepping =  YStepperMotor.STEPPING_INVALID;
    protected int _overcurrent =  YStepperMotor.OVERCURRENT_INVALID;
    protected int _tCurrStop =  YStepperMotor.TCURRSTOP_INVALID;
    protected int _tCurrRun =  YStepperMotor.TCURRRUN_INVALID;
    protected String _alertMode =  YStepperMotor.ALERTMODE_INVALID;
    protected String _auxMode =  YStepperMotor.AUXMODE_INVALID;
    protected int _auxSignal =  YStepperMotor.AUXSIGNAL_INVALID;
    protected String _command =  YStepperMotor.COMMAND_INVALID;
    protected YStepperMotor _ysteppermotor;

    public StepperMotor(YStepperMotor yfunc)
    {
       super(yfunc);
       _ysteppermotor = yfunc;
    }

    public StepperMotor(String hwid)
    {
       super(hwid);
       _ysteppermotor = YStepperMotor.FindStepperMotor(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _motorState = _ysteppermotor.get_motorState();
        _diags = _ysteppermotor.get_diags();
        _stepPos = _ysteppermotor.get_stepPos();
        _speed = _ysteppermotor.get_speed();
        _pullinSpeed = _ysteppermotor.get_pullinSpeed();
        _maxAccel = _ysteppermotor.get_maxAccel();
        _maxSpeed = _ysteppermotor.get_maxSpeed();
        _stepping = _ysteppermotor.get_stepping();
        _overcurrent = _ysteppermotor.get_overcurrent();
        _tCurrStop = _ysteppermotor.get_tCurrStop();
        _tCurrRun = _ysteppermotor.get_tCurrRun();
        _alertMode = _ysteppermotor.get_alertMode();
        _auxMode = _ysteppermotor.get_auxMode();
        _auxSignal = _ysteppermotor.get_auxSignal();
        _command = _ysteppermotor.get_command();
    }
    /**
     * Returns the motor working state.
     *
     * @return a value among Y_MOTORSTATE_ABSENT, Y_MOTORSTATE_ALERT, Y_MOTORSTATE_HI_Z,
     * Y_MOTORSTATE_STOP, Y_MOTORSTATE_RUN and Y_MOTORSTATE_BATCH corresponding to the motor working state
     *
     * On failure, throws an exception or returns Y_MOTORSTATE_INVALID.
     */
    public int getMotorState()
    {
        return _motorState;
    }

    /**
     * Returns the stepper motor controller diagnostics, as a bitmap.
     *
     * @return an integer corresponding to the stepper motor controller diagnostics, as a bitmap
     *
     * On failure, throws an exception or returns Y_DIAGS_INVALID.
     */
    public int getDiags()
    {
        return _diags;
    }

    /**
     * Changes the current logical motor position, measured in steps.
     * This command does not cause any motor move, as its purpose is only to setup
     * the origin of the position counter. The fractional part of the position,
     * that corresponds to the physical position of the rotor, is not changed.
     * To trigger a motor move, use methods moveTo() or moveRel()
     * instead.
     *
     * @param newval : a floating point number corresponding to the current logical motor position, measured in steps
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setStepPosBg(double newval) throws YAPI_Exception
    {
        _stepPos = newval;
        _ysteppermotor.set_stepPos(newval);
    }

    /**
     * Returns the current logical motor position, measured in steps.
     * The value may include a fractional part when micro-stepping is in use.
     *
     * @return a floating point number corresponding to the current logical motor position, measured in steps
     *
     * On failure, throws an exception or returns Y_STEPPOS_INVALID.
     */
    public double getStepPos()
    {
        return _stepPos;
    }

    /**
     * Returns current motor speed, measured in steps per second.
     * To change speed, use method changeSpeed().
     *
     * @return a floating point number corresponding to current motor speed, measured in steps per second
     *
     * On failure, throws an exception or returns Y_SPEED_INVALID.
     */
    public double getSpeed()
    {
        return _speed;
    }

    /**
     * Changes the motor speed immediately reachable from stop state, measured in steps per second.
     *
     * @param newval : a floating point number corresponding to the motor speed immediately reachable from
     * stop state, measured in steps per second
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setPullinSpeedBg(double newval) throws YAPI_Exception
    {
        _pullinSpeed = newval;
        _ysteppermotor.set_pullinSpeed(newval);
    }

    /**
     * Returns the motor speed immediately reachable from stop state, measured in steps per second.
     *
     * @return a floating point number corresponding to the motor speed immediately reachable from stop
     * state, measured in steps per second
     *
     * On failure, throws an exception or returns Y_PULLINSPEED_INVALID.
     */
    public double getPullinSpeed()
    {
        return _pullinSpeed;
    }

    /**
     * Changes the maximal motor acceleration, measured in steps per second^2.
     *
     * @param newval : a floating point number corresponding to the maximal motor acceleration, measured
     * in steps per second^2
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setMaxAccelBg(double newval) throws YAPI_Exception
    {
        _maxAccel = newval;
        _ysteppermotor.set_maxAccel(newval);
    }

    /**
     * Returns the maximal motor acceleration, measured in steps per second^2.
     *
     * @return a floating point number corresponding to the maximal motor acceleration, measured in steps per second^2
     *
     * On failure, throws an exception or returns Y_MAXACCEL_INVALID.
     */
    public double getMaxAccel()
    {
        return _maxAccel;
    }

    /**
     * Changes the maximal motor speed, measured in steps per second.
     *
     * @param newval : a floating point number corresponding to the maximal motor speed, measured in steps per second
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setMaxSpeedBg(double newval) throws YAPI_Exception
    {
        _maxSpeed = newval;
        _ysteppermotor.set_maxSpeed(newval);
    }

    /**
     * Returns the maximal motor speed, measured in steps per second.
     *
     * @return a floating point number corresponding to the maximal motor speed, measured in steps per second
     *
     * On failure, throws an exception or returns Y_MAXSPEED_INVALID.
     */
    public double getMaxSpeed()
    {
        return _maxSpeed;
    }

    /**
     * Returns the stepping mode used to drive the motor.
     *
     * @return a value among Y_STEPPING_MICROSTEP16, Y_STEPPING_MICROSTEP8, Y_STEPPING_MICROSTEP4,
     * Y_STEPPING_HALFSTEP and Y_STEPPING_FULLSTEP corresponding to the stepping mode used to drive the motor
     *
     * On failure, throws an exception or returns Y_STEPPING_INVALID.
     */
    public int getStepping()
    {
        return _stepping;
    }

    /**
     * Changes the stepping mode used to drive the motor.
     *
     * @param newval : a value among Y_STEPPING_MICROSTEP16, Y_STEPPING_MICROSTEP8, Y_STEPPING_MICROSTEP4,
     * Y_STEPPING_HALFSTEP and Y_STEPPING_FULLSTEP corresponding to the stepping mode used to drive the motor
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setSteppingBg(int newval) throws YAPI_Exception
    {
        _stepping = newval;
        _ysteppermotor.set_stepping(newval);
    }

    /**
     * Returns the overcurrent alert and emergency stop threshold, measured in mA.
     *
     * @return an integer corresponding to the overcurrent alert and emergency stop threshold, measured in mA
     *
     * On failure, throws an exception or returns Y_OVERCURRENT_INVALID.
     */
    public int getOvercurrent()
    {
        return _overcurrent;
    }

    /**
     * Changes the overcurrent alert and emergency stop threshold, measured in mA.
     *
     * @param newval : an integer corresponding to the overcurrent alert and emergency stop threshold, measured in mA
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setOvercurrentBg(int newval) throws YAPI_Exception
    {
        _overcurrent = newval;
        _ysteppermotor.set_overcurrent(newval);
    }

    /**
     * Returns the torque regulation current when the motor is stopped, measured in mA.
     *
     * @return an integer corresponding to the torque regulation current when the motor is stopped, measured in mA
     *
     * On failure, throws an exception or returns Y_TCURRSTOP_INVALID.
     */
    public int getTCurrStop()
    {
        return _tCurrStop;
    }

    /**
     * Changes the torque regulation current when the motor is stopped, measured in mA.
     *
     * @param newval : an integer corresponding to the torque regulation current when the motor is
     * stopped, measured in mA
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setTCurrStopBg(int newval) throws YAPI_Exception
    {
        _tCurrStop = newval;
        _ysteppermotor.set_tCurrStop(newval);
    }

    /**
     * Returns the torque regulation current when the motor is running, measured in mA.
     *
     * @return an integer corresponding to the torque regulation current when the motor is running, measured in mA
     *
     * On failure, throws an exception or returns Y_TCURRRUN_INVALID.
     */
    public int getTCurrRun()
    {
        return _tCurrRun;
    }

    /**
     * Changes the torque regulation current when the motor is running, measured in mA.
     *
     * @param newval : an integer corresponding to the torque regulation current when the motor is
     * running, measured in mA
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setTCurrRunBg(int newval) throws YAPI_Exception
    {
        _tCurrRun = newval;
        _ysteppermotor.set_tCurrRun(newval);
    }

    public String getAlertMode()
    {
        return _alertMode;
    }

    public void setAlertModeBg(String newval) throws YAPI_Exception
    {
        _alertMode = newval;
        _ysteppermotor.set_alertMode(newval);
    }

    public String getAuxMode()
    {
        return _auxMode;
    }

    public void setAuxModeBg(String newval) throws YAPI_Exception
    {
        _auxMode = newval;
        _ysteppermotor.set_auxMode(newval);
    }

    /**
     * Returns the current value of the signal generated on the auxiliary output.
     *
     * @return an integer corresponding to the current value of the signal generated on the auxiliary output
     *
     * On failure, throws an exception or returns Y_AUXSIGNAL_INVALID.
     */
    public int getAuxSignal()
    {
        return _auxSignal;
    }

    /**
     * Changes the value of the signal generated on the auxiliary output.
     * Acceptable values depend on the auxiliary output signal type configured.
     *
     * @param newval : an integer corresponding to the value of the signal generated on the auxiliary output
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setAuxSignalBg(int newval) throws YAPI_Exception
    {
        _auxSignal = newval;
        _ysteppermotor.set_auxSignal(newval);
    }

    public String getCommand()
    {
        return _command;
    }

    public void setCommandBg(String newval) throws YAPI_Exception
    {
        _command = newval;
        _ysteppermotor.set_command(newval);
    }

    public static YStepperMotor FindStepperMotor(String func)
    {
        return YStepperMotor.FindStepperMotor(func);
    }

    public int sendCommand(String command) throws YAPI_Exception
    {
        return _ysteppermotor.sendCommand(command);
    }

    public int reset() throws YAPI_Exception
    {
        return _ysteppermotor.reset();
    }

    public int findHomePosition(double speed) throws YAPI_Exception
    {
        return _ysteppermotor.findHomePosition(speed);
    }

    public int changeSpeed(double speed) throws YAPI_Exception
    {
        return _ysteppermotor.changeSpeed(speed);
    }

    public int moveTo(double absPos) throws YAPI_Exception
    {
        return _ysteppermotor.moveTo(absPos);
    }

    public int moveRel(double relPos) throws YAPI_Exception
    {
        return _ysteppermotor.moveRel(relPos);
    }

    public int pause(int waitMs) throws YAPI_Exception
    {
        return _ysteppermotor.pause(waitMs);
    }

    public int emergencyStop() throws YAPI_Exception
    {
        return _ysteppermotor.emergencyStop();
    }

    public int alertStepOut() throws YAPI_Exception
    {
        return _ysteppermotor.alertStepOut();
    }

    public int abortAndBrake() throws YAPI_Exception
    {
        return _ysteppermotor.abortAndBrake();
    }

    public int abortAndHiZ() throws YAPI_Exception
    {
        return _ysteppermotor.abortAndHiZ();
    }

//--- (end of YStepperMotor class start)
}

