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
    
    testImplementation(Deps.Test.junit)
    androidTestImplementation(Deps.Test.androidxJunit)
    androidTestImplementation(Deps.Test.espresso)
}