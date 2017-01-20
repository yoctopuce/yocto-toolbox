/*********************************************************************
 *
 * $Id: pic24config.php 26169 2016-12-12 01:36:34Z mvuilleu $
 *
 * Implements Led wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YLed;

//--- (YLed class start)
/**
 * YLed Class: Led function interface
 *
 * The Yoctopuce application programming interface
 * allows you not only to drive the intensity of the LED, but also to
 * have it blink at various preset frequencies.
 */
 @SuppressWarnings("UnusedDeclaration")
public class Led extends Function
{
// valueCallbackLed
    protected int _power =  YLed.POWER_INVALID;
    protected int _luminosity =  YLed.LUMINOSITY_INVALID;
    protected int _blinking =  YLed.BLINKING_INVALID;
    protected YLed _yled;

    public Led(YLed yfunc)
    {
       super(yfunc);
       _yled = yfunc;
    }

    public Led(String hwid)
    {
       super(hwid);
       _yled = YLed.FindLed(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _power = _yled.get_power();
        _luminosity = _yled.get_luminosity();
        _blinking = _yled.get_blinking();
    }
    /**
     * Returns the current LED state.
     *
     * @return either Y_POWER_OFF or Y_POWER_ON, according to the current LED state
     *
     * On failure, throws an exception or returns Y_POWER_INVALID.
     */
    public int getPower()
    {
        return _power;
    }

    /**
     * Changes the state of the LED.
     *
     * @param newval : either Y_POWER_OFF or Y_POWER_ON, according to the state of the LED
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setPowerBg(int newval) throws YAPI_Exception
    {
        _power = newval;
        _yled.set_power(newval);
    }

    /**
     * Returns the current LED intensity (in per cent).
     *
     * @return an integer corresponding to the current LED intensity (in per cent)
     *
     * On failure, throws an exception or returns Y_LUMINOSITY_INVALID.
     */
    public int getLuminosity()
    {
        return _luminosity;
    }

    /**
     * Changes the current LED intensity (in per cent).
     *
     * @param newval : an integer corresponding to the current LED intensity (in per cent)
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setLuminosityBg(int newval) throws YAPI_Exception
    {
        _luminosity = newval;
        _yled.set_luminosity(newval);
    }

    /**
     * Returns the current LED signaling mode.
     *
     * @return a value among Y_BLINKING_STILL, Y_BLINKING_RELAX, Y_BLINKING_AWARE, Y_BLINKING_RUN,
     * Y_BLINKING_CALL and Y_BLINKING_PANIC corresponding to the current LED signaling mode
     *
     * On failure, throws an exception or returns Y_BLINKING_INVALID.
     */
    public int getBlinking()
    {
        return _blinking;
    }

    /**
     * Changes the current LED signaling mode.
     *
     * @param newval : a value among Y_BLINKING_STILL, Y_BLINKING_RELAX, Y_BLINKING_AWARE, Y_BLINKING_RUN,
     * Y_BLINKING_CALL and Y_BLINKING_PANIC corresponding to the current LED signaling mode
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setBlinkingBg(int newval) throws YAPI_Exception
    {
        _blinking = newval;
        _yled.set_blinking(newval);
    }

    public static YLed FindLed(String func)
    {
        return YLed.FindLed(func);
    }

//--- (end of YLed class start)
}

