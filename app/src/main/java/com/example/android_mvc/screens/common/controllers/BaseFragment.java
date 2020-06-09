package com.example.android_mvc.screens.common.controllers;

import com.example.android_mvc.common.dependencyinjection.ControllerCompositionRoot;
import com.example.android_mvc.screens.common.main.MainActivity;

import androidx.fragment.app.Fragment;


public class BaseFragment extends Fragment {

    private ControllerCompositionRoot mControllerCompositionRoot;

    public ControllerCompositionRoot getCompositionRoot() {
        if(mControllerCompositionRoot == null) {
            mControllerCompositionRoot = new ControllerCompositionRoot(
                    ((MainActivity)getActivity()).getActivityCompositionRoot());
        }
        return mControllerCompositionRoot;
    }
}
