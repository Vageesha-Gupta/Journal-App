package androidsamples.java.journalapp;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class TimePickerFragment extends DialogFragment {

  @NonNull
  public static TimePickerFragment newInstance(Date time) {
    TimePickerFragment fragment = new TimePickerFragment();
    Bundle args = new Bundle();
    args.putInt("hours", time.getHours());
    args.putInt("minute", time.getMinutes());
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // TODO implement the method
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
      Calendar c = Calendar.getInstance();
      int hour = getArguments() != null ? getArguments().getInt("hour", c.get(Calendar.HOUR_OF_DAY)): c.get(Calendar.HOUR_OF_DAY);
      int minute = getArguments() != null ? getArguments().getInt("minute", c.get(Calendar.MINUTE)): c.get(Calendar.MINUTE);
      return new TimePickerDialog(requireContext(), (view, selectedHour, selectedMinute) -> {
          // Pass the selected time back to EntryDetailsFragment
          EntryDetailsFragment fragment = (EntryDetailsFragment) getParentFragment();
          if (fragment != null) {
              boolean isStartTime = getArguments() != null && getArguments().getBoolean("isStartTime", true);
              fragment.updateTime(selectedHour, selectedMinute, isStartTime);
          }
      }, hour, minute, true);
  }
}
