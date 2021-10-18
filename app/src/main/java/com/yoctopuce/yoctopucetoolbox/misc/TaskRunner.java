package com.yoctopuce.yoctopucetoolbox.misc;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TaskRunner
{
    private final Executor executor = Executors.newSingleThreadExecutor(); // change according to your requirements
    private final Handler handler = new Handler(Looper.getMainLooper());

    public interface Callback<R>
    {
        void onComplete(R result);
    }

    public <R> void executeAsync(Callable<R> toExecuteBG, Callback<R> onResult)
    {
        executor.execute(() -> {
            final R result;
            try {
                result = toExecuteBG.call();
                handler.post(() -> {
                    onResult.onComplete(result);
                });
            } catch (Exception e) {
                //fixme proagate error
                e.printStackTrace();
            }
        });
    }
}