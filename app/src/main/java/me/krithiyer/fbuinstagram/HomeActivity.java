package me.krithiyer.fbuinstagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

import me.krithiyer.fbuinstagram.model.Post;

public class HomeActivity extends AppCompatActivity {

    private static final String imagePath = "/storage/self/primary/DCIM/Camera/IMG_20180709_154621.jpg";
    private EditText descriptionInput;
    private Button createButton;
    private Button refreshButton;
    private Button logOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        descriptionInput = findViewById(R.id.tvDescription);
        createButton = findViewById(R.id.btCreate);
        refreshButton = findViewById(R.id.btRefresh);
        logOutButton = findViewById(R.id.btLogOut);

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser();
                if (currentUser == null) {
                    Log.d("HomeActivity", "Logout successful!");
                    final Intent i = new Intent(HomeActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();

                }
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String description = descriptionInput.getText().toString();
                final ParseUser user = ParseUser.getCurrentUser();

                final File file = new File(imagePath);
                final ParseFile parseFile = new ParseFile(file);
                parseFile.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null) {
                            createPost(description, parseFile, user);
                        } else {
                            e.printStackTrace();
                        }

                    }
                });

            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadTopPosts();
            }
        });


    }

    private void createPost(String description, ParseFile imageFile, ParseUser user) {
       final Post newPost = new Post();
       newPost.setDescription(description);
       newPost.setImage(imageFile);
       newPost.setUser(user);

       newPost.saveInBackground(new SaveCallback() {
           @Override
           public void done(ParseException e) {
               if (e == null) {
                   Log.d("HomeActivity", "Create post success!");
               } else {
                   e.printStackTrace();
               }
           }
       });
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
