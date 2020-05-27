package com.example.android_mvc.screens.questiondetails;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android_mvc.R;
import com.example.android_mvc.questions.FetchQuestionDetailsUsecase;
import com.example.android_mvc.questions.QuestionDetails;
import com.example.android_mvc.screens.common.controllers.BackPressedDispatcher;
import com.example.android_mvc.screens.common.controllers.BackPressedListener;
import com.example.android_mvc.screens.common.controllers.BaseFragment;
import com.example.android_mvc.screens.common.navdrawer.DrawerItems;
import com.example.android_mvc.screens.common.screensnavigation.ScreensNavigator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class QuestionDetailsFragment extends BaseFragment implements BackPressedListener,
        FetchQuestionDetailsUsecase.Listener, QuestionDetailsViewMvc.Listener {

    private static final String ARG_QUESTION_ID = "ARG_QUESTION_ID";

    private QuestionDetailsViewMvc mQuestionDetailsViewMvc;
    private FetchQuestionDetailsUsecase mFetchQuestionDetailsUsecase;
    private BackPressedDispatcher mBackPressedDispatcher;
    private ScreensNavigator mScreensNavigator;

    public static Fragment newInstance(String questionId) {
        Bundle args = new Bundle();
        args.putString(ARG_QUESTION_ID, questionId);
        QuestionDetailsFragment fragment = new QuestionDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mQuestionDetailsViewMvc = getCompositionRoot().getMvcFactory().getQuestionDetailsViewMvc(container);
        mFetchQuestionDetailsUsecase = getCompositionRoot().getFetchQuestionDetailsUsecase();
        mBackPressedDispatcher = getCompositionRoot().getBackPressedDispatcher();
        mScreensNavigator = getCompositionRoot().getScreensNavigator();

        return mQuestionDetailsViewMvc.getRootView();
    }

    @Override
    public void onStart() {
        super.onStart();
        mFetchQuestionDetailsUsecase.registerListener(this);
        mQuestionDetailsViewMvc.showProgressIndication();
        mFetchQuestionDetailsUsecase.fetchQuestionDetailsAndNotify(getQuestionId());
        mQuestionDetailsViewMvc.registerListener(this);
        mBackPressedDispatcher.registerListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mFetchQuestionDetailsUsecase.unregisterListener(this);
        mQuestionDetailsViewMvc.unregisterListener(this);
        mBackPressedDispatcher.unregisterListener(this);
    }

    private String getQuestionId() {
        Bundle b = getArguments();
        String id = b.getString(ARG_QUESTION_ID);
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
        getActivity().onBackPressed();
    }

    @Override
    public void onDrawerItemClicked(DrawerItems drawerItem) {
        switch(drawerItem) {
            case QUESTIONS_LIST:
                mScreensNavigator.toQuestionsList();
        }
    }

    @Override
    public boolean onBackPressed() {

        if(mQuestionDetailsViewMvc.isDrawerOpen()) {
            mQuestionDetailsViewMvc.closeDrawer();
            return true;
        } else {
            return false;
        }
    }
}
