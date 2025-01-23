package cn.tsguas.osc.exception;

import cn.tsguas.osc.config.ResultUtils;
import cn.tsguas.osc.enums.ErrorCodeEnum;
import cn.tsguas.osc.model.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * 全局异常处理器
 *
 * @author Uncommon
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseVO<Object> businessExceptionHandler(BusinessException e) {
        log.error("businessException: " + e.getMessage(), e);
        return ResultUtils.error(e.getCode(), e.getMessage(), e.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseVO<Object> runtimeExceptionHandler(RuntimeException e) {
        log.error("runtimeException: ", e);
        return ResultUtils.error(ErrorCodeEnum.SYSTEM_ERROR, e.getMessage(), "");
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseVO<Object> argumentExceptionHandler(MethodArgumentNotValidException e) {
        String result = "";
        if (!e.getBindingResult().getAllErrors().isEmpty()) {
            result = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        }
        log.error("MethodArgumentNotValidException:", e);
        return ResultUtils.error(ErrorCodeEnum.PARAMS_ERROR, "请求参数错误", result);
    }

    /**
     * 文件传输超限异常
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseVO<Object> maxUploadSizeExceptionHandler(MaxUploadSizeExceededException e) {
        log.error("MaxUploadSizeExceededException: ", e);
        return ResultUtils.error(ErrorCodeEnum.SYSTEM_ERROR, e.getMessage(), "文件太大");
    }
}
