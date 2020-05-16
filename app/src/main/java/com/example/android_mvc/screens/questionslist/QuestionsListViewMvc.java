package com.example.android_mvc.screens.questionslist;

import android.view.View;

import com.example.android_mvc.questions.Question;

import java.util.List;

interface QuestionsListViewMvc {

    public interface Listener {
        void onQuestionClicked(Question question);
    }

    View getRootView();

    void registerListener(Listener l);

    void unregisterListener(Listener l);

    void bindQuestions(List<Question> questions);

}
