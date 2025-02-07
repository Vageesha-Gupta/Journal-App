package androidsamples.java.journalapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {JournalEntry.class}, version = 2, exportSchema = false)
public abstract class JournalDatabase extends RoomDatabase {

    public abstract JournalEntryDao journalEntryDao();

    private static volatile JournalDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    // Database migration from version 1 to version 2
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Adding a new column 'location' to the 'JournalEntry' table
            database.execSQL("ALTER TABLE journal_entries ADD COLUMN location TEXT");
        }
    };
    public static JournalDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (JournalDatabase.class) {
                if (INSTANCE == null) {
//                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
//                                    JournalDatabase.class, "journal_database")
//                            .build();
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    JournalDatabase.class, "journal_database")
                            .addMigrations(MIGRATION_1_2) // Add the migration here
                            .build();

                }
            }
        }
        return INSTANCE;
    }
}

