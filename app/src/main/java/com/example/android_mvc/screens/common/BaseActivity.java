package com.example.android_mvc.screens.common;

import com.example.android_mvc.CustomApplication;
import com.example.android_mvc.common.dependencyinjection.CompositionRoot;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    public CompositionRoot getCompositionRoot() {
        return ((CustomApplication) getApplication()).getCompositionRoot();
    }
}
