package me.krithiyer.fbuinstagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.parse.FindCallback;
import com.parse.ParseException;

import java.io.File;
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



    public final String APP_TAG = "MyCustomApp";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    File photoFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        //descriptionInput = findViewById(R.id.tvDescription);
        //createButton = findViewById(R.id.btCreate);
        //refreshButton = findViewById(R.id.btRefresh);
        //logOutButton = findViewById(R.id.btLogOut);
        //cameraButton = findViewById(R.id.btCamera);

        // setting navigation tasks
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.create_button:
                        Intent i = new Intent(HomeActivity.this, PostActivity.class);
                        startActivity(i);
                        return true;

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
                    for (int i = 0; i < objects.size(); i++) {
                        Log.d("HomeActivity", "Post[" + i + "] = "
                                + objects.get(i).getDescription()
                                + "\nusername = " + objects.get(i).getUser().getUsername()
                        );
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
    }



}
