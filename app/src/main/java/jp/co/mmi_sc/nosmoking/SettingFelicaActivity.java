package jp.co.mmi_sc.nosmoking;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class SettingFelicaActivity extends AppCompatActivity {
    private FelicaDetection mFelica = null;
    private String mCardID = null;
    private TextView mFelicaIDText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_felica);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFelica = new FelicaDetection(this);

        mFelicaIDText = (TextView) findViewById(R.id.setfelicatextView);

        if (mFelica.isState() == FelicaDetection.NFC_ADAPTER_DISABLE) {
            mFelicaIDText.setText(getString(R.string.setting_felica_set_warning));
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mFelica.isState() == FelicaDetection.NFC_ADAPTER_ENABLE) {
            if (mCardID == null) {
                mFelicaIDText.setText(getString(R.string.setting_felica_set_legend));
            }
            mFelica.enable();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mFelica.isState() == FelicaDetection.NFC_ADAPTER_ENABLE) {
            mFelica.disable();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        mCardID = mFelica.getCardId(intent);
        if (mCardID.length() > 0) {
            MyConfig config = new MyConfig(this);
            config.setFelicaId(mCardID);

            Vibrator vib = (Vibrator)getSystemService(VIBRATOR_SERVICE);
            vib.vibrate(200);
            mFelicaIDText.setText(getString(R.string.setting_felica_set_ok));
        }
    }

}
