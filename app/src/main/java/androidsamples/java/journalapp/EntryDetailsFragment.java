package androidsamples.java.journalapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import org.w3c.dom.Text;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EntryDetailsFragment # newInstance} factory method to
 * create an instance of this fragment.
 */


public class EntryDetailsFragment extends Fragment implements DatePickerFragment.DatePickerListener,TimePickerFragment.TimePickerListener {

  private TextView dateTextView;
  private TextView startTimeTextView;
  private TextView endTimeTextView;
  private TextView descriptionTextView;
  private JournalViewModel journalViewModel;
//  private TextView descriptionTextView;
  private JournalEntry currentJournalEntry;
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view= inflater.inflate(R.layout.fragment_entry_details, container, false);
    Toolbar toolbar = view.findViewById(R.id.toolbar);
    ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    Toolbar toolbar = view.findViewById(R.id.toolbar);
    ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//    TextView toolbarTitle = view.findViewById(R.id.toolbar_title);
//    toolbarTitle.setText("JournalApp");

    dateTextView = view.findViewById(R.id.btn_entry_date);
    startTimeTextView = view.findViewById(R.id.btn_start_time);
    endTimeTextView = view.findViewById(R.id.btn_end_time);
    descriptionTextView = view.findViewById(R.id.edit_title);
//    descriptionTextView = view.findViewById(R.id.edit_description);

    journalViewModel = new ViewModelProvider(requireActivity()).get(JournalViewModel.class);

    getJournalEntry();

//    dateTextView.setOnClickListener(v -> {
//      NavController navController = Navigation.findNavController(view);
//        navController.navigate(R.id.datePickerAction);
//    });
  dateTextView.setOnClickListener(v -> {
    Date currentDate = new Date(); // You may want to pass the current date or the existing date
    DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(currentDate);
    datePickerFragment.setTargetFragment(EntryDetailsFragment.this, 0); // Set the target fragment to receive the result
    datePickerFragment.show(getParentFragmentManager(), "datePicker");
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
    ImageButton deleteButton = view.findViewById(R.id.btn_delete_entry);
    deleteButton.setOnClickListener(v -> {
      if (currentJournalEntry == null) {
        // Show a message if there's no entry to delete
        Toast.makeText(getContext(), "No entry to delete.", Toast.LENGTH_SHORT).show();
      } else {
        // Show confirmation dialog if an entry exists
        new AlertDialog.Builder(requireContext())
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton("Yes", (dialog, which) -> {
                  // Delete the entry only if it exists
                  journalViewModel.delete(currentJournalEntry);
                  // Navigate back after deletion
                  NavHostFragment.findNavController(EntryDetailsFragment.this).navigateUp();
                })
                .setNegativeButton("No", null)
                .show();
      }
    });
    ImageButton shareButton = view.findViewById(R.id.btn_share_entry);
    shareButton.setOnClickListener(v -> {
      if (currentJournalEntry != null) {
        String shareText = "Look what I have been up to: " + currentJournalEntry.getDescription() +
                " on " + currentJournalEntry.getDate() + ", " + currentJournalEntry.getStartTime() + " to " + currentJournalEntry.getEndTime();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Share via"));
      }
    });
//    descriptionTextView.setOnClickListener(v -> {
//      // Handle click event for title if needed (e.g., you could open a dialog)
//      Toast.makeText(getContext(), "Title clicked", Toast.LENGTH_SHORT).show();
//    });
    Button saveButton = view.findViewById(R.id.btn_save);
    saveButton.setOnClickListener(v -> saveEntry());


  }
  @Override
  public void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putString("date", dateTextView.getText().toString());
    outState.putString("startTime", startTimeTextView.getText().toString());
    outState.putString("endTime", endTimeTextView.getText().toString());
    outState.putString("description", descriptionTextView.getText().toString());
  }

