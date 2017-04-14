package com.example.wen.wenbook.common.exception;

/**
 * Created by wen on 2017/4/12.
 */

public class BaseException extends Exception{
    /*API错误*/
    public static final int API_ERROR = 0x0;
    /*网络错误*/
    public static final int NETWORK_ERROR = 0x1;
    /*http错误*/
    public static final int HTTP_ERROR = 0x2;
    /*json错误*/
    public static final int JSON_ERROR = 0x3;
    /*未知错误*/
    public static final int UNKNOW_ERROR = 0x4;
    /*运行时错误，包含自定义异常*/
    public static final int RUNTIME_ERROR = 0x5;
    /*无法解析该域名/
    public static final int UN_KNOW_HOST_ERROR = 0x6;
    /*网络连接超时*/
    public static final int SOCKET_TIME_OUT_ERROR = 0x7;
    /*无网络连接*/
    public static final int SOCKET_ERROR = 0x8;



    //<---------------http方面细节异常-------------->
    public static final int ERROR_HTTP_400  = 400;

    public static final int ERROR_HTTP_404  = 404;

    public static final int ERROR_HTTP_405  = 405;

    public static final int ERROR_HTTP_500  = 500;



    private int code; //错误码

    private String msg;//显示给用户的错误提示信息

    public BaseException() {
    }

    public BaseException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseException(String message, int code, String msg) {
        super(message);
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
