package com.example.android_mvc.screens.questiondetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.android_mvc.R;
import com.example.android_mvc.networking.QuestionDetailsResponseSchema;
import com.example.android_mvc.networking.QuestionSchema;
import com.example.android_mvc.networking.StackoverflowApi;
import com.example.android_mvc.questions.QuestionDetails;
import com.example.android_mvc.screens.common.BaseActivity;

import androidx.annotation.Nullable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionDetailsActivity extends BaseActivity {

    private static final String EXTRA_QUESTION_ID = "question_id";
    private StackoverflowApi mStackoverflowApi;
    private QuestionDetailsViewMvc mQuestionDetailsViewMvc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mStackoverflowApi = getCompositionRoot().getStackoverflowApi();
        mQuestionDetailsViewMvc = getCompositionRoot().getMvcFactory().getQuestionDetailsViewMvc(null);


        setContentView(mQuestionDetailsViewMvc.getRootView());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mQuestionDetailsViewMvc.showProgressIndication();
        fetchQuestionDetails();
    }

    private void fetchQuestionDetails() {
        mStackoverflowApi.fetchQuestionDetails(getQuestionId())
                .enqueue(new Callback<QuestionDetailsResponseSchema>() {
                    @Override
                    public void onResponse(Call<QuestionDetailsResponseSchema> call,
                                           Response<QuestionDetailsResponseSchema> response) {
                        if (response.isSuccessful()) {
                            bindQuestion(response.body().getQuestion());
                        } else {
                            networkCallFailed();
                        }
                    }

                    @Override
                    public void onFailure(Call<QuestionDetailsResponseSchema> call, Throwable t) {
                        networkCallFailed();
                    }
                } );
    }


    private String getQuestionId() {
        return getIntent().getStringExtra(EXTRA_QUESTION_ID);
    }

    public static void start(Context context, String questionId) {
        Intent intent = new Intent(context, QuestionDetailsActivity.class);
        intent.putExtra(EXTRA_QUESTION_ID, questionId);
        context.startActivity(intent);
    }


    private void networkCallFailed() {
        mQuestionDetailsViewMvc.hideProgressIndication();
        Toast.makeText(this, getString(R.string.error_network_call_failed), Toast.LENGTH_LONG).show();
    }

    private void bindQuestion(QuestionSchema question) {
        mQuestionDetailsViewMvc.hideProgressIndication();
        mQuestionDetailsViewMvc.bindQuestionDetails(
                new QuestionDetails(
                        question.getId(),
                        question.getTitle(),
                        question.getBody()
                )
        );
    }
}
