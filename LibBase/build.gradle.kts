plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
}

configureAndroidLib()

android {
    namespace = "com.example.LibBase"
    
    kotlinOptions {
        jvmTarget = ProjectConfig.jvmTarget
    }
}

dependencies {
    implementation(Deps.AndroidX.coreKtx)
    implementation(Deps.AndroidX.appcompat)
    
    implementation(project(ProjectModules.libNetwork))
    implementation(project(ProjectModules.libLog))
    implementation(project(ProjectModules.libUtils))

    implementation(Deps.Image.glide)
    implementation(Deps.Image.webpDecoder)
    
    implementation(Deps.Lifecycle.viewModel)
    implementation(Deps.Lifecycle.runtime)
    implementation(Deps.AndroidX.material)
    
    testImplementation(Deps.Test.junit)
    androidTestImplementation(Deps.Test.androidJunit)
    androidTestImplementation(Deps.Test.espresso)
}