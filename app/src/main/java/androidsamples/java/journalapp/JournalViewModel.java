package androidsamples.java.journalapp;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;

import java.util.List;

public class JournalViewModel extends AndroidViewModel {
    private JournalRepository repository;
    private LiveData<List<JournalEntry>> allEntries;
    private final SavedStateHandle savedStateHandle;

    public JournalViewModel(Application application, @NonNull SavedStateHandle savedStateHandle) {
        super(application);
        repository = new JournalRepository(application);
        allEntries = repository.getAllEntries();
        this.savedStateHandle=savedStateHandle;
    }

    public void insert(JournalEntry entry) {

        new InsertEntryAsyncTask(repository).execute(entry);
    }
    public void update(JournalEntry entry) {
        new UpdateEntryAsyncTask(repository).execute(entry);
    }
//    public LiveData<List<JournalEntry>> getAllEntries() {
//        return allEntries; // Return all journal entries
//    }

    public LiveData<JournalEntry> getEntryById(int id) {
        return repository.getEntryById(id); // Adjust the repository to return LiveData
    }
    private static class UpdateEntryAsyncTask extends AsyncTask<JournalEntry, Void, Void> {
        private JournalRepository repository;

        private UpdateEntryAsyncTask(JournalRepository repository) {
            this.repository = repository;
        }

        @Override
        protected Void doInBackground(JournalEntry... entries) {
            repository.update(entries[0]); // Call the update method in the repository
            return null;
        }
    }

    // AsyncTask to insert entry
    private static class InsertEntryAsyncTask extends AsyncTask<JournalEntry, Void, Void> {
        private JournalRepository repository;

        private InsertEntryAsyncTask(JournalRepository repository) {
            this.repository = repository;
        }

        @Override
        protected Void doInBackground(JournalEntry... entries) {
            repository.insert(entries[0]);
            return null;
        }
    }
    public LiveData<List<JournalEntry>> getAllEntries() {
        return allEntries; // Return all journal entries
    }

    public void delete(JournalEntry entry) {
        new DeleteEntryAsyncTask(repository).execute(entry);
    }
    private static class DeleteEntryAsyncTask extends AsyncTask<JournalEntry, Void, Void> {
        private JournalRepository repository;

        private DeleteEntryAsyncTask(JournalRepository repository) {
            this.repository = repository;
        }

        @Override
        protected Void doInBackground(JournalEntry... entries) {
            repository.delete(entries[0]);  // Call the delete method in the repository
            return null;
        }
    }
//    public JournalEntry getEntryById(int id) {
//        return repository.getEntryById(id); // Fetch entry by ID
//    }


}
