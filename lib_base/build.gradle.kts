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
    
    implementation(project(ProjectModules.libLog))
    implementation(project(ProjectModules.libNetwork))
    
    testImplementation(Deps.Test.junit)
    androidTestImplementation(Deps.Test.androidJunit)
    androidTestImplementation(Deps.Test.espresso)
}