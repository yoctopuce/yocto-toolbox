/*********************************************************************
 *
 * $Id: Watchdog.java 46698 2021-10-01 06:31:31Z web $
 *
 * Implements Watchdog wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YWatchdog;

//--- (YWatchdog class start)
/**
 * YWatchdog Class: Watchdog function interface
 *
 * The watchog function works like a relay and can cause a brief power cut
 * to an appliance after a preset delay to force this appliance to
 * reset. The Watchdog must be called from time to time to reset the
 * timer and prevent the appliance reset.
 * The watchdog can be driven direcly with <i>pulse</i> and <i>delayedpulse</i> methods to switch
 * off an appliance for a given duration.
 */
 @SuppressWarnings("UnusedDeclaration")
public class Watchdog extends Function
{
// valueCallbackWatchdog
    protected int _state =  YWatchdog.STATE_INVALID;
    protected int _stateAtPowerOn =  YWatchdog.STATEATPOWERON_INVALID;
    protected long _maxTimeOnStateA =  YWatchdog.MAXTIMEONSTATEA_INVALID;
    protected long _maxTimeOnStateB =  YWatchdog.MAXTIMEONSTATEB_INVALID;
    protected int _output =  YWatchdog.OUTPUT_INVALID;
    protected long _pulseTimer =  YWatchdog.PULSETIMER_INVALID;
    protected YWatchdog.YDelayedPulse _delayedPulseTimer = new YWatchdog.YDelayedPulse();
    protected long _countdown =  YWatchdog.COUNTDOWN_INVALID;
    protected int _autoStart =  YWatchdog.AUTOSTART_INVALID;
    protected int _running =  YWatchdog.RUNNING_INVALID;
    protected long _triggerDelay =  YWatchdog.TRIGGERDELAY_INVALID;
    protected long _triggerDuration =  YWatchdog.TRIGGERDURATION_INVALID;
    protected YWatchdog _ywatchdog;

    public Watchdog(YWatchdog yfunc)
    {
       super(yfunc);
       _ywatchdog = yfunc;
    }

