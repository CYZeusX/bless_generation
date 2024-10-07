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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation ("androidx.multidex:multidex:2.0.1")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation("com.google.android.exoplayer:exoplayer:2.19.1")
    implementation("com.github.bosphere.android-fadingedgelayout:fadingedgelayout:1.0.0")
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}