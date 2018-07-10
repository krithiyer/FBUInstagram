package me.krithiyer.fbuinstagram;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import me.krithiyer.fbuinstagram.model.Post;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder> {
    // list of movies
    ArrayList<Post> timelinePosts;
    // config needed for image URL
    Bitmap.Config config;
    // context for rendering
    Context context;

    public Bitmap.Config getConfig() {
        return config;
    }

    public void setConfig(Bitmap.Config config) {
        this.config = config;
    }

    public void setTimelinePosts(ArrayList<Post> posts) {
        timelinePosts = posts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        // get context
        context = parent.getContext();
        LayoutInflater inflator = LayoutInflater.from(context);
        // create view
        View timelineView = inflator.inflate(R.layout.timeline_item, parent, false);
        // return new view
        return new ViewHolder(timelineView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // get post data at a specific index
        final Post post = timelinePosts.get(position);
        // populate data
        holder.tvTLCaption.setText(post.getDescription());
        holder.tvTLUsername.setText(post.getUser().getUsername());

        String imgURL = post.getImage().getUrl();
        Glide.with(context)
                .load(imgURL)
                .into(holder.ivPicture);

    }

    @Override
    public int getItemCount() {
        return timelinePosts.size();
    }
    // create the Viewholder as a static inner class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // track view objects
        ImageView ivPicture;
        TextView tvTLUsername;
        TextView tvTLCaption;

        public ViewHolder(View itemView) {
            super(itemView);
            // lookup view objects
            ivPicture = (ImageView) itemView.findViewById(R.id.ivPicture);
            tvTLUsername = (TextView) itemView.findViewById(R.id.tvTLUsername);
            tvTLCaption = (TextView) itemView.findViewById(R.id.tvTLCaption);

        }
    }
}
