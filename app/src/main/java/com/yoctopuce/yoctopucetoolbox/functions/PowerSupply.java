/*********************************************************************
 *
 * $Id: PowerSupply.java 46698 2021-10-01 06:31:31Z web $
 *
 * Implements PowerSupply wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YPowerSupply;

//--- (YPowerSupply class start)
/**
 * YPowerSupply Class: PowerSupply function interface
 *
 * The Yoctopuce application programming interface allows you to change the voltage set point,
 * the current limit and the enable/disable the output.
 */
 @SuppressWarnings("UnusedDeclaration")
public class PowerSupply extends Function
{
// valueCallbackPowerSupply
    protected double _voltageSetPoint =  YPowerSupply.VOLTAGESETPOINT_INVALID;
    protected double _currentLimit =  YPowerSupply.CURRENTLIMIT_INVALID;
    protected int _powerOutput =  YPowerSupply.POWEROUTPUT_INVALID;
    protected int _voltageSense =  YPowerSupply.VOLTAGESENSE_INVALID;
    protected double _measuredVoltage =  YPowerSupply.MEASUREDVOLTAGE_INVALID;
    protected double _measuredCurrent =  YPowerSupply.MEASUREDCURRENT_INVALID;
    protected double _inputVoltage =  YPowerSupply.INPUTVOLTAGE_INVALID;
    protected double _vInt =  YPowerSupply.VINT_INVALID;
    protected double _ldoTemperature =  YPowerSupply.LDOTEMPERATURE_INVALID;
    protected String _voltageTransition =  YPowerSupply.VOLTAGETRANSITION_INVALID;
    protected double _voltageAtStartUp =  YPowerSupply.VOLTAGEATSTARTUP_INVALID;
    protected double _currentAtStartUp =  YPowerSupply.CURRENTATSTARTUP_INVALID;
    protected String _command =  YPowerSupply.COMMAND_INVALID;
    protected YPowerSupply _ypowersupply;

    public PowerSupply(YPowerSupply yfunc)
    {
       super(yfunc);
       _ypowersupply = yfunc;
    }

