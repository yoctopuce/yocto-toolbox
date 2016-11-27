/*********************************************************************
 *
 * $Id: CarbonDioxide.java 26014 2016-11-24 13:52:08Z seb $
 *
 * Implements CarbonDioxide wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YCarbonDioxide;

//--- (YCarbonDioxide class start)
/**
 * YCarbonDioxide Class: CarbonDioxide function interface
 *
 * The Yoctopuce class YCarbonDioxide allows you to read and configure Yoctopuce CO2
 * sensors. It inherits from YSensor class the core functions to read measurements,
 * register callback functions, access to the autonomous datalogger.
 * This class adds the ability to perform manual calibration if reuired.
 */
 @SuppressWarnings("UnusedDeclaration")
public class CarbonDioxide extends Sensor
{
// valueCallbackCarbonDioxide
// timedReportCallbackCarbonDioxide
    protected int _abcPeriod =  YCarbonDioxide.ABCPERIOD_INVALID;
    protected String _command =  YCarbonDioxide.COMMAND_INVALID;
    protected YCarbonDioxide _ycarbondioxide;

    public CarbonDioxide(YCarbonDioxide yfunc)
    {
       super(yfunc);
       _ycarbondioxide = yfunc;
    }

    public CarbonDioxide(String hwid)
    {
       super(hwid);
       _ycarbondioxide = YCarbonDioxide.FindCarbonDioxide(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _abcPeriod = _ycarbondioxide.get_abcPeriod();
        _command = _ycarbondioxide.get_command();
    }
    /**
     * Returns the Automatic Baseline Calibration period, in hours. A negative value
     * means that automatic baseline calibration is disabled.
     *
     * @return an integer corresponding to the Automatic Baseline Calibration period, in hours
     *
     * On failure, throws an exception or returns Y_ABCPERIOD_INVALID.
     */
    public int getAbcPeriod()
    {
        return _abcPeriod;
    }

    /**
     * Modifies Automatic Baseline Calibration period, in hours. If you need
     * to disable automatic baseline calibration (for instance when using the
     * sensor in an environment that is constantly above 400ppm CO2), set the
     * period to -1. Remember to call the saveToFlash() method of the
     * module if the modification must be kept.
     *
     * @param newval : an integer
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setAbcPeriodBg(int newval) throws YAPI_Exception
    {
        _abcPeriod = newval;
        _ycarbondioxide.set_abcPeriod(newval);
    }

    public String getCommand()
    {
        return _command;
    }

    public void setCommandBg(String newval) throws YAPI_Exception
    {
        _command = newval;
        _ycarbondioxide.set_command(newval);
    }

//--- (end of YCarbonDioxide class start)
}

