plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
}

configureAndroidLib()

android {
    namespace = "com.example.lib_base"
    
    kotlinOptions {
        jvmTarget = ProjectConfig.jvmTarget
    }
}

dependencies {
    implementation(Deps.AndroidX.coreKtx)
    implementation(Deps.AndroidX.appcompat)
    
    implementation(project(ProjectModules.libNetwork))
    implementation(project(ProjectModules.libLog))
    
    implementation(Deps.AndroidX.Lifecycle.viewModel)
    implementation(Deps.AndroidX.Lifecycle.runtime)
    implementation(Deps.AndroidX.material)
    
    testImplementation(Deps.Test.junit)
    androidTestImplementation(Deps.Test.androidJunit)
    androidTestImplementation(Deps.Test.espresso)
}