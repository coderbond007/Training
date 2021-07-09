package net.media.training.designpattern.state;

public class ErrorState implements CalculatorState {
    CalculatorContext calculatorContext;

    ErrorState(CalculatorContext calculatorContext) {
        this.calculatorContext = calculatorContext;
    }


    public void gotOperator(char operator) {
        //Go to error state
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
