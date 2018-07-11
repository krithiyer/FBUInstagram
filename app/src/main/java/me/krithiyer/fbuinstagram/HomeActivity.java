package me.krithiyer.fbuinstagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

import me.krithiyer.fbuinstagram.model.Post;

public class HomeActivity extends AppCompatActivity {

    // adapter variables
    RecyclerView rvPosts;
    TimelineAdapter adapter;
    ArrayList<Post> tlPosts;
    SwipeRefreshLayout swipeContainer;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // initialize arraylist of posts
        tlPosts = new ArrayList<>();
        // initialize adapter
        adapter = new TimelineAdapter(tlPosts);

        // resolve recyclerview
        rvPosts = findViewById(R.id.rvPosts);
        rvPosts.setLayoutManager(new LinearLayoutManager(this));
        rvPosts.setAdapter(adapter);

        // loading timeline
        loadTopPosts();

        // setting refresh on pull down
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadTopPosts();
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

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
                        loadTopPosts();



                }
                return false;
            }
        });


        // logging out
        //logOutButton.setOnClickListener(new View.OnClickListener() {
          //  @Override
            //public void onClick(View view) {
              //  ParseUser.logOut();
               // ParseUser currentUser = ParseUser.getCurrentUser();
               // if (currentUser == null) {
                 //   Log.d("HomeActivity", "Logout successful!");
                   // final Intent i = new Intent(HomeActivity.this, MainActivity.class);
                   // startActivity(i);
                   // finish();

               // }
           // }
       // });

    }


    private void loadTopPosts() {
        tlPosts.clear();
        final Post.Query postQuery = new Post.Query();
        postQuery.getTop().withUser();


        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null) {
                    for(int i = objects.size() - 1; i > -1; i--) {
                        tlPosts.add(objects.get(i));
                    }
                    adapter.notifyDataSetChanged();
                    swipeContainer.setRefreshing(false);
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    public void clear() {
        tlPosts.clear();
        adapter.notifyDataSetChanged();
    }

}
