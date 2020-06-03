package com.example.android_mvc.screens.questionslist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.android_mvc.R;
import com.example.android_mvc.questions.Question;
import com.example.android_mvc.screens.common.navdrawer.NavDrawerHelper;
import com.example.android_mvc.screens.common.toolbars.ToolbarViewMvc;
import com.example.android_mvc.screens.common.ViewMvcFactory;
import com.example.android_mvc.screens.common.views.BaseObservableViewMvc;

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
    private final NavDrawerHelper mNavDrawerHelper;
    private ProgressBar mProgressBar;

    public QuestionsListViewMvcImpl(LayoutInflater inflater, ViewGroup parent,
                                    ViewMvcFactory viewMvcFactory,
                                    NavDrawerHelper navDrawerHelper) {

        setRootView(inflater.inflate(R.layout.activity_questions_list, parent, false));
        mQuestionsListAdapter = new QuestionsRecyclerAdapter(this, viewMvcFactory);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mQuestionsListAdapter);

        mToolbar = findViewById(R.id.toolbar);      //viewgroup
        mToolbarViewMvc = viewMvcFactory.getToolbarViewMvc(mToolbar);
        mNavDrawerHelper = navDrawerHelper;
        mProgressBar = findViewById(R.id.progress);

        initToolbar();
    }

    private void initToolbar() {
        //toolbar set
        mToolbarViewMvc.setToolbarTitle((getString(R.string.questions_list_screen_title)));
        mToolbar.addView(mToolbarViewMvc.getRootView());
        mToolbarViewMvc.enableHamburgerButtonAndListen(new ToolbarViewMvc.HamburgerBtnClickListener() {
            @Override
            public void onHamburgerBtnClicked() {
                mNavDrawerHelper.openDrawer();
            }
        });
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

    @Override
    public void hideProgressIndication() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showProgressIndication() {
        mProgressBar.setVisibility(View.VISIBLE);
    }
}
