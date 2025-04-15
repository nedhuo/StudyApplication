pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://jitpack.io") }
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
include(":app")
include(":lib_base")
include(":lib_network")
include(":lib_log")
include(":lib_common")
include(":lib_utils")
include(":lib_monitor")
include("lib_config")
include(":feature_login")