package com.example.android_mvc.common.dependencyinjection;

import android.content.Context;
import android.view.LayoutInflater;

import com.example.android_mvc.common.permissions.PermissionManager;
import com.example.android_mvc.networking.StackoverflowApi;
import com.example.android_mvc.questions.FetchLastActiveQuestionsUsecase;
import com.example.android_mvc.questions.FetchQuestionDetailsUsecase;
import com.example.android_mvc.screens.common.ViewMvcFactory;
import com.example.android_mvc.screens.common.controllers.BackPressedDispatcher;
import com.example.android_mvc.screens.common.controllers.FragmentFrameWrapper;
import com.example.android_mvc.screens.common.dialogs.DialogEventBus;
import com.example.android_mvc.screens.common.dialogs.DialogsManager;
import com.example.android_mvc.screens.common.navdrawer.NavDrawerHelper;
import com.example.android_mvc.screens.common.screensnavigation.ScreensNavigator;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

public class ControllerCompositionRoot {

    private final ActivityCompositionRoot mActivityCompositionRoot;

    public ControllerCompositionRoot(ActivityCompositionRoot mActivityCompositionRoot) {
        this.mActivityCompositionRoot = mActivityCompositionRoot;
    }

    public FragmentActivity getActivity() {
        return mActivityCompositionRoot.getActivity();
    }

    private FragmentManager getFragmentManager() {
        return getActivity().getSupportFragmentManager();
    }

    private StackoverflowApi getStackoverflowApi() {
        return mActivityCompositionRoot.getStackoverflowApi();
    }

    private LayoutInflater getInflater() {
        return LayoutInflater.from(mActivityCompositionRoot.getActivity());
    }

    public ViewMvcFactory getMvcFactory() {
        return  new ViewMvcFactory(getInflater(), getNavDrawerHelper());
    }

    private NavDrawerHelper getNavDrawerHelper() {
        return (NavDrawerHelper) getActivity();
    }

    public FetchQuestionDetailsUsecase getFetchQuestionDetailsUsecase() {
        return new FetchQuestionDetailsUsecase(getStackoverflowApi());
    }

    public FetchLastActiveQuestionsUsecase getFetchLastActiveQuestionsUsecase() {
        return new FetchLastActiveQuestionsUsecase(getStackoverflowApi());
    }

    public ScreensNavigator getScreensNavigator() {
        return new ScreensNavigator(getFragmentManager(), getFragmentFrameWrapper());
    }

    private FragmentFrameWrapper getFragmentFrameWrapper() {
        return (FragmentFrameWrapper) getActivity();
    }

    public BackPressedDispatcher getBackPressedDispatcher() {
        return (BackPressedDispatcher) getActivity();
    }

    private Context getContext() {
        return getActivity();
    }

    public DialogsManager getDialogsManager() {
        return new DialogsManager(getContext(), getFragmentManager());
    }

    public DialogEventBus getDialogEventBus() {
        return mActivityCompositionRoot.getDialogEventBus();
    }

    public PermissionManager getPermissionManager() {
        return mActivityCompositionRoot.getPermissionManager();
    }
}
