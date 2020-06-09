package com.example.android_mvc.screens.common.dialogs;

import com.example.android_mvc.common.dependencyinjection.ControllerCompositionRoot;
import com.example.android_mvc.screens.common.main.MainActivity;

import androidx.fragment.app.DialogFragment;

public abstract class BaseDialog extends DialogFragment {

    private ControllerCompositionRoot mControllerCompositionRoot;

    public ControllerCompositionRoot getCompositionRoot() {
        if(mControllerCompositionRoot == null) {
            mControllerCompositionRoot = new ControllerCompositionRoot(
                    ((MainActivity)requireActivity()).getActivityCompositionRoot());
        }
        return mControllerCompositionRoot;
    }

}
