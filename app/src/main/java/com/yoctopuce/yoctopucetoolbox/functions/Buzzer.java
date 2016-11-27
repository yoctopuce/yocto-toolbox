/*********************************************************************
 *
 * $Id: Buzzer.java 26014 2016-11-24 13:52:08Z seb $
 *
 * Implements Buzzer wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YBuzzer;

//--- (YBuzzer class start)
/**
 * YBuzzer Class: Buzzer function interface
 *
 * The Yoctopuce application programming interface allows you to
 * choose the frequency and volume at which the buzzer must sound.
 * You can also pre-program a play sequence.
 */
 @SuppressWarnings("UnusedDeclaration")
public class Buzzer extends Function
{
// valueCallbackBuzzer
    protected double _frequency =  YBuzzer.FREQUENCY_INVALID;
    protected int _volume =  YBuzzer.VOLUME_INVALID;
    protected int _playSeqSize =  YBuzzer.PLAYSEQSIZE_INVALID;
    protected int _playSeqMaxSize =  YBuzzer.PLAYSEQMAXSIZE_INVALID;
    protected int _playSeqSignature =  YBuzzer.PLAYSEQSIGNATURE_INVALID;
    protected String _command =  YBuzzer.COMMAND_INVALID;
    protected YBuzzer _ybuzzer;

    public Buzzer(YBuzzer yfunc)
    {
       super(yfunc);
       _ybuzzer = yfunc;
    }

    public Buzzer(String hwid)
    {
       super(hwid);
       _ybuzzer = YBuzzer.FindBuzzer(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _frequency = _ybuzzer.get_frequency();
        _volume = _ybuzzer.get_volume();
        _playSeqSize = _ybuzzer.get_playSeqSize();
        _playSeqMaxSize = _ybuzzer.get_playSeqMaxSize();
        _playSeqSignature = _ybuzzer.get_playSeqSignature();
        _command = _ybuzzer.get_command();
    }
    /**
     * Changes the frequency of the signal sent to the buzzer. A zero value stops the buzzer.
     *
     * @param newval : a floating point number corresponding to the frequency of the signal sent to the buzzer
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setFrequencyBg(double newval) throws YAPI_Exception
    {
        _frequency = newval;
        _ybuzzer.set_frequency(newval);
    }

    /**
     * Returns the  frequency of the signal sent to the buzzer/speaker.
     *
     * @return a floating point number corresponding to the  frequency of the signal sent to the buzzer/speaker
     *
     * On failure, throws an exception or returns Y_FREQUENCY_INVALID.
     */
    public double getFrequency()
    {
        return _frequency;
    }

    /**
     * Returns the volume of the signal sent to the buzzer/speaker.
     *
     * @return an integer corresponding to the volume of the signal sent to the buzzer/speaker
     *
     * On failure, throws an exception or returns Y_VOLUME_INVALID.
     */
    public int getVolume()
    {
        return _volume;
    }

    /**
     * Changes the volume of the signal sent to the buzzer/speaker.
     *
     * @param newval : an integer corresponding to the volume of the signal sent to the buzzer/speaker
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setVolumeBg(int newval) throws YAPI_Exception
    {
        _volume = newval;
        _ybuzzer.set_volume(newval);
    }

    /**
     * Returns the current length of the playing sequence.
     *
     * @return an integer corresponding to the current length of the playing sequence
     *
     * On failure, throws an exception or returns Y_PLAYSEQSIZE_INVALID.
     */
    public int getPlaySeqSize()
    {
        return _playSeqSize;
    }

    /**
     * Returns the maximum length of the playing sequence.
     *
     * @return an integer corresponding to the maximum length of the playing sequence
     *
     * On failure, throws an exception or returns Y_PLAYSEQMAXSIZE_INVALID.
     */
    public int getPlaySeqMaxSize()
    {
        return _playSeqMaxSize;
    }

    /**
     * Returns the playing sequence signature. As playing
     * sequences cannot be read from the device, this can be used
     * to detect if a specific playing sequence is already
     * programmed.
     *
     * @return an integer corresponding to the playing sequence signature
     *
     * On failure, throws an exception or returns Y_PLAYSEQSIGNATURE_INVALID.
     */
    public int getPlaySeqSignature()
    {
        return _playSeqSignature;
    }

    public String getCommand()
    {
        return _command;
    }

    public void setCommandBg(String newval) throws YAPI_Exception
    {
        _command = newval;
        _ybuzzer.set_command(newval);
    }

//--- (end of YBuzzer class start)
}

