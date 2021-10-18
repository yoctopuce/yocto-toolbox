package com.yoctopuce.yoctopucetoolbox.misc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.webkit.WebView;

import com.yoctopuce.YoctoAPI.YAPI;
import com.yoctopuce.yoctopucetoolbox.R;


public class AboutDialog extends DialogFragment
{

    private static final String VERSION_UNAVAILABLE = "N/A";

    public static void showAbout(Activity activity)
    {
        FragmentManager fm = activity.getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog_about");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        new AboutDialog().show(ft, "dialog_about");
    }

    private static final String[][] USED_LIBRARIES = new String[][]{
            new String[]{"ColorPickerPreference", "https://github.com/skydoves/ColorPickerPreference"}
    };

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // Get app version
        Activity activity = getActivity();
        Resources resources = activity.getResources();
        PackageManager pm = activity.getPackageManager();
        String packageName = activity.getPackageName();
        String versionName;
        try {
            PackageInfo info = pm.getPackageInfo(packageName, 0);
            versionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            versionName = VERSION_UNAVAILABLE;
        }
        String app_name = resources.getString(R.string.app_name);
        WebView wv = new WebView(activity);
        StringBuilder html = new StringBuilder()
                .append("<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" />")
                .append(getString(R.string.about_text, app_name, versionName, YAPI.GetAPIVersion()))
                .append("<hr/>");

        StringBuilder libs = new StringBuilder().append("<ul>");
        for (String[] library : USED_LIBRARIES) {
            libs.append("<li><a href=\"").append(library[1]).append("\">").append(library[0]).append("</a></li>");
        }
        libs.append("</ul>");

        html.append(getString(R.string.app_libraries))
                .append(libs.toString())
                .append("</p>");


        final String data = html.toString();
        wv.loadDataWithBaseURL("file:///android_res/drawable/", data, "text/html", "utf-8", null);

        return new AlertDialog.Builder(activity)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(R.string.app_name)
                .setView(wv)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int whichButton)
                            {
                                dialog.dismiss();
                            }
                        }
                )
                .create();
    }
}
