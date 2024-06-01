package store.auroraauction.be.exception;

public class BadRequestException extends org.apache.coyote.BadRequestException {

    public BadRequestException(String message) {
        super(message);
    }
}
