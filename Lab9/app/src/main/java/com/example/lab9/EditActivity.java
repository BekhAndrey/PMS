package com.example.lab9;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.lab9.data.AppDatabase;
import com.example.lab9.data.Task;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class EditActivity extends AppCompatActivity {

    ImageView imageViewAvatar;
    CheckBox checkBoxFinished;
    Task editedTask;
    TextInputLayout textInputLayoutName, textInputLayoutDuration, textInputLayoutDescription;
    Spinner spinnerPriority, spinnerDifficulty;
    Button buttonEdit;
    TaskViewModel taskViewModel;

    private void initializeWidgets() {
        textInputLayoutName = (TextInputLayout) findViewById(R.id.textInputLayoutName);
        textInputLayoutDuration = (TextInputLayout) findViewById(R.id.textInputLayoutDuration);
        textInputLayoutDescription = (TextInputLayout) findViewById(R.id.textInputLayoutDescription);
        spinnerPriority = (Spinner) findViewById(R.id.spinnerPriority);
        spinnerDifficulty = (Spinner) findViewById(R.id.spinnerDifficulty);
        buttonEdit = (Button) findViewById(R.id.buttonEdit);
        imageViewAvatar = (ImageView) findViewById(R.id.imageViewAvatar);
        checkBoxFinished = (CheckBox) findViewById(R.id.checkBoxFinished);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initializeWidgets();
        Bundle arguments = getIntent().getExtras();
        editedTask = (Task) arguments.getSerializable(Task.class.getSimpleName());
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        if(editedTask.getStatus().equals("Finished")){
            checkBoxFinished.setSelected(true);
        }
        textInputLayoutName.getEditText().setText(editedTask.getName());
        textInputLayoutDuration.getEditText().setText(editedTask.getDuration());
        textInputLayoutDescription.getEditText().setText(editedTask.getDescription());
        spinnerPriority.setSelection(((ArrayAdapter)spinnerPriority.getAdapter()).getPosition(editedTask.getPriority()));
        spinnerDifficulty.setSelection(((ArrayAdapter)spinnerDifficulty.getAdapter()).getPosition(editedTask.getDifficulty()));
        try {
            File file=new File(editedTask.getImagePath());
            imageViewAvatar.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(file)));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(EditActivity.this)
                        .setTitle("Сохранить изменения")
                        .setMessage("Вы уверены, что хотите сохранить изменения?")
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                editedTask.setName(textInputLayoutName.getEditText().getText().toString());
                                editedTask.setDescription(textInputLayoutDescription.getEditText().getText().toString());
                                editedTask.setDuration(textInputLayoutDuration.getEditText().getText().toString());
                                editedTask.setPriority(spinnerPriority.getSelectedItem().toString());
                                editedTask.setDifficulty(spinnerDifficulty.getSelectedItem().toString());
                                if(checkBoxFinished.isChecked()){
                                    editedTask.setStatus("Finished");
                                }
                                taskViewModel.update(editedTask);
                                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Отмена", null)
                        .show();
            }
        });
        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && null != result.getData()) {
                            Uri selectedImage = result.getData().getData();
                            editedTask.setImagePath(Helper.getRealPathFromURI(getBaseContext(),selectedImage));
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
    }
}