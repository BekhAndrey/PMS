package com.example.lab3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    TextView nicknameTextView, fullNameTextView;
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
                }
                else{
                    Toast.makeText(view.getContext(), "Не удалось сохранить данные", Toast.LENGTH_LONG).show();
                }
                Intent intent = new Intent(view.getContext(), ThirdActivity.class);
                intent.putExtra(Contact.class.getSimpleName(), contact);
                startActivity(intent);
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
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("phoneInput", phoneNumberInputLayout.getEditText().getText().toString());
        outState.putString("emailInput", emailInputLayout.getEditText().getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        phoneNumberInputLayout.getEditText().setText(savedInstanceState.getString("phoneInput"));
        emailInputLayout.getEditText().setText(savedInstanceState.getString("emailInput"));
    }
}