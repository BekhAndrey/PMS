package com.example.lab7;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class PriorityComparator implements Comparator<Task> {

    List<String> priorities = Arrays.asList("High", "Medium", "Low");
    @Override
    public int compare(Task task, Task t1) {
        return priorities.indexOf(task.getPriority()) - priorities.indexOf(t1.getPriority());
    }
}
