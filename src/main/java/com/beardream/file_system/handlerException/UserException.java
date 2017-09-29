package com.beardream.file_system.handlerException;

import com.beardream.file_system.enums.ResultEnum;

/**
 * Created by soft01 on 2017/4/18.
 */
public class UserException extends RuntimeException {

    private int code;

    public UserException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
