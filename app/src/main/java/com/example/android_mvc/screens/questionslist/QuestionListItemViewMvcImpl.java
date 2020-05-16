package com.example.android_mvc.screens.questionslist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android_mvc.R;
import com.example.android_mvc.questions.Question;
import com.example.android_mvc.screens.common.BaseObservableViewMvc;

public class QuestionListItemViewMvcImpl extends BaseObservableViewMvc<QuestionListItemViewMvc.Listener>
        implements QuestionListItemViewMvc {

    private final TextView mTxtTitle;
    private Question mQuestion;

    public QuestionListItemViewMvcImpl(LayoutInflater inflater, ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.layout_question_list_item, parent , false));
        mTxtTitle = findViewById(R.id.txt_title);
        getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Listener l : getListeners()) {
                    l.onQuestionClicked(mQuestion);
                }
            }
        });
    }

    @Override
    public void bindQuestion(Question question) {
        mQuestion = question;
        mTxtTitle.setText(question.getTitle());
    }
}
