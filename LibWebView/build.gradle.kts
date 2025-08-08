plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
}

configureAndroidLib()
android {
    namespace = "com.example.LibWebView"

}

dependencies {
    implementation(Deps.AndroidX.coreKtx)
    implementation(Deps.AndroidX.appcompat)
    implementation(Deps.AndroidX.material)

    implementation(Deps.Lifecycle.runtime)
    implementation(Deps.Lifecycle.viewModel)
    implementation(Deps.Lifecycle.liveData)
    
    // WebView dependencies
    implementation(Deps.WebView.tbs)
    implementation(Deps.WebView.agentweb)

    implementation(project(ProjectModules.libBase))
    implementation(project(ProjectModules.libCommon))
    implementation(project(ProjectModules.libLog))

    implementation(Deps.Google.gson)

    testImplementation(Deps.Test.junit)
    androidTestImplementation(Deps.Test.androidJunit)
    androidTestImplementation(Deps.Test.espresso)
} 