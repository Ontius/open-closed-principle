package ch.zhaw.solid.shared.error;

public class OrderException extends RuntimeException {

    public OrderException(String message) {
        super(message);
    }
}
