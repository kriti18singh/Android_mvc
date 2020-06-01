package com.example.android_mvc.screens.common.dialogs.promptdialog;

public class PromptDialogEvent {

    public enum Button {
        POSITIVE, NEGATIVE
    }

    private final Button mClickedButton;

    public PromptDialogEvent(Button mButton) {
        this.mClickedButton = mButton;
    }

    public Button getClickedButton() {
        return mClickedButton;
    }
}
