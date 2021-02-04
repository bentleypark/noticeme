object Libs {

    // ClassPath
    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.buildGradleVersion}"
    const val kotlinGradlePlugin =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"
    const val googleService = "com.google.gms:google-services:${Versions.googleServiceVersion}"
    const val sentryGradle =
        "io.sentry:sentry-android-gradle-plugin:${Versions.sentryGradleVersion}"
    const val r8 = "com.android.tools:r8:${Versions.r8Version}"
    const val androidJunit5Version =
        "de.mannodermaus.gradle.plugins:android-junit5:${Versions.androidJunit5Version}"
    const val androidMavenGradle =
        "com.github.dcendents:android-maven-gradle-plugin:${Versions.androidMavenGradleVersion}"
    const val detektGradle =
        "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${Versions.detektGradleVersion}"

    const val hiltPlugin =  "com.google.dagger:hilt-android-gradle-plugin:${Versions.hiltVersion}"

    const val navSafeArgPlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navSafArgPluginVersion}"

    const val kotlinAndroidExtension = "org.jetbrains.kotlin:kotlin-android-extensions-runtime:${Versions.kotlinVersion}"

    // Kotlin
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlinVersion}"

    // Coroutines
    const val coroutineAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.androidVersion}"
    const val coroutineCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.androidVersion}"

    // rx java
    const val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.rxJavaVersion}"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroidVersion}"

    // AndroidX
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appCompatVersion}"
    const val constraintlayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayoutVersion}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtxVersion}"
    const val legacySupport =
        "androidx.legacy:legacy-support-v4:${Versions.legacySupportVersion}"
    const val activity = "androidx.activity:activity:${Versions.activityVersion}"
    const val activityKtx = "androidx.activity:activity-ktx:${Versions.activityVersion}"
    const val fragment = "androidx.fragment:fragment:${Versions.fragmentVersion}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragmentVersion}"
    const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerviewVersion}"
    const val viewpager2 = "androidx.viewpager2:viewpager2:${Versions.viewpager2Version}"
    const val swipeRefreshLayout =
        "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshLayoutVersion}"
    const val databindingCompiler =
        "com.android.databinding:compiler:${Versions.databindingVersion}"

    // Work
    const val workRuntime = "androidx.work:work-runtime:${Versions.workVersion}"
    const val workRuntimeKtx = "androidx.work:work-runtime-ktx:${Versions.workVersion}"

    // Test
    const val testCore = "androidx.test:core:${Versions.testVersion}"
    const val testrunner = "androidx.test:runner:${Versions.testVersion}"
    const val testrules = "androidx.test:rules:${Versions.testVersion}"
    const val espressoCore =
        "androidx.test.espresso:espresso-core:${Versions.espressoVersion}"
    const val junitExt = "androidx.test.ext:junit:${Versions.junitExtVersion}"

    // Lifecycle
    const val lifeCyle = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifeCycleVersion}"
    const val viewModel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifeCycleVersion}"
    const val extensions =
        "androidx.lifecycle:lifecycle-extensions:${Versions.lifeCycleVersion}"
    const val liveData =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifeCycleVersion}"
    const val commonJava8 =
        "androidx.lifecycle:lifecycle-common-java8:${Versions.lifeCycleVersion}"
    const val compiler =
        "androidx.lifecycle:lifecycle-compiler:${Versions.lifeCycleVersion}"
    const val viewModelSavedState =
        "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifeCycleVersion}"

    // Room
    const val roomRuntime = "androidx.room:room-runtime:${Versions.roomVersion}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.roomVersion}"

    // Navigation
    const val navFragment =
        "androidx.navigation:navigation-ui:${Versions.navigationVersion}"
    const val navFragmentKtx =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigationVersion}"
    const val navUi = "androidx.navigation:navigation-ui:${Versions.navigationVersion}"
    const val navUiKtx =
        "androidx.navigation:navigation-ui-ktx:${Versions.navigationVersion}"

    // StartUp
    const val startUp = "androidx.startup:startup-runtime:${Versions.startupVersion}"

    // Google
    const val material = "com.google.android.material:material:${Versions.materialVersion}"
    const val gson = "com.google.code.gson:gson:${Versions.gsonVersion}"
    const val firebaseAnalytics =
        "com.google.firebase:firebase-analytics:${Versions.firebaseAnalyticsVersion}"
    const val playCoreKtx = "com.google.android.play:core-ktx:${Versions.playCoreVersion}"

    // Junit
    const val junit4 = "junit:junit:${Versions.junitVersion}"

    // Retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
    const val gsonConverter =
        "com.squareup.retrofit2:converter-gson:${Versions.retrofitVersion}"

    // Moshi
    const val moshiCore = "com.squareup.moshi:moshi:${Versions.moshiVersion}"
    const val moshiCodegen =
        "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshiVersion}"

    // Okhttp
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp3Version}"
    const val loggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp3Version}"

    // RxBiding
    const val rxbinding = "com.jakewharton.rxbinding3:rxbinding:${Versions.rxbindingVersion}"

    // Lottie
    const val lottie = "com.airbnb.android:lottie:${Versions.lottieVerison}"

    // Debug
    const val timber = "com.jakewharton.timber:timber:${Versions.timberVersion}"
    const val leakcanary =
        "com.squareup.leakcanary:leakcanary-android:${Versions.leakcanaryVersion}"
    const val stetho = "com.facebook.stetho:stetho:${Versions.stethoVersion}"
    const val stethoOkhttp3 = "com.facebook.stetho:stetho-okhttp3:${Versions.stethoVersion}"
    const val stethoJs = "com.facebook.stetho:stetho-js-rhino:${Versions.stethoVersion}"
    const val sentry = "io.sentry:sentry-android:${Versions.sentryVersion}"

    // Klint
    const val klint = "com.pinterest:ktlint:${Versions.klintVersion}"

    // Hilt
    const val hiltAndroid =  "com.google.dagger:hilt-android:${Versions.hiltVersion}"
    const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hiltVersion}"

    const val hiltViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hiltViewModel}"
    const val hiltCompiler = "androidx.hilt:hilt-compiler:${Versions.hiltViewModel}"

    // Admob
    const val admob = "com.google.android.gms:play-services-ads:${Versions.admobVersion}"

    // Colil
    const val coil = "io.coil-kt:coil:${Versions.coilVersion}"
}
