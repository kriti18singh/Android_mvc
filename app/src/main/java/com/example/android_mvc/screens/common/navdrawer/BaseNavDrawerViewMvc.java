package com.example.android_mvc.screens.common.navdrawer;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.android_mvc.R;
import com.example.android_mvc.screens.common.views.BaseObservableViewMvc;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;

public abstract class BaseNavDrawerViewMvc<ListenerType> extends BaseObservableViewMvc<ListenerType>
        implements NavDrawerViewMvc {

    private final DrawerLayout mDrawerLayout;
    private final FrameLayout mFrameLayout;
    private final NavigationView mNavigationView;

    public BaseNavDrawerViewMvc(LayoutInflater inflater, ViewGroup parent) {
        super.setRootView(inflater.inflate(R.layout.layout_drawer, parent, false));
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mFrameLayout = findViewById(R.id.frame_content);
        mNavigationView = findViewById(R.id.nav_view);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawers();
                if(item.getItemId() == R.id.drawer_menu_questions_list) {
                    //convert call to abstract method with Enum as parameter
                    onDrawerItemClicked(DrawerItems.QUESTIONS_LIST);
                }
                return false;
            }
        });
    }

    protected abstract void onDrawerItemClicked(DrawerItems drawerItem);

    @Override
    protected void setRootView(View rootView) {
        mFrameLayout.addView(rootView);
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
    public boolean isDrawerOpen() {
        return mDrawerLayout.isDrawerOpen(Gravity.START);
    }
}
