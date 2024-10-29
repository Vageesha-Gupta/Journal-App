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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


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
  private ExecutorService executorService;

  @Before
  public void setUp() {
    Context context = ApplicationProvider.getApplicationContext();
    database = Room.inMemoryDatabaseBuilder(context, JournalDatabase.class).allowMainThreadQueries().build();
    journalEntryDao = database.journalEntryDao();
    executorService = Executors.newSingleThreadExecutor();
  }

  @After
  public void tearDown() {
    database.close();
    executorService.shutdown();
  }

  @Test
  public void testInsertAndGetEntries() throws InterruptedException {
    // Arrange: Insert a sample entry
//    JournalEntry entry = new JournalEntry("2024-10-25", "08:00", "10:00","Homework");
//    journalEntryDao.insert(entry);
//    CountDownLatch latch = new CountDownLatch(1);
//
//    executorService.execute(() -> {
//      journalEntryDao.insert(entry);
//      latch.countDown(); // Count down after insert
//    });
//    latch.await();
//
//    // Act & Assert: Observe the LiveData directly and verify the inserted entry
//    LiveData<List<JournalEntry>> liveDataEntries = journalEntryDao.getAllEntries();
//    liveDataEntries.observeForever(entries -> {
//      assertNotNull(entries);
//      assertTrue(entries.size() > 0);
//      assertEquals("08:00", entries.get(0).getStartTime());
//      latch.countDown();
//    });
//    latch.await();
    CountDownLatch latch = new CountDownLatch(1);

    // Insert entry in background thread
    executorService.execute(() -> {
      JournalEntry entry = new JournalEntry("2024-10-25", "08:00", "10:00", "Homework");
      journalEntryDao.insert(entry);
      latch.countDown();
    });

    // Wait for insert to complete
    assertTrue(latch.await(2, TimeUnit.SECONDS));

    // Create new latch for LiveData observation
    CountDownLatch observerLatch = new CountDownLatch(1);

    // Observe the LiveData
    journalEntryDao.getAllEntries().observeForever(entries -> {
      if (entries != null && !entries.isEmpty()) {
        assertEquals("Homework", entries.get(0).getTitle());
        assertEquals("08:00", entries.get(0).getStartTime());
        observerLatch.countDown();
      }
    });

    // Wait for observation to complete
    assertTrue(observerLatch.await(2, TimeUnit.SECONDS));
  }


  @Test
  public void testUpdateEntry() throws InterruptedException {
//    // Arrange: Insert a sample entry
//    JournalEntry entry = new JournalEntry("2024-10-25", "08:00", "10:00","Dishes");
//    journalEntryDao.insert(entry);
//
//    CountDownLatch latch = new CountDownLatch(1);
//
//    executorService.execute(() -> {
//      journalEntryDao.insert(entry);
//      latch.countDown();
//    });
//    latch.await();
//
//    // Update the entry
//    entry.setStartTime("09:00");
//    journalEntryDao.update(entry);
//    executorService.execute(() -> {
//      journalEntryDao.insert(entry);
//      latch.countDown();
//    });
//    latch.await();
//
//    // Act: Observe the LiveData to get the updated entry
//    LiveData<List<JournalEntry>> liveDataEntries = journalEntryDao.getAllEntries();
//
//    // Wait for LiveData to deliver the result
//    liveDataEntries.observeForever(entries -> {
//      assertNotNull(entries);
//      assertFalse(entries.isEmpty());
//      assertEquals("09:00", entries.get(0).getStartTime());
//      latch.countDown();
//    });
//    latch.await();
    CountDownLatch insertLatch = new CountDownLatch(1);
    final JournalEntry[] entry = new JournalEntry[1];

    // Insert entry
    executorService.execute(() -> {
      entry[0] = new JournalEntry("2024-10-25", "08:00", "10:00", "Dishes");
      journalEntryDao.insert(entry[0]);
      insertLatch.countDown();
    });

    assertTrue(insertLatch.await(2, TimeUnit.SECONDS));

    // Update entry
    CountDownLatch updateLatch = new CountDownLatch(1);
    executorService.execute(() -> {
      entry[0].setStartTime("09:00");
      journalEntryDao.update(entry[0]);
      updateLatch.countDown();
    });

    assertTrue(updateLatch.await(2, TimeUnit.SECONDS));

    // Observe updated data
    CountDownLatch observerLatch = new CountDownLatch(1);
    journalEntryDao.getAllEntries().observeForever(entries -> {
      if (entries != null && !entries.isEmpty()) {
        assertEquals("09:00", entries.get(0).getStartTime());
        observerLatch.countDown();
      }
    });

    assertTrue(observerLatch.await(2, TimeUnit.SECONDS));
  }

  @Test
  public void testDeleteEntry() throws InterruptedException {
    // Arrange: Insert and then delete a sample entry
//    JournalEntry entry = new JournalEntry("2024-10-25", "08:00", "10:00","Clothes");
//    journalEntryDao.insert(entry);
//    CountDownLatch latch = new CountDownLatch(1);
//
//    executorService.execute(() -> {
//      journalEntryDao.insert(entry);
//      latch.countDown();
//    });
//    latch.await();
//
//
//
//    // Deleting the entry
//    journalEntryDao.delete(entry);
//
//    executorService.execute(() -> {
//      journalEntryDao.insert(entry);
//      latch.countDown();
//    });
//    latch.await();
//
//
//    // Act & Assert: Observe the LiveData to confirm the entry was deleted
//    LiveData<List<JournalEntry>> liveDataEntries = journalEntryDao.getAllEntries();
//    liveDataEntries.observeForever(entries -> {
//      assertNotNull(entries);
//      assertTrue(entries.isEmpty());
//      latch.countDown();// Ensure the list is empty after deletion
//    });
//    latch.await();
    CountDownLatch insertLatch = new CountDownLatch(1);
    final JournalEntry[] entry = new JournalEntry[1];

    // Insert entry
    executorService.execute(() -> {
      entry[0] = new JournalEntry("2024-10-25", "08:00", "10:00", "Clothes");
      journalEntryDao.insert(entry[0]);
      insertLatch.countDown();
    });

    assertTrue(insertLatch.await(2, TimeUnit.SECONDS));

    // Delete entry
    CountDownLatch deleteLatch = new CountDownLatch(1);
    executorService.execute(() -> {
      journalEntryDao.delete(entry[0]);
      deleteLatch.countDown();
    });

    assertTrue(deleteLatch.await(2, TimeUnit.SECONDS));

    // Verify deletion
    CountDownLatch observerLatch = new CountDownLatch(1);
    journalEntryDao.getAllEntries().observeForever(entries -> {
      assertNotNull(entries);
      assertTrue(entries.isEmpty());
      observerLatch.countDown();
    });

    assertTrue(observerLatch.await(2, TimeUnit.SECONDS));
  }
}