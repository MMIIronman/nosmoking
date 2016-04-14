package jp.co.mmi_sc.nosmoking;

/**
 * Created by tsukiji on 2016/03/25.
 */
public class CountTimeInfo {
    private long mDay = 0;
    private long mHour = 0;
    private long mMin = 0;
    private long mSec = 0;

    private static final int DAY = 24;
    private static final int HOUR = 60;
    private static final int MINUTE = 60;
    private static final int SECOND = 1000;

    CountTimeInfo (long startTime, long endTime) {
        long counttime = 0;

        if (startTime == 0) {
            mDay = 0;
            mHour = 0;
            mMin = 0;
            mSec = 0;
        } else if (endTime > startTime) {
            counttime = (endTime - startTime);
            mDay = (counttime / (DAY * HOUR * MINUTE * SECOND));
            mHour =  (counttime / (HOUR * MINUTE * SECOND));
            if (mHour  >= DAY) {
                mHour = (mHour % DAY);
            }
            mMin = (counttime / (MINUTE * SECOND));
            if (mMin  >= HOUR) {
                mMin = (mMin % HOUR);
            }
            mSec = (counttime / (SECOND));
            if (mSec  >= MINUTE) {
                mSec = (mSec % MINUTE);
            }
        }
    }

    long getTimeDay() {
        return mDay;
    }

    long getTimeHour() {
        return mHour;
    }

    long getTimeMin() {
        return mMin;
    }

    long getTimeSec() {
        return mSec;
    }
}
