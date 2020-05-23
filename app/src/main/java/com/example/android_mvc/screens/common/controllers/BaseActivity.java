package com.example.android_mvc.screens.common.controllers;

import android.app.Activity;

import com.example.android_mvc.common.CustomApplication;
import com.example.android_mvc.common.dependencyinjection.ControllerCompositionRoot;

public class BaseActivity extends Activity {

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
