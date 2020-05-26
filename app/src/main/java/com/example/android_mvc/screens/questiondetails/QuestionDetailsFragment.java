package com.example.android_mvc.screens.questiondetails;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android_mvc.R;
import com.example.android_mvc.common.Constants;
import com.example.android_mvc.questions.FetchQuestionDetailsUsecase;
import com.example.android_mvc.questions.QuestionDetails;
import com.example.android_mvc.screens.common.controllers.BackPressedListener;
import com.example.android_mvc.screens.common.controllers.BaseFragment;
import com.example.android_mvc.screens.common.navdrawer.DrawerItems;
import com.example.android_mvc.screens.questionslist.QuestionsListActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class QuestionDetailsFragment extends BaseFragment implements BackPressedListener,
        FetchQuestionDetailsUsecase.Listener, QuestionDetailsViewMvc.Listener {


    private QuestionDetailsViewMvc mQuestionDetailsViewMvc;
    private FetchQuestionDetailsUsecase mFetchQuestionDetailsUsecase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mQuestionDetailsViewMvc = getCompositionRoot().getMvcFactory().getQuestionDetailsViewMvc(container);
        mFetchQuestionDetailsUsecase = getCompositionRoot().getFetchQuestionDetailsUsecase();

        return mQuestionDetailsViewMvc.getRootView();
    }

    @Override
    public void onStart() {
        super.onStart();
        mFetchQuestionDetailsUsecase.registerListener(this);
        mQuestionDetailsViewMvc.showProgressIndication();
        mFetchQuestionDetailsUsecase.fetchQuestionDetailsAndNotify(getQuestionId());
        mQuestionDetailsViewMvc.registerListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mFetchQuestionDetailsUsecase.unregisterListener(this);
        mQuestionDetailsViewMvc.unregisterListener(this);
    }

    private String getQuestionId() {
        //return getIntent().getStringExtra(Constants.EXTRA_QUESTION_ID);
        Bundle b = getArguments();
        String id = b.getString(Constants.EXTRA_QUESTION_ID);
        return id;
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
        Toast.makeText(getContext(), getString(R.string.error_network_call_failed), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNavigateUpClicked() {
        Log.d("KRITI", "onNavigateUpClicked in fragment");
        getActivity().onBackPressed();
    }

    @Override
    public void onDrawerItemClicked(DrawerItems drawerItem) {
        switch(drawerItem) {
            case QUESTIONS_LIST:
                QuestionsListActivity.startClearTop(getContext());
        }
    }

    @Override
    public boolean onBackPressed() {

        if(mQuestionDetailsViewMvc.isDrawerOpen()) {
            Log.d("KRITI", "onNavigateUpClicked in if");
            mQuestionDetailsViewMvc.closeDrawer();
            return true;
        } else {
            Log.d("KRITI", "onNavigateUpClicked in else");
            return false;
        }
    }
}
