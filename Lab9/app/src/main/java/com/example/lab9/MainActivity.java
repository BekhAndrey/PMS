package com.example.lab9;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.lab9.data.AppDatabase;
import com.example.lab9.data.Task;
import com.example.lab9.data.TaskDao;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CustomAdapter adapter;
    private RecyclerView recyclerView;
    private TaskViewModel taskViewModel;

    public static final int IDM_OPEN = 101;
    public static final int IDM_EDIT = 102;
    public static final int IDM_DELETE = 103;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.list);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Task selectedTask = adapter.getTaskAt(position);
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
        adapter = new CustomAdapter(this);
        recyclerView.setAdapter(adapter);
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        taskViewModel.getAllTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> list) {
                adapter.setTasks(list);
            }
        });

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Task selectedTask = adapter.getTaskAt(item.getGroupId());
        switch (item.getItemId()) {
            case IDM_OPEN:
                Intent inspectIntent = new Intent(getBaseContext(), InspectActivity.class);
                inspectIntent.putExtra(Task.class.getSimpleName(), selectedTask);
                startActivity(inspectIntent);
                break;
            case IDM_EDIT:
                Intent editIntent = new Intent(getBaseContext(), EditActivity.class);
                editIntent.putExtra(Task.class.getSimpleName(), selectedTask);
                startActivity(editIntent);
                break;
            case IDM_DELETE:
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Удалить задачу")
                        .setMessage("Вы уверены, что хотите удалить выбранную задачу?")
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                taskViewModel.delete(selectedTask);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.action_add :
                Intent intent = new Intent(getBaseContext(), CreateActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}