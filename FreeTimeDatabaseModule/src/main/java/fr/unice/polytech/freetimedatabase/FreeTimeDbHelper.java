package fr.unice.polytech.freetimedatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import fr.unice.polytech.freetimedatabase.FreeTimeDbContract.*;

/**
 * Created by Hakim on 09/06/2014.
 */
public class FreeTimeDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FreeTimeDb.db";

    private static final String SQL_CREATE_UNOCCIPIED_TIME_TABLE =
            "CREATE TABLE " + UnoccupiedTime.TABLE_NAME + "(" +
            UnoccupiedTime._ID + " INTEGER PRIMARY KEY, " +
            UnoccupiedTime.COLUMN_START_TIME + " INTEGER, " +
            UnoccupiedTime.COLUMN_END_TIME + " INTEGER" +
            ")";

    private static final String SQL_DELETE_UNOCCUPIED_TIME_TABLE =
            "DROP TABLE IF EXISTS " + UnoccupiedTime.TABLE_NAME;

    private static final String SQL_CREATE_FREETIME_BLOCK_TABLE =
            "CREATE TABLE " + FreeTimeBlock.TABLE_NAME + "(" +
            FreeTimeBlock._ID + " INTEGER PRIMARY KEY, " +
            FreeTimeBlock.COLUMN_START_TIME + " INTEGER, " +
            FreeTimeBlock.COLUMN_END_TIME + " INTEGER" +
            ")";

    private static final String SQL_DELETE_FREETIME_BLOCK_TABLE =
            "DROP TABLE IF EXISTS " + UnoccupiedTime.TABLE_NAME;

    public FreeTimeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_UNOCCIPIED_TIME_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_FREETIME_BLOCK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        //The upgrade policy for this database is to discard all data and start over
        sqLiteDatabase.execSQL(SQL_DELETE_UNOCCUPIED_TIME_TABLE);
        sqLiteDatabase.execSQL(SQL_DELETE_FREETIME_BLOCK_TABLE);
        onCreate(sqLiteDatabase);
    }
}
