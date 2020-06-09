package com.example.android_mvc.screens.common.controllers;

import com.example.android_mvc.common.CustomApplication;
import com.example.android_mvc.common.dependencyinjection.ActivityCompositionRoot;
import com.example.android_mvc.common.dependencyinjection.ControllerCompositionRoot;

import androidx.fragment.app.FragmentActivity;

public class BaseActivity extends FragmentActivity {

    private ControllerCompositionRoot mControllerCompositionRoot;
    private ActivityCompositionRoot mActivityCompositionRoot;

    public ControllerCompositionRoot getCompositionRoot() {
        if(mControllerCompositionRoot == null) {
            mControllerCompositionRoot = new ControllerCompositionRoot(
                    getActivityCompositionRoot());
        }
        return mControllerCompositionRoot;
    }

    public ActivityCompositionRoot getActivityCompositionRoot() {
        if(mActivityCompositionRoot == null) {
            mActivityCompositionRoot = new ActivityCompositionRoot(
                    ((CustomApplication) getApplication()).getCompositionRoot(),
                    this);
        }
        return mActivityCompositionRoot;
    }
}
