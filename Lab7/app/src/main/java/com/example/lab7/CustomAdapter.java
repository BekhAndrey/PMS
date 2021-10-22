package com.example.lab7;

import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lab7.Task;
import com.example.lab7.data.DBContract;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class CustomAdapter extends CursorAdapter {
    private LayoutInflater cursorInflater;

    private List<Task> taskList;

    public CustomAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        cursorInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }
//    public CustomAdapter(Context context, int resource, List<Task> taskList) {
//        super(context, resource, taskList);
//        this.taskList = taskList;
//        this .context = context;
//        this.resource = resource;
//    }
//
//
//    //this will return the ListView Item as a View
//    @NonNull
//    @Override
//    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//
//        //we need to get the view of the xml for our list item
//        //And for this we need a layoutinflater
//        LayoutInflater layoutInflater = LayoutInflater.from(context);
//
//        //getting the view
//        View view = layoutInflater.inflate(resource, null, false);
//
//        ImageView imageView = view.findViewById(R.id.image);
//        TextView textViewName = view.findViewById(R.id.name);
//        TextView textViewDescription = view.findViewById(R.id.description);
//        TextView textViewDifficulty = view.findViewById(R.id.difficulty);
//        TextView textViewPriority = view.findViewById(R.id.priority);
//        TextView textViewDuration = view.findViewById(R.id.duration);
//        ImageView imageViewAvatar = view.findViewById(R.id.image);
//        Task task = taskList.get(position);
//
//        textViewName.setText(task.getName());
//        textViewDescription.setText(task.getDescription());
//        textViewDifficulty.setText("Difficulty: " + task.getDifficulty());
//        textViewPriority.setText("Priority: " +task.getPriority());
//        textViewDuration.setText("Duration: " +task.getDuration() + " days");
//        try {
//            File file=new File(task.getImagePath());
//            imageViewAvatar.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(file)));
//        }
//        catch (FileNotFoundException e)
//        {
//            e.printStackTrace();
//        }
//
//        //finally returning the view
//        return view;
//    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return cursorInflater.inflate(R.layout.custom_listview, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView imageView = view.findViewById(R.id.image);
        TextView textViewName = view.findViewById(R.id.name);
        TextView textViewDescription = view.findViewById(R.id.description);
        TextView textViewDifficulty = view.findViewById(R.id.difficulty);
        TextView textViewPriority = view.findViewById(R.id.priority);
        TextView textViewDuration = view.findViewById(R.id.duration);
        ImageView imageViewAvatar = view.findViewById(R.id.image);

        textViewName.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_NAME)));
        textViewDescription.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_DESCRIPTION)));
        textViewDifficulty.setText("Difficulty: " + cursor.getString(cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_DIFFICULTY)));
        textViewPriority.setText("Priority: " +cursor.getString(cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_PRIORITY)));
        textViewDuration.setText("Duration: " +cursor.getString(cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_DURATION)) + " days");
        try {
            File file=new File(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_IMAGE_PATH)));
            imageViewAvatar.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(file)));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

}
