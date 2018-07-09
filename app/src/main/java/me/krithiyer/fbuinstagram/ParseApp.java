package me.krithiyer.fbuinstagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

import me.krithiyer.fbuinstagram.model.Post;

public class ParseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);
        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("krithiyerinsta")
                .clientKey("krithiyerinsta")
                .server("http://krithiyer-fbu-instagram.herokuapp.com/parse")
                .build();

        Parse.initialize(configuration);
    }
}
