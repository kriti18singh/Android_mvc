package com.example.android_mvc.screens.questionslist;

import android.view.ViewGroup;

import com.example.android_mvc.questions.Question;
import com.example.android_mvc.screens.common.ViewMvcFactory;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class QuestionsRecyclerAdapter extends RecyclerView.Adapter<QuestionsRecyclerAdapter.ViewHolder>
        implements QuestionListItemViewMvc.Listener {

    private ViewMvcFactory mViewMvcFactory;
    private List<Question> mQuestions = new ArrayList<>();

    private final OnQuestionClickListener mOnQuestionClickListener;

    public interface OnQuestionClickListener {
        void onQuestionClicked(Question question);
    }

    public QuestionsRecyclerAdapter(OnQuestionClickListener listener, ViewMvcFactory viewMvcFactory) {
        mOnQuestionClickListener = listener;
        mViewMvcFactory = viewMvcFactory;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        QuestionListItemViewMvc mViewMvc;

        ViewHolder(@NonNull QuestionListItemViewMvc viewMvc) {
            super(viewMvc.getRootView());
            mViewMvc = viewMvc;
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        QuestionListItemViewMvc viewMvc = mViewMvcFactory.getQuestionListItemViewMvc(parent);
        viewMvc.registerListener(this);
        return new ViewHolder(viewMvc);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mViewMvc.bindQuestion(mQuestions.get(position));
    }

    @Override
    public int getItemCount() {
        return mQuestions.size();
    }

    @Override
    public void onQuestionClicked(Question question) {
        mOnQuestionClickListener.onQuestionClicked(question);
    }

    public void bindQuestions(List<Question> questions) {
        mQuestions = new ArrayList<>(questions);
        notifyDataSetChanged();
    }
}
