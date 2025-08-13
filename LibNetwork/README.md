# lib_network

## 模块简介
lib_network是应用的网络请求管理核心模块，提供统一、高效、安全的网络通信能力。本模块基于Retrofit和OkHttp构建，通过封装网络请求流程、拦截器链、缓存策略和错误处理机制，为上层业务模块提供简洁易用的网络接口，同时保证网络通信的稳定性和安全性。

## 模块角色
网络请求管理模块，提供统一的网络请求封装、拦截器管理和缓存策略，是应用网络层的统一入口。

## 模块边界
### 职责范围
✅ 允许：
- 网络请求封装
- 拦截器管理
- 缓存策略
- 错误处理
- 安全通信

❌ 禁止：
- 不处理具体业务 API
- 不处理业务数据解析
- 不直接操作 UI 或存储

### 依赖规则
- 依赖 lib_base、lib_log
- 可被其他模块依赖，用于网络请求
- 通过泛型和接口解耦业务数据结构
- 依赖声明方式：`implementation(project(ProjectModules.lib_network))`
- 版本管理：依赖版本统一由`buildSrc/src/main/kotlin/Dependencies.kt`管理

## 主要功能

### 1. 网络请求
- RetrofitManager：Retrofit 管理器，负责配置和创建Retrofit实例
- ApiFactory：API 接口工厂，用于创建API接口实例
- RequestBuilder：请求构建器，提供链式调用构建请求
- ResponseHandler：响应处理器，统一处理网络响应

### 2. 拦截器管理
- InterceptorManager：拦截器管理器，负责注册和管理拦截器链
- HeaderInterceptor：请求头拦截器，添加公共请求头
- LogInterceptor：日志拦截器，打印请求和响应日志
- CacheInterceptor：缓存拦截器，处理请求缓存
- TokenInterceptor：令牌拦截器，处理令牌验证和刷新

### 3. 缓存策略
- CacheManager：缓存管理器，统一管理缓存配置
- CacheStrategy：缓存策略定义，支持多种缓存策略（如强制缓存、网络优先等）
- CacheValidator：缓存验证器，验证缓存有效性
- CacheCleaner：缓存清理器，提供缓存清理功能

### 4. 错误处理
- ErrorHandler：错误处理器，统一处理网络错误
- RetryStrategy：重试策略，定义请求失败后的重试逻辑
- TimeoutHandler：超时处理，处理请求超时情况
- NetworkChecker：网络状态检查，检测设备网络状态

### 5. 安全通信
- SSLManager：SSL 证书管理，配置和管理SSL证书
- CertificateValidator：证书验证器，验证服务器证书
- EncryptionManager：加密管理器，提供数据加密解密功能
- SecurityConfig：安全配置，配置网络安全参数

## 使用指南
### 1. 初始化网络模块
```kotlin
// 在Application中初始化
NetworkManager.init(applicationContext)

// 或者使用自定义配置初始化
val config = NetworkConfig(
    baseUrl = "https://api.example.com/",
    timeout = 60L,
    retryCount = 3,
    showLog = BuildConfig.DEBUG,
    headers = mapOf("App-Version" to BuildConfig.VERSION_NAME),
    cacheSize = 20 * 1024 * 1024,
    enableCache = true,
    trustSSL = BuildConfig.DEBUG
)
NetworkManager.init(applicationContext, config)
```

### 2. 创建API接口
```kotlin
// 定义API接口
interface UserApi {
    @GET("user/info")
    suspend fun getUserInfo(@Query("userId") userId: String): ApiResponse<UserInfo>
}

// 使用ApiFactory创建API实例
val userApi = ApiFactory.create<UserApi>()
```

### 3. 发送网络请求
```kotlin
// 发送普通请求
coroutineScope.launch {
    try {
        val response = userApi.getUserInfo("123456")
        if (response.isSuccess) {
            // 处理成功响应
            val userInfo = response.data
        } else {
            // 处理失败响应
            val errorMsg = response.message
        }
    } catch (e: Exception) {
        // 处理异常
        val networkException = NetworkManager.mapException(e)
        // 处理特定类型的异常
        when (networkException) {
            is NetworkException.UnauthorizedError -> {
                // 处理未授权错误
            }
            is NetworkException.TimeoutError -> {
                // 处理超时错误
            }
            else -> {
                // 处理其他错误
            }
        }
    }
}

// 发送可取消的请求
val requestId = "user_info_request"
NetworkManager.executeRequest(requestId) {
    val response = userApi.getUserInfo("123456")
    // 处理响应
}

// 取消请求
NetworkManager.cancelRequest(requestId)

// 取消所有请求
NetworkManager.cancelAllRequests()
```

### 4. 配置拦截器
```kotlin
// 添加自定义拦截器
NetworkManager.addInterceptor(MyCustomInterceptor())

// 移除自定义拦截器
NetworkManager.removeInterceptor(myInterceptor)
```

### 5. 配置缓存策略
```kotlin
// 设置缓存策略
CacheManager.setCacheStrategy(
    CacheStrategy.NETWORK_FIRST
)
```

## 最佳实践
1. 所有网络请求统一使用lib_network模块，避免直接使用Retrofit或OkHttp
2. 遵循单一职责原则，业务逻辑和网络请求分离
3. 使用协程处理异步网络请求，避免回调地狱
4. 合理配置缓存策略，减少不必要的网络请求
5. 实现适当的错误处理和重试机制，提高应用稳定性
6. 注意保护用户隐私，敏感数据传输前需加密
7. 对长时间运行的请求使用可取消机制，避免内存泄漏
8. 在App生命周期结束时取消所有未完成的网络请求
9. 根据不同网络状况调整请求策略（如弱网络下减少请求频率）

## 版本信息
- 1.0.0：初始版本，提供基础网络请求功能
- 1.1.0：增加缓存策略和拦截器管理
- 1.2.0：完善错误处理和安全通信功能