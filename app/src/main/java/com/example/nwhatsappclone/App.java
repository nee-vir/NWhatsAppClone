package com.example.nwhatsappclone;

import android.app.Application;

import com.parse.Parse;

public class App  extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("T4fCZWd7KxyLH7I46XCfMokW5tpZWMxttlgyb9xI")
                // if defined
                .clientKey("O6zw94Q8xit62SPcGqAOdMxyu2vLTQnvmbqok18q")
                .server("https://parseapi.back4app.com/")
                .build()
        );


    }
}
