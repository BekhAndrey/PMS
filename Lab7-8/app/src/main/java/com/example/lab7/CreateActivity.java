package com.example.lab7;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.lab7.data.DBContract;
import com.example.lab7.data.DBHelper;
import com.google.android.material.textfield.TextInputLayout;

public class CreateActivity extends AppCompatActivity {

    private ImageView imageViewAvatar;
    private TextInputLayout textInputLayoutName, textInputLayoutDuration, textInputLayoutDescription;
    private Spinner spinnerPriority, spinnerDifficulty;
    private Button buttonCreate;
    private DBHelper mDbHelper;

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
        mDbHelper = new DBHelper(this);
        ContentValues values = new ContentValues();
        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && null != result.getData()) {
                            Uri selectedImage = result.getData().getData();
                            values.put(DBContract.DBEntry.COLUMN_NAME_IMAGE_PATH, Helper.getRealPathFromURI(getBaseContext(),selectedImage));
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
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                values.put(DBContract.DBEntry.COLUMN_NAME_NAME, textInputLayoutName.getEditText().getText().toString());
                values.put(DBContract.DBEntry.COLUMN_NAME_DESCRIPTION, textInputLayoutDescription.getEditText().getText().toString());
                values.put(DBContract.DBEntry.COLUMN_NAME_DIFFICULTY, spinnerDifficulty.getSelectedItem().toString());
                values.put(DBContract.DBEntry.COLUMN_NAME_PRIORITY, spinnerPriority.getSelectedItem().toString());
                values.put(DBContract.DBEntry.COLUMN_NAME_DURATION, textInputLayoutDuration.getEditText().getText().toString());
                values.put(DBContract.DBEntry.COLUMN_NAME_STATUS, "Unfinished");
                long newRowId;
                newRowId = db.insert(
                        DBContract.DBEntry.TABLE_NAME,
                        null,
                        values);
                db.close();
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}