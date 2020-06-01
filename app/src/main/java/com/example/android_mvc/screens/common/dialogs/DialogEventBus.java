package com.example.android_mvc.screens.common.dialogs;

import com.example.android_mvc.common.BaseObservable;

public class DialogEventBus extends BaseObservable<DialogEventBus.Listener> {

    public interface Listener {
        void onDialogEvent(Object event);
    }

    public void postEvent(Object event) {
        for(Listener listener : getListeners()) {
             listener.onDialogEvent(event);
        }
    }
}
