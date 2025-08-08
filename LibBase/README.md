# lib_base

## 模块角色
作为整个项目的基础架构模块，提供基础组件和通用能力，是其他所有模块的基础依赖。

## 模块边界
### 职责范围
✅ 允许：
- 基类封装（Activity/Fragment/Dialog/ViewModel）
- UI 状态管理（加载/成功/失败/空状态）
- 生命周期管理
- 基础扩展函数
- 全局异常处理

❌ 禁止：
- 不处理具体业务逻辑
- 不包含具体 UI 组件实现

### 依赖规则
- 可以被所有其他模块依赖
- 自身尽量减少对其他模块的依赖
- 如需其他模块能力，通过接口解耦

## 主要功能

### 1. 基础组件
- BaseActivity：活动基类，统一生命周期管理、状态管理
- BaseFragment：片段基类，统一视图绑定、生命周期
- BaseDialogFragment：对话框片段基类，统一弹窗样式和行为
- BaseViewModel：视图模型基类，统一数据管理和 UI 状态
- BaseAdapter：列表适配器基类，简化列表开发

### 2. UI 状态管理
- UiState：统一 UI 状态封装（加载、成功、失败、空）
- LoadingDialog：统一加载弹窗样式和行为
- ToastUtils：统一消息提示工具

### 3. 生命周期管理
- ActivityManager：Activity 栈管理，支持应用内导航
- AppLifecycle：应用生命周期回调管理
- LifecycleExt：生命周期相关扩展方法

### 4. 通用扩展
- ViewBinding：视图绑定扩展，简化视图操作
- Flow：协程流扩展，简化异步操作
- Context：上下文扩展，提供常用工具方法

### 5. 异常处理
- GlobalExceptionHandler：全局异常捕获处理
- CrashHandler：应用崩溃处理和日志收集

## 使用指南
1. 所有 Activity 必须继承 BaseActivity
2. 所有 Fragment 必须继承 BaseFragment
3. 所有 ViewModel 必须继承 BaseViewModel
4. 使用提供的扩展方法简化开发
5. 遵循模块定义的规范和最佳实践 