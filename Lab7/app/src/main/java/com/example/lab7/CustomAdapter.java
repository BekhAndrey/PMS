package com.example.lab7;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<Task> {

    List<Task> taskList;
    Context context;
    int resource;

    public CustomAdapter(Context context, int resource, List<Task> taskList) {
        super(context, resource, taskList);
        this.taskList = taskList;
        this .context = context;
        this.resource = resource;
    }

    //this will return the ListView Item as a View
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //we need to get the view of the xml for our list item
        //And for this we need a layoutinflater
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        //getting the view
        View view = layoutInflater.inflate(resource, null, false);

        ImageView imageView = view.findViewById(R.id.image);
        TextView textViewName = view.findViewById(R.id.name);
        TextView textViewDescription = view.findViewById(R.id.description);
        TextView textViewDifficulty = view.findViewById(R.id.difficulty);
        TextView textViewPriority = view.findViewById(R.id.priority);
        TextView textViewDuration = view.findViewById(R.id.duration);
        ImageView imageViewAvatar = view.findViewById(R.id.image);
        Task task = taskList.get(position);

        textViewName.setText(task.getName());
        textViewDescription.setText(task.getDescription());
        textViewDifficulty.setText("Difficulty: " + task.getDifficulty());
        textViewPriority.setText("Priority: " +task.getPriority());
        textViewDuration.setText("Duration: " +task.getDuration() + " days");
        try {
            File file=new File(task.getImagePath());
            imageViewAvatar.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(file)));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        //finally returning the view
        return view;
    }

}
