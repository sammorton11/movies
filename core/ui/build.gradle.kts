@file:Suppress("UnstableApiUsage")

import org.michaelbel.moviemade.dependencies.apiAccompanistDependencies
import org.michaelbel.moviemade.dependencies.apiCoreSplashScreenDependencies

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = 32
}

dependencies {
    apiAccompanistDependencies()
    apiCoreSplashScreenDependencies()
}