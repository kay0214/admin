package com.personal.common.exception;

/**
 * 应用运行时异常，统一抛此类
 */
public class IFastException extends RuntimeException {

    private static final long serialVersionUID = 6403925731816439878L;

    public IFastException() {
        super();
    }

    public IFastException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public IFastException(String message, Throwable cause) {
        super(message, cause);
    }

    public IFastException(String message) {
        super(message);
    }

    public IFastException(Throwable cause) {
        super(cause);
    }

}
