package net.media.training.designpattern.validator;

public interface Validator<T> {
    void validate(T value);
}
