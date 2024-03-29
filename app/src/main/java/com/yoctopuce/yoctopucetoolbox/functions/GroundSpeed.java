/*********************************************************************
 *
 * $Id: GroundSpeed.java 46698 2021-10-01 06:31:31Z web $
 *
 * Implements GroundSpeed wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YGroundSpeed;

//--- (YGroundSpeed class start)
/**
 * YGroundSpeed Class: GroundSpeed function interface
 *
 * The Yoctopuce class YGroundSpeed allows you to read the ground speed from Yoctopuce
 * geolocalization sensors. It inherits from the YSensor class the core functions to
 * read measurements, register callback functions, access the autonomous
 * datalogger.
 */
 @SuppressWarnings("UnusedDeclaration")
public class GroundSpeed extends Sensor
{
// valueCallbackGroundSpeed
// timedReportCallbackGroundSpeed
    protected YGroundSpeed _ygroundspeed;

    public GroundSpeed(YGroundSpeed yfunc)
    {
       super(yfunc);
       _ygroundspeed = yfunc;
    }

    public GroundSpeed(String hwid)
    {
       super(hwid);
       _ygroundspeed = YGroundSpeed.FindGroundSpeed(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
    }
    public static YGroundSpeed FindGroundSpeed(String func)
    {
        return YGroundSpeed.FindGroundSpeed(func);
    }

//--- (end of YGroundSpeed class start)
}

