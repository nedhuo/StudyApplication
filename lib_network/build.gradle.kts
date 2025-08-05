plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.lib_network"
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        minSdk = ProjectConfig.minSdk
        
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = ProjectConfig.javaVersion
        targetCompatibility = ProjectConfig.javaVersion
    }
    
    kotlinOptions {
        jvmTarget = ProjectConfig.jvmTarget
    }
}

dependencies {
    implementation(project(ProjectModules.libLog))
    implementation(project(ProjectModules.libConfig))
    
    // AndroidX Core
    implementation(Deps.AndroidX.coreKtx)
    implementation(Deps.AndroidX.appcompat)
    
    // Retrofit & OkHttp
    api(Deps.Network.retrofit)
    implementation(Deps.Network.retrofitGsonConverter)
    implementation(Deps.Network.retrofitScalarsConverter)
    implementation(Deps.Network.okhttp)
    implementation(Deps.Network.okhttpLogging)
    
    // Gson
    implementation(Deps.Google.gson)
    
    // Coroutines
    implementation(Deps.Kotlin.coroutinesAndroid)
    implementation(Deps.Kotlin.coroutinesCore)
    
    // Lifecycle
    implementation(Deps.Lifecycle.runtime)
    implementation(Deps.Lifecycle.viewModel)
    
    // Cookie
    implementation(Deps.Network.persistentCookieJar)
    
    // Test dependencies
    testImplementation(Deps.Test.junit)
    testImplementation(Deps.Test.mockito)
    testImplementation(Deps.Test.coroutinesTest)
    androidTestImplementation(Deps.Test.androidJunit)
    androidTestImplementation(Deps.Test.espresso)
}