  @Override
  public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
    super.onViewStateRestored(savedInstanceState);
    if (savedInstanceState != null) {
      dateTextView.setText(savedInstanceState.getString("date"));
      startTimeTextView.setText(savedInstanceState.getString("startTime"));
      endTimeTextView.setText(savedInstanceState.getString("endTime"));
      descriptionTextView.setText(savedInstanceState.getString("description"));
    }
  }

  @Override
  public void onDateSelected(int year, int month, int day) {
    updateDate(year, month, day);
  }

  public void updateDate(int year, int month, int day) {
    String selectedDate = day + "/" + (month ) + "/" + year; // Format the date correctly by adding 1 to the month
    dateTextView.setText(selectedDate); // Display the selected date in the TextView
//    String selectedDate = day + "/" + month + "/" + year; // Format the date
//    dateTextView.setText(selectedDate); // Display the selected date in the TextView
  }
  public void updateTime(int hour, int minute, boolean isStartTime) {
    String time = String.format("%02d:%02d", hour, minute);
    if (isStartTime) {
      startTimeTextView.setText(time); // Update the start time
    } else {
      endTimeTextView.setText(time); // Update the end time
    }
  }
  private void saveEntry() {
    String date = dateTextView.getText().toString();
    String startTime = startTimeTextView.getText().toString();
    String endTime = endTimeTextView.getText().toString();
    String description = descriptionTextView.getText().toString(); // Use this if you have a description field

    if (isFieldEmpty(date, "DATE") || isFieldEmpty(description, "") || isFieldEmpty(startTime, "START TIME") || isFieldEmpty(endTime, "END TIME")) {
      // Handle error: All fields must be filled
      Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
      return;
    }

    if (isFutureDate(date)) {
      Toast.makeText(getContext(), "Selected date cannot be in the future", Toast.LENGTH_SHORT).show();
      return;
    }
    // Time validation: Check if end time is before start time
    if (isEndTimeBeforeStartTime(startTime, endTime)) {
      Toast.makeText(getContext(), "End time must be after start time", Toast.LENGTH_SHORT).show();
      return;
    }



    if (currentJournalEntry != null) {
      // We're editing an existing entry, so update it
      currentJournalEntry.setDate(date);
      currentJournalEntry.setStartTime(startTime);
      currentJournalEntry.setEndTime(endTime);
      currentJournalEntry.setDescription(description); // Add if applicable

      // Update entry in the ViewModel
      journalViewModel.update(currentJournalEntry);
    } else {
      // We're creating a new entry
      JournalEntry entry = new JournalEntry();
      entry.setDate(date);
      entry.setStartTime(startTime);
      entry.setEndTime(endTime);
      entry.setDescription(description); // Add if applicable

      // Save the new entry to the ViewModel
      journalViewModel.insert(entry);
    }

    // Navigate back to the list
    NavHostFragment.findNavController(this).navigateUp();
  }
  private boolean isEndTimeBeforeStartTime(String startTime, String endTime) {
    try {
      // Parse the times as Date objects
      SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
      Date start = timeFormat.parse(startTime);
      Date end = timeFormat.parse(endTime);

      // Compare the times
      return end != null && start != null && (end.before(start) || end.equals(start)); // return true if end time is before start time
    } catch (ParseException e) {
      e.printStackTrace();
      return false; // In case of a parse error, we return false (time is valid)
    }
  }


  private boolean isFieldEmpty(String fieldValue, String defaultValue) {
    return fieldValue.isEmpty() || fieldValue.equalsIgnoreCase(defaultValue);
  }
  private boolean isFutureDate(String selectedDate) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    try {
      Date selected = dateFormat.parse(selectedDate);
      Date currentDate = new Date();
      return selected != null && selected.after(currentDate); // Return true if the selected date is in the future
    } catch (ParseException e) {
      e.printStackTrace();
      return false; // If parsing fails, return false
    }
  }

  @Override
  public void onTimeSelected(int hour, int minute, boolean isStartTime) {
    updateTime(hour, minute, isStartTime);
  }
  private void getJournalEntry() {
    int entryId = EntryDetailsFragmentArgs.fromBundle(getArguments()).getEntryId();
    if (entryId != -1) {
      // Observe LiveData from the ViewModel for this entry ID
      journalViewModel.getEntryById(entryId).observe(getViewLifecycleOwner(), journalEntry -> {
        if (journalEntry != null) {
          // Set currentJournalEntry to the retrieved entry for later use
          currentJournalEntry = journalEntry;

          // Populate the UI fields with data from the journal entry
          dateTextView.setText(journalEntry.getDate());
          startTimeTextView.setText(journalEntry.getStartTime());
          endTimeTextView.setText(journalEntry.getEndTime());
          descriptionTextView.setText(journalEntry.getDescription());
          // Assuming a TextView for description
//          descriptionTextView.setText(journalEntry.getDescription());
        }
      });
    }
//    int entryId = getArguments().getInt("entryId",-1); // Retrieve entry ID from arguments
//    if (entryId == -1) {
//      return;
//    }
//
//    journalViewModel.getEntryById(entryId).observe(getViewLifecycleOwner(), journalEntry -> {
//      if (journalEntry != null) {
//        currentJournalEntry = journalEntry;
//        dateTextView.setText(journalEntry.getDate());
//        startTimeTextView.setText(journalEntry.getStartTime());
//        endTimeTextView.setText(journalEntry.getEndTime());
//      }});

  }


}