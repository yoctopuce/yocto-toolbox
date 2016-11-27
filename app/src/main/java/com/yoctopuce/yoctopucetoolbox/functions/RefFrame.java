/*********************************************************************
 *
 * $Id: RefFrame.java 26014 2016-11-24 13:52:08Z seb $
 *
 * Implements RefFrame wrapper for Android toolbox
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
import com.yoctopuce.YoctoAPI.YRefFrame;
import java.util.ArrayList;

//--- (YRefFrame class start)
/**
 * YRefFrame Class: Reference frame configuration
 *
 * This class is used to setup the base orientation of the Yocto-3D, so that
 * the orientation functions, relative to the earth surface plane, use
 * the proper reference frame. The class also implements a tridimensional
 * sensor calibration process, which can compensate for local variations
 * of standard gravity and improve the precision of the tilt sensors.
 */
 @SuppressWarnings("UnusedDeclaration")
public class RefFrame extends Function
{
// valueCallbackRefFrame
    protected int _mountPos =  YRefFrame.MOUNTPOS_INVALID;
    protected double _bearing =  YRefFrame.BEARING_INVALID;
    protected String _calibrationParam =  YRefFrame.CALIBRATIONPARAM_INVALID;
    protected boolean _calibV2;
    protected int _calibStage = 0;
    protected String _calibStageHint;
    protected int _calibStageProgress = 0;
    protected int _calibProgress = 0;
    protected String _calibLogMsg;
    protected String _calibSavedParams;
    protected int _calibCount = 0;
    protected int _calibInternalPos = 0;
    protected int _calibPrevTick = 0;
    protected ArrayList<Integer> _calibOrient = new ArrayList<Integer>();
    protected ArrayList<Double> _calibDataAccX = new ArrayList<Double>();
    protected ArrayList<Double> _calibDataAccY = new ArrayList<Double>();
    protected ArrayList<Double> _calibDataAccZ = new ArrayList<Double>();
    protected ArrayList<Double> _calibDataAcc = new ArrayList<Double>();
    protected double _calibAccXOfs = 0;
    protected double _calibAccYOfs = 0;
    protected double _calibAccZOfs = 0;
    protected double _calibAccXScale = 0;
    protected double _calibAccYScale = 0;
    protected double _calibAccZScale = 0;
    protected YRefFrame _yrefframe;

    public RefFrame(YRefFrame yfunc)
    {
       super(yfunc);
       _yrefframe = yfunc;
    }

    public RefFrame(String hwid)
    {
       super(hwid);
       _yrefframe = YRefFrame.FindRefFrame(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _mountPos = _yrefframe.get_mountPos();
        _bearing = _yrefframe.get_bearing();
        _calibrationParam = _yrefframe.get_calibrationParam();
    }
    public int getMountPos()
    {
        return _mountPos;
    }

    public void setMountPosBg(int newval) throws YAPI_Exception
    {
        _mountPos = newval;
        _yrefframe.set_mountPos(newval);
    }

    /**
     * Changes the reference bearing used by the compass. The relative bearing
     * indicated by the compass is the difference between the measured magnetic
     * heading and the reference bearing indicated here.
     *
     * For instance, if you setup as reference bearing the value of the earth
     * magnetic declination, the compass will provide the orientation relative
     * to the geographic North.
     *
     * Similarly, when the sensor is not mounted along the standard directions
     * because it has an additional yaw angle, you can set this angle in the reference
     * bearing so that the compass provides the expected natural direction.
     *
     * Remember to call the saveToFlash()
     * method of the module if the modification must be kept.
     *
     * @param newval : a floating point number corresponding to the reference bearing used by the compass
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setBearingBg(double newval) throws YAPI_Exception
    {
        _bearing = newval;
        _yrefframe.set_bearing(newval);
    }

    /**
     * Returns the reference bearing used by the compass. The relative bearing
     * indicated by the compass is the difference between the measured magnetic
     * heading and the reference bearing indicated here.
     *
     * @return a floating point number corresponding to the reference bearing used by the compass
     *
     * On failure, throws an exception or returns Y_BEARING_INVALID.
     */
    public double getBearing()
    {
        return _bearing;
    }

    public String getCalibrationParam()
    {
        return _calibrationParam;
    }

    public void setCalibrationParamBg(String newval) throws YAPI_Exception
    {
        _calibrationParam = newval;
        _yrefframe.set_calibrationParam(newval);
    }

//--- (end of YRefFrame class start)
}

