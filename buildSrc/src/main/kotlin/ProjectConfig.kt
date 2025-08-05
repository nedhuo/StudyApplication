import org.gradle.api.JavaVersion

object ProjectConfig {
    const val applicationId = "com.example.studyapplication"
    const val compileSdk = 34
    const val minSdk = 24
    const val targetSdk = 34
    const val versionCode = 1
    const val versionName = "1.0.0"
    
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    const val consumerProguardFiles = "consumer-rules.pro"
    
    const val jvmTarget = "17"
    val javaVersion = JavaVersion.VERSION_17
    
    // Kotlin
    const val kotlin = "1.9.22"
    
    // Module Configuration
    const val isModuleRunAlone = false  // 控制模块是否可以独立运行，true：可以独立运行，false：作为依赖库运行
}