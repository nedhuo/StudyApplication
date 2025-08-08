plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinKapt)
}

configureAndroidLib()

android {
    namespace = "com.example.LibCommon"
    
    kotlinOptions {
        jvmTarget = ProjectConfig.jvmTarget
    }
}

dependencies {
    api(project(ProjectModules.libBase))
    api(project(ProjectModules.libNetwork))
    api(project(ProjectModules.libLog))
    api(project(ProjectModules.libUtils))
    
    // AndroidX
    api(Deps.AndroidX.coreKtx)
    api(Deps.AndroidX.appcompat)
    api(Deps.AndroidX.material)
    api(Deps.AndroidX.constraintLayout)
    api(Deps.AndroidX.recyclerView)
    api(Deps.AndroidX.swipeRefreshLayout)
    
    // Lifecycle
    api(Deps.Lifecycle.viewModel)
    api(Deps.Lifecycle.liveData)
    api(Deps.Lifecycle.runtime)

    implementation(libs.androidx.startup.runtime)

    // Navigation
    api(Deps.Navigation.fragment)
    api(Deps.Navigation.ui)
    
    // Coroutines
    api(Deps.Kotlin.coroutinesCore)
    api(Deps.Kotlin.coroutinesAndroid)
    
    // Glide
    api(Deps.Image.glide)
    kapt(Deps.Image.glideCompiler)
    
    // RecyclerView Adapter
    api(Deps.RecyclerView.brvah)
    
    // Testing
    testImplementation(Deps.Test.junit)
    androidTestImplementation(Deps.Test.androidJunit)
    androidTestImplementation(Deps.Test.espresso)
}