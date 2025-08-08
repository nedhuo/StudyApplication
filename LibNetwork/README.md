# lib_network

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

## 主要功能

### 1. 网络请求
- RetrofitManager：Retrofit 管理器
- ApiFactory：API 接口工厂
- RequestBuilder：请求构建器
- ResponseHandler：响应处理器

### 2. 拦截器管理
- InterceptorManager：拦截器管理器
- HeaderInterceptor：请求头拦截器
- LogInterceptor：日志拦截器
- CacheInterceptor：缓存拦截器
- TokenInterceptor：令牌拦截器

### 3. 缓存策略
- CacheManager：缓存管理器
- CacheStrategy：缓存策略定义
- CacheValidator：缓存验证器
- CacheCleaner：缓存清理器

### 4. 错误处理
- ErrorHandler：错误处理器
- RetryStrategy：重试策略
- TimeoutHandler：超时处理
- NetworkChecker：网络状态检查

### 5. 安全通信
- SSLManager：SSL 证书管理
- CertificateValidator：证书验证器
- EncryptionManager：加密管理器
- SecurityConfig：安全配置

## 使用指南
1. 初始化 RetrofitManager 配置网络参数
2. 使用 ApiFactory 创建 API 接口
3. 配置必要的拦截器
4. 设置合适的缓存策略
5. 遵循网络请求规范和最佳实践 