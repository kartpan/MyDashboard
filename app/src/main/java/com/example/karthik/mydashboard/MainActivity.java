package com.example.karthik.mydashboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String PREFS_NAME = "MySettings";
    public boolean schduledRestart = false;
    protected TypedArray avatar;
    protected RadioGroup radiotoggle;
    protected SharedPreferences sharedPref;
    protected int themeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE);
        reloadTheme();
        setTheme(getThemeId());
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //To be cleaned up
        radiotoggle = (RadioGroup)findViewById(R.id.toggleGroup);

        int viewId = sharedPref.getInt(getString(R.string.key_theme),0);

        for (int j = 0; j < radiotoggle.getChildCount(); j++) {
            final ToggleButton view = (ToggleButton) radiotoggle.getChildAt(j);
            if(view.getId() == viewId) {
                view.setChecked(true);
            }
        }

        radiotoggle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final RadioGroup radioGroup, final int i) {
                for (int j = 0; j < radioGroup.getChildCount(); j++) {
                    final ToggleButton view = (ToggleButton) radioGroup.getChildAt(j);
                    view.setChecked(view.getId() == i);
                }
            }
        });

        avatar = getResources().obtainTypedArray(R.array.avatars);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);


                String username = sharedPref.getString(getString(R.string.key_name),getResources().getString(R.string.default_user));
                String emailAddress = sharedPref.getString(getString(R.string.key_email),"");
                int user_avatar = sharedPref.getInt(getString(R.string.key_avatar),0);

                TextView user = (TextView) findViewById(R.id.sd_username);
                TextView email = (TextView) findViewById(R.id.sd_email_address);
                ImageView avatar_img = (ImageView) findViewById(R.id.sd_avatar);

                user.setText(username);
                email.setText(emailAddress);
                avatar_img.setImageResource(avatar.getResourceId(user_avatar,R.drawable.avatar_angry_bird));

                try {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
                        // Do something for lollipop and above versions

                        Window window = getWindow();

                        // clear FLAG_TRANSLUCENT_STATUS flag:
                        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

                        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

                        // finally change the color to any color with transparency

                        LinearLayout sidenav = (LinearLayout) findViewById(R.id.sidenavbg);

                        if(getThemeId()==R.style.AppTheme_NoActionBar){
                            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkwithTransparency));
                            sidenav.setBackground(getResources().getDrawable(R.drawable.side_nav_bar));}
                        else if (getThemeId()==R.style.Theme_App_Green_NoActionBar){
                            window.setStatusBarColor(getResources().getColor(R.color.colorGreenPrimaryDarkwithTransparency));
                            sidenav.setBackground(getResources().getDrawable(R.drawable.side_nav_bar_green));}
                        else if (getThemeId()==R.style.Theme_App_Blue_NoActionBar){
                            window.setStatusBarColor(getResources().getColor(R.color.colorBluePrimaryDarkwithTransparency));
                            sidenav.setBackground(getResources().getDrawable(R.drawable.side_nav_bar_blue));}
                        else if (getThemeId()==R.style.Theme_App_Orange_NoActionBar){
                            window.setStatusBarColor(getResources().getColor(R.color.colorOrangePrimaryDarkwithTransparency));
                            sidenav.setBackground(getResources().getDrawable(R.drawable.side_nav_bar_orange));}
                        else if (getThemeId()==R.style.Theme_App_Pink_NoActionBar){
                            window.setStatusBarColor(getResources().getColor(R.color.colorPinkPrimaryDarkwithTransparency));
                            sidenav.setBackground(getResources().getDrawable(R.drawable.side_nav_bar_pink));}
                    }

                } catch (Exception e) {

                    e.printStackTrace();

                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                try {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        // Do something for lollipop and above versions

                        Window window = getWindow();

                        // clear FLAG_TRANSLUCENT_STATUS flag:
                        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

                        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

                        // finally change the color again to dark
                        if(getThemeId()==R.style.AppTheme_NoActionBar)
                            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
                        else if (getThemeId()==R.style.Theme_App_Green_NoActionBar)
                            window.setStatusBarColor(getResources().getColor(R.color.colorGreenPrimaryDark));
                        else if (getThemeId()==R.style.Theme_App_Blue_NoActionBar)
                            window.setStatusBarColor(getResources().getColor(R.color.colorBluePrimaryDark));
                        else if (getThemeId()==R.style.Theme_App_Orange_NoActionBar)
                            window.setStatusBarColor(getResources().getColor(R.color.colorOrangePrimaryDark));
                        else if (getThemeId()==R.style.Theme_App_Pink_NoActionBar)
                            window.setStatusBarColor(getResources().getColor(R.color.colorPinkPrimaryDark));

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            // Dashboard view
        } else if (id == R.id.nav_settings) {

            BottomSheetSettings bottomSheet = new BottomSheetSettings();
            bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onToggle(View view) {
        ((RadioGroup)view.getParent()).check(view.getId());
        ToggleButton tb = (ToggleButton) view;
        Log.w("Delete:", "Color:" + view.getId());
        SharedPreferences.Editor editor = sharedPref.edit();
        if(tb.isChecked()) {
            editor.putInt(getString(R.string.key_theme), view.getId());
        }else{
            editor.putInt(getString(R.string.key_theme), 0);
        }
        reloadTheme();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
        schduledRestart = true;
        editor.commit();


    }

    public void reloadTheme()
    {
        int themeSetting = sharedPref.getInt(getString(R.string.key_theme),0);

        Log.w("Delete:", "Entered reload theme  - " + themeSetting );

        if(themeSetting == 0)
            themeId = R.style.AppTheme_NoActionBar;
        else if(themeSetting == 2131624066)
            themeId = R.style.Theme_App_Green_NoActionBar;
        else if(themeSetting == 2131624067)
            themeId = R.style.Theme_App_Blue_NoActionBar;
        else if(themeSetting == 2131624068)
            themeId = R.style.Theme_App_Orange_NoActionBar;
        else if(themeSetting == 2131624069)
            themeId = R.style.Theme_App_Pink_NoActionBar;
    }

    public int getThemeId()
    {
        return themeId;
    }

}
