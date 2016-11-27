/*********************************************************************
 *
 * $Id: QuadratureDecoder.java 26014 2016-11-24 13:52:08Z seb $
 *
 * Implements QuadratureDecoder wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YQuadratureDecoder;

//--- (YQuadratureDecoder class start)
/**
 * YQuadratureDecoder Class: QuadratureDecoder function interface
 *
 * The class YQuadratureDecoder allows you to decode a two-wire signal produced by a
 * quadrature encoder. It inherits from YSensor class the core functions to read measurements,
 * register callback functions, access to the autonomous datalogger.
 */
 @SuppressWarnings("UnusedDeclaration")
public class QuadratureDecoder extends Sensor
{
// valueCallbackQuadratureDecoder
// timedReportCallbackQuadratureDecoder
    protected double _speed =  YQuadratureDecoder.SPEED_INVALID;
    protected int _decoding =  YQuadratureDecoder.DECODING_INVALID;
    protected YQuadratureDecoder _yquadraturedecoder;

    public QuadratureDecoder(YQuadratureDecoder yfunc)
    {
       super(yfunc);
       _yquadraturedecoder = yfunc;
    }

    public QuadratureDecoder(String hwid)
    {
       super(hwid);
       _yquadraturedecoder = YQuadratureDecoder.FindQuadratureDecoder(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _speed = _yquadraturedecoder.get_speed();
        _decoding = _yquadraturedecoder.get_decoding();
    }
    /**
     * Changes the current expected position of the quadrature decoder.
     * Invoking this function implicitely activates the quadrature decoder.
     *
     * @param newval : a floating point number corresponding to the current expected position of the quadrature decoder
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setCurrentValueBg(double newval) throws YAPI_Exception
    {
        _currentValue = newval;
        _yquadraturedecoder.set_currentValue(newval);
    }

    /**
     * Returns the PWM frequency in Hz.
     *
     * @return a floating point number corresponding to the PWM frequency in Hz
     *
     * On failure, throws an exception or returns Y_SPEED_INVALID.
     */
    public double getSpeed()
    {
        return _speed;
    }

    /**
     * Returns the current activation state of the quadrature decoder.
     *
     * @return either Y_DECODING_OFF or Y_DECODING_ON, according to the current activation state of the
     * quadrature decoder
     *
     * On failure, throws an exception or returns Y_DECODING_INVALID.
     */
    public int getDecoding()
    {
        return _decoding;
    }

    /**
     * Changes the activation state of the quadrature decoder.
     *
     * @param newval : either Y_DECODING_OFF or Y_DECODING_ON, according to the activation state of the
     * quadrature decoder
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setDecodingBg(int newval) throws YAPI_Exception
    {
        _decoding = newval;
        _yquadraturedecoder.set_decoding(newval);
    }

//--- (end of YQuadratureDecoder class start)
}

