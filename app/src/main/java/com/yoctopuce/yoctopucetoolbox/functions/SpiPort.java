/*********************************************************************
 *
 * $Id: SpiPort.java 26014 2016-11-24 13:52:08Z seb $
 *
 * Implements SpiPort wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YSpiPort;

//--- (YSpiPort class start)
/**
 * YSpiPort Class: SPI Port function interface
 *
 * The SpiPort function interface allows you to fully drive a Yoctopuce
 * SPI port, to send and receive data, and to configure communication
 * parameters (baud rate, bit count, parity, flow control and protocol).
 * Note that Yoctopuce SPI ports are not exposed as virtual COM ports.
 * They are meant to be used in the same way as all Yoctopuce devices.
 */
 @SuppressWarnings("UnusedDeclaration")
public class SpiPort extends Function
{
// valueCallbackSpiPort
    protected int _rxCount =  YSpiPort.RXCOUNT_INVALID;
    protected int _txCount =  YSpiPort.TXCOUNT_INVALID;
    protected int _errCount =  YSpiPort.ERRCOUNT_INVALID;
    protected int _rxMsgCount =  YSpiPort.RXMSGCOUNT_INVALID;
    protected int _txMsgCount =  YSpiPort.TXMSGCOUNT_INVALID;
    protected String _lastMsg =  YSpiPort.LASTMSG_INVALID;
    protected String _currentJob =  YSpiPort.CURRENTJOB_INVALID;
    protected String _startupJob =  YSpiPort.STARTUPJOB_INVALID;
    protected String _command =  YSpiPort.COMMAND_INVALID;
    protected int _voltageLevel =  YSpiPort.VOLTAGELEVEL_INVALID;
    protected String _protocol =  YSpiPort.PROTOCOL_INVALID;
    protected String _spiMode =  YSpiPort.SPIMODE_INVALID;
    protected int _ssPolarity =  YSpiPort.SSPOLARITY_INVALID;
    protected int _shitftSampling =  YSpiPort.SHITFTSAMPLING_INVALID;
    protected int _rxptr = 0;
    protected byte[] _rxbuff;
    protected int _rxbuffptr = 0;
    protected YSpiPort _yspiport;

    public SpiPort(YSpiPort yfunc)
    {
       super(yfunc);
       _yspiport = yfunc;
    }

