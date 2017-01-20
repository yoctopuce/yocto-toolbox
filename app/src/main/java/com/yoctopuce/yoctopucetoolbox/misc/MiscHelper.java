package com.yoctopuce.yoctopucetoolbox.misc;

import android.util.Log;
import android.widget.TextView;

import com.yoctopuce.yoctopucetoolbox.functions.GenericSensor;
import com.yoctopuce.yoctopucetoolbox.functions.HubPort;
import com.yoctopuce.yoctopucetoolbox.functions.Temperature;
import com.yoctopuce.yoctopucetoolbox.hub.Hub;
import com.yoctopuce.yoctopucetoolbox.hub.HubStorage;
import com.yoctopuce.yoctopucetoolbox.widget.CustomCompoundButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MiscHelper
{
    public static String UnixTimeToHumanReadable(long unixtime)
    {
        DateFormat ft = SimpleDateFormat.getDateTimeInstance();
        return ft.format(new Date(unixtime));
    }

    public static double applyResolution(double value, double resolution)
    {
        //todo: round the value to the resolution
        //var l = Math.round(Math.log(r) / Math.LN10);
        //var r = Math.pow(10, ~~l);
        return value; //Math.round(r * Math.round(v / r) * 10000) / 10000;
    }

    public static void updateUI_temperature(Temperature tempSensor, TextView currentTextView, TextView maxTextView, TextView minTextView)
    {
        Locale locale = Locale.US;
        String unit = tempSensor.getUnit();
        unit = unit.replace('\'', '°');
        double res = tempSensor.getResolution();
        currentTextView.setText(String.format(locale, "%s %s", Double.toString(applyResolution(tempSensor.getCurrentValue(), res)), unit));
        maxTextView.setText(String.format(locale, "%s %s", Double.toString(applyResolution(tempSensor.getHighestValue(), res)), unit));
        minTextView.setText(String.format(locale, "%s %s", Double.toString(applyResolution(tempSensor.getLowestValue(), res)), unit));
    }

    public static void updateUI_genericSensor(TextView minView, GenericSensor sensor, TextView valueView, TextView signalView, TextView maxView)
    {
        minView.setText(String.format(Locale.US, "%s %s", Double.toString(sensor.getLowestValue()), sensor.getUnit()));
        String signal = String.format(Locale.US, "%s %s", Double.toString(sensor.getSignalValue()), sensor.getSignalUnit());
        String value;
        if (sensor.getSignalRange().equals(sensor.getValueRange()) && sensor.getUnit().equals(sensor.getSignalUnit())) {
            value = signal;
            signal = "";
        } else {
            double v = sensor.getCurrentValue();
            if (v < -29998)
                value = "---";
            else if (v > 29998)
                value = "+++";
            else {
                value = String.format(Locale.US, "%s %s", Double.toString(v), sensor.getUnit());
            }
            if (signal.equals(value)) {
                signal = "";
            } else {
                signal = "(" + signal + ")";
            }
        }
        valueView.setText(value);
        signalView.setText(signal);
        maxView.setText(String.format(Locale.US, "%s %s", Double.toString(sensor.getHighestValue()), sensor.getUnit()));
    }

    public static void updateUI_HubPort(HubPort hubPort, TextView portTextView, CustomCompoundButton portSwitch)
    {
        String advertisedValue = hubPort.getAdvertisedValue();
        if (advertisedValue.equals("RUN") || advertisedValue.equals("PROG")) {
            advertisedValue += " : " + hubPort.getLogicalName();
        }
        portTextView.setText(advertisedValue);
        portSwitch.setCheckedNoNotify(!advertisedValue.equals("OFF"));
    }


    public static JSONObject requestJson(String url_str)
    {
        final URL url;
        try {
            url = new URL(url_str);
        } catch (MalformedURLException e) {
            return null;
        }
        HttpURLConnection connection;
        try {
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                final String responseMessage = connection.getResponseMessage();
                Log.d("tst", responseMessage);
                return null;
            }
            int bytesRead;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            byte[] bytes = out.toByteArray();
            final String json;
            json = new String(bytes);
            return new JSONObject(json);
        } catch (IOException e) {
            return null;
        } catch (JSONException e) {
            return null;
        } finally {
            connection.disconnect();
        }
    }


    public static boolean updateHubFromJson(JSONObject jsonObject, Hub hub, HubStorage hubStorage)
    {
        boolean needRefresh = false;
        try {
            boolean needSave = false;
            JSONObject jsonModule = jsonObject.getJSONObject("module");
            boolean beacon = jsonModule.optInt("beacon") == 1;
            String serial = jsonModule.optString("serialNumber");
            if (!hub.getSerial().equals(serial)) {
                hub.setSerial(serial);
                needSave = true;
            }

            String logicalName = jsonModule.optString("logicalName");
            if (!hub.getName().equals(logicalName)) {
                hub.setName(logicalName);
                needSave = true;
            }

            if (beacon != hub.isBeacon()) {
                hub.setBeacon(beacon);
                needRefresh = true;
            }
            if (needSave) {
                hubStorage.updateHub(hub);
            }
            if (!hub.isOnline()) {
                hub.setOnline(true);
                needRefresh = true;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            if (!hub.isOnline()) {
                needRefresh = true;
                hub.setOnline(false);
            }
        }
        return needRefresh;
    }


}
