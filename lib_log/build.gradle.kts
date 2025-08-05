plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
}

configureAndroidLib()
android {
    namespace = "com.example.lib_log"

}

dependencies {
    implementation(Deps.AndroidX.coreKtx)
    implementation(Deps.Network.okhttp)
    implementation(Deps.Log.timber)
    
    testImplementation(Deps.Test.junit)
    androidTestImplementation(Deps.Test.androidJunit)
    androidTestImplementation(Deps.Test.espresso)
}