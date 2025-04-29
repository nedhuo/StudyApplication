# lib_log

## 模块角色
日志管理模块，提供统一的日志记录、收集和上报能力，是整个应用的日志基础设施。

## 模块边界
### 职责范围
✅ 允许：
- 日志记录与管理
- 崩溃信息收集
- 性能监控（ANR/内存/帧率）
- 日志上报策略
- 调试工具支持

❌ 禁止：
- 不处理业务相关日志
- 不直接操作 UI
- 不处理业务相关的性能统计

### 依赖规则
- 仅依赖 lib_base
- 可被其他模块依赖，用于写入日志
- 通过接口方式暴露日志能力

## 主要功能

### 1. 日志记录
- LogManager：统一日志管理器
- LogLevel：日志级别定义（DEBUG、INFO、WARN、ERROR）
- LogFormatter：日志格式化工具
- LogWriter：日志写入器，支持控制台和文件

### 2. 性能监控
- PerformanceMonitor：性能监控工具
- ANRDetector：ANR 检测器
- MemoryMonitor：内存监控
- FPSMonitor：帧率监控

### 3. 崩溃收集
- CrashCollector：崩溃信息收集器
- CrashParser：崩溃日志解析器
- CrashReport：崩溃报告生成器
- CrashUploader：崩溃信息上传器

### 4. 日志上报
- LogUploader：日志上传管理
- LogCompressor：日志压缩工具
- LogEncryptor：日志加密工具
- LogStrategy：日志上报策略

### 5. 调试工具
- DebugView：调试信息查看器
- LogcatHelper：Logcat 工具类
- FileLogger：文件日志工具
- ConsoleLogger：控制台日志工具

## 使用指南
1. 初始化 LogManager 配置日志级别
2. 使用提供的日志方法记录信息
3. 配置崩溃收集和上报策略
4. 根据需要开启性能监控
5. 遵循日志规范和最佳实践 