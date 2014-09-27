package common.model.exception;


public class BusinessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5503484719386393490L;
	private ExceptionLevelEnum nivel;

    public BusinessException() {
    }

    public BusinessException(String message, Throwable cause) {
        this(message, cause, ExceptionLevelEnum.ADMIN_LEVEL);
    }

    public BusinessException(String message, Throwable cause,
        ExceptionLevelEnum nivel) {
        super(message, cause);

        this.nivel = nivel;
    }

    public BusinessException(Throwable cause) {
        this(cause, ExceptionLevelEnum.ADMIN_LEVEL);
    }

    public BusinessException(Throwable cause, ExceptionLevelEnum nivel) {
        super(cause);

        this.nivel = nivel;
    }

    public BusinessException(String message) {
        this(message, ExceptionLevelEnum.ADMIN_LEVEL);
    }

    public BusinessException(String message, ExceptionLevelEnum nivel) {
        super(message);

        this.nivel = nivel;
    }

    public ExceptionLevelEnum getNivel() {
        return nivel;
    }
}
