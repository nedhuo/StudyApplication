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
