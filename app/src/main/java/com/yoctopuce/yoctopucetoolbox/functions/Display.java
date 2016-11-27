/*********************************************************************
 *
 * $Id: Display.java 26014 2016-11-24 13:52:08Z seb $
 *
 * Implements Display wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YDisplay;

//--- (generated code: YDisplay class start)
/**
 * YDisplay Class: Display function interface
 *
 * Yoctopuce display interface has been designed to easily
 * show information and images. The device provides built-in
 * multi-layer rendering. Layers can be drawn offline, individually,
 * and freely moved on the display. It can also replay recorded
 * sequences (animations).
 */
 @SuppressWarnings("UnusedDeclaration")
public class Display extends Function
{
// valueCallbackDisplay
    protected int _enabled =  YDisplay.ENABLED_INVALID;
    protected String _startupSeq =  YDisplay.STARTUPSEQ_INVALID;
    protected int _brightness =  YDisplay.BRIGHTNESS_INVALID;
    protected int _orientation =  YDisplay.ORIENTATION_INVALID;
    protected int _displayWidth =  YDisplay.DISPLAYWIDTH_INVALID;
    protected int _displayHeight =  YDisplay.DISPLAYHEIGHT_INVALID;
    protected int _displayType =  YDisplay.DISPLAYTYPE_INVALID;
    protected int _layerWidth =  YDisplay.LAYERWIDTH_INVALID;
    protected int _layerHeight =  YDisplay.LAYERHEIGHT_INVALID;
    protected int _layerCount =  YDisplay.LAYERCOUNT_INVALID;
    protected String _command =  YDisplay.COMMAND_INVALID;
    protected YDisplay _ydisplay;

    public Display(YDisplay yfunc)
    {
       super(yfunc);
       _ydisplay = yfunc;
    }

    public Display(String hwid)
    {
       super(hwid);
       _ydisplay = YDisplay.FindDisplay(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _enabled = _ydisplay.get_enabled();
        _startupSeq = _ydisplay.get_startupSeq();
        _brightness = _ydisplay.get_brightness();
        _orientation = _ydisplay.get_orientation();
        _displayWidth = _ydisplay.get_displayWidth();
        _displayHeight = _ydisplay.get_displayHeight();
        _displayType = _ydisplay.get_displayType();
        _layerWidth = _ydisplay.get_layerWidth();
        _layerHeight = _ydisplay.get_layerHeight();
        _layerCount = _ydisplay.get_layerCount();
        _command = _ydisplay.get_command();
    }
    /**
     * Returns true if the screen is powered, false otherwise.
     *
     * @return either Y_ENABLED_FALSE or Y_ENABLED_TRUE, according to true if the screen is powered, false otherwise
     *
     * On failure, throws an exception or returns Y_ENABLED_INVALID.
     */
    public int getEnabled()
    {
        return _enabled;
    }

    /**
     * Changes the power state of the display.
     *
     * @param newval : either Y_ENABLED_FALSE or Y_ENABLED_TRUE, according to the power state of the display
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setEnabledBg(int newval) throws YAPI_Exception
    {
        _enabled = newval;
        _ydisplay.set_enabled(newval);
    }

    /**
     * Returns the name of the sequence to play when the displayed is powered on.
     *
     * @return a string corresponding to the name of the sequence to play when the displayed is powered on
     *
     * On failure, throws an exception or returns Y_STARTUPSEQ_INVALID.
     */
    public String getStartupSeq()
    {
        return _startupSeq;
    }

    /**
     * Changes the name of the sequence to play when the displayed is powered on.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : a string corresponding to the name of the sequence to play when the displayed is powered on
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setStartupSeqBg(String newval) throws YAPI_Exception
    {
        _startupSeq = newval;
        _ydisplay.set_startupSeq(newval);
    }

    /**
     * Returns the luminosity of the  module informative leds (from 0 to 100).
     *
     * @return an integer corresponding to the luminosity of the  module informative leds (from 0 to 100)
     *
     * On failure, throws an exception or returns Y_BRIGHTNESS_INVALID.
     */
    public int getBrightness()
    {
        return _brightness;
    }

    /**
     * Changes the brightness of the display. The parameter is a value between 0 and
     * 100. Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : an integer corresponding to the brightness of the display
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setBrightnessBg(int newval) throws YAPI_Exception
    {
        _brightness = newval;
        _ydisplay.set_brightness(newval);
    }

    /**
     * Returns the currently selected display orientation.
     *
     * @return a value among Y_ORIENTATION_LEFT, Y_ORIENTATION_UP, Y_ORIENTATION_RIGHT and
     * Y_ORIENTATION_DOWN corresponding to the currently selected display orientation
     *
     * On failure, throws an exception or returns Y_ORIENTATION_INVALID.
     */
    public int getOrientation()
    {
        return _orientation;
    }

    /**
     * Changes the display orientation. Remember to call the saveToFlash()
     * method of the module if the modification must be kept.
     *
     * @param newval : a value among Y_ORIENTATION_LEFT, Y_ORIENTATION_UP, Y_ORIENTATION_RIGHT and
     * Y_ORIENTATION_DOWN corresponding to the display orientation
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setOrientationBg(int newval) throws YAPI_Exception
    {
        _orientation = newval;
        _ydisplay.set_orientation(newval);
    }

    /**
     * Returns the display width, in pixels.
     *
     * @return an integer corresponding to the display width, in pixels
     *
     * On failure, throws an exception or returns Y_DISPLAYWIDTH_INVALID.
     */
    public int getDisplayWidth()
    {
        return _displayWidth;
    }

    /**
     * Returns the display height, in pixels.
     *
     * @return an integer corresponding to the display height, in pixels
     *
     * On failure, throws an exception or returns Y_DISPLAYHEIGHT_INVALID.
     */
    public int getDisplayHeight()
    {
        return _displayHeight;
    }

    /**
     * Returns the display type: monochrome, gray levels or full color.
     *
     * @return a value among Y_DISPLAYTYPE_MONO, Y_DISPLAYTYPE_GRAY and Y_DISPLAYTYPE_RGB corresponding to
     * the display type: monochrome, gray levels or full color
     *
     * On failure, throws an exception or returns Y_DISPLAYTYPE_INVALID.
     */
    public int getDisplayType()
    {
        return _displayType;
    }

    /**
     * Returns the width of the layers to draw on, in pixels.
     *
     * @return an integer corresponding to the width of the layers to draw on, in pixels
     *
     * On failure, throws an exception or returns Y_LAYERWIDTH_INVALID.
     */
    public int getLayerWidth()
    {
        return _layerWidth;
    }

    /**
     * Returns the height of the layers to draw on, in pixels.
     *
     * @return an integer corresponding to the height of the layers to draw on, in pixels
     *
     * On failure, throws an exception or returns Y_LAYERHEIGHT_INVALID.
     */
    public int getLayerHeight()
    {
        return _layerHeight;
    }

    /**
     * Returns the number of available layers to draw on.
     *
     * @return an integer corresponding to the number of available layers to draw on
     *
     * On failure, throws an exception or returns Y_LAYERCOUNT_INVALID.
     */
    public int getLayerCount()
    {
        return _layerCount;
    }

    public String getCommand()
    {
        return _command;
    }

    public void setCommandBg(String newval) throws YAPI_Exception
    {
        _command = newval;
        _ydisplay.set_command(newval);
    }

//--- (end of generated code: YDisplay class start)
}

