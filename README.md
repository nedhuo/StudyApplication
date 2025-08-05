# Android 模块化项目架构示例

本项目旨在仿照 TVBox，打造一个支持多站点、可扩展的视频与直播流播放工具。项目聚焦于为用户提供便捷、灵活的影视内容聚合与播放体验，核心业务包括：

1. **配置 JSON 解析**  
   支持多种格式的配置 JSON 文件解析，实现站点、播放源、界面等功能的灵活配置与动态扩展。

2. **站点文件解析与管理**  
   支持多站点文件的导入、解析与管理，便于用户自定义和切换内容源，实现内容聚合。

3. **站点数据抓取**  
   内置多种数据抓取策略，支持对各类影视站点的数据采集，包括影片列表、详情、播放链接等，适配多种站点结构。

4. **视频与直播流播放**  
   支持主流视频格式与直播流协议（如 m3u8、mp4、flv 等）的播放，提供流畅的点播与直播体验。
   支持多播放器内核切换，兼容性强。

5. **内容聚合与分类**  
   实现多站点内容的聚合展示，支持按类型、地区、年份等多维度分类浏览。

6. **自定义与扩展能力**  
   支持用户自定义站点、解析规则和界面主题，满足个性化需求。
   预留插件/脚本扩展接口，便于后续功能拓展。

7. **播放历史与收藏**  
   记录用户播放历史，支持内容收藏，提升用户体验。

8. **多端适配与优化**  
   适配手机、平板、TV 等多种 Android 终端，界面简洁易用，操作流畅。

> 本项目致力于为影视爱好者提供一个开源、可定制的聚合播放器解决方案。通过灵活的配置和强大的解析能力，用户可轻松接入各类影视站点，实现一站式的内容浏览与播放。适用于个人娱乐、家庭影院、智能电视盒子等多种场景。

## 项目架构

### 基础层模块
- **lib_base**: 基础架构模块，提供基础组件和通用能力
- **lib_log**: 日志管理模块，提供统一的日志记录能力
- **lib_config**: 配置管理模块，处理应用配置和环境切换
- **lib_network**: 网络请求模块，处理网络通信
- **lib_database**: 数据库模块，处理数据持久化
- **lib_utils**: 工具类模块，提供通用工具方法

### 公共层模块
- **lib_common**: 跨模块通信与共享模块，处理模块间交互，带有业务色彩

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