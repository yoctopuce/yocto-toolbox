/*********************************************************************
 *
 * $Id: VoltageOutput.java 46698 2021-10-01 06:31:31Z web $
 *
 * Implements VoltageOutput wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YVoltageOutput;

//--- (YVoltageOutput class start)
/**
 * YVoltageOutput Class: VoltageOutput function interface
 *
 * The Yoctopuce application programming interface allows you to change the value of the voltage output.
 */
 @SuppressWarnings("UnusedDeclaration")
public class VoltageOutput extends Function
{
// valueCallbackVoltageOutput
    protected double _currentVoltage =  YVoltageOutput.CURRENTVOLTAGE_INVALID;
    protected String _voltageTransition =  YVoltageOutput.VOLTAGETRANSITION_INVALID;
    protected double _voltageAtStartUp =  YVoltageOutput.VOLTAGEATSTARTUP_INVALID;
    protected YVoltageOutput _yvoltageoutput;

    public VoltageOutput(YVoltageOutput yfunc)
    {
       super(yfunc);
       _yvoltageoutput = yfunc;
    }

    public VoltageOutput(String hwid)
    {
       super(hwid);
       _yvoltageoutput = YVoltageOutput.FindVoltageOutput(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _currentVoltage = _yvoltageoutput.get_currentVoltage();
        _voltageTransition = _yvoltageoutput.get_voltageTransition();
        _voltageAtStartUp = _yvoltageoutput.get_voltageAtStartUp();
    }
    /**
     * Changes the output voltage, in V. Valid range is from 0 to 10V.
     *
     * @param newval : a floating point number corresponding to the output voltage, in V
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setCurrentVoltageBg(double newval) throws YAPI_Exception
    {
        _currentVoltage = newval;
        _yvoltageoutput.set_currentVoltage(newval);
    }

    /**
     * Returns the output voltage set point, in V.
     *
     * @return a floating point number corresponding to the output voltage set point, in V
     *
     * On failure, throws an exception or returns Y_CURRENTVOLTAGE_INVALID.
     */
    public double getCurrentVoltage()
    {
        return _currentVoltage;
    }

    public String getVoltageTransition()
    {
        return _voltageTransition;
    }

    public void setVoltageTransitionBg(String newval) throws YAPI_Exception
    {
        _voltageTransition = newval;
        _yvoltageoutput.set_voltageTransition(newval);
    }

    /**
     * Changes the output voltage at device start up. Remember to call the matching
     * module saveToFlash() method, otherwise this call has no effect.
     *
     * @param newval : a floating point number corresponding to the output voltage at device start up
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setVoltageAtStartUpBg(double newval) throws YAPI_Exception
    {
        _voltageAtStartUp = newval;
        _yvoltageoutput.set_voltageAtStartUp(newval);
    }

    /**
     * Returns the selected voltage output at device startup, in V.
     *
     * @return a floating point number corresponding to the selected voltage output at device startup, in V
     *
     * On failure, throws an exception or returns Y_VOLTAGEATSTARTUP_INVALID.
     */
    public double getVoltageAtStartUp()
    {
        return _voltageAtStartUp;
    }

    public static YVoltageOutput FindVoltageOutput(String func)
    {
        return YVoltageOutput.FindVoltageOutput(func);
    }

    public int voltageMove(double V_target, int ms_duration) throws YAPI_Exception
    {
        return _yvoltageoutput.voltageMove(V_target, ms_duration);
    }

//--- (end of YVoltageOutput class start)
}

