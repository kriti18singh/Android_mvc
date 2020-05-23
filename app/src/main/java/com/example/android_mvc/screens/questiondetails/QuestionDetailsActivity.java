package com.example.android_mvc.screens.questiondetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.android_mvc.R;
import com.example.android_mvc.questions.FetchQuestionDetailsUsecase;
import com.example.android_mvc.questions.QuestionDetails;
import com.example.android_mvc.screens.common.controllers.BaseActivity;
import com.example.android_mvc.screens.common.navdrawer.DrawerItems;
import com.example.android_mvc.screens.questionslist.QuestionsListActivity;

import androidx.annotation.Nullable;

public class QuestionDetailsActivity extends BaseActivity
        implements FetchQuestionDetailsUsecase.Listener, QuestionDetailsViewMvc.Listener {

    private static final String EXTRA_QUESTION_ID = "question_id";
    private QuestionDetailsViewMvc mQuestionDetailsViewMvc;
    private FetchQuestionDetailsUsecase mFetchQuestionDetailsUsecase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mQuestionDetailsViewMvc = getCompositionRoot().getMvcFactory().getQuestionDetailsViewMvc(null);
        mFetchQuestionDetailsUsecase = getCompositionRoot().getFetchQuestionDetailsUsecase();

        setContentView(mQuestionDetailsViewMvc.getRootView());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFetchQuestionDetailsUsecase.registerListener(this);
        mQuestionDetailsViewMvc.showProgressIndication();
        mFetchQuestionDetailsUsecase.fetchQuestionDetailsAndNotify(getQuestionId());
        mQuestionDetailsViewMvc.registerListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFetchQuestionDetailsUsecase.unregisterListener(this);
        mQuestionDetailsViewMvc.unregisterListener(this);
    }

    private String getQuestionId() {
        return getIntent().getStringExtra(EXTRA_QUESTION_ID);
    }

    public static void start(Context context, String questionId) {
        Intent intent = new Intent(context, QuestionDetailsActivity.class);
        intent.putExtra(EXTRA_QUESTION_ID, questionId);
        context.startActivity(intent);
    }

    private void bindQuestion(QuestionDetails questionDetails) {
        mQuestionDetailsViewMvc.hideProgressIndication();
        mQuestionDetailsViewMvc.bindQuestionDetails(questionDetails);
    }

    @Override
    public void onQuestionDetailsFetched(QuestionDetails questionDetails) {
        bindQuestion(questionDetails);
    }

    @Override
    public void onQuestionDetailsFetchFailed() {
        mQuestionDetailsViewMvc.hideProgressIndication();
        Toast.makeText(this, getString(R.string.error_network_call_failed), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNavigateUpClicked() {
        onBackPressed();
    }

    @Override
    public void onDrawerItemClicked(DrawerItems drawerItem) {
        switch(drawerItem) {
            case QUESTIONS_LIST:
                QuestionsListActivity.startClearTop(this);
        }
    }

    @Override
    public void onBackPressed() {
        if(mQuestionDetailsViewMvc.isDrawerOpen()) {
            mQuestionDetailsViewMvc.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
}
