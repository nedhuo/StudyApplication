object Versions {
    // AndroidX
    const val coreKtx = "1.12.0"
    const val appcompat = "1.6.1"
    const val material = "1.11.0"
    const val constraintLayout = "2.1.4"
    const val recyclerView = "1.3.2"
    const val swipeRefreshLayout = "1.1.0"

    // Lifecycle
    const val lifecycle = "2.7.0"

    // Navigation
    const val navigation = "2.7.7"

    // Room
    const val room = "2.6.1"

    // Network
    const val retrofit = "2.9.0"
    const val okhttp = "4.12.0"
    const val gson = "2.10.1"
    const val persistentCookieJar = "1.0.1"

    // Kotlin
    const val coroutines = "1.7.3"

    // Image Loading
    const val glide = "4.16.0"

    // RecyclerView Adapter
    const val brvah = "4.1.2"

    // Utils
    const val utilcodex = "1.31.1"

    // WebView
    const val tbs = "44286"
    const val agentweb = "v5.0.6-androidx"

    // Test
    const val junit = "4.13.2"
    const val mockito = "5.7.0"
    const val androidJunit = "1.1.5"
    const val espresso = "3.5.1"

    // Hilt
    const val hilt = "2.50"
}

object Deps {
    object AndroidX {
        val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
        val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
        val material = "com.google.android.material:material:${Versions.material}"
        val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
        val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
        val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshLayout}"
    }

    object Lifecycle {
        val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
        val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
        val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    }

    object Navigation {
        val fragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
        val ui = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    }

    object Room {
        val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
        val roomKtx = "androidx.room:room-ktx:${Versions.room}"
        val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    }


    object Network {
        val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
        val retrofitScalarsConverter = "com.squareup.retrofit2:converter-scalars:${Versions.retrofit}"
        val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
        val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
        val persistentCookieJar = "com.github.franmontiel:PersistentCookieJar:${Versions.persistentCookieJar}"
    }

    object Google {
        val gson = "com.google.code.gson:gson:${Versions.gson}"
        val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
        val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    }

    object Kotlin {
        val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    }

    object Image {
        val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
        val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
    }

    object RecyclerView {
        val brvah = "io.github.cymchad:BaseRecyclerViewAdapterHelper4:${Versions.brvah}"
    }

    object Utils {
        val utilcodex = "com.blankj:utilcodex:${Versions.utilcodex}"
    }

    object WebView {
        val tbs = "com.tencent.tbs:tbssdk:${Versions.tbs}"
        val agentweb = "com.github.Justson.AgentWeb:agentweb-core:${Versions.agentweb}"
    }

    object Test {
        val junit = "junit:junit:${Versions.junit}"
        val mockito = "org.mockito:mockito-core:${Versions.mockito}"
        val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
        val androidJunit = "androidx.test.ext:junit:${Versions.androidJunit}"
        val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    }

    object Log {
        const val timber = "com.jakewharton.timber:timber:5.0.1"
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
    const val libDatabase = ":lib_database"
    const val libWebView = ":lib_webview"
    const val featureLogin = ":feature_login"
    const val featureMain = ":feature_main"
}