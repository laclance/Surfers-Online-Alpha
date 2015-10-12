package com.coesolutions.surfersonline.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.coesolutions.surfersonline.adapter.PagerAdapter;
import com.coesolutions.surfersonline.fragment.FriendsFragment;
import com.coesolutions.surfersonline.fragment.GroupsFragment;
import com.coesolutions.surfersonline.fragment.HomeFragment;
import com.coesolutions.surfersonline.R;
import com.coesolutions.surfersonline.fragment.RootFragment;
import com.coesolutions.surfersonline.fragment.SurfAreasFragment;
import com.coesolutions.surfersonline.fragment.SurfSpotsFragment;
import com.coesolutions.surfersonline.helper.ManagePreferences;

public class MainActivity extends AppCompatActivity {
    private static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    private boolean userLearnedDrawer;

    private DrawerLayout drawerLayout;
    private ViewPager viewPager;
    private PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_action_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null)
            setupDrawerContent(navigationView);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null)
            setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        if (tabLayout != null)
            tabLayout.setupWithViewPager(viewPager);

        if (savedInstanceState == null) {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    userLearnedDrawer = Boolean.valueOf(ManagePreferences.readFromPreferences(getApplicationContext(), KEY_USER_LEARNED_DRAWER, "false"));
                    if (!userLearnedDrawer) {
                        drawerLayout.openDrawer(GravityCompat.START);
                        ManagePreferences.saveToPreferences(getApplicationContext(), KEY_USER_LEARNED_DRAWER, "true");
                    }
                }
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                final ActionBar ab = getSupportActionBar();
                if (position == 3){
                    FragmentManager fm = getSupportFragmentManager();
                    if (fm.getBackStackEntryCount() != 0) {
                        String currentFragmentTag = fm.getBackStackEntryAt(fm.getBackStackEntryCount() - 1).getName();
                        if (currentFragmentTag.equals("SurfSpots") && ab != null)
                            ab.setHomeAsUpIndicator(R.drawable.ic_action_back);
                    }
                }
                else{
                    if (ab != null)
                        ab.setHomeAsUpIndicator(R.drawable.ic_action_menu);
                }
                if (toolbar.getMenu().hasVisibleItems()) {
                    switch (position) {
                        case 3:
                            toolbar.getMenu().getItem(0).setVisible(true);
                            break;
                        default:
                            toolbar.getMenu().getItem(0).setVisible(false);
                    }
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fm = getSupportFragmentManager();
        if (item.getItemId() == android.R.id.home) {
            if (fm.getBackStackEntryCount() <= 1)
                drawerLayout.openDrawer(GravityCompat.START);
            else
                onBackPressed();
        }
        else {
            switch (item.getTitle().toString()) {
                case "Log out":
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                    return true;
                case "Add":
                    switch (viewPager.getCurrentItem()) {
                        case 1 :
                            GroupsFragment groups = (GroupsFragment) adapter.getItem(1) ;
                            // groups.addItem();
                            return true;
                        case 2 :
                            FriendsFragment friends = (FriendsFragment) adapter.getItem(2);
                            //friends.addItem();
                            return true;
                        case 3 :
                            Intent intent = null;
                            String currentFragmentTag = "";
                            if (fm.getBackStackEntryCount() == 0)
                                currentFragmentTag = "SurfAreas";
                            else
                                currentFragmentTag = fm.getBackStackEntryAt(fm.getBackStackEntryCount() - 1).getName();

                            switch (currentFragmentTag) {
                                case "SurfAreas":
                                    intent = new Intent(this, EditSurfAreaActivity.class);
                                    startActivityForResult(intent, 1);
                                    return true;

                                case "SurfSpots":
                                    intent = new Intent(this, EditSurfSpotActivity.class);
                                    startActivityForResult(intent, 2);
                                    return true;
                            }
                    }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFragment(HomeFragment.newInstance(0), getString(R.string.home));
        adapter.addFragment(GroupsFragment.newInstance(1), getString(R.string.groups));
        adapter.addFragment(FriendsFragment.newInstance(2), getString(R.string.friends));
        adapter.addFragment(RootFragment.newInstance(3), getString(R.string.surfspots));
        viewPager.setAdapter(adapter);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        Fragment fragment = null;
                        String title = (String) menuItem.getTitle();

                        switch (title) {
                            case "Home":
                                //fragment = new HomeFragment();
                                break;
                            case "Groups":
                                // fragment = new GroupsFragment();
                                break;
                            case "Friends":
                                //fragment = new FriendsFragment();
                                break;
                            case "Surf Spots":
                                //fragment = new SurfSpotsFragment();
                                break;
                        }
                        drawerLayout.closeDrawers();

                       /* if (fragment != null) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.container_body, fragment).addToBackStack(null);
                            //fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                            fragmentTransaction.commit();
                        }*/
                        return true;
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode < 3){
            int id = data.getIntExtra("ID", 0);
            String name = data.getStringExtra("NAME");
            String description = data.getStringExtra("DESCRIPTION");
            FragmentManager fm = getSupportFragmentManager();
            SurfAreasFragment surfAreas;
            SurfSpotsFragment surfSpots;
            switch (requestCode){
                case 1 :
                    surfAreas = (SurfAreasFragment) fm.findFragmentByTag("SurfAreas");
                    surfAreas.addItem(name, description);
                    break;
                case 2 :
                    surfSpots = (SurfSpotsFragment) fm.findFragmentByTag("SurfSpots");
                    surfSpots.addItem(name, description);
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count > 1){
            if (count == 2 && getSupportActionBar() != null)
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_menu);
            super.onBackPressed();
        }
    }
}
