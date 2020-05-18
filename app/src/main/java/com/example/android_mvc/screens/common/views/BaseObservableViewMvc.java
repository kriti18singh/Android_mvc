package com.example.android_mvc.screens.common.views;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/*

This class provides observable capabilities to any view mvc
 */
public abstract class BaseObservableViewMvc<ListenerType> extends BaseViewMvc
        implements ObservableViewMvc<ListenerType> {

    private final Set<ListenerType> mListeners = new HashSet<>();

    public final void registerListener(ListenerType listener) {
        mListeners.add(listener);
    }

    public final void unregisterListener(ListenerType listener) {
        mListeners.remove(listener);
    }

    protected final Set<ListenerType> getListeners() {
        return Collections.unmodifiableSet(mListeners);
    }
}
