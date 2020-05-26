package com.example.android_mvc.screens.questiondetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.android_mvc.R;
import com.example.android_mvc.common.Constants;
import com.example.android_mvc.screens.common.controllers.BackPressedListener;
import com.example.android_mvc.screens.common.controllers.BaseActivity;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

public class QuestionDetailsActivity extends BaseActivity {


    public static void start(Context context, String questionId) {
        Intent intent = new Intent(context, QuestionDetailsActivity.class);
        intent.putExtra(Constants.EXTRA_QUESTION_ID, questionId);
        context.startActivity(intent);
    }

    private BackPressedListener mListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_content_frame);
        QuestionDetailsFragment fragment;
        if(savedInstanceState == null) {
            fragment = new QuestionDetailsFragment();
            Bundle b = new Bundle();
            b.putString(Constants.EXTRA_QUESTION_ID, getQuestionId());
            fragment.setArguments(b);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.content_frame, fragment).commit();
        } else {
            fragment = (QuestionDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        }
        mListener = fragment;
    }

    private String getQuestionId() {
        return getIntent().getStringExtra(Constants.EXTRA_QUESTION_ID);
    }

    @Override
    public void onBackPressed() {
        Log.d("KRITI", "onBackPRessed in Activity");
        if(!mListener.onBackPressed()) {
            Log.d("KRITI", "onBackPRessed in Activity 111");
            super.onBackPressed();
        }
    }
}
