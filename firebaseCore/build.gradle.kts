plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp")
    kotlin("plugin.serialization") version "1.9.23"
}

dependencies {

    // implementation (project(":advanceCore"))
    implementation (project(":cache"))
    implementation (project(":utils"))
    implementation (project(":firebase-inappmessaging"))
    implementation (project(":domain"))

    implementation (platform(libs.firebaseBom))
    implementation (libs.firebaseAnalytics)
    implementation (libs.firebaseConfig)
    implementation (libs.firebaseDatabase)
  //  implementation libs.firebaseInappmessaging

    // For make an http requests
    implementation (libs.ktor.client.okhttp)
    implementation (libs.ktor.client.core)
    implementation (libs.ktor.client.content.negotiation)
    implementation (libs.ktor.client.serialization)


    // Joda time to parse standard date formats
    implementation (libs.joda)

}