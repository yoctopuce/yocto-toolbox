/*********************************************************************
 * $Id: Function.java 26014 2016-11-24 13:52:08Z seb $
 * <p>
 * Implements Relay wrapper for Android toolbox
 * <p>
 * - - - - - - - - - License information: - - - - - - - - -
 * <p>
 * Copyright (C) 2011 and beyond by Yoctopuce Sarl, Switzerland.
 * <p>
 * Yoctopuce Sarl (hereafter Licensor) grants to you a perpetual
 * non-exclusive license to use, modify, copy and integrate this
 * file into your software for the sole purpose of interfacing
 * with Yoctopuce products.
 * <p>
 * You may reproduce and distribute copies of this file in
 * source or object form, as long as the sole purpose of this
 * code is to interface with Yoctopuce products. You must retain
 * this notice in the distributed source file.
 * <p>
 * You should refer to Yoctopuce General Terms and Conditions
 * for additional information regarding oyour rights and
 * obligations.
 * <p>
 * THE SOFTWARE AND DOCUMENTATION ARE PROVIDED 'AS IS' WITHOUT
 * WARRANTY OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING
 * WITHOUT LIMITATION, ANY WARRANTY OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE, TITLE AND NON-INFRINGEMENT. IN NO
 * EVENT SHALL LICENSOR BE LIABLE FOR ANY INCIDENTAL, SPECIAL,
 * INDIRECT OR CONSEQUENTIAL DAMAGES, LOST PROFITS OR LOST DATA,
 * COST OF PROCUREMENT OF SUBSTITUTE GOODS, TECHNOLOGY OR
 * SERVICES, ANY CLAIMS BY THIRD PARTIES (INCLUDING BUT NOT
 * LIMITED TO ANY DEFENSE THEREOF), ANY CLAIMS FOR INDEMNITY OR
 * CONTRIBUTION, OR OTHER SIMILAR COSTS, WHETHER ASSERTED ON THE
 * BASIS OF CONTRACT, TORT (INCLUDING NEGLIGENCE), BREACH OF
 * WARRANTY, OR OTHERWISE.
 *********************************************************************/

package com.yoctopuce.yoctopucetoolbox.functions;

import android.os.AsyncTask;

import com.yoctopuce.YoctoAPI.YAPI;
import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YFunction;

import java.net.URL;

//--- (generated code: YFunction class start)
/**
 * YFunction Class: Common function interface
 *
 * This is the parent class for all public objects representing device functions documented in
 * the high-level programming API. This abstract class does all the real job, but without
 * knowledge of the specific function attributes.
 *
 * Instantiating a child class of YFunction does not cause any communication.
 * The instance simply keeps track of its function identifier, and will dynamically bind
 * to a matching device at the time it is really being used to read or set an attribute.
 * In order to allow true hot-plug replacement of one device by another, the binding stay
 * dynamic through the life of the object.
 *
 * The YFunction class implements a generic high-level cache for the attribute values of
 * the specified function, pre-parsed from the REST API string.
 */
 @SuppressWarnings("UnusedDeclaration")
public class Function
{
    // Yoctopuce error codes, used by default as function return value

// valueCallbackFunction
    protected String _logicalName =  YFunction.LOGICALNAME_INVALID;
    protected String _advertisedValue =  YFunction.ADVERTISEDVALUE_INVALID;
    protected long _cacheExpiration = 0;
    protected String _serial;
    protected String _funId;
    protected String _hwId;
    protected YFunction _yfunction;

    public Function(YFunction yfunc)
    {
       _yfunction = yfunc;
    }

    public Function(String hwid)
    {
       _yfunction = YFunction.FindFunction(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        _logicalName = _yfunction.get_logicalName();
        _advertisedValue = _yfunction.get_advertisedValue();
    }
    /**
     * Returns the logical name of the function.
     *
     * @return a string corresponding to the logical name of the function
     *
     * On failure, throws an exception or returns Y_LOGICALNAME_INVALID.
     */
    public String getLogicalName()
    {
        return _logicalName;
    }

    /**
     * Changes the logical name of the function. You can use yCheckLogicalName()
     * prior to this call to make sure that your parameter is valid.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : a string corresponding to the logical name of the function
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setLogicalNameBg(String newval) throws YAPI_Exception
    {
        _logicalName = newval;
        _yfunction.set_logicalName(newval);
    }

    /**
     * Returns a short string representing the current state of the function.
     *
     * @return a string corresponding to a short string representing the current state of the function
     *
     * On failure, throws an exception or returns Y_ADVERTISEDVALUE_INVALID.
     */
    public String getAdvertisedValue()
    {
        return _advertisedValue;
    }

    public void setAdvertisedValueBg(String newval) throws YAPI_Exception
    {
        _advertisedValue = newval;
        _yfunction.set_advertisedValue(newval);
    }




}

