/*********************************************************************
 *
 * $Id: DualPower.java 26014 2016-11-24 13:52:08Z seb $
 *
 * Implements DualPower wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YDualPower;

//--- (YDualPower class start)
/**
 * YDualPower Class: External power supply control interface
 *
 * Yoctopuce application programming interface allows you to control
 * the power source to use for module functions that require high current.
 * The module can also automatically disconnect the external power
 * when a voltage drop is observed on the external power source
 * (external battery running out of power).
 */
 @SuppressWarnings("UnusedDeclaration")
public class DualPower extends Function
{
// valueCallbackDualPower
    protected int _powerState =  YDualPower.POWERSTATE_INVALID;
    protected int _powerControl =  YDualPower.POWERCONTROL_INVALID;
    protected int _extVoltage =  YDualPower.EXTVOLTAGE_INVALID;
    protected YDualPower _ydualpower;

    public DualPower(YDualPower yfunc)
    {
       super(yfunc);
       _ydualpower = yfunc;
    }

    public DualPower(String hwid)
    {
       super(hwid);
       _ydualpower = YDualPower.FindDualPower(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _powerState = _ydualpower.get_powerState();
        _powerControl = _ydualpower.get_powerControl();
        _extVoltage = _ydualpower.get_extVoltage();
    }
    /**
     * Returns the current power source for module functions that require lots of current.
     *
     * @return a value among Y_POWERSTATE_OFF, Y_POWERSTATE_FROM_USB and Y_POWERSTATE_FROM_EXT
     * corresponding to the current power source for module functions that require lots of current
     *
     * On failure, throws an exception or returns Y_POWERSTATE_INVALID.
     */
    public int getPowerState()
    {
        return _powerState;
    }

    /**
     * Returns the selected power source for module functions that require lots of current.
     *
     * @return a value among Y_POWERCONTROL_AUTO, Y_POWERCONTROL_FROM_USB, Y_POWERCONTROL_FROM_EXT and
     * Y_POWERCONTROL_OFF corresponding to the selected power source for module functions that require lots of current
     *
     * On failure, throws an exception or returns Y_POWERCONTROL_INVALID.
     */
    public int getPowerControl()
    {
        return _powerControl;
    }

    /**
     * Changes the selected power source for module functions that require lots of current.
     *
     * @param newval : a value among Y_POWERCONTROL_AUTO, Y_POWERCONTROL_FROM_USB, Y_POWERCONTROL_FROM_EXT
     * and Y_POWERCONTROL_OFF corresponding to the selected power source for module functions that require
     * lots of current
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setPowerControlBg(int newval) throws YAPI_Exception
    {
        _powerControl = newval;
        _ydualpower.set_powerControl(newval);
    }

    /**
     * Returns the measured voltage on the external power source, in millivolts.
     *
     * @return an integer corresponding to the measured voltage on the external power source, in millivolts
     *
     * On failure, throws an exception or returns Y_EXTVOLTAGE_INVALID.
     */
    public int getExtVoltage()
    {
        return _extVoltage;
    }

//--- (end of YDualPower class start)
}

