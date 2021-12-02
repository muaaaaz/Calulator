package com.nera.calculatorassignment;

import java.util.Arrays;
import java.util.Stack;

public class Tokens {
    Stack<String> tokens;   // hold input tokens

    /**
     * Init of data members (tokens).
     */
    public Tokens() {
        tokens = new Stack<String>();
    }

    /**
     * Add new token to the tokens.
     * @param token value to be added
     */
    public void addToken(String token) {
        if (token.equals(".") && !tokens.empty() && tokens.peek().equals("."))
            return;
        if (tokensToBeChecked(token) && (tokens.isEmpty() || !tokens.empty() && tokensToBeChecked(tokens.peek())))
            return;

        tokens.push(token);
    }

    /**
     * Remove latest token from tokens
     */
    public void removeToken() {
        if (!tokens.empty())
            tokens.pop();
    }

    /**
     * Clear all tokens.
     */
    public void removeAllTokens() {
        tokens.clear();
    }

    /**
     * Convert tokens to math expression that will be evaluated
     * @return Expression to be evaluated
     */
    public String toExpression() {
        StringBuilder exp = new StringBuilder();
        for (String s : tokens)
            exp.append(s);
        return exp.toString();
    }

    /**
     * Check if token will invalidate the expression or not.
     * @param token value to be checked
     * @return true if it is illegal token, false otherwise
     */
    private boolean tokensToBeChecked(String token) {
        switch (token) {
            case "^":
            case "/":
            case "*":
            case "+":
            case "-":
                return true;
            case "%":
            case "^2":
            case "^3":
                if (tokens.empty())
                    return true;
                if ((token.equals("^2") && tokens.peek().equals("^2")) || (token.equals("^3") && tokens.peek().equals("^3")))
                    return false;
        }
        return false;
    }

    /**
     * Convert tokens to a CSV formatted String
     * @return CSV String of tokens
     */
    public String toCSV() {
        StringBuilder exp = new StringBuilder();
        for (int i = 0; i < tokens.size(); i++) {
            exp.append(tokens.elementAt(i));
            if (i != tokens.size() - 1)
                exp.append(",");
        }

        return exp.toString();
    }

    /**
     *
     * @param tokensCSV
     */
    public void loadCSV(String tokensCSV) {
        tokens.addAll(Arrays.asList(tokensCSV.split(",")));
    }
}
