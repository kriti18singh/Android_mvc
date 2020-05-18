package com.example.android_mvc.screens.questionslist.questionlistitem;

import com.example.android_mvc.screens.common.views.ObservableViewMvc;
import com.example.android_mvc.questions.Question;

public interface QuestionListItemViewMvc extends ObservableViewMvc<QuestionListItemViewMvc.Listener> {

    public interface Listener {
        void onQuestionClicked(Question question);
    }
    void bindQuestion(Question question);
}
