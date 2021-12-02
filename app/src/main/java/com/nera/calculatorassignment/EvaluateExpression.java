package com.nera.calculatorassignment;

import java.util.Stack;

/**
 * The ExpressionEval class evalutes mathmatical expressions seprated with help
 * of operators and functions.
 * <p>
 * Braces > Power > Division >= Multiplication > Addition >= Subtraction
 */
public class EvaluateExpression {

    String exp; // expression to be evaluated
    Stack<Double> values;
    Stack<Character> operators;

    /**
     * Initialize data members of class.
     *
     * @param expression Arithmetic expression to be evaluated
     */
    public EvaluateExpression(String expression) {
        this.exp = expression;
        values = new Stack<Double>();
        operators = new Stack<Character>();
    }

    /**
     * Set new value to expression. Clear values and operators stack.
     *
     * @param expression Arithmetic expression to be evaluated
     */
    public void setExpression(String expression) {
        this.exp = expression;
        values.clear();
        operators.clear();
    }

    /**
     * Braces > Power > Division >= Multiplication > Addition >= Subtraction
     *
     * @param a operator
     * @param b operator
     * @return true if operator a have precedence over b, false otherwise
     */
    private boolean precedence(char a, char b) {
        if (a == '^')
            return true;
        if ((a == '/' || a == '*') && b != '^')
            return true;
        if ((a == '+' || a == '-') && (b != '/' && b != '*'))
            return true;
        return false;
    }

    /**
     * Check if character is numeric.
     *
     * @param c character to be checked
     * @return true if character is numeric false otherwise
     */
    private boolean isNum(char c) {
        return c >= '0' && c <= '9';
    }

    /**
     * Check if character is in [/, *, +, -]
     *
     * @param c character to be checked
     * @return true if character is in [/, *, +, -] false otherwise
     */
    private boolean isOperator(char c) {
        switch (c) {
            case '^':
            case '/':
            case '*':
            case '+':
            case '-':
                return true;
            default:
                return false;
        }
    }

    /**
     * Apply specified operation on n1 and n2.
     *
     * @param n1       number 1
     * @param n2       number 2
     * @param operator operation to be peformed [/, *, +, -]
     * @return result (double) after performing the operation
     * @throws Exception ZeroDivisionError, Undefined Operator
     */
    private double applyOperator(double n1, double n2, char operator) throws Exception {
        switch (operator) {
            case '^':
                return Math.pow(n2, n1);
            case '/':
                try {
                    return n2 / n1;
                } catch (Exception e) {
                    throw e;
                }
            case '*':
                return n2 * n1;
            case '+':
                return n2 + n1;
            case '-':
                return n2 - n1;
            default:
                throw new Exception("Undefined operator.");
        }
    }

    /**
     * Process expression and compute it.
     *
     * @throws Exception
     */
    private void processExpression() throws Exception {
        int i = 0;
        while (i < exp.length()) {
            char currChar = exp.charAt(i);
            if (isAlpha(currChar)) {
                int bCount = 1, bIndex = i;
                bIndex = exp.indexOf('(', i) + 1; // holds index of first (
                while (bIndex < exp.length() && bCount != 0) {
                    if (exp.charAt(bIndex) == ')')
                        bCount -= 1;
                    if (exp.charAt(bIndex) == '(')
                        bCount += 1;
                    bIndex += 1;
                }
                String temp = exp.substring(i, bIndex);
                i = bIndex - 1;
                try {
                    values.push(applyFunction(temp));
                } catch (Exception e) {
                    throw e;
                }
            } else if (isNum(currChar)) {
                String tempNum = "";
                while (i < exp.length() && (isNum(exp.charAt(i)) || exp.charAt(i) == '.')) {
                    tempNum += exp.charAt(i);
                    i++;
                }
                try {
                    double num = Double.parseDouble(tempNum);
                    values.push(num);
                } catch (Exception e) {
                    throw e;
                }
                i -= 1; // while loop incremented it to the next character in exp
            } else if (currChar == '(') {
                operators.push(currChar);
            } else if (currChar == ')') {
                while (!operators.empty() && values.size() >= 2 && operators.peek() != '(') {
                    try {
                        double result = applyOperator(values.pop(), values.pop(), operators.pop());
                        values.push(result);
                    } catch (Exception e) {
                        throw e;
                    }
                }
                operators.pop();
            } else if (isOperator(currChar)) {
                char currOp = currChar;
                while (!operators.empty() && values.size() >= 2 && precedence(operators.peek(), currOp)) {
                    try {
                        double result = applyOperator(values.pop(), values.pop(), operators.pop());
                        values.push(result);
                    } catch (Exception e) {
                        throw e;
                    }
                }
                operators.push(currOp);
            } else if (currChar == '%') {
                if (!values.empty()) {
                    double num = values.pop();
                    num = num / 100;
                    values.push(num);
                }
            } else if (currChar == '!') {
                if (!values.empty()) {
                    double num = values.pop();
                    for (int ii = (int) num - 1; ii > 0; ii--) {
                        num *= (double) ii;
                    }
                    values.push(num);
                }
            } else if (Character.toLowerCase(currChar) == Character.toLowerCase(960)) {
                values.push(Math.PI);
            }
            i++;
        }
        while (!operators.empty() && values.size() >= 2) {
            try {
                double result = applyOperator(values.pop(), values.pop(), operators.pop());
                values.push(result);
            } catch (Exception e) {
                throw e;
            }
        }
    }

    /**
     * Check if character is capital alphabet.
     *
     * @param c character to be checked
     * @return true if charater is in between A-Z, false otherwise
     */
    private boolean isAlpha(char c) {
        return c >= 'A' && c <= 'Z';
    }

    /**
     * Provide result after processing of expression.
     *
     * @return result after processing the expression
     * @throws Exception any if encountered
     */
    public Double result() throws Exception {
        try {
            processExpression();
            /*double value = values.peek();
            if (value == (long) value)
                return Long.toString((long) value);
            else
                return Double.toString(value);*/
            return values.peek();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Convert double to String.
     *
     * @param value value to be converted
     * @return converted value
     */
    public static String formatDoubleValue(double value) {
        if (value == (long) value) {
            return Long.toString((long) value);
        } else
            return Double.toString(value);
    }

    /**
     * Apply mathematical function to expression using recursive approach.
     *
     * @param fexp expression to be evaluated
     * @return result (double) of expression
     * @throws Exception Undefined function
     */
    public double applyFunction(String fexp) throws Exception {
        int startIndex = fexp.indexOf('(');
        String func = fexp.substring(0, startIndex);
        fexp = fexp.substring(startIndex);
        try {
            double fexpResult = (new EvaluateExpression(fexp)).result();
            switch (func) {
                case "SQRT":
                    return Math.sqrt(fexpResult);
                case "CBRT":
                    return Math.cbrt(fexpResult);
                case "EPOW":
                    return Math.exp(fexpResult);
                case "SIN":
                    return Math.sin(Math.toRadians(fexpResult));
                case "ASIN":
                    return Math.toDegrees(Math.asin(fexpResult));
                case "COS":
                    return Math.cos(Math.toRadians(fexpResult));
                case "ACOS":
                    return Math.toDegrees(Math.acos(fexpResult));
                case "TAN":
                    return Math.tan(Math.toRadians(fexpResult));
                case "ATAN":
                    return Math.toDegrees(Math.atan(fexpResult));
                case "LOG":
                    return Math.log10(fexpResult);
                case "LN":
                    return Math.log(fexpResult);
                default:
                    throw new Exception("Undefined function.");
            }
        } catch (Exception e) {
            throw e;
        }
    }
}