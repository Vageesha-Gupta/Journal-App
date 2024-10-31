package androidsamples.java.journalapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class JournalEntryAdapter extends RecyclerView.Adapter<JournalEntryAdapter.EntryViewHolder> {
    private final List<JournalEntry> entries;
    private final OnEntryClickListener listener;

    public interface OnEntryClickListener {
        void onEntryClick(int entryId);
    }

    public JournalEntryAdapter(List<JournalEntry> entries, OnEntryClickListener listener) {
        this.entries = entries;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_entry, parent, false);
        return new EntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryViewHolder holder, int position) {
        JournalEntry entry = entries.get(position);
        holder.titleTextView.setText(entry.getDescription());
        holder.dateTextView.setText(entry.getDate());
        holder.startTimeTextView.setText(entry.getStartTime());
        holder.endTimeTextView.setText(entry.getEndTime());

        holder.itemView.setOnClickListener(v -> listener.onEntryClick(entry.getId())); // Pass entry ID to listener
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    static class EntryViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, dateTextView, startTimeTextView, endTimeTextView;

        public EntryViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.txt_item_title);
            dateTextView = itemView.findViewById(R.id.txt_item_date);
            startTimeTextView = itemView.findViewById(R.id.txt_item_start_time);
            endTimeTextView = itemView.findViewById(R.id.txt_item_end_time);
        }
    }
}
