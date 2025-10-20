plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("org.jetbrains.kotlin.plugin.serialization") version libs.versions.kotlin.get()
}

android {
    namespace = "ru.fnkr.drivenextapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "ru.fnkr.drivenextapp"
        minSdk = 30
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation("io.github.jan-tennert.supabase:gotrue-kt:2.5.3")     // Аутентификация
    implementation("io.github.jan-tennert.supabase:postgrest-kt:2.5.3")  // Работа с базой данных
    implementation("io.github.jan-tennert.supabase:storage-kt:2.5.3")    // Supabase Storage
    implementation("io.github.jan-tennert.supabase:realtime-kt:2.5.3")   // Реалтайм
    implementation("io.github.jan-tennert.supabase:functions-kt:2.5.3")  // Edge Functions

    val ktor2 = "2.3.12"
    implementation("io.ktor:ktor-client-android:$ktor2")                 // <-- engine
    // или: implementation("io.ktor:ktor-client-okhttp:$ktor2")          // <-- альтернативный engine

    implementation("io.ktor:ktor-client-content-negotiation:$ktor2")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor2")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}