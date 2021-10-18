package com.yoctopuce.yoctopucetoolbox.details_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yoctopuce.YoctoAPI.YAPI_Exception;

/**
 * Created by seb on 22.11.2016.
 */
public class DetailYoctoRS485Fragment extends DetailGenericModuleFragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return super.onCreateView(inflater, container, savedInstanceState);
        // todo: implement stub
    }

    @Override
    protected void reloadDataInBG(boolean firstReload) throws YAPI_Exception
    {
        super.reloadDataInBG(firstReload);
        // todo: implement stub
    }

    @Override
    protected void setupUI(View rootView)
    {
        super.setupUI(rootView);
        // todo: implement stub
    }

    @Override
    protected void updateUI(boolean firstUpdate)
    {
        super.updateUI(firstUpdate);
        // todo: implement stub
    }
}
