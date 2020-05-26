package com.example.android_mvc.screens.common.controllers;

public interface BackPressedDispatcher {
    void registerListener(BackPressedListener listener);
    void unregisterListener(BackPressedListener listener);
}
