package net.media.training.designpattern.state;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: goyalamit
 * Date: Jul 18, 2011
 * Time: 4:05:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class Calculator {
    private StringBuilder display;
    private Character lastChar;

    private List<Character> operators = new ArrayList<Character>() {
        {
            add('+');
            add('-');
            add('*');
            add('/');
        }
    };
    private Integer currentValue;
    private Character currentOperator;

    public String getDisplay() {
        return this.display.toString();
    }

    public Calculator() {
        this.lastChar = null;
        this.display = new StringBuilder();
    }

    public boolean isDigit(Character c) {
        return c >= 48 && c <= 57;
    }

    public boolean isOperator(Character c) {
        return operators.contains(c);
    }

    public boolean isClear(Character c) {
        return c != null && c.equals('c');
    }

    public boolean isError() {
        return "Error".equals(display.toString());
    }

    public boolean isEqualsOperator(Character c) {
        return c != null && c.equals('=');
    }

    public void input(Character c) {
        if (isClear(c)) {
            lastChar = null;
            clearState();
            return;
        }

        if (isError())
            return;

        if (isEqualsOperator(this.lastChar))
            return;

        if (isDigit(c)) {
            if (isOperator(lastChar)) {
                display = new StringBuilder();
            }
            lastChar = c;
            display.append(c);
            return;
        }
        if (isOperator(c)) {
            if ((lastChar == null) || isOperator(lastChar)) {
                display = new StringBuilder("Error");
                return;
            }

            if (isDigit(lastChar)) {
                updateCurrentValue(Integer.parseInt(display.toString()));
                updateCurrentOperator(c);
                lastChar = c;
            }
        }
        if (isEqualsOperator(c)) {
            if (lastChar == null)
                return;

            if (isOperator(lastChar)) {
                display = new StringBuilder("Error");
                return;
            }
            if (isDigit(lastChar) && currentValue != null) {
                updateCurrentValue(Integer.parseInt(display.toString()));
            }
        }
    }

    public void updateCurrentValue(Integer val) {
        currentValue = currentValue != null ? operate(currentValue, val) : val;
        display = new StringBuilder(currentValue.toString());
    }

    public void updateCurrentOperator(Character op) {
        this.currentOperator = op;
    }

    public void clearState() {
        currentOperator = null;
        currentValue = null;
        display = new StringBuilder();
    }

    public Integer operate(Integer i1, Integer i2) {
        switch (currentOperator) {
            case '+':
                return i1 + i2;
            case '-':
                return i1 - i2;
            case '*':
                return i1 * i2;
            case '/':
                return i1 / i2;
        }
        throw new RuntimeException("Invalid operator");
    }
}