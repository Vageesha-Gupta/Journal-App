package androidsamples.java.journalapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EntryDetailsFragment # newInstance} factory method to
 * create an instance of this fragment.
 */


public class EntryDetailsFragment extends Fragment implements DatePickerFragment.DatePickerListener,TimePickerFragment.TimePickerListener {

  private TextView dateTextView;
  private TextView startTimeTextView;
  private TextView endTimeTextView;
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_entry_details, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    dateTextView = view.findViewById(R.id.btn_entry_date);
    startTimeTextView = view.findViewById(R.id.btn_start_time);
    endTimeTextView = view.findViewById(R.id.btn_end_time);

    dateTextView.setOnClickListener(v -> {
      NavController navController = Navigation.findNavController(view);
        navController.navigate(R.id.datePickerAction);
    });

    startTimeTextView.setOnClickListener(v -> {
//      NavController navController = Navigation.findNavController(view);
//      Bundle bundle = new Bundle();
//      bundle.putBoolean("isStartTime", true);
//      navController.navigate(R.id.timePickerAction,bundle);
      TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(new Date(), true);
      timePickerFragment.setTargetFragment(EntryDetailsFragment.this, 0); // Set the target fragment to receive the result
      timePickerFragment.show(getParentFragmentManager(), "timePicker");
    });
    endTimeTextView.setOnClickListener(v -> {
//      NavController navController = Navigation.findNavController(view);
//      Bundle bundle = new Bundle();
//      bundle.putBoolean("isStartTime", false); // Indicate that this is for the end time
//      navController.navigate(R.id.timePickerAction, bundle);
      TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(new Date(), false);
      timePickerFragment.setTargetFragment(EntryDetailsFragment.this, 0); // Set the target fragment to receive the result
      timePickerFragment.show(getParentFragmentManager(), "timePicker");
    });
  }
  @Override
  public void onDateSelected(int year, int month, int day) {
    updateDate(year, month, day);
  }

  public void updateDate(int year, int month, int day) {
    String selectedDate = day + "/" + month + "/" + year; // Format the date
    dateTextView.setText(selectedDate); // Display the selected date in the TextView
  }
  public void updateTime(int hour, int minute, boolean isStartTime) {
    String time = String.format("%02d:%02d", hour, minute);
    if (isStartTime) {
      startTimeTextView.setText(time); // Update the start time
    } else {
      endTimeTextView.setText(time); // Update the end time
    }
  }
  @Override
  public void onTimeSelected(int hour, int minute, boolean isStartTime) {
    updateTime(hour, minute, isStartTime);
  }


}