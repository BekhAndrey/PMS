package com.example.lab7;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lab7.data.DBContract;
import com.example.lab7.data.DBHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class TaskDetailFragment extends Fragment {

    private int taskPosition;

    public void setTask(int position) {
        this.taskPosition = position;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null) {
            DBHelper helper = new DBHelper(this.getContext());
            SQLiteDatabase db = helper.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + DBContract.DBEntry.TABLE_NAME, null);
            cursor.moveToPosition(taskPosition);
            TextView textViewName = (TextView)view.findViewById(R.id.textViewName);
            TextView textViewDescription = (TextView)view.findViewById(R.id.textViewDescription);
            TextView textViewDuration = (TextView)view.findViewById(R.id.textViewDuration);
            TextView textViewDifficulty = (TextView)view.findViewById(R.id.textViewDifficulty);
            TextView textViewPriority = (TextView)view.findViewById(R.id.textViewPriority);
            textViewName.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_NAME)));
            textViewDescription.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_DESCRIPTION)));
            textViewDuration.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_DURATION)));
            textViewDifficulty.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_DIFFICULTY)));
            textViewPriority.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_PRIORITY)));
            cursor.close();
            db.close();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_detail, container, false);
    }
}