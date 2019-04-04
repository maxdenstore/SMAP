package com.example.maxbarlyjorgensen_au520670_f19smap_assignment1;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class MovieService extends Service {

    //First time
    @Override
    public void onCreate() {
        super.onCreate();
    }

    //Every time
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    //When service is stopped
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
