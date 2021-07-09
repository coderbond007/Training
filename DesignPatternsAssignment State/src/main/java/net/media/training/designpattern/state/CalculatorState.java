package net.media.training.designpattern.state;

public interface CalculatorState {
    void gotOperator(char operator);
    void gotEqualsOperator();
    void gotNumber(char c);
    void gotClear();
}
