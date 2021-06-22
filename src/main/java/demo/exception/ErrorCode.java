package demo.exception;

import org.springframework.http.HttpStatus;

/**
 * 自定义错误状态码
 * 规范设计，便于统一管理
 */
public enum ErrorCode {
    // 400 Bad Request
    BAD_REQUEST_COMMON(400_000, "Bad Request"),
    PARAM_VALIDATION_ERROR(400_001, "参数不符合规范"),
    PARAM_ERR_REQUEST_DATA_REQUIRED_FIELD_IS_NULL(400_002, "请求数据必须字段不可为空"),

    // 500 Internal Server Error 服务器错误
    SERVER_EXCEPTION(500_000, "服务器发生异常"),
    SERVER_ERR_DB(500_001, "数据库异常"),

    // 404 Not Found
    NOT_FOUND_SOURCE(404_000, "找不到消息"),
    NOT_FOUND_PROJECT(404_001, "找不到项目"),
    NOT_FOUND_USER(404_002, "找不到用户"),

    // 403 Forbidden 未经授权访问
    FORBIDDEN_COMMON(403_000, "Forbidden"),

    ;
    private final int status;
    private final String msg;

    ErrorCode(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    /**
     * 获取相应的http状态码，用于设置返回response的status
     *
     * @return 3位 http status
     */
    public int getHttpStatus() {
        HttpStatus status = HttpStatus.valueOf(getStatus() / 1000);
        return status.value();
    }
}

