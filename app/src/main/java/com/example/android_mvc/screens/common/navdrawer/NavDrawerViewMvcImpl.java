package com.example.android_mvc.screens.common.navdrawer;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.android_mvc.R;
import com.example.android_mvc.screens.common.views.BaseObservableViewMvc;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;

public class NavDrawerViewMvcImpl extends BaseObservableViewMvc<NavDrawerViewMvc.Listener>
        implements NavDrawerViewMvc {

    private final DrawerLayout mDrawerLayout;
    private final FrameLayout mFrameLayout;
    private final NavigationView mNavigationView;

    public NavDrawerViewMvcImpl(LayoutInflater inflater, ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.layout_drawer, parent, false));
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mFrameLayout = findViewById(R.id.frame_content);
        mNavigationView = findViewById(R.id.nav_view);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawers();
                if(item.getItemId() == R.id.drawer_menu_questions_list) {
                    //convert call to abstract method with Enum as parameter
                    for(Listener listener : getListeners()) {
                         listener.onQuestionListItemClicked();
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void openDrawer() {
        mDrawerLayout.openDrawer(Gravity.START);
    }

    @Override
    public void closeDrawer() {
        mDrawerLayout.closeDrawers();
    }

    @Override
    public FrameLayout getFragmentFrame() {
        return mFrameLayout;
    }

    @Override
    public boolean isDrawerOpen() {
        return mDrawerLayout.isDrawerOpen(Gravity.START);
    }
}
