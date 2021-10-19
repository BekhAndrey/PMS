package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    Button nextButton;
    TextInputLayout nicknameInputLayout, firstnameInputLayout, lastnameInputLayout;
    Contact contact;

    private void initializeWidgets() {
        nextButton = (Button) findViewById(R.id.nextButton);
        nicknameInputLayout = (TextInputLayout) findViewById(R.id.contactNickname);
        firstnameInputLayout = (TextInputLayout) findViewById(R.id.contactFirstname);
        lastnameInputLayout = (TextInputLayout) findViewById(R.id.contactLastname);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeWidgets();
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contact = new Contact();
                contact.setNickname(nicknameInputLayout.getEditText().getText().toString());
                contact.setFirstname(firstnameInputLayout.getEditText().getText().toString());
                contact.setLastname(lastnameInputLayout.getEditText().getText().toString());
                Intent intent = new Intent(view.getContext(), SecondActivity.class);
                intent.putExtra(Contact.class.getSimpleName(), contact);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("nicknameInput", nicknameInputLayout.getEditText().getText().toString());
        outState.putString("firstnameInput", firstnameInputLayout.getEditText().getText().toString());
        outState.putString("lastnameInput", lastnameInputLayout.getEditText().getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        nicknameInputLayout.getEditText().setText(savedInstanceState.getString("nicknameInput"));
        firstnameInputLayout.getEditText().setText(savedInstanceState.getString("firstnameInput"));
        lastnameInputLayout.getEditText().setText(savedInstanceState.getString("lastnameInput"));
    }
}