# mysql-rcp 客户端说明

## 1. 项目概览

`mysql-rcp` 是一个基于 Eclipse RCP、SWT/JFace 和 Maven/Tycho 的 MySQL 连接测试客户端。客户端只执行只读的 `SELECT VERSION()` 查询，不创建数据库、不修改表，也不会保存密码。

默认连接参数：

- Host：`127.0.0.1`
- Port：`3306`
- Database：可为空
- User：`root`
- Password：`password`

## 2. 代码结构

```text
mysql-rcp/
├─ pom.xml                         Maven/Tycho 根工程
├─ mysql-rcp.target                Eclipse target platform
├─ com.example.mysql.rcp/          RCP 主插件
│  ├─ META-INF/MANIFEST.MF         OSGi bundle 声明和依赖
│  ├─ plugin.xml                   Application、Product、Perspective、View 扩展
│  ├─ build.properties             PDE 构建资源配置
│  └─ src/com/example/mysql/rcp/
│     ├─ Activator.java             插件生命周期
│     ├─ Application.java           IApplication 入口
│     ├─ ApplicationWorkbenchAdvisor.java
│     ├─ ApplicationWorkbenchWindowAdvisor.java
│     ├─ db/
│     │  ├─ ConnectionSettings.java 连接参数和 JDBC URL
│     │  ├─ ConnectionTestResult.java 查询结果模型
│     │  └─ MySqlConnectionService.java MySQL 连接及版本查询
│     └─ ui/
│        ├─ Perspective.java        初始透视图布局
│        └─ MySqlConnectionView.java SWT/JFace 连接界面
├─ com.example.mysql.rcp.feature/   Feature 聚合工程
│  ├─ feature.xml                   包含主插件和 MySQL JDBC bundle
│  └─ pom.xml
└─ com.example.mysql.rcp.product/   Product/Repository 工程
   ├─ mysql-rcp.product             产品定义和启动参数
   ├─ category.xml                  p2 分类信息
   └─ pom.xml                       eclipse-repository 构建配置
```

## 3. 运行流程

1. `mysql-rcp.product` 指定产品 ID `com.example.mysql.rcp.product`。
2. 产品通过 `application="com.example.mysql.rcp.application"` 指向主插件中的 Application。
3. `Application.start()` 创建 SWT `Display`，并调用 `PlatformUI.createAndRunWorkbench()`。
4. `ApplicationWorkbenchAdvisor` 指定初始 Perspective。
5. `Perspective` 打开 `MySqlConnectionView`。
6. 用户点击连接按钮后，视图在后台线程调用 `MySqlConnectionService`。
7. 服务使用 MySQL Connector/J 建立连接并执行 `SELECT VERSION()`，结果异步回到 UI 线程显示。

空 Database 时使用服务器级 URL：

```text
jdbc:mysql://127.0.0.1:3306/?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
```

## 4. Maven/Tycho 构建关系

根 `pom.xml` 是 Maven reactor，模块顺序为：

```text
com.example.mysql.rcp
        ↓
com.example.mysql.rcp.feature
        ↓
com.example.mysql.rcp.product
```

Tycho 将普通 Maven 构建扩展为 Eclipse/OSGi 构建：

- `tycho-maven-plugin`：识别 Eclipse 插件、Feature 和 Repository 工程。
- `target-platform-configuration`：读取 `mysql-rcp.target`，使用本机 Eclipse 安装作为基础平台。
- `pomDependencies=consider`：将 Maven 依赖的 MySQL Connector/J 转换为 OSGi bundle `com.mysql.cj`。
- `eclipse-plugin`：编译并打包 `com.example.mysql.rcp` 插件。
- `eclipse-feature`：生成包含主插件和 JDBC bundle 的 Feature。
- `eclipse-repository`：解析产品、安装 Feature 并物化 Windows 产品。

当前构建环境：

- Eclipse Platform：`4.40.0` / Eclipse 2026-06
- Java：`JavaSE-21` 编译目标
- Tycho：`5.0.3`
- MySQL Connector/J：`9.7.0`
- Target：`win32/win32/x86_64`

