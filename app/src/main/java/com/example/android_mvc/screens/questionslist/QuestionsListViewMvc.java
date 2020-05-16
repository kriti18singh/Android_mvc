package com.example.android_mvc.screens.questionslist;

import com.example.android_mvc.common.ViewMvc;
import com.example.android_mvc.questions.Question;

import java.util.List;

interface QuestionsListViewMvc extends ViewMvc {

    public interface Listener {
        void onQuestionClicked(Question question);
    }

    void registerListener(Listener l);

    void unregisterListener(Listener l);

    void bindQuestions(List<Question> questions);

}
