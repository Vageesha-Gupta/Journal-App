package androidsamples.java.journalapp;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import static androidx.test.espresso.assertion.ViewAssertions.matches;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

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





