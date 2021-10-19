package com.example.lab6;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class TaskListFragment extends ListFragment {

    CustomAdapter adapter;
    List<Task> taskList;

    static interface TaskListListener {
        void itemClicked(int position);
    };
    private TaskListListener listener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (TaskListListener)context;
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (listener != null) {
            listener.itemClicked(position);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        taskList = JSONHelper.importFromJSON(inflater.getContext());
        adapter = new CustomAdapter(inflater.getContext(), R.layout.custom_listview, taskList);
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);

    }
}