    public SpiPort(String hwid)
    {
       super(hwid);
       _yspiport = YSpiPort.FindSpiPort(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _rxCount = _yspiport.get_rxCount();
        _txCount = _yspiport.get_txCount();
        _errCount = _yspiport.get_errCount();
        _rxMsgCount = _yspiport.get_rxMsgCount();
        _txMsgCount = _yspiport.get_txMsgCount();
        _lastMsg = _yspiport.get_lastMsg();
        _currentJob = _yspiport.get_currentJob();
        _startupJob = _yspiport.get_startupJob();
        _command = _yspiport.get_command();
        _voltageLevel = _yspiport.get_voltageLevel();
        _protocol = _yspiport.get_protocol();
        _spiMode = _yspiport.get_spiMode();
        _ssPolarity = _yspiport.get_ssPolarity();
        _shitftSampling = _yspiport.get_shitftSampling();
    }
    /**
     * Returns the total number of bytes received since last reset.
     *
     * @return an integer corresponding to the total number of bytes received since last reset
     *
     * On failure, throws an exception or returns Y_RXCOUNT_INVALID.
     */
    public int getRxCount()
    {
        return _rxCount;
    }

    /**
     * Returns the total number of bytes transmitted since last reset.
     *
     * @return an integer corresponding to the total number of bytes transmitted since last reset
     *
     * On failure, throws an exception or returns Y_TXCOUNT_INVALID.
     */
    public int getTxCount()
    {
        return _txCount;
    }

    /**
     * Returns the total number of communication errors detected since last reset.
     *
     * @return an integer corresponding to the total number of communication errors detected since last reset
     *
     * On failure, throws an exception or returns Y_ERRCOUNT_INVALID.
     */
    public int getErrCount()
    {
        return _errCount;
    }

    /**
     * Returns the total number of messages received since last reset.
     *
     * @return an integer corresponding to the total number of messages received since last reset
     *
     * On failure, throws an exception or returns Y_RXMSGCOUNT_INVALID.
     */
    public int getRxMsgCount()
    {
        return _rxMsgCount;
    }

    /**
     * Returns the total number of messages send since last reset.
     *
     * @return an integer corresponding to the total number of messages send since last reset
     *
     * On failure, throws an exception or returns Y_TXMSGCOUNT_INVALID.
     */
    public int getTxMsgCount()
    {
        return _txMsgCount;
    }

    /**
     * Returns the latest message fully received (for Line and Frame protocols).
     *
     * @return a string corresponding to the latest message fully received (for Line and Frame protocols)
     *
     * On failure, throws an exception or returns Y_LASTMSG_INVALID.
     */
    public String getLastMsg()
    {
        return _lastMsg;
    }

    /**
     * Returns the name of the job file currently in use.
     *
     * @return a string corresponding to the name of the job file currently in use
     *
     * On failure, throws an exception or returns Y_CURRENTJOB_INVALID.
     */
    public String getCurrentJob()
    {
        return _currentJob;
    }

    /**
     * Changes the job to use when the device is powered on.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : a string corresponding to the job to use when the device is powered on
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setCurrentJobBg(String newval) throws YAPI_Exception
    {
        _currentJob = newval;
        _yspiport.set_currentJob(newval);
    }

    /**
     * Returns the job file to use when the device is powered on.
     *
     * @return a string corresponding to the job file to use when the device is powered on
     *
     * On failure, throws an exception or returns Y_STARTUPJOB_INVALID.
     */
    public String getStartupJob()
    {
        return _startupJob;
    }

    /**
     * Changes the job to use when the device is powered on.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : a string corresponding to the job to use when the device is powered on
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setStartupJobBg(String newval) throws YAPI_Exception
    {
        _startupJob = newval;
        _yspiport.set_startupJob(newval);
    }

    public String getCommand()
    {
        return _command;
    }

    public void setCommandBg(String newval) throws YAPI_Exception
    {
        _command = newval;
        _yspiport.set_command(newval);
    }

    /**
     * Returns the voltage level used on the serial line.
     *
     * @return a value among Y_VOLTAGELEVEL_OFF, Y_VOLTAGELEVEL_TTL3V, Y_VOLTAGELEVEL_TTL3VR,
     * Y_VOLTAGELEVEL_TTL5V, Y_VOLTAGELEVEL_TTL5VR, Y_VOLTAGELEVEL_RS232 and Y_VOLTAGELEVEL_RS485
     * corresponding to the voltage level used on the serial line
     *
     * On failure, throws an exception or returns Y_VOLTAGELEVEL_INVALID.
     */
    public int getVoltageLevel()
    {
        return _voltageLevel;
    }

    /**
     * Changes the voltage type used on the serial line. Valid
     * values  will depend on the Yoctopuce device model featuring
     * the serial port feature.  Check your device documentation
     * to find out which values are valid for that specific model.
     * Trying to set an invalid value will have no effect.
     *
     * @param newval : a value among Y_VOLTAGELEVEL_OFF, Y_VOLTAGELEVEL_TTL3V, Y_VOLTAGELEVEL_TTL3VR,
     * Y_VOLTAGELEVEL_TTL5V, Y_VOLTAGELEVEL_TTL5VR, Y_VOLTAGELEVEL_RS232 and Y_VOLTAGELEVEL_RS485
     * corresponding to the voltage type used on the serial line
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setVoltageLevelBg(int newval) throws YAPI_Exception
    {
        _voltageLevel = newval;
        _yspiport.set_voltageLevel(newval);
    }

    /**
     * Returns the type of protocol used over the serial line, as a string.
     * Possible values are "Line" for ASCII messages separated by CR and/or LF,
     * "Frame:[timeout]ms" for binary messages separated by a delay time,
     * "Char" for a continuous ASCII stream or
     * "Byte" for a continuous binary stream.
     *
     * @return a string corresponding to the type of protocol used over the serial line, as a string
     *
     * On failure, throws an exception or returns Y_PROTOCOL_INVALID.
     */
    public String getProtocol()
    {
        return _protocol;
    }

    /**
     * Changes the type of protocol used over the serial line.
     * Possible values are "Line" for ASCII messages separated by CR and/or LF,
     * "Frame:[timeout]ms" for binary messages separated by a delay time,
     * "Char" for a continuous ASCII stream or
     * "Byte" for a continuous binary stream.
     * The suffix "/[wait]ms" can be added to reduce the transmit rate so that there
     * is always at lest the specified number of milliseconds between each bytes sent.
     *
     * @param newval : a string corresponding to the type of protocol used over the serial line
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setProtocolBg(String newval) throws YAPI_Exception
    {
        _protocol = newval;
        _yspiport.set_protocol(newval);
    }

    /**
     * Returns the SPI port communication parameters, as a string such as
     * "125000,0,msb". The string includes the baud rate, the SPI mode (between
     * 0 and 3) and the bit order.
     *
     * @return a string corresponding to the SPI port communication parameters, as a string such as
     *         "125000,0,msb"
     *
     * On failure, throws an exception or returns Y_SPIMODE_INVALID.
     */
    public String getSpiMode()
    {
        return _spiMode;
    }

    /**
     * Changes the SPI port communication parameters, with a string such as
     * "125000,0,msb". The string includes the baud rate, the SPI mode (between
     * 0 and 3) and the bit order.
     *
     * @param newval : a string corresponding to the SPI port communication parameters, with a string such as
     *         "125000,0,msb"
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setSpiModeBg(String newval) throws YAPI_Exception
    {
        _spiMode = newval;
        _yspiport.set_spiMode(newval);
    }

    /**
     * Returns the SS line polarity.
     *
     * @return either Y_SSPOLARITY_ACTIVE_LOW or Y_SSPOLARITY_ACTIVE_HIGH, according to the SS line polarity
     *
     * On failure, throws an exception or returns Y_SSPOLARITY_INVALID.
     */
    public int getSsPolarity()
    {
        return _ssPolarity;
    }

    /**
     * Changes the SS line polarity.
     *
     * @param newval : either Y_SSPOLARITY_ACTIVE_LOW or Y_SSPOLARITY_ACTIVE_HIGH, according to the SS line polarity
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setSsPolarityBg(int newval) throws YAPI_Exception
    {
        _ssPolarity = newval;
        _yspiport.set_ssPolarity(newval);
    }

    /**
     * Returns true when the SDI line phase is shifted with regards to the SDO line.
     *
     * @return either Y_SHITFTSAMPLING_OFF or Y_SHITFTSAMPLING_ON, according to true when the SDI line
     * phase is shifted with regards to the SDO line
     *
     * On failure, throws an exception or returns Y_SHITFTSAMPLING_INVALID.
     */
    public int getShitftSampling()
    {
        return _shitftSampling;
    }

    /**
     * Changes the SDI line sampling shift. When disabled, SDI line is
     * sampled in the middle of data output time. When enabled, SDI line is
     * samples at the end of data output time.
     *
     * @param newval : either Y_SHITFTSAMPLING_OFF or Y_SHITFTSAMPLING_ON, according to the SDI line sampling shift
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setShitftSamplingBg(int newval) throws YAPI_Exception
    {
        _shitftSampling = newval;
        _yspiport.set_shitftSampling(newval);
    }

//--- (end of YSpiPort class start)
}

