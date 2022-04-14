package com.example.timetablemanager;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Task.class}, version = 1, exportSchema = false)
public abstract class TaskDatabase extends RoomDatabase {
    private static TaskDatabase instance;

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract TaskDao taskDao();
    public static synchronized TaskDatabase getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(), TaskDatabase.class, "task_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomDataBaseCallback)
                    .build();
        }
        return instance;
    }
    private static final RoomDatabase.Callback roomDataBaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            databaseWriteExecutor.execute(()->{
                TaskDao dao = instance.taskDao();
                dao.deleteAll();
                dao.insert(new Task("Title1", "Description1", "Time1"));
                dao.insert(new Task("Title2", "Description2", "Time2"));


            });
        }
    };
}
