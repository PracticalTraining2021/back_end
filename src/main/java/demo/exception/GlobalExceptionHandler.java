package demo.exception;

import cn.hutool.crypto.CryptoException;
import demo.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLSyntaxErrorException;
import java.util.Set;

/**
 * 控制器增强
 * 用来捕获@RequestMapping的方法中所有抛出的SparrowException
 * 将error message 放入Result中，返回给前端
 *
 * @see Result
 * @see BusinessException
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理逻辑异常或其他越界异常
     *
     * @param ex
     * @param response
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public Result errorHandle(BusinessException ex, HttpServletResponse response) {
        logger.error(ex.getMessage());
        logger.error(ex.getDetailMessage());

//        HttpUtil.setResponseStatus(response, ex.getErrorCode());
        return ex.getErrorResult();
    }

    @ExceptionHandler(CryptoException.class)
    @ResponseBody
    public Result expHandler(CryptoException e) {
        logger.error(e.getLocalizedMessage());

        return Result.BAD().data("密钥校验出错，请重新获取公钥再操作").build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result expHandler(Exception e) {
        logger.error("==================================");
        logger.error(e.getMessage());
        e.printStackTrace();

        return Result.BAD().data(e.getLocalizedMessage() + "==").build();
    }

    /**
     * 处理参数校验失败异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseBody
    public Result resolveConstraintViolationException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        if (!CollectionUtils.isEmpty(constraintViolations)) {
            StringBuilder msgBuilder = new StringBuilder();
            for (ConstraintViolation constraintViolation : constraintViolations) {
                msgBuilder.append(constraintViolation.getMessage()).append(",");
            }
            String errorMessage = msgBuilder.toString();
            if (errorMessage.length() > 1) {
                errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
            }
            return Result.BAD().status(ErrorCode.PARAM_VALIDATION_ERROR.
                    getHttpStatus()).msg(ErrorCode.PARAM_VALIDATION_ERROR.getMsg()).data(errorMessage).build();
        }
        return Result.BAD().status(ErrorCode.PARAM_VALIDATION_ERROR.
                getHttpStatus()).msg(ErrorCode.PARAM_VALIDATION_ERROR.getMsg()).data(ex.getMessage()).build();
    }

    /**
     * 处理请求中缺少请求参数（或请求参数为空）异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseBody
    public Result resolveMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        StringBuilder msgBuilder = new StringBuilder();
        msgBuilder.append("缺少请求参数:").append(ex.getParameterName());
        return Result.BAD().status(ErrorCode.PARAM_ERR_REQUEST_DATA_REQUIRED_FIELD_IS_NULL.getHttpStatus())
                .msg(ErrorCode.PARAM_ERR_REQUEST_DATA_REQUIRED_FIELD_IS_NULL.getMsg())
                .data(msgBuilder.toString()).build();
    }

    /**
     * 处理请求中缺少请求体异常
     * 如，Controller中处理post或put请求的方法使用了@RequestBody注解（且该注解的required属性为true）接收请求体，而实际请求中缺少该请求体
     * <p>
     * 待完善。。。
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseBody
    public Result resolveHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return Result.BAD().data(ex.getHttpInputMessage()).build();
    }

    /**
     * 进行数据库操作时发生异常
     * 如，使用 updateSelective 时，缺少更新字段
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({SQLSyntaxErrorException.class})
    @ResponseBody
    public Result resolveSQLSyntaxErrorException(SQLSyntaxErrorException ex) {
        return Result.BAD().status(ErrorCode.SERVER_ERR_DB.getHttpStatus()).msg(ErrorCode.SERVER_ERR_DB.getMsg())
                .data("服务器数据库错误：请求体中缺少必要的字段或其他原因导致").build();
    }
}
