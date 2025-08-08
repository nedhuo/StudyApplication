import java.util.Properties

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "StudyApplication"


fun includeModule(moduleName: String, propertyName: String) {
    val localPropertiesFile = rootProject.projectDir.resolve("local.properties")
    localPropertiesFile
        .takeIf { it.exists() }
        ?.inputStream()
        ?.use {
            val properties = Properties().apply { load(it) }
            val property = properties.getProperty(propertyName)
            if (!property.isNullOrEmpty()) {
                include(":$moduleName")
                project(":$moduleName").projectDir = File(property)
            }
        }
}


include(":app")
includeModule("LibBase", "libBase")
includeModule("LibNetwork", "libNetwork")
includeModule("LibLog", "libLog")
includeModule("LibCommon", "libCommon")
includeModule("LibUtils", "libUtils")
includeModule("LibConfig", "libConfig")
includeModule("LibDatabase", "libDatabase")
includeModule("LibWebView", "libWebView")
includeModule("FeatureMain", "featureMain")
includeModule("FeatureLogin", "featureLogin")
includeModule("FeatureLoginApi", "featureLoginApi")
