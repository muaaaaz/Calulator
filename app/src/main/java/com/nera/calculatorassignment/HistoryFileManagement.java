package com.nera.calculatorassignment;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class Expression {
    public String expression, result;

    public Expression(String expression, String result) {
        this.expression = expression;
        this.result = result;
    }
}

public class HistoryFileManagement {
    Context applicationContext;
    File file;

    public HistoryFileManagement(Context applicationContext) {
        this.applicationContext = applicationContext;
        this.file = new File(applicationContext.getFilesDir(), "history.csv");
    }

    /**
     * Append expressions from list at end of file in CSV format ended with line break.
     *
     * @param expressionArrayList ArrayList containing expressions to be added
     * @throws IOException IOExpression
     */
    public void addExpressionListToFile(ArrayList<Expression> expressionArrayList) throws IOException {
        try (FileWriter fileWriter = new FileWriter(file, true)) {
            for (Expression expression : expressionArrayList) {
                // writing in CSV format
                fileWriter.append(expression.expression + "," + expression.result + "\n");
            }
        }
    }

    /**
     * Append expression at end of file in CSV format ended with line break.
     *
     * @param expression expression to be added
     * @throws IOException IOExpression
     */
    public void addExpressionToFile(Expression expression) throws IOException {
        try (FileWriter fileWriter = new FileWriter(file, true)) {
            // writing in CSV format
            fileWriter.append(expression.expression + "," + expression.result + "\n");
        }
    }

    public ArrayList<Expression> getExpressionList() {
        ArrayList<Expression> expressionArrayList = new ArrayList<>();
        try (Scanner fileReader = new Scanner(file)) {
            while (fileReader.hasNextLine()) {
                String[] expressionResult = fileReader.nextLine().split(",");
                expressionArrayList.add(new Expression(expressionResult[0], expressionResult[1]));
            }
        } catch (FileNotFoundException ignored) {
        }
        return expressionArrayList;
    }

    /**
     * Delete history.csv file.
     */
    public void clearHistory(){
        file.delete();
    }
}
