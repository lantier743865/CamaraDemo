package com.example.camarademo;

import android.app.Application;

import com.mm.mediasdk.MoMediaManager;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MoMediaManager.init(this, "53c2b08cd6aa13e678c37240c9e6d1f9");

    }
}
