package com.example.lab7;

import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
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

    public static final int IDM_OPEN = 101;
    public static final int IDM_EDIT = 102;
    public static final int IDM_DELETE = 103;


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
        holder.nameView.setText("Name: " + task.getName());
        holder.descriptionView.setText("Description: " +task.getDescription());
        holder.priorityView.setText("Priority: " +task.getPriority());
        holder.difficultyView.setText("Difficulty: " +task.getDifficulty());
        holder.durationView.setText("Duration: " +task.getDuration() + " days");
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
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        final ImageView imageView;
        final TextView nameView, descriptionView, priorityView, difficultyView, durationView;
        CardView cardView;
        ViewHolder(View view){
            super(view);
            nameView = view.findViewById(R.id.name);
            descriptionView = view.findViewById(R.id.description);
            priorityView = view.findViewById(R.id.priority);
            difficultyView = view.findViewById(R.id.difficulty);
            durationView = view.findViewById(R.id.duration);
            imageView = view.findViewById(R.id.image);
            cardView = view.findViewById(R.id.cardView);
            cardView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v,
                                        ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(this.getAdapterPosition(), IDM_OPEN, Menu.NONE, "??????????????????????");
            menu.add(this.getAdapterPosition(), IDM_EDIT, Menu.NONE, "????????????????");
            menu.add(this.getAdapterPosition(), IDM_DELETE, Menu.NONE, "??????????????");
        }
    }

}
