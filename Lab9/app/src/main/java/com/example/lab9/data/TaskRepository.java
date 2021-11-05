package com.example.lab9.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskRepository {
    private TaskDao taskDao;
    private LiveData<List<Task>> allTasks;

    public TaskRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        taskDao = database.taskDao();
        allTasks = taskDao.getAll();
    }

    public void insert(Task task){
        new InsertNoteAsyncTask(taskDao).execute(task);
    }

    public void update(Task task){
        new UpdateNoteAsyncTask(taskDao).execute(task);
    }

    public void delete(Task task){
        new DeleteNoteAsyncTask(taskDao).execute(task);
    }

    public void deleteAllTasks(){
        new DeleteAllNotesAsyncTask(taskDao).execute();
    }

    public LiveData<List<Task>> getAllTasks(){
        return allTasks;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDao taskDao;

        private InsertNoteAsyncTask(TaskDao taskDao){
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.insert(tasks[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Task, Void, Void>{
        private TaskDao taskDao;

        private UpdateNoteAsyncTask(TaskDao taskDao){
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.update(tasks[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Task, Void, Void>{
        private TaskDao taskDao;

        private DeleteNoteAsyncTask(TaskDao taskDao){
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.delete(tasks[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void>{
        private TaskDao taskDao;

        private DeleteAllNotesAsyncTask(TaskDao taskDao){
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            taskDao.deleteAllNotes();
            return null;
        }
    }

}
