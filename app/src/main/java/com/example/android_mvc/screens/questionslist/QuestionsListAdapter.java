package com.example.android_mvc.screens.questionslist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.example.android_mvc.questions.Question;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class QuestionsListAdapter extends ArrayAdapter<Question>
        implements QuestionListItemViewMvc.Listener {

    private final OnQuestionClickListener mOnQuestionClickListener;

    public interface OnQuestionClickListener {
        void onQuestionClicked(Question question);
    }

    public QuestionsListAdapter(Context context,
                                OnQuestionClickListener onQuestionClickListener) {
        super(context, 0);
        mOnQuestionClickListener = onQuestionClickListener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            QuestionListItemViewMvc viewMvc = new QuestionListItemViewMvcImpl(
                    LayoutInflater.from(getContext()) ,
                    parent
            );
            viewMvc.register(this);
            convertView = viewMvc.getRootView();
            convertView.setTag(viewMvc);
        }

        final Question question = getItem(position);

        // bind the data to views
        QuestionListItemViewMvc viewMvc = (QuestionListItemViewMvc) convertView.getTag();
        viewMvc.bindQuestion(question);

        // set listener
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onQuestionClicked(question);
            }
        });

        return convertView;
    }

    public void onQuestionClicked(Question question) {
        mOnQuestionClickListener.onQuestionClicked(question);
    }
}
