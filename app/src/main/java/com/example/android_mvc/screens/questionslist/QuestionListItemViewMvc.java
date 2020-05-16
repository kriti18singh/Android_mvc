package com.example.android_mvc.screens.questionslist;

import android.view.View;

import com.example.android_mvc.questions.Question;

public interface QuestionListItemViewMvc {

    public interface Listener {
        void onQuestionClicked(Question question);
    }
    public View getRootView();
    public void register(Listener listener);
    public void unregister(Listener listener);
    public void bindQuestion(Question question);
}
