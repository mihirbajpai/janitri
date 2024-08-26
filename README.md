# Janitri Color App

## Overview

The **Janitri Color App** is an Android application that allows users to manage and view color data. The app features integration with Firebase Realtime Database to fetch and store color information. Users can view colors in a grid, add new colors, and synchronize their color data with the cloud. The app also includes animations to enhance user interaction.

## Technologies Used

- **Jetpack Compose**: For building the UI using a declarative approach.
- **Firebase Realtime Database**: To store and retrieve color data.
- **Room**: For local database management.
- **LiveData**: To observe data changes and update the UI accordingly.
- **Coroutines**: For asynchronous operations.
- **ViewModel**: To manage UI-related data lifecycle-consciously.
- **Kotlin**: For writing the application logic.
- **Material3**: For implementing Material Design components.

## Features

- **Color Grid Display**: Shows a grid of colors with their hex codes and creation dates.
- **Add New Color**: Allows users to add random colors to the list.
- **Sync with Cloud**: Users can synchronize their color data with Firebase Realtime Database. The sync operation is visually enhanced with a rotation animation of the sync icon.
- **Local Storage**: Color data is also stored locally using Room, enabling offline access.
- **Animations**: Includes animations for the sync icon to provide visual feedback during data synchronization.

## Key Components

- **HomeScreen**: Displays the list of colors and provides options to add new colors or sync with the cloud.
- **ColorRepository**: Manages data operations including fetching and storing color data both locally and in the cloud.
- **ColorViewModel**: Handles the business logic and interacts with the repository.
- **ColorData**: Data model representing color information including hex code and creation date.

## Animations

- **Sync Animation**: When the user clicks the sync button, the sync icon rotates 360 degrees over one second to indicate the synchronization process.

Thank you.
