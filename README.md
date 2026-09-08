# CineMovie

Free, ad-free movie catalogue for Android, built on the TMDB API.
MVVM + Clean Architecture, Kotlin Flows, Retrofit/OkHttp, Room, Dagger Hilt,
Navigation Component, Bottom Sheet Dialog, WebView trailers.

## Movie skins

The app is fully skinnable: from the palette button on the home screen the user
picks the film their app should look like, and the choice is remembered.
Shipping skins: **Matrix** (black, phosphor green, monospace) and **Dune**
(warm sand amber). No screen hardcodes a colour or a font — every layout paints
itself from theme attributes, so a new skin is three steps:

1. a palette in `res/values/colors.xml`;
2. a `Theme.CineMovie.<Name>` style plus its `TextAppearance.<Name>.*` styles
   in `res/values/themes.xml` / `styles.xml`;
3. one entry in `presentation/theme/CineTheme.kt`.

The theme picker screen, the preview cards and the persistence pick it up
automatically.

## Getting started

1. Get a free API key at <https://www.themoviedb.org/settings/api>.
2. Add it to `local.properties` (git-ignored, see `local.properties.example`):

   ```properties
   TMDB_API_KEY=your_key_here
   ```
3. Open the project in **Android Studio Ladybug (2024.2) or newer** (AGP 8.7.3 /
   Gradle 8.9 / JDK 17) and run the `app` configuration, or from the terminal:

   ```bash
   ./gradlew assembleDebug
   ```

Without a key the app starts, but every request fails with
"TMDB API key is missing or invalid".

## Release build

See [PLAY_STORE.md](PLAY_STORE.md) for signing, the release checklist and the
Play Console answers. [PRIVACY_POLICY.md](PRIVACY_POLICY.md) is the policy text that
has to be published at a public URL for the store listing.

This product uses the TMDB API but is not endorsed or certified by TMDB.

## Screens

![image](https://github.com/rafaelmamedli/MovieApp/assets/106253655/a9b3e6a0-2fd7-40b0-81b6-05666a01a120)
![image](https://github.com/rafaelmamedli/MovieApp/assets/106253655/8431b636-773c-48ed-8a55-e5f01bbbc4a8)
![image](https://github.com/rafaelmamedli/MovieApp/assets/106253655/e4f6f654-2090-4633-9f07-82dbe0b9e2f4)
![image](https://github.com/rafaelmamedli/CineMovie/assets/106253655/0786a7da-3145-4ec8-a7f1-e6dc28d88a73)
![image](https://github.com/rafaelmamedli/MovieApp/assets/106253655/4849403a-09df-4405-8fb0-46bb784b91a3)
![image](https://github.com/rafaelmamedli/MovieApp/assets/106253655/dd7ac7f5-16a4-4808-abee-3b895f1fa786)
