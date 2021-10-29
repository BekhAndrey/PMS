package com.example.lab7;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.lab7.data.DBContract;
import com.example.lab7.data.DBHelper;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class EditActivity extends AppCompatActivity {

    private ImageView imageViewAvatar;
    private CheckBox checkBoxFinished;
    private TextInputLayout textInputLayoutName, textInputLayoutDuration, textInputLayoutDescription;
    private Spinner spinnerPriority, spinnerDifficulty;
    private Button buttonEdit;
    private DBHelper mDbHelper;
    private SQLiteDatabase db;

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
        int id = arguments.getInt("id");
        mDbHelper = new DBHelper(this);
        db = mDbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBContract.DBEntry.TABLE_NAME +
                " WHERE " + DBContract.DBEntry._ID + "=" + id,null);
        cursor.moveToFirst();
        if(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_STATUS)).equals("Finished")){
            checkBoxFinished.setChecked(true);
        }
        textInputLayoutName.getEditText().setText(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_NAME)));
        textInputLayoutDuration.getEditText().setText(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_DURATION)));
        textInputLayoutDescription.getEditText().setText(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_DESCRIPTION)));
        spinnerPriority.setSelection(((ArrayAdapter)spinnerPriority.getAdapter()).getPosition(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_PRIORITY))));
        spinnerDifficulty.setSelection(((ArrayAdapter)spinnerDifficulty.getAdapter()).getPosition(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_DIFFICULTY))));
        try {
            File file=new File(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_IMAGE_PATH)));
            imageViewAvatar.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(file)));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        ContentValues values = new ContentValues();
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(EditActivity.this)
                        .setTitle("Сохранить изменения")
                        .setMessage("Вы уверены, что хотите сохранить изменения?")
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                values.put(DBContract.DBEntry.COLUMN_NAME_NAME, textInputLayoutName.getEditText().getText().toString());
                                values.put(DBContract.DBEntry.COLUMN_NAME_DESCRIPTION, textInputLayoutDescription.getEditText().getText().toString());
                                values.put(DBContract.DBEntry.COLUMN_NAME_DIFFICULTY, spinnerDifficulty.getSelectedItem().toString());
                                values.put(DBContract.DBEntry.COLUMN_NAME_PRIORITY, spinnerPriority.getSelectedItem().toString());
                                values.put(DBContract.DBEntry.COLUMN_NAME_DURATION, textInputLayoutDuration.getEditText().getText().toString());
                                if(checkBoxFinished.isChecked()){
                                    values.put(DBContract.DBEntry.COLUMN_NAME_STATUS, "Finished");
                                }

                                db.update(DBContract.DBEntry.TABLE_NAME, values, DBContract.DBEntry._ID + "=" + id, null);
                                db.close();
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
    }
}