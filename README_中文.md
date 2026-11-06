# Sherlock Android（Android Studio 构建工程）

这是一个将 Sherlock Python 项目封装到 Android 原生界面的实验性工程，使用 Chaquopy 在 Android 进程内运行 Python。

## 当前交付内容

- Android Studio 工程源码
- Kotlin 原生输入界面
- Chaquopy Python 运行时配置
- Sherlock 0.16.2 的 pip 安装配置
- 输出捕获和界面展示

**当前压缩包不是 APK。** 此执行环境没有 Android SDK、Gradle 安装和 Android 构建工具链，因此无法在这里编译、签名并验证可安装的 APK。请勿将本工程误认为已编译安装包。

## 构建要求

- Android Studio（建议最新版稳定版）
- JDK 17
- Android SDK Platform 35
- 可访问 Google Maven、Maven Central、PyPI 的网络
- Android Gradle Plugin 8.13.1 / Kotlin 2.2.20 / Chaquopy 16.1.0

## 构建步骤

1. 解压本工程。
2. 使用 Android Studio 选择 `Open`，打开 `SherlockAndroid` 文件夹。
3. 等待 Gradle Sync 和 Python 依赖下载完成。
4. 连接 Android 手机并开启 USB 调试，或启动模拟器。
5. 选择 `Build > Build Bundle(s) / APK(s) > Build APK(s)`。
6. APK 通常生成在 `app/build/outputs/apk/debug/app-debug.apk`。

如需发布版本，请在 Android Studio 中配置自己的签名密钥，再构建 Signed APK。

## 使用说明

1. 安装构建出的 APK。
2. 输入一个或多个用户名（多个用户名可用空格或换行分隔）。
3. 点击“开始查询”。
4. 查询结果显示在下方。

## 重要说明

- Sherlock 会访问第三方网站；可用性、响应速度和结果准确性取决于网络、网站策略及上游数据。
- 本工程没有绕过登录、验证码、访问控制或平台限制的功能。
- 查询结果可能包含误报或过时信息，请人工核验。
- 仅用于合法、合规的公开信息检索；请尊重他人隐私和各平台服务条款。
- 上游项目采用 MIT License。请在再分发时保留上游许可与版权声明。
- 这是初始封装工程，尚未经过真实 Android 设备构建和运行测试。Chaquopy 对依赖包及其 Android wheel 的兼容性需在 Gradle 构建时确认；如果 pandas 等依赖无法解析，需要进一步适配，不能保证首次同步即可成功。

上游项目：https://github.com/sherlock-project/sherlock
上游版本配置：sherlock-project==0.16.2

## 可选：GitHub Actions 云端构建

工程已附带 `.github/workflows/android-apk.yml`。将本工程上传到你自己的 GitHub 仓库并推送到 `main` 或 `master`，或在 Actions 页面手动运行 `Build Sherlock Android APK`。成功后，在该次 workflow 的 Artifacts 中下载 `sherlock-android-debug-apk`。

这只是自动化构建配置，不代表当前工程已经通过云端构建；Sherlock 及其依赖在 Chaquopy/Android 上的兼容性仍需以实际构建日志为准。
