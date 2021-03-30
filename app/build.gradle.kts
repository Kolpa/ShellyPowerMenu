plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.2")

    defaultConfig {
        applicationId("de.kolpa.shellypowermenu")
        minSdkVersion(30)
        targetSdkVersion(30)
        versionCode(1)
        versionName("1.0")
    }

    buildTypes {
        getByName("release") {
            minifyEnabled(false)
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Kotlin Extensions
    implementation(
        kotlin("stdlib", Versions.kotlinVersion)
    )
    implementation(
        "org.jetbrains.kotlinx",
        "kotlinx-coroutines-core",
        Versions.coroutineVersion
    )
    implementation(
        "org.jetbrains.kotlinx",
        "kotlinx-coroutines-android",
        Versions.coroutineVersion
    )
    implementation(
        "org.jetbrains.kotlinx",
        "kotlinx-coroutines-jdk9",
        Versions.coroutineVersion
    )

    // Android Extensions
    implementation(
        "androidx.core",
        "core-ktx",
        "1.3.2"
    )
    implementation(
        "androidx.appcompat",
        "appcompat",
        "1.2.0"
    )
    implementation(
        "androidx.lifecycle",
        "lifecycle-livedata-ktx",
        Versions.lifecycleVersion
    )
    implementation(
        "androidx.constraintlayout",
        "constraintlayout",
        "2.0.4"
    )

    // Koin
    implementation(
        "org.koin",
        "koin-core",
        Versions.koinVersion
    )
    implementation(
        "org.koin",
        "koin-android",
        Versions.koinVersion
    )
    implementation(
        "org.koin",
        "koin-android-viewmodel",
        Versions.koinVersion
    )

    // Volley
    implementation(
        "com.android.volley",
        "volley",
        "1.2.0"
    )

    // GSON
    implementation(
        "com.google.code.gson",
        "gson",
        "2.8.6"
    )
}