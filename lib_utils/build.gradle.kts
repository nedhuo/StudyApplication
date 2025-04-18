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
    // AndroidX
    implementation(Deps.AndroidX.coreKtx)
    implementation(Deps.AndroidX.appcompat)
    
    // Utils
    api(Deps.Utils.utilcodex)
    
    // Testing
    testImplementation(Deps.Test.junit)
    androidTestImplementation(Deps.Test.androidJunit)
    androidTestImplementation(Deps.Test.espresso)
}