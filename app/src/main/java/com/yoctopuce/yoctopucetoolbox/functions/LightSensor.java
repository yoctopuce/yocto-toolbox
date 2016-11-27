/*********************************************************************
 *
 * $Id: LightSensor.java 26014 2016-11-24 13:52:08Z seb $
 *
 * Implements LightSensor wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YLightSensor;

//--- (YLightSensor class start)
/**
 * YLightSensor Class: LightSensor function interface
 *
 * The Yoctopuce class YLightSensor allows you to read and configure Yoctopuce light
 * sensors. It inherits from YSensor class the core functions to read measurements,
 * register callback functions, access to the autonomous datalogger.
 * This class adds the ability to easily perform a one-point linear calibration
 * to compensate the effect of a glass or filter placed in front of the sensor.
 * For some light sensors with several working modes, this class can select the
 * desired working mode.
 */
 @SuppressWarnings("UnusedDeclaration")
public class LightSensor extends Sensor
{
// valueCallbackLightSensor
// timedReportCallbackLightSensor
    protected int _measureType =  YLightSensor.MEASURETYPE_INVALID;
    protected YLightSensor _ylightsensor;

    public LightSensor(YLightSensor yfunc)
    {
       super(yfunc);
       _ylightsensor = yfunc;
    }

    public LightSensor(String hwid)
    {
       super(hwid);
       _ylightsensor = YLightSensor.FindLightSensor(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _measureType = _ylightsensor.get_measureType();
    }
    public void setCurrentValueBg(double newval) throws YAPI_Exception
    {
        _currentValue = newval;
        _ylightsensor.set_currentValue(newval);
    }

    /**
     * Returns the type of light measure.
     *
     * @return a value among Y_MEASURETYPE_HUMAN_EYE, Y_MEASURETYPE_WIDE_SPECTRUM, Y_MEASURETYPE_INFRARED,
     * Y_MEASURETYPE_HIGH_RATE and Y_MEASURETYPE_HIGH_ENERGY corresponding to the type of light measure
     *
     * On failure, throws an exception or returns Y_MEASURETYPE_INVALID.
     */
    public int getMeasureType()
    {
        return _measureType;
    }

    /**
     * Modify the light sensor type used in the device. The measure can either
     * approximate the response of the human eye, focus on a specific light
     * spectrum, depending on the capabilities of the light-sensitive cell.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : a value among Y_MEASURETYPE_HUMAN_EYE, Y_MEASURETYPE_WIDE_SPECTRUM,
     * Y_MEASURETYPE_INFRARED, Y_MEASURETYPE_HIGH_RATE and Y_MEASURETYPE_HIGH_ENERGY
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setMeasureTypeBg(int newval) throws YAPI_Exception
    {
        _measureType = newval;
        _ylightsensor.set_measureType(newval);
    }

//--- (end of YLightSensor class start)
}

