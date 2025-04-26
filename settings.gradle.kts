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
include(":app")
include(":lib_base")
include(":lib_network")
include(":lib_log")
include(":lib_common")
include(":lib_utils")
include(":lib_monitor")
include(":lib_config")
include(":lib_database")
include(":feature_login")
include(":feature_main")
include(":feature_tvbox")
include(":lib_player")
include(":lib_spider")