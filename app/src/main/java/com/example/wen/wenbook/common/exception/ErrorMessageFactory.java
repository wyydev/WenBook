package com.example.wen.wenbook.common.exception;

import android.content.Context;

import com.example.wen.wenbook.R;

/**
 * Created by wen on 2017/4/12.
 */

public class ErrorMessageFactory {
    public static  String create(Context context, int code){

        String errorMsg = null ;

        switch (code){

            case BaseException.HTTP_ERROR:

                errorMsg =  context.getResources().getString(R.string.error_http);

                break;

            case BaseException.SOCKET_TIME_OUT_ERROR:

                errorMsg =  context.getResources().getString(R.string.error_socket_timeout);

                break;
            case BaseException.SOCKET_ERROR:

                errorMsg =  context.getResources().getString(R.string.error_socket_unreachable);

                break;


            case BaseException.ERROR_HTTP_400:

                errorMsg =  context.getResources().getString(R.string.error_http_400);

                break;


            case BaseException.ERROR_HTTP_404:

                errorMsg =  context.getResources().getString(R.string.error_http_404);

                break;

            case BaseException.ERROR_HTTP_500:

                errorMsg =  context.getResources().getString(R.string.error_http_500);

                break;

            default:
                errorMsg=context.getResources().getString(R.string.error_unkown);
                break;


        }


        return  errorMsg;

    }
}
