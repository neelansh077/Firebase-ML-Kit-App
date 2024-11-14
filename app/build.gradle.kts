plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.project"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.project"
        minSdk = 24
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

    aaptOptions{
        noCompress; "tflite"
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation ("androidx.appcompat:appcompat:1.2.0")


    implementation(platform("com.google.firebase:firebase-bom:33.5.1"))
//    Recognise Text
    implementation("com.google.firebase:firebase-analytics")
    implementation ("com.google.mlkit:language-id:17.0.6")
//    Translate text
    implementation ("com.google.mlkit:translate:17.0.3")

    // To recognize Latin script
    implementation ("com.google.mlkit:text-recognition:16.0.1")

    // To recognize Chinese script
    implementation ("com.google.mlkit:text-recognition-chinese:16.0.1")

    // To recognize Devanagari script
    implementation ("com.google.mlkit:text-recognition-devanagari:16.0.1")

    // To recognize Japanese script
    implementation ("com.google.mlkit:text-recognition-japanese:16.0.1")

    // To recognize Korean script
    implementation ("com.google.mlkit:text-recognition-korean:16.0.1")


    implementation ("com.google.mlkit:image-labeling:17.0.9")

    implementation ("com.google.android.gms:play-services-mlkit-image-labeling:16.0.8")

    //Barcode Scanner
    implementation ("com.google.mlkit:barcode-scanning:17.3.0")
    implementation ("com.google.android.gms:play-services-mlkit-barcode-scanning:18.3.1")

    //Smart Replies
    implementation ("com.google.mlkit:smart-reply:17.0.4")
    implementation ("com.google.mlkit:smart-reply:16.1.1")

    implementation ("com.google.android.gms:play-services-mlkit-smart-reply:16.0.0-beta1")






}