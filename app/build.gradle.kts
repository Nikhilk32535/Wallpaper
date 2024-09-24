import shadow.bundletool.com.android.tools.r8.internal.id

plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.gms.google-services")
}


android {
    namespace = "com.example.wallpaper"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.wallpaper"
        minSdk = 26
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true
    }

}


dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.database)
    implementation(libs.recyclerview)
    implementation(libs.firebase.storage)
    implementation(libs.volley)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.firebase.auth)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation (libs.glide)
    implementation (libs.photoview)
    implementation (libs.okhttp)

    implementation (libs.rxjava)
    implementation (libs.rxandroid)

    implementation(libs.tablericons)

}