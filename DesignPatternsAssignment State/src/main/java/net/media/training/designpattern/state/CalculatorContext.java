package net.media.training.designpattern.state;

public class CalculatorContext {
    public void setCurrentState(CalculatorState currentState) {
        this.currentState = currentState;
    }

    public CalculatorState currentState;

    public CalculatorState errorState = new ErrorState(this);
    public CalculatorState clearState = new ClearState(this);
    public CalculatorState oneNumberState = new OneNumberState(this);
    public CalculatorState oneNumberAndOperandState = new OneNumberAndOperandState(this);

    public CalculatorState getCurrentState() {
        return currentState;
    }

    public CalculatorState getErrorState() {
        return errorState;
    }

    public void setErrorState(CalculatorState errorState) {
        this.errorState = errorState;
    }

    public CalculatorState getClearState() {
        return clearState;
    }

    public void setClearState(CalculatorState clearState) {
        this.clearState = clearState;
    }

    public CalculatorState getOneNumberState() {
        return oneNumberState;
    }

    public void setOneNumberState(CalculatorState oneNumberState) {
        this.oneNumberState = oneNumberState;
    }

    public CalculatorState getOneNumberAndOperandState() {
        return oneNumberAndOperandState;
    }

    public void setOneNumberAndOperandState(CalculatorState oneNumberAndOperandState) {
        this.oneNumberAndOperandState = oneNumberAndOperandState;
    }

    public int operand1;
    public int operand2;
    public char operator;

    public void appendToFirstOperand(char c) {
        operand1 = operand1 * 10 + (int) c;
    }

    public int getOperand1() {
        return operand1;
    }

    public void setOperand1(int operand1) {
        this.operand1 = operand1;
    }

    public int getOperand2() {
        return operand2;
    }

    public void setOperand2(int operand2) {
        this.operand2 = operand2;
    }

    public char getOperator() {
        return operator;
    }

    public void setOperator(Character operator) {
        this.operator = operator;
    }
}
