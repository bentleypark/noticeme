# Noticeme
<p align="center">
<a href="https://android-arsenal.com/api?level=21"><img alt="API" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat"/></a>
<a href="https://github.com/bentleypark/noticeme/actions"><img alt="Build Status" src="https://github.com/bentleypark/noticeme/workflows/Android%20CI/badge.svg"/></a>
</p>

<p align="center">
This is android app which informs you of daily routine job at the right time.
</p>
</br>

<p align="center">
<img src="/images/notieme_main_image.png"/>
</p>

## Download
<a href="https://play.google.com/store/apps/details?id=com.project.noticeme"><img alt="Build Status" src="https://play.google.com/intl/en_us/badges/images/generic/en-play-badge.png" height="100"/></a>

Currently only Korean version is available on playstore.

## Table of Contents
- [Techstack](#techstack)
- [Architecture](#architecture)
- [Package](#package)
- [Screenshot](#screenshot)
- [Documentation](#documentation)

## Techstack

- Android 5.0 (API level 21) or later
- Kotlin
- Coroutine + Flow : Asynchronous event handling
- Dagger-Hilt : Dependency injection
- Jetpack
    - LiveData
    - ViewModel
    - Lifecycle
    - Room
    - Startup
    - Navigation
- Coil : Image Loader
- Timber: Debug Log
- Sentry: Error Monitor
- Admob : In-app advertisement

## Architecture
This project is developed based on MVVM architecture which is recommended by Google.

<img src="/images/final-architecture.png" height="700"/>


## Package
<img src="/images/package_tree.png" />

## Screenshot

<img src="/images/screenshot01.png" height="500"/>
<img src="/images/screenshot02.png" height="500"/> <img src="/images/screenshot03.png" height="500"/>


## Documentation
See [`CHANGELOG.md`](CHANGELOG.md) for a brief history of the project