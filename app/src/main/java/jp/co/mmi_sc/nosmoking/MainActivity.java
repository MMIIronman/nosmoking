package jp.co.mmi_sc.nosmoking;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    MyConfig mConfig;
    int mAnimeCount = 0;
    Timer mTimer = null;
    Handler mHandler = new android.os.Handler();
    TextView mText = null;

    private static final int MENU_SELECT_DEBUG = 100;
    private static final int DEBUG_SUB_MENU_ID_1 = 101;
    private static final int DEBUG_SUB_MENU_ID_2 = 102;

    private static final int ANIME_COUNT_MAX = 4;

    private static final int CUSTOM_DIALOG = 1;

    private static final int anime_level1[] = {R.drawable.business_suit_good, R.drawable.business_suit_good, R.drawable.business_suit_good, R.drawable.business_suit_good};
    private static final int anime_level2[] = {R.drawable.tabako_kyukei, R.drawable.tabako_kyukei, R.drawable.tabako_kyukei, R.drawable.tabako_kyukei};
    private static final int anime_level3[] = {R.drawable.pose_zasetsu, R.drawable.pose_zasetsu, R.drawable.pose_zasetsu, R.drawable.pose_zasetsu};
    private static final int anime_level4[] = {R.drawable.tabako_heavy_smoker, R.drawable.tabako_heavy_smoker, R.drawable.tabako_heavy_smoker, R.drawable.tabako_heavy_smoker};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mText = (TextView) findViewById(R.id.mainTextView);

        mConfig = new MyConfig(this);
        mAnimeCount = 0;
        stopAnimationTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();

        setTextViewStrings();
        startAnimtaionTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();

        stopAnimationTimer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if (mConfig.getDebugMode() == true) {
            SubMenu debugSubMenu;
            debugSubMenu = menu.addSubMenu(Menu.NONE, MENU_SELECT_DEBUG, Menu.NONE, "Debug Menu");

            debugSubMenu.add(Menu.NONE, DEBUG_SUB_MENU_ID_1, Menu.NONE, "All Reset");
            debugSubMenu.add(Menu.NONE, DEBUG_SUB_MENU_ID_2, Menu.NONE, "Set Count");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingMenuActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_manual) {
            Intent intent = new Intent(this, ManualActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_contact) {
            Uri uri = Uri.parse(getString(R.string.contact_form));
            Intent i = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(i);
            return true;
        } else if (id == MENU_SELECT_DEBUG) {
            Toast.makeText(this, "Select menu is Debug Menu", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == DEBUG_SUB_MENU_ID_1) {
            Toast.makeText(this, "Setting Reset All!!", Toast.LENGTH_LONG).show();
            mConfig.initMyConfig(); // test
            return true;
        } else if (id == DEBUG_SUB_MENU_ID_2) {
            Toast.makeText(this, "Setting Count!", Toast.LENGTH_SHORT).show();
            showDialog(CUSTOM_DIALOG);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    int getAnimeImageId() {
        int ret = anime_level1[0];

        if (mAnimeCount >= ANIME_COUNT_MAX) {
            mAnimeCount = 0;
        }

        Long count = mConfig.getSmokingCount();
        int level = MySetting.getCountLevel(count);
        switch (level) {
            case MySetting.COUNTER_LEVEL1:
                ret = anime_level1[mAnimeCount];
                break;
            case MySetting.COUNTER_LEVEL2:
                ret = anime_level2[mAnimeCount];
                break;
            case MySetting.COUNTER_LEVEL3:
                ret = anime_level3[mAnimeCount];
                break;
            case MySetting.COUNTER_LEVEL4:
                ret = anime_level4[mAnimeCount];
                break;
            default:
                break;
        }

        return ret;
    }

    void setImageViewAnime() {
        ImageView iv = (ImageView)findViewById(R.id.imageMainView);
        iv.setImageResource(getAnimeImageId());
        mAnimeCount++;
    }

    void setTextViewStrings() {
        Long count = mConfig.getSmokingCount();
        if (mConfig.getDebugMode() == true) {
	        String strCount = new String("Count = [");
	        strCount += String.valueOf(count);
	        strCount += "]\n";
	        strCount += "Level = [";
	        strCount += String.valueOf(MySetting.getCountLevel(count));
	        strCount += "]";
	        mText.setText(strCount);
		} else {
	        int level = MySetting.getCountLevel(count);
            int id;
	        switch (level) {
	            case MySetting.COUNTER_LEVEL2:
	                id = R.string.main_strings_level2;
	                break;
	            case MySetting.COUNTER_LEVEL3:
                    id = R.string.main_strings_level3;
	                break;
	            case MySetting.COUNTER_LEVEL4:
                    id = R.string.main_strings_level4;
	                break;
                case MySetting.COUNTER_LEVEL1:
	            default:
                    id = R.string.main_strings_level1;
	                break;
	        }
	        mText.setText(getString(id));
		}
        mText.append("\n");
        mText.append(getString(R.string.last_tap_time));
        mText.append("\n");
        mText.append(MySetting.getTimeStrings(getResources()));
        mText.append("\n");
        mText.append(getString(R.string.last_tap_time_end));
    }

    void startAnimtaionTimer() {
        if (mTimer == null) {
            mTimer = new Timer(true);
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mHandler.post( new Runnable() {
                        @Override
                        public void run() {
                            setImageViewAnime();
                        }
                    });

                }
            },
            100,
            1000);

        }
    }

    void stopAnimationTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {

            case CUSTOM_DIALOG:
                //レイアウトの呼び出し
                LayoutInflater factory = LayoutInflater.from(this);
                final View inputView = factory.inflate(R.layout.input_number_dialog, null);

                EditText editText = (EditText) inputView.findViewById(R.id.dialog_edittext);
                if (editText != null) {
                    Long count = mConfig.getSmokingCount();
                    editText.setText(String.valueOf(count), TextView.BufferType.NORMAL);
                }

                //ダイアログの作成(AlertDialog.Builder)
                return new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(R.string.debug_dialog)
                        .setView(inputView)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                EditText editText = (EditText) inputView.findViewById(R.id.dialog_edittext);
                                if (editText != null) {
                                    mConfig.setSmokingCount(Long.parseLong(editText.getText().toString()));
                                    setTextViewStrings();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                        .create();
        }

        return null;
    }
}

