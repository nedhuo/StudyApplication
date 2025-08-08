# lib_config

## 模块角色
应用配置管理模块，提供统一的配置管理、环境切换和动态配置能力，是应用配置的统一入口。

## 模块边界
### 职责范围
✅ 允许：
- 本地配置管理
- 远程配置获取
- 环境切换（开发/测试/生产）
- Feature Flag 管理
- 配置加密存储

❌ 禁止：
- 不包含业务配置
- 不直接进行网络请求
- 不处理业务相关的配置逻辑

### 依赖规则
- 仅依赖 lib_base
- 可被其他模块依赖，用于获取配置
- 网络请求通过接口依赖注入

## 主要功能

### 1. 配置管理
- ConfigManager：统一配置管理器
- ConfigLoader：配置加载器
- ConfigParser：配置解析器
- ConfigValidator：配置验证器

### 2. 环境管理
- EnvironmentManager：环境管理器
- EnvironmentSwitcher：环境切换器
- EnvironmentConfig：环境配置定义
- EnvironmentValidator：环境参数验证

### 3. 动态配置
- RemoteConfig：远程配置管理
- ConfigUpdater：配置更新器
- ConfigListener：配置变更监听
- ConfigCache：配置缓存管理

### 4. Feature Flag
- FeatureManager：功能开关管理
- FeatureToggle：功能切换器
- FeatureConfig：功能配置定义
- FeatureValidator：功能参数验证

### 5. 安全管理
- ConfigEncryption：配置加密工具
- SecureStorage：安全存储工具
- KeyManager：密钥管理器
- SecurityValidator：安全校验器

## 使用指南
1. 初始化 ConfigManager 加载配置
2. 使用环境管理器切换环境
3. 配置动态更新监听
4. 使用 Feature Flag 控制功能
5. 遵循配置管理规范和最佳实践 