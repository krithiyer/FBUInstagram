package me.krithiyer.fbuinstagram;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.parse.ParseUser;

import java.io.File;
import java.util.ArrayList;

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
    //ProfileAdapter userAdapter;
    // initialize arraylist of posts
    ArrayList<Post> profPosts;
    SwipeRefreshLayout swipeContainerProf;
    // TextView tvCaption;

    // Taking profile photo variables
    public final String APP_TAG = "MyCustomApp";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    File photoFile;

    Button logout;
    Button createProfPic;
    ImageView ibCreateProfPic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_profile, parent, false);
//        profPosts = new ArrayList<>();
//        userAdapter = new ProfileAdapter(profPosts);
//        // loading recyclerview
//        rvProfilePosts = userView.findViewById(R.id.rvProfileLayout);
//        rvProfilePosts.setLayoutManager(new LinearLayoutManager(userView.getContext()));
//        rvProfilePosts.setAdapter(userAdapter);
//         tvCaption = currView.findViewById(R.id.tvTLCaption);
//
//        // loading posts
//        //loadTopPosts();
//
//        // setting refresh on pull down
//        swipeContainerProf = (SwipeRefreshLayout) userView.findViewById(R.id.swipeContainer);
//        swipeContainerProf.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                //loadTopPosts();
//            }
//        });
//        swipeContainerProf.setColorSchemeResources(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);
//        return userView;


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // adding profile picture
        ibCreateProfPic = (ImageView) view.findViewById(R.id.ibProfilePhoto);
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
}
