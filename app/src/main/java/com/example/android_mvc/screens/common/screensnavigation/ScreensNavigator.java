package com.example.android_mvc.screens.common.screensnavigation;

import com.example.android_mvc.screens.common.controllers.FragmentFrameWrapper;
import com.example.android_mvc.screens.questiondetails.QuestionDetailsFragment;
import com.example.android_mvc.screens.questionslist.QuestionsListFragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ScreensNavigator {

    private final FragmentManager mFragmentManager;
    private final FragmentFrameWrapper mFragmentFrameWrapper;

    public ScreensNavigator(FragmentManager mFragmentManager, FragmentFrameWrapper mFragmentFrameWrapper) {
        this.mFragmentManager = mFragmentManager;
        this.mFragmentFrameWrapper = mFragmentFrameWrapper;
    }

    public void toQuestionsList() {
        mFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.replace(mFragmentFrameWrapper.getFragmentFrame().getId(), QuestionsListFragment.newInstance()).commit();
    }

    public void toQuestionDetails(String questionId) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(mFragmentFrameWrapper.getFragmentFrame().getId(), QuestionDetailsFragment.newInstance(questionId)).commit();
    }
}
