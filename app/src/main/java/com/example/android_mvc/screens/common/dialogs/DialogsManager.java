package com.example.android_mvc.screens.common.dialogs;

import android.content.Context;

import com.example.android_mvc.R;
import com.example.android_mvc.screens.common.dialogs.infodialog.InfoDialog;
import com.example.android_mvc.screens.common.dialogs.promptdialog.PromptDialog;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class DialogsManager {

    private final Context mContext;
    private final FragmentManager mFragmentManager;

    public DialogsManager(Context mContext, FragmentManager mFragmentManager) {
        this.mContext = mContext;
        this.mFragmentManager = mFragmentManager;
    }

    public void showUsecaseErrorDialog(@Nullable String tag) {
        DialogFragment infoDialog = PromptDialog.newPromptDialog(
                getString(R.string.error_network_call_failed_title),
                getString(R.string.error_network_call_failed_message),
                getString(R.string.error_network_call_failed_positive_button),
                getString(R.string.error_network_call_failed_negative_button)
        );
        infoDialog.show(mFragmentManager, tag);
    }

    private String getString(int stringId) {
        return mContext.getString(stringId);
    }

    public @Nullable String getShownDialogTag() {
        for(Fragment fragment : mFragmentManager.getFragments()) {
            if(fragment instanceof BaseDialog) {
                return fragment.getTag();
            }
        }
        return null;
    }

    public void showPermissionsGrantedDialog(@Nullable String tag) {
        DialogFragment infoDialog = InfoDialog.newInfoDialog(
                getString(R.string.permissions_granted_title),
                getString(R.string.permissions_granted_message),
                getString(R.string.permissions_granted_button_caption)
        );
        infoDialog.show(mFragmentManager, tag);
    }

    public void showPermissionDeclinedCantAskMoreDialog(@Nullable String tag) {
        DialogFragment infoDialog = InfoDialog.newInfoDialog(
                getString(R.string.permissions_declined_title),
                getString(R.string.permissions_declined_cant_ask_more_message),
                getString(R.string.permissions_declined_button_caption)
        );
        infoDialog.show(mFragmentManager, tag);
    }

    public void showPermissionDeclinedDialog(@Nullable String tag) {
        DialogFragment infoDialog = InfoDialog.newInfoDialog(
                getString(R.string.permissions_declined_title),
                getString(R.string.permissions_declined_message),
                getString(R.string.permissions_declined_button_caption)
        );
        infoDialog.show(mFragmentManager, tag);
    }
}
