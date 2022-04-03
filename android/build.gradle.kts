import org.jetbrains.kotlin.config.JvmAnalysisFlags.useIR

plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jetbrains.compose") version "1.0.1-rc2"
    id("kotlin-android")
}

group = "me.ztvr"
version = "1.0-SNAPSHOT"
val ktorVersion: String by rootProject.extra


repositories {
    google()
    mavenCentral()
    maven ( url= "https://jitpack.io" )

}

dependencies {
    implementation(project(":library"))
    implementation(project(":otherLibraryModule"))
    implementation("com.google.android.material:material:1.2.1")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.2")
    implementation("com.eclipsesource.j2v8:j2v8:6.2.0@aar")

/*    implementation("io.coil-kt:coil:1.4.0")
    implementation("io.coil-kt:coil-compose:1.4.0")
*/
    implementation("androidx.compose.runtime:runtime:1.1.0-rc01")
    implementation("androidx.compose.ui:ui:1.1.0-rc01")
    implementation ("androidx.compose.material:material:1.1.0-rc01")
    implementation ("androidx.compose.foundation:foundation:1.1.0-rc01")

    implementation("androidx.activity:activity-compose:1.4.0")

    implementation("androidx.compose.ui:ui-tooling:1.1.0-rc01")
    implementation("androidx.compose.runtime:runtime-livedata:1.1.0-rc01")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.1")

    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:${rootProject.extra["compose_version"]}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${rootProject.extra["compose_version"]}")

    //implementation("com.squareup.okhttp3:okhttp:4.9.0")

}

android {
    compileSdk = 31
    defaultConfig {
        applicationId = "me.ztvr.android"
        minSdk = 24
        targetSdk =31
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
        vectorDrawables {
            useSupportLibrary = true
        }
    }

   compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.1.0-rc01"
        kotlinCompilerVersion = "1.5.10"
    }

    buildFeatures {
        viewBinding = true
    }


    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    kotlinOptions {
        jvmTarget = "1.8"
       // useIR( true)
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }


}