import org.gradle.kotlin.dsl.implementation

plugins {
    if (ProjectConfig.isModuleRunAlone) {
        id(Plugins.androidApplication)
    } else {
        id(Plugins.androidLibrary)
    }
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinKapt)
}

if (ProjectConfig.isModuleRunAlone) {
    configureAndroidApp()
} else {
    configureAndroidLib()
}
android {
    namespace = "com.nedhuo.login.api"

}

dependencies {
    implementation(Deps.AndroidX.coreKtx)
    implementation(Deps.AndroidX.appcompat)
    implementation(Deps.AndroidX.material)
    implementation(Deps.AndroidX.constraintLayout)


}