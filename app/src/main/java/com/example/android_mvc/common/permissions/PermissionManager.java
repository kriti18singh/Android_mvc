package com.example.android_mvc.common.permissions;

import android.app.Activity;
import android.content.pm.PackageManager;

import com.example.android_mvc.common.BaseObservable;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionManager extends BaseObservable<PermissionManager.Listener> {

    public interface Listener {

        void onPermissionGranted(String permission, int requestCode);

        void onPermissionDeniedAndDontAskAgain(String permission, int requestCode);

        void onPermissionDenied(String permission, int requestCode);
    }

    private final Activity mActivity;

    public PermissionManager(Activity activity) {
        this.mActivity = activity;
    }

    public boolean hasPermission(String permission) {
        return ContextCompat.checkSelfPermission(mActivity, permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermission(String permission, int requestCode) {
        ActivityCompat.requestPermissions(mActivity,
                new String[]{permission},
                requestCode
        );
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            notifyPermissionGranted(permissions[0], requestCode);
        } else {
            if(ActivityCompat.shouldShowRequestPermissionRationale(
                    mActivity,
                    permissions[0])) {
                notifyPermissionDenied(permissions[0], requestCode);
            } else {
                notifyPermissionDeniedAndDontAskAgain(permissions[0], requestCode);
            }
        }
    }

    private void notifyPermissionDeniedAndDontAskAgain(String permission, int requestCode) {
        for(Listener listener : getListeners()) {
             listener.onPermissionDeniedAndDontAskAgain(permission, requestCode);
        }
    }

    private void notifyPermissionDenied(String permission, int requestCode) {
        for(Listener listener : getListeners()) {
             listener.onPermissionDenied(permission, requestCode);
        }
    }

    private void notifyPermissionGranted(String permission, int requestCode) {
        for(Listener listener : getListeners()) {
             listener.onPermissionGranted(permission, requestCode);
        }
    }

}
