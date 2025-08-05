plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinKapt)
}

configureAndroidLib()
android {
    namespace = "com.example.lib_database"

}

dependencies {
    implementation(project(ProjectModules.libBase))
    
    // Room
    implementation(Deps.Room.roomRuntime)
    implementation(Deps.Room.roomKtx)
    kapt(Deps.Room.roomCompiler)
    
    // Coroutines
    implementation(Deps.Kotlin.coroutinesAndroid)
    
    // Test
    testImplementation(Deps.Test.junit)
    androidTestImplementation(Deps.Test.androidJunit)
    androidTestImplementation(Deps.Test.espresso)
}

kapt {
    arguments {
        arg("room.schemaLocation", "$projectDir/schemas")
    }
} 