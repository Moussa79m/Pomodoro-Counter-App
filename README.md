<div align="center">
  <!-- ضع رابط الصورة الرئيسية هنا -->
  <img src="link_to_your_main_hero_image.png" alt="PomoTree App Cover" width="100%">
</div>

---


<p align="left">
<img width="50%" height="50%" alt="Screenshot 2026-08-11 154345" src="https://github.com/user-attachments/assets/38065da9-09ee-402d-aa82-6e3dfacd70f4" />
<img width="50%" height="50%" alt="Screenshot 2026-08-11 154333" src="https://github.com/user-attachments/assets/13ad09c2-0dfd-4f69-a889-c17924325c48" />
<img width="50%" height="50%" alt="Screenshot 2026-08-11 154324" src="https://github.com/user-attachments/assets/4da11689-1cb9-497f-b768-7deebca306ff" />
<img width="50%" height="50%" alt="Screenshot 2026-08-11 154356" src="https://github.com/user-attachments/assets/bede77d9-c836-439d-831f-581d3dc4871e" />

</p>

## 📖 About PomoTree
**PomoTree** is an innovative, fully-featured productivity and time-management application built natively for Android. Based on the proven Pomodoro Technique, the app motivates users to maintain deep focus by planting virtual trees. Successfully complete a focus session, and a new tree flourishes in your personal "Forest". Give up early, and the tree withers. It's gamified productivity at its best.

## ✨ Comprehensive Features
* ⏱️ **Custom iOS-Style Time Picker:** Features a highly interactive, custom-built `IOSTimePicker` for a smooth and premium time-selection experience.
* 🌳 **Interactive Forest (My Forest):** A visually engaging dashboard tracking your productivity history, displaying successfully grown trees and failed attempts.
* 📊 **In-Depth Session Analytics:** Clicking on any tree reveals detailed metrics (Date, exact Time, Duration, and final Status).
* 🛡️ **Bulletproof Background Execution:** Utilizes Android's `Foreground Service` bound with custom Notifications to ensure the timer never drops, even when the app is killed or the device enters Doze mode.
* 🌓 **Persistent Dynamic Theming:** Full system-independent Dark & Light mode toggle. Preferences are saved instantly via `SharedPreferences`, ensuring the app remembers your choice across sessions.
* 🌍 **Seamless Localization:** Native support for both English (LTR) and Arabic (RTL), with automated layout mirroring and text translations.
* 🚀 **Fluid Navigation & Animations:** Built with `Navigation Compose` integrating custom enter/exit slide and fade transitions for a premium, stutter-free UX.

## 🛠️ Complete Tech Stack
This project represents modern Android development standards, utilizing the latest libraries and architectures:
* **Core:** Kotlin, Android SDK
* **UI Toolkit:** Jetpack Compose (Material Design 3)
* **Architecture:** MVVM (Model-View-ViewModel) paired with Clean Architecture principles (Separation of Data, Domain, and UI).
* **Local Persistence:** 
  * **Room Database:** For robust, structured storage of Tree Sessions and history.
  * **SharedPreferences:** For lightweight, fast key-value storage (Theme Preferences).
* **Concurrency & Reactive State:** Kotlin Coroutines, `StateFlow`, and `MutableStateFlow` for unidirectional data flow.
* **Services:** `Foreground Service`, `NotificationCompat`, `PendingIntent`.
* **Routing:** `Navigation Compose`.

## 🏗️ Exact Project Structure
The codebase is modular, scalable, and meticulously organized:

```text
com.example.pomodorowatch
├── Data/
│   └── LocalStorage/
│       ├── TreeDatabase.kt        # Room DB setup & configuration
│       ├── TreeSessionDao.kt      # Data Access Object queries
│       └── TreeSessionEntity.kt   # Database tables schema
├── Model/                         # Domain models
├── Repositories/
│   └── TreeSessionsRepo.kt        # Single source of truth for data operations
├── Service/
│   ├── TimerManager.kt            # Global state manager for the active timer
│   └── TimerService.kt            # Foreground service handling background ticks
├── ui/
│   ├── Screens/
│   │   ├── DetailsScreen.kt       # Session detailed view
│   │   ├── ForestScreen.kt        # User's trees grid/list view
│   │   ├── IOSTimePicker.kt       # Custom-built UI component
│   │   ├── MainScreen.kt          # BottomBar & Navigation host container
│   │   ├── ThemeToggleButton.kt   # Reusable UI component
│   │   └── TimerScreen.kt         # Active countdown UI
│   └── theme/
│       ├── Color.kt               # App color palettes
│       ├── Theme.kt               # MaterialTheme wrapper
│       ├── ThemeManager.kt        # SharedPreferences theme controller
│       └── Type.kt                # Typography settings
├── ViewModel/                     # ViewModels directory
├── AppNavigation.kt               # Compose Navigation graph definitions
├── AppScreens.kt                  # Sealed classes for route management
├── MainActivity.kt                # Entry point & theme initialization
└── TimerViewModelFactory.kt       # Dependency injection for ViewModel
```

