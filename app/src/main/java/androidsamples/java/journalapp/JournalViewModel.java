package androidsamples.java.journalapp;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class JournalViewModel extends AndroidViewModel {
    private JournalRepository repository;

    public JournalViewModel(Application application) {
        super(application);
        repository = new JournalRepository(application);
    }

    public void insert(JournalEntry entry) {
        new InsertEntryAsyncTask(repository).execute(entry);
    }

    public LiveData<JournalEntry> getEntryById(int id) {
        return repository.getEntryById(id); // Adjust the repository to return LiveData
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
