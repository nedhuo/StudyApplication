# LibBase

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

#### Activity 基类
- **BaseActivity**：活动基类，统一生命周期管理、状态管理、加载弹窗处理
  - 提供 `initView()`、`initData()`、`initListener()`、`initObservers()` 标准化初始化流程
  - 实现 `IStateView` 接口，提供 `showLoading()` 和 `dismissLoading()` 方法
  - 自动管理 Activity 栈

- **BaseVMActivity**：结合 ViewModel 的 Activity 基类
  - 自动创建和管理 ViewModel 实例
  - 提供通用的加载状态监听
  - 支持自定义 ViewModel 创建逻辑

```kotlin
// 使用示例
class MyActivity : BaseVMActivity<MyViewModel>() {
    private val binding by bindings<ActivityMyBinding>()
    
    override fun initView() {
        // 初始化视图
    }
    
    override fun initData() {
        // 初始化数据
        viewModel.fetchData()
    }
}
```

#### Fragment 基类
- **BaseFragment**：片段基类，统一视图绑定、生命周期管理
  - 提供标准化的初始化流程和生命周期方法
  - 实现 `IStateView` 接口
  - 自动管理 Fragment 生命周期

- **BaseVMFragment**：结合 ViewModel 的 Fragment 基类
  - 自动创建和管理 ViewModel 实例
  - 支持自定义 ViewModel 创建逻辑

```kotlin
// 使用示例
class MyFragment : BaseVMFragment<MyViewModel>() {
    private val binding by bindings<FragmentMyBinding>()
    
    override fun initView() {
        // 初始化视图
    }
    
    override fun initData() {
        // 初始化数据
        viewModel.fetchData()
    }
}
```

#### Dialog 基类
- **BaseDialog**：对话框基类，统一对话框样式和行为
- **BaseDialogFragment**：对话框片段基类，提供弹窗标准实现
- **BaseVMDialogFragment**：结合 ViewModel 的对话框片段基类

#### ViewModel 基类
- **BaseViewModel**：视图模型基类，统一数据管理和 UI 状态
  - 提供 `launchWithLoading()` 方法，在 ViewModel 范围内安全启动协程
  - 内置加载状态管理（`loadingFlow`）
  - 自动处理异常捕获

```kotlin
// 使用示例
class MyViewModel : BaseViewModel() {
    fun fetchData() {
        launchWithLoading {
            // 执行异步操作
            val result = repository.getData()
            // 处理结果
        }
    }
}
```

### 2. UI 状态管理

- **IStateView**：状态视图接口，定义加载状态切换方法
- **ViewState**：统一 UI 状态封装（加载、成功、失败、异常、空状态）
  - `ViewState.Loading`：加载中状态
  - `ViewState.Success<T>`：成功状态，包含数据
  - `ViewState.Error`：错误状态，包含错误码和错误信息
  - `ViewState.Exception`：异常状态，包含异常对象
  - `ViewState.Empty`：空数据状态

- **LoadingDialog**：统一加载弹窗样式和行为
  - 支持自定义加载内容和自动关闭
  - 自动绑定生命周期，防止内存泄漏
  - 提供 `LoadingConfig` 配置类定制加载行为

- **LoadingManager**：全局加载管理器
  - 在非页面组件中显示加载弹窗
  - 自动绑定顶层 Activity

```kotlin
// 在 Activity/Fragment 中使用 IStateView
showLoading("加载中...")
// 执行操作后隐藏加载
// dismissLoading()

// 在非页面组件中使用全局加载管理器
LoadingManager.showLoading("正在处理...")
// 处理完成后隐藏
// LoadingManager.dismissLoading()

// 观察 ViewState 状态
viewModel.dataState.observe(this) {
    when (it) {
        is ViewState.Loading -> showLoading()
        is ViewState.Success -> {
            dismissLoading()
            // 处理成功数据
        }
        is ViewState.Error -> {
            dismissLoading()
            // 处理错误
        }
        is ViewState.Exception -> {
            dismissLoading()
            // 处理异常
        }
        is ViewState.Empty -> {
            dismissLoading()
            // 处理空数据
        }
    }
}
```

### 3. 生命周期管理

- **ActivityManager**：Activity 栈管理，支持应用内导航
  - 管理 Activity 的添加、移除和获取
  - 支持获取顶层 Activity
  - 提供退出应用方法

- **AppLifecycle**：应用生命周期回调管理（预留扩展）

- **LifecycleExt**：生命周期相关扩展方法（预留扩展）

### 4. 通用扩展

#### ViewBinding 扩展
- `bindings()`：ViewBinding 委托属性，简化视图绑定
  - 支持 Activity、Fragment、Dialog 的视图绑定
  - 自动管理绑定对象的生命周期，防止内存泄漏

