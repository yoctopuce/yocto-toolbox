/*********************************************************************
 *
 * $Id: SerialPort.java 46698 2021-10-01 06:31:31Z web $
 *
 * Implements SerialPort wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YSerialPort;
import com.yoctopuce.YoctoAPI.YSnoopingRecord;

import java.util.ArrayList;

//--- (generated code: YSerialPort class start)
/**
 * YSerialPort Class: SerialPort function interface
 *
 * The SerialPort function interface allows you to fully drive a Yoctopuce
 * serial port, to send and receive data, and to configure communication
 * parameters (baud rate, bit count, parity, flow control and protocol).
 * Note that Yoctopuce serial ports are not exposed as virtual COM ports.
 * They are meant to be used in the same way as all Yoctopuce devices.
 */
 @SuppressWarnings("UnusedDeclaration")
public class SerialPort extends Function
{
// valueCallbackSerialPort
    protected int _rxCount =  YSerialPort.RXCOUNT_INVALID;
    protected int _txCount =  YSerialPort.TXCOUNT_INVALID;
    protected int _errCount =  YSerialPort.ERRCOUNT_INVALID;
    protected int _rxMsgCount =  YSerialPort.RXMSGCOUNT_INVALID;
    protected int _txMsgCount =  YSerialPort.TXMSGCOUNT_INVALID;
    protected String _lastMsg =  YSerialPort.LASTMSG_INVALID;
    protected String _currentJob =  YSerialPort.CURRENTJOB_INVALID;
    protected String _startupJob =  YSerialPort.STARTUPJOB_INVALID;
    protected String _command =  YSerialPort.COMMAND_INVALID;
    protected int _voltageLevel =  YSerialPort.VOLTAGELEVEL_INVALID;
    protected String _protocol =  YSerialPort.PROTOCOL_INVALID;
    protected String _serialMode =  YSerialPort.SERIALMODE_INVALID;
    protected int _rxptr = 0;
    protected byte[] _rxbuff;
    protected int _rxbuffptr = 0;
    protected YSerialPort _yserialport;

    public SerialPort(YSerialPort yfunc)
    {
       super(yfunc);
       _yserialport = yfunc;
    }

