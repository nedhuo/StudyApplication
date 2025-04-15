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
    
    const val jvmTarget = "1.8"
    val javaVersion = JavaVersion.VERSION_1_8
}