package com.example.android_mvc.screens.common.dialogs.promptdialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android_mvc.R;
import com.example.android_mvc.screens.common.views.BaseObservableViewMvc;

public class PromptViewMvcImpl extends BaseObservableViewMvc<PromptViewMvc.Listener>
        implements PromptViewMvc {

    private TextView mDialogTitle;
    private TextView mDialogMessage;
    private Button mDialogPositiveButton;
    private Button mDialogNegativeButton;

    public PromptViewMvcImpl(LayoutInflater inflater, ViewGroup parent) {

        setRootView(inflater.inflate(R.layout.layout_prompt_dialog, parent, false));
        mDialogTitle = findViewById(R.id.txt_title);
        mDialogMessage = findViewById(R.id.txt_message);
        mDialogPositiveButton = findViewById(R.id.btn_positive);
        mDialogNegativeButton = findViewById(R.id.btn_negative);

        mDialogPositiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Listener listener : getListeners()) {
                     listener.onPositiveButtonClicked();
                }
            }
        });

        mDialogNegativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Listener listener : getListeners()) {
                     listener.onNegativeButtonClicked();
                }
            }
        });
    }

    @Override
    public void setTitle(String title) {
        mDialogTitle.setText(title);
    }

    @Override
    public void setMessage(String message) {
        mDialogMessage.setText(message);
    }

    @Override
    public void setPositiveButtonCaption(String caption) {
        mDialogPositiveButton.setText(caption);
    }

    @Override
    public void setNegativeButtonCaption(String caption) {
        mDialogNegativeButton.setText(caption);
    }
}
