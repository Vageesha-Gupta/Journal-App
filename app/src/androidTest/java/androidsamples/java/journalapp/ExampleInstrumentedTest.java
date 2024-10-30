package androidsamples.java.journalapp;

import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.widget.DatePicker;

import androidx.test.espresso.accessibility.AccessibilityChecks;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExampleInstrumentedTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @BeforeClass
    public static void enableAccessibilityChecks() {
        AccessibilityChecks.enable().setRunChecksFromRootView(true);
    }

    @Test
    public void testAddEntryButtonAccessibility() {
        onView(withId(R.id.btn_add_entry)).perform(click());
    }

    //
    @Test
    public void testNavigateToCreateEntry() {
        // Click on the "Create Entry" button
        onView(withId(R.id.btn_add_entry)).perform(click());

        // Check if the "Create Entry" screen is displayed by verifying if specific fields are visible
        onView(withId(R.id.edit_title)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_entry_date)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_start_time)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_end_time)).check(matches(isDisplayed()));
    }

    @Test
    public void testEnterEntryDetailsAndSave() {
        // Navigate to "Create Entry" screen
        onView(withId(R.id.btn_add_entry)).perform(click());

        // Enter title
        onView(withId(R.id.edit_title)).perform(replaceText("My Test Journal Entry"));

        // Select date, start time, and end time (assuming date and time pickers are correctly set up)
        onView(withId(R.id.btn_entry_date)).perform(click());
        //  date picker interaction
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2024, 10, 28)); // Replace with desired year, month, day
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.btn_start_time)).perform(click());
        // Add start time picker interaction here if needed

        onView(withId(R.id.btn_end_time)).perform(click());
        // Add end time picker interaction here if needed

        // Click "Save Entry" button to save
        onView(withId(R.id.btn_save)).perform(click());

        // Verify the Entry List screen or confirmation message is displayed
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
    }

    @Test
    public void testBackNavigationFromCreateEntry() {
        // Navigate to "Create Entry" screen
        onView(withId(R.id.btn_add_entry)).perform(click());

        // Press back button to return to the main screen
        pressBack();

        // Verify main screen (Entry List) is displayed again
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
    }
    @Test
    public void testNavigationToEntryDetails() {
        // Click on the add entry button in EntryListFragment
        onView(withId(R.id.btn_add_entry)).perform(click());

        // Verify a unique view in EntryDetailsFragment is displayed
        onView(withId(R.id.layout_entry_detail)).check(matches(isDisplayed())); // Unique layout ID in EntryDetailsFragment
    }

    @Test
    public void testBackNavigationToEntryList() {
        // Navigate to EntryDetailsFragment by clicking the add entry button
        onView(withId(R.id.btn_add_entry)).perform(click());

        // Press the back button
        pressBack();

        // Verify that EntryListFragment is displayed again by checking for a unique view in EntryListFragment
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed())); // RecyclerView ID in EntryListFragment
    }

    //over



}





