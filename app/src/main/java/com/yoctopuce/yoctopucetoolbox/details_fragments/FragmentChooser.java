package com.yoctopuce.yoctopucetoolbox.details_fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;


public class FragmentChooser
{

    //FIXME: use YAPI.YOCTO_BASE_SERIAL_LEN with fix has been released
    static final int YOCTO_BASE_SERIAL_LEN = 8;

    public static Fragment GetFragment(String serial)
    {
        String base_serial = serial.substring(0, YOCTO_BASE_SERIAL_LEN);
        DetailGenericModuleFragment fragment = null;
        Bundle args = new Bundle();
        args.putString(DetailGenericModuleFragment.ARG_SERIAL, serial);
        switch (base_serial) {
            case "VIRTHUB0": //VirtualHub
                fragment = new DetailGenericModuleFragment();
                break;
            case "YGNSSMK1": //Yocto-GPS
                fragment = new DetailYoctoGPSFragment();
                break;
            case "RS232MK1": //Yocto-RS232
                fragment = new DetailYoctoRS232Fragment();
                break;
            case "RS485MK1": //Yocto-RS485
                fragment = new DetailYoctoRS485Fragment();
                break;
            case "YAMPMK01": //Yocto-Amp
            case "YAMPMK02": //Yocto-Amp-V2
                fragment = new DetailYoctoAmpFragment();
                break;
            case "YBUZZER2": //Yocto-Buzzer
                fragment = new DetailYoctoBuzzerFragment();
                break;
            case "YRGBLED2": //Yocto-Color-V2
                fragment = new DetailYoctoColorV2Fragment();
                break;
            case "RXMVOLT1": //Yocto-milliVolt-Rx
            case "RXMVOLT2": //Yocto-milliVolt-Rx-BNC
                fragment = new DetailYoctomilliVoltRxFragment();
                break;
            case "MINIIIO0": //Yocto-IO
                fragment = new DetailYoctoIOFragment();
                break;
            case "YSERIAL1": //Yocto-Serial
                fragment = new DetailYoctoSerialFragment();
                break;
            case "YSPIMK01": //Yocto-SPI
                fragment = new DetailYoctoSPIFragment();
                break;
            case "YSTEPMK1": //Yocto-StepperMotor
                fragment = new DetailYoctoStepperMotorFragment();
                break;
            case "VOLTAGE2": //Yocto-Volt-V2
            case "VOLTAGE1": //Yocto-Volt
                fragment = new DetailYoctoVoltFragment();
                break;
            case "YWATTMK1": //Yocto-Watt
            case "YWATTMK2": //Yocto-Watt-V2
                fragment = new DetailYoctoWattFragment();
                break;
            case "RX010V01": //Yocto-0-10V-Rx
            case "RX420MA1": //Yocto-4-20mA-Rx
                fragment = new DetailYocto010VRxFragment();
                break;
            case "Y3DMK001": //Yocto-3D
            case "Y3DMK002": //Yocto-3D-V2
                fragment = new DetailYocto3DFragment();
                break;
            case "TX420MA1": //Yocto-4-20mA-Tx
                fragment = new DetailYocto420mATxFragment();
                break;
            case "YCO2MK01": //Yocto-CO2
                fragment = new DetailYoctoCO2Fragment();
                break;
            case "MAXIIO01": //Yocto-Maxi-IO
                fragment = new DetailYoctoMaxiIOFragment();
                break;
            case "YPWMTX01": //Yocto-PWM-Tx
                fragment = new DetailYoctoPWMTxFragment();
                break;
            case "YPWMRX01": //Yocto-PWM-Rx
                fragment = new DetailYoctoPWMRxFragment();
                break;
            case "WDOGDC01": //Yocto-WatchdogDC
                fragment = new DetailYoctoWatchdogDCFragment();
                break;
            case "YALTIMK1": //Yocto-Altimeter
                fragment = new DetailYoctoAltimeterFragment();
                break;
            case "YBUTTON1": //Yocto-Knob
                fragment = new DetailYoctoKnobFragment();
                break;
            case "YD128X32": //Yocto-Display
                fragment = new DetailYoctoDisplayFragment();
                break;
            case "YD128X64": //Yocto-MaxiDisplay
            case "YD128G64": //Yocto-MaxiDisplay-G
                fragment = new DetailYoctoMaxiDisplayFragment();
                break;
            case "YD096X16": //Yocto-MiniDisplay
                fragment = new DetailYoctoMiniDisplayFragment();
                break;
            case "YHUBSHL1": //YoctoHub-Shield
                fragment = new DetailYoctoHubShieldFragment();
                break;
            case "YHUBETH1": //YoctoHub-Ethernet
                fragment = new DetailYoctoHubEthernetFragment();
                break;
            case "YHUBGSM1": //YoctoHub-GSM-2G
            case "YHUBGSM3": //YoctoHub-GSM-3G-EU
            case "YHUBGSM4": //YoctoHub-GSM-3G-NA
                fragment = new DetailYoctoHubGSMFragment();
                break;
            case "YHUBWLN1": //YoctoHub-Wireless
            case "YHUBWLN3": //YoctoHub-Wireless-g
            case "YHUBWLN2": //YoctoHub-Wireless-SR
                fragment = new DetailYoctoHubWirelessFragment();
                break;
            case "YRGBLED1": //Yocto-Color
                fragment = new DetailYoctoColorFragment();
                break;
            case "YRGBHI01": //Yocto-PowerColor
                fragment = new DetailYoctoPowerColorFragment();
                break;
            case "LIGHTMK1": //Yocto-Light
            case "LIGHTMK2": //Yocto-Light-V2
            case "LIGHTMK3": //Yocto-Light-V3
                fragment = new DetailYoctoLightFragment();
                break;
            case "METEOMK1": //Yocto-Meteo
                fragment = new DetailYoctoMeteoFragment();
                break;
            case "MOTORCTL": //Yocto-Motor-DC
                fragment = new DetailYoctoMotorDCFragment();
                break;
            case "YCTOPOC1": //Yocto-Demo
                fragment = new DetailYoctoDemoFragment();
                break;
            case "YMXCOUPL": //Yocto-MaxiCoupler
            case "RELAYLO1": //Yocto-Relay
            case "RELAYHI1": //Yocto-PowerRelay
            case "YLTCHRL1": //Yocto-LatchedRelay
            case "MXPWRRLY": //Yocto-MaxiPowerRelay
            case "HI8PWER1": //Yocto-MaxiRelay
                fragment = new DetailYoctoRelayFragment();
                break;
            case "SERVORC1": //Yocto-Servo
                fragment = new DetailYoctoServoFragment();
                break;
            case "TMPSENS1": //Yocto-Temperature
            case "PT100MK1": //Yocto-PT100
            case "PT100MK2": //Yocto-PT100-V2
                fragment = new DetailYoctoTemperatureFragment();
                break;
            case "THRMCPL1": //Yocto-Thermocouple
            case "THRMSTR2": //Yocto-MaxiThermistor
            case "THRMSTR1": //Yocto-Thermistor-C
                fragment = new DetailYoctoThermocoupleFragment();
                break;
            case "YVOCMK01": //Yocto-VOC
                fragment = new DetailYoctoVOCFragment();
                break;
            default:
                fragment = new DetailGenericModuleFragment();
        }
        fragment.setArguments(args);
        return fragment;

    }
}
