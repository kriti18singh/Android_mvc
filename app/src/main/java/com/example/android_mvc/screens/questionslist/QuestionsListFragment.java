package com.example.android_mvc.screens.questionslist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android_mvc.R;
import com.example.android_mvc.questions.FetchLastActiveQuestionsUsecase;
import com.example.android_mvc.questions.Question;
import com.example.android_mvc.screens.common.controllers.BaseFragment;
import com.example.android_mvc.screens.common.screensnavigation.ScreensNavigator;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class QuestionsListFragment extends BaseFragment implements
        QuestionsListViewMvcImpl.Listener, FetchLastActiveQuestionsUsecase.Listener {

    private QuestionsListViewMvc mViewMvc;
    private FetchLastActiveQuestionsUsecase mFetchLastActiveQuestionsUsecase;
    private ScreensNavigator mScreensNavigator;

    public static Fragment newInstance() {
        return new QuestionsListFragment();
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

        return mViewMvc.getRootView();
    }

    @Override
    public void onStart() {
        super.onStart();
        mFetchLastActiveQuestionsUsecase.registerListener(this);
        mFetchLastActiveQuestionsUsecase.fetchLastActiveQuestionsAndNotify();
    }

    @Override
    public void onStop() {
        super.onStop();
        mFetchLastActiveQuestionsUsecase.unregisterListener(this);
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
    public void onLastActiveQuestionsFetched(List<Question> questions) {
        bindQuestions(questions);
    }

    @Override
    public void onLastActiveQuestionsFetchFailed() {
        Toast.makeText(getContext(), R.string.error_network_call_failed, Toast.LENGTH_SHORT).show();
    }
}
