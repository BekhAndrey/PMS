package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ThirdActivity extends AppCompatActivity {

    ListView contactListView;
    private List<Contact> contactsList;

    private void initializeWidgets() {
        contactListView = (ListView) findViewById(R.id.contactList);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        initializeWidgets();
        contactsList = JSONHelper.importFromJSON(this.getBaseContext());
        if(contactsList==null){
            contactsList = new ArrayList<Contact>();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, contactsList);
        contactListView.setAdapter(adapter);
        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Contact selectedContact = (Contact)adapterView.getItemAtPosition(i);
                Intent intent = new Intent(view.getContext(), FourthActivity.class);
                intent.putExtra(Contact.class.getSimpleName(), selectedContact);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this.getBaseContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}