plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.openclassrooms.tajmahal"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.openclassrooms.tajmahal"
        minSdk = 24
        targetSdk = 33
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

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks.whenTaskAdded {
    if (name == "assembleDebug" || name == "assembleRelease") {
        finalizedBy("testDebugUnitTest")
    }
}

dependencies {

    implementation("androidx.work:work-runtime:2.8.1")
    

    val hiltVersion = "2.44"

    //Hilt
    implementation("com.google.dagger:hilt-android:${hiltVersion}")
    annotationProcessor("com.google.dagger:hilt-compiler:${hiltVersion}")

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.work:work-runtime:2.8.1")
    implementation("androidx.core:core-splashscreen:1.0.0")

    implementation("org.mockito:mockito-core:3.12.4")

    implementation("com.squareup.picasso:picasso:2.71828")

    // JUnit 5 dependencies
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    testImplementation("org.junit.vintage:junit-vintage-engine:5.8.2") // If you want to run JUnit 4 tests as well

    // Optional: Additional dependencies for JUnit 5 features
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.2")

    // Android test dependencies
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}


