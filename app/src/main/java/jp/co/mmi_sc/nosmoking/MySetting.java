package jp.co.mmi_sc.nosmoking;

import android.content.res.Resources;

/**
 * Created by hajime on 2016/03/12.
 */
public class MySetting {
    // count level
    public static final int COUNTER_LEVEL1 = 0;
    public static final int COUNTER_LEVEL2 = 1;
    public static final int COUNTER_LEVEL3 = 2;
    public static final int COUNTER_LEVEL4 = 3;

    // count check param
    private static final Long COUNT_CHECK_LEVEL1 = 100L;
    private static final Long COUNT_CHECK_LEVEL2 = 1000L;
    private static final Long COUNT_CHECK_LEVEL3 = 10000L;
    private static final Long COUNT_CHECK_LEVEL4 = 100000L;

    // count up start time
    public static final Long COUNT_UP_SPACE_DEFAULT = (60 * 1000L);
    public static Long sCount_timer = 0L;
    public static Long sCountUpSpace = COUNT_UP_SPACE_DEFAULT;

    // カウンタより画面に表示する内容レベルを取得
    static int getCountLevel(Long count) {
        int ret = 0;

        if (count <= COUNT_CHECK_LEVEL1) {
            ret = COUNTER_LEVEL1;
        } else if (count <= COUNT_CHECK_LEVEL2) {
            ret = COUNTER_LEVEL2;
        } else if (count <= COUNT_CHECK_LEVEL3) {
            ret = COUNTER_LEVEL3;
        } else {
            ret = COUNTER_LEVEL4;
        }

        return ret;
    }

    //前回タップ時より一定期間空いているかチェック
    static boolean checkCountUp() {
        boolean bRetFlag = false;
        Long time = System.currentTimeMillis();

        if (time > sCount_timer) {
            time -= sCount_timer;
            if (time >= sCountUpSpace) {
                bRetFlag = true;
            }
        }

        return bRetFlag;

    }

    static void setCountUpTimer() {
        sCount_timer = System.currentTimeMillis();
    }

    static String getTimeStrings(Resources res) {
        long counttime_day = 0;
        long counttime_hour = 0;
        long counttime_min = 0;
        long nowtTime;
        StringBuilder sb = new StringBuilder("");

        nowtTime = System.currentTimeMillis();
        CountTimeInfo time = new CountTimeInfo(sCount_timer.longValue(), nowtTime);
        counttime_day = time.getTimeDay();
        counttime_hour = time.getTimeHour();
        counttime_min = time.getTimeMin();

        if (counttime_day > 0) {
            sb.append(String.format("%1$02d", counttime_day));
            sb.append(res.getString(R.string.time_day));
            sb.append(" ");
        }
        if ((counttime_day > 0) || (counttime_hour > 0) || (counttime_min > 0)) {
            sb.append(String.format("%1$02d", counttime_hour));
            sb.append(res.getString(R.string.time_hour));
            sb.append(" ");
        }
        sb.append(String.format("%1$02d", counttime_min));
        sb.append(res.getString(R.string.time_min));

        return sb.toString();
    }
}
