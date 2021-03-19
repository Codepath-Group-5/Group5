package com.example.stockpot;

import android.app.Application;

import com.example.stockpot.models.Post;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("QbGXYqZf40QoZsTFapHmjLerwpflADIXAjNl2R8e")
                .clientKey("uJt3dTtXAsnladxScHLrHsq9iOr5hUgNJ8Bc7v0q")
                .server("https://parseapi.back4app.com")
                        .build()
                );
    }
}
