package com.example.android_mvc.screens.questiondetails;

import com.example.android_mvc.questions.QuestionDetails;
import com.example.android_mvc.screens.common.navdrawer.DrawerItems;
import com.example.android_mvc.screens.common.navdrawer.NavDrawerViewMvc;
import com.example.android_mvc.screens.common.views.ObservableViewMvc;

public interface QuestionDetailsViewMvc extends ObservableViewMvc<QuestionDetailsViewMvc.Listener>,
        NavDrawerViewMvc {

    public interface Listener {
        void onNavigateUpClicked();
        void onDrawerItemClicked(DrawerItems drawerItem);
    }

    void bindQuestionDetails(QuestionDetails questionDetails);

    void showProgressIndication();

    void hideProgressIndication();
}
