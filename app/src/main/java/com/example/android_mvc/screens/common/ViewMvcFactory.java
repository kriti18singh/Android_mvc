package com.example.android_mvc.screens.common;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.android_mvc.screens.common.toolbars.ToolbarViewMvc;
import com.example.android_mvc.screens.questiondetails.QuestionDetailsViewMvc;
import com.example.android_mvc.screens.questiondetails.QuestionDetailsViewMvcImpl;
import com.example.android_mvc.screens.questionslist.questionlistitem.QuestionListItemViewMvc;
import com.example.android_mvc.screens.questionslist.questionlistitem.QuestionListItemViewMvcImpl;
import com.example.android_mvc.screens.questionslist.QuestionsListViewMvc;
import com.example.android_mvc.screens.questionslist.QuestionsListViewMvcImpl;

import androidx.annotation.Nullable;

public class ViewMvcFactory {

    private final LayoutInflater mInflater;

    public ViewMvcFactory(LayoutInflater mInflater) {
        this.mInflater = mInflater;
    }

    public QuestionsListViewMvc getQuestionListViewMvc(@Nullable ViewGroup parent) {
        return new QuestionsListViewMvcImpl(mInflater, parent, this);
    }

    public QuestionListItemViewMvc getQuestionListItemViewMvc(@Nullable ViewGroup parent) {
        return new QuestionListItemViewMvcImpl(mInflater, parent);
    }

    public QuestionDetailsViewMvc getQuestionDetailsViewMvc(@Nullable ViewGroup parent) {
        return new QuestionDetailsViewMvcImpl(mInflater, parent, this);
    }

    public ToolbarViewMvc getToolbarViewMvc(@Nullable ViewGroup parent) {
        return new ToolbarViewMvc(mInflater, parent);
    }
}
