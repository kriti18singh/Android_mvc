package com.example.android_mvc.screens.common.dialogs.infodialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android_mvc.R;
import com.example.android_mvc.screens.common.dialogs.BaseDialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class InfoDialog extends BaseDialog {

    private static final String EXTRA_DIALOG_TITLE = "dialog_title";
    private static final String EXTRA_DIALOG_MESSAGE = "dialog_message";
    private static final String EXTRA_DIALOG_BUTTON_CAPTION = "dialog_button_caption";

    public static InfoDialog newInfoDialog(String title, String message, String buttonCaption) {
        InfoDialog dialog = new InfoDialog();
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_DIALOG_TITLE, title);
        arguments.putString(EXTRA_DIALOG_MESSAGE, message);
        arguments.putString(EXTRA_DIALOG_BUTTON_CAPTION, buttonCaption);
        dialog.setArguments(arguments);
        return dialog;
    }

    private TextView mDialogTitle;
    private TextView mDialogMessage;
    private Button mDialogButton;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getArguments() == null) {
            throw new IllegalStateException("arguments mustn't be null");
        }

        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_info_dialog);
        mDialogTitle = dialog.findViewById(R.id.txt_title);
        mDialogMessage = dialog.findViewById(R.id.txt_message);
        mDialogButton = dialog.findViewById(R.id.btn_positive);

        mDialogTitle.setText(getArguments().getString(EXTRA_DIALOG_TITLE));
        mDialogMessage.setText(getArguments().getString(EXTRA_DIALOG_MESSAGE));
        mDialogButton.setText(getArguments().getString(EXTRA_DIALOG_BUTTON_CAPTION));

        mDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClicked();
            }
        });

        return dialog;
    }

    private void onButtonClicked() {
        dismiss();
    }
}
