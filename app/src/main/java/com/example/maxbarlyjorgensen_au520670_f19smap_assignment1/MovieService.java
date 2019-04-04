package com.example.maxbarlyjorgensen_au520670_f19smap_assignment1;

import android.app.Service;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class MovieService extends Service {

    //First time
    @Override
    public void onCreate() {
        super.onCreate();
    }

    //Every time
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "dbBuilding", Toast.LENGTH_SHORT).show();

        AppDb db = Room.databaseBuilder(getApplicationContext(),
                AppDb.class, "movieDb").build();

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
