package androidsamples.java.journalapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements DatePickerFragment.DatePickerListener, TimePickerFragment.TimePickerListener {

  private TextView dateTextView;
  private TextView startTimeTextView;
  private TextView endTimeTextView;
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
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_save:
        // Handle save action
        saveEntry(); // Create this method to handle saving the entry
        return true;
      case R.id.action_view_entries:
        // Navigate to view entries
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.navigate(R.id.action_view_entries);
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void saveEntry() {
    String date = dateTextView.getText().toString();
    String startTime = startTimeTextView.getText().toString();
    String endTime = endTimeTextView.getText().toString();

    // Create a new JournalEntry object
    JournalEntry entry = new JournalEntry(date, startTime, endTime);
    entry.setDate(date);
    entry.setStartTime(startTime);
    entry.setEndTime(endTime);

    // Use a ViewModel to interact with the database
    JournalViewModel viewModel = new ViewModelProvider(this).get(JournalViewModel.class);

    // Insert the entry into the database
    viewModel.insert(entry);

  }





}