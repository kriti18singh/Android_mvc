package com.example.android_mvc.screens.questiondetails;

import com.example.android_mvc.questions.QuestionDetails;
import com.example.android_mvc.screens.common.views.ObservableViewMvc;

public interface QuestionDetailsViewMvc extends ObservableViewMvc<QuestionDetailsViewMvc.Listener>{

    public interface Listener {
        void onNavigateUpClicked();

        void onLocationRequested();
    }

    void bindQuestionDetails(QuestionDetails questionDetails);

    void showProgressIndication();

    void hideProgressIndication();
}