    public PowerSupply(String hwid)
    {
       super(hwid);
       _ypowersupply = YPowerSupply.FindPowerSupply(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _voltageSetPoint = _ypowersupply.get_voltageSetPoint();
        _currentLimit = _ypowersupply.get_currentLimit();
        _powerOutput = _ypowersupply.get_powerOutput();
        _voltageSense = _ypowersupply.get_voltageSense();
        _measuredVoltage = _ypowersupply.get_measuredVoltage();
        _measuredCurrent = _ypowersupply.get_measuredCurrent();
        _inputVoltage = _ypowersupply.get_inputVoltage();
        _vInt = _ypowersupply.get_vInt();
        _ldoTemperature = _ypowersupply.get_ldoTemperature();
        _voltageTransition = _ypowersupply.get_voltageTransition();
        _voltageAtStartUp = _ypowersupply.get_voltageAtStartUp();
        _currentAtStartUp = _ypowersupply.get_currentAtStartUp();
        _command = _ypowersupply.get_command();
    }
    /**
     * Changes the voltage set point, in V.
     *
     * @param newval : a floating point number corresponding to the voltage set point, in V
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setVoltageSetPointBg(double newval) throws YAPI_Exception
    {
        _voltageSetPoint = newval;
        _ypowersupply.set_voltageSetPoint(newval);
    }

    /**
     * Returns the voltage set point, in V.
     *
     * @return a floating point number corresponding to the voltage set point, in V
     *
     * On failure, throws an exception or returns Y_VOLTAGESETPOINT_INVALID.
     */
    public double getVoltageSetPoint()
    {
        return _voltageSetPoint;
    }

    /**
     * Changes the current limit, in mA.
     *
     * @param newval : a floating point number corresponding to the current limit, in mA
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setCurrentLimitBg(double newval) throws YAPI_Exception
    {
        _currentLimit = newval;
        _ypowersupply.set_currentLimit(newval);
    }

    /**
     * Returns the current limit, in mA.
     *
     * @return a floating point number corresponding to the current limit, in mA
     *
     * On failure, throws an exception or returns Y_CURRENTLIMIT_INVALID.
     */
    public double getCurrentLimit()
    {
        return _currentLimit;
    }

    /**
     * Returns the power supply output switch state.
     *
     * @return either Y_POWEROUTPUT_OFF or Y_POWEROUTPUT_ON, according to the power supply output switch state
     *
     * On failure, throws an exception or returns Y_POWEROUTPUT_INVALID.
     */
    public int getPowerOutput()
    {
        return _powerOutput;
    }

    /**
     * Changes the power supply output switch state.
     *
     * @param newval : either Y_POWEROUTPUT_OFF or Y_POWEROUTPUT_ON, according to the power supply output switch state
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setPowerOutputBg(int newval) throws YAPI_Exception
    {
        _powerOutput = newval;
        _ypowersupply.set_powerOutput(newval);
    }

    /**
     * Returns the output voltage control point.
     *
     * @return either Y_VOLTAGESENSE_INT or Y_VOLTAGESENSE_EXT, according to the output voltage control point
     *
     * On failure, throws an exception or returns Y_VOLTAGESENSE_INVALID.
     */
    public int getVoltageSense()
    {
        return _voltageSense;
    }

    /**
     * Changes the voltage control point.
     *
     * @param newval : either Y_VOLTAGESENSE_INT or Y_VOLTAGESENSE_EXT, according to the voltage control point
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setVoltageSenseBg(int newval) throws YAPI_Exception
    {
        _voltageSense = newval;
        _ypowersupply.set_voltageSense(newval);
    }

    /**
     * Returns the measured output voltage, in V.
     *
     * @return a floating point number corresponding to the measured output voltage, in V
     *
     * On failure, throws an exception or returns Y_MEASUREDVOLTAGE_INVALID.
     */
    public double getMeasuredVoltage()
    {
        return _measuredVoltage;
    }

    /**
     * Returns the measured output current, in mA.
     *
     * @return a floating point number corresponding to the measured output current, in mA
     *
     * On failure, throws an exception or returns Y_MEASUREDCURRENT_INVALID.
     */
    public double getMeasuredCurrent()
    {
        return _measuredCurrent;
    }

    /**
     * Returns the measured input voltage, in V.
     *
     * @return a floating point number corresponding to the measured input voltage, in V
     *
     * On failure, throws an exception or returns Y_INPUTVOLTAGE_INVALID.
     */
    public double getInputVoltage()
    {
        return _inputVoltage;
    }

    /**
     * Returns the internal voltage, in V.
     *
     * @return a floating point number corresponding to the internal voltage, in V
     *
     * On failure, throws an exception or returns Y_VINT_INVALID.
     */
    public double getVInt()
    {
        return _vInt;
    }

    /**
     * Returns the LDO temperature, in Celsius.
     *
     * @return a floating point number corresponding to the LDO temperature, in Celsius
     *
     * On failure, throws an exception or returns Y_LDOTEMPERATURE_INVALID.
     */
    public double getLdoTemperature()
    {
        return _ldoTemperature;
    }

    public String getVoltageTransition()
    {
        return _voltageTransition;
    }

    public void setVoltageTransitionBg(String newval) throws YAPI_Exception
    {
        _voltageTransition = newval;
        _ypowersupply.set_voltageTransition(newval);
    }

    /**
     * Changes the voltage set point at device start up. Remember to call the matching
     * module saveToFlash() method, otherwise this call has no effect.
     *
     * @param newval : a floating point number corresponding to the voltage set point at device start up
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setVoltageAtStartUpBg(double newval) throws YAPI_Exception
    {
        _voltageAtStartUp = newval;
        _ypowersupply.set_voltageAtStartUp(newval);
    }

    /**
     * Returns the selected voltage set point at device startup, in V.
     *
     * @return a floating point number corresponding to the selected voltage set point at device startup, in V
     *
     * On failure, throws an exception or returns Y_VOLTAGEATSTARTUP_INVALID.
     */
    public double getVoltageAtStartUp()
    {
        return _voltageAtStartUp;
    }

    /**
     * Changes the current limit at device start up. Remember to call the matching
     * module saveToFlash() method, otherwise this call has no effect.
     *
     * @param newval : a floating point number corresponding to the current limit at device start up
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setCurrentAtStartUpBg(double newval) throws YAPI_Exception
    {
        _currentAtStartUp = newval;
        _ypowersupply.set_currentAtStartUp(newval);
    }

    /**
     * Returns the selected current limit at device startup, in mA.
     *
     * @return a floating point number corresponding to the selected current limit at device startup, in mA
     *
     * On failure, throws an exception or returns Y_CURRENTATSTARTUP_INVALID.
     */
    public double getCurrentAtStartUp()
    {
        return _currentAtStartUp;
    }

    public String getCommand()
    {
        return _command;
    }

    public void setCommandBg(String newval) throws YAPI_Exception
    {
        _command = newval;
        _ypowersupply.set_command(newval);
    }

    public static YPowerSupply FindPowerSupply(String func)
    {
        return YPowerSupply.FindPowerSupply(func);
    }

    public int voltageMove(double V_target, int ms_duration) throws YAPI_Exception
    {
        return _ypowersupply.voltageMove(V_target, ms_duration);
    }

//--- (end of YPowerSupply class start)
}

