plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.galleryapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.galleryapp"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
    defaultConfig {
        vectorDrawables.useSupportLibrary = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

}

dependencies {
    val roomVersion = "2.6.1"
    val hiltVersion = "2.50"
    val cameraXversion = "1.3.1"
    val lifecycleVersion = "2.7.0"
    val glideVersion = "4.16.0"

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation ("androidx.activity:activity-ktx:1.6.1")
    implementation ("androidx.fragment:fragment-ktx:1.5.5")

    //CameraX
    implementation ("androidx.camera:camera-camera2:$cameraXversion")
    implementation ("androidx.camera:camera-lifecycle:$cameraXversion")
    implementation ("androidx.camera:camera-view:$cameraXversion")

    //Lifecycle
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")

    //dagger hilt
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-compiler:$hiltVersion")

    //Room
    implementation("androidx.room:room-ktx:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-runtime:$roomVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")

    //Glide
    implementation ("com.github.bumptech.glide:glide:$glideVersion")
    kapt ("com.github.bumptech.glide:compiler:$glideVersion")

}

kapt {
    correctErrorTypes = true
}