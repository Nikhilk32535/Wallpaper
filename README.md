---

# Crazy Wallpaper

Crazy Wallpaper is a feature-rich Android wallpaper app that allows users to browse, set, and download high-quality wallpapers directly to their devices. The app is integrated with Firebase for real-time updates and offers login/logout functionality with Firebase Authentication.

## Features

- **Browse Wallpapers:** Explore a wide variety of high-quality wallpapers across several categories.
- **Favorite Wallpapers:** Mark wallpapers as favorites to access them easily.
- **Login/Logout Functionality:**
  - Users can log in using Firebase Authentication.
  - Long-pressing on the logout button deletes the user account from the server.
- **Search by Category:** Users can search for wallpapers by category.
- **Dynamic UI Updates:** Real-time updates for wallpapers and categories.

## Feature Updates
- **Database:** Replaced Realm with SQLite for offline data storage.
- **Android Version Requirements:** Increased minimum Android version from 5.0 to 8.0.
- **Search Functionality:** Added the ability to search wallpapers by category.

## Technology Stack
- **Programming Language:** Java
- **Image Loading:** Glide Library
- **Backend:** Firebase Firestore for storing user and wallpaper data
- **Authentication:** Firebase Authentication for user login/logout and account management
- **Database:** SQLite 
- **UI Components:** TabLayout, ViewPager2, and CustomDialogs for pop-up screens

## Login/Logout Feature

- **Login:** Users are required to sign in before using the app.
- **Logout:** Standard logout is available. Long-pressing the logout button will delete the user account from Firebase Authentication.

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/Nikhilk32535/Wallpaper.git
   ```

2. Open the project in Android Studio.

3. Set up Firebase:
   - Add the `google-services.json` file to your project.
   - Enable Firebase Authentication and Firestore in your Firebase Console.

4. Run the app on your device or emulator.

## Contribution

Feel free to fork the repository and submit pull requests. Please make sure to follow the code style and add meaningful commit messages.

