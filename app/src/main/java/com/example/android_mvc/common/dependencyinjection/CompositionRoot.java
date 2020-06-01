package com.example.android_mvc.common.dependencyinjection;

import com.example.android_mvc.common.Constants;
import com.example.android_mvc.networking.StackoverflowApi;
import com.example.android_mvc.screens.common.dialogs.DialogEventBus;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompositionRoot {

    private Retrofit mRetrofit;
    private DialogEventBus mDialogEventBus;

    public StackoverflowApi getStackoverflowApi() {
        return getRetrofit().create(StackoverflowApi.class);
    }

    private Retrofit getRetrofit() {
        if(mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }

    public DialogEventBus getDialogEventBus() {
        if(mDialogEventBus == null) {
            mDialogEventBus = new DialogEventBus();
        }
        return mDialogEventBus;
    }
}
