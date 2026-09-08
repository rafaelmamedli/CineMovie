# CineMovie — Google Play release guide

The app is free, contains no ads, no in-app purchases, no login and no tracking SDKs,
so the Play Console forms stay simple. This file is the checklist for shipping it.

---

## 1. Build the release artifact

```bash
# once: put your TMDB key in local.properties (git-ignored)
echo "TMDB_API_KEY=your_key_here" >> local.properties

# once: create the upload keystore and NEVER lose it
keytool -genkey -v -keystore cinemovie-upload.jks -keyalg RSA \
        -keysize 2048 -validity 10000 -alias cinemovie

# once: copy keystore.properties.example to keystore.properties and fill it in
cp keystore.properties.example keystore.properties

# every release
./gradlew clean bundleRelease
```

The signed bundle is written to `app/build/outputs/bundle/release/app-release.aab` —
that is the file you upload. (`assembleRelease` produces an APK, which Play no longer
accepts for new apps.)

Requirements of the current build setup:

* JDK 17, Android Gradle Plugin 8.7.3 / Gradle 8.9 → **Android Studio Ladybug (2024.2)
  or newer**.
* `compileSdk` / `targetSdk` = **35**, the minimum Google Play accepts for new apps and
  updates since August 2025. `minSdk` = 24 (Android 7.0), ~99% of devices.
* Release builds are minified and resource-shrunk by R8 (`app/proguard-rules.pro` keeps
  the Gson models, Retrofit interfaces, Room and Glide).

## 2. Before the first upload — one-time account work

| Item | Status |
| --- | --- |
| Google Play developer account (25 USD, one-off) | you |
| Identity + address verification (mandatory since 2023) | you |
| For personal accounts: 12 testers × 14 days closed test before production | you |
| App name, short + full description (below) | ready |
| Privacy policy hosted on a public URL | `PRIVACY_POLICY.md` — publish it (GitHub Pages / Gist) and paste the URL |
| Icon 512×512 PNG | export from `app/src/main/res/mipmap-*/ic_launcher` |
| Feature graphic 1024×500 PNG | you |
| At least 2 phone screenshots (min 320px, max 3840px) | you |
| Content rating questionnaire | answer "no" to everything; the app only shows movie metadata |
| Data safety form | see section 3 |
| Ads declaration | **No ads** |
| Pricing | **Free** |
| Target audience | 13+ recommended (movie descriptions are not curated for children) |

## 3. Data safety form answers

* Does your app collect or share any of the required user data types? → **No**
* Is all of the user data encrypted in transit? → **Yes** (all traffic is HTTPS,
  `usesCleartextTraffic="false"`)
* Do you provide a way for users to request that their data is deleted? → not
  applicable, no data is collected; favourites are removed by uninstalling the app.

## 4. Store listing text (copy-paste)

**App name (max 30):**
`CineMovie — Movies & Trailers`

**Short description (max 80):**
`Movies, trailers and favourites — with a look that matches your favourite film.`

**Full description (max 4000):**
```
CineMovie is a free, ad-free movie catalogue powered by TMDB.

• Dress the app up as the movie you love — pick the Matrix or the Dune skin and
  the whole interface changes colours and typography
• Popular, top rated and recently released movies on one home screen
• Search any movie by name
• Movie details: poster, rating, release date, synopsis and cast
• Watch the official trailer without leaving the app
• Save any movie to your favourites — available offline, stored only on your device

No account, no ads, no in-app purchases, no tracking. CineMovie asks for nothing but
an internet connection to load movie data.

This product uses the TMDB API but is not endorsed or certified by TMDB.
```

## 5. Legal / policy notes specific to this app

* **TMDB attribution is mandatory** by the TMDB API terms. It is shown on the splash
  screen (`fragment_splash.xml`) and in the store description above. Do not remove it.
* Trailers are played through YouTube's official embed, which is what YouTube's terms
  require — never download or re-host the video streams.
* The API key must not be committed. It now comes from `local.properties` /
  `TMDB_API_KEY`. **The old key was hard-coded in git history — revoke it on
  themoviedb.org and generate a new one.**
* Movie posters and metadata belong to TMDB; the app only displays them, which the API
  terms allow for a non-commercial free app.

## 6. Every subsequent release

1. Bump `versionCode` (+1, mandatory) and `versionName` in `app/build.gradle`.
2. `./gradlew clean bundleRelease`
3. Upload the `.aab`, write the release notes, roll out.
