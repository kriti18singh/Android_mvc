package com.example.android_mvc.screens.questiondetails;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android_mvc.R;
import com.example.android_mvc.questions.QuestionDetails;
import com.example.android_mvc.screens.common.views.BaseViewMvc;

public class QuestionDetailsViewMvcImpl extends BaseViewMvc implements QuestionDetailsViewMvc {

    private TextView mTitleTv;
    private TextView mDetailsTv;
    private ProgressBar mProgressBar;

    public QuestionDetailsViewMvcImpl(LayoutInflater inflater, ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.layout_question_details, parent, false));

        mTitleTv = findViewById(R.id.txt_question_title);
        mDetailsTv = findViewById(R.id.txt_question_body);
        mProgressBar = findViewById(R.id.progress);
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
}
