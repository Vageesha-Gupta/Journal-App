package androidsamples.java.journalapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface JournalEntryDao {
    @Insert
    void insert(JournalEntry entry);

    @Update
    void update(JournalEntry entry);

    @Delete
    void delete(JournalEntry entry);

    @Query("SELECT * FROM journal_entries")
    LiveData<List<JournalEntry>> getAllEntries();

    @Query("SELECT * FROM journal_entries WHERE id = :id LIMIT 1") // Ensure 'id' is the correct column name
    JournalEntry getEntryById(int id);

}
