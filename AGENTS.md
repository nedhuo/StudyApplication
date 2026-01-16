# AGENTS.md - Development Guide for AI Agents

This document provides comprehensive guidelines for AI coding agents working on this Android project.

## Build Commands

### Gradle Commands
```bash
# Build project
./gradlew assembleDebug          # Debug build
./gradlew assembleRelease        # Release build

# Clean build
./gradlew clean                  # Clean all build artifacts
./gradlew clean assembleDebug    # Clean and rebuild

# Run tests
./gradlew test                   # Run all unit tests
./gradlew connectedAndroidTest   # Run instrumented tests

# Single test execution
./gradlew test --tests "com.example.module.ClassName.testMethod"
./gradlew :LibBase:test --tests "*RepositoryTest"  # Test specific module

# Lint
./gradlew lint                   # Run lint checks
./gradlew lintDebug              # Lint debug build
./gradlew :LibBase:lint          # Lint specific module

# Dependency updates
./gradlew dependencies          # View dependency tree
./gradlew :LibBase:dependencies # View module dependencies
```

### Module-Specific Commands
```bash
./gradlew :LibBase:assembleDebug     # Build LibBase module only
./gradlew :LibBase:test              # Test LibBase module only
./gradlew :FeatureMain:assembleDebug # Build FeatureMain module only
```

## Project Structure

### Module Types
- **lib_***: Foundation modules (lib_base, lib_network, lib_log, etc.)
- **feature_***: Feature modules (feature_login, feature_main, etc.)
- **app**: Main application module

### Module Dependencies (Topological Order)
```
lib_log → lib_base → lib_common → feature_*
lib_base → lib_network, lib_utils, lib_database
lib_common → lib_base
feature_* → lib_common, lib_base
```

### Key Modules
- **LibBase**: Base components (BaseActivity, BaseFragment, BaseViewModel)
- **LibNetwork**: Network layer (Retrofit, OkHttp)
- **LibDatabase**: Room database
- **LibLog**: Logging (Timber)
- **LibUtils**: Utility functions
- **LibCommon**: Shared business logic

## Code Style Guidelines

### Imports
- Use explicit imports (no wildcard imports)
- Organize imports: AndroidX → Kotlin → Project → Third-party
- Group by package level with blank lines between groups
- Alphabetical within groups

### Formatting
- Use Android Studio's default Kotlin formatter
- 4 spaces for indentation (no tabs)
- Maximum line width: 120 characters
- Blank lines: One blank line between functions, two between class declarations
- No trailing whitespace
- No consecutive blank lines

### Types & Declarations
- Always declare explicit types for variables and function parameters/returns
- Avoid using `Any`; create specific data classes
- Use `val` for immutable data, `var` only when mutation is required
- Use `data class` for data models
- Avoid nullable types when possible; handle nullability explicitly

### Naming Conventions
- **Classes**: PascalCase (BaseActivity, UserRepository)
- **Functions/Variables**: camelCase (fetchData, isLoading)
- **Files/Directories**: snake_case (base_activity, user_repository)
- **Constants**: UPPER_SNAKE_CASE (DEFAULT_TIMEOUT, MAX_RETRY_COUNT)
- **Booleans**: isX, hasX, canX (isLoading, hasError, canDelete)
- **Avoid abbreviations** except standard ones (API, URL, JSON, HTTP)
- **Packages**: Lowercase, meaningful names (com.example.lib_base.base)

### Function Guidelines
- Maximum 20 lines per function
- Single responsibility principle
- Function name: verb + noun pattern (fetchUserData, saveConfig)
- Boolean returns: isX/hasX/canX pattern
- Use default parameters to avoid null checks
- Return early to reduce nesting
- Extract reusable logic into helper functions

### Class Guidelines
- Maximum 200 lines per class
- Maximum 10 public methods per class
- Maximum 10 properties per class
- Follow SOLID principles
- Prefer composition over inheritance
- Define contracts with interfaces

### Error Handling
- Use exceptions for unexpected errors
- Wrap exceptions with context: `throw CustomException("Failed to X because Y", cause)`
- Never use empty catch blocks
- Use Result/NetworkResponse wrappers for expected failures
- Log errors with Timber before throwing
- Use BaseViewModel.launchWithLoading() for coroutine error handling

### Coroutines
- Use `viewModelScope` or `lifecycleScope` for UI-related coroutines
- Use `Dispatchers.IO` for blocking operations
- Avoid `GlobalScope`; use structured concurrency
- Handle cancellation properly
- Use `launchWithLoading()` from BaseViewModel for loading state management

### Android Components
- **Activities/Fragments**: Extend BaseActivity/BaseVMActivity or BaseFragment/BaseVMFragment
- **ViewModels**: Extend BaseViewModel
- **ViewBinding**: Use `by bindings<ViewBindingClass>()` extension
- **Navigation**: Use ARouter for module navigation
- **UI**: XML layouts with ViewBinding (no Jetpack Compose)

