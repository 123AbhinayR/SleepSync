# SleepSync

An Android app for tracking sleep. Tap a button when you go to bed and another when you wake up, and the app logs the session, calculates a sleep score, and gives you simple recommendations to sleep better.

## How it works

1. Tap **Start Sleep** to log the current time, then **End Sleep** when you wake up.
2. The app calculates the duration and saves the entry (start time, end time, hours, minutes) to a local SQLite database.
3. A rolling average of your sleep duration is turned into a **sleep score** out of 100, scaled against a 12-hour (720 minute) target.
4. The score is color-coded on the home screen: red under 50, yellow between 50 and 75, green above 75.
5. The **Data** page lists your past sleep sessions and shows a few personalized recommendations based on your score.
6. The **Resources** page links out to sleep guidance split by age group (youth, middle-aged, older adults).

## Technical details

- **Language:** Java
- **Min SDK:** 28, **Target/Compile SDK:** 34
- **Storage:** A local SQLite database (`SleepDatabaseHelper`, extending `SQLiteOpenHelper`) with one table storing start time, end time, and duration per session
- **UI:** Standard AndroidX widgets (`CalendarView`, `ListView`, `TextView`) across a handful of activities, no external UI libraries
- **Screens:**
  - `MainActivity` — start/end sleep, calendar, live average and score
  - `AdvancedData` — full sleep history and recommendations
  - `SleepResourcesPageActivity` — routes to age-specific resource screens
- **Score calculation:** average sleep minutes over all logged sessions, scaled to a percentage of a target sleep duration

## Getting started

1. Clone the repository
2. Open the project in Android Studio
3. Let Gradle sync and build
4. Run on a device or emulator

## Possible next steps

- Persist and reload the in-memory session list from the database on app start, instead of just the most recent session
- Replace the manual string-splitting used to parse stored sleep entries with structured data straight from the database
- Add automatic sleep detection (e.g. via device motion or a wearable) instead of manual start/end taps
