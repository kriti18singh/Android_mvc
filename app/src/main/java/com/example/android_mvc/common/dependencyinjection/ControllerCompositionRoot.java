package com.example.android_mvc.common.dependencyinjection;

import android.app.Activity;
import android.view.LayoutInflater;

import com.example.android_mvc.networking.StackoverflowApi;
import com.example.android_mvc.screens.common.ViewMvcFactory;

public class ControllerCompositionRoot {

    private final CompositionRoot mCompositionRoot;
    private final Activity mActivity;

    public ControllerCompositionRoot(CompositionRoot mCompositionRoot, Activity activity) {
        this.mCompositionRoot = mCompositionRoot;
        this.mActivity = activity;
    }

    public StackoverflowApi getStackoverflowApi() {
        return mCompositionRoot.getStackoverflowApi();
    }

    private LayoutInflater getInflater() {
        return LayoutInflater.from(mActivity);
    }

    public ViewMvcFactory getMvcFactory() {
        return  new ViewMvcFactory(getInflater());
    }
}
