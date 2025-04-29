# lib_utils

## 模块角色
工具类集合模块，提供各种通用工具方法，简化开发过程，提高代码复用性。

## 模块边界
### 职责范围
✅ 允许：
- 设备工具（屏幕/设备信息）
- 数据处理（JSON/字符串/数字）
- 文件操作
- 加密工具
- UI 工具（动画/键盘）

❌ 禁止：
- 不包含业务相关工具
- 不依赖其他基础模块
- 不直接操作数据库或网络

### 依赖规则
- 可被除 lib_base 外的模块依赖
- 保持工具类独立，避免互相依赖
- 不依赖任何其他模块

## 主要功能

### 1. 设备与系统
- DeviceUtils：设备信息工具
- SystemUtils：系统工具类
- ScreenUtils：屏幕相关工具
- NetworkUtils：网络状态工具

### 2. 数据处理
- JsonUtils：JSON 处理工具
- StringUtils：字符串处理工具
- NumberUtils：数字处理工具
- DateTimeUtils：日期时间工具

### 3. 文件操作
- FileUtils：文件操作工具
- StorageUtils：存储工具类
- ImageUtils：图片处理工具
- MediaUtils：媒体文件工具

### 4. 加密与安全
- EncryptUtils：加密工具类
- HashUtils：哈希计算工具
- RandomUtils：随机数工具
- SecurityUtils：安全工具类

### 5. UI 工具
- ViewUtils：视图工具类
- ResourceUtils：资源工具类
- AnimationUtils：动画工具类
- KeyboardUtils：键盘工具类

## 使用指南
1. 按需引入所需工具类
2. 遵循工具类使用规范
3. 注意性能和内存使用
4. 处理好异常情况
5. 参考示例代码使用 