# Android 模块化项目架构示例

这是一个采用模块化架构设计的 Android 项目示例，展示了如何构建一个可扩展、易维护的应用程序。

## 项目架构

### 基础层模块
- **lib_base**: 基础架构模块，提供基础组件和通用能力
- **lib_log**: 日志管理模块，提供统一的日志记录能力
- **lib_config**: 配置管理模块，处理应用配置和环境切换
- **lib_network**: 网络请求模块，处理网络通信
- **lib_database**: 数据库模块，处理数据持久化
- **lib_utils**: 工具类模块，提供通用工具方法

### 公共层模块
- **lib_common**: 跨模块通信与共享模块，处理模块间交互

### 业务层模块
- **feature_xxx**: 具体业务功能模块

## 模块依赖关系
```
业务模块 -> lib_common -> lib_base
业务模块 -> lib_base
```

## 技术栈
- Kotlin
- MVVM + Clean Architecture
- Jetpack Components
- Coroutines + Flow
- Hilt 依赖注入
- Room 数据库
- Retrofit + OkHttp

## 开发环境
- Android Studio Hedgehog | 2023.1.1
- Kotlin 1.9.0
- Gradle 8.0
- compileSdk 34
- minSdk 24
- targetSdk 34

## 项目特点
1. 采用模块化架构，解耦业务逻辑
2. 统一的基础设施层
3. 规范的模块间通信机制
4. 完善的工具类支持
5. 统一的编码规范

## 如何使用
1. Clone 项目
2. 使用 Android Studio 打开
3. 同步 Gradle 文件
4. 运行项目

## 贡献指南
1. Fork 项目
2. 创建特性分支
3. 提交代码
4. 发起 Pull Request

## 许可证
```
MIT License

Copyright (c) 2024 Your Name

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
``` 