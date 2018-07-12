package me.krithiyer.fbuinstagram;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import me.krithiyer.fbuinstagram.model.Post;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder> {
    // list of Posts
    ArrayList<Post> timelinePosts;
    // context for rendering
    Context context;

    public TimelineAdapter(ArrayList<Post> post) {

        this.timelinePosts = post;
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
        holder.tvTLCaption.setText(post.getUser().getUsername() + ": " + post.getDescription());
        holder.tvTLUsername.setText(post.getUser().getUsername());
        holder.tvCreatedAt.setText(post.getCreatedAt().toString());

        String profImage = post.getUser().getParseFile("profileImage").getUrl();
        Glide.with(context)
                .load(profImage)
                .into(holder.profilePicture);


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
        ImageButton ibFavorite;
        ImageButton ibShare;
        ImageButton ibCommentPost;
        TextView tvCreatedAt;
        ImageView profilePicture;

        public ViewHolder(View itemView) {
            super(itemView);
            // lookup view objects
            ivPicture = (ImageView) itemView.findViewById(R.id.ivPicture);
            tvTLUsername = (TextView) itemView.findViewById(R.id.tvTLUsername);
            tvTLCaption = (TextView) itemView.findViewById(R.id.tvTLCaption);
            tvCreatedAt = (TextView) itemView.findViewById(R.id.tvCreateA);
            ibFavorite = (ImageButton) itemView.findViewById(R.id.ibFav);
            ibShare = (ImageButton) itemView.findViewById(R.id.ibSend);
            ibCommentPost = (ImageButton) itemView.findViewById(R.id.ibComment);
            profilePicture = (ImageView) itemView.findViewById(R.id.ivTLProfPic);


        }
    }
}
