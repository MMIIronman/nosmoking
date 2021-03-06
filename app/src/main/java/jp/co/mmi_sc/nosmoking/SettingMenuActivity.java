package jp.co.mmi_sc.nosmoking;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;



/**
 * Created by k_hijiya on 2016/04/10.
 */
public class SettingMenuActivity  extends AppCompatActivity {
    MyConfig mConfig;
    private TextView mSettingMenuIDText;
    private Button mButtonResetCount;
    private Button mButtonFelica;
    private AlertDialog mConfirmDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingmenu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mConfig = new MyConfig(this);

        mSettingMenuIDText = (TextView) findViewById(R.id.setttingmenuTextView);

        mSettingMenuIDText.setText(R.string.message_activity_setting_menu);

        mButtonResetCount = (Button)findViewById(R.id.button_resetcount);
        mButtonResetCount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    mConfirmDialog.show();
            }
        });

        mButtonFelica = (Button)findViewById(R.id.button_felica);
        mButtonFelica.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
	            Intent intent = new Intent(getApplicationContext(), SettingFelicaActivity.class);
	            startActivity(intent);
            }
        });
        FelicaDetection felica = new FelicaDetection(this);
        int state = felica.isState();
        if (state == FelicaDetection.NFC_ADAPTER_NONE) {
            mButtonFelica.setEnabled(false);
        } else {
            mButtonFelica.setEnabled(true);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.button_reset_count);
        builder.setMessage(R.string.message_confirm_dlg);
        builder.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                mConfig.initMyConfig();
                Toast toast = Toast.makeText(SettingMenuActivity.this, R.string.message_reset_count, Toast.LENGTH_LONG);
                toast.show();
            }
        });
        builder.setNegativeButton(R.string.button_no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        mConfirmDialog = builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }

}
