//package me.krithiyer.fbuinstagram;
//
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//
//import com.bumptech.glide.Glide;
//
//import java.util.ArrayList;
//
//import me.krithiyer.fbuinstagram.model.Post;
//
//public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {
//
//    // list of Posts
//    ArrayList<Post> userTLPosts;
//    // context for rendering
//    Context userContext;
//
//    public ProfileAdapter(ArrayList<Post> post) {
//        this.userTLPosts = post;
//    }
//
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
//        // get context
//        userContext = parent.getContext();
//        LayoutInflater profileInflator = LayoutInflater.from(userContext);
//        // create view
//        View profileView = profileInflator.inflate(R.layout.profile_item, parent, false);
//        // return new view
//        return new ViewHolder(profileView);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
//        final Post userPost = userTLPosts.get(i);
//        String imgURL = userPost.getImage().getUrl();
//        // loading picture
//        Glide.with(userContext)
//                .load(imgURL)
//                .into(viewHolder.ivProfileTL);
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//
//    // create the Viewholder as a static inner class
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        // track view objects
//        ImageView ivProfileTL;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            // lookup view objects
//            ivProfileTL = (ImageView) itemView.findViewById(R.id.ivUserPosts);
//        }
//    }
//}
