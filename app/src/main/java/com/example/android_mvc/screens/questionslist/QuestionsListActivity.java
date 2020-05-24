package com.example.android_mvc.screens.questionslist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.android_mvc.R;
import com.example.android_mvc.screens.common.controllers.BackPressedListener;
import com.example.android_mvc.screens.common.controllers.BaseActivity;

import androidx.fragment.app.FragmentTransaction;

public class QuestionsListActivity extends BaseActivity {

    public static void startClearTop(Context context) {
        Intent intent = new Intent(context, QuestionsListActivity.class);
        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    private BackPressedListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_content_frame);
        QuestionsListFragment fragment;
        if(savedInstanceState == null) {
            //no recreation, fresh activity
            fragment  = new QuestionsListFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.content_frame, fragment).commit();
        } else {
            fragment = (QuestionsListFragment) getSupportFragmentManager().findFragmentById(R.id.frame_content);
        }
        mListener = fragment;
    }

    @Override
    public void onBackPressed() {
        if(!mListener.onBackPressed()) {
            super.onBackPressed();
        }
    }
}
