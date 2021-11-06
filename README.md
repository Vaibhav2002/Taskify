![](media/header_dark.png)

# **Taskify** 

**Taskify** is an app to manage your daily tasks and boost your productivity

# Video Introduction 📹

This is a small introduction video about Taskify  https://youtu.be/MEuoxix5SGY.

# Article ✍

To know more about how I build this project, read my [Article](https://vaibhav2002.hashnode.dev/taskify-an-app-to-manage-your-daily-tasks-and-boost-your-productivity-harperdb-hackathon)

# Application Install

***You can Install and test latest Taskify app from below 👇***

[![Taskify](https://img.shields.io/badge/Taskify✅-APK-red.svg?style=for-the-badge&logo=android)](https://github.com/Vaibhav2002/Taskify/releases/tag/1.0.0)

## Setup
Clone the repository on your machine. Open the project on your IDE and connect it to firebase and harperDB and everything will be setup

## About

 It uses firebase and HarperDB as it's backend. It uses Firebase Authentication for email based auth and Google auth, Firebase Crashlytics for crash reporting and HarperDB as its database.

- Fully functionable. 
- Clean and Simple Material UI.
- It supports dark theme too 🌗.

### Insights into the app 🔎

![light_dark.png](media/light_dark.png)

**Taskify** offers light as well as dark theme 🌓. So now you can use Taskify in whatever theme you like the most. 🔥


![slide-1.png](media/slide-1.png)


**Taskify** supports both email based and Social media authentication like Google authentication. Planning to add Facebook authentication too. 😁



![slide-2.png](media/slide-2.png)


Have a look at your running task, upcoming and completed tasks all in one place. Create a task by pressing the FAB below.

![slide-3.png](media/slide-3.png)

Taskify lets you start a timer ⌚ to track the time you take completing a task. Timer runs in background, so you dont need to keep the app open all the time. 👍 See stats 💪 of all tasks you created within the last week, including total tasks created in a day and breakdown of tasks based on category.

## 📸 Screenshots

||||
|:----------------------------------------:|:-----------------------------------------:|:-----------------------------------------: |
| ![](media/getting_started_dark.jpg) | ![](media/login_dark.jpg) | ![](media/register_dark.jpg) |
| ![](media/ongoing_dark.jpg)  | ![](media/upcoming_dark.jpg) | ![](media/completed_dark.jpg)    |
| ![](media/add_task_dark.jpg) | ![](media/stats_dark.jpg)    | ![](media/profile_dark.jpg)      |
|![](media/menu_dark.jpg)  |![](media/timer_dark.jpg)  |![](media/about_dark.jpg)      |

## Built With 🛠
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more..
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) - A cold asynchronous data stream that sequentially emits values and completes normally or with an exception.
 - [StateFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow) - StateFlow is a state-holder observable flow that emits the current and new state updates to its collectors.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes. 
  - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
  - [DataBinding](https://developer.android.com/topic/libraries/data-binding) - Binds data directly into XML layouts
  - [Room](https://developer.android.com/training/data-storage/room) - Room is an android library which is an ORM which wraps android's native SQLite database
  - [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) - Jetpack DataStore is a data storage solution that allows you to store key-value pairs or typed objects with protocol buffers.
- [Dependency Injection](https://developer.android.com/training/dependency-injection) - 
  - [Hilt-Dagger](https://dagger.dev/hilt/) - Standard library to incorporate Dagger dependency injection into an Android application.
  - [Hilt-ViewModel](https://developer.android.com/training/dependency-injection/hilt-jetpack) - DI for injecting `ViewModel`.
- Backend
  - [Firebase](https://firebase.google.com)
    - Firebase Auth - To support email based authentication and google authentication
    - Firebase Crashlytics - To report app crashes
  - [HarperDB](https://harperdb.io) -  HarperDB is a geo-distributed database with hybrid SQL & NoSQL functionality in one powerful tool, accessed via a REST API.
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
- [GSON](https://github.com/google/gson) - A modern JSON library for Kotlin and Java.
- [Timber](https://github.com/JakeWharton/timber) - A simple logging library for android.
- [GSON Converter](https://github.com/square/retrofit/tree/master/retrofit-converters/gson) - A Converter which uses Moshi for serialization to and from JSON.
- [Glide](https://github.com/bumptech/glide) - An image loading and caching library for Android focused on smooth scrolling
- [Coil](https://github.com/coil-kt/coil) - An image loading library for Android backed by Kotlin Coroutines.
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.

# Package Structure
    
    com.vaibhav.taskify    # Root Package
    .
    ├── data                # For data handling.
    |   ├── local           # Room DB and its related classes
    |   ├── remote          # Firebase, HarperDB and their relative classes
    │   ├── model           # Model data classes, both remote and local entities
    │   └── repo            # Single source of data.
    |
    ├── di                  # Dependency Injection             
    │   └── module          # DI Modules
    |
    ├── ui                  # UI/View layer
    |   ├── adapters        # All Adapters, viewholder and diffUtils for recyclerViews      
    │   ├── auth            # Authorization Activity and its fragments
    │   ├── mainScreen      # Home Activity and its fragments
    |   ├── addTaskScreen   # Add Task Activity and its fragments
    |   ├── onBoarding      # OnboardingScreen
    │   └── splashScreen    # SplashScreen
    |
    ├── service             # Timer Service and its related classes
    |
    └── utils               # Utility Classes / Kotlin extensions


## Architecture
This app uses [***MVVM (Model View View-Model)***](https://developer.android.com/jetpack/docs/guide#recommended-app-arch) architecture.

![](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png)
  


---

## If you like my projects and want to support me to build more cool open source projects
  
<a href="https://www.buymeacoffee.com/VaibhavJaiswal"><img src="https://img.buymeacoffee.com/button-api/?text=Buy me a coffee&emoji=&slug=VaibhavJaiswal&button_colour=FFDD00&font_colour=000000&font_family=Cookie&outline_colour=000000&coffee_colour=ffffff"></a>

---

 ## Contact
If you need any help, you can connect with me.

Visit:- [VaibhavJaiswal.io](https://vaibhav2002.github.io)
  



