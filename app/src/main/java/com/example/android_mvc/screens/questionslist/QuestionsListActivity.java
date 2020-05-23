package com.example.android_mvc.screens.questionslist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.android_mvc.R;
import com.example.android_mvc.questions.FetchLastActiveQuestionsUsecase;
import com.example.android_mvc.screens.common.controllers.BaseActivity;
import com.example.android_mvc.questions.Question;
import com.example.android_mvc.screens.questiondetails.QuestionDetailsActivity;

import java.util.List;

public class QuestionsListActivity extends BaseActivity implements QuestionsListViewMvcImpl.Listener, FetchLastActiveQuestionsUsecase.Listener {

    private QuestionsListViewMvc mViewMvc;
    private FetchLastActiveQuestionsUsecase mFetchLastActiveQuestionsUsecase;

    public static void startClearTop(Context context) {
        Intent intent = new Intent(context, QuestionsListActivity.class);
        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewMvc = getCompositionRoot().getMvcFactory().getQuestionListViewMvc(null);

        mViewMvc.registerListener(this);

        mFetchLastActiveQuestionsUsecase = getCompositionRoot().getFetchLastActiveQuestionsUsecase();

        setContentView(mViewMvc.getRootView());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFetchLastActiveQuestionsUsecase.registerListener(this);
        mFetchLastActiveQuestionsUsecase.fetchLastActiveQuestionsAndNotify();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFetchLastActiveQuestionsUsecase.unregisterListener(this);
    }

    private void bindQuestions(List<Question> questions) {
        mViewMvc.bindQuestions(questions);
    }

    @Override
    public void onQuestionClicked(Question question) {
        QuestionDetailsActivity.start(this, question.getId());
    }

    @Override
    public void onQuestionsListItemClicked() {
        //this is the Questions List screen, so no op
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewMvc.unregisterListener(this);
    }

    @Override
    public void onLastActiveQuestionsFetched(List<Question> questions) {
        bindQuestions(questions);
    }

    @Override
    public void onLastActiveQuestionsFetchFailed() {
        Toast.makeText(this, R.string.error_network_call_failed, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        //close the drawer if it is open
        if(mViewMvc.isDrawerOpen()) {
            mViewMvc.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
}
