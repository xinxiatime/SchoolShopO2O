package com.lzr.exceptions;

/**
 * 业务类异常
 */
public class ShopOperationException extends RuntimeException {
    //构造函数
    public ShopOperationException(String msg){
        super(msg);
    }
}
