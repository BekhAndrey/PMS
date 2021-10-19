package com.example.lab6;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskListFragment.TaskListListener {

    CustomAdapter adapter;
    List<Task> taskList;
    ListView listView;
    TaskListFragment taskListFragment;

    public static final int IDM_OPEN = 101;
    public static final int IDM_EDIT = 102;
    public static final int IDM_DELETE = 103;

    @Override
    public void itemClicked(int position) {
        View fragmentContainer = findViewById(R.id.detail_frame);
        if(fragmentContainer!=null){
            TaskDetailFragment fragm = new TaskDetailFragment();
            FragmentTransaction ftr = getSupportFragmentManager().beginTransaction();
            fragm.setTask(position);
            ftr.replace(R.id.detail_frame, fragm);
            ftr.addToBackStack(null);
            ftr.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ftr.commit();
        }
        else{
            Task selectedTask = taskList.get(position);
            Intent intent = new Intent(this, InspectActivity.class);
            intent.putExtra(Task.class.getSimpleName(), selectedTask);
            startActivity(intent);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taskList = JSONHelper.importFromJSON(this);
        if (taskList == null) {
            taskList = new ArrayList<Task>();
            taskList.add(new Task("First", "This is a long long long long long long long long description",
                    "easy", "Medium", "1", "/storage/emulated/0/Download/1.jpg"));
            taskList.add(new Task("Second", "This is a short description",
                    "medium", "High", "5", "/storage/emulated/0/Download/1.jpg"));
            taskList.add(new Task("Third", "This is a medium medium medium description",
                    "hard", "Low", "7", "/storage/emulated/0/Pictures/IMG_20210929_052107.jpg"));
            taskList.add(new Task("Fourth", "This is the long long long long long long long long description",
                    "easy", "Low", "7", "/storage/emulated/0/Pictures/IMG_20210929_052107.jpg"));
            taskList.add(new Task("Fifth", "This is a long long long long long long long long description",
                    "easy", "High", "7", "/storage/emulated/0/Pictures/IMG_20210929_052107.jpg"));
            JSONHelper.exportToJSON(this, taskList);
        }
        taskListFragment = (TaskListFragment) getSupportFragmentManager().findFragmentById(R.id.list_frag);
        registerForContextMenu(taskListFragment.getListView());
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, IDM_OPEN, Menu.NONE, "Просмотреть");
        menu.add(Menu.NONE, IDM_EDIT, Menu.NONE, "Изменить");
        menu.add(Menu.NONE, IDM_DELETE, Menu.NONE, "Удалить");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Task selectedTask = taskList.get(info.position);
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
                                taskList.remove(info.position);
                                JSONHelper.exportToJSON(MainActivity.this,taskList);
                                adapter = new CustomAdapter(taskListFragment.getContext(), R.layout.custom_listview, taskList);
                                taskListFragment.setListAdapter(adapter);
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
            case R.id.action_sort_by_name:
                taskList.sort(Comparator.comparing(task->task.getName()));
                adapter = new CustomAdapter(taskListFragment.getContext(), R.layout.custom_listview, taskList);
                taskListFragment.setListAdapter(adapter);
                JSONHelper.exportToJSON(this, taskList);
                adapter.notifyDataSetChanged();
                return true;
            case R.id.action_sort_by_priority:
                Comparator comparator = new PriorityComparator();
                taskList.sort(comparator);
                adapter = new CustomAdapter(taskListFragment.getContext(), R.layout.custom_listview, taskList);
                taskListFragment.setListAdapter(adapter);
                JSONHelper.exportToJSON(this, taskList);
                adapter.notifyDataSetChanged();
                return true;
            case R.id.action_get_finished:
                Long unfinishedAmount = taskList.stream().filter(x->x.getStatus().equals("Unfinished")).count();
                Long finishedAmount = new Long(taskList.size()-unfinishedAmount);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Информация по задачам")
                        .setMessage("Всего задач: " + taskList.size()
                                +";\nКоличество выполненных задач: " + finishedAmount
                                + ";\nКоличество невыполненных задача: " + unfinishedAmount)
                        .setPositiveButton("Ок", null)
                        .show();

        }
        return super.onOptionsItemSelected(item);
    }
}