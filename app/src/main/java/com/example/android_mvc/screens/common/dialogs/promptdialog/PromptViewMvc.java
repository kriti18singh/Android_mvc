package com.example.android_mvc.screens.common.dialogs.promptdialog;

import com.example.android_mvc.screens.common.views.ObservableViewMvc;

public interface PromptViewMvc extends ObservableViewMvc<PromptViewMvc.Listener> {

    public interface Listener {
        void onPositiveButtonClicked();
        void onNegativeButtonClicked();
    }

    //to make the dialog configurable
    void setTitle(String title);
    void setMessage(String message);
    void setPositiveButtonCaption(String caption);
    void setNegativeButtonCaption(String caption);
}
