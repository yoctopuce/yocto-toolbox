/*********************************************************************
 *
 * $Id: WeighScale.java 46698 2021-10-01 06:31:31Z web $
 *
 * Implements WeighScale wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YWeighScale;
import java.util.ArrayList;

//--- (YWeighScale class start)
/**
 * YWeighScale Class: WeighScale function interface
 *
 * The YWeighScale class provides a weight measurement from a ratiometric load cell
 * sensor. It can be used to control the bridge excitation parameters, in order to avoid
 * measure shifts caused by temperature variation in the electronics, and can also
 * automatically apply an additional correction factor based on temperature to
 * compensate for offsets in the load cell itself.
 */
 @SuppressWarnings("UnusedDeclaration")
public class WeighScale extends Sensor
{
// valueCallbackWeighScale
// timedReportCallbackWeighScale
    protected int _excitation =  YWeighScale.EXCITATION_INVALID;
    protected double _tempAvgAdaptRatio =  YWeighScale.TEMPAVGADAPTRATIO_INVALID;
    protected double _tempChgAdaptRatio =  YWeighScale.TEMPCHGADAPTRATIO_INVALID;
    protected double _compTempAvg =  YWeighScale.COMPTEMPAVG_INVALID;
    protected double _compTempChg =  YWeighScale.COMPTEMPCHG_INVALID;
    protected double _compensation =  YWeighScale.COMPENSATION_INVALID;
    protected double _zeroTracking =  YWeighScale.ZEROTRACKING_INVALID;
    protected String _command =  YWeighScale.COMMAND_INVALID;
    protected YWeighScale _yweighscale;

    public WeighScale(YWeighScale yfunc)
    {
       super(yfunc);
       _yweighscale = yfunc;
    }

