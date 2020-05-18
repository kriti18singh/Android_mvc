package com.example.android_mvc.screens.questiondetails;

import com.example.android_mvc.questions.QuestionDetails;
import com.example.android_mvc.screens.common.ViewMvc;

public interface QuestionDetailsViewMvc extends ViewMvc {

    void bindQuestionDetails(QuestionDetails questionDetails);

    void showProgressIndication();

    void hideProgressIndication();
}
