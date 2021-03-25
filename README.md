# wildfire-uniplugin-demo

# 快速开始

参照[uniapp本地打包指南](https://nativesupport.dcloud.net.cn/AppDocs/usesdk/android)

1. 生成本地打包资源，并放入`app/src/main/assets/apps/`中，目录应该形如`app/src/main/assets/apps/__UNI__9B0F661/www/...`
2. 修改`app/src/main/assets/data/dcloud_control.xml`中的 appid 为 uniAppID
3. 在`app/src/main/AndroidManifest.xml`的`<application>`标签中添加`<meta-data android:name="IM_SERVER_HOST" android:value="服务器地址"></meta-data>`
4. 运行！
