package com.example.android_mvc.screens.common.navdrawer;

import android.widget.FrameLayout;

import com.example.android_mvc.screens.common.views.ObservableViewMvc;

public interface NavDrawerViewMvc extends ObservableViewMvc<NavDrawerViewMvc.Listener> {

    FrameLayout getFragmentFrame();

    public interface Listener {
        void onQuestionListItemClicked();
    }

    boolean isDrawerOpen();
    void openDrawer();
    void closeDrawer();
}
