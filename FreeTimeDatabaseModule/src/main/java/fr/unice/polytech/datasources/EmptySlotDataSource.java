package fr.unice.polytech.datasources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.unice.polytech.entities.EmptySlot;
import fr.unice.polytech.freetimedatabase.FreeTimeDbContract.EmptySlots;

/**
 * Created by Hakim on 15/06/2014.
 */
public class EmptySlotDataSource extends DataSource {

    public EmptySlotDataSource(Context context) { super(context); }

    //Table columns
    public static String[] ALL_COLUMNS = { EmptySlots._ID,
                                           EmptySlots.COLUMN_START_TIME, EmptySlots.COLUMN_END_TIME };

    public final int ID = 0, START = 1, END = 2;

    public EmptySlot createEmptySlot(long startTime, long endTime){
        ContentValues values = new ContentValues();
        values.put(EmptySlots.COLUMN_START_TIME, startTime);
        values.put(EmptySlots.COLUMN_END_TIME, endTime);

        open();
        long insertId = database.insert(EmptySlots.TABLE_NAME, null, values);
        Cursor cursor = database.query(EmptySlots.TABLE_NAME, ALL_COLUMNS, EmptySlots._ID + " = "
                                       + insertId, null, null, null, null);
        cursor.moveToFirst();
        EmptySlot newEmptySlot = cursorToEmptySlot(cursor);
        cursor.close();
        close();
        return newEmptySlot;
    }

    public void deleteEmptySlot(EmptySlot emptySlot) {
        long id = emptySlot.getId();
        System.out.println("EmptySlot deleted with id: " + id);
        database.delete(EmptySlots.TABLE_NAME, EmptySlots._ID + " = " + id, null);
    }

    public List<EmptySlot> getAllEmptySlots() {
        List<EmptySlot> emptySlots = new ArrayList<EmptySlot>();

        Cursor cursor = database.query(EmptySlots.TABLE_NAME,
                ALL_COLUMNS, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emptySlots.add(cursorToEmptySlot(cursor));
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return emptySlots;
    }

    public void clearEmptySlotTable() {
        open();
        database.delete(EmptySlots.TABLE_NAME, null, null);
        close();
    }

    private EmptySlot cursorToEmptySlot(Cursor cursor) {
        return new EmptySlot(cursor.getLong(ID), cursor.getLong(START), cursor.getLong(END));
    }
}
