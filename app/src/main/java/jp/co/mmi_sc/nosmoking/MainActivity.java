package jp.co.mmi_sc.nosmoking;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
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

    public static final int MENU_SELECT_DEBUG = 100;
    private static final int ANIME_COUNT_MAX = 4;

    private static final int anime_level1[] = {R.drawable.anime_level1_001, R.drawable.anime_level1_002, R.drawable.anime_level1_003, R.drawable.anime_level1_004};
    private static final int anime_level2[] = {R.drawable.anime_level2_001, R.drawable.anime_level2_002, R.drawable.anime_level2_003, R.drawable.anime_level2_004};
    private static final int anime_level3[] = {R.drawable.anime_level3_001, R.drawable.anime_level3_002, R.drawable.anime_level3_003, R.drawable.anime_level3_004};
    private static final int anime_level4[] = {R.drawable.anime_level4_001, R.drawable.anime_level4_002, R.drawable.anime_level4_003, R.drawable.anime_level4_004};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mConfig = new MyConfig(this);
        mAnimeCount = 0;
        stopAnimationTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // test -->
        setTextViewStrings();
        // test <--
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
            menu.add(Menu.NONE, MENU_SELECT_DEBUG, Menu.NONE, "Debug Menu");
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
            Toast.makeText(this, "Select menu is Setting", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        } else if (id == MENU_SELECT_DEBUG) {
            Toast.makeText(this, "Select menu is Debug Menu", Toast.LENGTH_SHORT).show();
            mConfig.initMyConfig(); // test
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
        String strCount = new String("Count = [");
        strCount += String.valueOf(count);
        strCount += "]\n";
        strCount += "Level = [";
        strCount += String.valueOf(MySetting.getCountLevel(count));
        strCount += "]";
        TextView mText = (TextView) findViewById(R.id.mainTextView);
        mText.setText(strCount);
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
            500);

        }
    }

    void stopAnimationTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }
}
