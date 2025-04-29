plugins {
    id(if (ProjectConfig.isModuleRunAlone) Plugins.androidApplication else Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinKapt)
    id("dagger.hilt.android.plugin")
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

    // AndroidX 基础
    implementation(Deps.AndroidX.coreKtx)
    implementation(Deps.AndroidX.appcompat)
    implementation(Deps.AndroidX.material)
    implementation(Deps.AndroidX.constraintLayout)
    implementation(Deps.AndroidX.recyclerView)
    implementation(Deps.AndroidX.swipeRefreshLayout)
    // implementation(Deps.AndroidX.fragmentKtx) // 已移除未声明依赖

    // Lifecycle
    implementation(Deps.AndroidX.Lifecycle.viewModel)
    implementation(Deps.AndroidX.Lifecycle.liveData)
    implementation(Deps.AndroidX.Lifecycle.runtime)

    // Navigation
    implementation(Deps.AndroidX.Navigation.fragment)
    implementation(Deps.AndroidX.Navigation.ui)

    // Room
    implementation(Deps.AndroidX.Room.runtime)
    implementation(Deps.AndroidX.Room.ktx)
    kapt(Deps.AndroidX.Room.compiler)

    // 协程
    implementation(Deps.Kotlin.coroutinesAndroid)

    // Hilt
    implementation(Deps.Google.hiltAndroid)
    kapt(Deps.Google.hiltCompiler)

    // OkHttp
    implementation(Deps.Network.okhttp)
    implementation(Deps.Network.okhttpLogging)

    // Glide
    implementation(Deps.Image.glide)
    kapt(Deps.Image.glideCompiler)

    // Test
    testImplementation(Deps.Test.junit)
    androidTestImplementation(Deps.Test.androidJunit)
    androidTestImplementation(Deps.Test.espresso)
}