package com.example.android_mvc.screens.questionslist;

import com.example.android_mvc.common.ViewMvc;
import com.example.android_mvc.questions.Question;

public interface QuestionListItemViewMvc extends ViewMvc {

    public interface Listener {
        void onQuestionClicked(Question question);
    }
    void register(Listener listener);
    void unregister(Listener listener);
    void bindQuestion(Question question);
}
