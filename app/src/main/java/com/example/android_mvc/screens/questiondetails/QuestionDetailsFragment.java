package com.example.android_mvc.screens.questiondetails;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android_mvc.common.dependencyinjection.ControllerCompositionRoot;
import com.example.android_mvc.common.permissions.PermissionManager;
import com.example.android_mvc.questions.FetchQuestionDetailsUsecase;
import com.example.android_mvc.questions.QuestionDetails;
import com.example.android_mvc.screens.common.controllers.BaseFragment;
import com.example.android_mvc.screens.common.dialogs.DialogEventBus;
import com.example.android_mvc.screens.common.dialogs.DialogsManager;
import com.example.android_mvc.screens.common.dialogs.promptdialog.PromptDialogEvent;
import com.example.android_mvc.screens.common.main.MainActivity;
import com.example.android_mvc.screens.common.screensnavigation.ScreensNavigator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class QuestionDetailsFragment extends BaseFragment implements
        FetchQuestionDetailsUsecase.Listener, QuestionDetailsViewMvc.Listener, DialogEventBus.Listener, PermissionManager.Listener {

    private static final String ARG_QUESTION_ID = "ARG_QUESTION_ID";
    private static final String DIALOG_NETWORK_ERROR_TAG = "dialog_network_error_tag";
    private static final String SAVED_STATE_SCREEN_STATE = "saved_state_screen_state";
    private static final int REQUEST_CODE = 5009;


    public static Fragment newInstance(String questionId) {
        Bundle args = new Bundle();
        args.putString(ARG_QUESTION_ID, questionId);
        QuestionDetailsFragment fragment = new QuestionDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private enum ScreenState {
        IDLE, QUESTION_DETAILS_SHOWN, NETWORK_ERROR
    }

    private QuestionDetailsViewMvc mQuestionDetailsViewMvc;
    private FetchQuestionDetailsUsecase mFetchQuestionDetailsUsecase;
    private ScreensNavigator mScreensNavigator;
    private DialogsManager mDialogsManager;
    private DialogEventBus mDialogEventBus;
    private PermissionManager mPermissionManager;

    private ScreenState mScreenState = ScreenState.IDLE;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null) {
            mScreenState = (ScreenState) savedInstanceState.getSerializable(SAVED_STATE_SCREEN_STATE);
        }
        mPermissionManager = getCompositionRoot().getPermissionManager();
        mFetchQuestionDetailsUsecase = ((MainActivity)getActivity()).getCompositionRoot().getFetchQuestionDetailsUsecase();
        mScreensNavigator = ((MainActivity)getActivity()).getCompositionRoot().getScreensNavigator();
        mDialogsManager = ((MainActivity)getActivity()).getCompositionRoot().getDialogsManager();
        mDialogEventBus = ((MainActivity)getActivity()).getCompositionRoot().getDialogEventBus();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mQuestionDetailsViewMvc = getCompositionRoot().getMvcFactory().getQuestionDetailsViewMvc(container);
        return mQuestionDetailsViewMvc.getRootView();
    }

    @Override
    public void onStart() {
        super.onStart();
        mFetchQuestionDetailsUsecase.registerListener(this);
        mQuestionDetailsViewMvc.registerListener(this);
        mDialogEventBus.registerListener(this);
        mPermissionManager.registerListener(this);

        if(!mScreenState.equals(ScreenState.NETWORK_ERROR)) {
            fetchQuestionDetailsAndNotify();
        }
    }

    private void fetchQuestionDetailsAndNotify() {
        mQuestionDetailsViewMvc.showProgressIndication();
        mFetchQuestionDetailsUsecase.fetchQuestionDetailsAndNotify(getQuestionId());
    }

    @Override
    public void onStop() {
        super.onStop();
        mFetchQuestionDetailsUsecase.unregisterListener(this);
        mQuestionDetailsViewMvc.unregisterListener(this);
        mDialogEventBus.unregisterListener(this);
        mPermissionManager.unregisterListener(this);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SAVED_STATE_SCREEN_STATE, mScreenState);
    }

    private String getQuestionId() {
        Bundle b = getArguments();
        String id = b.getString(ARG_QUESTION_ID);
        return id;
    }

    private void bindQuestion(QuestionDetails questionDetails) {
        mQuestionDetailsViewMvc.hideProgressIndication();
        mQuestionDetailsViewMvc.bindQuestionDetails(questionDetails);
    }

    @Override
    public void onQuestionDetailsFetched(QuestionDetails questionDetails) {
        bindQuestion(questionDetails);
        mScreenState = ScreenState.QUESTION_DETAILS_SHOWN;
    }

    @Override
    public void onQuestionDetailsFetchFailed() {
        mQuestionDetailsViewMvc.hideProgressIndication();
        mDialogsManager.showUsecaseErrorDialog(DIALOG_NETWORK_ERROR_TAG);
        mScreenState = ScreenState.NETWORK_ERROR;
    }

    @Override
    public void onNavigateUpClicked() {
        mScreensNavigator.navigateUp();
    }

    @Override
    public void onLocationRequested() {
        if(mPermissionManager.hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            mDialogsManager.showPermissionsGrantedDialog(null);
        } else {
            mPermissionManager.requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, REQUEST_CODE);
        }

    }

    @Override
    public void onDialogEvent(Object event) {
        if(event instanceof PromptDialogEvent) {
            switch (((PromptDialogEvent) event).getClickedButton()) {
                case POSITIVE:
                    mScreenState = ScreenState.IDLE;
                    mFetchQuestionDetailsUsecase.fetchQuestionDetailsAndNotify(getQuestionId());
                    break;
                case NEGATIVE:
                    mScreenState = ScreenState.IDLE;
                    break;
            }
        }
    }

    @Override
    public void onPermissionGranted(String permission, int requestCode) {
        mDialogsManager.showPermissionsGrantedDialog(null);
    }

    @Override
    public void onPermissionDeniedAndDontAskAgain(String permission, int requestCode) {
        mDialogsManager.showPermissionDeclinedCantAskMoreDialog(null);
    }

    @Override
    public void onPermissionDenied(String permission, int requestCode) {
        mDialogsManager.showPermissionDeclinedDialog(null);
    }
}
