package com.example.android_mvc.screens.questionslist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android_mvc.R;
import com.example.android_mvc.questions.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionListItemViewMvcImpl implements QuestionListItemViewMvc {

    private View mRootView;
    private final TextView mTxtTitle;
    private Question mQuestion;

    private final List<Listener> mListeners = new ArrayList<>(1);

    public QuestionListItemViewMvcImpl(LayoutInflater inflater, ViewGroup parent) {
        mRootView = inflater.inflate(R.layout.layout_question_list_item, parent , false);
        mTxtTitle = findViewById(R.id.txt_title);
        getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Listener l : mListeners) {
                    l.onQuestionClicked(mQuestion);
                }
            }
        });
    }

    private <T extends View> T findViewById(int id) {
        return getRootView().findViewById(id);
    }

    @Override
    public View getRootView() {
        return mRootView;
    }

    @Override
    public void register(Listener listener) {
        mListeners.add(listener);
    }

    @Override
    public void unregister(Listener listener) {
        mListeners.remove(listener);
    }

    @Override
    public void bindQuestion(Question question) {
        mQuestion = question;
        mTxtTitle.setText(question.getTitle());
    }
}