### State Management
- Use ViewState sealed class: Loading, Success<T>, Error, Exception, Empty
- Expose StateFlow or LiveData from ViewModel
- Observe in Activity/Fragment with lifecycle awareness

### Testing
- Follow Arrange-Act-Assert pattern
- Test naming: shouldX_whenY (shouldReturnUser_whenIdProvided)
- Use descriptive variable names: inputX, mockX, actualX, expectedX
- Mock dependencies using Mockito
- Write unit tests for all public functions
- Write integration tests for each feature module

### Architecture
- Clean Architecture: Data → Domain → Presentation layers
- Repository pattern for data abstraction
- MVI pattern in ViewModels: State → Intent → Side Effect
- Separate concerns: UI, ViewModel, Repository, Data Source

### Gradle Conventions
- Use Kotlin DSL (`.kts` files)
- All versions in `buildSrc/src/main/kotlin/Dependencies.kt`
- All project configs in `buildSrc/src/main/kotlin/ProjectConfig.kt`
- Module dependencies: `implementation(project(ProjectModules.xxx))`
- External dependencies: `implementation(Deps.Xxx.xxx)`

### Prohibited Patterns
- No `as Any`, `@ts-ignore`, or type suppression
- No empty catch blocks
- No magic numbers (define constants)
- No hardcoded strings (use resources)
- No direct imports between feature modules (use lib_common)
- No circular dependencies
- No synchronous network/database operations on main thread

## Lib Module Guidelines

### Module Responsibility Matrix

| Module | Responsibility | Allowed Content | NOT Allowed |
|--------|---------------|-----------------|-------------|
| **lib_base** | Android 基础组件 | BaseActivity, BaseFragment, BaseViewModel, ViewBinding extensions, LoadingDialog, ViewState, ActivityManager | Business logic, network calls, database operations |
| **lib_network** | 网络层 | Retrofit, OkHttp, interceptors, NetworkResponse, error handling | UI code, database operations |
| **lib_log** | 日志系统 | Timber config, FileLogger, CrashReporter, log utilities | Business logic, UI code |
| **lib_config** | 配置管理 | Environment switching, feature flags, dynamic config | Business logic, UI code |
| **lib_database** | 数据持久化 | Room, DAOs, entities, migrations, DatabaseManager | UI code, network calls |
| **lib_webview** | WebView 封装 | BaseWebView, JS bridge, cookie management, WebViewActivity | Business logic unrelated to WebView |
| **lib_image** | 图片加载 | Glide wrapper, image transformations, cache management | UI components, business logic |
| **lib_common** | 跨模块通信 | Interfaces, shared models, DI setup, startup initializers, routing | UI code, business implementation |

### Dependency Rules (lib modules only)

**Allowed Dependencies:**
```
lib_log → (no deps - pure utility)
lib_config → lib_base
lib_database → lib_base
lib_image → lib_base
lib_webview → lib_base, lib_common, lib_log

lib_base → lib_log, lib_network (⚠️ Avoid - foundation should be independent)
lib_network → lib_log, lib_config, lib_utils (⚠️ lib_utils must exist)
lib_common → lib_base, lib_network, lib_log, lib_utils (⚠️ lib_utils must exist)
```

**Recommended Dependency Order:**
```
lib_log (base)
    ↓
lib_base, lib_config (foundation)
    ↓
lib_network, lib_database, lib_image, lib_webview (infrastructure)
    ↓
lib_common (integration layer)
```

### Module Cleanup Rules

1. **Delete Empty Modules**
   - Modules with 0 implementation files should be deleted
   - `lib_utils` (0 files) → DELETE
   - `lib_push` (only tests) → DELETE or implement

2. **Avoid Layering Violations**
   - Foundation modules (`lib_base`) should NOT depend on infrastructure modules (`lib_network`)
   - Network layer should be independent of business logic

3. **No Business Logic in lib_**
   - lib_* modules should only contain infrastructure code
   - Any business logic belongs in `lib_common` (interfaces) or `feature_*` (implementation)

### Package Structure per Module

Each lib module should follow this structure:
```
lib_xxx/
├── base/           # Base classes
├── ext/            # Extension functions
├── ui/             # UI components (only if needed)
├── utils/          # Utility classes
├── manager/        # Managers (if needed)
├── data/           # Data layer (database/network - use specific libs instead)
└── README.md       # Module documentation
```

### Adding New lib_ Modules

When creating a new lib module, ensure:

1. **Single Responsibility**: Module has one clear purpose
2. **No Business Logic**: Only infrastructure code
3. **Minimal Dependencies**: Only depend on lower-layer libs
4. **Clear API**: Expose interfaces, hide implementation
5. **Documentation**: Each public class/function has KDoc
