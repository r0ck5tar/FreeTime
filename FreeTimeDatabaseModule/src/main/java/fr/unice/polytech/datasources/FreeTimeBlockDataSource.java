package fr.unice.polytech.datasources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import fr.unice.polytech.entities.FreeTimeBlockEntity;
import fr.unice.polytech.freetimedatabase.FreeTimeDbContract.FreeTimeBlocks;

/**
 * Created by Hakim on 16/06/2014.
 */
public class FreeTimeBlockDataSource extends  DataSource {
    //Table columns
    public static String[] ALL_COLUMNS = {FreeTimeBlocks._ID, FreeTimeBlocks.COLUMN_DAY,
                                          FreeTimeBlocks.COLUMN_START_TIME, FreeTimeBlocks.COLUMN_END_TIME,
                                          FreeTimeBlocks.COLUMN_EVENT_ID};

    private final int ID = 0, DAY = 1, START = 2, END = 3, EVENT_ID = 4;

    public FreeTimeBlockDataSource(Context context) { super(context); }

    public FreeTimeBlockEntity createFreeTimeBlock(String day, long startTime, long endTime, long eventId) {
        ContentValues values = new ContentValues();
        values.put(FreeTimeBlocks.COLUMN_DAY, day);
        values.put(FreeTimeBlocks.COLUMN_START_TIME, startTime);
        values.put(FreeTimeBlocks.COLUMN_END_TIME, endTime);
        values.put(FreeTimeBlocks.COLUMN_EVENT_ID, eventId);

        open();
        long insertId = database.insert(FreeTimeBlocks.TABLE_NAME, null, values);
        Cursor cursor = database.query(FreeTimeBlocks.TABLE_NAME, ALL_COLUMNS, FreeTimeBlocks._ID + " = "
                                      + insertId, null, null, null, null);
        cursor.moveToFirst();
        FreeTimeBlockEntity newFreeTimeBlock = cursorToFreeTimeBlock(cursor);
        close();
        return newFreeTimeBlock;
    }

    private FreeTimeBlockEntity cursorToFreeTimeBlock(Cursor cursor) {
        return new FreeTimeBlockEntity(cursor.getLong(ID), cursor.getString(DAY),
                                       cursor.getLong(START), cursor.getLong(END))
                                       .setEventId(cursor.getLong(EVENT_ID));
    }
}
