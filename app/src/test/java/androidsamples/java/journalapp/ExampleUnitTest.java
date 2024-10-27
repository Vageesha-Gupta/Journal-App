package androidsamples.java.journalapp;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import java.net.HttpCookie;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleUnitTest {
//  @Test
//  public void addition_isCorrect() {
//    assertEquals(4, 2 + 2);
//  }
@Rule
public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

  private JournalDatabase database;
  private JournalEntryDao journalEntryDao;

  @Before
  public void setUp() {
    Context context = ApplicationProvider.getApplicationContext();
    database = Room.inMemoryDatabaseBuilder(context, JournalDatabase.class).build();
    journalEntryDao = database.journalEntryDao();
  }

  @After
  public void tearDown() {
    database.close();
  }

  @Test
  public void testInsertAndGetEntries() throws InterruptedException {
    // Arrange: Insert a sample entry
    JournalEntry entry = new JournalEntry("2024-10-25", "08:00", "10:00");
    journalEntryDao.insert(entry);

    // Act & Assert: Observe the LiveData directly and verify the inserted entry
    LiveData<List<JournalEntry>> liveDataEntries = journalEntryDao.getAllEntries();
    liveDataEntries.observeForever(entries -> {
      assertNotNull(entries);
      assertTrue(entries.size() > 0);
      assertEquals("08:00", entries.get(0).getStartTime());
    });
  }


  @Test
  public void testUpdateEntry() throws InterruptedException {
    // Arrange: Insert a sample entry
    JournalEntry entry = new JournalEntry("2024-10-25", "08:00", "10:00");
    journalEntryDao.insert(entry);

    // Update the entry
    entry.setStartTime("09:00");
    journalEntryDao.update(entry);

    // Act: Observe the LiveData to get the updated entry
    LiveData<List<JournalEntry>> liveDataEntries = journalEntryDao.getAllEntries();

    // Wait for LiveData to deliver the result
    liveDataEntries.observeForever(entries -> {
      assertNotNull(entries);
      assertFalse(entries.isEmpty());
      assertEquals("09:00", entries.get(0).getStartTime());
    });
  }

  @Test
  public void testDeleteEntry() throws InterruptedException {
    // Arrange: Insert and then delete a sample entry
    JournalEntry entry = new JournalEntry("2024-10-25", "08:00", "10:00");
    journalEntryDao.insert(entry);

    // Deleting the entry
    journalEntryDao.delete(entry);

    // Act & Assert: Observe the LiveData to confirm the entry was deleted
    LiveData<List<JournalEntry>> liveDataEntries = journalEntryDao.getAllEntries();
    liveDataEntries.observeForever(entries -> {
      assertNotNull(entries);
      assertTrue(entries.isEmpty());  // Ensure the list is empty after deletion
    });
  }
}