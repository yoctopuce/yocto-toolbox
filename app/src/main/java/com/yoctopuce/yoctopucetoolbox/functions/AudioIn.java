/*********************************************************************
 *
 * $Id: AudioIn.java 26014 2016-11-24 13:52:08Z seb $
 *
 * Implements AudioIn wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YAudioIn;

//--- (YAudioIn class start)
/**
 * YAudioIn Class: AudioIn function interface
 *
 * The Yoctopuce application programming interface allows you to configure the volume of the input channel.
 */
 @SuppressWarnings("UnusedDeclaration")
public class AudioIn extends Function
{
// valueCallbackAudioIn
    protected int _volume =  YAudioIn.VOLUME_INVALID;
    protected int _mute =  YAudioIn.MUTE_INVALID;
    protected String _volumeRange =  YAudioIn.VOLUMERANGE_INVALID;
    protected int _signal =  YAudioIn.SIGNAL_INVALID;
    protected int _noSignalFor =  YAudioIn.NOSIGNALFOR_INVALID;
    protected YAudioIn _yaudioin;

    public AudioIn(YAudioIn yfunc)
    {
       super(yfunc);
       _yaudioin = yfunc;
    }

    public AudioIn(String hwid)
    {
       super(hwid);
       _yaudioin = YAudioIn.FindAudioIn(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _volume = _yaudioin.get_volume();
        _mute = _yaudioin.get_mute();
        _volumeRange = _yaudioin.get_volumeRange();
        _signal = _yaudioin.get_signal();
        _noSignalFor = _yaudioin.get_noSignalFor();
    }
    /**
     * Returns audio input gain, in per cents.
     *
     * @return an integer corresponding to audio input gain, in per cents
     *
     * On failure, throws an exception or returns Y_VOLUME_INVALID.
     */
    public int getVolume()
    {
        return _volume;
    }

    /**
     * Changes audio input gain, in per cents.
     *
     * @param newval : an integer corresponding to audio input gain, in per cents
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setVolumeBg(int newval) throws YAPI_Exception
    {
        _volume = newval;
        _yaudioin.set_volume(newval);
    }

    /**
     * Returns the state of the mute function.
     *
     * @return either Y_MUTE_FALSE or Y_MUTE_TRUE, according to the state of the mute function
     *
     * On failure, throws an exception or returns Y_MUTE_INVALID.
     */
    public int getMute()
    {
        return _mute;
    }

    /**
     * Changes the state of the mute function. Remember to call the matching module
     * saveToFlash() method to save the setting permanently.
     *
     * @param newval : either Y_MUTE_FALSE or Y_MUTE_TRUE, according to the state of the mute function
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setMuteBg(int newval) throws YAPI_Exception
    {
        _mute = newval;
        _yaudioin.set_mute(newval);
    }

    /**
     * Returns the supported volume range. The low value of the
     * range corresponds to the minimal audible value. To
     * completely mute the sound, use set_mute()
     * instead of the set_volume().
     *
     * @return a string corresponding to the supported volume range
     *
     * On failure, throws an exception or returns Y_VOLUMERANGE_INVALID.
     */
    public String getVolumeRange()
    {
        return _volumeRange;
    }

    /**
     * Returns the detected input signal level.
     *
     * @return an integer corresponding to the detected input signal level
     *
     * On failure, throws an exception or returns Y_SIGNAL_INVALID.
     */
    public int getSignal()
    {
        return _signal;
    }

    /**
     * Returns the number of seconds elapsed without detecting a signal.
     *
     * @return an integer corresponding to the number of seconds elapsed without detecting a signal
     *
     * On failure, throws an exception or returns Y_NOSIGNALFOR_INVALID.
     */
    public int getNoSignalFor()
    {
        return _noSignalFor;
    }

//--- (end of YAudioIn class start)
}

