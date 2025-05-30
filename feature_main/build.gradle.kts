// 根据 isModuleRunAlone 动态切换插件
plugins {
    if (ProjectConfig.isModuleRunAlone) {
        id(Plugins.androidApplication)
    } else {
        id(Plugins.androidLibrary)
    }
    id(Plugins.kotlinAndroid)
}

if (ProjectConfig.isModuleRunAlone) {
    configureAndroidApp()
} else {
    configureAndroidLib()
}

android {
    namespace = "com.example.feature_main"
    
    defaultConfig {
        if (ProjectConfig.isModuleRunAlone) {
            applicationId = "${ProjectConfig.applicationId}.feature.main"
        }
    }
    
    kotlinOptions {
        jvmTarget = ProjectConfig.jvmTarget
    }
    
    buildFeatures {
        viewBinding = true
    }
    
    sourceSets {
        getByName("main") {
            if (ProjectConfig.isModuleRunAlone) {
                manifest.srcFile("src/main/debug/AndroidManifest.xml")
            } else {
                manifest.srcFile("src/main/AndroidManifest.xml")
            }
        }
    }
}

dependencies {
    implementation(Deps.AndroidX.coreKtx)
    implementation(Deps.AndroidX.appcompat)
    implementation(Deps.AndroidX.material)
    implementation(Deps.AndroidX.constraintLayout)
    
    // Navigation
    implementation(Deps.AndroidX.Navigation.fragment)
    implementation(Deps.AndroidX.Navigation.ui)
    
    // ViewModel & LiveData
    implementation(Deps.AndroidX.Lifecycle.runtime)
    implementation(Deps.AndroidX.Lifecycle.viewModel)
    implementation(Deps.AndroidX.Lifecycle.liveData)
    
    implementation(project(ProjectModules.libBase))
    
    testImplementation(Deps.Test.junit)
    androidTestImplementation(Deps.Test.androidJunit)
    androidTestImplementation(Deps.Test.espresso)
} 