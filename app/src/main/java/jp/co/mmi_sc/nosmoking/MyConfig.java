package jp.co.mmi_sc.nosmoking;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by h-tsukiji on 2016/03/04.
 */
public class MyConfig {
    private static final String MYCONFIG = "KEY_MYCONFIG";
    private static final String SMOKINGCOUNT = "KEY_SMOKING_COUNT";

    SharedPreferences mPref = null;

    MyConfig(Context context) {
        mPref = context.getSharedPreferences(MYCONFIG, Context.MODE_PRIVATE);
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
}