    public SerialPort(String hwid)
    {
       super(hwid);
       _yserialport = YSerialPort.FindSerialPort(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _rxCount = _yserialport.get_rxCount();
        _txCount = _yserialport.get_txCount();
        _errCount = _yserialport.get_errCount();
        _rxMsgCount = _yserialport.get_rxMsgCount();
        _txMsgCount = _yserialport.get_txMsgCount();
        _lastMsg = _yserialport.get_lastMsg();
        _currentJob = _yserialport.get_currentJob();
        _startupJob = _yserialport.get_startupJob();
        _command = _yserialport.get_command();
        _voltageLevel = _yserialport.get_voltageLevel();
        _protocol = _yserialport.get_protocol();
        _serialMode = _yserialport.get_serialMode();
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
     * Returns the latest message fully received (for Line, Frame and Modbus protocols).
     *
     * @return a string corresponding to the latest message fully received (for Line, Frame and Modbus protocols)
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
        _yserialport.set_currentJob(newval);
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
        _yserialport.set_startupJob(newval);
    }

    public String getCommand()
    {
        return _command;
    }

    public void setCommandBg(String newval) throws YAPI_Exception
    {
        _command = newval;
        _yserialport.set_command(newval);
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
        _yserialport.set_voltageLevel(newval);
    }

    /**
     * Returns the type of protocol used over the serial line, as a string.
     * Possible values are "Line" for ASCII messages separated by CR and/or LF,
     * "Frame:[timeout]ms" for binary messages separated by a delay time,
     * "Modbus-ASCII" for MODBUS messages in ASCII mode,
     * "Modbus-RTU" for MODBUS messages in RTU mode,
     * "Wiegand-ASCII" for Wiegand messages in ASCII mode,
     * "Wiegand-26","Wiegand-34", etc for Wiegand messages in byte mode,
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
     * "Modbus-ASCII" for MODBUS messages in ASCII mode,
     * "Modbus-RTU" for MODBUS messages in RTU mode,
     * "Wiegand-ASCII" for Wiegand messages in ASCII mode,
     * "Wiegand-26","Wiegand-34", etc for Wiegand messages in byte mode,
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
        _yserialport.set_protocol(newval);
    }

    /**
     * Returns the serial port communication parameters, as a string such as
     * "9600,8N1". The string includes the baud rate, the number of data bits,
     * the parity, and the number of stop bits. An optional suffix is included
     * if flow control is active: "CtsRts" for hardware handshake, "XOnXOff"
     * for logical flow control and "Simplex" for acquiring a shared bus using
     * the RTS line (as used by some RS485 adapters for instance).
     *
     * @return a string corresponding to the serial port communication parameters, as a string such as
     *         "9600,8N1"
     *
     * On failure, throws an exception or returns Y_SERIALMODE_INVALID.
     */
    public String getSerialMode()
    {
        return _serialMode;
    }

    /**
     * Changes the serial port communication parameters, with a string such as
     * "9600,8N1". The string includes the baud rate, the number of data bits,
     * the parity, and the number of stop bits. An optional suffix can be added
     * to enable flow control: "CtsRts" for hardware handshake, "XOnXOff"
     * for logical flow control and "Simplex" for acquiring a shared bus using
     * the RTS line (as used by some RS485 adapters for instance).
     *
     * @param newval : a string corresponding to the serial port communication parameters, with a string such as
     *         "9600,8N1"
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setSerialModeBg(String newval) throws YAPI_Exception
    {
        _serialMode = newval;
        _yserialport.set_serialMode(newval);
    }

    public static YSerialPort FindSerialPort(String func)
    {
        return YSerialPort.FindSerialPort(func);
    }

    public int sendCommand(String text) throws YAPI_Exception
    {
        return _yserialport.sendCommand(text);
    }

    public int reset() throws YAPI_Exception
    {
        return _yserialport.reset();
    }

    public int writeByte(int code) throws YAPI_Exception
    {
        return _yserialport.writeByte(code);
    }

    public int writeStr(String text) throws YAPI_Exception
    {
        return _yserialport.writeStr(text);
    }

    public int writeBin(byte[] buff) throws YAPI_Exception
    {
        return _yserialport.writeBin(buff);
    }

    public int writeArray(ArrayList<Integer> byteList) throws YAPI_Exception
    {
        return _yserialport.writeArray(byteList);
    }

    public int writeHex(String hexString) throws YAPI_Exception
    {
        return _yserialport.writeHex(hexString);
    }

    public int writeLine(String text) throws YAPI_Exception
    {
        return _yserialport.writeLine(text);
    }

    public int readByte() throws YAPI_Exception
    {
        return _yserialport.readByte();
    }

    public String readStr(int nChars) throws YAPI_Exception
    {
        return _yserialport.readStr(nChars);
    }

    public byte[] readBin(int nChars) throws YAPI_Exception
    {
        return _yserialport.readBin(nChars);
    }

    public ArrayList<Integer> readArray(int nChars) throws YAPI_Exception
    {
        return _yserialport.readArray(nChars);
    }

    public String readHex(int nBytes) throws YAPI_Exception
    {
        return _yserialport.readHex(nBytes);
    }

    public String readLine() throws YAPI_Exception
    {
        return _yserialport.readLine();
    }

    public ArrayList<String> readMessages(String pattern, int maxWait) throws YAPI_Exception
    {
        return _yserialport.readMessages(pattern, maxWait);
    }

    public int read_seek(int absPos)
    {
        return _yserialport.read_seek(absPos);
    }

    public int read_tell()
    {
        return _yserialport.read_tell();
    }

    public int read_avail() throws YAPI_Exception
    {
        return _yserialport.read_avail();
    }

    public String queryLine(String query, int maxWait) throws YAPI_Exception
    {
        return _yserialport.queryLine(query, maxWait);
    }

    public int uploadJob(String jobfile, String jsonDef) throws YAPI_Exception
    {
        return _yserialport.uploadJob(jobfile, jsonDef);
    }

    public int selectJob(String jobfile) throws YAPI_Exception
    {
        return _yserialport.selectJob(jobfile);
    }

    public int set_RTS(int val) throws YAPI_Exception
    {
        return _yserialport.set_RTS(val);
    }

    public int get_CTS() throws YAPI_Exception
    {
        return _yserialport.get_CTS();
    }

    public ArrayList<YSnoopingRecord> snoopMessages(int maxWait) throws YAPI_Exception
    {
        return _yserialport.snoopMessages(maxWait);
    }

    public int writeMODBUS(String hexString) throws YAPI_Exception
    {
        return _yserialport.writeMODBUS(hexString);
    }

    public ArrayList<Integer> queryMODBUS(int slaveNo, ArrayList<Integer> pduBytes) throws YAPI_Exception
    {
        return _yserialport.queryMODBUS(slaveNo, pduBytes);
    }

    public ArrayList<Integer> modbusReadBits(int slaveNo, int pduAddr, int nBits) throws YAPI_Exception
    {
        return _yserialport.modbusReadBits(slaveNo, pduAddr, nBits);
    }

    public ArrayList<Integer> modbusReadInputBits(int slaveNo, int pduAddr, int nBits) throws YAPI_Exception
    {
        return _yserialport.modbusReadInputBits(slaveNo, pduAddr, nBits);
    }

    public ArrayList<Integer> modbusReadRegisters(int slaveNo, int pduAddr, int nWords) throws YAPI_Exception
    {
        return _yserialport.modbusReadRegisters(slaveNo, pduAddr, nWords);
    }

    public ArrayList<Integer> modbusReadInputRegisters(int slaveNo, int pduAddr, int nWords) throws YAPI_Exception
    {
        return _yserialport.modbusReadInputRegisters(slaveNo, pduAddr, nWords);
    }

    public int modbusWriteBit(int slaveNo, int pduAddr, int value) throws YAPI_Exception
    {
        return _yserialport.modbusWriteBit(slaveNo, pduAddr, value);
    }

    public int modbusWriteBits(int slaveNo, int pduAddr, ArrayList<Integer> bits) throws YAPI_Exception
    {
        return _yserialport.modbusWriteBits(slaveNo, pduAddr, bits);
    }

    public int modbusWriteRegister(int slaveNo, int pduAddr, int value) throws YAPI_Exception
    {
        return _yserialport.modbusWriteRegister(slaveNo, pduAddr, value);
    }

    public int modbusWriteRegisters(int slaveNo, int pduAddr, ArrayList<Integer> values) throws YAPI_Exception
    {
        return _yserialport.modbusWriteRegisters(slaveNo, pduAddr, values);
    }

    public ArrayList<Integer> modbusWriteAndReadRegisters(int slaveNo, int pduWriteAddr, ArrayList<Integer> values, int pduReadAddr, int nReadWords) throws YAPI_Exception
    {
        return _yserialport.modbusWriteAndReadRegisters(slaveNo, pduWriteAddr, values, pduReadAddr, nReadWords);
    }

//--- (end of generated code: YSerialPort class start)
}

