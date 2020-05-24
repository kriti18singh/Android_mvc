package com.example.android_mvc.screens.common.controllers;

import com.example.android_mvc.common.CustomApplication;
import com.example.android_mvc.common.dependencyinjection.ControllerCompositionRoot;

import androidx.fragment.app.FragmentActivity;

public class BaseActivity extends FragmentActivity {

    private ControllerCompositionRoot mControllerCompositionRoot;

    public ControllerCompositionRoot getCompositionRoot() {
        if(mControllerCompositionRoot == null) {
            mControllerCompositionRoot = new ControllerCompositionRoot(
                    ((CustomApplication) getApplication()).getCompositionRoot(),
                    this
            );
        }
        return mControllerCompositionRoot;
    }
}
