# 登录模块（lib_login）

## 模块说明
`lib_login` 是一个独立的登录认证模块，提供完整的用户认证功能，包括登录、注册、密码重置等功能。该模块采用 MVVM 架构设计，支持多种登录方式。

## 功能特性
- 支持多种登录方式：
  - 账号密码登录
  - 手机号验证码登录
  - 第三方平台登录（微信、QQ等）
- 用户注册功能
- 密码重置/找回
- 登录状态管理
- Token 管理与刷新
- 生物识别登录（指纹/面部识别）

## 架构设计
```
lib_login/
├── api/                 # 网络接口定义
├── data/               # 数据层
│   ├── model/         # 数据模型
│   ├── repository/    # 数据仓库
│   └── source/        # 数据源
├── di/                 # 依赖注入
├── ui/                 # 界面层
│   ├── login/         # 登录界面
│   ├── register/      # 注册界面
│   └── reset/         # 密码重置界面
└── utils/             # 工具类
```

## 主要类说明
- `LoginViewModel`: 登录业务逻辑处理
- `LoginRepository`: 登录数据仓库
- `UserManager`: 用户信息管理
- `TokenManager`: Token 管理
- `LoginApi`: 登录相关接口定义

## 使用方法
1. 在 app 模块的 build.gradle.kts 中添加依赖：
```kotlin
implementation(project(":lib_login"))
```

2. 初始化登录模块：
```kotlin
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        LoginManager.init(this)
    }
}
```

3. 启动登录界面：
```kotlin
LoginActivity.start(context)
```

4. 获取登录状态：
```kotlin
if (LoginManager.isLoggedIn()) {
    // 已登录
} else {
    // 未登录
}
```

## 自定义配置
可在 Application 中配置以下参数：
```kotlin
LoginConfig.Builder()
    .setLoginTimeout(30_000L)           // 登录超时时间
    .setTokenExpireTime(7 * 24 * 3600L) // Token 过期时间
    .setAutoLogin(true)                 // 是否自动登录
    .build()
```

## 事件监听
```kotlin
LoginManager.addLoginStateListener { state ->
    when (state) {
        is LoginState.Success -> // 登录成功
        is LoginState.Failure -> // 登录失败
        is LoginState.TokenExpired -> // Token过期
    }
}
```

## 混淆配置
```proguard
-keep class com.example.lib_login.data.model.** { *; }
-keep class com.example.lib_login.api.** { *; }
```

## 注意事项
1. 需要在 AndroidManifest.xml 中添加相关权限：
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.USE_BIOMETRIC" />
```

2. 第三方登录需要在项目根目录下的 local.properties 中配置相关 Key：
```properties
WECHAT_APP_ID=your_wechat_app_id
WECHAT_APP_SECRET=your_wechat_app_secret
```

## 版本历史
- 1.0.0
  - 基础登录功能
  - 账号密码登录
  - Token 管理
- 1.1.0
  - 添加手机号验证码登录
  - 支持指纹登录
- 1.2.0
  - 添加第三方登录支持
  - 优化登录流程 