package common.model.exception;


public enum MessageCRUDEnum {LOAD("ExceptionLOAD"),
    LOAD_NOT_FOUND("ExceptionLOADNOTFOUND"),
    LOADCOLLECTION("ExceptionLOADCOLLECTION"),
    SAVEORUPDATE("ExceptionSAVEORUPDATE"),
    SAVE_DUPLICATE("ExceptionSAVEDUPLICATE"),
    DELETE("ExceptionDELETE");

    // Identificador del mensaje.
    private final String key;

    // No permitimos instanciar objetos del tipo de esta clase.
    private MessageCRUDEnum(String key) {
        this.key = key;
    }

    public String toString() {
        return this.key;
    }

    public String key() {
        return this.key;
    }
}
