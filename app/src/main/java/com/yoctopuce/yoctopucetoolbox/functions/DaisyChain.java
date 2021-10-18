/*********************************************************************
 *
 * $Id: DaisyChain.java 46698 2021-10-01 06:31:31Z web $
 *
 * Implements DaisyChain wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YDaisyChain;

//--- (YDaisyChain class start)
/**
 * YDaisyChain Class: DaisyChain function interface
 *
 * The YDaisyChain interface can be used to verify that devices that
 * are daisy-chained directly from device to device, without a hub,
 * are detected properly.
 */
 @SuppressWarnings("UnusedDeclaration")
public class DaisyChain extends Function
{
// valueCallbackDaisyChain
    protected int _daisyState =  YDaisyChain.DAISYSTATE_INVALID;
    protected int _childCount =  YDaisyChain.CHILDCOUNT_INVALID;
    protected int _requiredChildCount =  YDaisyChain.REQUIREDCHILDCOUNT_INVALID;
    protected YDaisyChain _ydaisychain;

    public DaisyChain(YDaisyChain yfunc)
    {
       super(yfunc);
       _ydaisychain = yfunc;
    }

    public DaisyChain(String hwid)
    {
       super(hwid);
       _ydaisychain = YDaisyChain.FindDaisyChain(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _daisyState = _ydaisychain.get_daisyState();
        _childCount = _ydaisychain.get_childCount();
        _requiredChildCount = _ydaisychain.get_requiredChildCount();
    }
    /**
     * Returns the state of the daisy-link between modules.
     *
     * @return a value among Y_DAISYSTATE_READY, Y_DAISYSTATE_IS_CHILD, Y_DAISYSTATE_FIRMWARE_MISMATCH,
     * Y_DAISYSTATE_CHILD_MISSING and Y_DAISYSTATE_CHILD_LOST corresponding to the state of the daisy-link
     * between modules
     *
     * On failure, throws an exception or returns Y_DAISYSTATE_INVALID.
     */
    public int getDaisyState()
    {
        return _daisyState;
    }

    /**
     * Returns the number of child nodes currently detected.
     *
     * @return an integer corresponding to the number of child nodes currently detected
     *
     * On failure, throws an exception or returns Y_CHILDCOUNT_INVALID.
     */
    public int getChildCount()
    {
        return _childCount;
    }

    /**
     * Returns the number of child nodes expected in normal conditions.
     *
     * @return an integer corresponding to the number of child nodes expected in normal conditions
     *
     * On failure, throws an exception or returns Y_REQUIREDCHILDCOUNT_INVALID.
     */
    public int getRequiredChildCount()
    {
        return _requiredChildCount;
    }

    /**
     * Changes the number of child nodes expected in normal conditions.
     * If the value is zero, no check is performed. If it is non-zero, the number
     * child nodes is checked on startup and the status will change to error if
     * the count does not match.
     *
     * @param newval : an integer corresponding to the number of child nodes expected in normal conditions
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setRequiredChildCountBg(int newval) throws YAPI_Exception
    {
        _requiredChildCount = newval;
        _ydaisychain.set_requiredChildCount(newval);
    }

    public static YDaisyChain FindDaisyChain(String func)
    {
        return YDaisyChain.FindDaisyChain(func);
    }

//--- (end of YDaisyChain class start)
}

