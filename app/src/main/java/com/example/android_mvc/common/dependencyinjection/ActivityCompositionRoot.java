package com.example.android_mvc.common.dependencyinjection;

import com.example.android_mvc.common.permissions.PermissionManager;
import com.example.android_mvc.networking.StackoverflowApi;
import com.example.android_mvc.screens.common.dialogs.DialogEventBus;

import androidx.fragment.app.FragmentActivity;

public class ActivityCompositionRoot {

    private final CompositionRoot mCompositionRoot;
    private final FragmentActivity mActivity;
    private PermissionManager mPermissionManager;

    public ActivityCompositionRoot(CompositionRoot mCompositionRoot, FragmentActivity mActivity) {
        this.mCompositionRoot = mCompositionRoot;
        this.mActivity = mActivity;
    }

    public FragmentActivity getActivity() {
        return mActivity;
    }

    public StackoverflowApi getStackoverflowApi() {
        return mCompositionRoot.getStackoverflowApi();
    }

    public DialogEventBus getDialogEventBus() {
        return mCompositionRoot.getDialogEventBus();
    }

    public PermissionManager getPermissionManager() {
        if(mPermissionManager == null) {
            mPermissionManager = new PermissionManager(getActivity());
        }
        return mPermissionManager;
    }
}
