package androidsamples.java.journalapp;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;

public class JournalRepository {
    private JournalEntryDao journalEntryDao;
    private LiveData<List<JournalEntry>> allEntries;

    public JournalRepository(Application application) {
        JournalDatabase db = JournalDatabase.getDatabase(application);
        journalEntryDao = db.journalEntryDao();
        allEntries = journalEntryDao.getAllEntries();
    }
    

    // Insert method
    public void insert(JournalEntry entry) {
        JournalDatabase.databaseWriteExecutor.execute(() -> {
            journalEntryDao.insert(entry);
        });
    }

    // Update method
    public void update(JournalEntry entry) {
        JournalDatabase.databaseWriteExecutor.execute(() -> {
            journalEntryDao.update(entry);
        });
    }
    // Delete method
    public void delete(JournalEntry entry) {
        JournalDatabase.databaseWriteExecutor.execute(() -> {
            journalEntryDao.delete(entry);
        });
    }
    public LiveData<JournalEntry> getEntryById(int id) {
        return journalEntryDao.getEntryById(id); // Retrieve entry by ID
    }
    public LiveData<List<JournalEntry>> getAllEntries() {
        return allEntries;
    }
}

