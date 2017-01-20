/*********************************************************************
 *
 * $Id: pic24config.php 26169 2016-12-12 01:36:34Z mvuilleu $
 *
 * Implements MultiAxisController wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YMultiAxisController;
import java.util.ArrayList;

//--- (YMultiAxisController class start)
/**
 * YMultiAxisController Class: MultiAxisController function interface
 *
 * The Yoctopuce application programming interface allows you to drive a stepper motor.
 */
 @SuppressWarnings("UnusedDeclaration")
public class MultiAxisController extends Function
{
// valueCallbackMultiAxisController
    protected int _nAxis =  YMultiAxisController.NAXIS_INVALID;
    protected int _globalState =  YMultiAxisController.GLOBALSTATE_INVALID;
    protected String _command =  YMultiAxisController.COMMAND_INVALID;
    protected YMultiAxisController _ymultiaxiscontroller;

    public MultiAxisController(YMultiAxisController yfunc)
    {
       super(yfunc);
       _ymultiaxiscontroller = yfunc;
    }

    public MultiAxisController(String hwid)
    {
       super(hwid);
       _ymultiaxiscontroller = YMultiAxisController.FindMultiAxisController(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _nAxis = _ymultiaxiscontroller.get_nAxis();
        _globalState = _ymultiaxiscontroller.get_globalState();
        _command = _ymultiaxiscontroller.get_command();
    }
    /**
     * Returns the number of synchronized controllers.
     *
     * @return an integer corresponding to the number of synchronized controllers
     *
     * On failure, throws an exception or returns Y_NAXIS_INVALID.
     */
    public int getNAxis()
    {
        return _nAxis;
    }

    /**
     * Changes the number of synchronized controllers.
     *
     * @param newval : an integer corresponding to the number of synchronized controllers
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setNAxisBg(int newval) throws YAPI_Exception
    {
        _nAxis = newval;
        _ymultiaxiscontroller.set_nAxis(newval);
    }

    /**
     * Returns the stepper motor set overall state.
     *
     * @return a value among Y_GLOBALSTATE_ABSENT, Y_GLOBALSTATE_ALERT, Y_GLOBALSTATE_HI_Z,
     * Y_GLOBALSTATE_STOP, Y_GLOBALSTATE_RUN and Y_GLOBALSTATE_BATCH corresponding to the stepper motor
     * set overall state
     *
     * On failure, throws an exception or returns Y_GLOBALSTATE_INVALID.
     */
    public int getGlobalState()
    {
        return _globalState;
    }

    public String getCommand()
    {
        return _command;
    }

    public void setCommandBg(String newval) throws YAPI_Exception
    {
        _command = newval;
        _ymultiaxiscontroller.set_command(newval);
    }

    public static YMultiAxisController FindMultiAxisController(String func)
    {
        return YMultiAxisController.FindMultiAxisController(func);
    }

    public int sendCommand(String command) throws YAPI_Exception
    {
        return _ymultiaxiscontroller.sendCommand(command);
    }

    public int reset() throws YAPI_Exception
    {
        return _ymultiaxiscontroller.reset();
    }

    public int findHomePosition(ArrayList<Double> speed) throws YAPI_Exception
    {
        return _ymultiaxiscontroller.findHomePosition(speed);
    }

    public int moveTo(ArrayList<Double> absPos) throws YAPI_Exception
    {
        return _ymultiaxiscontroller.moveTo(absPos);
    }

    public int moveRel(ArrayList<Double> relPos) throws YAPI_Exception
    {
        return _ymultiaxiscontroller.moveRel(relPos);
    }

    public int pause(int waitMs) throws YAPI_Exception
    {
        return _ymultiaxiscontroller.pause(waitMs);
    }

    public int emergencyStop() throws YAPI_Exception
    {
        return _ymultiaxiscontroller.emergencyStop();
    }

    public int abortAndBrake() throws YAPI_Exception
    {
        return _ymultiaxiscontroller.abortAndBrake();
    }

    public int abortAndHiZ() throws YAPI_Exception
    {
        return _ymultiaxiscontroller.abortAndHiZ();
    }

//--- (end of YMultiAxisController class start)
}

