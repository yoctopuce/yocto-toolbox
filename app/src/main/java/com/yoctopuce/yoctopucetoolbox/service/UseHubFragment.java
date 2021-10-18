package com.yoctopuce.yoctopucetoolbox.service;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.yoctopuce.YoctoAPI.YAPI_Exception;

public abstract class UseHubFragment extends Fragment implements UseHubAPI, BGHandler.BgErrorHandler {
    private UseHubAPI _useHubActivity;
    protected FragementNavigactionCallbacks _callbacks;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            _useHubActivity = (UseHubAPI) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " does not inherit from UseHubActivity");
        }

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            _callbacks = (FragementNavigactionCallbacks) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement DetailFragmentCallbackInterface");
        }
    }

    @Override
    public void postBg(BgRunnable bgRunnable) {
        _useHubActivity.postBg(bgRunnable);
    }

    @Override
    public void postDelayedBg(BgRunnable bgRunnable, long delayMs) {
        _useHubActivity.postDelayedBg(bgRunnable, delayMs);
    }

    @Override
    public void postUI(Runnable fgRunnable) {
        _useHubActivity.postUI(fgRunnable);

    }

    @Override
    public void postDelayedUI(Runnable fgRunnable, long delayMs) {
        _useHubActivity.postDelayedUI(fgRunnable, delayMs);

    }

    @Override
    public void onBGError(YAPI_Exception e) {
        _useHubActivity.onBGError(e);
    }

}
