<div align="center">
  <!-- ضع رابط الصورة الرئيسية هنا -->
  <img src="link_to_your_main_hero_image.png" alt="PomoTree App Cover" width="100%">
</div>

---

# 🇬🇧 English Version

<p align="left">
  <img src="https://img.shields.io/badge/Kotlin-1.9.0-purple.svg?style=flat&logo=kotlin" alt="Kotlin">
  <img src="https://img.shields.io/badge/Jetpack%20Compose-UI-blue.svg?style=flat&logo=android" alt="Jetpack Compose">
  <img src="https://img.shields.io/badge/Architecture-MVVM-green.svg?style=flat" alt="MVVM">
  <img src="https://img.shields.io/badge/Database-Room-orange.svg?style=flat" alt="Room DB">
</p>

## 📖 About PomoTree

**PomoTree** is an innovative productivity and time-management app based on the Pomodoro Technique. The app motivates users to stay focused by planting virtual trees that grow with every successful focus session. Complete a session, and a new tree is added to your "Forest"; give up early, and the tree withers.

## ✨ Key Features

* ⏱️ **Custom Pomodoro Timer:** Set your focus time in minutes with a fully interactive circular UI.
* 🌳 **My Forest:** A dedicated screen to visually track your achievements and the trees you've grown.
* 📊 **Session Details:** Detailed tracking for each session (Date, Time, Duration, and Tree Status).
* 🔔 **Foreground Services:** The timer runs flawlessly in the background with interactive notifications to pause or cancel the session outside the app.
* 🌗 **Dynamic Theme (Dark/Light Mode):** Full support for Dark Mode, saving user preferences locally using `SharedPreferences`.
* 🌍 **Localization (RTL/LTR):** Fully supports English and Arabic, automatically adapting the layout direction.

## 🛠️ Tech Stack & Tools

Built with modern Android development standards:

* **Language:** Kotlin
* **UI Toolkit:** Jetpack Compose (Material Design 3)
* **Architecture:** MVVM (Model-View-ViewModel) + Clean Architecture principles
* **Local Database:** Room Database for secure local storage of sessions and trees.
* **Asynchronous Programming:** Kotlin Coroutines & `StateFlow` / `MutableStateFlow` for state management.
* **Background Processing:** `Foreground Service` combined with `NotificationCompat` to prevent Doze Mode interruptions.
* **Navigation:** `Navigation Compose` with custom smooth transitions (Fade & Slide).

## 🏗️ Architecture & Project Structure

```text
com.example.pomodorowatch
├── Data/            # Room DB, Entities, and DAOs
├── Repositories/    # Data repositories (TreeSessionsRepo)
├── ViewModel/       # State management and logic (TimerViewModel & Factory)
├── Service/         # Background services & Notifications (TimerService)
└── ui/              # Compose UI
    ├── Screens/     # Main screens (Timer, Forest, Details)
    └── theme/       # Colors, Typography, and ThemeManager
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

# 🇪🇬 النسخة العربية

## 📖 عن التطبيق (About)

PomoTree هو تطبيق مبتكر لإدارة الوقت وزيادة الإنتاجية يعتمد على تقنية "بومودورو". التطبيق يحفز المستخدمين على التركيز من خلال زراعة أشجار افتراضية تنمو مع كل جلسة تركيز ناجحة. إذا نجحت في إكمال الجلسة، ستتم إضافة شجرة جديدة إلى "غابتك"، وإذا استسلمت مبكراً، ستذبل الشجرة.

## ✨ المميزات الرئيسية (Features)

* ⏱️ **مؤقت بومودورو مخصص:** إمكانية تحديد وقت التركيز بالدقائق مع واجهة مستخدم دائرية تفاعلية.
* 🌳 **غابة الإنجازات (My Forest):** شاشة مخصصة تعرض سجل نجاحاتك والأشجار التي قمت بزراعتها بشكل مرئي جذاب.
* 📊 **تفاصيل الجلسات:** تتبع دقيق لكل جلسة (التاريخ، الوقت، المدة، وحالة الشجرة).
* 🔔 **العمل في الخلفية (Foreground Services):** استمرار عمل المؤقت بسلاسة في الخلفية مع إشعارات تفاعلية تتيح إيقاف أو إلغاء الجلسة من خارج التطبيق.
* 🌗 **الوضع الداكن والفاتح (Dark/Light Mode):** دعم كامل للثيم الديناميكي مع حفظ تفضيلات المستخدم تلقائياً.
* 🌍 **تعدد اللغات (Localization):** دعم كامل للغتين العربية والإنجليزية، مع تعديل اتجاه الشاشة (RTL/LTR) تلقائياً بسلاسة.

## 🛠️ التقنيات والأدوات المستخدمة (Tech Stack)

تم بناء هذا التطبيق باستخدام أحدث تقنيات تطوير تطبيقات أندرويد:

* **لغة البرمجة:** Kotlin
* **واجهة المستخدم:** Jetpack Compose (Material Design 3)
* **المعمارية (Architecture):** MVVM (Model-View-ViewModel)
* **قاعدة البيانات:** Room Database للحفظ المحلي لمعلومات الأشجار والجلسات.
* **البرمجة غير المتزامنة:** Kotlin Coroutines & Flow (StateFlow / MutableStateFlow).
* **الخدمات (Services):** Foreground Service مقترنة بـ NotificationCompat لضمان عمل المؤقت عند غلق الشاشة.
* **التنقل (Navigation):** استخدام Navigation Compose مع حركات انتقال مخصصة (Animations).

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
