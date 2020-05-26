package com.example.android_mvc.screens.common.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.android_mvc.R;
import com.example.android_mvc.screens.common.controllers.BackPressedDispatcher;
import com.example.android_mvc.screens.common.controllers.BackPressedListener;
import com.example.android_mvc.screens.common.controllers.BaseActivity;
import com.example.android_mvc.screens.common.controllers.FragmentFrameWrapper;
import com.example.android_mvc.screens.questionslist.QuestionsListFragment;

import java.util.HashSet;
import java.util.Set;

import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends BaseActivity implements BackPressedDispatcher, FragmentFrameWrapper {

    public static void startClearTop(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    private final Set<BackPressedListener> mListeners = new HashSet<>();

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
        }
    }

    @Override
    public void onBackPressed() {
        boolean isBackPressConsumed = false;
        for(BackPressedListener l : mListeners) {
            if(l.onBackPressed()) {
                isBackPressConsumed = true;
            }
        }
        if(!isBackPressConsumed) {
            super.onBackPressed();
        }
    }

    @Override
    public void registerListener(BackPressedListener listener) {
        mListeners.add(listener);
    }

    @Override
    public void unregisterListener(BackPressedListener listener) {
        mListeners.remove(listener);
    }

    @Override
    public FrameLayout getFragmentFrame() {
        return findViewById(R.id.content_frame);
    }
}
