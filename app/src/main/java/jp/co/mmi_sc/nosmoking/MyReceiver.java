package jp.co.mmi_sc.nosmoking;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {
    public static final String ACTION_UPDATE_COUNT = "mmi_sc.co.jp.intent.action.UPDATE_COUNT";

    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_UPDATE_COUNT)) {
            Log.d("Smoking","BroadcastReceiver.onReceive()");
            if (MySetting.checkCountUp() == true) {
                MyConfig config = new MyConfig(context);
                Long count = config.getSmokingCount();
                count++;
                config.setSmokingCount(count);
                MySetting.setCountUpTimer();
            }
        }
    }
}
