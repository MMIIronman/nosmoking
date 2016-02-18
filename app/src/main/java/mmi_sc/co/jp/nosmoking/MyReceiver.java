package mmi_sc.co.jp.nosmoking;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {
    public static int clickCount = 0;

    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("UPDATE_WIDGET")) {
           Log.d("Smoking","BroadcastReceiver.onReceive()");
        }
    }
}
