# 认证插件模块说明

+ 基于第三方sdk封装

# 使用方法

+ 以 uniplugin_wiiauth 为例
+ 复制整个模块文件夹(uniplugin_wiiauth)到跟app文件夹同级
+ 在 settings.gradle 添加

```groovy
include ':uniplugin_wiiauth'
```
+ 在父工程 build.gradle
```groovy
repositories {
    flatDir {
        dirs 'libs'
    }
}
```
添加 (如果模块没有依赖库,即/uniplugin_wiiauth/libs为空可以不加这行)
```groovy
dirs '../uniplugin_wiiauth/libs'
```
在
```groovy
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.aar'])
}
```
添加
```groovy
implementation project(':uniplugin_wi uniplugin_wiiauthiauth')
```
+ 操作成功模块文件夹图标会改变

# 去除依赖模块

+ 参考使用方法进行逆向操作即可
