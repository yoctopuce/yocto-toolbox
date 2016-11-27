/*********************************************************************
 *
 * $Id: DigitalIO.java 26014 2016-11-24 13:52:08Z seb $
 *
 * Implements DigitalIO wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YDigitalIO;

//--- (YDigitalIO class start)
/**
 * YDigitalIO Class: Digital IO function interface
 *
 * The Yoctopuce application programming interface allows you to switch the state of each
 * bit of the I/O port. You can switch all bits at once, or one by one. The library
 * can also automatically generate short pulses of a determined duration. Electrical behavior
 * of each I/O can be modified (open drain and reverse polarity).
 */
 @SuppressWarnings("UnusedDeclaration")
public class DigitalIO extends Function
{
// valueCallbackDigitalIO
    protected int _portState =  YDigitalIO.PORTSTATE_INVALID;
    protected int _portDirection =  YDigitalIO.PORTDIRECTION_INVALID;
    protected int _portOpenDrain =  YDigitalIO.PORTOPENDRAIN_INVALID;
    protected int _portPolarity =  YDigitalIO.PORTPOLARITY_INVALID;
    protected int _portSize =  YDigitalIO.PORTSIZE_INVALID;
    protected int _outputVoltage =  YDigitalIO.OUTPUTVOLTAGE_INVALID;
    protected String _command =  YDigitalIO.COMMAND_INVALID;
    protected YDigitalIO _ydigitalio;

    public DigitalIO(YDigitalIO yfunc)
    {
       super(yfunc);
       _ydigitalio = yfunc;
    }

    public DigitalIO(String hwid)
    {
       super(hwid);
       _ydigitalio = YDigitalIO.FindDigitalIO(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _portState = _ydigitalio.get_portState();
        _portDirection = _ydigitalio.get_portDirection();
        _portOpenDrain = _ydigitalio.get_portOpenDrain();
        _portPolarity = _ydigitalio.get_portPolarity();
        _portSize = _ydigitalio.get_portSize();
        _outputVoltage = _ydigitalio.get_outputVoltage();
        _command = _ydigitalio.get_command();
    }
    /**
     * Returns the digital IO port state: bit 0 represents input 0, and so on.
     *
     * @return an integer corresponding to the digital IO port state: bit 0 represents input 0, and so on
     *
     * On failure, throws an exception or returns Y_PORTSTATE_INVALID.
     */
    public int getPortState()
    {
        return _portState;
    }

    /**
     * Changes the digital IO port state: bit 0 represents input 0, and so on. This function has no effect
     * on bits configured as input in portDirection.
     *
     * @param newval : an integer corresponding to the digital IO port state: bit 0 represents input 0, and so on
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setPortStateBg(int newval) throws YAPI_Exception
    {
        _portState = newval;
        _ydigitalio.set_portState(newval);
    }

    /**
     * Returns the IO direction of all bits of the port: 0 makes a bit an input, 1 makes it an output.
     *
     * @return an integer corresponding to the IO direction of all bits of the port: 0 makes a bit an
     * input, 1 makes it an output
     *
     * On failure, throws an exception or returns Y_PORTDIRECTION_INVALID.
     */
    public int getPortDirection()
    {
        return _portDirection;
    }

    /**
     * Changes the IO direction of all bits of the port: 0 makes a bit an input, 1 makes it an output.
     * Remember to call the saveToFlash() method  to make sure the setting is kept after a reboot.
     *
     * @param newval : an integer corresponding to the IO direction of all bits of the port: 0 makes a bit
     * an input, 1 makes it an output
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setPortDirectionBg(int newval) throws YAPI_Exception
    {
        _portDirection = newval;
        _ydigitalio.set_portDirection(newval);
    }

    /**
     * Returns the electrical interface for each bit of the port. For each bit set to 0  the matching I/O
     * works in the regular,
     * intuitive way, for each bit set to 1, the I/O works in reverse mode.
     *
     * @return an integer corresponding to the electrical interface for each bit of the port
     *
     * On failure, throws an exception or returns Y_PORTOPENDRAIN_INVALID.
     */
    public int getPortOpenDrain()
    {
        return _portOpenDrain;
    }

    /**
     * Changes the electrical interface for each bit of the port. 0 makes a bit a regular input/output, 1 makes
     * it an open-drain (open-collector) input/output. Remember to call the
     * saveToFlash() method  to make sure the setting is kept after a reboot.
     *
     * @param newval : an integer corresponding to the electrical interface for each bit of the port
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setPortOpenDrainBg(int newval) throws YAPI_Exception
    {
        _portOpenDrain = newval;
        _ydigitalio.set_portOpenDrain(newval);
    }

    /**
     * Returns the polarity of all the bits of the port.  For each bit set to 0, the matching I/O works the regular,
     * intuitive way; for each bit set to 1, the I/O works in reverse mode.
     *
     * @return an integer corresponding to the polarity of all the bits of the port
     *
     * On failure, throws an exception or returns Y_PORTPOLARITY_INVALID.
     */
    public int getPortPolarity()
    {
        return _portPolarity;
    }

    /**
     * Changes the polarity of all the bits of the port: For each bit set to 0, the matching I/O works the regular,
     * intuitive way; for each bit set to 1, the I/O works in reverse mode.
     * Remember to call the saveToFlash() method  to make sure the setting will be kept after a reboot.
     *
     * @param newval : an integer corresponding to the polarity of all the bits of the port: For each bit
     * set to 0, the matching I/O works the regular,
     *         intuitive way; for each bit set to 1, the I/O works in reverse mode
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setPortPolarityBg(int newval) throws YAPI_Exception
    {
        _portPolarity = newval;
        _ydigitalio.set_portPolarity(newval);
    }

    /**
     * Returns the number of bits implemented in the I/O port.
     *
     * @return an integer corresponding to the number of bits implemented in the I/O port
     *
     * On failure, throws an exception or returns Y_PORTSIZE_INVALID.
     */
    public int getPortSize()
    {
        return _portSize;
    }

    /**
     * Returns the voltage source used to drive output bits.
     *
     * @return a value among Y_OUTPUTVOLTAGE_USB_5V, Y_OUTPUTVOLTAGE_USB_3V and Y_OUTPUTVOLTAGE_EXT_V
     * corresponding to the voltage source used to drive output bits
     *
     * On failure, throws an exception or returns Y_OUTPUTVOLTAGE_INVALID.
     */
    public int getOutputVoltage()
    {
        return _outputVoltage;
    }

    /**
     * Changes the voltage source used to drive output bits.
     * Remember to call the saveToFlash() method  to make sure the setting is kept after a reboot.
     *
     * @param newval : a value among Y_OUTPUTVOLTAGE_USB_5V, Y_OUTPUTVOLTAGE_USB_3V and
     * Y_OUTPUTVOLTAGE_EXT_V corresponding to the voltage source used to drive output bits
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setOutputVoltageBg(int newval) throws YAPI_Exception
    {
        _outputVoltage = newval;
        _ydigitalio.set_outputVoltage(newval);
    }

    public String getCommand()
    {
        return _command;
    }

    public void setCommandBg(String newval) throws YAPI_Exception
    {
        _command = newval;
        _ydigitalio.set_command(newval);
    }

//--- (end of YDigitalIO class start)
}

