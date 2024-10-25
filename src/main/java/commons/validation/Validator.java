package commons.validation;

public interface Validator<T> {
    ValidationResult isValid(T object);
}
