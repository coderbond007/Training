package net.media.training.designpattern.exception;

public class WrongSalaryException extends RuntimeException {
    String message;

    public WrongSalaryException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "WrongSalaryException{" +
                "message='" + message + '\'' +
                '}';
    }
}
