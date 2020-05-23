package com.example.android_mvc.screens.questiondetails;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android_mvc.R;
import com.example.android_mvc.questions.QuestionDetails;
import com.example.android_mvc.screens.common.ViewMvcFactory;
import com.example.android_mvc.screens.common.navdrawer.BaseNavDrawerViewMvc;
import com.example.android_mvc.screens.common.navdrawer.DrawerItems;
import com.example.android_mvc.screens.common.toolbars.ToolbarViewMvc;

import androidx.appcompat.widget.Toolbar;

public class QuestionDetailsViewMvcImpl extends BaseNavDrawerViewMvc<QuestionDetailsViewMvc.Listener>
        implements QuestionDetailsViewMvc {

    private TextView mTitleTv;
    private TextView mDetailsTv;
    private ProgressBar mProgressBar;
    private Toolbar mToolbar;
    private ToolbarViewMvc mToolbarViewMvc;

    public QuestionDetailsViewMvcImpl(LayoutInflater inflater, ViewGroup parent,
                                      ViewMvcFactory viewMvcFactory) {
        super(inflater, parent);
        setRootView(inflater.inflate(R.layout.layout_question_details, parent, false));

        mTitleTv = findViewById(R.id.txt_question_title);
        mDetailsTv = findViewById(R.id.txt_question_body);
        mProgressBar = findViewById(R.id.progress);
        mToolbar = findViewById(R.id.toolbar);
        mToolbarViewMvc = viewMvcFactory.getToolbarViewMvc(mToolbar);

        initToolbar();
    }

    private void initToolbar() {
        mToolbar.addView(mToolbarViewMvc.getRootView());
        mToolbarViewMvc.setToolbarTitle(getString(R.string.question_detail_screen_title));
        mToolbarViewMvc.enableUpButtonAndListen(new ToolbarViewMvc.NavigateUpClickListener() {
            @Override
            public void onUpButtonClicked() {
                for(Listener l : getListeners()) {
                    l.onNavigateUpClicked();
                }
            }
        });

    }

    @Override
    public void bindQuestionDetails(QuestionDetails questionDetails) {
        mTitleTv.setText(questionDetails.getTitle());
        mDetailsTv.setText(questionDetails.getBody());
    }

    @Override
    public void showProgressIndication() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressIndication() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onDrawerItemClicked(DrawerItems drawerItem) {
        for(Listener l :getListeners()) {
            l.onDrawerItemClicked(drawerItem);
        }
    }
}
