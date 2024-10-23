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

    public interface TimePickerListener {
        void onTimeSelected(int hour, int minute, boolean isStartTime);
    }

    private TimePickerListener listener;
    private boolean isStartTime;

  @NonNull
  public static TimePickerFragment newInstance(Date time,boolean isStartTime) {
    TimePickerFragment fragment = new TimePickerFragment();
    Bundle args = new Bundle();
    args.putInt("hour", time.getHours());
    args.putBoolean("isStartTime", isStartTime);
    args.putInt("minute", time.getMinutes());

    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      if (getTargetFragment() instanceof TimePickerListener) {
          listener = (TimePickerListener) getTargetFragment();
      } else {
          throw new ClassCastException("Target fragment must implement TimePickerListener");
      }
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
      Calendar c = Calendar.getInstance();
      int hour = getArguments() != null ? getArguments().getInt("hour", c.get(Calendar.HOUR_OF_DAY)): c.get(Calendar.HOUR_OF_DAY);
      int minute = getArguments() != null ? getArguments().getInt("minute", c.get(Calendar.MINUTE)): c.get(Calendar.MINUTE);

      isStartTime = getArguments() != null && getArguments().getBoolean("isStartTime", true);

      return new TimePickerDialog(requireContext(), (view, selectedHour, selectedMinute) -> {
          if (listener != null) {
              listener.onTimeSelected(selectedHour, selectedMinute, isStartTime);
          }
      }, hour, minute, true);
  }
}
