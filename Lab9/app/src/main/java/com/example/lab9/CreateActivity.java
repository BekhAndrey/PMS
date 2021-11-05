package com.example.lab9;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.lab9.data.AppDatabase;
import com.example.lab9.data.Task;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class CreateActivity extends AppCompatActivity {

    ImageView imageViewAvatar;
    TextInputLayout textInputLayoutName, textInputLayoutDuration, textInputLayoutDescription;
    Spinner spinnerPriority, spinnerDifficulty;
    Button buttonCreate;
    TaskViewModel taskViewModel;

    private void initializeWidgets() {
        textInputLayoutName = (TextInputLayout) findViewById(R.id.textInputLayoutName);
        textInputLayoutDuration = (TextInputLayout) findViewById(R.id.textInputLayoutDuration);
        textInputLayoutDescription = (TextInputLayout) findViewById(R.id.textInputLayoutDescription);
        spinnerPriority = (Spinner) findViewById(R.id.spinnerPriority);
        spinnerDifficulty = (Spinner) findViewById(R.id.spinnerDifficulty);
        buttonCreate = (Button) findViewById(R.id.buttonCreate);
        imageViewAvatar = (ImageView) findViewById(R.id.imageViewAvatar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        initializeWidgets();
        Task newTask = new Task();
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && null != result.getData()) {
                            Uri selectedImage = result.getData().getData();
                            newTask.setImagePath(Helper.getRealPathFromURI(getBaseContext(),selectedImage));
                            imageViewAvatar.setImageURI(selectedImage);
                        }
                    }
                });
        imageViewAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                someActivityResultLauncher.launch(intent);
            }
        });
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newTask.setName(textInputLayoutName.getEditText().getText().toString());
                newTask.setDescription(textInputLayoutDescription.getEditText().getText().toString());
                newTask.setDifficulty(spinnerDifficulty.getSelectedItem().toString());
                newTask.setPriority(spinnerPriority.getSelectedItem().toString());
                newTask.setDuration(textInputLayoutDuration.getEditText().getText().toString());
                taskViewModel.insert(newTask);
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }



}