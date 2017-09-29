package com.beardream.file_system.handlerException;

import com.beardream.file_system.model.Result;
import com.beardream.file_system.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by beardream on 2017/9/15.
 */
@ControllerAdvice
public class handlerException {

    private final static Logger logger = LoggerFactory.getLogger(handlerException.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    private Result ExceptionResult(Exception e){
        if(e instanceof UserException){
            UserException userException = (UserException) e;
            return ResultUtil.error(userException.getCode(),userException.getMessage());
        }else {
            logger.error("异常={}",e);
            return ResultUtil.error(-1,"系统错误");
        }
    }
}
