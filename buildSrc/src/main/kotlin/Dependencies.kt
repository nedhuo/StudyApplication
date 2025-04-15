object Versions {
    // AndroidX
    const val coreKtx = "1.12.0"
    const val appcompat = "1.6.1"
    
    // Lifecycle
    const val lifecycle = "2.7.0"
    
    // Network
    const val retrofit = "2.9.0"
    const val okhttp = "4.12.0"
    const val gson = "2.10.1"
    const val persistentCookieJar = "v1.0.1"
    
    // Kotlin
    const val coroutines = "1.7.3"
    
    // Test
    const val junit = "4.13.2"
    const val mockito = "5.7.0"
    const val androidJunit = "1.1.5"
    const val espresso = "3.5.1"
}

object Deps {
    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
        const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
        
        object Lifecycle {
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
        }
    }
    
    object Network {
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
        const val retrofitScalarsConverter = "com.squareup.retrofit2:converter-scalars:${Versions.retrofit}"
        const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
        const val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
        const val persistentCookieJar = "com.github.franmontiel:PersistentCookieJar:${Versions.persistentCookieJar}"
    }
    
    object Google {
        const val gson = "com.google.code.gson:gson:${Versions.gson}"
    }
    
    object Kotlin {
        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    }
    
    object Test {
        const val junit = "junit:junit:${Versions.junit}"
        const val mockito = "org.mockito:mockito-core:${Versions.mockito}"
        const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
        const val androidJunit = "androidx.test.ext:junit:${Versions.androidJunit}"
        const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    }
}

object ProjectModules {
    const val app = ":app"
    const val libBase = ":lib_base"
    const val libNetwork = ":lib_network"
    const val libLog = ":lib_log"
    const val libCommon = ":lib_common"
    const val libUtils = ":lib_utils"
    const val libMonitor = ":lib_monitor"
    const val libConfig = ":lib_config"

    const val featureLogin = ":feature_login"
}