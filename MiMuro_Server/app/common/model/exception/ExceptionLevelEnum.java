package common.model.exception;


public enum ExceptionLevelEnum {USER_LEVEL("USER_LEVEL"),
    ADMIN_LEVEL("ADMIN_LEVEL");

    // Nivel de la excepci�n.
    private String nivel;

    ExceptionLevelEnum(String nivel) {
        this.nivel = nivel;
    }

    public String toString() {
        return this.nivel;
    }

    public String nivel() {
        return this.nivel;
    }
}