```kotlin
// Activity 中使用
class MyActivity : BaseActivity() {
    private val binding by bindings<ActivityMyBinding>()
    
    override fun initView() {
        // 直接使用 binding 访问视图
        binding.textView.text = "Hello World"
    }
}

// Fragment 中使用
class MyFragment : BaseFragment() {
    private val binding by bindings<FragmentMyBinding>()
    
    override fun initView() {
        binding.button.setOnClickListener { /* 点击事件 */ }
    }
}
```

#### ViewModel 扩展
- **GenericViewModelFactory**：通用 ViewModel 工厂类
  - 通过 lambda 表达式创建 ViewModel
  - 简化 ViewModel 创建逻辑

```kotlin
// 自定义 ViewModel 创建
protected override fun createViewModel(): MyViewModel {
    return MyViewModel(customParameter)
}
```

#### 协程扩展
- `launchWithLoading`：ViewModel 中的协程扩展，自动管理加载状态

#### 网络响应扩展
- `dataOrThrow`：BaseResponse 扩展，获取数据或抛出异常

### 5. 异常处理
- **GlobalExceptionHandler**：全局异常捕获处理（预留扩展）
- **CrashHandler**：应用崩溃处理和日志收集（预留扩展）

## 使用指南

### 基础组件使用

1. **Activity 开发**
   - 所有 Activity 必须继承 BaseActivity 或 BaseVMActivity
   - 实现 `initView()` 和 `initData()` 方法
   - 可选实现 `initListener()` 和 `initObservers()` 方法

2. **Fragment 开发**
   - 所有 Fragment 必须继承 BaseFragment 或 BaseVMFragment
   - 实现 `initView()` 和 `initData()` 方法
   - 可选实现 `initArguments(arguments)` 和 `initObservers()` 方法

3. **ViewModel 开发**
   - 所有 ViewModel 必须继承 BaseViewModel
   - 使用 `launchWithLoading()` 处理异步操作
   - 使用 `loadingFlow` 管理加载状态

### 视图绑定
- 使用 `by bindings<ViewBindingClass>()` 委托属性简化视图绑定
- Activity 和 Fragment 中统一使用此方式
- 自动处理绑定对象的生命周期

### 状态管理
- 使用 `ViewState` 密封类统一管理 UI 状态
- 在 ViewModel 中创建状态 LiveData/StateFlow
- 在 Activity/Fragment 中观察状态变化并更新 UI

### 扩展方法使用
- **Activity/Fragment/Dialog**：使用 `bindings()` 扩展简化 ViewBinding
- **ViewModel**：使用 `launchWithLoading()` 处理异步操作
- **NetworkResponse**：使用 `toViewState()` 转换为 UI 状态
- **BaseResponse**：使用 `dataOrThrow()` 获取数据或抛出异常

## 项目结构

LibBase 模块采用清晰的包结构组织代码：

```
com.example.lib_base/
├── base/             # 基础组件基类
│   ├── BaseActivity.kt
│   ├── BaseFragment.kt
│   ├── BaseViewModel.kt
│   ├── BaseDialog.kt
│   ├── BaseDialogFragment.kt
│   ├── BaseVMActivity.kt
│   ├── BaseVMFragment.kt
│   ├── BaseVMDialogFragment.kt
│   └── IStateView.kt
├── ext/              # 扩展函数
│   ├── BaseExtensions.kt
│   ├── ViewModelExtensions.kt
│   └── LoadingExt.kt
├── ui/               # UI 组件
│   └── LoadingDialog.kt
├── utils/            # 工具类
│   └── ActivityManager.kt
├── GenericViewModelFactory.kt
├── FragmentBindingDelegate.kt
└── LoadingManager.kt
```

## 与其他模块的关系

LibBase 模块作为项目的基础模块，遵循以下依赖规则：

- 可以被所有其他模块（Feature 模块、Common 模块等）依赖
- 自身依赖于：
  - AndroidX 组件（core-ktx, activity-ktx, fragment-ktx, appcompat, lifecycle 等）
  - 项目内基础模块（lib_network, lib_log, lib_utils）
  - 第三方库（Glide, Material Design 等）
- 不直接依赖业务模块
- 提供的接口和抽象类被上层模块实现和扩展

## 最佳实践

1. **统一使用基类**：所有组件都应使用 LibBase 提供的基类
2. **标准化初始化流程**：遵循 `initView()` -> `initData()` -> `initListener()` -> `initObservers()` 的初始化顺序
3. **使用 ViewBinding**：通过 `bindings()` 委托属性简化视图绑定
4. **状态管理**：使用 `ViewState` 统一管理 UI 状态
5. **异常处理**：使用 `launchWithLoading()` 捕获和处理异常
6. **生命周期安全**：确保所有异步操作都在合适的生命周期范围内执行