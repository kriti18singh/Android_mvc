package com.example.android_mvc.screens.questionslist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android_mvc.questions.FetchLastActiveQuestionsUsecase;
import com.example.android_mvc.questions.Question;
import com.example.android_mvc.screens.common.controllers.BaseFragment;
import com.example.android_mvc.screens.common.dialogs.DialogEventBus;
import com.example.android_mvc.screens.common.dialogs.DialogsManager;
import com.example.android_mvc.screens.common.dialogs.promptdialog.PromptDialogEvent;
import com.example.android_mvc.screens.common.screensnavigation.ScreensNavigator;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class QuestionsListFragment extends BaseFragment implements
        QuestionsListViewMvc.Listener, FetchLastActiveQuestionsUsecase.Listener, DialogEventBus.Listener {

    private static final String SAVED_STATE_SCREEN_STATE = "saved_state_screen_state";

    public static Fragment newInstance() {
        return new QuestionsListFragment();
    }

    private enum ScreenState {
        IDLE, QUESTIONS_LIST_SHOWN, NETWORK_ERROR
    }

    private QuestionsListViewMvc mViewMvc;
    private FetchLastActiveQuestionsUsecase mFetchLastActiveQuestionsUsecase;
    private ScreensNavigator mScreensNavigator;
    private DialogsManager mDialogsManager;
    private DialogEventBus mDialogEventBus;

    private ScreenState mScreenState = ScreenState.IDLE;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null) {
            mScreenState = (ScreenState) savedInstanceState.getSerializable(SAVED_STATE_SCREEN_STATE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewMvc = getCompositionRoot().getMvcFactory().getQuestionListViewMvc(null);

        mViewMvc.registerListener(this);

        mFetchLastActiveQuestionsUsecase = getCompositionRoot().getFetchLastActiveQuestionsUsecase();

        mScreensNavigator = getCompositionRoot().getScreensNavigator();

        mDialogsManager = getCompositionRoot().getDialogsManager();

        mDialogEventBus = getCompositionRoot().getDialogEventBus();

        return mViewMvc.getRootView();
    }

    @Override
    public void onStart() {
        super.onStart();
        mFetchLastActiveQuestionsUsecase.registerListener(this);
        mDialogEventBus.registerListener(this);
        if(!mScreenState.equals(ScreenState.NETWORK_ERROR)) {
            fetchQuestionsAndNotify();
        }
    }

    private void fetchQuestionsAndNotify() {
        mViewMvc.showProgressIndication();
        mFetchLastActiveQuestionsUsecase.fetchLastActiveQuestionsAndNotify();
    }

    @Override
    public void onStop() {
        super.onStop();
        mFetchLastActiveQuestionsUsecase.unregisterListener(this);
        mDialogEventBus.unregisterListener(this);
    }

    private void bindQuestions(List<Question> questions) {
        mViewMvc.bindQuestions(questions);
    }

    @Override
    public void onQuestionClicked(Question question) {
        mScreensNavigator.toQuestionDetails(question.getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewMvc.unregisterListener(this);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SAVED_STATE_SCREEN_STATE, mScreenState);
    }

    @Override
    public void onLastActiveQuestionsFetched(List<Question> questions) {
        mViewMvc.hideProgressIndication();
        bindQuestions(questions);
        mScreenState = ScreenState.QUESTIONS_LIST_SHOWN;
    }

    @Override
    public void onLastActiveQuestionsFetchFailed() {
        mViewMvc.hideProgressIndication();
        mDialogsManager.showUsecaseErrorDialog(null);
        mScreenState = ScreenState.NETWORK_ERROR;
    }

    @Override
    public void onDialogEvent(Object event) {
        if(event instanceof PromptDialogEvent) {
            switch(((PromptDialogEvent) event).getClickedButton()) {
                case POSITIVE:
                    mScreenState = ScreenState.IDLE;
                    mFetchLastActiveQuestionsUsecase.fetchLastActiveQuestionsAndNotify();
                    break;
                case NEGATIVE:
                    mScreenState = ScreenState.IDLE;
                    break;
            }
        }
    }
}
