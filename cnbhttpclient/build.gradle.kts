plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
   // id("com.google.dagger.hilt.android") // Hilt plugin
    id("kotlin-kapt")
    id("maven-publish")
}

publishing {
    publications {

    }
}

android {
    namespace = "com.android.app.cnbhttpclient"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        //  consumerProguardFiles("consumer-rules.pro")
        consumerProguardFiles("lib-proguard-rules.txt")
    }
//    defaultConfig {
//        consumerProguardFiles("lib-proguard-rules.txt")
//    }

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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.retrofit.core) // Exposes Retrofit to dependent modules
    implementation(libs.retrofit.converter)// Exposes Gson converter if needed

    implementation(libs.okhttp.core)
    implementation(libs.okhttp.logger)

//    api("com.google.dagger:hilt-android:2.50")
//    kapt("com.google.dagger:hilt-android-compiler:2.50")
}

//kapt {
//    correctErrorTypes = true
//}