## 5. Eclipse target platform

`mysql-rcp.target` 包含两个位置：

1. `C:\1\eclipse-rcp-2026-06-R` 本地 Eclipse Installation Profile。
2. Eclipse 2026-06 p2 仓库中的 `org.eclipse.equinox.executable.feature.group`，用于提供 Windows launcher。

因此构建机需要能够访问 Eclipse p2 仓库，或者提前准备对应的 p2 缓存。

## 6. Product 打包过程

产品打包由 `com.example.mysql.rcp.product/mysql-rcp.product` 定义：

- `useFeatures="true"`：使用 Feature-based 产品。
- 包含 `org.eclipse.rcp` 和 `com.example.mysql.rcp.feature`。
- `includeLaunchers="true"`：生成 Windows launcher。
- launcher 名称为 `mysql-rcp`。
- 显式启动 Eclipse 事件服务和 Declarative Services，保证 Eclipse 4 工作台的 `IEventBroker` 可用。

执行以下命令：

```bash
cd /c/1/eclipse-rcp-2026-06-R/workspace/mysql-rcp
/c/Users/enjing.guo/.m2/wrapper/dists/apache-maven-3.9.12-bin/*/apache-maven-3.9.12/bin/mvn.cmd -DskipTests verify
```

构建阶段如下：

1. 解析 `mysql-rcp.target`。
2. 解析 Eclipse Platform、SWT、JFace、Workbench 和 MySQL JDBC bundle。
3. 编译并打包主插件。
4. 生成 Feature p2 metadata。
5. 解析 product 配置。
6. 使用 p2 director 安装产品 Feature。
7. 生成 Windows x86_64 产品目录和 zip 压缩包。

## 7. 构建产物

可运行产品目录：

```text
com.example.mysql.rcp.product/target/products/
└─ com.example.mysql.rcp.product/win32/win32/x86_64/
   ├─ mysql-rcp.exe
   ├─ plugins/
   └─ configuration/
```

可分发压缩包：

```text
com.example.mysql.rcp.product/target/products/
└─ com.example.mysql.rcp.product-win32.win32.x86_64.zip
```

启动诊断时可以使用：

```bash
cd /c/1/eclipse-rcp-2026-06-R/workspace/mysql-rcp/com.example.mysql.rcp.product/target/products/com.example.mysql.rcp.product/win32/win32/x86_64
mysql-rcp.exe -consoleLog -clean
```

其中 `-consoleLog` 输出 OSGi 和 Workbench 日志，`-clean` 清理 OSGi 缓存，适合产品配置发生变化后首次启动。

## 8. 在 Eclipse 中导入

使用 Eclipse 2026-06-R：

1. 选择 `File > Import`。
2. 选择 `Maven > Existing Maven Projects`。
3. 选择目录 `C:\1\eclipse-rcp-2026-06-R\workspace\mysql-rcp`。
4. 导入根工程及三个 Maven 模块。
5. 如需 PDE 编辑，可使用 `Import > General > Existing Projects into Workspace` 导入插件、Feature 和 Product 工程。

## 9. 以源码方式调试

### 9.1 导入 Maven 工程

使用 `C:\1\eclipse-rcp-2026-06-R` 启动 Eclipse，然后：

1. 选择 `File > Import...`。
2. 选择 `Maven > Existing Maven Projects`。
3. 在 `Root Directory` 中选择：

   ```text
   C:\1\eclipse-rcp-2026-06-R\workspace\mysql-rcp
   ```

4. 勾选根 `pom.xml` 以及三个模块：
   `com.example.mysql.rcp`、`com.example.mysql.rcp.feature`、`com.example.mysql.rcp.product`。
5. 点击 `Finish`，等待 Maven/Tycho 完成依赖解析。

如果 Eclipse 没有自动识别 PDE 工程，可以再次选择 `File > Import... > General > Existing Projects into Workspace`，导入同一目录下的三个工程。

### 9.2 设置 Target Platform

