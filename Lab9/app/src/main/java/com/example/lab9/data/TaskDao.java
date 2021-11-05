package com.example.lab9.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM task")
    LiveData<List<Task>> getAll();

    @Query("SELECT * FROM task WHERE id = :id")
    Task getById(int id);

    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

    @Query("DELETE FROM task")
    void deleteAllNotes();
}
