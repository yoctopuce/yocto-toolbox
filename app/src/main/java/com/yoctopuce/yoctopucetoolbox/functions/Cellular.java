/*********************************************************************
 *
 * $Id: Cellular.java 46698 2021-10-01 06:31:31Z web $
 *
 * Implements Cellular wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YCellRecord;
import com.yoctopuce.YoctoAPI.YCellular;

import java.util.ArrayList;

//--- (generated code: YCellular class start)
/**
 * YCellular Class: Cellular function interface
 *
 * YCellular functions provides control over cellular network parameters
 * and status for devices that are GSM-enabled.
 */
 @SuppressWarnings("UnusedDeclaration")
public class Cellular extends Function
{
// valueCallbackCellular
    protected int _linkQuality =  YCellular.LINKQUALITY_INVALID;
    protected String _cellOperator =  YCellular.CELLOPERATOR_INVALID;
    protected String _cellIdentifier =  YCellular.CELLIDENTIFIER_INVALID;
    protected int _cellType =  YCellular.CELLTYPE_INVALID;
    protected String _imsi =  YCellular.IMSI_INVALID;
    protected String _message =  YCellular.MESSAGE_INVALID;
    protected String _pin =  YCellular.PIN_INVALID;
    protected String _lockedOperator =  YCellular.LOCKEDOPERATOR_INVALID;
    protected int _airplaneMode =  YCellular.AIRPLANEMODE_INVALID;
    protected int _enableData =  YCellular.ENABLEDATA_INVALID;
    protected String _apn =  YCellular.APN_INVALID;
    protected String _apnSecret =  YCellular.APNSECRET_INVALID;
    protected int _pingInterval =  YCellular.PINGINTERVAL_INVALID;
    protected int _dataSent =  YCellular.DATASENT_INVALID;
    protected int _dataReceived =  YCellular.DATARECEIVED_INVALID;
    protected String _command =  YCellular.COMMAND_INVALID;
    protected YCellular _ycellular;

    public Cellular(YCellular yfunc)
    {
       super(yfunc);
       _ycellular = yfunc;
    }

