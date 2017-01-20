/*********************************************************************
 *
 * $Id: pic24config.php 26169 2016-12-12 01:36:34Z mvuilleu $
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
import com.yoctopuce.YoctoAPI.YAPIContext;
import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YColorLedCluster;
import java.util.ArrayList;

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

    public static YColorLedCluster FindColorLedCluster(String func)
    {
        return YColorLedCluster.FindColorLedCluster(func);
    }

    public int sendCommand(String command) throws YAPI_Exception
    {
        return _ycolorledcluster.sendCommand(command);
    }

    public int set_rgbColor(int ledIndex, int count, int rgbValue) throws YAPI_Exception
    {
        return _ycolorledcluster.set_rgbColor(ledIndex, count, rgbValue);
    }

    public int set_rgbColorAtPowerOn(int ledIndex, int count, int rgbValue) throws YAPI_Exception
    {
        return _ycolorledcluster.set_rgbColorAtPowerOn(ledIndex, count, rgbValue);
    }

    public int set_hslColor(int ledIndex, int count, int hslValue) throws YAPI_Exception
    {
        return _ycolorledcluster.set_hslColor(ledIndex, count, hslValue);
    }

    public int rgb_move(int ledIndex, int count, int rgbValue, int delay) throws YAPI_Exception
    {
        return _ycolorledcluster.rgb_move(ledIndex, count, rgbValue, delay);
    }

    public int hsl_move(int ledIndex, int count, int hslValue, int delay) throws YAPI_Exception
    {
        return _ycolorledcluster.hsl_move(ledIndex, count, hslValue, delay);
    }

    public int addRgbMoveToBlinkSeq(int seqIndex, int rgbValue, int delay) throws YAPI_Exception
    {
        return _ycolorledcluster.addRgbMoveToBlinkSeq(seqIndex, rgbValue, delay);
    }

    public int addHslMoveToBlinkSeq(int seqIndex, int hslValue, int delay) throws YAPI_Exception
    {
        return _ycolorledcluster.addHslMoveToBlinkSeq(seqIndex, hslValue, delay);
    }

    public int addMirrorToBlinkSeq(int seqIndex) throws YAPI_Exception
    {
        return _ycolorledcluster.addMirrorToBlinkSeq(seqIndex);
    }

    public int linkLedToBlinkSeq(int ledIndex, int count, int seqIndex, int offset) throws YAPI_Exception
    {
        return _ycolorledcluster.linkLedToBlinkSeq(ledIndex, count, seqIndex, offset);
    }

    public int linkLedToBlinkSeqAtPowerOn(int ledIndex, int count, int seqIndex, int offset) throws YAPI_Exception
    {
        return _ycolorledcluster.linkLedToBlinkSeqAtPowerOn(ledIndex, count, seqIndex, offset);
    }

    public int linkLedToPeriodicBlinkSeq(int ledIndex, int count, int seqIndex, int periods) throws YAPI_Exception
    {
        return _ycolorledcluster.linkLedToPeriodicBlinkSeq(ledIndex, count, seqIndex, periods);
    }

    public int unlinkLedFromBlinkSeq(int ledIndex, int count) throws YAPI_Exception
    {
        return _ycolorledcluster.unlinkLedFromBlinkSeq(ledIndex, count);
    }

    public int startBlinkSeq(int seqIndex) throws YAPI_Exception
    {
        return _ycolorledcluster.startBlinkSeq(seqIndex);
    }

    public int stopBlinkSeq(int seqIndex) throws YAPI_Exception
    {
        return _ycolorledcluster.stopBlinkSeq(seqIndex);
    }

    public int resetBlinkSeq(int seqIndex) throws YAPI_Exception
    {
        return _ycolorledcluster.resetBlinkSeq(seqIndex);
    }

    public int set_blinkSeqStateAtPowerOn(int seqIndex, int autostart) throws YAPI_Exception
    {
        return _ycolorledcluster.set_blinkSeqStateAtPowerOn(seqIndex, autostart);
    }

    public int set_blinkSeqSpeed(int seqIndex, int speed) throws YAPI_Exception
    {
        return _ycolorledcluster.set_blinkSeqSpeed(seqIndex, speed);
    }

    public int saveLedsConfigAtPowerOn() throws YAPI_Exception
    {
        return _ycolorledcluster.saveLedsConfigAtPowerOn();
    }

    public int saveLedsState() throws YAPI_Exception
    {
        return _ycolorledcluster.saveLedsState();
    }

    public int saveBlinkSeq(int seqIndex) throws YAPI_Exception
    {
        return _ycolorledcluster.saveBlinkSeq(seqIndex);
    }

    public int set_rgbColorBuffer(int ledIndex, byte[] buff) throws YAPI_Exception
    {
        return _ycolorledcluster.set_rgbColorBuffer(ledIndex, buff);
    }

    public int set_rgbColorArray(int ledIndex, ArrayList<Integer> rgbList) throws YAPI_Exception
    {
        return _ycolorledcluster.set_rgbColorArray(ledIndex, rgbList);
    }

    public int rgbArray_move(ArrayList<Integer> rgbList, int delay) throws YAPI_Exception
    {
        return _ycolorledcluster.rgbArray_move(rgbList, delay);
    }

    public int set_hslColorBuffer(int ledIndex, byte[] buff) throws YAPI_Exception
    {
        return _ycolorledcluster.set_hslColorBuffer(ledIndex, buff);
    }

    public int set_hslColorArray(int ledIndex, ArrayList<Integer> hslList) throws YAPI_Exception
    {
        return _ycolorledcluster.set_hslColorArray(ledIndex, hslList);
    }

    public int hslArray_move(ArrayList<Integer> hslList, int delay) throws YAPI_Exception
    {
        return _ycolorledcluster.hslArray_move(hslList, delay);
    }

    public byte[] get_rgbColorBuffer(int ledIndex, int count) throws YAPI_Exception
    {
        return _ycolorledcluster.get_rgbColorBuffer(ledIndex, count);
    }

    public ArrayList<Integer> get_rgbColorArray(int ledIndex, int count) throws YAPI_Exception
    {
        return _ycolorledcluster.get_rgbColorArray(ledIndex, count);
    }

    public ArrayList<Integer> get_rgbColorArrayAtPowerOn(int ledIndex, int count) throws YAPI_Exception
    {
        return _ycolorledcluster.get_rgbColorArrayAtPowerOn(ledIndex, count);
    }

    public ArrayList<Integer> get_linkedSeqArray(int ledIndex, int count) throws YAPI_Exception
    {
        return _ycolorledcluster.get_linkedSeqArray(ledIndex, count);
    }

    public ArrayList<Integer> get_blinkSeqSignatures(int seqIndex, int count) throws YAPI_Exception
    {
        return _ycolorledcluster.get_blinkSeqSignatures(seqIndex, count);
    }

    public ArrayList<Integer> get_blinkSeqStateSpeed(int seqIndex, int count) throws YAPI_Exception
    {
        return _ycolorledcluster.get_blinkSeqStateSpeed(seqIndex, count);
    }

    public ArrayList<Integer> get_blinkSeqStateAtPowerOn(int seqIndex, int count) throws YAPI_Exception
    {
        return _ycolorledcluster.get_blinkSeqStateAtPowerOn(seqIndex, count);
    }

    public ArrayList<Integer> get_blinkSeqState(int seqIndex, int count) throws YAPI_Exception
    {
        return _ycolorledcluster.get_blinkSeqState(seqIndex, count);
    }

//--- (end of YColorLedCluster class start)
}

