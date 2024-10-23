package androidsamples.java.journalapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "journal_entries")
public class JournalEntry {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String date;
    private String startTime;
    private String endTime;

    private String title;
    private String description;

//    private Date entryDate;


    public JournalEntry(String date, String startTime, String endTime) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

//    public Date getEntryDate() { return entryDate; }
//    public void setEntryDate(Date entryDate) { this.entryDate = entryDate; }


}
