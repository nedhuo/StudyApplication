plugins {
    id(Plugins.androidApplication)
    id(Plugins.kotlinAndroid)
}

configureAndroidApp()

android {
    namespace = "com.example.studyapplication"
    
    kotlinOptions {
        jvmTarget = ProjectConfig.jvmTarget
    }
}

dependencies {
    implementation(project(ProjectModules.libCommon))
    implementation(project(ProjectModules.featureMain))
    
    testImplementation(Deps.Test.junit)
    androidTestImplementation(Deps.Test.androidJunit)
    androidTestImplementation(Deps.Test.espresso)
}