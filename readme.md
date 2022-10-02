<!------------------------------------------------------------------------------------------------------>
<img src="screenshots/mockup2.png"/>
<img src="../master/icons/ic_launcher_playstore.png" width="192" align="right" hspace="0"/>

Moviemade
=

[![kotlin-badge](https://img.shields.io/badge/Awesome-Kotlin-F18E33.svg)](https://kotlinlang.org)
[![platform-badge](https://img.shields.io/badge/Platform-Android-F3745F.svg)](https://github.com/michaelbel/moviemade)
[![minsdk-badge](https://img.shields.io/badge/minSdk-21-F3745F.svg)](https://github.com/michaelbel/moviemade)
[![license-badge](https://img.shields.io/badge/License-Apache_v2.0-F3745F.svg)](https://apache.org/licenses/LICENSE-2.0)
[![paypal-badge](https://img.shields.io/badge/Donate-Paypal-F3745F.svg)](https://paypal.me/michaelbel)
[![googleplay-badge](https://img.shields.io/badge/Google_Play-Demo-F3745F.svg)](https://play.google.com/store/apps/details?id=org.michaelbel.moviemade)
[![issues-badge](https://img.shields.io/github/issues-raw/michaelbel/moviemade?color=F3745F)](https://github.com/michaelbel/moviemade/issues)
[![last-commit-badge](https://img.shields.io/github/last-commit/michaelbel/moviemade?color=F3745F)](https://github.com/michaelbel/moviemade/commits)
[![prs-badge](https://img.shields.io/badge/PRs-welcome-F3745F.svg)](https://makeapullrequest.com)

Moviemade - easy way to discover popular movies. This is a simple TMDb client for Android with material design.

## Build
Take a look at <b>`local.properties`</b> and fill it with [your own](https://developers.themoviedb.org/3/getting-started/introduction) <b>tmdb_api_key</b> like this:
```gradle
tmdb_api_key=YOUR_OWN_TMDB_KEY
```

## Download
[<img src="https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png" alt="" height="100">](https://play.google.com/store/apps/details?id=org.michaelbel.moviemade)
[<img src="screenshots/direct-apk.png" alt="" height="100">](https://github.com/michaelbel/Moviemade/releases/download/1.3.1/moviemade-v1.3.1-release.apk)

## Screenshots
<div style="dispaly:flex">
    <img src="screenshots/screen1.png" width="33%">
    <img src="screenshots/screen2.png" width="33%">
    <img src="screenshots/screen3.png" width="33%">
    <img src="screenshots/screen4.png" width="33%">
    <img src="screenshots/screen5.png" width="33%">
    <img src="screenshots/screen6.png" width="33%">
</div>

## Technologies

- [x] Multi-module project
- [x] Gradle Plugin 7.3.0
- [x] MinSDK 21
- [x] TargetSDK 33
- [x] CompileSDK 33
- [x] Material3
- [x] Dark Theme
- [x] Dynamic Colors
- [x] 100% Kotlin 1.7.10
- [x] 100% Jetpack Compose, No XML
- [x] [KotlinX Serialization](https://github.com/Kotlin/kotlinx.serialization) 1.4.0
- [x] [Dagger Hilt](https://github.com/google/dagger) 2.44
- [x] [OkHttp](https://github.com/square/okhttp) 4.10.0
- [x] [Retrofit](https://github.com/square/retrofit) 2.9.0
- [x] [Retrofit Kotlinx Converter Serialization](https://github.com/JakeWharton/retrofit2-kotlinx-serialization-converter) 0.8.0
- [x] [Chucker](https://github.com/ChuckerTeam/chucker) 3.5.2
- [x] [Coil](https://github.com/coil-kt/coil) 2.2.1
- [x] [DataStore](https://d.android.com/datastore) 1.0.0
- [x] [Startup](https://d.android.com/jetpack/androidx/releases/startup) 1.1.1
- [x] [Timber](https://github.com/JakeWharton/timber) 5.0.1
- [x] [Accompanist](https://github.com/google/accompanist) 0.25.1
- [x] Crashlytics (Firebase)
- [ ] Analytics (Firebase)
- [ ] MVVM
- [ ] Gradle Kotlin DSL
- [ ] Room
- [ ] Navigation (deep links)
- [ ] Kotlin Coroutines & Flow
- [ ] Clean Architecture
- [ ] Unit Tests
- [ ] UI Tests
- [ ] SplashScreen API
- [ ] Firebase Remote Config
- [ ] In-App Updates
- [ ] In-App Review
- [ ] Dynamic App Icon
- [ ] App Shortcuts
- [ ] Google Ads
- [ ] Lifecycle API
- [ ] Paging Library
- [ ] Preloaded Fonts
- [ ] TMDB API
- [ ] Firebase App Distribution
- [ ] WorkManager
- [ ] ConstraintLayout
- [ ] Detekt
- [ ] Lint
- [ ] KtLint
- [ ] Spotless
- [ ] Github Actions
- [ ] OAuth
- [ ] Animations
- [ ] Landscape orientation

## Issues
If you find any problems or would like to suggest a feature, please feel free to file an [issue](https://github.com/michaelbel/moviemade/issues).

## License
<a href="http://www.apache.org/licenses/LICENSE-2.0" target="_blank">
  <img alt="Apache License 2.0" src="screenshots/apache.png" height="110"/>
</a>

    Copyright 2017 Michael Bely

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.