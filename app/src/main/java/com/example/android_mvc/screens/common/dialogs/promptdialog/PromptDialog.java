package com.example.android_mvc.screens.common.dialogs.promptdialog;

import android.app.Dialog;
import android.os.Bundle;

import com.example.android_mvc.screens.common.dialogs.BaseDialog;
import com.example.android_mvc.screens.common.dialogs.DialogEventBus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PromptDialog extends BaseDialog implements PromptViewMvc.Listener {

    private static final String EXTRA_DIALOG_TITLE = "dialog_title";
    private static final String EXTRA_DIALOG_MESSAGE = "dialog_message";
    private static final String EXTRA_DIALOG_POSITIVE_BUTTON_CAPTION = "dialog_positive_button_caption";
    private static final String EXTRA_DIALOG_NEGATIVE_BUTTON_CAPTION = "dialog_negative_button_caption";


    public static PromptDialog newPromptDialog(String title, String message, String positiveBtnCaption,
                                               String negativeBtnCaption) {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_DIALOG_TITLE, title);
        bundle.putString(EXTRA_DIALOG_MESSAGE, message);
        bundle.putString(EXTRA_DIALOG_POSITIVE_BUTTON_CAPTION, positiveBtnCaption);
        bundle.putString(EXTRA_DIALOG_NEGATIVE_BUTTON_CAPTION, negativeBtnCaption);
        PromptDialog dialog = new PromptDialog();
        dialog.setArguments(bundle);
        return dialog;
    }
    private DialogEventBus mDialogEventBus;
    private PromptViewMvc mPromptViewMvc;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDialogEventBus = getCompositionRoot().getDialogEventBus();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getArguments() == null) {
            throw new IllegalStateException("arguments mustn't be null");
        }
        mPromptViewMvc = getCompositionRoot().getMvcFactory().getPromptViewMvc(null);
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(mPromptViewMvc.getRootView());

        mPromptViewMvc.setTitle(getArguments().getString(EXTRA_DIALOG_TITLE));
        mPromptViewMvc.setMessage(getArguments().getString(EXTRA_DIALOG_MESSAGE));
        mPromptViewMvc.setPositiveButtonCaption(getArguments().getString(EXTRA_DIALOG_POSITIVE_BUTTON_CAPTION));
        mPromptViewMvc.setNegativeButtonCaption(getArguments().getString(EXTRA_DIALOG_NEGATIVE_BUTTON_CAPTION));

        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPromptViewMvc.registerListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mPromptViewMvc.unregisterListener(this);
    }

    @Override
    public void onNegativeButtonClicked() {
        dismiss();
        mDialogEventBus.postEvent(new PromptDialogEvent(PromptDialogEvent.Button.NEGATIVE));
    }

    @Override
    public void onPositiveButtonClicked() {
        dismiss();
        mDialogEventBus.postEvent(new PromptDialogEvent(PromptDialogEvent.Button.POSITIVE));
    }
}
