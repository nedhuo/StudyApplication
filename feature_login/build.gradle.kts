import org.gradle.kotlin.dsl.implementation

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

configureAndroidLib()
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





    // Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")
    
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}