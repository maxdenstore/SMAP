package broadcastreciever;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.maxbarlyjorgensen_au520670_f19smap_assignment1.OverviewActivity;

public class BroadcastReceiverClass extends android.content.BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction() == "Update") {
        }
    }
}
