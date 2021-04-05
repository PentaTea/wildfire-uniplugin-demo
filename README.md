# wildfire-uniplugin-demo

# 快速开始

1. 跟随[野火文档](https://docs.wildfirechat.net/)在本地或在服务器上构建并配置好服务端
2. `app/src/main/AndroidManifest.xml`->`<application>`标签内 ->`<meta-data android:name="IM_SERVER_HOST" android:value="服务器地址"></meta-data>`替换为自己的服务器地址，
3. 运行！

# 更改示例项目并测试

用 hbuilderx 打开 `uniapp示例工程/PentaTea-Wildfire-Demo`

- 更改示例后在 hbuilderx -> 发行 -> 本地打包 -> 生成本地打包资源，并将其放入`app/src/main/assets/apps`中，然后在 android studio 中运行
- 或者修改原生代码后[打自定义基座](https://ask.dcloud.net.cn/article/35482)并在 hbuilderx 中运行

具体选择哪种取决于当前工作重心，封装原生新功能使用本地打包，只需调试示例打自定义基座

# 贡献指南

这个插件本来是我准备自用的，大家需要什么功能可以封装进来，我测试没问题就打包成[插件](https://ext.dcloud.net.cn/plugin?id=4547)，方便大家使用。（因为本地插件不支持 hbuilderx 的安心打包，还是云端方便一些）

- 请提交[emoji风格的commit](https://github.com/liuchengxu/git-commit-emoji-cn)
- 请保持良好的代码习惯与清晰的 commit 注释
- 请勿将个人信息，如 manifest 中的服务端地址，新申请的 appid, 临时文件等提交上来，可使用`git update-index --skip-worktree`命令忽略这些文件的修改如

  ``` code
  git update-index --skip-worktree app/src/main/AndroidManifest.xml
  git update-index --skip-worktree uniapp示例工程/PentaTea-Wildfire-Demo/manifest.json
  ```

- 请保证每一次 pr 是经过测试可用的
