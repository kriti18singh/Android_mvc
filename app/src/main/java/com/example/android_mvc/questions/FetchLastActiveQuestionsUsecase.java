package com.example.android_mvc.questions;

import com.example.android_mvc.common.BaseObservable;
import com.example.android_mvc.common.Constants;
import com.example.android_mvc.networking.QuestionSchema;
import com.example.android_mvc.networking.QuestionsListResponseSchema;
import com.example.android_mvc.networking.StackoverflowApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchLastActiveQuestionsUsecase extends BaseObservable<FetchLastActiveQuestionsUsecase.Listener> {

    private final StackoverflowApi mStackoverflowApi;

    public FetchLastActiveQuestionsUsecase(StackoverflowApi mStackoverflowApi) {
        this.mStackoverflowApi = mStackoverflowApi;
    }

    public interface Listener {

        void onLastActiveQuestionsFetched(List<Question> questions);

        void onLastActiveQuestionsFetchFailed();
    }
    
    public void fetchLastActiveQuestionsAndNotify() {
        mStackoverflowApi.fetchLastActiveQuestions(Constants.QUESTIONS_LIST_PAGE_SIZE)
                .enqueue(new Callback<QuestionsListResponseSchema>() {
                    @Override
                    public void onResponse(Call<QuestionsListResponseSchema> call, Response<QuestionsListResponseSchema> response) {
                        if (response.isSuccessful()) {
                            notifySuccess(response.body().getQuestions());
                        } else {
                            notifyFailure();
                        }
                    }

                    @Override
                    public void onFailure(Call<QuestionsListResponseSchema> call, Throwable t) {
                        notifyFailure();
                    }
                } );
    }

    private void notifyFailure() {
        for(Listener l : getListeners()) {
            l.onLastActiveQuestionsFetchFailed();
        }
    }

    private void notifySuccess(List<QuestionSchema> questionSchemas) {
        List<Question> questions = new ArrayList<>(questionSchemas.size());
        for (QuestionSchema questionSchema : questionSchemas) {
            questions.add(new Question(questionSchema.getId(), questionSchema.getTitle()));
        }
        
        for(Listener l : getListeners()) {
            l.onLastActiveQuestionsFetched(questions);
        }
    }
}
