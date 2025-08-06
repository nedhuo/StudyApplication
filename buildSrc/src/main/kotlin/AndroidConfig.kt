import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

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


/**
 * 配置App模块的依赖关系
 */
val moduleList = mutableListOf<String>(
    ProjectModules.libBase,
    ProjectModules.libCommon,
    ProjectModules.featureMain,
    ProjectModules.libNetwork,
    ProjectModules.libLog,
    ProjectModules.libUtils,
    ProjectModules.libConfig,
    ProjectModules.libDatabase,
    ProjectModules.featureLogin,
)