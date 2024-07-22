import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    kotlin("plugin.serialization").version("1.9.0")

}

android {
    namespace = "com.example.pixabayapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.pixabayapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        //define apiKey from local.properties and get by BuildConfig.API_KEY
        val properties = Properties()
        properties.load(
            project.rootProject.file("local.properties").inputStream()
        )
        buildConfigField(
            "String",
            "KEY",
            properties.getProperty("KEY")
        )
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
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.compose.material3.adaptive.navigationSuite)
    // hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // add coil
    implementation(libs.coil.kt.compose)
    implementation(libs.coil.kt.compose.video)

    // ktor
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.negotiation)
    implementation(libs.ktor.client.serialization.json)
    implementation(libs.kotlinx.serialization)
    implementation(libs.ktor.client.cio)

    // navigation
    implementation(libs.androidx.hilt.navigation.compose)

    // haze
    implementation(libs.haze.haze)
    implementation(libs.haze.materials)

    implementation(libs.constainLayout)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    testImplementation(libs.truth.test)
    androidTestImplementation(libs.truth.test)
    testImplementation(libs.ktor.server.test)
    testImplementation(libs.kotlin.test.junit)
}