package net.media.training.designpattern.validator;

public class NotNullValidator implements Validator<String>{
    public void validate(String value) {
        if (value == null || value.isEmpty())
            throw new RuntimeException("String empty or null");
    }
}
