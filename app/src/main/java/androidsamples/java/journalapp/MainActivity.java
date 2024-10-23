package androidsamples.java.journalapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;



public class MainActivity extends AppCompatActivity implements DatePickerFragment.DatePickerListener, TimePickerFragment.TimePickerListener {

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

}