package me.krithiyer.fbuinstagram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

import me.krithiyer.fbuinstagram.model.Post;

public class PostActivity extends AppCompatActivity {
    //ImageView ivCameraPhoto;

    private Button createButton;

    private EditText descriptionInput;

    private String imagePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        createButton = (Button) findViewById(R.id.btCreatePost);
        descriptionInput = (EditText) findViewById(R.id.tvCreateDescription);

        imagePath = getIntent().getStringExtra("photoFilePath");

        Bitmap takenImage = BitmapFactory.decodeFile(imagePath);
        // RESIZE BITMAP, see section below
        // Load the taken image into a preview
        ImageView ivPreview = findViewById(R.id.ivCameraPhoto);
        ivPreview.setImageBitmap(takenImage);

        // creating a new post
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
                        Intent i = new Intent(PostActivity.this, HomeActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        e.printStackTrace();
                    }
                    }
                });
            }
        });
    }

    public void createPost(String description, ParseFile imageFile, ParseUser user) {
        final Post newPost = new Post();
        newPost.setDescription(description);
        newPost.setImage(imageFile);
        newPost.setUser(user);

        newPost.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("HomeActivity", "Create post success!");
                    Intent i = new Intent(PostActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

}
