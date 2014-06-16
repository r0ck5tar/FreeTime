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

    //region empty_slots table SQL code
    private static final String SQL_CREATE_EMPTY_SLOTS_TABLE =
            "CREATE TABLE " + EmptySlots.TABLE_NAME + "(" +
            EmptySlots._ID + " INTEGER PRIMARY KEY, " +
            EmptySlots.COLUMN_START_TIME + " INTEGER, " +
            EmptySlots.COLUMN_END_TIME + " INTEGER" +
            ")";

    private static final String SQL_DELETE_EMPTY_SLOTS_TABLE =
            "DROP TABLE IF EXISTS " + EmptySlots.TABLE_NAME;
    //endregion

    //region freetime_block table SQL code
    private static final String SQL_CREATE_FREETIME_BLOCK_TABLE =
            "CREATE TABLE " + FreeTimeBlocks.TABLE_NAME + "(" +
            FreeTimeBlocks._ID + " INTEGER PRIMARY KEY, " +
            FreeTimeBlocks.COLUMN_DAY + " TEXT, " +
            FreeTimeBlocks.COLUMN_EVENT_ID + " INTEGER, " +
            FreeTimeBlocks.COLUMN_START_TIME + " INTEGER, " +
            FreeTimeBlocks.COLUMN_END_TIME + " INTEGER" +
            ")";

    private static final String SQL_DELETE_FREETIME_BLOCK_TABLE =
            "DROP TABLE IF EXISTS " + EmptySlots.TABLE_NAME;
    //endregion

    //region tasks table SQL code
    private static final String SQL_CREATE_TASKS_TABLE =
            "CREATE TABLE " + Tasks.TABLE_NAME + "(" +
                    Tasks._ID + " INTEGER PRIMARY KEY, " +
                    Tasks.COLUMN_TITLE + " TEXT, " +
                    Tasks.COLUMN_START_DATE + " INTEGER, " +
                    Tasks.COLUMN_END_DATE + " INTEGER, " +
                    Tasks.COLUMN_DESCRIPTION + " TEXT, " +
                    Tasks.COLUMN_ESTIMATION + " INTEGER, " +
                    Tasks.COLUMN_USER_PRIORITY + " INTEGER, " +
                    Tasks.COLUMN_WEIGHT + " INTEGER " +
                    ")";

    private static final String SQL_DELETE_TASKS_TABLE =
            "DROP TABLE IF EXISTS " + Tasks.TABLE_NAME;
    //endregion

    //region ft_events_to_task table SQL code
    private static final String SQL_CREATE_FT_EVENTS_TO_TASK_TABLE =
            "CREATE TABLE " + FtEventsToTask.TABLE_NAME + "(" +
                    FtEventsToTask._ID + " INTEGER PRIMARY KEY, " +
                    FtEventsToTask.COLUMN_EVENT_ID + " INTEGER, " +
                    FtEventsToTask.COLUMN_TASK_ID + " INTEGER, " +
                    FtEventsToTask.COLUMN_COMPLETED + " INTEGER, " +
                    " FOREIGN KEY (" + FtEventsToTask.COLUMN_TASK_ID + ") REFERENCES " +
                    Tasks.TABLE_NAME + " (" + Tasks._ID +
                    "));";

    private static final String SQL_DELETE_FT_EVENTS_TO_TASK_TABLE =
            "DROP TABLE IF EXISTS " + FtEventsToTask.TABLE_NAME;
    //endregion

    public FreeTimeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_EMPTY_SLOTS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_FREETIME_BLOCK_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TASKS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_FT_EVENTS_TO_TASK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        //The upgrade policy for this database is to discard all data and start over
        sqLiteDatabase.execSQL(SQL_DELETE_EMPTY_SLOTS_TABLE);
        sqLiteDatabase.execSQL(SQL_DELETE_FREETIME_BLOCK_TABLE);
        sqLiteDatabase.execSQL(SQL_DELETE_TASKS_TABLE);
        sqLiteDatabase.execSQL(SQL_DELETE_FT_EVENTS_TO_TASK_TABLE);
        onCreate(sqLiteDatabase);
    }
}
