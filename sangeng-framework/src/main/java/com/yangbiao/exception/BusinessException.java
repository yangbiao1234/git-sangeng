package com.yangbiao.exception;

import com.yangbiao.enums.AppHttpCodeEnum;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BusinessException extends RuntimeException{
    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public BusinessException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }
}
