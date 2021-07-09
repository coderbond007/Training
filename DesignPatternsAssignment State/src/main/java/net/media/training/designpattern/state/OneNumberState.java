package net.media.training.designpattern.state;

public class OneNumberState implements CalculatorState {
    CalculatorContext calculatorContext;

    OneNumberState(CalculatorContext calculatorContext) {
        this.calculatorContext = calculatorContext;
    }


    public void gotOperator(char operator) {
        calculatorContext.setOperator(operator);
        //change to oneNumberAndOperandState
        calculatorContext.setCurrentState(calculatorContext.getOneNumberAndOperandState());
    }

    public void gotEqualsOperator() {
        //change to error state
        calculatorContext.setCurrentState(calculatorContext.getErrorState());
    }

    public void gotNumber(char c) {
        calculatorContext.appendToFirstOperand(c);
        //state remains same
    }

    public void gotClear() {
        calculatorContext.setOperand1(0);
        calculatorContext.setOperand1(0);
        calculatorContext.setOperator(null);

        //change state to clear
        calculatorContext.setCurrentState(calculatorContext.getClearState());
    }
}
