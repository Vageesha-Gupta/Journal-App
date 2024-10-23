package androidsamples.java.journalapp;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {JournalEntry.class}, version = 1, exportSchema = false)
public abstract class JournalDatabase extends RoomDatabase {

    public abstract JournalEntryDao journalEntryDao();

    private static volatile JournalDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static JournalDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (JournalDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    JournalDatabase.class, "journal_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

