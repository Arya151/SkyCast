# SkyCast - Weather Forecast App

SkyCast is a modern Android weather app following **Clean Architecture** principles. It provides accurate weather forecasts with a smooth UI built using Jetpack Compose. The app supports offline access, city search, dynamic themes, and modular code separation for scalability.

---

## 🌤 Features

- Current weather and 4-day forecast
- City search with autocomplete
- Dynamic gradients based on weather conditions
- Offline access via Room DB
- Modern Compose UI
- Clean Architecture (Data → Domain → Presentation)

---

## 🧱 Clean Architecture Layers

## 🛠 Tech Stack

| Layer         | Tools & Libraries                      |
|---------------|----------------------------------------|
| UI            | Jetpack Compose, Material 3            |
| Architecture  | MVVM + Clean Architecture              |
| Async / Flow  | Kotlin Coroutines, StateFlow, Flow     |
| Networking    | Retrofit, OkHttp, Gson                 |
| Local DB      | Room                                   |
| Dependency Injection | Hilt                            |
| Location      | FusedLocationProvider                  |


## 📱 Screens Overview

### 🏠 Home Screen
- Displays current weather info including temperature, feels like, pressure, and sea level.
- Shows a 4-day forecast section.
- Has a gradient background that dynamically changes based on weather condition.
- Allows user to trigger location-based weather data fetching.

  <img src="assets/home_screen1.png" width="300"/>  <img src="assets/home_screen2.png" width="300"/>  <img src="assets/home_screen3.png" width="300"/>

### 🔍 Search Screen
- User can type a city name and search it.
- Results are displayed in a list with city name, state, and country.

   <img src="assets/search_screen.png" width="300"/>
