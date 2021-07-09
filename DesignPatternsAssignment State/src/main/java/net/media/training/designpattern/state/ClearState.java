package net.media.training.designpattern.state;

public class ClearState implements CalculatorState {
    CalculatorContext calculatorContext;

    ClearState(CalculatorContext calculatorContext) {
        this.calculatorContext = calculatorContext;
    }


    public void gotOperator(char operator) {
        //Go to error state
        calculatorContext.setCurrentState(calculatorContext.getErrorState());
    }

    public void gotEqualsOperator() {
        //Do nothing state remains same
    }

    public void gotNumber(char c) {
        calculatorContext.appendToFirstOperand(c);

        //Change state to OneNumberState
        calculatorContext.setCurrentState(calculatorContext.getOneNumberState());
    }

    public void gotClear() {
        //Do nothing state remains same
    }
}
