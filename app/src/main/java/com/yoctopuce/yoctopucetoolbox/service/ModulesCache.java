package com.yoctopuce.yoctopucetoolbox.service;

import com.yoctopuce.yoctopucetoolbox.functions.Module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Thread safe cache of Module Object
 */
public class ModulesCache {

    private static ModulesCache __instance = null;
    private final ArrayList<CacheObserver> _observers = new ArrayList<>(2);

    public synchronized static ModulesCache getInstance() {
        if (__instance == null) {
            __instance = new ModulesCache();
        }
        return __instance;
    }

    private static final List<Module> _items = new ArrayList<>();
    private static final Map<String, Module> _item_map = new HashMap<>();

    private ModulesCache() {
    }

    public void resetCache() {
        synchronized (this) {
            _item_map.clear();
            _items.clear();
        }
        notifyBigChange();
    }

    synchronized void add(String serial, Module module) {
        synchronized (this) {
            _items.add(module);
            _item_map.put(serial, module);
        }
        notifyBigChange();
    }

    void remove(String serial) {
        synchronized (this) {
            Module module = _item_map.get(serial);
            if (module != null) {
                _items.remove(module);
                _item_map.remove(serial);
            }
        }
        notifyBigChange();
    }


    public synchronized int getItemCount() {
        return _items.size();
    }

    public synchronized Module get(int position) {
        return _items.get(position);
    }

    public synchronized Module getFromSerial(String serial) {
        return _item_map.get(serial);
    }


    private void notifyBigChange() {
        synchronized (_observers) {
            for (CacheObserver observer : _observers) {
                observer.onReload();
            }
        }
    }

    private void notifyChange(String serial) {
        synchronized (_observers) {
            for (CacheObserver observer : _observers) {
                observer.onChange(serial);
            }
        }
    }


    public void registerCacheObserver(CacheObserver observer) {
        if (observer == null) {
            throw new IllegalArgumentException("The observer is null.");
        }
        synchronized (_observers) {
            if (_observers.contains(observer)) {
                throw new IllegalStateException("Observer " + observer + " is already registered.");
            }
            _observers.add(observer);
        }
    }

    public void unregisterCacheObserver(CacheObserver observer) {
        if (observer == null) {
            throw new IllegalArgumentException("The observer is null.");
        }
        synchronized (_observers) {
            if (!_observers.contains(observer)) {
                throw new IllegalStateException("Observer " + observer + " is not registered.");
            }
            _observers.remove(observer);
        }

    }

}
