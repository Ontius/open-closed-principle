package ch.zhaw.solid.shared.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Resource not found")
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(Class<?> clazz, Serializable identifier) {
        super("A %s with the identifier '%s' does not exist.".formatted(clazz.getSimpleName(), identifier));
    }
}
