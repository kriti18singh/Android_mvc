package com.example.android_mvc.screens.questionslist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android_mvc.R;
import com.example.android_mvc.questions.Question;
import com.example.android_mvc.screens.common.BaseViewMvc;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class QuestionsListViewMvcImpl extends BaseViewMvc implements QuestionsRecyclerAdapter.OnQuestionClickListener,
        QuestionsListViewMvc {

    private RecyclerView mRecyclerView;
    private QuestionsRecyclerAdapter mQuestionsListAdapter;

    private List<Listener> mListeners = new ArrayList<>(1);

    public QuestionsListViewMvcImpl(LayoutInflater inflater, ViewGroup parent) {
        setmRootView(inflater.inflate(R.layout.activity_questions_list, parent, false));
        mQuestionsListAdapter = new QuestionsRecyclerAdapter(inflater, this);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mQuestionsListAdapter);
    }

    @Override
    public void registerListener(Listener l) {
        mListeners.add(l);
    }

    @Override
    public void unregisterListener(Listener l) {
        mListeners.remove(l);
    }

    @Override
    public void onQuestionClicked(Question question) {
        for(Listener l : mListeners) {
            l.onQuestionClicked(question);
        }
    }

    @Override
    public void bindQuestions(List<Question> questions) {
        mQuestionsListAdapter.bindQuestions(questions);
    }
}
