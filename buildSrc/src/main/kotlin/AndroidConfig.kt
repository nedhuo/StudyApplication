import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

fun Project.configureAndroidApp() {
    extensions.configure<BaseAppModuleExtension> {
        compileSdk = ProjectConfig.compileSdk

        defaultConfig {
            applicationId = ProjectConfig.applicationId
            minSdk = ProjectConfig.minSdk
            targetSdk = ProjectConfig.targetSdk
            versionCode = ProjectConfig.versionCode
            versionName = ProjectConfig.versionName

            testInstrumentationRunner = ProjectConfig.testInstrumentationRunner
        }

        buildTypes {
            release {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }

        compileOptions {
            sourceCompatibility = ProjectConfig.javaVersion
            targetCompatibility = ProjectConfig.javaVersion
        }

        buildFeatures {
            viewBinding = true
        }
    }
}

fun Project.configureAndroidLib() {
    extensions.configure<LibraryExtension> {
        compileSdk = ProjectConfig.compileSdk

        defaultConfig {
            minSdk = ProjectConfig.minSdk
            targetSdk = ProjectConfig.targetSdk

            testInstrumentationRunner = ProjectConfig.testInstrumentationRunner
            consumerProguardFiles(ProjectConfig.consumerProguardFiles)
        }

        buildTypes {
            release {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }

        compileOptions {
            sourceCompatibility = ProjectConfig.javaVersion
            targetCompatibility = ProjectConfig.javaVersion
        }

        buildFeatures {
            viewBinding = true
        }
    }
}