plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.nedhuo.libnetwork"
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
    api(Deps.Network.retrofit2)
    implementation(Deps.Network.retrofit2GsonConverter)
    implementation(Deps.Network.okhttp)
    implementation(Deps.Network.okhttpLogging)

    implementation(Deps.Google.gson)
    implementation(Deps.Network.persistentCookieJar)

    // Test dependencies
    testImplementation(Deps.Test.junit)
    testImplementation(Deps.Test.mockito)
    testImplementation(Deps.Test.coroutinesTest)
    androidTestImplementation(Deps.Test.androidJunit)
    androidTestImplementation(Deps.Test.espresso)
}