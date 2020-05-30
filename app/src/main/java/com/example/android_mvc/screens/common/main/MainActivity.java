package com.example.android_mvc.screens.common.main;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.android_mvc.R;
import com.example.android_mvc.screens.common.controllers.BackPressedDispatcher;
import com.example.android_mvc.screens.common.controllers.BackPressedListener;
import com.example.android_mvc.screens.common.controllers.BaseActivity;
import com.example.android_mvc.screens.common.controllers.FragmentFrameWrapper;
import com.example.android_mvc.screens.common.navdrawer.NavDrawerHelper;
import com.example.android_mvc.screens.common.navdrawer.NavDrawerViewMvc;
import com.example.android_mvc.screens.common.screensnavigation.ScreensNavigator;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends BaseActivity implements BackPressedDispatcher,
        FragmentFrameWrapper, NavDrawerViewMvc.Listener, NavDrawerHelper {

    private final Set<BackPressedListener> mListeners = new HashSet<>();
    private NavDrawerViewMvc mNavDrawerViewMvc;
    private ScreensNavigator mScreensNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mScreensNavigator = getCompositionRoot().getScreensNavigator();
        mNavDrawerViewMvc = getCompositionRoot().getMvcFactory().getNavDrawerViewMvc(null);
        setContentView(mNavDrawerViewMvc.getRootView());
        if(savedInstanceState == null) {
            //no recreation, fresh activity
            mScreensNavigator.toQuestionsList();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mNavDrawerViewMvc.registerListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mNavDrawerViewMvc.unregisterListener(this);
    }

    @Override
    public void onBackPressed() {
        boolean isBackPressConsumed = false;
        for(BackPressedListener l : mListeners) {
            if(l.onBackPressed()) {
                isBackPressConsumed = true;
            }
        }
        if(!isBackPressConsumed) {
            super.onBackPressed();
        }
    }

    @Override
    public void registerListener(BackPressedListener listener) {
        mListeners.add(listener);
    }

    @Override
    public void unregisterListener(BackPressedListener listener) {
        mListeners.remove(listener);
    }

    @Override
    public FrameLayout getFragmentFrame() {
        return mNavDrawerViewMvc.getFragmentFrame();
    }

    @Override
    public void onQuestionListItemClicked() {
        mScreensNavigator.toQuestionsList();
    }

    @Override
    public void openDrawer() {
        mNavDrawerViewMvc.openDrawer();
    }

    @Override
    public void closeDrawer() {
        mNavDrawerViewMvc.closeDrawer();
    }

    @Override
    public boolean isDrawerOpen() {
        return mNavDrawerViewMvc.isDrawerOpen();
    }
}
