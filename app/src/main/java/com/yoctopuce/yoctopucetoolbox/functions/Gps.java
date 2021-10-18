/*********************************************************************
 *
 * $Id: Gps.java 46698 2021-10-01 06:31:31Z web $
 *
 * Implements Gps wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YGps;

//--- (YGps class start)
/**
 * YGps Class: GPS function interface
 *
 * The Gps function allows you to extract positionning
 * data from the GPS device. This class can provides
 * complete positionning information: However, if you
 * whish to define callbacks on position changes, you
 * should use the YLatitude et YLongitude classes.
 */
 @SuppressWarnings("UnusedDeclaration")
public class Gps extends Function
{
// valueCallbackGps
    protected int _isFixed =  YGps.ISFIXED_INVALID;
    protected long _satCount =  YGps.SATCOUNT_INVALID;
    protected int _coordSystem =  YGps.COORDSYSTEM_INVALID;
    protected String _latitude =  YGps.LATITUDE_INVALID;
    protected String _longitude =  YGps.LONGITUDE_INVALID;
    protected double _dilution =  YGps.DILUTION_INVALID;
    protected double _altitude =  YGps.ALTITUDE_INVALID;
    protected double _groundSpeed =  YGps.GROUNDSPEED_INVALID;
    protected double _direction =  YGps.DIRECTION_INVALID;
    protected long _unixTime =  YGps.UNIXTIME_INVALID;
    protected String _dateTime =  YGps.DATETIME_INVALID;
    protected int _utcOffset =  YGps.UTCOFFSET_INVALID;
    protected String _command =  YGps.COMMAND_INVALID;
    protected YGps _ygps;

    public Gps(YGps yfunc)
    {
       super(yfunc);
       _ygps = yfunc;
    }

    public Gps(String hwid)
    {
       super(hwid);
       _ygps = YGps.FindGps(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _isFixed = _ygps.get_isFixed();
        _satCount = _ygps.get_satCount();
        _coordSystem = _ygps.get_coordSystem();
        _latitude = _ygps.get_latitude();
        _longitude = _ygps.get_longitude();
        _dilution = _ygps.get_dilution();
        _altitude = _ygps.get_altitude();
        _groundSpeed = _ygps.get_groundSpeed();
        _direction = _ygps.get_direction();
        _unixTime = _ygps.get_unixTime();
        _dateTime = _ygps.get_dateTime();
        _utcOffset = _ygps.get_utcOffset();
        _command = _ygps.get_command();
    }
    /**
     * Returns TRUE if the receiver has found enough satellites to work.
     *
     * @return either Y_ISFIXED_FALSE or Y_ISFIXED_TRUE, according to TRUE if the receiver has found
     * enough satellites to work
     *
     * On failure, throws an exception or returns Y_ISFIXED_INVALID.
     */
    public int getIsFixed()
    {
        return _isFixed;
    }

    /**
     * Returns the count of visible satellites.
     *
     * @return an integer corresponding to the count of visible satellites
     *
     * On failure, throws an exception or returns Y_SATCOUNT_INVALID.
     */
    public long getSatCount()
    {
        return _satCount;
    }

    /**
     * Returns the representation system used for positioning data.
     *
     * @return a value among Y_COORDSYSTEM_GPS_DMS, Y_COORDSYSTEM_GPS_DM and Y_COORDSYSTEM_GPS_D
     * corresponding to the representation system used for positioning data
     *
     * On failure, throws an exception or returns Y_COORDSYSTEM_INVALID.
     */
    public int getCoordSystem()
    {
        return _coordSystem;
    }

    /**
     * Changes the representation system used for positioning data.
     *
     * @param newval : a value among Y_COORDSYSTEM_GPS_DMS, Y_COORDSYSTEM_GPS_DM and Y_COORDSYSTEM_GPS_D
     * corresponding to the representation system used for positioning data
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setCoordSystemBg(int newval) throws YAPI_Exception
    {
        _coordSystem = newval;
        _ygps.set_coordSystem(newval);
    }

    /**
     * Returns the current latitude.
     *
     * @return a string corresponding to the current latitude
     *
     * On failure, throws an exception or returns Y_LATITUDE_INVALID.
     */
    public String getLatitude()
    {
        return _latitude;
    }

    /**
     * Returns the current longitude.
     *
     * @return a string corresponding to the current longitude
     *
     * On failure, throws an exception or returns Y_LONGITUDE_INVALID.
     */
    public String getLongitude()
    {
        return _longitude;
    }

    /**
     * Returns the current horizontal dilution of precision,
     * the smaller that number is, the better .
     *
     * @return a floating point number corresponding to the current horizontal dilution of precision,
     *         the smaller that number is, the better
     *
     * On failure, throws an exception or returns Y_DILUTION_INVALID.
     */
    public double getDilution()
    {
        return _dilution;
    }

    /**
     * Returns the current altitude. Beware:  GPS technology
     * is very inaccurate regarding altitude.
     *
     * @return a floating point number corresponding to the current altitude
     *
     * On failure, throws an exception or returns Y_ALTITUDE_INVALID.
     */
    public double getAltitude()
    {
        return _altitude;
    }

    /**
     * Returns the current ground speed in Km/h.
     *
     * @return a floating point number corresponding to the current ground speed in Km/h
     *
     * On failure, throws an exception or returns Y_GROUNDSPEED_INVALID.
     */
    public double getGroundSpeed()
    {
        return _groundSpeed;
    }

    /**
     * Returns the current move bearing in degrees, zero
     * is the true (geographic) north.
     *
     * @return a floating point number corresponding to the current move bearing in degrees, zero
     *         is the true (geographic) north
     *
     * On failure, throws an exception or returns Y_DIRECTION_INVALID.
     */
    public double getDirection()
    {
        return _direction;
    }

    /**
     * Returns the current time in Unix format (number of
     * seconds elapsed since Jan 1st, 1970).
     *
     * @return an integer corresponding to the current time in Unix format (number of
     *         seconds elapsed since Jan 1st, 1970)
     *
     * On failure, throws an exception or returns Y_UNIXTIME_INVALID.
     */
    public long getUnixTime()
    {
        return _unixTime;
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
     * If current UTC time is known, the current time is automatically be updated according to the selected time zone.
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
        _ygps.set_utcOffset(newval);
    }

    public String getCommand()
    {
        return _command;
    }

    public void setCommandBg(String newval) throws YAPI_Exception
    {
        _command = newval;
        _ygps.set_command(newval);
    }

    public static YGps FindGps(String func)
    {
        return YGps.FindGps(func);
    }

//--- (end of YGps class start)
}

