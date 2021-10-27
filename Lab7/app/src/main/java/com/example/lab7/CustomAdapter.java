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
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab7.Task;
import com.example.lab7.data.DBContract;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<Task> tasks;

    CustomAdapter(Context context, List<Task> tasks) {
        this.tasks = tasks;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.custom_listview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomAdapter.ViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.nameView.setText(task.getName());
        holder.descriptionView.setText(task.getDescription());
        holder.priorityView.setText(task.getPriority());
        holder.difficultyView.setText(task.getDifficulty());
        holder.durationView.setText(task.getDuration());
        try {
            File file=new File(task.getImagePath());
            holder.imageView.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(file)));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView nameView, descriptionView, priorityView, difficultyView, durationView;
        ViewHolder(View view){
            super(view);
            nameView = view.findViewById(R.id.name);
            descriptionView = view.findViewById(R.id.description);
            priorityView = view.findViewById(R.id.priority);
            difficultyView = view.findViewById(R.id.difficulty);
            durationView = view.findViewById(R.id.duration);
            imageView = view.findViewById(R.id.image);
        }
    }
//    private LayoutInflater cursorInflater;
//
//    private List<Task> taskList;
//
//    public CustomAdapter(Context context, Cursor c, int flags) {
//        super(context, c, flags);
//        cursorInflater = (LayoutInflater) context.getSystemService(
//                Context.LAYOUT_INFLATER_SERVICE);
//    }
//
//    @Override
//    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
//        return cursorInflater.inflate(R.layout.custom_listview, viewGroup, false);
//    }
//
//    @Override
//    public void bindView(View view, Context context, Cursor cursor) {
//        ImageView imageView = view.findViewById(R.id.image);
//        TextView textViewName = view.findViewById(R.id.name);
//        TextView textViewDescription = view.findViewById(R.id.description);
//        TextView textViewDifficulty = view.findViewById(R.id.difficulty);
//        TextView textViewPriority = view.findViewById(R.id.priority);
//        TextView textViewDuration = view.findViewById(R.id.duration);
//        ImageView imageViewAvatar = view.findViewById(R.id.image);
//
//        textViewName.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_NAME)));
//        textViewDescription.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_DESCRIPTION)));
//        textViewDifficulty.setText("Difficulty: " + cursor.getString(cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_DIFFICULTY)));
//        textViewPriority.setText("Priority: " +cursor.getString(cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_PRIORITY)));
//        textViewDuration.setText("Duration: " +cursor.getString(cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_DURATION)) + " days");
//        try {
//            File file=new File(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_IMAGE_PATH)));
//            imageViewAvatar.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(file)));
//        }
//        catch (FileNotFoundException e)
//        {
//            e.printStackTrace();
//        }
//    }

}
