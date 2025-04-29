# lib_database

## 模块角色
数据库管理模块，提供统一的数据库访问、数据迁移和加密存储能力，是应用数据持久化的统一入口。

## 模块边界
### 职责范围
✅ 允许：
- 数据库管理（Room）
- 数据迁移
- 数据加密
- 基础 CRUD 封装
- 缓存策略

❌ 禁止：
- 不包含具体业务表结构
- 不处理业务数据转换
- 不直接操作 UI 或网络

### 依赖规则
- 依赖 lib_base、lib_utils
- 可被其他模块依赖，用于数据存储
- 通过接口方式暴露数据访问能力

## 主要功能

### 1. 数据库管理
- DatabaseManager：数据库管理器
- RoomDatabase：Room 数据库封装
- DatabaseConfig：数据库配置
- DatabaseHelper：数据库辅助工具

### 2. 数据迁移
- MigrationManager：迁移管理器
- MigrationHelper：迁移辅助工具
- VersionManager：版本管理器
- SchemaValidator：数据库架构验证

### 3. 数据加密
- EncryptionManager：加密管理器
- KeyStore：密钥存储
- CipherHelper：加密辅助工具
- SecurityConfig：安全配置

### 4. 数据访问
- BaseDao：基础数据访问对象
- EntityManager：实体管理器
- QueryBuilder：查询构建器
- TransactionManager：事务管理器

### 5. 缓存管理
- CacheManager：缓存管理器
- MemoryCache：内存缓存
- DiskCache：磁盘缓存
- CacheStrategy：缓存策略

## 使用指南
1. 初始化 DatabaseManager 配置数据库
2. 创建数据访问对象（DAO）
3. 处理数据库迁移
4. 配置数据加密
5. 遵循数据库操作规范和最佳实践 