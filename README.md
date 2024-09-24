# Crazy Wallpaper

Crazy Wallpaper is an Android app that allows users to explore, download, set, and favorite high-quality wallpapers. The app fetches wallpapers from Firebase and displays them in a user-friendly interface. Users can browse through different categories and save their favorite wallpapers for easy access.

## Features

- **Browse Wallpapers:** Explore high-quality wallpapers and set them as your device's background.
- **Favorite Wallpapers:** Mark wallpapers as favorites and access them from a dedicated favorites section.
- **Category Search :** User can able to search wallpapers by pre-define category.
- **Offline Favorites:** Favorite wallpapers are stored locally on the device using SQLite.
- **Glide Integration:** Efficient loading and caching of wallpapers using the Glide library.
- **Tab Layout & ViewPager2:** Easy navigation between Home, Categories, and Favorites using a clean tabbed interface.

## Technology Stack

- **Firebase**: Backend for storing and managing wallpaper URLs.
- **Glide**: Image loading and caching library.
- **SQLite**: Local database for storing favorite wallpapers (previously Realm, but switched to SQLite).
- **ViewPager2 & TabLayout**: For fragment management and smooth navigation.
- **SharedPreferences**: For lightweight storage of settings and favorite status.

## Requirements

- **Minimum Android Version:** Android 8.0 (Oreo) or higher.
- **Internet Connection:** Required for fetching wallpapers from Firebase.
- **Permissions:**
  - `INTERNET`: To fetch wallpapers.
  - `SET_WALLPAPER`: To allow users to set wallpapers directly from the app.
  - `WRITE_EXTERNAL_STORAGE`: To allow users to download wallpapers.

## Setup

1. Clone the repository from GitHub:
   ```bash
   git clone https://github.com/Nikhilk32535/Wallpaper.git
