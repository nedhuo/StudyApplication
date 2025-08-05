plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
}

configureAndroidLib()
android {
    namespace = "com.example.lib_config"

}

dependencies {
    implementation(Deps.AndroidX.coreKtx)
    implementation(Deps.AndroidX.appcompat)
    implementation(Deps.AndroidX.material)
    implementation(Deps.Kotlin.coroutinesAndroid)
    
    testImplementation(Deps.Test.junit)
    androidTestImplementation(Deps.Test.androidJunit)
    androidTestImplementation(Deps.Test.espresso)
}