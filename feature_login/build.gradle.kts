import org.gradle.kotlin.dsl.implementation

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

if (ProjectConfig.isModuleRunAlone) {
    configureAndroidApp()
} else {
    configureAndroidLib()
}
android {
    namespace = "com.example.feature_login"

}

dependencies {
    implementation(project(ProjectModules.libBase))
    implementation(project(ProjectModules.libNetwork))

    implementation(Deps.AndroidX.coreKtx)
    implementation(Deps.AndroidX.appcompat)
    implementation(Deps.AndroidX.material)
    implementation(Deps.AndroidX.constraintLayout)

    // Navigation
    implementation(Deps.Navigation.fragment)
    implementation(Deps.Navigation.ui)

    // ViewModel & LiveData
    implementation(Deps.Lifecycle.runtime)
    implementation(Deps.Lifecycle.viewModel)
    implementation(Deps.Lifecycle.liveData)

    implementation(Deps.Google.hiltAndroid)
    kapt(Deps.Google.hiltCompiler)

}