1. 打开 `Window > Preferences`。
2. 进入 `Plug-in Development > Target Platform`。
3. 点击 `Add...`，选择 `Nothing: Start with an empty target definition`。
4. 点击 `Add... > Target Definition`，选择工程根目录中的：

   ```text
   C:\1\eclipse-rcp-2026-06-R\workspace\mysql-rcp\mysql-rcp.target
   ```

5. 选中该 Target，点击 `Set as Active Target Platform`，再点击 `Apply and Close`。

Target 定义使用本机 Eclipse Installation Profile，并为 `win32/win32/x86_64` 和 `JavaSE-21` 配置运行环境。设置完成后，执行 `Project > Clean...`，让 PDE 重新计算插件依赖。

### 9.3 创建 Eclipse Application 调试配置

1. 右键主插件工程 `com.example.mysql.rcp`。
2. 选择 `Run As > Eclipse Application`，先运行一次。
3. Eclipse 会自动创建一个名为 `com.example.mysql.rcp` 或类似名称的启动配置。
4. 打开 `Run > Debug Configurations...`，选择 `Eclipse Application` 下的该配置。
5. 在 `Main` 页确认：

   - `Run an application` 选择 `com.example.mysql.rcp.application`。
   - `Workspace Data` 使用单独的调试工作区，例如：

     ```text
     C:\1\eclipse-rcp-2026-06-R\workspace\mysql-rcp-runtime
     ```

   - 不要把开发工作区和运行时工作区设置成同一个目录。

6. 在 `Plug-ins` 页选择 `Launch with: plug-ins selected below only` 或保留自动计算，然后点击 `Validate Plug-ins`。
7. 确认 `com.example.mysql.rcp`、`com.mysql.cj`、`org.eclipse.rcp` 及其依赖均已选中。
8. 在 `Arguments` 页可添加：

   ```text
   -consoleLog -clean
   ```

9. 点击 `Apply`，再点击 `Debug`。

### 9.4 设置断点

常用断点位置：

- 应用入口：`com.example.mysql.rcp.Application.start()`。
- 连接按钮：`com.example.mysql.rcp.ui.MySqlConnectionView` 中的连接处理方法。
- JDBC URL：`com.example.mysql.rcp.db.ConnectionSettings.toJdbcUrl()`。
- 数据库连接：`com.example.mysql.rcp.db.MySqlConnectionService.testConnection()`。
- SQL 查询：`MySqlConnectionService` 中执行 `SELECT VERSION()` 的位置。

点击视图中的 `Connect / Test` 后，连接任务在后台线程执行；UI 更新通过 SWT Display 回到 UI 线程。因此调试数据库连接时，应同时关注后台线程和 UI 异步回调。

### 9.5 直接调试 Product 配置

如果希望调试与最终产品一致的配置，可以：

1. 打开 `com.example.mysql.rcp.product/mysql-rcp.product`。
2. 切换到 `Overview` 页。
3. 点击 `Launch an Eclipse application`。
4. 或点击右上角 `Debug` 图标，以 Product 定义启动。

这种方式会使用产品中声明的 Feature、Application、启动参数和插件启动级别，更适合验证打包后的运行环境。源码断点仍然可以命中，因为 PDE 启动使用的是当前工作区中的插件源码。

### 9.6 常见问题

- `BundleException` 或插件未解析：确认 `mysql-rcp.target` 已设置为 Active Target Platform。
- 找不到 `com.mysql.cj`：先执行 Maven/Tycho 构建，让本地 Maven 依赖进入 target platform，然后在 Debug Configuration 中重新执行 `Validate Plug-ins`。
- 修改产品配置后仍加载旧缓存：在启动参数中加入 `-clean`，或删除独立运行时工作区中的 `.metadata/.plugins/org.eclipse.osgi` 缓存。
- 启动后没有窗口：查看 `Console` 和运行时工作区下的 `.metadata/.log`，优先检查 Application ID、`org.eclipse.equinox.event` 和 `org.apache.felix.scr` 是否已启动。
