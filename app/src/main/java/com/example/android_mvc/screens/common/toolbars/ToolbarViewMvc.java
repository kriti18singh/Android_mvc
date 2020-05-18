package com.example.android_mvc.screens.common.toolbars;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android_mvc.R;
import com.example.android_mvc.screens.common.views.BaseViewMvc;

public class ToolbarViewMvc extends BaseViewMvc {

    private TextView mToolbarTitle;

    public ToolbarViewMvc(LayoutInflater inflater, ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.layout_toolbar, parent, false));
        mToolbarTitle = findViewById(R.id.txt_toolbar_title);
    }

    public void setToolbarTitle(String title) {
        mToolbarTitle.setText(title);
    }
}
