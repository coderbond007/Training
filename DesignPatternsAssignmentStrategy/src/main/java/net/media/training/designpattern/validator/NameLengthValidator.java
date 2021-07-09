package net.media.training.designpattern.validator;

public class NameLengthValidator implements Validator<String> {
    private int maximumLength;

    public NameLengthValidator(int minimumLength) {
        this.maximumLength = minimumLength;
    }

    public void validate(String value) {
        if (value.length() > maximumLength) {
            throw new IllegalStateException("String length more than expected");
        }
    }
}
