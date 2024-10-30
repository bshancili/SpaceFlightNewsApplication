# Spaceflight News Application
## Overview

The SpaceFlight News Application is an Android application developed using Kotlin and the MVVM architecture. It fetches the latest space-related articles from the Space Flight News API, displaying them in the home screen. Users can view article details, save articles for later reference, and navigate between different sections of the app. The app also supports infinite scrolling to load more articles as users scroll down the list.

## Usage
* **Navigate**: Use the bottom navigation bar to switch between Home and Bookmarks views.
* **Browse Articles**: On the Home screen, scroll through the list of articles. Click on an article to view more details.
* **Bookmark Articles**: Click the save button on an article to bookmark it. Bookmarked articles can be viewed in the Bookmarks tab. Bookmark Articles are being kept in PlayerPrefs as JSON format.
* **View Bookmarked Articles**: Navigate to the Bookmarks tab to see all saved articles.

## Installation
To install the SpaceFlightNews application, navigate to your `AndroidStudioProjects` directory and clone the repository using the following command:
   ```bash
   git clone https://github.com/bshancili/SpaceFlightNewsApplication.git
   ```
Once installed, open the project in Android Studio, sync the Gradle files, and run the application on an emulator or connected device.

## Architecture
This application is built using the MVVM (Model-View-ViewModel) architecture. This design pattern helps in separating concerns, making the app easier to manage and test:

* **Model**: Represents the data and business logic. It fetches data from the Space Flight News API using Retrofit and stores articles as `Article` objects. The model handles data operations and updates the ViewModel when data changes.

* **View**: Composed of the Activity and Fragment classes. It is responsible for displaying data and user interactions. The UI is built using XML layouts and incorporates View Binding for type-safe access to views.

* **ViewModel**: Acts as a bridge between the Model and View. It holds the UI-related data and manages the lifecycle of the UI components. The ViewModel fetches data from the Model and exposes it to the View, allowing for easier updates and better lifecycle management.

## Dependencies
In this project, the following libraries and dependencies have been used:

- **Retrofit**: The app uses Retrofit as the HTTP client to make API calls to the Space Flight News API.

- **Gson**: The app integrates Gson to convert JSON responses from the API into Kotlin data classes.

- **Glide**: The app utilizes Glide for efficient image loading and caching from various sources.

## API
This application uses the [Space Flight News API](https://api.spaceflightnewsapi.net/v4/). The base URL for the API is:
   ```bash
   https://api.spaceflightnewsapi.net/v4/articles/
   ```
The API provides endpoints to retrieve articles, and includes pagination support.

## License

This project is licensed under the Apache License. See the [LICENSE](LICENSE) file for details.
