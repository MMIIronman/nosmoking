package jp.co.mmi_sc.nosmoking;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by h-tsukiji on 2016/03/04.
 */
public class MyConfig {
    private static final String KEY_MYCONFIG = "KEY_MYCONFIG";
    private static final String KEY_SMOKING_COUNT = "KEY_SMOKING_COUNT";
    private static final String KEY_DEBUGMODE = "KEY_DEBUGMODE";
    private static final String KEY_FELICA_ID = "KEY_FELICA_ID";

    SharedPreferences mPref = null;

    MyConfig(Context context) {
        mPref = context.getSharedPreferences(KEY_MYCONFIG, Context.MODE_PRIVATE);
    }

    void initMyConfig() {
        setSmokingCount(0L);
        if (getDebugMode() == true) {
            changeDebugMode();
        }
        setFelicaId(null);
        MySetting.sCountUpSpace = MySetting.COUNT_UP_SPACE_DEFAULT;
        MySetting.sCount_timer = System.currentTimeMillis();
    }

    Long getSmokingCount() {
        Long ret = new Long(0);

        if (mPref != null) {
            ret = mPref.getLong(KEY_SMOKING_COUNT, 0);
        }

        return ret;
    }

    void setSmokingCount(Long count) {
        SharedPreferences.Editor editor = mPref.edit();

        editor.putLong(KEY_SMOKING_COUNT, count);
        editor.commit();
    }

    boolean getDebugMode() {
        boolean ret = false;

        if (mPref != null) {
            ret = mPref.getBoolean(KEY_DEBUGMODE, false);
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
        editor.putBoolean(KEY_DEBUGMODE, setFlag);
        editor.commit();
    }

    String getFelicaId() {
        String ret = null;

        if (mPref != null) {
            ret = mPref.getString(KEY_FELICA_ID, null);
        }

        return ret;
    }

    void setFelicaId(String id) {
        SharedPreferences.Editor editor = mPref.edit();

        editor.putString(KEY_FELICA_ID, id);
        editor.commit();
    }

}
