package com.example.lab7;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class TaskDetailFragment extends Fragment {

    Task selectedTask;
    List<Task> taskList;
    private int taskPosition;

    public void setTask(int position) {
        this.taskPosition = position;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        taskList = JSONHelper.importFromJSON(getContext());
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null) {
            selectedTask = taskList.get(taskPosition);
            TextView textViewName = (TextView)view.findViewById(R.id.textViewName);
            TextView textViewDescription = (TextView)view.findViewById(R.id.textViewDescription);
            TextView textViewDuration = (TextView)view.findViewById(R.id.textViewDuration);
            TextView textViewDifficulty = (TextView)view.findViewById(R.id.textViewDifficulty);
            TextView textViewPriority = (TextView)view.findViewById(R.id.textViewPriority);
            textViewName.setText(selectedTask.getName());
            textViewDescription.setText(selectedTask.getDescription());
            textViewDuration.setText(selectedTask.getDuration());
            textViewDifficulty.setText(selectedTask.getDifficulty());
            textViewPriority.setText(selectedTask.getPriority());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_detail, container, false);
    }
}