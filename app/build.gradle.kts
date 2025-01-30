plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.CYZco.nygreets"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.CYZco.nygreets"
        minSdk = 16
        targetSdk = 34
        versionCode = 10
        versionName = "2.9.8"

        multiDexEnabled = true
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation (libs.multidex)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.exoplayer)
    implementation(libs.fadingedgelayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}