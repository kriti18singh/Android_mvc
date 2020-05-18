package com.example.android_mvc.screens.questionslist;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.android_mvc.R;
import com.example.android_mvc.questions.Question;
import com.example.android_mvc.screens.common.toolbars.ToolbarViewMvc;
import com.example.android_mvc.screens.common.views.BaseObservableViewMvc;
import com.example.android_mvc.screens.common.ViewMvcFactory;

import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class QuestionsListViewMvcImpl extends BaseObservableViewMvc<QuestionsListViewMvc.Listener>
        implements QuestionsRecyclerAdapter.OnQuestionClickListener, QuestionsListViewMvc {

    private RecyclerView mRecyclerView;
    private QuestionsRecyclerAdapter mQuestionsListAdapter;
    private ToolbarViewMvc mToolbarViewMvc;
    private Toolbar mToolbar;

    public QuestionsListViewMvcImpl(LayoutInflater inflater, ViewGroup parent,
                                    ViewMvcFactory viewMvcFactory) {

        setRootView(inflater.inflate(R.layout.activity_questions_list, parent, false));
        mQuestionsListAdapter = new QuestionsRecyclerAdapter(this, viewMvcFactory);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mQuestionsListAdapter);

        //toolbar set
        mToolbar = findViewById(R.id.toolbar);      //viewgroup
        mToolbarViewMvc = viewMvcFactory.getToolbarViewMvc(mToolbar);
        mToolbarViewMvc.setToolbarTitle((getString(R.string.questions_list_screen_title)));
        mToolbar.addView(mToolbarViewMvc.getRootView());
    }

    @Override
    public void onQuestionClicked(Question question) {
        for(Listener l : getListeners()) {
            l.onQuestionClicked(question);
        }
    }

    @Override
    public void bindQuestions(List<Question> questions) {
        mQuestionsListAdapter.bindQuestions(questions);
    }
}
