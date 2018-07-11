package me.krithiyer.fbuinstagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // setting up Fragments
        final FragmentManager fragmentManager = getSupportFragmentManager();
        // define your fragments here
        final Fragment fgProfile = new ProfileFragment();
        final Fragment fgTimeline = new TimelineFragment();

        // initially loading timeline
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.flFGLayout, fgTimeline).commit();

        // establishing bottom navigation
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        // setting navigation tasks
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.create_button:
                        Intent i = new Intent(HomeActivity.this, PostActivity.class);
                        startActivity(i);
                        return true;
                    case R.id.home_buttom:
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.flFGLayout, fgTimeline).commit();
                        return true;
                    case R.id.profile_button:
                        FragmentTransaction fragmentProf = fragmentManager.beginTransaction();
                        fragmentProf.replace(R.id.flFGLayout, fgProfile).commit();


                }
                return false;
            }
        });

    }
}
