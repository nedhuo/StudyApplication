plugins {
    id(if (ProjectConfig.isModuleRunAlone) Plugins.androidApplication else Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinKapt)
}

android {
    configureAndroidApp()

    namespace = "com.example.feature_tvbox"
    // 独立运行时的 applicationId
    if (ProjectConfig.isModuleRunAlone) {
        defaultConfig {
            applicationId = "com.example.feature_tvbox"
        }
    }
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        minSdk = ProjectConfig.minSdk
        testInstrumentationRunner = ProjectConfig.testInstrumentationRunner
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
    buildFeatures {
        viewBinding = true
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
    implementation(project(ProjectModules.libBase))
    implementation(project(ProjectModules.libNetwork))
    implementation(project(ProjectModules.libDatabase))
    implementation(project(ProjectModules.libSpider))
    implementation(project(ProjectModules.libPlayer))

    implementation(Deps.AndroidX.coreKtx)
    implementation(Deps.AndroidX.appcompat)
    implementation(Deps.AndroidX.material)
    implementation(Deps.AndroidX.constraintLayout)

    // Lifecycle
    implementation(Deps.AndroidX.Lifecycle.viewModel)
    implementation(Deps.AndroidX.Lifecycle.liveData)
    implementation(Deps.AndroidX.Lifecycle.runtime)

    // Coroutines
    implementation(Deps.Kotlin.coroutinesAndroid)

    // Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")

    testImplementation(Deps.Test.junit)
    androidTestImplementation(Deps.Test.androidJunit)
    androidTestImplementation(Deps.Test.espresso)
}