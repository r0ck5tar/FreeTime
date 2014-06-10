package fr.unice.polytech.freetimedatabase;

import android.provider.BaseColumns;

/**
 * Created by Hakim on 09/06/2014.
 */
public final class FreeTimeDbContract {

    //empty constructor to prevent accidentally instantiating the contract class
    public FreeTimeDbContract() {}

    /* Inner class that defines the unoccupiedtime table contents */
    public static abstract class UnoccupiedTime implements BaseColumns {
        public static final String TABLE_NAME = "unoccupiedtime";
        //public static final String COLUMN_ID = BaseColumns._ID;
        public static final String COLUMN_START_TIME = "starttime";
        public static final String COLUMN_END_TIME = "endtime";
    }

    public static abstract class FreeTimeBlock implements BaseColumns {
        public static final String TABLE_NAME = "freetimeblock";
        //public static final String COLUMN_ID = BaseColumns._ID;
        public static final String COLUMN_START_TIME = "starttime";
        public static final String COLUMN_END_TIME = "endtime";
    }
}