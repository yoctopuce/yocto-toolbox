/*********************************************************************
 *
 * $Id: ColorLedCluster.java 26014 2016-11-24 13:52:08Z seb $
 *
 * Implements ColorLedCluster wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YColorLedCluster;

//--- (YColorLedCluster class start)
/**
 * YColorLedCluster Class: ColorLedCluster function interface
 *
 * The Yoctopuce application programming interface
 * allows you to drive a color LED cluster. Unlike the ColorLed class, the ColorLedCluster
 * allows to handle several LEDs at one. Color changes can be done   using RGB coordinates as well as
 * HSL coordinates.
 * The module performs all conversions form RGB to HSL automatically. It is then
 * self-evident to turn on a LED with a given hue and to progressively vary its
 * saturation or lightness. If needed, you can find more information on the
 * difference between RGB and HSL in the section following this one.
 */
 @SuppressWarnings("UnusedDeclaration")
public class ColorLedCluster extends Function
{
// valueCallbackColorLedCluster
    protected int _activeLedCount =  YColorLedCluster.ACTIVELEDCOUNT_INVALID;
    protected int _maxLedCount =  YColorLedCluster.MAXLEDCOUNT_INVALID;
    protected int _blinkSeqMaxCount =  YColorLedCluster.BLINKSEQMAXCOUNT_INVALID;
    protected int _blinkSeqMaxSize =  YColorLedCluster.BLINKSEQMAXSIZE_INVALID;
    protected String _command =  YColorLedCluster.COMMAND_INVALID;
    protected YColorLedCluster _ycolorledcluster;

    public ColorLedCluster(YColorLedCluster yfunc)
    {
       super(yfunc);
       _ycolorledcluster = yfunc;
    }

    public ColorLedCluster(String hwid)
    {
       super(hwid);
       _ycolorledcluster = YColorLedCluster.FindColorLedCluster(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _activeLedCount = _ycolorledcluster.get_activeLedCount();
        _maxLedCount = _ycolorledcluster.get_maxLedCount();
        _blinkSeqMaxCount = _ycolorledcluster.get_blinkSeqMaxCount();
        _blinkSeqMaxSize = _ycolorledcluster.get_blinkSeqMaxSize();
        _command = _ycolorledcluster.get_command();
    }
    /**
     * Returns the number of LEDs currently handled by the device.
     *
     * @return an integer corresponding to the number of LEDs currently handled by the device
     *
     * On failure, throws an exception or returns Y_ACTIVELEDCOUNT_INVALID.
     */
    public int getActiveLedCount()
    {
        return _activeLedCount;
    }

    /**
     * Changes the number of LEDs currently handled by the device.
     *
     * @param newval : an integer corresponding to the number of LEDs currently handled by the device
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setActiveLedCountBg(int newval) throws YAPI_Exception
    {
        _activeLedCount = newval;
        _ycolorledcluster.set_activeLedCount(newval);
    }

    /**
     * Returns the maximum number of LEDs that the device can handle.
     *
     * @return an integer corresponding to the maximum number of LEDs that the device can handle
     *
     * On failure, throws an exception or returns Y_MAXLEDCOUNT_INVALID.
     */
    public int getMaxLedCount()
    {
        return _maxLedCount;
    }

    /**
     * Returns the maximum number of sequences that the device can handle.
     *
     * @return an integer corresponding to the maximum number of sequences that the device can handle
     *
     * On failure, throws an exception or returns Y_BLINKSEQMAXCOUNT_INVALID.
     */
    public int getBlinkSeqMaxCount()
    {
        return _blinkSeqMaxCount;
    }

    /**
     * Returns the maximum length of sequences.
     *
     * @return an integer corresponding to the maximum length of sequences
     *
     * On failure, throws an exception or returns Y_BLINKSEQMAXSIZE_INVALID.
     */
    public int getBlinkSeqMaxSize()
    {
        return _blinkSeqMaxSize;
    }

    public String getCommand()
    {
        return _command;
    }

    public void setCommandBg(String newval) throws YAPI_Exception
    {
        _command = newval;
        _ycolorledcluster.set_command(newval);
    }

//--- (end of YColorLedCluster class start)
}

