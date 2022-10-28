plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}

dependencies {

    //Coroutines
    implementation(libs.kotlinx.coroutines.core)

    //Map
    implementation(libs.google.mapsUtils)
    implementation(libs.google.maps)
    implementation(libs.google.location)
}