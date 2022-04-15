package ch.zhaw.solid.shared.error;

public class ShippingException extends RuntimeException {

    public ShippingException(String message) {
        super(message);
    }
}
