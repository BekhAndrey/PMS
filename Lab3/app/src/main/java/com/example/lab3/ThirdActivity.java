package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class ThirdActivity extends AppCompatActivity {

    TextView nicknameTextView, fullNameTextView, phoneNumberTextView, emailTextView, groupTextView;
    Contact contact;

    private void initializeWidgets() {
        nicknameTextView = (TextView) findViewById(R.id.nicknameTextView);
        fullNameTextView = (TextView) findViewById(R.id.fullnameTextView);
        phoneNumberTextView = (TextView) findViewById(R.id.phoneTextView);
        emailTextView = (TextView) findViewById(R.id.emailTextView);
        groupTextView = (TextView) findViewById(R.id.groupTextView);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        initializeWidgets();
        Bundle arguments = getIntent().getExtras();
        contact = (Contact) arguments.getSerializable(Contact.class.getSimpleName());
        nicknameTextView.setText(contact.getNickname());
        fullNameTextView.setText(contact.getFirstname() + " " + contact.getLastname());
        phoneNumberTextView.setText(contact.getPhoneNumber());
        emailTextView.setText(contact.getEmail());
        groupTextView.setText(contact.getGroup());
    }

}