    public Cellular(String hwid)
    {
       super(hwid);
       _ycellular = YCellular.FindCellular(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _linkQuality = _ycellular.get_linkQuality();
        _cellOperator = _ycellular.get_cellOperator();
        _cellIdentifier = _ycellular.get_cellIdentifier();
        _cellType = _ycellular.get_cellType();
        _imsi = _ycellular.get_imsi();
        _message = _ycellular.get_message();
        _pin = _ycellular.get_pin();
        _lockedOperator = _ycellular.get_lockedOperator();
        _airplaneMode = _ycellular.get_airplaneMode();
        _enableData = _ycellular.get_enableData();
        _apn = _ycellular.get_apn();
        _apnSecret = _ycellular.get_apnSecret();
        _pingInterval = _ycellular.get_pingInterval();
        _dataSent = _ycellular.get_dataSent();
        _dataReceived = _ycellular.get_dataReceived();
        _command = _ycellular.get_command();
    }
    /**
     * Returns the link quality, expressed in percent.
     *
     * @return an integer corresponding to the link quality, expressed in percent
     *
     * On failure, throws an exception or returns Y_LINKQUALITY_INVALID.
     */
    public int getLinkQuality()
    {
        return _linkQuality;
    }

    /**
     * Returns the name of the cell operator currently in use.
     *
     * @return a string corresponding to the name of the cell operator currently in use
     *
     * On failure, throws an exception or returns Y_CELLOPERATOR_INVALID.
     */
    public String getCellOperator()
    {
        return _cellOperator;
    }

    /**
     * Returns the unique identifier of the cellular antenna in use: MCC, MNC, LAC and Cell ID.
     *
     * @return a string corresponding to the unique identifier of the cellular antenna in use: MCC, MNC,
     * LAC and Cell ID
     *
     * On failure, throws an exception or returns Y_CELLIDENTIFIER_INVALID.
     */
    public String getCellIdentifier()
    {
        return _cellIdentifier;
    }

    /**
     * Active cellular connection type.
     *
     * @return a value among Y_CELLTYPE_GPRS, Y_CELLTYPE_EGPRS, Y_CELLTYPE_WCDMA, Y_CELLTYPE_HSDPA,
     * Y_CELLTYPE_NONE and Y_CELLTYPE_CDMA
     *
     * On failure, throws an exception or returns Y_CELLTYPE_INVALID.
     */
    public int getCellType()
    {
        return _cellType;
    }

    /**
     * Returns an opaque string if a PIN code has been configured in the device to access
     * the SIM card, or an empty string if none has been configured or if the code provided
     * was rejected by the SIM card.
     *
     * @return a string corresponding to an opaque string if a PIN code has been configured in the device to access
     *         the SIM card, or an empty string if none has been configured or if the code provided
     *         was rejected by the SIM card
     *
     * On failure, throws an exception or returns Y_IMSI_INVALID.
     */
    public String getImsi()
    {
        return _imsi;
    }

    /**
     * Returns the latest status message from the wireless interface.
     *
     * @return a string corresponding to the latest status message from the wireless interface
     *
     * On failure, throws an exception or returns Y_MESSAGE_INVALID.
     */
    public String getMessage()
    {
        return _message;
    }

    /**
     * Returns an opaque string if a PIN code has been configured in the device to access
     * the SIM card, or an empty string if none has been configured or if the code provided
     * was rejected by the SIM card.
     *
     * @return a string corresponding to an opaque string if a PIN code has been configured in the device to access
     *         the SIM card, or an empty string if none has been configured or if the code provided
     *         was rejected by the SIM card
     *
     * On failure, throws an exception or returns Y_PIN_INVALID.
     */
    public String getPin()
    {
        return _pin;
    }

    /**
     * Changes the PIN code used by the module to access the SIM card.
     * This function does not change the code on the SIM card itself, but only changes
     * the parameter used by the device to try to get access to it. If the SIM code
     * does not work immediately on first try, it will be automatically forgotten
     * and the message will be set to "Enter SIM PIN". The method should then be
     * invoked again with right correct PIN code. After three failed attempts in a row,
     * the message is changed to "Enter SIM PUK" and the SIM card PUK code must be
     * provided using method sendPUK.
     *
     * Remember to call the saveToFlash() method of the module to save the
     * new value in the device flash.
     *
     * @param newval : a string corresponding to the PIN code used by the module to access the SIM card
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setPinBg(String newval) throws YAPI_Exception
    {
        _pin = newval;
        _ycellular.set_pin(newval);
    }

    /**
     * Returns the name of the only cell operator to use if automatic choice is disabled,
     * or an empty string if the SIM card will automatically choose among available
     * cell operators.
     *
     * @return a string corresponding to the name of the only cell operator to use if automatic choice is disabled,
     *         or an empty string if the SIM card will automatically choose among available
     *         cell operators
     *
     * On failure, throws an exception or returns Y_LOCKEDOPERATOR_INVALID.
     */
    public String getLockedOperator()
    {
        return _lockedOperator;
    }

    /**
     * Changes the name of the cell operator to be used. If the name is an empty
     * string, the choice will be made automatically based on the SIM card. Otherwise,
     * the selected operator is the only one that will be used.
     *
     * @param newval : a string corresponding to the name of the cell operator to be used
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setLockedOperatorBg(String newval) throws YAPI_Exception
    {
        _lockedOperator = newval;
        _ycellular.set_lockedOperator(newval);
    }

    /**
     * Returns true if the airplane mode is active (radio turned off).
     *
     * @return either Y_AIRPLANEMODE_OFF or Y_AIRPLANEMODE_ON, according to true if the airplane mode is
     * active (radio turned off)
     *
     * On failure, throws an exception or returns Y_AIRPLANEMODE_INVALID.
     */
    public int getAirplaneMode()
    {
        return _airplaneMode;
    }

    /**
     * Changes the activation state of airplane mode (radio turned off).
     *
     * @param newval : either Y_AIRPLANEMODE_OFF or Y_AIRPLANEMODE_ON, according to the activation state
     * of airplane mode (radio turned off)
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setAirplaneModeBg(int newval) throws YAPI_Exception
    {
        _airplaneMode = newval;
        _ycellular.set_airplaneMode(newval);
    }

    /**
     * Returns the condition for enabling IP data services (GPRS).
     * When data services are disabled, SMS are the only mean of communication.
     *
     * @return a value among Y_ENABLEDATA_HOMENETWORK, Y_ENABLEDATA_ROAMING, Y_ENABLEDATA_NEVER and
     * Y_ENABLEDATA_NEUTRALITY corresponding to the condition for enabling IP data services (GPRS)
     *
     * On failure, throws an exception or returns Y_ENABLEDATA_INVALID.
     */
    public int getEnableData()
    {
        return _enableData;
    }

    /**
     * Changes the condition for enabling IP data services (GPRS).
     * The service can be either fully deactivated, or limited to the SIM home network,
     * or enabled for all partner networks (roaming). Caution: enabling data services
     * on roaming networks may cause prohibitive communication costs !
     *
     * When data services are disabled, SMS are the only mean of communication.
     *
     * @param newval : a value among Y_ENABLEDATA_HOMENETWORK, Y_ENABLEDATA_ROAMING, Y_ENABLEDATA_NEVER
     * and Y_ENABLEDATA_NEUTRALITY corresponding to the condition for enabling IP data services (GPRS)
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setEnableDataBg(int newval) throws YAPI_Exception
    {
        _enableData = newval;
        _ycellular.set_enableData(newval);
    }

    /**
     * Returns the Access Point Name (APN) to be used, if needed.
     * When left blank, the APN suggested by the cell operator will be used.
     *
     * @return a string corresponding to the Access Point Name (APN) to be used, if needed
     *
     * On failure, throws an exception or returns Y_APN_INVALID.
     */
    public String getApn()
    {
        return _apn;
    }

    /**
     * Returns the Access Point Name (APN) to be used, if needed.
     * When left blank, the APN suggested by the cell operator will be used.
     *
     * @param newval : a string
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setApnBg(String newval) throws YAPI_Exception
    {
        _apn = newval;
        _ycellular.set_apn(newval);
    }

    /**
     * Returns an opaque string if APN authentication parameters have been configured
     * in the device, or an empty string otherwise.
     * To configure these parameters, use set_apnAuth().
     *
     * @return a string corresponding to an opaque string if APN authentication parameters have been configured
     *         in the device, or an empty string otherwise
     *
     * On failure, throws an exception or returns Y_APNSECRET_INVALID.
     */
    public String getApnSecret()
    {
        return _apnSecret;
    }

    public void setApnSecretBg(String newval) throws YAPI_Exception
    {
        _apnSecret = newval;
        _ycellular.set_apnSecret(newval);
    }

    /**
     * Returns the automated connectivity check interval, in seconds.
     *
     * @return an integer corresponding to the automated connectivity check interval, in seconds
     *
     * On failure, throws an exception or returns Y_PINGINTERVAL_INVALID.
     */
    public int getPingInterval()
    {
        return _pingInterval;
    }

    /**
     * Changes the automated connectivity check interval, in seconds.
     *
     * @param newval : an integer corresponding to the automated connectivity check interval, in seconds
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setPingIntervalBg(int newval) throws YAPI_Exception
    {
        _pingInterval = newval;
        _ycellular.set_pingInterval(newval);
    }

    /**
     * Returns the number of bytes sent so far.
     *
     * @return an integer corresponding to the number of bytes sent so far
     *
     * On failure, throws an exception or returns Y_DATASENT_INVALID.
     */
    public int getDataSent()
    {
        return _dataSent;
    }

    /**
     * Changes the value of the outgoing data counter.
     *
     * @param newval : an integer corresponding to the value of the outgoing data counter
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setDataSentBg(int newval) throws YAPI_Exception
    {
        _dataSent = newval;
        _ycellular.set_dataSent(newval);
    }

    /**
     * Returns the number of bytes received so far.
     *
     * @return an integer corresponding to the number of bytes received so far
     *
     * On failure, throws an exception or returns Y_DATARECEIVED_INVALID.
     */
    public int getDataReceived()
    {
        return _dataReceived;
    }

    /**
     * Changes the value of the incoming data counter.
     *
     * @param newval : an integer corresponding to the value of the incoming data counter
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setDataReceivedBg(int newval) throws YAPI_Exception
    {
        _dataReceived = newval;
        _ycellular.set_dataReceived(newval);
    }

    public String getCommand()
    {
        return _command;
    }

    public void setCommandBg(String newval) throws YAPI_Exception
    {
        _command = newval;
        _ycellular.set_command(newval);
    }

    public static YCellular FindCellular(String func)
    {
        return YCellular.FindCellular(func);
    }

    public int sendPUK(String puk, String newPin) throws YAPI_Exception
    {
        return _ycellular.sendPUK(puk, newPin);
    }

    public int set_apnAuth(String username, String password) throws YAPI_Exception
    {
        return _ycellular.set_apnAuth(username, password);
    }

    public int clearDataCounters() throws YAPI_Exception
    {
        return _ycellular.clearDataCounters();
    }

    public ArrayList<String> get_availableOperators() throws YAPI_Exception
    {
        return _ycellular.get_availableOperators();
    }

    public ArrayList<YCellRecord> quickCellSurvey() throws YAPI_Exception
    {
        return _ycellular.quickCellSurvey();
    }

//--- (end of generated code: YCellular class start)
}

