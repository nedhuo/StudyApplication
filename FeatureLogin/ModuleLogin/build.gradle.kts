import org.gradle.kotlin.dsl.implementation

plugins {
    if (ProjectConfig.isModuleRunAlone) {
        id(Plugins.androidApplication)
    } else {
        id(Plugins.androidLibrary)
    }
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinKapt)
}

if (ProjectConfig.isModuleRunAlone) {
    configureAndroidApp()
} else {
    configureAndroidLib()
}
android {
    namespace = "com.example.feature_login"

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
//    implementation(project(ProjectModules.libBase))
//    implementation(project(ProjectModules.libNetwork))
    implementation(ProjectModules.libBase)
    implementation(ProjectModules.libNetwork)

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