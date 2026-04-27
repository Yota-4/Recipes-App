# Cooking Recipes App 

A clean, responsive Android application built with Java and XML. This project demonstrates core Android development concepts, including UI navigation, a mock user authentication flow, and robust state management during screen orientation changes.

## Key Features
* **User Authentication Flow:** * Login and Registration screens with UI validation.
  * Local data persistence using `SharedPreferences` acting as a mock database.
* **Responsive Design:** * Adaptive layouts using `ScrollView` and nested `LinearLayouts` (Card UI design).
  * Seamless handling of Portrait and Landscape orientation changes (`onSaveInstanceState`) without data loss.
* **Navigation & State Management:** * Clean Intent routing and Back Stack management (`finish()`).
  * Explicit Back Button handling using `OnBackPressedDispatcher`.
* **Clean Code & Accessibility:**
  * Strict Encapsulation and Method Extraction.
  * Optimized UI elements focusing on contrast ratios and accessibility (`contentDescription`).

## Tech Stack
* **Language:** Java
* **UI/Layout:** XML (LinearLayout, ScrollView, ListView)
* **IDE:** Android Studio
* **Local Storage:** SharedPreferences

## What I Learned
This project helped me solidify my understanding of the Android Activity Lifecycle. A major challenge I overcame was retaining dynamic UI states (like the selected recipe details) during configuration changes (screen rotations) without relying on Fragments, utilizing `Bundle` and `onSaveInstanceState` instead.

---
*This project was developed as part of my Android Development coursework.*
