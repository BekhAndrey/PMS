package com.example.lab7;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.loader.content.CursorLoader;

import com.example.lab7.data.DBContract;
import com.example.lab7.data.DBHelper;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

public class Helper {
    public static String getRealPathFromURI(Context context, Uri contentUri) {
        // can post image
        String [] proj={MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(context, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    public static List<Task> getTaskList(Context context){
        List<Task> taskList = new ArrayList<>();
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBContract.DBEntry.TABLE_NAME, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            Task task = new Task();
            task.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.DBEntry._ID)));
            task.setName(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_NAME)));
            task.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_DESCRIPTION)));
            task.setDifficulty(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_DIFFICULTY)));
            task.setDuration(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_DURATION)));
            task.setPriority(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_PRIORITY)));
            task.setImagePath(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_IMAGE_PATH)));
            task.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_STATUS)));
            taskList.add(task); //add the item
            cursor.moveToNext();
        }
        db.close();
        return taskList;
    }

    public static List<Task> getFinished(Context context){
        List<Task> taskList = new ArrayList<>();
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBContract.DBEntry.TABLE_NAME + " WHERE " + DBContract.DBEntry.COLUMN_NAME_STATUS + "=" + "'Finished'", null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            Task task = new Task();
            task.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.DBEntry._ID)));
            task.setName(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_NAME)));
            task.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_DESCRIPTION)));
            task.setDifficulty(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_DIFFICULTY)));
            task.setDuration(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_DURATION)));
            task.setPriority(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_PRIORITY)));
            task.setImagePath(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_IMAGE_PATH)));
            task.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_STATUS)));
            taskList.add(task); //add the item
            cursor.moveToNext();
        }
        db.close();
        return taskList;
    }
}
