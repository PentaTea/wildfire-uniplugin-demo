# 基于野火 im 的即时通讯 SDK

> [野火im](https://docs.wildfirechat.net/)是一套开源通用的即时通讯组件，能够更加容易地赋予客户im能力，使客户可以快速的在自有产品上添加聊天功能。

本插件在野火[AndroidSDK中chatModule](https://github.com/wildfirechat/android-chat)的基础上封装了相关方法和事件侦听，目前只实现了基本的登录和所有事件侦听，本人将项目[开源到github](https://github.com/PentaTea/wildfire-uniplugin-demo)，希望和大家一起交流学习。

- 野火 im 文档：https://docs.wildfirechat.net/
- 野火安卓 SDK 源码：https://github.com/wildfirechat/android-chat
- 野火后端源码：https://github.com/wildfirechat/server
- 本插件源码：https://github.com/PentaTea/wildfire-uniplugin-demo

---

- 本项目遵循野火安卓 SDK 的 `CC3.0-BY-ND协议`，未修改 SDK 内部代码
- 本项目依然使用`CC3.0-BY-ND协议`
- 感谢大佬 [`Destiny_Xue`](https://github.com/1369521908) 对我的指导，本项目最初的框架和登录功能就是他封装的

## 使用教程

使用野火im需要先去搭建[野火后端服务](https://github.com/wildfirechat/server),请参考[野火im文档](https://docs.wildfirechat.net/)

### 引用方式

``` TS
this.wildfire = uni.requireNativePlugin('PentaTea-Wildfire')
```

### 初始化

`init`方法会为所有事件添加监听器并释放事件消息队列。所以一定要在 init 之前监听 wildfire 事件，否则无法获得 init 之前的事件

具体事件名称和参数定义请参考[ChatManager.java](https://github.com/PentaTea/wildfire-uniplugin-demo/blob/master/client/src/main/java/cn/wildfirechat/remote/ChatManager.java)

``` TS
;(plus as any).globalEvent.addEventListener(
  'wildfire',
  (e: {timestamp: number; data: [string, any] | [string, any, any]}) => {
    //处理事件
    //data数组中第一个元素是事件名称,从第二个开始是事件参数
  }
)

//获得clientId用于申请token
var info: {clientId: string; platform: number} = this.wildfire.init()
```

### 连接服务器

需要先在服务端创建新用户并使用 clientId 获取 token

```TS
this.wildfire.connect({
  userId: this.id,
  token: this.token
})
```

## 示例工程

下载后购买本插件，填写配置好野火的服务器地址即可运行
