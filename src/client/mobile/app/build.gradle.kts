plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.superman.movieticket"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.superman.movieticket"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"https://ce17-113-190-242-151.ngrok-free.app\"")

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
        viewBinding = true
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

    // binding model
    implementation(libs.androidx.lifecycle.viewmodel.ktx)


    // coroutines
    implementation(libs.kotlinx.coroutines.android)

    //di
    implementation(libs.hilt.android)
    implementation(libs.firebase.messaging)
    ksp(libs.hilt.compiler)


    // app core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    //test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    // network
    implementation(libs.retrofit)
    implementation(libs.converter.scalars)

    // conveter
    implementation(libs.gson)
    implementation(libs.retrofit.gson)

    // constrainlayout
    implementation(libs.androidx.constraintlayout.compose)


    // jetpack compose
    val composeBom = platform("androidx.compose:compose-bom:2024.05.00")
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.material.icons.core)
    implementation(libs.androidx.material.icons.extended)
    implementation("androidx.compose.material3:material3-window-size-class")

    implementation(libs.androidx.activity.compose)

    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)
    implementation (libs.androidx.hilt.navigation.compose)



    implementation("io.coil-kt:coil-compose:2.1.0")



  implementation ("com.makeramen:roundedimageview:2.3.0")


    //ViedeoYoutubetrailer
    implementation( "com.pierfrancescosoffritti.androidyoutubeplayer:chromecast-sender:0.28")
    implementation ("androidx.webkit:webkit:1.4.0")

    implementation(libs.androidx.runtime.livedata)

    // Signalr
    implementation(libs.signalr)

    // Datastore
    implementation(libs.datastore)



    //Login Google
    implementation ("com.google.android.gms:play-services-auth:21.2.0")
}