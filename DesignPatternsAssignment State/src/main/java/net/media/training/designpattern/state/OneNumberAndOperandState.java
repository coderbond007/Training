package net.media.training.designpattern.state;

public class OneNumberAndOperandState implements CalculatorState {
    CalculatorContext calculatorContext;

    OneNumberAndOperandState(CalculatorContext calculatorContext) {
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
        //State remains same
    }

    public void gotClear() {
        calculatorContext.setOperand1(0);
        calculatorContext.setOperand1(0);
        calculatorContext.setOperator(null);

        //change state to clear
        calculatorContext.setCurrentState(calculatorContext.getClearState());
    }
}
