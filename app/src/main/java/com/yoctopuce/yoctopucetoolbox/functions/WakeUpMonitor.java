/*********************************************************************
 *
 * $Id: WakeUpMonitor.java 46698 2021-10-01 06:31:31Z web $
 *
 * Implements WakeUpMonitor wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YWakeUpMonitor;

//--- (YWakeUpMonitor class start)
/**
 * YWakeUpMonitor Class: WakeUpMonitor function interface
 *
 * The WakeUpMonitor function handles globally all wake-up sources, as well
 * as automated sleep mode.
 */
 @SuppressWarnings("UnusedDeclaration")
public class WakeUpMonitor extends Function
{
// valueCallbackWakeUpMonitor
    protected int _powerDuration =  YWakeUpMonitor.POWERDURATION_INVALID;
    protected int _sleepCountdown =  YWakeUpMonitor.SLEEPCOUNTDOWN_INVALID;
    protected long _nextWakeUp =  YWakeUpMonitor.NEXTWAKEUP_INVALID;
    protected int _wakeUpReason =  YWakeUpMonitor.WAKEUPREASON_INVALID;
    protected int _wakeUpState =  YWakeUpMonitor.WAKEUPSTATE_INVALID;
    protected long _rtcTime =  YWakeUpMonitor.RTCTIME_INVALID;
    public static final int _endOfTime =2145960000;
    protected YWakeUpMonitor _ywakeupmonitor;

    public WakeUpMonitor(YWakeUpMonitor yfunc)
    {
       super(yfunc);
       _ywakeupmonitor = yfunc;
    }

    public WakeUpMonitor(String hwid)
    {
       super(hwid);
       _ywakeupmonitor = YWakeUpMonitor.FindWakeUpMonitor(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _powerDuration = _ywakeupmonitor.get_powerDuration();
        _sleepCountdown = _ywakeupmonitor.get_sleepCountdown();
        _nextWakeUp = _ywakeupmonitor.get_nextWakeUp();
        _wakeUpReason = _ywakeupmonitor.get_wakeUpReason();
        _wakeUpState = _ywakeupmonitor.get_wakeUpState();
        _rtcTime = _ywakeupmonitor.get_rtcTime();
    }
    /**
     * Returns the maximal wake up time (in seconds) before automatically going to sleep.
     *
     * @return an integer corresponding to the maximal wake up time (in seconds) before automatically going to sleep
     *
     * On failure, throws an exception or returns Y_POWERDURATION_INVALID.
     */
    public int getPowerDuration()
    {
        return _powerDuration;
    }

    /**
     * Changes the maximal wake up time (seconds) before automatically going to sleep.
     *
     * @param newval : an integer corresponding to the maximal wake up time (seconds) before automatically
     * going to sleep
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setPowerDurationBg(int newval) throws YAPI_Exception
    {
        _powerDuration = newval;
        _ywakeupmonitor.set_powerDuration(newval);
    }

    /**
     * Returns the delay before the  next sleep period.
     *
     * @return an integer corresponding to the delay before the  next sleep period
     *
     * On failure, throws an exception or returns Y_SLEEPCOUNTDOWN_INVALID.
     */
    public int getSleepCountdown()
    {
        return _sleepCountdown;
    }

    /**
     * Changes the delay before the next sleep period.
     *
     * @param newval : an integer corresponding to the delay before the next sleep period
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setSleepCountdownBg(int newval) throws YAPI_Exception
    {
        _sleepCountdown = newval;
        _ywakeupmonitor.set_sleepCountdown(newval);
    }

    /**
     * Returns the next scheduled wake up date/time (UNIX format).
     *
     * @return an integer corresponding to the next scheduled wake up date/time (UNIX format)
     *
     * On failure, throws an exception or returns Y_NEXTWAKEUP_INVALID.
     */
    public long getNextWakeUp()
    {
        return _nextWakeUp;
    }

    /**
     * Changes the days of the week when a wake up must take place.
     *
     * @param newval : an integer corresponding to the days of the week when a wake up must take place
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setNextWakeUpBg(long newval) throws YAPI_Exception
    {
        _nextWakeUp = newval;
        _ywakeupmonitor.set_nextWakeUp(newval);
    }

    /**
     * Returns the latest wake up reason.
     *
     * @return a value among Y_WAKEUPREASON_USBPOWER, Y_WAKEUPREASON_EXTPOWER, Y_WAKEUPREASON_ENDOFSLEEP,
     * Y_WAKEUPREASON_EXTSIG1, Y_WAKEUPREASON_SCHEDULE1 and Y_WAKEUPREASON_SCHEDULE2 corresponding to the
     * latest wake up reason
     *
     * On failure, throws an exception or returns Y_WAKEUPREASON_INVALID.
     */
    public int getWakeUpReason()
    {
        return _wakeUpReason;
    }

    /**
     * Returns  the current state of the monitor.
     *
     * @return either Y_WAKEUPSTATE_SLEEPING or Y_WAKEUPSTATE_AWAKE, according to  the current state of the monitor
     *
     * On failure, throws an exception or returns Y_WAKEUPSTATE_INVALID.
     */
    public int getWakeUpState()
    {
        return _wakeUpState;
    }

    public void setWakeUpStateBg(int newval) throws YAPI_Exception
    {
        _wakeUpState = newval;
        _ywakeupmonitor.set_wakeUpState(newval);
    }

    public long getRtcTime()
    {
        return _rtcTime;
    }

    public static YWakeUpMonitor FindWakeUpMonitor(String func)
    {
        return YWakeUpMonitor.FindWakeUpMonitor(func);
    }

    public int wakeUp() throws YAPI_Exception
    {
        return _ywakeupmonitor.wakeUp();
    }

    public int sleep(int secBeforeSleep) throws YAPI_Exception
    {
        return _ywakeupmonitor.sleep(secBeforeSleep);
    }

    public int sleepFor(int secUntilWakeUp, int secBeforeSleep) throws YAPI_Exception
    {
        return _ywakeupmonitor.sleepFor(secUntilWakeUp, secBeforeSleep);
    }

    public int sleepUntil(int wakeUpTime, int secBeforeSleep) throws YAPI_Exception
    {
        return _ywakeupmonitor.sleepUntil(wakeUpTime, secBeforeSleep);
    }

    public int resetSleepCountDown() throws YAPI_Exception
    {
        return _ywakeupmonitor.resetSleepCountDown();
    }

//--- (end of YWakeUpMonitor class start)
}

