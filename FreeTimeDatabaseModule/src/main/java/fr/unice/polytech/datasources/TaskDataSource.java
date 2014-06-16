package fr.unice.polytech.datasources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.unice.polytech.entities.TaskEntity;
import fr.unice.polytech.freetimedatabase.FreeTimeDbContract.*;

/**
 * Created by Hakim on 15/06/2014.
 */
public class TaskDataSource extends DataSource {
    public static final double DEFAULT_WEIGHT = 0;

    //Table columns
    public static String[] ALL_COLUMNS = {Tasks._ID, Tasks.COLUMN_TITLE,
                                   Tasks.COLUMN_START_DATE, Tasks.COLUMN_END_DATE,
                                   Tasks.COLUMN_ESTIMATION, Tasks.COLUMN_DESCRIPTION,
                                   Tasks.COLUMN_USER_PRIORITY, Tasks.COLUMN_WEIGHT};
    private final int ID = 0, TITLE = 1, START = 2, END = 3, ESTIMATION = 4,
                      DESCRIPTION = 5, PRIORITY = 6, WEIGHT = 7;

    public TaskDataSource(Context context) {
        super(context);
    }

    public TaskEntity createTask(String title, String description, Calendar startDate, Calendar endDate,
                           int hourEstimation, int priority) {
        ContentValues values = new ContentValues();
        values.put(Tasks.COLUMN_TITLE, title);
        values.put(Tasks.COLUMN_START_DATE, startDate.getTimeInMillis());
        values.put(Tasks.COLUMN_END_DATE, endDate.getTimeInMillis());
        values.put(Tasks.COLUMN_USER_PRIORITY, priority);
        values.put(Tasks.COLUMN_WEIGHT, DEFAULT_WEIGHT);
        values.put(Tasks.COLUMN_ESTIMATION, hourEstimation);
        if(description != null) {
            values.put(Tasks.COLUMN_DESCRIPTION, description);
        }
        long insertId = database.insert(Tasks.TABLE_NAME, null,
                values);
        Cursor cursor = database.query(Tasks.TABLE_NAME,
                ALL_COLUMNS, Tasks._ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        TaskEntity newTask = cursorToTask(cursor);
        cursor.close();
        return newTask;
    }

    public void deleteTask(TaskEntity task) {
        long id = task.getId();
        System.out.println("Task deleted with id: " + id);
        database.delete(Tasks.TABLE_NAME, Tasks._ID + " = " + id, null);
    }

    public List<TaskEntity> getAllTasks() {
        List<TaskEntity> tasks = new ArrayList<TaskEntity>();

        Cursor cursor = database.query(Tasks.TABLE_NAME,
                ALL_COLUMNS, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            tasks.add(cursorToTask(cursor));
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return tasks;
    }

    public TaskEntity cursorToTask(Cursor cursor) {
        return new TaskEntity(cursor.getLong(ID), cursor.getString(TITLE),
                        cursor.getLong(START), cursor.getLong(END))
                       .setDescription(cursor.getString(DESCRIPTION))
                       .setHourEstimation(cursor.getLong(ESTIMATION))
                       .setPriority(cursor.getInt(PRIORITY))
                       .setWeight(cursor.getDouble(WEIGHT));
    }

    public void open()  { super.open(); }
    public void close() { super.close(); }
}
