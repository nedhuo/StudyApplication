plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
}

configureAndroidLib()

android {
    namespace = "com.example.lib_utils"
    
    kotlinOptions {
        jvmTarget = ProjectConfig.jvmTarget
    }
}

dependencies {
    implementation(project(ProjectModules.libBase))
    
    api(Deps.coreKtx)
    api(Deps.appcompat)
    
    // Utils
    api(Deps.utilcodex)
    
    testImplementation(Deps.Test.junit)
    androidTestImplementation(Deps.Test.androidxJunit)
    androidTestImplementation(Deps.Test.espresso)
}