## 🧪 Testing (Quality Assurance)

The app is designed with testability in mind, separating UI from business logic:

* **Unit Testing:** Used JUnit for accurate time calculations and view model states.
* **UI Testing:** Compose UI tests to ensure correct components render based on the state.

## 🚀 Getting Started

1. Clone the repository:

   ```bash
   git clone https://github.com/YourUsername/PomoTree.git
   ```

2. Open the project in Android Studio (Latest version recommended for Compose).
3. Wait for the Gradle Sync to complete.
4. Run the app on an emulator or a physical device.

## 👨‍💻 Author

Developed by **Mahmoud**. Feel free to use, modify, and build upon it!

---


## 📖 عن التطبيق (Pomotree)

PomoTree هو تطبيق احترافي ومتكامل لإدارة الوقت وزيادة الإنتاجية مبني بالكامل لمنصة أندرويد. يعتمد التطبيق على تقنية "بومودورو" ويضيف لمسة من التحفيز (Gamification)؛ حيث تقوم بزراعة أشجار افتراضية تنمو طوال فترة تركيزك. عند إكمال الجلسة بنجاح، تُضاف شجرة جديدة إلى "غابتك"، وإذا استسلمت، تذبل الشجرة.
✨ المميزات الشاملة للتطبيق
⏱️ منتقي وقت احترافي (iOS-Style): يحتوي على أداة IOSTimePicker مخصصة بالكامل لتجربة اختيار وقت سلسة ومميزة.

🌳 غابة الإنجازات التفاعلية: واجهة بصرية جذابة تعرض تاريخ إنتاجيتك، الأشجار الناجحة، وتلك التي لم تكتمل.

📊 تحليلات دقيقة للجلسات: الضغط على أي شجرة يعرض تفاصيلها الدقيقة (التاريخ، الوقت، المدة، وحالة الإنجاز).

🛡️ عمليات خلفية لا تتوقف: استغلال قوي لـ Foreground Service مع إشعارات تفاعلية لضمان استمرار عمل المؤقت في الخلفية حتى لو تم إغلاق التطبيق أو دخل الهاتف في وضع توفير الطاقة.

🌓 إدارة ذكية للوضع الداكن: دعم كامل للوضع الداكن (Dark Mode) مع حفظ اختيار المستخدم محلياً باستخدام SharedPreferences لضمان تذكر التطبيق لإعداداتك.

🌍 دعم كامل للغات (RTL/LTR): التطبيق يدعم اللغتين العربية والإنجليزية بامتياز، مع انعكاس تلقائي لاتجاه الواجهات والحركات.

🚀 تنقل وحركات سلسة (Animations): مبني باستخدام Navigation Compose مع حركات انتقال مخصصة (Slide & Fade) لتجربة استخدام خالية من التقطيع.

🛠️ التقنيات (Tech Stack)
تم بناء المشروع باستخدام أحدث المعايير والتقنيات في تطوير الأندرويد:

الأساسيات: Kotlin, Android SDK

واجهة المستخدم: Jetpack Compose (Material Design 3)

المعمارية (Architecture): MVVM مع تطبيق مبادئ الـ Clean Architecture لفصل طبقات البيانات عن الواجهة.

قواعد البيانات والحفظ المحلي:Room Database: لحفظ بيانات الجلسات والأشجار بشكل مهيكل وآمن.

إدارة الحالات والمهام المتزامنة: Kotlin Coroutines, StateFlow, MutableStateFlow.

الخدمات (Services): Foreground Service, NotificationCompat, PendingIntent.

## 🧪 الاختبارات (Testing)

التطبيق مصمم ليكون قابلاً للاختبار (Testable) بفضل فصل الـ Logic عن الـ UI عبر معمارية MVVM:

* **الاختبارات الأحادية (Unit Testing):** استخدام JUnit لاختبار دقة حسابات الوقت في TimerViewModel.
* **اختبار واجهة المستخدم (UI Testing):** التأكد من ظهور المكونات الصحيحة بناءً على تغيرات الحالة.

## 🚀 كيفية تشغيل التطبيق

1. قم بعمل استنساخ للمشروع:

   ```bash
   git clone https://github.com/YourUsername/PomoTree.git
   ```

2. افتح المشروع باستخدام Android Studio.
3. انتظر حتى ينتهي الـ Gradle Sync.
4. قم بتشغيل التطبيق على محاكي (Emulator) أو هاتف أندرويد حقيقي عبر زر Run.

## 👨‍💻 المطور

تم تطوير هذا التطبيق بواسطة **محمود**.
