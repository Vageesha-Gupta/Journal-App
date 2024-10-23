package androidsamples.java.journalapp;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Database;


public abstract class AppDatabase extends RoomDatabase {
    public abstract JournalEntryDao journalEntryDao();

    private static AppDatabase instance;

    public static AppDatabase getDatabase(Context context){
        if(instance == null){
            synchronized (AppDatabase.class){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "journal_database")
                            .build();
                }
            }
        }
        return instance;
    }


}
