/*********************************************************************
 *
 * $Id: Current.java 26403 2017-01-16 17:29:21Z seb $
 *
 * Implements Current wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YCurrent;

//--- (YCurrent class start)
/**
 * YCurrent Class: Current function interface
 *
 * The Yoctopuce class YCurrent allows you to read and configure Yoctopuce current
 * sensors. It inherits from YSensor class the core functions to read measurements,
 * register callback functions, access to the autonomous datalogger.
 */
 @SuppressWarnings("UnusedDeclaration")
public class Current extends Sensor
{
// valueCallbackCurrent
// timedReportCallbackCurrent
    protected int _enabled =  YCurrent.ENABLED_INVALID;
    protected YCurrent _ycurrent;

    public Current(YCurrent yfunc)
    {
       super(yfunc);
       _ycurrent = yfunc;
    }

    public Current(String hwid)
    {
       super(hwid);
       _ycurrent = YCurrent.FindCurrent(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _enabled = _ycurrent.get_enabled();
    }
    public int getEnabled()
    {
        return _enabled;
    }

    public void setEnabledBg(int newval) throws YAPI_Exception
    {
        _enabled = newval;
        _ycurrent.set_enabled(newval);
    }

    public static YCurrent FindCurrent(String func)
    {
        return YCurrent.FindCurrent(func);
    }

//--- (end of YCurrent class start)
}

