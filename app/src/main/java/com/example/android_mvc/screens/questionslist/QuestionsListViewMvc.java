package com.example.android_mvc.screens.questionslist;

import com.example.android_mvc.screens.common.views.ObservableViewMvc;
import com.example.android_mvc.questions.Question;

import java.util.List;

public interface QuestionsListViewMvc extends ObservableViewMvc<QuestionsListViewMvc.Listener> {

    public interface Listener {
        void onQuestionClicked(Question question);

        void onQuestionsListItemClicked();
    }

    void bindQuestions(List<Question> questions);

}
