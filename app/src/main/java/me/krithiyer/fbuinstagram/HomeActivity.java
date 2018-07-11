package me.krithiyer.fbuinstagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

import me.krithiyer.fbuinstagram.model.Post;

public class HomeActivity extends AppCompatActivity {

    private static final String imagePath = "/storage/self/primary/DCIM/Camera/IMG_20180709_154621.jpg";
    private EditText descriptionInput;
    private Button createButton;
    private Button refreshButton;
    private Button logOutButton;
    private Button cameraButton;

    // adapter variables
    RecyclerView rvPosts;
    TimelineAdapter adapter;
    ArrayList<Post> tlPosts;

    // Query variables
    Post.Query newQuery;

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

        loadTopPosts();

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

        // refreshing the timeline
       // refreshButton.setOnClickListener(new View.OnClickListener() {
         //   @Override
           // public void onClick(View view) {
             //   loadTopPosts();
           // }
       // });

    }


    private void loadTopPosts() {
        final Post.Query postQuery = new Post.Query();
        postQuery.getTop().withUser();


        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null) {
                    tlPosts.addAll(objects);
                    adapter.notifyDataSetChanged();
                    //for (int i = 0; i < objects.size(); i++) {

                        //Log.d("HomeActivity", "Post[" + i + "] = "
                          //      + objects.get(i).getDescription()
                            //    + "\nusername = " + objects.get(i).getUser().getUsername()
                       // );
                 //   }
                } else {
                    e.printStackTrace();
                }
            }
        });
    }




}
