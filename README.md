Auto-Clicker-Game

An engaging and whimsical Android clicker game where players tap to earn points, unleash auto-click automation, and get fun cat facts via a public API! Built using modern Android architecture components including Kotlin, ViewModel, DataStore, Retrofit, and more.


-------

Features

| Feature                | Description                                                                     |
| ---------------------- | ------------------------------------------------------------------------------- |
| Manual Clicking        | Tap the "Upgrade Click" button to earn points manually                          |
| Auto-Clicker           | Automate clicking every 5 seconds, up to 720 auto clicks per session            |
| Persistent Points      | Uses DataStore to save points even after app restarts or process death          |
| ViewModel Architecture | Clean separation of UI and logic using ViewModel and StateFlow                  |
| Cat Facts API          | Fetches random facts via Retrofit                                               |
| Sound Effects          | Audio feedback on clicking and resetting with bounce animation                  |
| Orientation Support    | Fully responsive layouts for portrait and landscape                             |
| Fun UI/UX              | Animated image tapping, styled buttons, and clean layout using ConstraintLayout |

-------

Tech Stack

* Language: Kotlin
* Architecture: MVVM
* Persistence: Android DataStore
* Asynchronous: Kotlin Coroutines + Flow
* Networking: Retrofit + Gson
* UI: XML Layouts + ConstraintLayout + Animation
* Audio: MediaPlayer for sound effects
* Compatibility: Target SDK 33, Minimum SDK 21

-------

How It Works

* Clicking: Adds points and plays a bounce animation with a sound.
* Auto-Clicking: Triggered via button. Adds 1 point every 5 seconds, up to 720 times.
* Reset: Stops auto-clicking, resets points and auto-click count, and plays a sound.
* Persistence: Points are stored using DataStore and restored via Flow in ViewModel.
* Cat Fact API: On launch, a random cat fact is retrieved asynchronously using Retrofit.

-------

API Reference

Cat Fact API: https://catfact.ninja/fact

(Example) Returns JSON:
```json
{
  "fact": "Cats sleep 70% of their lives.",
  "length": 33
}
```
-------

Requirements

* Android Studio Flamingo or later
* Kotlin 1.8+
* Android SDK 21+
* Emulator or physical Android device

-------

Purpose

* NONE! ^__^
* Enjoy tapping & facts!

-------

Author

Alex Ramirez
* https://www.linkedin.com/in/alex-ramirez-5b673a367/

-------