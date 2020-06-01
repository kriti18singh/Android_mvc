package com.example.android_mvc.screens.common.dialogs;

import com.example.android_mvc.common.CustomApplication;
import com.example.android_mvc.common.dependencyinjection.ControllerCompositionRoot;

import androidx.fragment.app.DialogFragment;

public abstract class BaseDialog extends DialogFragment {

    private ControllerCompositionRoot mControllerCompositionRoot;

    public ControllerCompositionRoot getCompositionRoot() {
        if(mControllerCompositionRoot == null) {
            mControllerCompositionRoot = new ControllerCompositionRoot(
                    ((CustomApplication) requireActivity().getApplication()).getCompositionRoot(),
                    requireActivity()
            );
        }
        return mControllerCompositionRoot;
    }

}
