package com.example.android_mvc.screens.questiondetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android_mvc.questions.FetchQuestionDetailsUsecase;
import com.example.android_mvc.questions.QuestionDetails;
import com.example.android_mvc.screens.common.controllers.BaseFragment;
import com.example.android_mvc.screens.common.dialogs.DialogEventBus;
import com.example.android_mvc.screens.common.dialogs.DialogsManager;
import com.example.android_mvc.screens.common.dialogs.promptdialog.PromptDialogEvent;
import com.example.android_mvc.screens.common.screensnavigation.ScreensNavigator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class QuestionDetailsFragment extends BaseFragment implements
        FetchQuestionDetailsUsecase.Listener, QuestionDetailsViewMvc.Listener, DialogEventBus.Listener {

    private static final String ARG_QUESTION_ID = "ARG_QUESTION_ID";

    private QuestionDetailsViewMvc mQuestionDetailsViewMvc;
    private FetchQuestionDetailsUsecase mFetchQuestionDetailsUsecase;
    private ScreensNavigator mScreensNavigator;
    private DialogsManager mDialogsManager;
    private DialogEventBus mDialogEventBus;

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
        mScreensNavigator = getCompositionRoot().getScreensNavigator();
        mDialogsManager = getCompositionRoot().getDialogsManager();
        mDialogEventBus = getCompositionRoot().getDialogEventBus();

        return mQuestionDetailsViewMvc.getRootView();
    }

    @Override
    public void onStart() {
        super.onStart();
        mFetchQuestionDetailsUsecase.registerListener(this);
        mQuestionDetailsViewMvc.showProgressIndication();
        mFetchQuestionDetailsUsecase.fetchQuestionDetailsAndNotify(getQuestionId());
        mQuestionDetailsViewMvc.registerListener(this);
        mDialogEventBus.registerListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mFetchQuestionDetailsUsecase.unregisterListener(this);
        mQuestionDetailsViewMvc.unregisterListener(this);
        mDialogEventBus.unregisterListener(this);
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
        mDialogsManager.showUsecaseErrorDialog(null);
    }

    @Override
    public void onNavigateUpClicked() {
        mScreensNavigator.navigateUp();
    }

    @Override
    public void onDialogEvent(Object event) {
        if(event instanceof PromptDialogEvent) {
            switch (((PromptDialogEvent) event).getClickedButton()) {
                case POSITIVE:
                    mFetchQuestionDetailsUsecase.fetchQuestionDetailsAndNotify(getQuestionId());
                    break;
                case NEGATIVE:
                    break;
            }
        }
    }
}
