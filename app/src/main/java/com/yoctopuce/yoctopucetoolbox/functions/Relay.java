/*********************************************************************
 *
 * $Id: Relay.java 46698 2021-10-01 06:31:31Z web $
 *
 * Implements Relay wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YRelay;

//--- (YRelay class start)
/**
 * YRelay Class: Relay function interface
 *
 * The Yoctopuce application programming interface allows you to switch the relay state.
 * This change is not persistent: the relay will automatically return to its idle position
 * whenever power is lost or if the module is restarted.
 * The library can also generate automatically short pulses of determined duration.
 * On devices with two output for each relay (double throw), the two outputs are named A and B,
 * with output A corresponding to the idle position (at power off) and the output B corresponding to the
 * active state. If you prefer the alternate default state, simply switch your cables on the board.
 */
 @SuppressWarnings("UnusedDeclaration")
public class Relay extends Function
{
// valueCallbackRelay
    protected int _state =  YRelay.STATE_INVALID;
    protected int _stateAtPowerOn =  YRelay.STATEATPOWERON_INVALID;
    protected long _maxTimeOnStateA =  YRelay.MAXTIMEONSTATEA_INVALID;
    protected long _maxTimeOnStateB =  YRelay.MAXTIMEONSTATEB_INVALID;
    protected int _output =  YRelay.OUTPUT_INVALID;
    protected long _pulseTimer =  YRelay.PULSETIMER_INVALID;
    protected YRelay.YDelayedPulse _delayedPulseTimer = new YRelay.YDelayedPulse();
    protected long _countdown =  YRelay.COUNTDOWN_INVALID;
    protected YRelay _yrelay;

    public Relay(YRelay yfunc)
    {
       super(yfunc);
       _yrelay = yfunc;
    }

    public Relay(String hwid)
    {
       super(hwid);
       _yrelay = YRelay.FindRelay(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _state = _yrelay.get_state();
        _stateAtPowerOn = _yrelay.get_stateAtPowerOn();
        _maxTimeOnStateA = _yrelay.get_maxTimeOnStateA();
        _maxTimeOnStateB = _yrelay.get_maxTimeOnStateB();
        _output = _yrelay.get_output();
        _pulseTimer = _yrelay.get_pulseTimer();
        _delayedPulseTimer = _yrelay.get_delayedPulseTimer();
        _countdown = _yrelay.get_countdown();
    }
    /**
     * Returns the state of the relays (A for the idle position, B for the active position).
     *
     * @return either Y_STATE_A or Y_STATE_B, according to the state of the relays (A for the idle
     * position, B for the active position)
     *
     * On failure, throws an exception or returns Y_STATE_INVALID.
     */
    public int getState()
    {
        return _state;
    }

    /**
     * Changes the state of the relays (A for the idle position, B for the active position).
     *
     * @param newval : either Y_STATE_A or Y_STATE_B, according to the state of the relays (A for the idle
     * position, B for the active position)
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setStateBg(int newval) throws YAPI_Exception
    {
        _state = newval;
        _yrelay.set_state(newval);
    }

    /**
     * Returns the state of the relays at device startup (A for the idle position, B for the active
     * position, UNCHANGED for no change).
     *
     * @return a value among Y_STATEATPOWERON_UNCHANGED, Y_STATEATPOWERON_A and Y_STATEATPOWERON_B
     * corresponding to the state of the relays at device startup (A for the idle position, B for the
     * active position, UNCHANGED for no change)
     *
     * On failure, throws an exception or returns Y_STATEATPOWERON_INVALID.
     */
    public int getStateAtPowerOn()
    {
        return _stateAtPowerOn;
    }

    /**
     * Preset the state of the relays at device startup (A for the idle position,
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
        _yrelay.set_stateAtPowerOn(newval);
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
        _yrelay.set_maxTimeOnStateA(newval);
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
        _yrelay.set_maxTimeOnStateB(newval);
    }

    /**
     * Returns the output state of the relays, when used as a simple switch (single throw).
     *
     * @return either Y_OUTPUT_OFF or Y_OUTPUT_ON, according to the output state of the relays, when used
     * as a simple switch (single throw)
     *
     * On failure, throws an exception or returns Y_OUTPUT_INVALID.
     */
    public int getOutput()
    {
        return _output;
    }

    /**
     * Changes the output state of the relays, when used as a simple switch (single throw).
     *
     * @param newval : either Y_OUTPUT_OFF or Y_OUTPUT_ON, according to the output state of the relays,
     * when used as a simple switch (single throw)
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setOutputBg(int newval) throws YAPI_Exception
    {
        _output = newval;
        _yrelay.set_output(newval);
    }

    /**
     * Returns the number of milliseconds remaining before the relays is returned to idle position
     * (state A), during a measured pulse generation. When there is no ongoing pulse, returns zero.
     *
     * @return an integer corresponding to the number of milliseconds remaining before the relays is
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
        _yrelay.set_pulseTimer(newval);
    }

    public YRelay.YDelayedPulse getDelayedPulseTimer()
    {
        return _delayedPulseTimer;
    }

    public void setDelayedPulseTimerBg(YRelay.YDelayedPulse newval) throws YAPI_Exception
    {
        _delayedPulseTimer = newval;
        _yrelay.set_delayedPulseTimer(newval);
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

    public static YRelay FindRelay(String func)
    {
        return YRelay.FindRelay(func);
    }

//--- (end of YRelay class start)
}

