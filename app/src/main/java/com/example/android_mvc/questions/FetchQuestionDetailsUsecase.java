package com.example.android_mvc.questions;

import com.example.android_mvc.common.BaseObservable;
import com.example.android_mvc.networking.questions.QuestionDetailsResponseSchema;
import com.example.android_mvc.networking.questions.QuestionSchema;
import com.example.android_mvc.networking.StackoverflowApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchQuestionDetailsUsecase extends BaseObservable<FetchQuestionDetailsUsecase.Listener> {

    private final StackoverflowApi mStackoverflowApi;

    public FetchQuestionDetailsUsecase(StackoverflowApi mStackoverflowApi) {
        this.mStackoverflowApi = mStackoverflowApi;
    }

    public interface Listener {
        void onQuestionDetailsFetched(QuestionDetails questionDetails);

        void onQuestionDetailsFetchFailed();
    }

    //means this method is async (convention)
    public void fetchQuestionDetailsAndNotify(String questionId) {
        mStackoverflowApi.fetchQuestionDetails(questionId)
                .enqueue(new Callback<QuestionDetailsResponseSchema>() {
                    @Override
                    public void onResponse(Call<QuestionDetailsResponseSchema> call,
                                           Response<QuestionDetailsResponseSchema> response) {
                        if (response.isSuccessful()) {
                            notifySuccess(response.body().getQuestion());
                        } else {
                            notifyFailure();
                        }
                    }

                    @Override
                    public void onFailure(Call<QuestionDetailsResponseSchema> call, Throwable t) {
                        notifyFailure();
                    }
                } );
    }

    private void notifyFailure() {
        for(Listener l : getListeners()) {
            l.onQuestionDetailsFetchFailed();
        }
    }

    private void notifySuccess(QuestionSchema question) {
        for(Listener l : getListeners()) {
            l.onQuestionDetailsFetched(new QuestionDetails(
                    question.getId(),
                    question.getTitle(),
                    question.getBody()
            ));
        }
    }
}
