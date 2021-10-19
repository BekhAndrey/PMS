package com.example.lab4;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    TextView nicknameTextView, fullNameTextView;
    ImageView avatarImageView;
    TextInputLayout phoneNumberInputLayout, emailInputLayout;
    Contact contact;
    Button createContactButton;
    Spinner groupSpinner;
    CheckBox addGroup;
    private List<Contact> contactsList;

    private void initializeWidgets() {
        nicknameTextView = (TextView) findViewById(R.id.nickname);
        fullNameTextView = (TextView) findViewById(R.id.fullName);
        createContactButton = (Button) findViewById(R.id.createContact);
        groupSpinner = (Spinner) findViewById(R.id.contactGroup);
        addGroup = (CheckBox) findViewById(R.id.addGroup);
        phoneNumberInputLayout = (TextInputLayout) findViewById(R.id.contactPhone);
        emailInputLayout = (TextInputLayout) findViewById(R.id.contactEmail);
        avatarImageView = (ImageView) findViewById(R.id.avatarView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initializeWidgets();
        Bundle arguments = getIntent().getExtras();
        contact = (Contact) arguments.getSerializable(Contact.class.getSimpleName());
        nicknameTextView.setText(contact.getNickname());
        fullNameTextView.setText(contact.getFirstname() + " " + contact.getLastname());
        groupSpinner.setEnabled(false);
        contactsList = JSONHelper.importFromJSON(this.getBaseContext());
        if(contactsList==null){
            contactsList = new ArrayList<Contact>();
        }
        createContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contact.setPhoneNumber(phoneNumberInputLayout.getEditText().getText().toString());
                contact.setEmail(emailInputLayout.getEditText().getText().toString());
                if(addGroup.isChecked()){
                    contact.setGroup(groupSpinner.getSelectedItem().toString());
                }
                contactsList.add(contact);
                boolean result = JSONHelper.exportToJSON(view.getContext(), contactsList);
                if(result){
                    Toast.makeText(view.getContext(), "Данные сохранены", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(view.getContext(), ThirdActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(view.getContext(), "Не удалось сохранить данные", Toast.LENGTH_LONG).show();
                }
            }
        });
        addGroup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(addGroup.isChecked()){
                    groupSpinner.setEnabled(true);
                }
                else groupSpinner.setEnabled(false);
                contact.setGroup(null);
            }
        });
        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && null != result.getData()) {
                            Uri selectedImage = result.getData().getData();
                            contact.setImagePath(getRealPathFromURI(selectedImage));
                            avatarImageView.setImageURI(selectedImage);
                        }
                    }
                });
        avatarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                someActivityResultLauncher.launch(intent);
            }
        });
    }

    public String getRealPathFromURI(Uri contentUri) {
        // can post image
        String [] proj={MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this.getBaseContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("phoneInput", phoneNumberInputLayout.getEditText().getText().toString());
        outState.putString("emailInput", emailInputLayout.getEditText().getText().toString());
        outState.putString("image", contact.getImagePath());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        phoneNumberInputLayout.getEditText().setText(savedInstanceState.getString("phoneInput"));
        emailInputLayout.getEditText().setText(savedInstanceState.getString("emailInput"));
        try {
            File file=new File(savedInstanceState.getString("image"));
            avatarImageView.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(file)));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}