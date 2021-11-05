package com.example.lab9;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lab9.data.Task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class InspectActivity extends AppCompatActivity {

    TextView textViewName, textViewDescription, textViewPriority, textViewDifficulty, textViewDuration;
    ImageView imageViewAvatar;
    Task selectedTask;

    private void initializeWidgets() {
        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewDescription = (TextView) findViewById(R.id.textViewDescription);
        textViewPriority = (TextView) findViewById(R.id.textViewPriority);
        textViewDifficulty = (TextView) findViewById(R.id.textViewDifficulty);
        textViewDuration = (TextView) findViewById(R.id.textViewDuration);
        imageViewAvatar = (ImageView) findViewById(R.id.imageViewAvatar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect);
        initializeWidgets();
        Bundle arguments = getIntent().getExtras();
        selectedTask = (Task) arguments.getSerializable(Task.class.getSimpleName());
        textViewName.setText(selectedTask.getName());
        textViewDescription.setText(selectedTask.getDescription());
        textViewPriority.setText(selectedTask.getPriority());
        textViewDifficulty.setText(selectedTask.getDifficulty());
        textViewDuration.setText(selectedTask.getDuration() + " days");
        try {
            File file=new File(selectedTask.getImagePath());
            imageViewAvatar.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(file)));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}