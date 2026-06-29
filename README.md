# StreamParas 🎬
> A high-performance, hybrid Android and Android TV streaming application built with Jetpack Compose.

StreamParas (Streambert) is a modern streaming client optimized for both mobile devices and low-resource Android TV boxes. It integrates **TMDB** and **AniList** to offer a rich catalog of Movies, TV Shows, and Anime, backed by an optimized playback engine.

---

## 🌟 Key Features

*   **📺 Hybrid Form Factor:** Built-in dual navigation graphs. Automatically switches between a touch-friendly phone layout and a D-pad optimized **Android TV Leanback** interface.
*   **▶️ Advanced Video Player:** Supports native **HLS (.m3u8)** streaming using AndroidX Media3 ExoPlayer, as well as sandboxed WebView embed player sources (VidSrc, Videasy, Vidking, AllManga).
*   **📊 Automatic Playback Resume:** Saves your watch progress and automatically prompts or seeks to your last position when returning to a video.
*   **🔍 Rich Metadata & Tracking:**
    *   **TMDB API Integration** for movies and series metadata.
    *   **AniList GraphQL API Integration** for anime details, status, and sequel chains.
*   **🎨 Custom Theme Engine:** Premium visual aesthetics including dark mode, true black (AMOLED), Mocha, Slate, and customizable accent colors.
*   **💾 Secure & Persistent Storage:** Local database caching, watch history, watchlist, and backup/restore options.

---

## ⚡ Performance Optimizations (TV-Grade)

To ensure fluid 60 FPS performance on low-resource Android TV boxes, the app utilizes several performance enhancements:

1.  **Parallel Data Fetching:** Home feed sections are loaded concurrently using Kotlin Coroutine `async`/`await`, reducing home screen load times by over 75%.
2.  **Shared Network Client:** A single, shared `OkHttpClient` instance is reused across the entire app with a **50MB persistent HTTP disk cache** to speed up subsequent metadata loading.
3.  **Resource-Tuned Image Loader:** Coil is configured with a **15% memory cap** and a **50MB image disk cache** to prevent Out-Of-Memory (OOM) crashes.
4.  **Draw-Phase Animations:** Shimmer placeholders are drawn entirely in the graphics layer using Compose's `drawBehind`, bypassing layout/recomposition passes.
5.  **WebView Leak Prevention:** Custom lifecycle observers and `onRelease` callbacks stop and destroy WebView instances immediately when navigating away.

---

## ⚙️ Setup & Configuration

### Prerequisites
*   [Android Studio (Ladybug or newer)](https://developer.android.com/studio)
*   Android SDK 24 (Android 7.0) or higher

### 1. API Keys Configuration
StreamParas requires TMDB and Gemini API keys. Create a `.env` file in the root directory:

```env
TMDB_KEY=your_tmdb_developer_api_key
GEMINI_API_KEY=your_gemini_api_key
```

### 2. Signing Configurations
To build a debug or release build, ensure you have a keystore configured or use the default debug keystore. The Gradle setup will automatically read credentials from the `.env` properties.

---

## 🛠️ Tech Stack

*   **UI Framework:** Jetpack Compose (Material 3)
*   **Networking:** Retrofit 2, OkHttp 3, Moshi (JSON parsing)
*   **Image Loading:** Coil Compose
*   **Video Playback:** AndroidX Media3 (ExoPlayer & UI)
*   **Async/Concurrency:** Kotlin Coroutines & Flow
*   **Storage:** Jetpack DataStore Preferences

---

## 📂 Project Structure

```
app/src/main/java/com/example/
├── data/
│   ├── api/          # TMDB, AniList, and Scraping APIs
│   ├── local/        # Preferences and DataStore
│   ├── model/        # Unified data models
│   └── repository/   # MediaRepository coordinator
├── player/           # ExoPlayer Service and VideoViewModel
└── ui/
    ├── components/   # Shared UI elements (Shimmer, scale modifiers)
    ├── navigation/   # Phone & TV NavHost definitions
    ├── phone/        # Phone-optimized screens (Home, Detail, Player, Settings)
    └── tv/           # D-pad and TV-optimized screens
```
