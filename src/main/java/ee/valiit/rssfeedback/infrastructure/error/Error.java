package ee.valiit.rssfeedback.infrastructure.error;

import lombok.Getter;

@Getter
public enum Error {
    INCORRECT_CREDENTIALS("Vale kasutajanimi või parool", 111);

    private final String message;
    private final Integer errorCode;

    Error(String message, Integer errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }
}
