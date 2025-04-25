# 依赖管理规则

## 版本管理

1. 项目中的所有 SDK 版本（compileSdk、minSdk、targetSdk）应该统一从 `ProjectConfig` 类中获取，而不是使用 `Versions` 类或硬编码值。

2. `ProjectConfig` 类位于 `buildSrc/src/main/kotlin/ProjectConfig.kt`，包含以下版本信息：
   - compileSdk
   - minSdk
   - targetSdk
   - versionCode
   - versionName
   - jvmTarget
   - javaVersion

## 使用示例

在模块的 `build.gradle.kts` 文件中：

```kotlin
android {
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        minSdk = ProjectConfig.minSdk
        targetSdk = ProjectConfig.targetSdk
    }

    compileOptions {
        sourceCompatibility = ProjectConfig.javaVersion
        targetCompatibility = ProjectConfig.javaVersion
    }

    kotlinOptions {
        jvmTarget = ProjectConfig.jvmTarget
    }
}
```

## 注意事项

1. 不要创建重复的版本管理类（如 `Versions.kt`）
2. 不要在 build.gradle 文件中硬编码版本号
3. 所有模块都应该保持一致的 SDK 版本配置 