    public WeighScale(String hwid)
    {
       super(hwid);
       _yweighscale = YWeighScale.FindWeighScale(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _excitation = _yweighscale.get_excitation();
        _tempAvgAdaptRatio = _yweighscale.get_tempAvgAdaptRatio();
        _tempChgAdaptRatio = _yweighscale.get_tempChgAdaptRatio();
        _compTempAvg = _yweighscale.get_compTempAvg();
        _compTempChg = _yweighscale.get_compTempChg();
        _compensation = _yweighscale.get_compensation();
        _zeroTracking = _yweighscale.get_zeroTracking();
        _command = _yweighscale.get_command();
    }
    /**
     * Changes the measuring unit for the weight.
     * Remember to call the saveToFlash() method of the module if the
     * modification must be kept.
     *
     * @param newval : a string corresponding to the measuring unit for the weight
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setUnitBg(String newval) throws YAPI_Exception
    {
        _unit = newval;
        _yweighscale.set_unit(newval);
    }

    /**
     * Returns the current load cell bridge excitation method.
     *
     * @return a value among Y_EXCITATION_OFF, Y_EXCITATION_DC and Y_EXCITATION_AC corresponding to the
     * current load cell bridge excitation method
     *
     * On failure, throws an exception or returns Y_EXCITATION_INVALID.
     */
    public int getExcitation()
    {
        return _excitation;
    }

    /**
     * Changes the current load cell bridge excitation method.
     *
     * @param newval : a value among Y_EXCITATION_OFF, Y_EXCITATION_DC and Y_EXCITATION_AC corresponding
     * to the current load cell bridge excitation method
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setExcitationBg(int newval) throws YAPI_Exception
    {
        _excitation = newval;
        _yweighscale.set_excitation(newval);
    }

    /**
     * Changes the averaged temperature update rate, in per mille.
     * The purpose of this adaptation ratio is to model the thermal inertia of the load cell.
     * The averaged temperature is updated every 10 seconds, by applying this adaptation rate
     * to the difference between the measures ambiant temperature and the current compensation
     * temperature. The standard rate is 0.2 per mille, and the maximal rate is 65 per mille.
     *
     * @param newval : a floating point number corresponding to the averaged temperature update rate, in per mille
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setTempAvgAdaptRatioBg(double newval) throws YAPI_Exception
    {
        _tempAvgAdaptRatio = newval;
        _yweighscale.set_tempAvgAdaptRatio(newval);
    }

    /**
     * Returns the averaged temperature update rate, in per mille.
     * The purpose of this adaptation ratio is to model the thermal inertia of the load cell.
     * The averaged temperature is updated every 10 seconds, by applying this adaptation rate
     * to the difference between the measures ambiant temperature and the current compensation
     * temperature. The standard rate is 0.2 per mille, and the maximal rate is 65 per mille.
     *
     * @return a floating point number corresponding to the averaged temperature update rate, in per mille
     *
     * On failure, throws an exception or returns Y_TEMPAVGADAPTRATIO_INVALID.
     */
    public double getTempAvgAdaptRatio()
    {
        return _tempAvgAdaptRatio;
    }

    /**
     * Changes the temperature change update rate, in per mille.
     * The temperature change is updated every 10 seconds, by applying this adaptation rate
     * to the difference between the measures ambiant temperature and the current temperature used for
     * change compensation. The standard rate is 0.6 per mille, and the maximal rate is 65 pour mille.
     *
     * @param newval : a floating point number corresponding to the temperature change update rate, in per mille
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setTempChgAdaptRatioBg(double newval) throws YAPI_Exception
    {
        _tempChgAdaptRatio = newval;
        _yweighscale.set_tempChgAdaptRatio(newval);
    }

    /**
     * Returns the temperature change update rate, in per mille.
     * The temperature change is updated every 10 seconds, by applying this adaptation rate
     * to the difference between the measures ambiant temperature and the current temperature used for
     * change compensation. The standard rate is 0.6 per mille, and the maximal rate is 65 pour mille.
     *
     * @return a floating point number corresponding to the temperature change update rate, in per mille
     *
     * On failure, throws an exception or returns Y_TEMPCHGADAPTRATIO_INVALID.
     */
    public double getTempChgAdaptRatio()
    {
        return _tempChgAdaptRatio;
    }

    /**
     * Returns the current averaged temperature, used for thermal compensation.
     *
     * @return a floating point number corresponding to the current averaged temperature, used for thermal compensation
     *
     * On failure, throws an exception or returns Y_COMPTEMPAVG_INVALID.
     */
    public double getCompTempAvg()
    {
        return _compTempAvg;
    }

    /**
     * Returns the current temperature variation, used for thermal compensation.
     *
     * @return a floating point number corresponding to the current temperature variation, used for
     * thermal compensation
     *
     * On failure, throws an exception or returns Y_COMPTEMPCHG_INVALID.
     */
    public double getCompTempChg()
    {
        return _compTempChg;
    }

    /**
     * Returns the current current thermal compensation value.
     *
     * @return a floating point number corresponding to the current current thermal compensation value
     *
     * On failure, throws an exception or returns Y_COMPENSATION_INVALID.
     */
    public double getCompensation()
    {
        return _compensation;
    }

    /**
     * Changes the zero tracking threshold value. When this threshold is larger than
     * zero, any measure under the threshold will automatically be ignored and the
     * zero compensation will be updated.
     *
     * @param newval : a floating point number corresponding to the zero tracking threshold value
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setZeroTrackingBg(double newval) throws YAPI_Exception
    {
        _zeroTracking = newval;
        _yweighscale.set_zeroTracking(newval);
    }

    /**
     * Returns the zero tracking threshold value. When this threshold is larger than
     * zero, any measure under the threshold will automatically be ignored and the
     * zero compensation will be updated.
     *
     * @return a floating point number corresponding to the zero tracking threshold value
     *
     * On failure, throws an exception or returns Y_ZEROTRACKING_INVALID.
     */
    public double getZeroTracking()
    {
        return _zeroTracking;
    }

    public String getCommand()
    {
        return _command;
    }

    public void setCommandBg(String newval) throws YAPI_Exception
    {
        _command = newval;
        _yweighscale.set_command(newval);
    }

    public static YWeighScale FindWeighScale(String func)
    {
        return YWeighScale.FindWeighScale(func);
    }

    public int tare() throws YAPI_Exception
    {
        return _yweighscale.tare();
    }

    public int setupSpan(double currWeight, double maxWeight) throws YAPI_Exception
    {
        return _yweighscale.setupSpan(currWeight, maxWeight);
    }

    public int setCompensationTable(int tableIndex, ArrayList<Double> tempValues, ArrayList<Double> compValues) throws YAPI_Exception
    {
        return _yweighscale.setCompensationTable(tableIndex, tempValues, compValues);
    }

    public int loadCompensationTable(int tableIndex, ArrayList<Double> tempValues, ArrayList<Double> compValues) throws YAPI_Exception
    {
        return _yweighscale.loadCompensationTable(tableIndex, tempValues, compValues);
    }

    public int set_offsetAvgCompensationTable(ArrayList<Double> tempValues, ArrayList<Double> compValues) throws YAPI_Exception
    {
        return _yweighscale.set_offsetAvgCompensationTable(tempValues, compValues);
    }

    public int loadOffsetAvgCompensationTable(ArrayList<Double> tempValues, ArrayList<Double> compValues) throws YAPI_Exception
    {
        return _yweighscale.loadOffsetAvgCompensationTable(tempValues, compValues);
    }

    public int set_offsetChgCompensationTable(ArrayList<Double> tempValues, ArrayList<Double> compValues) throws YAPI_Exception
    {
        return _yweighscale.set_offsetChgCompensationTable(tempValues, compValues);
    }

    public int loadOffsetChgCompensationTable(ArrayList<Double> tempValues, ArrayList<Double> compValues) throws YAPI_Exception
    {
        return _yweighscale.loadOffsetChgCompensationTable(tempValues, compValues);
    }

    public int set_spanAvgCompensationTable(ArrayList<Double> tempValues, ArrayList<Double> compValues) throws YAPI_Exception
    {
        return _yweighscale.set_spanAvgCompensationTable(tempValues, compValues);
    }

    public int loadSpanAvgCompensationTable(ArrayList<Double> tempValues, ArrayList<Double> compValues) throws YAPI_Exception
    {
        return _yweighscale.loadSpanAvgCompensationTable(tempValues, compValues);
    }

    public int set_spanChgCompensationTable(ArrayList<Double> tempValues, ArrayList<Double> compValues) throws YAPI_Exception
    {
        return _yweighscale.set_spanChgCompensationTable(tempValues, compValues);
    }

    public int loadSpanChgCompensationTable(ArrayList<Double> tempValues, ArrayList<Double> compValues) throws YAPI_Exception
    {
        return _yweighscale.loadSpanChgCompensationTable(tempValues, compValues);
    }

//--- (end of YWeighScale class start)
}