    public Watchdog(String hwid)
    {
       super(hwid);
       _ywatchdog = YWatchdog.FindWatchdog(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _state = _ywatchdog.get_state();
        _stateAtPowerOn = _ywatchdog.get_stateAtPowerOn();
        _maxTimeOnStateA = _ywatchdog.get_maxTimeOnStateA();
        _maxTimeOnStateB = _ywatchdog.get_maxTimeOnStateB();
        _output = _ywatchdog.get_output();
        _pulseTimer = _ywatchdog.get_pulseTimer();
        _delayedPulseTimer = _ywatchdog.get_delayedPulseTimer();
        _countdown = _ywatchdog.get_countdown();
        _autoStart = _ywatchdog.get_autoStart();
        _running = _ywatchdog.get_running();
        _triggerDelay = _ywatchdog.get_triggerDelay();
        _triggerDuration = _ywatchdog.get_triggerDuration();
    }
    /**
     * Returns the state of the watchdog (A for the idle position, B for the active position).
     *
     * @return either Y_STATE_A or Y_STATE_B, according to the state of the watchdog (A for the idle
     * position, B for the active position)
     *
     * On failure, throws an exception or returns Y_STATE_INVALID.
     */
    public int getState()
    {
        return _state;
    }

    /**
     * Changes the state of the watchdog (A for the idle position, B for the active position).
     *
     * @param newval : either Y_STATE_A or Y_STATE_B, according to the state of the watchdog (A for the
     * idle position, B for the active position)
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setStateBg(int newval) throws YAPI_Exception
    {
        _state = newval;
        _ywatchdog.set_state(newval);
    }

    /**
     * Returns the state of the watchdog at device startup (A for the idle position, B for the active
     * position, UNCHANGED for no change).
     *
     * @return a value among Y_STATEATPOWERON_UNCHANGED, Y_STATEATPOWERON_A and Y_STATEATPOWERON_B
     * corresponding to the state of the watchdog at device startup (A for the idle position, B for the
     * active position, UNCHANGED for no change)
     *
     * On failure, throws an exception or returns Y_STATEATPOWERON_INVALID.
     */
    public int getStateAtPowerOn()
    {
        return _stateAtPowerOn;
    }

    /**
     * Preset the state of the watchdog at device startup (A for the idle position,
     * B for the active position, UNCHANGED for no modification). Remember to call the matching module saveToFlash()
     * method, otherwise this call will have no effect.
     *
     * @param newval : a value among Y_STATEATPOWERON_UNCHANGED, Y_STATEATPOWERON_A and Y_STATEATPOWERON_B
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setStateAtPowerOnBg(int newval) throws YAPI_Exception
    {
        _stateAtPowerOn = newval;
        _ywatchdog.set_stateAtPowerOn(newval);
    }

    /**
     * Retourne the maximum time (ms) allowed for $THEFUNCTIONS$ to stay in state A before automatically
     * switching back in to B state. Zero means no maximum time.
     *
     * @return an integer
     *
     * On failure, throws an exception or returns Y_MAXTIMEONSTATEA_INVALID.
     */
    public long getMaxTimeOnStateA()
    {
        return _maxTimeOnStateA;
    }

    /**
     * Sets the maximum time (ms) allowed for $THEFUNCTIONS$ to stay in state A before automatically
     * switching back in to B state. Use zero for no maximum time.
     *
     * @param newval : an integer
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setMaxTimeOnStateABg(long newval) throws YAPI_Exception
    {
        _maxTimeOnStateA = newval;
        _ywatchdog.set_maxTimeOnStateA(newval);
    }

    /**
     * Retourne the maximum time (ms) allowed for $THEFUNCTIONS$ to stay in state B before automatically
     * switching back in to A state. Zero means no maximum time.
     *
     * @return an integer
     *
     * On failure, throws an exception or returns Y_MAXTIMEONSTATEB_INVALID.
     */
    public long getMaxTimeOnStateB()
    {
        return _maxTimeOnStateB;
    }

    /**
     * Sets the maximum time (ms) allowed for $THEFUNCTIONS$ to stay in state B before automatically
     * switching back in to A state. Use zero for no maximum time.
     *
     * @param newval : an integer
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setMaxTimeOnStateBBg(long newval) throws YAPI_Exception
    {
        _maxTimeOnStateB = newval;
        _ywatchdog.set_maxTimeOnStateB(newval);
    }

    /**
     * Returns the output state of the watchdog, when used as a simple switch (single throw).
     *
     * @return either Y_OUTPUT_OFF or Y_OUTPUT_ON, according to the output state of the watchdog, when
     * used as a simple switch (single throw)
     *
     * On failure, throws an exception or returns Y_OUTPUT_INVALID.
     */
    public int getOutput()
    {
        return _output;
    }

    /**
     * Changes the output state of the watchdog, when used as a simple switch (single throw).
     *
     * @param newval : either Y_OUTPUT_OFF or Y_OUTPUT_ON, according to the output state of the watchdog,
     * when used as a simple switch (single throw)
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setOutputBg(int newval) throws YAPI_Exception
    {
        _output = newval;
        _ywatchdog.set_output(newval);
    }

    /**
     * Returns the number of milliseconds remaining before the watchdog is returned to idle position
     * (state A), during a measured pulse generation. When there is no ongoing pulse, returns zero.
     *
     * @return an integer corresponding to the number of milliseconds remaining before the watchdog is
     * returned to idle position
     *         (state A), during a measured pulse generation
     *
     * On failure, throws an exception or returns Y_PULSETIMER_INVALID.
     */
    public long getPulseTimer()
    {
        return _pulseTimer;
    }

    public void setPulseTimerBg(long newval) throws YAPI_Exception
    {
        _pulseTimer = newval;
        _ywatchdog.set_pulseTimer(newval);
    }

    public YWatchdog.YDelayedPulse getDelayedPulseTimer()
    {
        return _delayedPulseTimer;
    }

    public void setDelayedPulseTimerBg(YWatchdog.YDelayedPulse newval) throws YAPI_Exception
    {
        _delayedPulseTimer = newval;
        _ywatchdog.set_delayedPulseTimer(newval);
    }

    /**
     * Returns the number of milliseconds remaining before a pulse (delayedPulse() call)
     * When there is no scheduled pulse, returns zero.
     *
     * @return an integer corresponding to the number of milliseconds remaining before a pulse (delayedPulse() call)
     *         When there is no scheduled pulse, returns zero
     *
     * On failure, throws an exception or returns Y_COUNTDOWN_INVALID.
     */
    public long getCountdown()
    {
        return _countdown;
    }

    /**
     * Returns the watchdog runing state at module power on.
     *
     * @return either Y_AUTOSTART_OFF or Y_AUTOSTART_ON, according to the watchdog runing state at module power on
     *
     * On failure, throws an exception or returns Y_AUTOSTART_INVALID.
     */
    public int getAutoStart()
    {
        return _autoStart;
    }

    /**
     * Changes the watchdog runningsttae at module power on. Remember to call the
     * saveToFlash() method and then to reboot the module to apply this setting.
     *
     * @param newval : either Y_AUTOSTART_OFF or Y_AUTOSTART_ON, according to the watchdog runningsttae at
     * module power on
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setAutoStartBg(int newval) throws YAPI_Exception
    {
        _autoStart = newval;
        _ywatchdog.set_autoStart(newval);
    }

    /**
     * Returns the watchdog running state.
     *
     * @return either Y_RUNNING_OFF or Y_RUNNING_ON, according to the watchdog running state
     *
     * On failure, throws an exception or returns Y_RUNNING_INVALID.
     */
    public int getRunning()
    {
        return _running;
    }

    /**
     * Changes the running state of the watchdog.
     *
     * @param newval : either Y_RUNNING_OFF or Y_RUNNING_ON, according to the running state of the watchdog
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setRunningBg(int newval) throws YAPI_Exception
    {
        _running = newval;
        _ywatchdog.set_running(newval);
    }

    /**
     * Returns  the waiting duration before a reset is automatically triggered by the watchdog, in milliseconds.
     *
     * @return an integer corresponding to  the waiting duration before a reset is automatically triggered
     * by the watchdog, in milliseconds
     *
     * On failure, throws an exception or returns Y_TRIGGERDELAY_INVALID.
     */
    public long getTriggerDelay()
    {
        return _triggerDelay;
    }

    /**
     * Changes the waiting delay before a reset is triggered by the watchdog, in milliseconds.
     *
     * @param newval : an integer corresponding to the waiting delay before a reset is triggered by the
     * watchdog, in milliseconds
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setTriggerDelayBg(long newval) throws YAPI_Exception
    {
        _triggerDelay = newval;
        _ywatchdog.set_triggerDelay(newval);
    }

    /**
     * Returns the duration of resets caused by the watchdog, in milliseconds.
     *
     * @return an integer corresponding to the duration of resets caused by the watchdog, in milliseconds
     *
     * On failure, throws an exception or returns Y_TRIGGERDURATION_INVALID.
     */
    public long getTriggerDuration()
    {
        return _triggerDuration;
    }

    /**
     * Changes the duration of resets caused by the watchdog, in milliseconds.
     *
     * @param newval : an integer corresponding to the duration of resets caused by the watchdog, in milliseconds
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setTriggerDurationBg(long newval) throws YAPI_Exception
    {
        _triggerDuration = newval;
        _ywatchdog.set_triggerDuration(newval);
    }

    public static YWatchdog FindWatchdog(String func)
    {
        return YWatchdog.FindWatchdog(func);
    }

//--- (end of YWatchdog class start)
}

