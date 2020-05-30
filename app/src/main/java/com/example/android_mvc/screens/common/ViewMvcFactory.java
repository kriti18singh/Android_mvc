package com.example.android_mvc.screens.common;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.android_mvc.screens.common.navdrawer.NavDrawerHelper;
import com.example.android_mvc.screens.common.navdrawer.NavDrawerViewMvc;
import com.example.android_mvc.screens.common.navdrawer.NavDrawerViewMvcImpl;
import com.example.android_mvc.screens.common.toolbars.ToolbarViewMvc;
import com.example.android_mvc.screens.questiondetails.QuestionDetailsViewMvc;
import com.example.android_mvc.screens.questiondetails.QuestionDetailsViewMvcImplImpl;
import com.example.android_mvc.screens.questionslist.questionlistitem.QuestionListItemViewMvc;
import com.example.android_mvc.screens.questionslist.questionlistitem.QuestionListItemViewMvcImpl;
import com.example.android_mvc.screens.questionslist.QuestionsListViewMvc;
import com.example.android_mvc.screens.questionslist.QuestionsListViewMvcImpl;

import androidx.annotation.Nullable;

public class ViewMvcFactory {

    private final LayoutInflater mInflater;
    private final NavDrawerHelper mNavDrawerHelper;

    public ViewMvcFactory(LayoutInflater mInflater,NavDrawerHelper navDrawerHelper) {
        this.mInflater = mInflater;
        this.mNavDrawerHelper = navDrawerHelper;
    }

    public QuestionsListViewMvc getQuestionListViewMvc(@Nullable ViewGroup parent) {
        return new QuestionsListViewMvcImpl(mInflater, parent, this, mNavDrawerHelper);
    }

    public QuestionListItemViewMvc getQuestionListItemViewMvc(@Nullable ViewGroup parent) {
        return new QuestionListItemViewMvcImpl(mInflater, parent);
    }

    public QuestionDetailsViewMvc getQuestionDetailsViewMvc(@Nullable ViewGroup parent) {
        return new QuestionDetailsViewMvcImplImpl(mInflater, parent, this);
    }

    public ToolbarViewMvc getToolbarViewMvc(@Nullable ViewGroup parent) {
        return new ToolbarViewMvc(mInflater, parent);
    }

    public NavDrawerViewMvc getNavDrawerViewMvc(@Nullable ViewGroup parent) {
        return new NavDrawerViewMvcImpl(mInflater, parent);
    }
}
