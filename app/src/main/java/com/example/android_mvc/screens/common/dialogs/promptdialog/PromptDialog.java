package com.example.android_mvc.screens.common.dialogs.promptdialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android_mvc.R;
import com.example.android_mvc.screens.common.dialogs.BaseDialog;
import com.example.android_mvc.screens.common.dialogs.DialogEventBus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PromptDialog extends BaseDialog {

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
    private TextView mDialogTitle;
    private TextView mDialogMessage;
    private Button mDialogPositiveButton;
    private Button mDialogNegativeButton;
    private DialogEventBus mDialogEventBus;

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
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_prompt_dialog);
        mDialogTitle = dialog.findViewById(R.id.txt_title);
        mDialogMessage = dialog.findViewById(R.id.txt_message);
        mDialogPositiveButton = dialog.findViewById(R.id.btn_positive);
        mDialogNegativeButton = dialog.findViewById(R.id.btn_negative);

        mDialogTitle.setText(getArguments().getString(EXTRA_DIALOG_TITLE));
        mDialogMessage.setText(getArguments().getString(EXTRA_DIALOG_MESSAGE));
        mDialogPositiveButton.setText(getArguments().getString(EXTRA_DIALOG_POSITIVE_BUTTON_CAPTION));

        mDialogPositiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPositiveButtonClicked();
            }
        });
        mDialogNegativeButton.setText(getArguments().getString(EXTRA_DIALOG_NEGATIVE_BUTTON_CAPTION));

        mDialogNegativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNegativeButtonClicked();
            }
        });

        return dialog;
    }

    private void onNegativeButtonClicked() {
        dismiss();
        mDialogEventBus.postEvent(new PromptDialogEvent(PromptDialogEvent.Button.NEGATIVE));
    }

    private void onPositiveButtonClicked() {
        dismiss();
        mDialogEventBus.postEvent(new PromptDialogEvent(PromptDialogEvent.Button.POSITIVE));
    }
}
