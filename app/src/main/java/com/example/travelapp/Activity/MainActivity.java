package com.example.travelapp.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.travelapp.Fragment.HomeFragment;
import com.example.travelapp.Fragment.MapFragment;
import com.example.travelapp.Fragment.WishListFragment;
import com.example.travelapp.PreferenceSettings;
import com.example.travelapp.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private PreferenceSettings mPreferenceSettings;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPreferenceSettings = new PreferenceSettings(MainActivity.this);
        drawer = (DrawerLayout)
                findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        View headerView = navigationView.getHeaderView(0);
        TextView mTextUsername = (TextView) headerView.findViewById(R.id.txtUsername);
        mTextUsername.setText(mPreferenceSettings.getUserName());
        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(this, drawer, toolbar,
                        R.string.navigation_drawer_open,
                        R.string.navigation_drawer_close);
        if (drawer != null) {
            drawer.addDrawerListener(toggle);
        }
        toggle.syncState();

        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }
        displaySelectedScreen(R.id.nav_home, navigationView.getMenu().getItem(0));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        displaySelectedScreen(item.getItemId(), item);
        return true;
    }

    private void displaySelectedScreen(int itemId, MenuItem item) {
        Fragment fragment = null;
        switch (itemId) {
            case R.id.nav_home:

                fragment = new HomeFragment();

                break;
            case R.id.nav_wishlist:
                fragment = new WishListFragment();

                break;
            case R.id.nav_map:
                fragment = new MapFragment();

                break;
            case R.id.nav_logout:
                logout();
                break;

        }
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();
        }

        toolbar.setTitle(item.getTitle());
        this.drawer.closeDrawer(GravityCompat.START);
    }

    public void logout() {
        new AlertDialog.Builder(MainActivity.this).setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Logout").setMessage("Are you sure you want to Logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPreferenceSettings.logoutUser();
                        finish();

                    }
                }).setNegativeButton("No", null).show();
    }
}