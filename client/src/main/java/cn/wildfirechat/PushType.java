/*
 * Copyright (c) 2020 WildFireChat. All rights reserved.
 */

package cn.wildfirechat;

public interface PushType {
    int XIAOMI = 1;
    int HMS = 2;
    int MEIZU = 3;
    int VIVO = 4;
    int OPPO = 5;

    // 如果需要集成其他推送方式，请勿和上面的冲突
}
