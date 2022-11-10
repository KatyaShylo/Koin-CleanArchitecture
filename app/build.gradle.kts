import org.jetbrains.kotlin.ir.backend.js.compile

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {

    val googleMapApiKey: String by project

    namespace = "com.example.mykinopoisk"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.example.mykinopoisk"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        manifestPlaceholders["googleMapApiKey"] = googleMapApiKey

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(project(":data"))
    implementation(project(":domain"))

    //Navigation
    implementation(libs.bundles.androidx.navigation)

    //Coroutines
    implementation(libs.kotlinx.coroutines)

    implementation(libs.androidx.lifecycle.runtime)

    // Koin
    implementation(libs.koin)

    //Swipe
    implementation(libs.androidx.swiperefreshlayout)

    // Fragment
    implementation(libs.androidx.fragment)

    //Coil
    implementation(libs.coil)

    //Map
    implementation(libs.google.mapsUtils)
    implementation(libs.google.maps)
    implementation(libs.google.location)

    //Splash Screen
    implementation(libs.androidx.splashScreen)

    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.test.junit)
    androidTestImplementation(libs.test.androidx.junit)
    androidTestImplementation(libs.test.androidx.espresso)


}