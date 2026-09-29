# 💊 Medicine Reminder App

A basic Android application that helps users store medicines and receive reminders at scheduled times.

## Features

- Add medicine details
- Medicine type and dosage
- Set reminder time
- Set frequency
- Start and end date
- Food instructions
- Add optional notes
- View saved medicines
- View medicine details
- Delete medicines
- Alarm-based medicine reminders
- Notification reminders
- Local storage using Room Database

## Technologies Used

- Java
- XML
- Android Studio
- Room Database
- AlarmManager
- BroadcastReceiver
- Android Notifications

## App Flow

Add Medicine → Save to Room Database → Schedule Alarm → Alarm Triggers → Notification

## Project Structure

- data – Room Database, Entity and DAO
- 
Notification – Alarm and Notification handling
- ui – Add, List, Details and Settings screens
- MainActivity – Home screen

## Requirements

- Android Studio
- Android SDK
- Minimum SDK: 24

## How to Run

1. Clone the repository.
2. Open the project in Android Studio.
3. Sync Gradle.
4. Run the application on an Android emulator or physical device.
5. Allow notification and alarm permissions if requested.

## Authors

- Sommay Paliwal
- Samarth Prajapat
