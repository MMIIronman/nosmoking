package jp.co.mmi_sc.nosmoking;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class NfcIntentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_intent);

        Intent intent = getIntent();
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
            String ID = FelicaDetection.getCardId(intent);
            MyConfig config = new MyConfig(this);
            if (ID.equals(config.getFelicaId())) {
                if (MySetting.checkCountUp() == true) {
                    Long count = config.getSmokingCount();
                    count++;
                    config.setSmokingCount(count);
                    Vibrator vib = (Vibrator)getSystemService(VIBRATOR_SERVICE);
                    vib.vibrate(200);
                    MySetting.setCountUpTimer();
                }
            }
        }

        finish();
    }
}
