/*********************************************************************
 *
 * $Id: CurrentLoopOutput.java 46698 2021-10-01 06:31:31Z web $
 *
 * Implements CurrentLoopOutput wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YCurrentLoopOutput;

//--- (YCurrentLoopOutput class start)
/**
 * YCurrentLoopOutput Class: CurrentLoopOutput function interface
 *
 * The Yoctopuce application programming interface allows you to change the value of the 4-20mA
 * output as well as to know the current loop state.
 */
 @SuppressWarnings("UnusedDeclaration")
public class CurrentLoopOutput extends Function
{
// valueCallbackCurrentLoopOutput
    protected double _current =  YCurrentLoopOutput.CURRENT_INVALID;
    protected String _currentTransition =  YCurrentLoopOutput.CURRENTTRANSITION_INVALID;
    protected double _currentAtStartUp =  YCurrentLoopOutput.CURRENTATSTARTUP_INVALID;
    protected int _loopPower =  YCurrentLoopOutput.LOOPPOWER_INVALID;
    protected YCurrentLoopOutput _ycurrentloopoutput;

    public CurrentLoopOutput(YCurrentLoopOutput yfunc)
    {
       super(yfunc);
       _ycurrentloopoutput = yfunc;
    }

    public CurrentLoopOutput(String hwid)
    {
       super(hwid);
       _ycurrentloopoutput = YCurrentLoopOutput.FindCurrentLoopOutput(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _current = _ycurrentloopoutput.get_current();
        _currentTransition = _ycurrentloopoutput.get_currentTransition();
        _currentAtStartUp = _ycurrentloopoutput.get_currentAtStartUp();
        _loopPower = _ycurrentloopoutput.get_loopPower();
    }
    /**
     * Changes the current loop, the valid range is from 3 to 21mA. If the loop is
     * not propely powered, the  target current is not reached and
     * loopPower is set to LOWPWR.
     *
     * @param newval : a floating point number corresponding to the current loop, the valid range is from 3 to 21mA
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setCurrentBg(double newval) throws YAPI_Exception
    {
        _current = newval;
        _ycurrentloopoutput.set_current(newval);
    }

    /**
     * Returns the loop current set point in mA.
     *
     * @return a floating point number corresponding to the loop current set point in mA
     *
     * On failure, throws an exception or returns Y_CURRENT_INVALID.
     */
    public double getCurrent()
    {
        return _current;
    }

    public String getCurrentTransition()
    {
        return _currentTransition;
    }

    public void setCurrentTransitionBg(String newval) throws YAPI_Exception
    {
        _currentTransition = newval;
        _ycurrentloopoutput.set_currentTransition(newval);
    }

    /**
     * Changes the loop current at device start up. Remember to call the matching
     * module saveToFlash() method, otherwise this call has no effect.
     *
     * @param newval : a floating point number corresponding to the loop current at device start up
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setCurrentAtStartUpBg(double newval) throws YAPI_Exception
    {
        _currentAtStartUp = newval;
        _ycurrentloopoutput.set_currentAtStartUp(newval);
    }

    /**
     * Returns the current in the loop at device startup, in mA.
     *
     * @return a floating point number corresponding to the current in the loop at device startup, in mA
     *
     * On failure, throws an exception or returns Y_CURRENTATSTARTUP_INVALID.
     */
    public double getCurrentAtStartUp()
    {
        return _currentAtStartUp;
    }

    /**
     * Returns the loop powerstate.  POWEROK: the loop
     * is powered. NOPWR: the loop in not powered. LOWPWR: the loop is not
     * powered enough to maintain the current required (insufficient voltage).
     *
     * @return a value among Y_LOOPPOWER_NOPWR, Y_LOOPPOWER_LOWPWR and Y_LOOPPOWER_POWEROK corresponding
     * to the loop powerstate
     *
     * On failure, throws an exception or returns Y_LOOPPOWER_INVALID.
     */
    public int getLoopPower()
    {
        return _loopPower;
    }

    public static YCurrentLoopOutput FindCurrentLoopOutput(String func)
    {
        return YCurrentLoopOutput.FindCurrentLoopOutput(func);
    }

    public int currentMove(double mA_target, int ms_duration) throws YAPI_Exception
    {
        return _ycurrentloopoutput.currentMove(mA_target, ms_duration);
    }

//--- (end of YCurrentLoopOutput class start)
}

