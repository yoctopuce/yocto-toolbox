/*********************************************************************
 *
 * $Id: pic24config.php 26169 2016-12-12 01:36:34Z mvuilleu $
 *
 * Implements RealTimeClock wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YRealTimeClock;

//--- (YRealTimeClock class start)
/**
 * YRealTimeClock Class: Real Time Clock function interface
 *
 * The RealTimeClock function maintains and provides current date and time, even accross power cut
 * lasting several days. It is the base for automated wake-up functions provided by the WakeUpScheduler.
 * The current time may represent a local time as well as an UTC time, but no automatic time change
 * will occur to account for daylight saving time.
 */
 @SuppressWarnings("UnusedDeclaration")
public class RealTimeClock extends Function
{
// valueCallbackRealTimeClock
    protected long _unixTime =  YRealTimeClock.UNIXTIME_INVALID;
    protected String _dateTime =  YRealTimeClock.DATETIME_INVALID;
    protected int _utcOffset =  YRealTimeClock.UTCOFFSET_INVALID;
    protected int _timeSet =  YRealTimeClock.TIMESET_INVALID;
    protected YRealTimeClock _yrealtimeclock;

    public RealTimeClock(YRealTimeClock yfunc)
    {
       super(yfunc);
       _yrealtimeclock = yfunc;
    }

    public RealTimeClock(String hwid)
    {
       super(hwid);
       _yrealtimeclock = YRealTimeClock.FindRealTimeClock(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _unixTime = _yrealtimeclock.get_unixTime();
        _dateTime = _yrealtimeclock.get_dateTime();
        _utcOffset = _yrealtimeclock.get_utcOffset();
        _timeSet = _yrealtimeclock.get_timeSet();
    }
    /**
     * Returns the current time in Unix format (number of elapsed seconds since Jan 1st, 1970).
     *
     * @return an integer corresponding to the current time in Unix format (number of elapsed seconds
     * since Jan 1st, 1970)
     *
     * On failure, throws an exception or returns Y_UNIXTIME_INVALID.
     */
    public long getUnixTime()
    {
        return _unixTime;
    }

    /**
     * Changes the current time. Time is specifid in Unix format (number of elapsed seconds since Jan 1st, 1970).
     *
     * @param newval : an integer corresponding to the current time
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setUnixTimeBg(long newval) throws YAPI_Exception
    {
        _unixTime = newval;
        _yrealtimeclock.set_unixTime(newval);
    }

    /**
     * Returns the current time in the form "YYYY/MM/DD hh:mm:ss".
     *
     * @return a string corresponding to the current time in the form "YYYY/MM/DD hh:mm:ss"
     *
     * On failure, throws an exception or returns Y_DATETIME_INVALID.
     */
    public String getDateTime()
    {
        return _dateTime;
    }

    /**
     * Returns the number of seconds between current time and UTC time (time zone).
     *
     * @return an integer corresponding to the number of seconds between current time and UTC time (time zone)
     *
     * On failure, throws an exception or returns Y_UTCOFFSET_INVALID.
     */
    public int getUtcOffset()
    {
        return _utcOffset;
    }

    /**
     * Changes the number of seconds between current time and UTC time (time zone).
     * The timezone is automatically rounded to the nearest multiple of 15 minutes.
     *
     * @param newval : an integer corresponding to the number of seconds between current time and UTC time (time zone)
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setUtcOffsetBg(int newval) throws YAPI_Exception
    {
        _utcOffset = newval;
        _yrealtimeclock.set_utcOffset(newval);
    }

    /**
     * Returns true if the clock has been set, and false otherwise.
     *
     * @return either Y_TIMESET_FALSE or Y_TIMESET_TRUE, according to true if the clock has been set, and
     * false otherwise
     *
     * On failure, throws an exception or returns Y_TIMESET_INVALID.
     */
    public int getTimeSet()
    {
        return _timeSet;
    }

    public static YRealTimeClock FindRealTimeClock(String func)
    {
        return YRealTimeClock.FindRealTimeClock(func);
    }

//--- (end of YRealTimeClock class start)
}

