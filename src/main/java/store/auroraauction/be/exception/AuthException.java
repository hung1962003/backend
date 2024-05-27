package store.auroraauction.be.exception;

public class AuthException extends RuntimeException { // dua ra khi password ko dg
    public AuthException(String message) {
        super(message);
    }
}
