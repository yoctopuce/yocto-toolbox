/*********************************************************************
 *
 * $Id: PwmPowerSource.java 26014 2016-11-24 13:52:08Z seb $
 *
 * Implements PwmPowerSource wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YPwmPowerSource;

//--- (YPwmPowerSource class start)
/**
 * YPwmPowerSource Class: PwmPowerSource function interface
 *
 * The Yoctopuce application programming interface allows you to configure
 * the voltage source used by all PWM on the same device.
 */
 @SuppressWarnings("UnusedDeclaration")
public class PwmPowerSource extends Function
{
// valueCallbackPwmPowerSource
    protected int _powerMode =  YPwmPowerSource.POWERMODE_INVALID;
    protected YPwmPowerSource _ypwmpowersource;

    public PwmPowerSource(YPwmPowerSource yfunc)
    {
       super(yfunc);
       _ypwmpowersource = yfunc;
    }

    public PwmPowerSource(String hwid)
    {
       super(hwid);
       _ypwmpowersource = YPwmPowerSource.FindPwmPowerSource(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _powerMode = _ypwmpowersource.get_powerMode();
    }
    /**
     * Returns the selected power source for the PWM on the same device.
     *
     * @return a value among Y_POWERMODE_USB_5V, Y_POWERMODE_USB_3V, Y_POWERMODE_EXT_V and
     * Y_POWERMODE_OPNDRN corresponding to the selected power source for the PWM on the same device
     *
     * On failure, throws an exception or returns Y_POWERMODE_INVALID.
     */
    public int getPowerMode()
    {
        return _powerMode;
    }

    /**
     * Changes  the PWM power source. PWM can use isolated 5V from USB, isolated 3V from USB or
     * voltage from an external power source. The PWM can also work in open drain  mode. In that
     * mode, the PWM actively pulls the line down.
     * Warning: this setting is common to all PWM on the same device. If you change that parameter,
     * all PWM located on the same device are  affected.
     * If you want the change to be kept after a device reboot, make sure  to call the matching
     * module saveToFlash().
     *
     * @param newval : a value among Y_POWERMODE_USB_5V, Y_POWERMODE_USB_3V, Y_POWERMODE_EXT_V and
     * Y_POWERMODE_OPNDRN corresponding to  the PWM power source
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setPowerModeBg(int newval) throws YAPI_Exception
    {
        _powerMode = newval;
        _ypwmpowersource.set_powerMode(newval);
    }

//--- (end of YPwmPowerSource class start)
}

