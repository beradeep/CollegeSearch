
<h1 align="center"> CollegeSearch </h1>
<p align="center"> <img alt="API" src="https://img.shields.io/badge/Api%2024+-50f270?logo=android&logoColor=black&style=for-the-badge"/>
  <img alt="Kotlin" src="https://img.shields.io/badge/Kotlin-a503fc?logo=kotlin&logoColor=white&style=for-the-badge"/>
  <img alt="Jetpack Compose" src="https://img.shields.io/static/v1?style=for-the-badge&message=Jetpack+Compose&color=4285F4&logo=Jetpack+Compose&logoColor=FFFFFF&label="/>
  <img alt="material" src="https://custom-icon-badges.demolab.com/badge/material%20you-lightblue?style=for-the-badge&logoColor=333&logo=material-you"/>
</p>

### CollegeSearch is a simple and powerful android application for filtering out the best college suited for you among all the IITs, NITs, IIITs and GFTIs in India.
#### You just enter your JEE SCORE/RANK and boom, you get all the recommended colleges you can get into.

## Installation ⬇️

<a href='https://play.google.com/store/apps/details?id=com.bera.josaahelpertool'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png' height='80px'/><a href="https://github.com/beradeep/collegesearch/releases/latest"><img alt="Get it on GitHub" src="https://user-images.githubusercontent.com/69304392/148696068-0cfea65d-b18f-4685-82b5-329a330b1c0d.png" height=80px />

## Features ✨
- [x] Free to use, no login, no sign up required. 🆓
- [x] All kinds of filters, sorts to play with and decide your ideal college. 🏫
- [x] Takes into consideration, all the criteria & reservations- rank, sex, caste etc. 🚀
- [x] Get cutoffs for any branch in any IIT, NIT or GFTI individually. 📄
- [x] Dark and light modes. 🌓
- [x] Single activity design.  
    
## Building 🏗️

1. Clone the project
2. Open Android Studio IDE
3. Go to File » New » Project from VCS
4. Paste ``` https://github.com/beradeep/CollegeSearch.git ```
5. Open the project
6. Grab your ```YOUR_API_KEY``` from https://api-ninjas.com
7. Now, in your local.properties add the block
``` 
   API_KEY_QUOTES = YOUR_API_KEY
   API_KEY_CUTOFF = c3423fd80cca7707fe15
```
8. Build and run

## Tech Stack 🛠

- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous calls and tasks to utilize threads.
- [Jetpack Compose UI Toolkit](https://developer.android.com/jetpack/compose) - Modern UI development toolkit.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
  - [StateFlow and SharedFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow#:~:text=StateFlow%20is%20a%20state%2Dholder,property%20of%20the%20MutableStateFlow%20class.) - StateFlow and SharedFlow are Flow APIs that enable flows to optimally emit state updates and emit values to multiple consumers.
- [Dependency Injection](https://developer.android.com/training/dependency-injection) -
    - [Koin](https://insert-koin.io/docs/reference/koin-android/start) - A standard way to incorporate Koin dependency injection into an Android application.
    - [Koin-ViewModel](https://insert-koin.io/docs/reference/koin-android/viewmodel) - DI for injecting ```ViewModel```. 
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.

# Architecture 👷‍♂️
This app uses [MVVM(Model View View-Model)](https://developer.android.com/topic/architecture#recommended-app-arch)  and Clean architecture.

## Contributing 🤝

Contributions are always welcome. Feel free to make a pull request. Thanks to all the future contributors!

Highly appreciate leaving a :star: if you liked it!
