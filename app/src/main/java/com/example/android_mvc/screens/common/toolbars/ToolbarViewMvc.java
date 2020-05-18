package com.example.android_mvc.screens.common.toolbars;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android_mvc.R;
import com.example.android_mvc.screens.common.views.BaseViewMvc;

public class ToolbarViewMvc extends BaseViewMvc {

    public interface NavigateUpClickListener {
        public void onUpButtonClicked();
    }

    private TextView mToolbarTitle;
    private ImageButton mBackButton;
    private NavigateUpClickListener mListener;

    public ToolbarViewMvc(LayoutInflater inflater, ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.layout_toolbar, parent, false));
        mToolbarTitle = findViewById(R.id.txt_toolbar_title);
        mBackButton = findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onUpButtonClicked();
            }
        });
    }

    public void setToolbarTitle(String title) {
        mToolbarTitle.setText(title);
    }

    public void enableUpButtonAndListen(NavigateUpClickListener navigateUpClickListener) {
        mListener = navigateUpClickListener;
        mBackButton.setVisibility(View.VISIBLE);
    }
}
