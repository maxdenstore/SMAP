package NotificationChannel;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

public class MovieNotificationChannel extends Application {
    public static final String CHANNEL_ID_1 = "channe1_l";

    @Override
    public void onCreate() {
        super.onCreate();
        CreateNotificationChannels();
    }

    private void CreateNotificationChannels() {
         if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
             //Channel
             NotificationChannel channel = new NotificationChannel(CHANNEL_ID_1, "Channel_1",
                     NotificationManager.IMPORTANCE_LOW
             );

             NotificationManager manager = getSystemService(NotificationManager.class);
             manager.createNotificationChannel(channel);

             //Gør den så irriterende som mulig:
/*             channel.enableVibration(true);
             channel.enableLights(true);*/


         }

    }
}
