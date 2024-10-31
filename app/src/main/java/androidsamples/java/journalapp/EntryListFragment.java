package androidsamples.java.journalapp;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;


import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class EntryListFragment extends Fragment {

  private JournalViewModel journalViewModel;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_entry_list, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    Toolbar toolbar = view.findViewById(R.id.toolbar);
    ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    TextView toolbarTitle = view.findViewById(R.id.toolbar_title);
    toolbarTitle.setText("JournalApp");

    // Set up add entry button
    View addEntryButton = view.findViewById(R.id.btn_add_entry);
    addEntryButton.setOnClickListener(v -> {
      NavController navController = Navigation.findNavController(view);
      navController.navigate(R.id.addEntryAction); // Navigate to add entry screen
    });
//    View infoButton = view.findViewById(R.id.info_button); // Make sure this ID matches the XML
//    infoButton.setOnClickListener(v -> {
//      Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://jamesclear.com/atomic-habits"));
//      startActivity(browserIntent);
//    });
    ImageButton infoButton = view.findViewById(R.id.info_button); // Ensure this ID matches the XML
    infoButton.setOnClickListener(v -> {
      Log.d("InfoButton", "Info button clicked");
      Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://jamesclear.com/atomic-habits"));
      startActivity(browserIntent);
    });

    // Set up RecyclerView and adapter
    RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
    recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

    // Initialize ViewModel and observe data changes
    journalViewModel = new ViewModelProvider(this).get(JournalViewModel.class);
    journalViewModel.getAllEntries().observe(getViewLifecycleOwner(), entries -> {
      // Set adapter with retrieved entries list
      JournalEntryAdapter adapter = new JournalEntryAdapter(entries, this::navigateToEntryDetails);
      recyclerView.setAdapter(adapter);
    });
  }

  // Method to handle navigation with SafeArgs
  private void navigateToEntryDetails(int entryId) {
    EntryListFragmentDirections.AddEntryAction action = EntryListFragmentDirections.addEntryAction();
    action.setEntryId(entryId); // Pass the entry ID to the action
    Navigation.findNavController(requireView()).navigate(action);
  }
}

//package androidsamples.java.journalapp;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.navigation.NavController;
//import androidx.navigation.Navigation;
//import androidx.recyclerview.widget.RecyclerView;
//
///**
// * A fragment representing a list of Items.
// */
//@Override
//public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//  super.onViewCreated(view, savedInstanceState);
//
//  View addEntryButton = view.findViewById(R.id.btn_add_entry);
//  addEntryButton.setOnClickListener(v -> {
//    NavController navController = Navigation.findNavController(view);
//    navController.navigate(R.id.addEntryAction);
//  });
//
//  // Assuming RecyclerView setup here, pass in a click listener to the adapter
//  RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
//  JournalEntryAdapter adapter = new JournalEntryAdapter(entries, entryId -> {
//    // Navigate to EntryDetailsFragment with the entry ID
//    EntryListFragmentDirections.AddEntryAction action = EntryListFragmentDirections.addEntryAction();
//    action.setEntryId(entryId);
//    Navigation.findNavController(view).navigate(action);
//  });
//  MyAdapter adapter = new MyAdapter(this::navigateToEntryDetails); // Pass method reference
//  recyclerView.setAdapter(adapter);
//}
//
//// Method to handle navigation
//private void navigateToEntryDetails(int entryId) {
//  // Use SafeArgs for navigation
//  EntryListFragmentDirections.AddEntryAction action = EntryListFragmentDirections.addEntryAction();
//  action.setEntryId(entryId); // Pass the entry ID
//  Navigation.findNavController(requireView()).navigate(action);
//}
////public class EntryListFragment extends Fragment {
////
////  @Override
////  public void onCreate(Bundle savedInstanceState) {
////    super.onCreate(savedInstanceState);
////  }
////
////  @Override
////  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
////                           Bundle savedInstanceState) {
////    return  inflater.inflate(R.layout.fragment_entry_list, container, false);
////
////  }
////  @Override
////  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
////    super.onViewCreated(view, savedInstanceState);
////    View addEntryButton = view.findViewById(R.id.btn_add_entry);
////    addEntryButton.setOnClickListener(v -> {
////      NavController navController = Navigation.findNavController(view);
////      navController.navigate(R.id.addEntryAction);
////    });
////
////  }
////
////}