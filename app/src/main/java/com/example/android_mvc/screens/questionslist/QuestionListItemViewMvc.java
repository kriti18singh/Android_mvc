package com.example.android_mvc.screens.questionslist;

import com.example.android_mvc.screens.common.ObservableViewMvc;
import com.example.android_mvc.questions.Question;

public interface QuestionListItemViewMvc extends ObservableViewMvc<QuestionListItemViewMvc.Listener> {

    public interface Listener {
        void onQuestionClicked(Question question);
    }
    void bindQuestion(Question question);
}
