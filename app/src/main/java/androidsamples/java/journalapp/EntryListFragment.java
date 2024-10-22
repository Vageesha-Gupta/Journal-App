package androidsamples.java.journalapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

/**
 * A fragment representing a list of Items.
 */
public class EntryListFragment extends Fragment {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return  inflater.inflate(R.layout.fragment_entry_list, container, false);

  }
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
    super.onViewCreated(view, savedInstanceState);
    View addEntryButton = view.findViewById(R.id.btn_add_entry);
    addEntryButton.setOnClickListener(v -> {
      NavController navController = Navigation.findNavController(view);
      navController.navigate(R.id.addEntryAction);
    });

  }

}