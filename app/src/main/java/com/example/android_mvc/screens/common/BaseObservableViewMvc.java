package com.example.android_mvc.screens.common;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/*

This class provides observable capabilities to any view mvc
 */
public abstract class BaseObservableViewMvc<ListenerType> extends BaseViewMvc
        implements ObservableViewMvc<ListenerType> {

    private Set<ListenerType> mListeners = new HashSet<>();

    public void registerListener(ListenerType listener) {
        mListeners.add(listener);
    }

    public void unregisterListener(ListenerType listener) {
        mListeners.remove(listener);
    }

    protected Set<ListenerType> getListeners() {
        return Collections.unmodifiableSet(mListeners);
    }
}
