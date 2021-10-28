package com.example.lab7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lab7.data.DBContract;
import com.example.lab7.data.DBHelper;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private CustomAdapter adapter;
    private List<Task> taskList;
    private RecyclerView recyclerView;
    private DBHelper mDbHelper;
    private SQLiteDatabase db;
    private Cursor cursor;

    public static final int IDM_OPEN = 101;
    public static final int IDM_EDIT = 102;
    public static final int IDM_DELETE = 103;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        mDbHelper = new DBHelper(this);
        db = mDbHelper.getWritableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBContract.DBEntry.TABLE_NAME, null);
        taskList = Helper.getTaskListFromCursor(this, cursor);
        recyclerView = findViewById(R.id.list);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Task selectedTask = taskList.get(position);
                        Intent intent = new Intent(getBaseContext(), InspectActivity.class);
                        intent.putExtra(Task.class.getSimpleName(), selectedTask);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
        adapter = new CustomAdapter(this, taskList);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Task selectedTask = Helper.getTaskByPosition(this, item.getGroupId());
        switch (item.getItemId()) {
            case IDM_OPEN:
                Intent inspectIntent = new Intent(getBaseContext(), InspectActivity.class);
                inspectIntent.putExtra(Task.class.getSimpleName(), selectedTask);
                startActivity(inspectIntent);
                break;
            case IDM_EDIT:
                Intent editIntent = new Intent(getBaseContext(), EditActivity.class);
                editIntent.putExtra("id", selectedTask.getId());
                startActivity(editIntent);
                break;
            case IDM_DELETE:
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Удалить задачу")
                        .setMessage("Вы уверены, что хотите удалить выбранную задачу?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                db.delete(DBContract.DBEntry.TABLE_NAME, DBContract.DBEntry._ID + "=" + taskList.get(item.getGroupId()).getId(), null);
                                cursor = db.rawQuery("SELECT * FROM " + DBContract.DBEntry.TABLE_NAME, null);
                                adapter = new CustomAdapter(getBaseContext(), Helper.getTaskListFromCursor(getBaseContext(), cursor));
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Отмена", null)
                        .show();
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        return true;
//    }
//

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        switch (item.getItemId()) {
            case R.id.action_add :
                Intent intent = new Intent(getBaseContext(), CreateActivity.class);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.action_sort_by_name:
                cursor = db.rawQuery("SELECT * FROM " + DBContract.DBEntry.TABLE_NAME + " ORDER BY " + DBContract.DBEntry.COLUMN_NAME_NAME , null);
                taskList = Helper.getTaskListFromCursor(getBaseContext(),cursor);
                adapter = new CustomAdapter(getBaseContext(), taskList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.action_sort_by_priority:
                cursor = db.rawQuery("SELECT * FROM " + DBContract.DBEntry.TABLE_NAME
                        + " ORDER BY CASE WHEN " + DBContract.DBEntry.COLUMN_NAME_PRIORITY + "='High' THEN 0 "
                        + "WHEN " + DBContract.DBEntry.COLUMN_NAME_PRIORITY + "='Medium' THEN 1 "
                        + "WHEN " + DBContract.DBEntry.COLUMN_NAME_PRIORITY + "='Low' THEN 2 "
                        + "ELSE 3 END ASC", null);
                taskList = Helper.getTaskListFromCursor(getBaseContext(),cursor);
                adapter = new CustomAdapter(getBaseContext(), taskList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.action_get_finished:
                cursor = db.rawQuery("SELECT * FROM " + DBContract.DBEntry.TABLE_NAME + " WHERE " + DBContract.DBEntry.COLUMN_NAME_STATUS + "=" + "'Finished'", null);
                taskList = Helper.getTaskListFromCursor(getBaseContext(),cursor);
                adapter = new CustomAdapter(getBaseContext(), taskList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.action_refresh:
                cursor = db.rawQuery("SELECT * FROM " + DBContract.DBEntry.TABLE_NAME, null);
                taskList = Helper.getTaskListFromCursor(getBaseContext(),cursor);
                adapter = new CustomAdapter(getBaseContext(), taskList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                drawer.closeDrawer(GravityCompat.START);
                return true;
        }
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        switch(id){
//            case R.id.action_add :
//                Intent intent = new Intent(getBaseContext(), CreateActivity.class);
//                startActivity(intent);
//                return true;
//            case R.id.action_sort_by_name:
//                cursor = db.rawQuery("SELECT * FROM " + DBContract.DBEntry.TABLE_NAME + " ORDER BY " + DBContract.DBEntry.COLUMN_NAME_NAME , null);
//                taskList = Helper.getTaskListFromCursor(getBaseContext(),cursor);
//                adapter = new CustomAdapter(getBaseContext(), taskList);
//                recyclerView.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//                return true;
//            case R.id.action_sort_by_priority:
//                cursor = db.rawQuery("SELECT * FROM " + DBContract.DBEntry.TABLE_NAME
//                        + " ORDER BY CASE WHEN " + DBContract.DBEntry.COLUMN_NAME_PRIORITY + "='High' THEN 0 "
//                        + "WHEN " + DBContract.DBEntry.COLUMN_NAME_PRIORITY + "='Medium' THEN 1 "
//                        + "WHEN " + DBContract.DBEntry.COLUMN_NAME_PRIORITY + "='Low' THEN 2 "
//                        + "ELSE 3 END ASC", null);
//                taskList = Helper.getTaskListFromCursor(getBaseContext(),cursor);
//                adapter = new CustomAdapter(getBaseContext(), taskList);
//                recyclerView.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//                return true;
//            case R.id.action_get_finished:
//                cursor = db.rawQuery("SELECT * FROM " + DBContract.DBEntry.TABLE_NAME + " WHERE " + DBContract.DBEntry.COLUMN_NAME_STATUS + "=" + "'Finished'", null);
//                taskList = Helper.getTaskListFromCursor(getBaseContext(),cursor);
//                adapter = new CustomAdapter(getBaseContext(), taskList);
//                recyclerView.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//                return true;
//            case R.id.action_refresh:
//                cursor = db.rawQuery("SELECT * FROM " + DBContract.DBEntry.TABLE_NAME, null);
//                taskList = Helper.getTaskListFromCursor(getBaseContext(),cursor);
//                adapter = new CustomAdapter(getBaseContext(), taskList);
//                recyclerView.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}