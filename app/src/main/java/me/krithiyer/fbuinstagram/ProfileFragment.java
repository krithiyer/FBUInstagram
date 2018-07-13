package me.krithiyer.fbuinstagram;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import me.krithiyer.fbuinstagram.model.Post;

import static com.parse.ParseUser.getCurrentUser;

public class ProfileFragment extends Fragment {

    // listener set-up
    private OnItemSelectedListener listener;

    // Define the events that the fragment will use to communicate
    public interface OnItemSelectedListener {
        // This can be any number of events to be sent to the activity
        public void onLaunchCamera();

    }

    // Layout variables
    RecyclerView rvProfilePosts;
    ProfileAdapter userAdapter;
    // initialize arraylist of posts
    ArrayList<Post> profPosts;
    SwipeRefreshLayout swipeContainerProf;
    // TextView tvCaption;

    Button logout;
    Button createProfPic;
    ImageView ibCreateProfPic;
    TextView profFGUsername;
    ImageView userPic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View userView =  inflater.inflate(R.layout.fragment_profile, parent, false);
        profPosts = new ArrayList<>();
        userAdapter = new ProfileAdapter(profPosts);
        // loading recyclerview
        rvProfilePosts = userView.findViewById(R.id.rvProfileLayout);
        GridLayoutManager profUserView = new GridLayoutManager(userView.getContext(),3);
        rvProfilePosts.setLayoutManager(profUserView);
        rvProfilePosts.setAdapter(userAdapter);

        // loading posts
        loadUserPosts(ParseUser.getCurrentUser());

        // setting refresh on pull down
        return userView;


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // adding profile picture
        ibCreateProfPic = (ImageView) view.findViewById(R.id.ibProfilePhoto);
        ParseFile profPicTemp = getCurrentUser().getParseFile("profileImage");
                if (profPicTemp != null) {
                    Glide.with(view.getContext())
                            .load(profPicTemp.getUrl())
                            .into(ibCreateProfPic);
                }
        profFGUsername = (TextView) view.findViewById(R.id.tvFGProfUser);
        profFGUsername.setText(ParseUser.getCurrentUser().getUsername());
        createProfPic = (Button) view.findViewById(R.id.btAddProfilePhoto);
        createProfPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onLaunchCamera();
            }
        });

        // logging out
        logout = (Button) view.findViewById(R.id.btFGLogOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                ParseUser currentUser = getCurrentUser();
                if (currentUser == null) {
                    HomeActivity activity = (HomeActivity) getContext();
                    Log.d("HomeActivity", "Logging out...");
                    activity.logout();
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemSelectedListener) {
            listener = (OnItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
    }

    private void loadUserPosts(ParseUser currentUser) {
        final Post.Query quey = new Post.Query();
        quey.whereEqualTo("user", currentUser);

        quey.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null) {
                    for(int i = objects.size() - 1; i > -1; i--) {
                        profPosts.add(objects.get(i));
                    }
                    userAdapter.notifyDataSetChanged();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}
