[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/NJdesGoS)
# JournalApp

Name            | BITS ID      |Email ID                         |
----------------|--------------|---------------------------------|
Priya Rathi     |2022A7PS1096G |f20221096@goa.bits-pilani.ac.in  |
Vageesha Gupta  |2022A7PS1107G |f20221107@goa.bits-pilani.ac.in  |

## Project Overview
The Journal App allows users to record daily journal entries with details such as date, start time, and end time for each entry. Users can also delete and share individual entries, and there are accessible features designed to enhance usability for all users.

## Known Bugs
No known bugs

## Task Completion Summary

Task 1: Navigation with Safe Args
Implemented navigation actions between fragments using the nav graph actions. Integrated Safe Args to pass data from the EntryList fragment to EntryDetails, ensuring a seamless and error-free transition of data between these views.

Task 2: Database Modification
Extended the Room database schema to include date, start time, and end time columns for each entry. Implemented DELETE functionality to remove specific entries and added comprehensive unit tests to verify that all CRUD operations—INSERT, QUERY, UPDATE, and DELETE—operate correctly.

Task 3: DELETE Button in EntryDetailsFragment
Added a DELETE button to the EntryDetailsFragment menu bar. Clicking this prompts a confirmation dialog to prevent accidental deletions, and upon user confirmation, the entry is removed from the database.

Task 4: SHARE Button for Journal Entries
Included a SHARE button in the EntryDetailsFragment, enabling users to share entries in a plain-text format. The app creates a message string detailing the entry and opens a chooser for the user to select a compatible app to share the content.

Task 5: INFO Button Redirect
In the EntryList fragment menu bar, added an INFO button that redirects users to The Atomic Habits by James Clear either through the default browser or an in-app WebView.

Ideas were borrowed from ChatGPT and web, we also used StackOverFlow to resolve errors.

## Talkback experience
Using TalkBack with the Journal App provided valuable insights into the app’s accessibility for users who rely on screen readers. Here’s a summary of our experience:

1. Overall Navigation: TalkBack allowed easy navigation between screens. The EntryList and EntryDetails fragments were accessible, and TalkBack could read out labels and buttons, helping users understand each screen’s structure.
2. Date and Time Pickers: The date and time pickers were functional with TalkBack, but selecting specific dates and times required multiple gestures. We could enhance this by providing custom descriptions or hints to make it easier for users to pick values.
3. Action Buttons: Key action buttons, such as DELETE, SHARE, and INFO, were accessible and identified by TalkBack. Each button's purpose was communicated effectively, though adding more descriptive labels would provide even more clarity.
4. Confirmation Dialogs: The DELETE confirmation dialog was accessible, with TalkBack reading out the prompt and button options. Users could navigate and select options without issue.

## Testing
We did not follow a test-driven development (TDD) approach for this project. Instead, we first implemented the main code and then wrote test cases to verify the functionality of each feature. This approach allowed us to focus on coding the core features before turning our attention to validation and testing.

## Time and Difficulty Assessment
Hours taken: 72 hours approx
Difficulty: 7/10

Pair programming was used thoroughly.


