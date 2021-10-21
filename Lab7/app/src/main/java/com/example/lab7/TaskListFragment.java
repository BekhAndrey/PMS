package com.example.lab7;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.lab7.data.DBContract;
import com.example.lab7.data.DBHelper;

import java.util.ArrayList;
import java.util.List;


public class TaskListFragment extends ListFragment {

    private CustomAdapter adapter;
    private List<Task> taskList = new ArrayList<Task>();

    static interface TaskListListener {
        void itemClicked(int position);
    };
    private TaskListListener listener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (TaskListListener)context;
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (listener != null) {
            listener.itemClicked(position);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        DBHelper helper = new DBHelper(inflater.getContext());
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBContract.DBEntry.TABLE_NAME, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            Task task = new Task();
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
        adapter = new CustomAdapter(inflater.getContext(), R.layout.custom_listview, taskList);
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);

    }
}