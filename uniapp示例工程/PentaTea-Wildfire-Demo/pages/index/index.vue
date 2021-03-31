<template>
  <pre v-if="err" v-html="obj2html(err)"></pre>
  <div v-else style="display: flex">
    <scroll-view scroll-y class="log" :style="{ height: height }">
      <div class="item" v-for="(pkg, i) in log" :key="log.length - i">
        <template v-if="pkg.data">
          <div class="title">
            {{ pkg.data[0] }}
            <span class="tag">{{ time(pkg.timestamp) }}</span>
          </div>
          <div>
            <span v-for="(e, j) in pkg.tags" :key="j" class="tag">{{ e }}</span>
          </div>
          <pre
            v-for="(e, j) in pkg.data"
            :key="j"
            v-html="j == 0 ? '' : obj2html(e)"
          ></pre>
        </template>
        <pre v-else v-html="obj2html(pkg)"></pre>
      </div>
    </scroll-view>
    <scroll-view scroll-y class="operation" :style="{ height: height }">
      <input
        v-model="userId"
        placeholder="userId"
        maxlength="-1"
        style="width: auto"
      />
      <textarea
        v-model="token"
        placeholder="token"
        maxlength="-1"
        style="width: auto"
      ></textarea>
      <button @click="connect(userId, token)">登录野火</button>
      <div style="height: 100px">
        <!-- 占位,防止内容跑到屏幕外面 -->
      </div>
    </scroll-view>
  </div>
</template>

<script>
import hanabi from "common/hanabi";
import stringifyObject from "common/stringify-object";

var wildfire = uni.requireNativePlugin("PentaTea-Wildfire");

export default {
  data() {
    return {
      height: uni.getSystemInfoSync().windowHeight + "px",
      wildfire: null,
      log: [],
      err: "",

      userId: "",
      token: "",
    };
  },
  mounted() {
    // //#ifndef APP-PLUS
    // this.err = "请​在​APP​端​测​试​哦";
    // //#endif
    //#ifdef APP-PLUS

    plus.globalEvent.addEventListener("wildfire", (e) => {
      this.log = [this.interpreter(e), ...this.log];
      //   this.scrollToBottom();
    });
    this.log = [wildfire.init(), ...this.log];

    //#endif
  },
  methods: {
    connect: (userId, token) => {
      wildfire.connect({ userId, token });
    },
    obj2html: (data) => {
      return hanabi(
        stringifyObject(data, {
          indent: "  ",
        })
      );
    },
    interpreter(e) {
      //做一些操作,拦截事件等等
      //建议使用ts写清楚type,使用中间件模式做拦截
      // if(e.data[0])
      return e;
    },
    time(timestamp) {
      var date = new Date(timestamp);
      let prefix = function (num, len = 2) {
        return (
          Array(Math.abs(("" + num).length - ((len || 2) + 1))).join(0) + num
        );
      };
      return (
        prefix(date.getHours()) +
        ":" +
        prefix(date.getMinutes()) +
        ":" +
        prefix(date.getSeconds())
      );
    },
  },
};
</script>

<style lang="scss">
.operation {
  padding: 20rpx;
  box-sizing: border-box;
  width: 30%;
  position: fixed;
  right: 0;
  opacity: 0.5;
}

.log {
  user-select: text;
  padding: 20rpx;
  padding-top: 250rpx;
  box-sizing: border-box;
  font-size: 10px;
  line-height: 1.5;

  .container:nth-child(even) {
    background: rgb(246, 248, 250);
  }

  //翻转180度使最新项保持在滚动底部
  transform: rotate(180deg);

  .item {
    transform: rotate(180deg);
    animation: push 1s linear both,
      insert 0.3s 0.1s cubic-bezier(0.18, 0.89, 0.32, 1) both;

    margin-bottom: 10px;

    .title {
      font-size: 14px;
      color: rgb(65, 80, 95);
      padding: 6px;
      padding-left: 12px;
    }

    .tag {
      padding: 4px 8px;
      margin: 5px;
      border-radius: 4px;
      color: #476582;

      font-size: 9px;
      background-color: rgba(27, 31, 35, 0.05);
    }
  }

  @keyframes push {
    0% {
      max-height: 0%;
    }

    100% {
      max-height: 4000px;
    }
  }

  @keyframes insert {
    0% {
      opacity: 0;

      transform: translateX(100%) rotate(180deg);
    }

    100% {
      opacity: 1;
      transform: translateX(0) rotate(180deg);
    }
  }
}

input,
textarea {
  background: #f0f0f0;
  margin-bottom: 5px;
  padding: 5px;
}

button {
  font-size: 14px;
}
</style>
