package com.example.timetablemanager;

import android.app.Application;


import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskRepository {
    private TaskDao taskDao;
    private LiveData<List<Task>> allTasks;

    public TaskRepository(Application application){
        TaskDatabase taskDatabase = TaskDatabase.getInstance(application);
        taskDao = taskDatabase.taskDao();
        allTasks = taskDao.getTasks();
    }
    public void insert(Task task){
            TaskDatabase.databaseWriteExecutor.execute(() -> {
                taskDao.insert(task);
            });
        }
    public void update(Task task){
        TaskDatabase.databaseWriteExecutor.execute(() -> {
            taskDao.update(task);
        });
    }
    public void delete(Task task){
        TaskDatabase.databaseWriteExecutor.execute(() -> {
            taskDao.delete(task);
        });
    }
    public void deleteAll(){
        TaskDatabase.databaseWriteExecutor.execute(() -> {
            taskDao.deleteAll();
        });
    }

    public LiveData<List<Task>> getAllTasks(){
        return allTasks;
    }
}
