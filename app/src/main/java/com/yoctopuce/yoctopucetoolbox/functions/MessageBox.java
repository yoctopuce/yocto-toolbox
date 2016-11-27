/*********************************************************************
 *
 * $Id: MessageBox.java 26014 2016-11-24 13:52:08Z seb $
 *
 * Implements MessageBox wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YMessageBox;
import com.yoctopuce.YoctoAPI.YSms;

import java.util.ArrayList;

//--- (generated code: YMessageBox class start)
/**
 * YMessageBox Class: MessageBox function interface
 *
 * YMessageBox functions provides SMS sending and receiving capability to
 * GSM-enabled Yoctopuce devices.
 */
 @SuppressWarnings("UnusedDeclaration")
public class MessageBox extends Function
{
// valueCallbackMessageBox
    protected int _slotsInUse =  YMessageBox.SLOTSINUSE_INVALID;
    protected int _slotsCount =  YMessageBox.SLOTSCOUNT_INVALID;
    protected String _slotsBitmap =  YMessageBox.SLOTSBITMAP_INVALID;
    protected int _pduSent =  YMessageBox.PDUSENT_INVALID;
    protected int _pduReceived =  YMessageBox.PDURECEIVED_INVALID;
    protected String _command =  YMessageBox.COMMAND_INVALID;
    protected int _nextMsgRef = 0;
    protected String _prevBitmapStr;
    protected ArrayList<YSms> _pdus = new ArrayList<YSms>();
    protected ArrayList<YSms> _messages = new ArrayList<YSms>();
    protected boolean _gsm2unicodeReady;
    protected ArrayList<Integer> _gsm2unicode = new ArrayList<Integer>();
    protected byte[] _iso2gsm;
    protected YMessageBox _ymessagebox;

    public MessageBox(YMessageBox yfunc)
    {
       super(yfunc);
       _ymessagebox = yfunc;
    }

    public MessageBox(String hwid)
    {
       super(hwid);
       _ymessagebox = YMessageBox.FindMessageBox(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _slotsInUse = _ymessagebox.get_slotsInUse();
        _slotsCount = _ymessagebox.get_slotsCount();
        _slotsBitmap = _ymessagebox.get_slotsBitmap();
        _pduSent = _ymessagebox.get_pduSent();
        _pduReceived = _ymessagebox.get_pduReceived();
        _command = _ymessagebox.get_command();
    }
    /**
     * Returns the number of message storage slots currently in use.
     *
     * @return an integer corresponding to the number of message storage slots currently in use
     *
     * On failure, throws an exception or returns Y_SLOTSINUSE_INVALID.
     */
    public int getSlotsInUse()
    {
        return _slotsInUse;
    }

    /**
     * Returns the total number of message storage slots on the SIM card.
     *
     * @return an integer corresponding to the total number of message storage slots on the SIM card
     *
     * On failure, throws an exception or returns Y_SLOTSCOUNT_INVALID.
     */
    public int getSlotsCount()
    {
        return _slotsCount;
    }

    public String getSlotsBitmap()
    {
        return _slotsBitmap;
    }

    /**
     * Returns the number of SMS units sent so far.
     *
     * @return an integer corresponding to the number of SMS units sent so far
     *
     * On failure, throws an exception or returns Y_PDUSENT_INVALID.
     */
    public int getPduSent()
    {
        return _pduSent;
    }

    /**
     * Changes the value of the outgoing SMS units counter.
     *
     * @param newval : an integer corresponding to the value of the outgoing SMS units counter
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setPduSentBg(int newval) throws YAPI_Exception
    {
        _pduSent = newval;
        _ymessagebox.set_pduSent(newval);
    }

    /**
     * Returns the number of SMS units received so far.
     *
     * @return an integer corresponding to the number of SMS units received so far
     *
     * On failure, throws an exception or returns Y_PDURECEIVED_INVALID.
     */
    public int getPduReceived()
    {
        return _pduReceived;
    }

    /**
     * Changes the value of the incoming SMS units counter.
     *
     * @param newval : an integer corresponding to the value of the incoming SMS units counter
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setPduReceivedBg(int newval) throws YAPI_Exception
    {
        _pduReceived = newval;
        _ymessagebox.set_pduReceived(newval);
    }

    public String getCommand()
    {
        return _command;
    }

    public void setCommandBg(String newval) throws YAPI_Exception
    {
        _command = newval;
        _ymessagebox.set_command(newval);
    }

//--- (end of generated code: YMessageBox class start)
}

