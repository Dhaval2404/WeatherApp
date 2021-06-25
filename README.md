<h1 align="center">Weather App</h1>

<p align="center">

[![Releases](https://img.shields.io/github/release/Dhaval2404/WeatherApp/all.svg?style=flat-square)](https://github.com/Dhaval2404/WeatherApp/releases)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)
![Language](https://img.shields.io/badge/language-Kotlin-orange.svg)
![Build Status](https://github.com/Dhaval2404/WeatherApp/workflows/Build/badge.svg)
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)
[![Open Source Love](https://badges.frapsoft.com/os/v1/open-source.svg?v=102)](https://opensource.org/licenses/Apache-2.0)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](https://github.com/Dhaval2404/WeatherApp/blob/master/LICENSE)
</p>

<p align="center">
🌦 Everybody wants to know how it is going to be during the week. Will it be rainy, windy, or sunny? Luckily for us, in the information age, there are open APIs to retrieve information about it.
</p>
</br>

<p align="center">
<img src="https://github.com/Dhaval2404/WeatherApp/blob/master/arts/preview%201.png"/>
<img src="https://github.com/Dhaval2404/WeatherApp/blob/master/arts/preview%202.png"/>
</p>

## Tech stack & Open-source libraries
- Minimum SDK level 21
- [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) for asynchronous.
- [Koin](https://insert-koin.io/) for dependency injection.
- JetPack
  - LiveData - notify domain layer data to views.
  - ViewModel - UI related data holder, lifecycle aware.
  - Data Binding - Bind UI components in your layouts to data sources.
  - Room Persistence - construct a database using the abstract layer.
  - Navigation - Single activity with multiple fragments.
  - Settings - allow users to change the functionality and behavior of an application.
- Architecture
  - MVVM Architecture (View - DataBinding - ViewModel - Model)
  - Repository pattern
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - construct the REST APIs and Fetch network data.
- [Moshi](https://github.com/square/moshi) - A modern JSON library for Kotlin and Java..
- [Coil](https://github.com/coil-kt/coil) - loading images.
- [Lottie](https://github.com/airbnb/lottie-android) - For animation view
- [logger](https://github.com/orhanobut/logger) - logging.
- [ktlint](https://github.com/pinterest/ktlint) - Kotlin linter with built-in formatter.
- [detekt](https://github.com/detekt/detekt) - Static code analysis tool for the Kotlin.
- [Material-Components](https://github.com/material-components/material-components-android) - Material design components like cardView.
- [Material-Icons](https://fonts.google.com/icons?selected=Material+Icons) - Material icons are based on the core Material Design principles and metrics.
- Google Map & Place API - For city selection
- [junit4](https://junit.org/junit4)
- [mockk](https://github.com/mockk/mockk)
- [truth](https://github.com/google/truth)

## Credit

* App Logo</br>
	Auther: [Linector](https://www.flaticon.com/authors/linector)</br>
	Source: [https://www.flaticon.com/free-icon/cloudy_382565](https://www.flaticon.com/free-icon/cloudy_382565)

* Lottie Animation</br>
	Auther: [Grigoriy](https://lottiefiles.com/grigoriy)</br>
	Source: [https://lottiefiles.com/39090-beautiful-city](https://lottiefiles.com/39090-beautiful-city)

* Weather API</br>
	Provider: OpenWeather</br>
	Source: [https://openweathermap.org/api](https://openweathermap.org/api)


## License

    Copyright 2021, Dhaval Patel

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

