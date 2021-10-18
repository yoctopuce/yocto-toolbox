/*********************************************************************
 *
 * $Id: ColorLed.java 46698 2021-10-01 06:31:31Z web $
 *
 * Implements ColorLed wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YColorLed;

//--- (YColorLed class start)
/**
 * YColorLed Class: ColorLed function interface
 *
 * The Yoctopuce application programming interface
 * allows you to drive a color LED using RGB coordinates as well as HSL coordinates.
 * The module performs all conversions form RGB to HSL automatically. It is then
 * self-evident to turn on a LED with a given hue and to progressively vary its
 * saturation or lightness. If needed, you can find more information on the
 * difference between RGB and HSL in the section following this one.
 */
 @SuppressWarnings("UnusedDeclaration")
public class ColorLed extends Function
{
// valueCallbackColorLed
    protected int _rgbColor =  YColorLed.RGBCOLOR_INVALID;
    protected int _hslColor =  YColorLed.HSLCOLOR_INVALID;
    protected YColorLed.YMove _rgbMove = new YColorLed.YMove();
    protected YColorLed.YMove _hslMove = new YColorLed.YMove();
    protected int _rgbColorAtPowerOn =  YColorLed.RGBCOLORATPOWERON_INVALID;
    protected int _blinkSeqSize =  YColorLed.BLINKSEQSIZE_INVALID;
    protected int _blinkSeqMaxSize =  YColorLed.BLINKSEQMAXSIZE_INVALID;
    protected int _blinkSeqSignature =  YColorLed.BLINKSEQSIGNATURE_INVALID;
    protected String _command =  YColorLed.COMMAND_INVALID;
    protected YColorLed _ycolorled;

    public ColorLed(YColorLed yfunc)
    {
       super(yfunc);
       _ycolorled = yfunc;
    }

    public ColorLed(String hwid)
    {
       super(hwid);
       _ycolorled = YColorLed.FindColorLed(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _rgbColor = _ycolorled.get_rgbColor();
        _hslColor = _ycolorled.get_hslColor();
        _rgbMove = _ycolorled.get_rgbMove();
        _hslMove = _ycolorled.get_hslMove();
        _rgbColorAtPowerOn = _ycolorled.get_rgbColorAtPowerOn();
        _blinkSeqSize = _ycolorled.get_blinkSeqSize();
        _blinkSeqMaxSize = _ycolorled.get_blinkSeqMaxSize();
        _blinkSeqSignature = _ycolorled.get_blinkSeqSignature();
        _command = _ycolorled.get_command();
    }
    /**
     * Returns the current RGB color of the LED.
     *
     * @return an integer corresponding to the current RGB color of the LED
     *
     * On failure, throws an exception or returns Y_RGBCOLOR_INVALID.
     */
    public int getRgbColor()
    {
        return _rgbColor;
    }

    /**
     * Changes the current color of the LED, using an RGB color. Encoding is done as follows: 0xRRGGBB.
     *
     * @param newval : an integer corresponding to the current color of the LED, using an RGB color
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setRgbColorBg(int newval) throws YAPI_Exception
    {
        _rgbColor = newval;
        _ycolorled.set_rgbColor(newval);
    }

    /**
     * Returns the current HSL color of the LED.
     *
     * @return an integer corresponding to the current HSL color of the LED
     *
     * On failure, throws an exception or returns Y_HSLCOLOR_INVALID.
     */
    public int getHslColor()
    {
        return _hslColor;
    }

    /**
     * Changes the current color of the LED, using a color HSL. Encoding is done as follows: 0xHHSSLL.
     *
     * @param newval : an integer corresponding to the current color of the LED, using a color HSL
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setHslColorBg(int newval) throws YAPI_Exception
    {
        _hslColor = newval;
        _ycolorled.set_hslColor(newval);
    }

    public YColorLed.YMove getRgbMove()
    {
        return _rgbMove;
    }

    public void setRgbMoveBg(YColorLed.YMove newval) throws YAPI_Exception
    {
        _rgbMove = newval;
        _ycolorled.set_rgbMove(newval);
    }

    public YColorLed.YMove getHslMove()
    {
        return _hslMove;
    }

    public void setHslMoveBg(YColorLed.YMove newval) throws YAPI_Exception
    {
        _hslMove = newval;
        _ycolorled.set_hslMove(newval);
    }

    /**
     * Returns the configured color to be displayed when the module is turned on.
     *
     * @return an integer corresponding to the configured color to be displayed when the module is turned on
     *
     * On failure, throws an exception or returns Y_RGBCOLORATPOWERON_INVALID.
     */
    public int getRgbColorAtPowerOn()
    {
        return _rgbColorAtPowerOn;
    }

    /**
     * Changes the color that the LED will display by default when the module is turned on.
     *
     * @param newval : an integer corresponding to the color that the LED will display by default when the
     * module is turned on
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setRgbColorAtPowerOnBg(int newval) throws YAPI_Exception
    {
        _rgbColorAtPowerOn = newval;
        _ycolorled.set_rgbColorAtPowerOn(newval);
    }

    /**
     * Returns the current length of the blinking sequence.
     *
     * @return an integer corresponding to the current length of the blinking sequence
     *
     * On failure, throws an exception or returns Y_BLINKSEQSIZE_INVALID.
     */
    public int getBlinkSeqSize()
    {
        return _blinkSeqSize;
    }

    /**
     * Returns the maximum length of the blinking sequence.
     *
     * @return an integer corresponding to the maximum length of the blinking sequence
     *
     * On failure, throws an exception or returns Y_BLINKSEQMAXSIZE_INVALID.
     */
    public int getBlinkSeqMaxSize()
    {
        return _blinkSeqMaxSize;
    }

    /**
     * Return the blinking sequence signature. Since blinking
     * sequences cannot be read from the device, this can be used
     * to detect if a specific blinking sequence is already
     * programmed.
     *
     * @return an integer
     *
     * On failure, throws an exception or returns Y_BLINKSEQSIGNATURE_INVALID.
     */
    public int getBlinkSeqSignature()
    {
        return _blinkSeqSignature;
    }

    public String getCommand()
    {
        return _command;
    }

    public void setCommandBg(String newval) throws YAPI_Exception
    {
        _command = newval;
        _ycolorled.set_command(newval);
    }

    public static YColorLed FindColorLed(String func)
    {
        return YColorLed.FindColorLed(func);
    }

    public int sendCommand(String command) throws YAPI_Exception
    {
        return _ycolorled.sendCommand(command);
    }

    public int addHslMoveToBlinkSeq(int HSLcolor, int msDelay) throws YAPI_Exception
    {
        return _ycolorled.addHslMoveToBlinkSeq(HSLcolor, msDelay);
    }

    public int addRgbMoveToBlinkSeq(int RGBcolor, int msDelay) throws YAPI_Exception
    {
        return _ycolorled.addRgbMoveToBlinkSeq(RGBcolor, msDelay);
    }

    public int startBlinkSeq() throws YAPI_Exception
    {
        return _ycolorled.startBlinkSeq();
    }

    public int stopBlinkSeq() throws YAPI_Exception
    {
        return _ycolorled.stopBlinkSeq();
    }

    public int resetBlinkSeq() throws YAPI_Exception
    {
        return _ycolorled.resetBlinkSeq();
    }

//--- (end of YColorLed class start)
}

