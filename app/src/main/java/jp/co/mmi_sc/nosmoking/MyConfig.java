package jp.co.mmi_sc.nosmoking;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by h-tsukiji on 2016/03/04.
 */
public class MyConfig {
    private static final String MYCONFIG = "KEY_MYCONFIG";
    private static final String SMOKINGCOUNT = "KEY_SMOKING_COUNT";
    private static final String DEBUGMODE = "KEY_DEBUGMODE";

    SharedPreferences mPref = null;

    MyConfig(Context context) {
        mPref = context.getSharedPreferences(MYCONFIG, Context.MODE_PRIVATE);
    }

    void initMyConfig() {
        setSmokingCount(0L);
        if (getDebugMode() == true) {
            changeDebugMode();
        }
        MySetting.sCountUpSpace = MySetting.COUNT_UP_SPACE_DEFAULT;
        MySetting.sCount_timer = 0L;
    }


    Long getSmokingCount() {
        Long ret = new Long(0);

        if (mPref != null) {
            ret = mPref.getLong(SMOKINGCOUNT, 0);
        }

        return ret;
    }

    void setSmokingCount(Long count) {
        SharedPreferences.Editor editor = mPref.edit();

        editor.putLong(SMOKINGCOUNT, count);
        editor.commit();
    }

    boolean getDebugMode() {
        boolean ret = false;

        if (mPref != null) {
            ret = mPref.getBoolean(DEBUGMODE, false);
        }

        return ret;
    }

    void changeDebugMode() {
        boolean setFlag = getDebugMode();
        SharedPreferences.Editor editor = mPref.edit();

        if (setFlag == true) {
            setFlag = false;
        } else {
            setFlag = true;
        }
        editor.putBoolean(DEBUGMODE, setFlag);
        editor.commit();
    }
}
