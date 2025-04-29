# lib_common

## 模块角色
跨模块通信与共享模块，处理模块间的交互逻辑，提供共享数据模型，是业务模块间通信的桥梁。

## 模块边界
### 职责范围
✅ 允许：
- 定义模块间通信接口
- 提供共享数据模型
- 处理模块间事件分发
- 管理模块间状态同步
- 提供业务级工具类

❌ 禁止：
- 不包含具体业务实现逻辑
- 不直接操作 UI
- 不直接进行网络请求或数据库操作

### 依赖规则
- 依赖 lib_base
- 可被业务模块依赖
- 通过接口暴露能力，实现在业务模块中注入

## 主要功能

### 1. 模块通信
- EventBus：事件总线
- ModuleMediator：模块间调度器
- ServiceLocator：服务定位器
- RouterManager：路由管理器

### 2. 共享数据
- CommonModels：共享数据模型
- DataMapper：数据转换器
- StateManager：状态管理器
- CacheManager：缓存管理器

### 3. 业务工具
- BusinessUtils：业务相关工具类
- CommonFormatter：通用格式化工具
- ValidatorUtils：业务校验工具
- RegexConstants：正则表达式常量

### 4. 接口定义
- IUserService：用户服务接口
- IPayService：支付服务接口
- IShareService：分享服务接口
- ILoginService：登录服务接口

### 5. 常量定义
- BusinessConstants：业务常量
- ErrorConstants：错误码常量
- EventConstants：事件常量
- RouterConstants：路由常量

## 使用指南
1. 在此模块定义跨模块接口
2. 在业务模块中实现接口
3. 使用依赖注入注册实现
4. 通过接口调用其他模块功能
5. 使用事件总线处理模块间通信

## 最佳实践
1. 接口设计要考虑扩展性
2. 避免接口耦合具体实现
3. 合理使用依赖注入
4. 规范事件命名和参数
5. 及时清理无用接口和事件 