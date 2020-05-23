package com.example.android_mvc.screens.common.toolbars;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android_mvc.R;
import com.example.android_mvc.screens.common.views.BaseViewMvc;

public class ToolbarViewMvc extends BaseViewMvc {

    public interface NavigateUpClickListener {
        void onUpButtonClicked();
    }

    public interface HamburgerBtnClickListener {
        void onHamburgerBtnClicked();
    }

    private TextView mToolbarTitle;
    private ImageButton mBackButton;
    private ImageButton mHamburgerButton;
    private NavigateUpClickListener mNavigateUpListener;
    private HamburgerBtnClickListener mHamburgerListener;

    public ToolbarViewMvc(LayoutInflater inflater, ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.layout_toolbar, parent, false));
        mToolbarTitle = findViewById(R.id.txt_toolbar_title);
        mBackButton = findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNavigateUpListener.onUpButtonClicked();
            }
        });
        mHamburgerButton = findViewById(R.id.hamburger_button);
        mHamburgerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHamburgerListener.onHamburgerBtnClicked();
            }
        });
    }

    public void setToolbarTitle(String title) {
        mToolbarTitle.setText(title);
    }

    public void enableUpButtonAndListen(NavigateUpClickListener navigateUpClickListener) {
        if(mHamburgerListener != null) {
            throw new RuntimeException("Hamburger and Navigate Up button should not be shown together");
        }
        mNavigateUpListener = navigateUpClickListener;
        mBackButton.setVisibility(View.VISIBLE);
    }

    public void enableHamburgerButtonAndListen(HamburgerBtnClickListener hamburgerBtnClickListener) {
        if(mNavigateUpListener != null) {
            throw new RuntimeException("Hamburger and Navigate Up button should not be shown together");
        }
        mHamburgerListener = hamburgerBtnClickListener;
        mHamburgerButton.setVisibility(View.VISIBLE);
    }
}
