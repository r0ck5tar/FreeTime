package fr.unice.polytech.datasources;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import fr.unice.polytech.freetimedatabase.FreeTimeDbHelper;

/**
 * Created by Hakim on 15/06/2014.
 */
public class DataSource {
    protected SQLiteDatabase database;
    protected FreeTimeDbHelper ftDbHelper;

    public DataSource(Context context) {
        ftDbHelper = new FreeTimeDbHelper(context);
    }

    public void open()  { database = ftDbHelper.getWritableDatabase(); }
    public void close() { ftDbHelper.close(); }
}
