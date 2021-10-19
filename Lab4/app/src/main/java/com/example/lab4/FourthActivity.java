package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FourthActivity extends AppCompatActivity {

    TextView nicknameTextView, fullNameTextView, phoneNumberTextView, emailTextView, groupTextView;
    ImageView avatarImageView;
    Contact selectedContact;

    private void initializeWidgets() {
        nicknameTextView = (TextView) findViewById(R.id.nicknameTextView);
        fullNameTextView = (TextView) findViewById(R.id.fullnameTextView);
        phoneNumberTextView = (TextView) findViewById(R.id.phoneTextView);
        emailTextView = (TextView) findViewById(R.id.emailTextView);
        groupTextView = (TextView) findViewById(R.id.groupTextView);
        avatarImageView = (ImageView) findViewById(R.id.avatarView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
        initializeWidgets();
        Bundle arguments = getIntent().getExtras();
        selectedContact = (Contact) arguments.getSerializable(Contact.class.getSimpleName());
        nicknameTextView.setText(selectedContact.getNickname());
        fullNameTextView.setText(selectedContact.getFirstname() + " " + selectedContact.getLastname());
        phoneNumberTextView.setText(selectedContact.getPhoneNumber());
        emailTextView.setText(selectedContact.getEmail());
        groupTextView.setText(selectedContact.getGroup());
        try {
            File file=new File(selectedContact.getImagePath());
            avatarImageView.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(file)));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}