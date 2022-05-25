package kz.doss.security.exception;

public class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = -1276769148163334712L;

    public BadRequestException(String s) {
        super(s);
    }
}