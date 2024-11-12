package androidsamples.java.journalapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

public class DatePickerFragment extends DialogFragment {

  public interface DatePickerListener {
    void onDateSelected(int year, int month, int day);
  }

  private DatePickerListener listener;
  @NonNull
  public static DatePickerFragment newInstance(Date date) {
    DatePickerFragment fragment = new DatePickerFragment();
    Bundle args = new Bundle();
    args.putLong("date", date.getTime());
    fragment.setArguments(args);
    return fragment;
  }

  public void onCreate(@NonNull Bundle savedInstanceState) {
//    super.onCreate(context);
//    if (context instanceof DatePickerListener) {
//      listener = (DatePickerListener) context;
//    } else {
//      throw new ClassCastException(context.toString() + " must implement DatePickerListener");
//    }
    super.onCreate(savedInstanceState);
    if (getTargetFragment() instanceof DatePickerFragment.DatePickerListener) {
      listener = (DatePickerFragment.DatePickerListener) getTargetFragment();
    } else {
      throw new ClassCastException("Target fragment must implement DatePickerListener");
    }
  }
//  private void onDateSet(int year, int month, int day) {
//    // Notify the listener (which is EntryDetailsFragment)
//    if (listener != null) {
//      listener.onDateSelected(year, month, day);
//    }
//    dismiss(); // Close the dialog after selection
//  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
//    Calendar c = Calendar.getInstance();
//    long dateInMillis = getArguments() != null ? getArguments().getLong("date") : System.currentTimeMillis();
//    c.setTimeInMillis(dateInMillis); // Set time using the passed date
//
//    int year = c.get(Calendar.YEAR);
//    int month = c.get(Calendar.MONTH);
//    int day = c.get(Calendar.DAY_OF_MONTH);
//
//    return new DatePickerDialog(requireContext(), (dp, y, m, d) -> {
//      if (listener != null) {
//        listener.onDateSelected(y, m, d);
//      }
//    }, year, month, day);
//  }
    Calendar c = Calendar.getInstance();
    int year = getArguments() != null ? getArguments().getInt("year", c.get(Calendar.YEAR)):c.get(Calendar.YEAR);
    int month = getArguments() != null ? getArguments().getInt("month", c.get(Calendar.MONTH)):c.get(Calendar.MONTH);
    int day = getArguments() != null ? getArguments().getInt("day", c.get(Calendar.DAY_OF_MONTH)):c.get(Calendar.DAY_OF_MONTH);

    return new DatePickerDialog(requireContext(), (dp, y, m, d) -> {
//      EntryDetailsFragment fragment = (EntryDetailsFragment) getParentFragment();
      if (listener != null) {
        listener.onDateSelected(y, m + 1, d);
      }
    }, year, month, day);
  }
}
