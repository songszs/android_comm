package com.zs.test.bluetooth.fastble;

/**
 * @author: zang song
 * @version: V1.0
 * @date: 2019-08-22 14:42
 * @email: gnoszsong@gmail.com
 * @description: description
 */
public enum JGBleCmd {

    NET_BIND_CONFIG((short) 0x1001, "联网绑定"),
    NET_CONFIG((short) 0x1002, "联网"),
    BIND_CONFIG((short) 0x1003, "绑定"),

    RECV_MSG((short) 0x2001, "收到信息"),
    RECV_MSG_ERROR((short) 0x2002, "收到信息有误"),

    NET_CONNECT_SUCCESS((short) 0x2101, "网络连接成功"),
    NET_HAD_CONECT((short) 0x2102, "网络已经连接"),
    NET_NOT_EXIST((short) 0x2103, "网络不存在"),
    NET_PWD_ERROR((short) 0x2104, "网络密码有误"),
    NET_NOT_SUPPORT((short) 0x2105, "网络不支持"),
    NET_CONNECT_TIMEOUT((short) 0x2106, "网络连接超时"),

    NET_OK((short) 0x2201, "网络正常"),
    NET_NO_WWW((short) 0x2202, "无法访问万维网"),
    NET_NO_SERVER((short) 0x2203, "无法访问服务器"),

    LOGIN_SUCCESS((short) 0x2301, "登录成功"),
    LOGIN_FAILED((short) 0x2302, "登录失败"),

    BIND_SUCCESS((short) 0x2401, "绑定成功"),
    HAD_BIND((short) 0x2402, "已经绑定"),
    HAD_BIND_OTHER_ACCOUNT((short) 0x2403, "已经被其他账号绑定"),
    BIND_NO_BAND((short) 0x2404, "绑定品牌不兼容");


    private JGBleCmd(short cmd, String desc) {
        this.cmd = cmd;
        this.desc = desc;
    }

    short cmd;
    short from;
    String desc;

    public short getCmd() {
        return cmd;
    }

    public short getFrom() {
        return from;
    }

    public String getDesc() {
        return desc;
    }}
