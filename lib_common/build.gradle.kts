plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinKapt)
}

configureAndroidLib()

android {
    namespace = "com.example.lib_common"
    
    kotlinOptions {
        jvmTarget = ProjectConfig.jvmTarget
    }
}

dependencies {
    api(project(ProjectModules.libBase))
    api(project(ProjectModules.libNetwork))
    api(project(ProjectModules.libLog))
    api(project(ProjectModules.libUtils))
    
    api(Deps.coreKtx)
    api(Deps.appcompat)
    api(Deps.material)
    api(Deps.constraintLayout)
    api(Deps.recyclerView)
    api(Deps.swipeRefreshLayout)
    
    api(Deps.Lifecycle.viewModel)
    api(Deps.Lifecycle.liveData)
    api(Deps.Lifecycle.runtime)
    
    api(Deps.Navigation.fragment)
    api(Deps.Navigation.ui)
    
    api(Deps.Coroutines.core)
    api(Deps.Coroutines.android)
    
    api(Deps.Glide.core)
    kapt(Deps.Glide.compiler)
    
    api(Deps.brvah)
    
    testImplementation(Deps.Test.junit)
    androidTestImplementation(Deps.Test.androidxJunit)
    androidTestImplementation(Deps.Test.espresso)
}