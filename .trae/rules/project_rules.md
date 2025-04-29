# Gradle 构建与项目分层规范（结构化整理）

## 1. Gradle 构建文件与依赖声明规范

### 1.1 构建文件格式
- 全项目统一使用 Kotlin DSL（.kts）：
  - `build.gradle.kts` 替代 `build.gradle`
  - `settings.gradle.kts` 替代 `settings.gradle`

### 1.2 依赖声明
- 项目间依赖：`implementation(project(ProjectModules.xxx))`
- 外部依赖：`implementation(Deps.Xxx.xxx)`
- 所有依赖版本集中管理于 `buildSrc/src/main/kotlin/Dependencies.kt`

### 1.3 Gradle 配置集中管理
- 所有 Gradle 配置（如 compileSdk、minSdk、targetSdk）和依赖声明，必须统一使用 `buildSrc/src/main/kotlin/ProjectConfig.kt` 和 `Dependencies.kt` 中的常量
- 禁止在各模块的 `build.gradle.kts` 文件中硬编码版本号或依赖字符串

## 2. 模块命名与依赖关系

### 2.1 命名规则
- 基础库模块：`lib_` 前缀
- 功能模块：`feature_` 前缀
- 模块名全小写，单词间下划线分隔

### 2.2 依赖关系
- 禁止循环依赖
- `lib_log` 不依赖任何模块
- `lib_base` 可依赖 `lib_log` 及其他基础模块
- 功能模块（`feature_`）间不能直接依赖，需通过基础模块交互
- 依赖关系应单向，避免循环

## 3. feature/业务模块独立运行规范

- 支持 `isModuleRunAlone` 配置，动态切换 application/library 插件
- `android {}` 和 `dependencies {}` 必须为顶层代码块
- 独立运行时设置 `applicationId`，作为可单独调试的 app
- 作为依赖库时不设置 `applicationId`
- 统一基础配置调用 `configureAndroidApp()`，位置在 `android{}` 内部
- 所有配置均引用 buildSrc 常量

## 4. 项目分层架构

### 4.1 lib_ 层（基础能力层）
- 放全局通用、无业务色彩的能力模块，如：lib_base、lib_network、lib_log、lib_database、lib_config、lib_ui

### 4.2 common 层（通用业务层）
- 只建立一个 common 模块
- 通过包结构分领域管理，如 user、pay、share、message、media、location、coupon、address、comment、analytics
- 包下可有 data、ui、service、repository、utils 等子包

### 4.3 feat_ 层（业务功能层）
- 每个具体业务功能独立模块
- 只依赖 common 和 lib_，不直接依赖其他 feat_

### 4.4 依赖关系建议
- lib_ 层：不依赖 common 和 feat_，只被上层依赖
- common 层：只依赖 lib_，不依赖 feat_
- feat_ 层：可依赖 common 和 lib_，不依赖其他 feat_

### 4.5 common 层包内分层建议
- 每个包下可有 data、ui、service、repository、utils 等子包
- 如：
  ```
  common/
    └── user/
        ├── data/
        ├── ui/
        ├── service/
        ├── repository/
        └── UserManager.kt
  ```

### 4.6 补充建议
- 通用业务包过大可拆分为 common_xxx 模块
- 保持包内代码风格统一，便于协作
- 定期梳理归档不再通用的代码，保持 common 层精简 