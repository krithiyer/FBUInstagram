package me.krithiyer.fbuinstagram;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import me.krithiyer.fbuinstagram.model.Post;

public class TimelineFragment extends Fragment{
    // adapter variables
    RecyclerView rvPosts;
    TimelineAdapter adapter;
    // initialize arraylist of posts
    ArrayList<Post> tlPosts;
    SwipeRefreshLayout swipeContainer;
   // TextView tvCaption;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View currView = inflater.inflate(R.layout.fragment_timeline, parent, false);
        // initialize adapter
        tlPosts = new ArrayList<>();
        adapter = new TimelineAdapter(tlPosts);
        // loading recyclerview
        rvPosts = currView.findViewById(R.id.rvPosts);
        rvPosts.setLayoutManager(new LinearLayoutManager(currView.getContext()));
        rvPosts.setAdapter(adapter);
       // tvCaption = currView.findViewById(R.id.tvTLCaption);

        // loading posts
        loadTopPosts(ParseUser.getCurrentUser());

        // setting refresh on pull down
        swipeContainer = (SwipeRefreshLayout) currView.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadTopPosts(ParseUser.getCurrentUser());
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        return currView;
    }


    private void loadTopPosts(ParseUser currtUser) {
        clear();
        final Post.Query postQuery = new Post.Query();
        postQuery.getTop().withUser().whereEqualTo("user",currtUser);


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
