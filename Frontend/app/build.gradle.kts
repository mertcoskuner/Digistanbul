plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.a310frontend"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.example.a310frontend"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        buildFeatures {

            viewBinding = true

        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_16
        targetCompatibility = JavaVersion.VERSION_16
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.android.volley:volley:1.2.1")
    testImplementation("junit:junit:4.13.2")
    implementation ("com.squareup.picasso:picasso:2.71828")
    implementation ("com.squareup.okhttp3:okhttp:4.9.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

}