/*********************************************************************
 *
 * $Id: DataLogger.java 46698 2021-10-01 06:31:31Z web $
 *
 * Implements DataLogger wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YDataLogger;
import com.yoctopuce.YoctoAPI.YDataSet;

import java.util.ArrayList;

//--- (generated code: YDataLogger class start)
/**
 * YDataLogger Class: DataLogger function interface
 *
 * Yoctopuce sensors include a non-volatile memory capable of storing ongoing measured
 * data automatically, without requiring a permanent connection to a computer.
 * The DataLogger function controls the global parameters of the internal data
 * logger.
 */
 @SuppressWarnings("UnusedDeclaration")
public class DataLogger extends Function
{
// valueCallbackDataLogger
    protected int _currentRunIndex =  YDataLogger.CURRENTRUNINDEX_INVALID;
    protected long _timeUTC =  YDataLogger.TIMEUTC_INVALID;
    protected int _recording =  YDataLogger.RECORDING_INVALID;
    protected int _autoStart =  YDataLogger.AUTOSTART_INVALID;
    protected int _beaconDriven =  YDataLogger.BEACONDRIVEN_INVALID;
    protected int _clearHistory =  YDataLogger.CLEARHISTORY_INVALID;
    protected YDataLogger _ydatalogger;

    public DataLogger(YDataLogger yfunc)
    {
       super(yfunc);
       _ydatalogger = yfunc;
    }

    public DataLogger(String hwid)
    {
       super(hwid);
       _ydatalogger = YDataLogger.FindDataLogger(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _currentRunIndex = _ydatalogger.get_currentRunIndex();
        _timeUTC = _ydatalogger.get_timeUTC();
        _recording = _ydatalogger.get_recording();
        _autoStart = _ydatalogger.get_autoStart();
        _beaconDriven = _ydatalogger.get_beaconDriven();
        _clearHistory = _ydatalogger.get_clearHistory();
    }
    /**
     * Returns the current run number, corresponding to the number of times the module was
     * powered on with the dataLogger enabled at some point.
     *
     * @return an integer corresponding to the current run number, corresponding to the number of times the module was
     *         powered on with the dataLogger enabled at some point
     *
     * On failure, throws an exception or returns Y_CURRENTRUNINDEX_INVALID.
     */
    public int getCurrentRunIndex()
    {
        return _currentRunIndex;
    }

    /**
     * Returns the Unix timestamp for current UTC time, if known.
     *
     * @return an integer corresponding to the Unix timestamp for current UTC time, if known
     *
     * On failure, throws an exception or returns Y_TIMEUTC_INVALID.
     */
    public long getTimeUTC()
    {
        return _timeUTC;
    }

    /**
     * Changes the current UTC time reference used for recorded data.
     *
     * @param newval : an integer corresponding to the current UTC time reference used for recorded data
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setTimeUTCBg(long newval) throws YAPI_Exception
    {
        _timeUTC = newval;
        _ydatalogger.set_timeUTC(newval);
    }

    /**
     * Returns the current activation state of the data logger.
     *
     * @return a value among Y_RECORDING_OFF, Y_RECORDING_ON and Y_RECORDING_PENDING corresponding to the
     * current activation state of the data logger
     *
     * On failure, throws an exception or returns Y_RECORDING_INVALID.
     */
    public int getRecording()
    {
        return _recording;
    }

    /**
     * Changes the activation state of the data logger to start/stop recording data.
     *
     * @param newval : a value among Y_RECORDING_OFF, Y_RECORDING_ON and Y_RECORDING_PENDING corresponding
     * to the activation state of the data logger to start/stop recording data
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setRecordingBg(int newval) throws YAPI_Exception
    {
        _recording = newval;
        _ydatalogger.set_recording(newval);
    }

    /**
     * Returns the default activation state of the data logger on power up.
     *
     * @return either Y_AUTOSTART_OFF or Y_AUTOSTART_ON, according to the default activation state of the
     * data logger on power up
     *
     * On failure, throws an exception or returns Y_AUTOSTART_INVALID.
     */
    public int getAutoStart()
    {
        return _autoStart;
    }

    /**
     * Changes the default activation state of the data logger on power up.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : either Y_AUTOSTART_OFF or Y_AUTOSTART_ON, according to the default activation state
     * of the data logger on power up
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setAutoStartBg(int newval) throws YAPI_Exception
    {
        _autoStart = newval;
        _ydatalogger.set_autoStart(newval);
    }

    /**
     * Returns true if the data logger is synchronised with the localization beacon.
     *
     * @return either Y_BEACONDRIVEN_OFF or Y_BEACONDRIVEN_ON, according to true if the data logger is
     * synchronised with the localization beacon
     *
     * On failure, throws an exception or returns Y_BEACONDRIVEN_INVALID.
     */
    public int getBeaconDriven()
    {
        return _beaconDriven;
    }

    /**
     * Changes the type of synchronisation of the data logger.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : either Y_BEACONDRIVEN_OFF or Y_BEACONDRIVEN_ON, according to the type of
     * synchronisation of the data logger
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setBeaconDrivenBg(int newval) throws YAPI_Exception
    {
        _beaconDriven = newval;
        _ydatalogger.set_beaconDriven(newval);
    }

    public int getClearHistory()
    {
        return _clearHistory;
    }

    public void setClearHistoryBg(int newval) throws YAPI_Exception
    {
        _clearHistory = newval;
        _ydatalogger.set_clearHistory(newval);
    }

    public static YDataLogger FindDataLogger(String func)
    {
        return YDataLogger.FindDataLogger(func);
    }

    public int forgetAllDataStreams() throws YAPI_Exception
    {
        return _ydatalogger.forgetAllDataStreams();
    }

    public ArrayList<YDataSet> get_dataSets() throws YAPI_Exception
    {
        return _ydatalogger.get_dataSets();
    }

    public ArrayList<YDataSet> parse_dataSets(byte[] json) throws YAPI_Exception
    {
        return _ydatalogger.parse_dataSets(json);
    }

//--- (end of generated code: YDataLogger class start)
}

