package androidsamples.java.journalapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;


public class MainActivity extends AppCompatActivity implements DatePickerFragment.DatePickerListener, TimePickerFragment.TimePickerListener {

  private TextView dateTextView;
  private TextView startTimeTextView;
  private TextView endTimeTextView;
  private TextView descriptionTextView;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
    NavController navController = navHostFragment.getNavController();
  }
  @Override
  public void onDateSelected(int year, int month, int day) {
    // Handle the date selected event here
    // For example, you might want to pass the date back to the EntryDetailsFragment
    EntryDetailsFragment fragment = (EntryDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
    if (fragment != null) {
      fragment.updateDate(year, month, day);
    }
  }
  @Override
  public void onTimeSelected(int hour, int minute, boolean isStartTime) {
    // Handle the time selected event here
    EntryDetailsFragment fragment = (EntryDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
    if (fragment != null) {
      fragment.updateTime(hour, minute, isStartTime);
    }
  }
//  @Override
//  public boolean onCreateOptionsMenu(Menu menu) {
//    getMenuInflater().inflate(R.menu.menu_main, menu);
//    return false;
//  }
//  @Override
//  public boolean onOptionsItemSelected(MenuItem item) {
//    switch (item.getItemId()) {
//      case R.id.action_save:
//        // Handle save action
//        saveEntry(); // Create this method to handle saving the entry
//        return true;
//      case R.id.action_view_entries:
//        // Navigate to view entries
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        navController.navigate(R.id.action_view_entries);
//        return true;
//      default:
//        return super.onOptionsItemSelected(item);
//    }
//  }

  private void saveEntry() {
    // Find the current fragment in the NavHostFragment
    NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
    Fragment currentFragment = navHostFragment.getChildFragmentManager().getPrimaryNavigationFragment();

    // Check if the current fragment is EntryDetailsFragment
    if (currentFragment instanceof EntryDetailsFragment) {
      EntryDetailsFragment entryDetailsFragment = (EntryDetailsFragment) currentFragment;

      // Get the views from the fragment's layout
      String description = ((EditText) entryDetailsFragment.getView().findViewById(R.id.edit_title)).getText().toString().trim();
      String date = ((TextView) entryDetailsFragment.getView().findViewById(R.id.btn_entry_date)).getText().toString();
      String startTime = ((TextView) entryDetailsFragment.getView().findViewById(R.id.btn_start_time)).getText().toString();
      String endTime = ((TextView) entryDetailsFragment.getView().findViewById(R.id.btn_end_time)).getText().toString();
//      String description = ((EditText) entryDetailsFragment.getView().findViewById(R.id.edit_description)).getText().toString();

      // Validate fields
      if (description.isEmpty() || date.isEmpty() || startTime.isEmpty() || endTime.isEmpty()) {
        StringBuilder missingFields = new StringBuilder("Please fill in the following fields: ");
        if (description.isEmpty()) missingFields.append("Title, ");
        if (date.isEmpty()) missingFields.append("Date, ");
        if (startTime.isEmpty()) missingFields.append("Start Time, ");
        if (endTime.isEmpty()) missingFields.append("End Time, ");
        missingFields.setLength(missingFields.length() - 2);  // Remove last comma and space
        Toast.makeText(this, missingFields.toString(), Toast.LENGTH_SHORT).show();
        return;
      }

      // Create and save the JournalEntry
      JournalEntry entry = new JournalEntry(date, startTime, endTime, description);
      JournalViewModel viewModel = new ViewModelProvider(this).get(JournalViewModel.class);
      viewModel.insert(entry);

      Toast.makeText(this, "Entry saved successfully!", Toast.LENGTH_SHORT).show();
    }
  }


//  private void saveEntry() {
//    String title = ((EditText) findViewById(R.id.edit_title)).getText().toString().trim();
//    String date = dateTextView.getText().toString();
//    String startTime = startTimeTextView.getText().toString();
//    String endTime = endTimeTextView.getText().toString();
////    String description = descriptionTextView.getText().toString();
//
//    if (title.isEmpty() || date.isEmpty() || startTime.isEmpty() || endTime.isEmpty()) {
//      // Build a message with missing fields
//      StringBuilder missingFields = new StringBuilder("Please fill in the following fields: ");
//      if (title.isEmpty()) missingFields.append("Title, ");
//      if (date.isEmpty()) missingFields.append("Date, ");
//      if (startTime.isEmpty()) missingFields.append("Start Time, ");
//      if (endTime.isEmpty()) missingFields.append("End Time, ");
//
//      // Remove the last comma and space
//      missingFields.setLength(missingFields.length() - 2);
//
//      // Display a Toast message
//      Toast.makeText(this, missingFields.toString(), Toast.LENGTH_SHORT).show();
//      return; // Exit the method early if validation fails
//    }
//
//    // Create a new JournalEntry object
//    JournalEntry entry = new JournalEntry(date, startTime, endTime,description);
//    entry.setDate(date);
//    entry.setStartTime(startTime);
//    entry.setEndTime(endTime);
//    entry.setDescription(description);
//
//    // Use a ViewModel to interact with the database
//    JournalViewModel viewModel = new ViewModelProvider(this).get(JournalViewModel.class);
//
//    // Insert the entry into the database
//    viewModel.insert(entry);
//
//  }





}