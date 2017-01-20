/*********************************************************************
 *
 * $Id: pic24config.php 26169 2016-12-12 01:36:34Z mvuilleu $
 *
 * Implements SegmentedDisplay wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YSegmentedDisplay;

//--- (YSegmentedDisplay class start)
/**
 * YSegmentedDisplay Class: SegmentedDisplay function interface
 *
 * The SegmentedDisplay class allows you to drive segmented displays.
 */
 @SuppressWarnings("UnusedDeclaration")
public class SegmentedDisplay extends Function
{
// valueCallbackSegmentedDisplay
    protected String _displayedText =  YSegmentedDisplay.DISPLAYEDTEXT_INVALID;
    protected int _displayMode =  YSegmentedDisplay.DISPLAYMODE_INVALID;
    protected YSegmentedDisplay _ysegmenteddisplay;

    public SegmentedDisplay(YSegmentedDisplay yfunc)
    {
       super(yfunc);
       _ysegmenteddisplay = yfunc;
    }

    public SegmentedDisplay(String hwid)
    {
       super(hwid);
       _ysegmenteddisplay = YSegmentedDisplay.FindSegmentedDisplay(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _displayedText = _ysegmenteddisplay.get_displayedText();
        _displayMode = _ysegmenteddisplay.get_displayMode();
    }
    /**
     * Returns the text currently displayed on the screen.
     *
     * @return a string corresponding to the text currently displayed on the screen
     *
     * On failure, throws an exception or returns Y_DISPLAYEDTEXT_INVALID.
     */
    public String getDisplayedText()
    {
        return _displayedText;
    }

    /**
     * Changes the text currently displayed on the screen.
     *
     * @param newval : a string corresponding to the text currently displayed on the screen
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setDisplayedTextBg(String newval) throws YAPI_Exception
    {
        _displayedText = newval;
        _ysegmenteddisplay.set_displayedText(newval);
    }

    public int getDisplayMode()
    {
        return _displayMode;
    }

    public void setDisplayModeBg(int newval) throws YAPI_Exception
    {
        _displayMode = newval;
        _ysegmenteddisplay.set_displayMode(newval);
    }

    public static YSegmentedDisplay FindSegmentedDisplay(String func)
    {
        return YSegmentedDisplay.FindSegmentedDisplay(func);
    }

//--- (end of YSegmentedDisplay class start)
}

