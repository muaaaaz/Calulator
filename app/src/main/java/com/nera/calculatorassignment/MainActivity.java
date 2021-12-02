package com.nera.calculatorassignment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    /* Keys */
    private static final String KEY_TOKENS = "KEY_TOKENS";
    private static final String KEY_TEXT_HOLDER = "TEXT_HOLDER_TEXT";
    private static final String KEY_EXP_HOLDER = "EXP_HOLDER_TEXT";
    private static final String KEY_RESET_FLAG = "KEY_RESET_FLAG";
    private static final String TAG = "MainActivity";
    TextView textHolder;    // text holder
    TextView expressionHolder;    // live expression holder
    Tokens tokens = new Tokens();   // to hold input token in stacking order
    boolean resetFlag = false;  // clear text from textHolder, expressionHolder

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textHolder = findViewById(R.id.text_holder);
        expressionHolder = findViewById(R.id.text_expression);

        // Binding buttons
        findViewById(R.id.btn_ce).setOnClickListener(this::clearTokens);
        findViewById(R.id.btn_delete).setOnClickListener(this::deleteToken);

        findViewById(R.id.btn_point).setOnClickListener(this::insertToken);
        findViewById(R.id.btn0).setOnClickListener(this::insertToken);
        findViewById(R.id.btn1).setOnClickListener(this::insertToken);
        findViewById(R.id.btn2).setOnClickListener(this::insertToken);
        findViewById(R.id.btn3).setOnClickListener(this::insertToken);
        findViewById(R.id.btn4).setOnClickListener(this::insertToken);
        findViewById(R.id.btn5).setOnClickListener(this::insertToken);
        findViewById(R.id.btn6).setOnClickListener(this::insertToken);
        findViewById(R.id.btn7).setOnClickListener(this::insertToken);
        findViewById(R.id.btn8).setOnClickListener(this::insertToken);
        findViewById(R.id.btn9).setOnClickListener(this::insertToken);

        findViewById(R.id.btn_divide).setOnClickListener(this::insertToken);
        findViewById(R.id.btn_product).setOnClickListener(this::insertToken);
        findViewById(R.id.btn_add).setOnClickListener(this::insertToken);
        findViewById(R.id.btn_sub).setOnClickListener(this::insertToken);

        findViewById(R.id.btn_r0).setOnClickListener(this::roundOff);
        findViewById(R.id.btn_r2).setOnClickListener(this::roundOff);
        findViewById(R.id.btn_left_bracket).setOnClickListener(this::insertToken);
        findViewById(R.id.btn_right_bracket).setOnClickListener(this::insertToken);
        findViewById(R.id.btn_pie).setOnClickListener(this::insertToken);
        findViewById(R.id.btn_percent).setOnClickListener(this::insertToken);
        findViewById(R.id.btn_sq_root).setOnClickListener(v -> insertToken(v, "SQRT("));
        findViewById(R.id.btn_square).setOnClickListener(v -> insertToken(v, "^2"));

        findViewById(R.id.btn_equal).setOnClickListener(this::evaluate);

        // landscape button's binding
        if (findViewById(R.id.btn_factorial) != null)
            findViewById(R.id.btn_factorial).setOnClickListener(this::insertToken);
        if (findViewById(R.id.btn_ePowerX) != null)
            findViewById(R.id.btn_ePowerX).setOnClickListener(v -> insertToken(v, "EPOW("));
        if (findViewById(R.id.btn_xPowerY) != null)
            findViewById(R.id.btn_xPowerY).setOnClickListener(v -> insertToken(v, "^"));
        if (findViewById(R.id.btn_cube_root) != null)
            findViewById(R.id.btn_cube_root).setOnClickListener(v -> insertToken(v, "CBRT("));
        if (findViewById(R.id.btn_cube) != null)
            findViewById(R.id.btn_cube).setOnClickListener(v -> insertToken(v, "^3"));
        if (findViewById(R.id.btn_sin) != null)
            findViewById(R.id.btn_sin).setOnClickListener(v -> insertToken(v, "SIN("));
        if (findViewById(R.id.btn_sin_inverse) != null)
            findViewById(R.id.btn_sin_inverse).setOnClickListener(v -> insertToken(v, "ASIN("));
        if (findViewById(R.id.btn_cos) != null)
            findViewById(R.id.btn_cos).setOnClickListener(v -> insertToken(v, "COS("));
        if (findViewById(R.id.btn_cos_inverse) != null)
            findViewById(R.id.btn_cos_inverse).setOnClickListener(v -> insertToken(v, "ACOS("));
        if (findViewById(R.id.btn_tan) != null)
            findViewById(R.id.btn_tan).setOnClickListener(v -> insertToken(v, "TAN("));
        if (findViewById(R.id.btn_tan_inverse) != null)
            findViewById(R.id.btn_tan_inverse).setOnClickListener(v -> insertToken(v, "ATAN("));
        if (findViewById(R.id.btn_log) != null)
            findViewById(R.id.btn_log).setOnClickListener(v -> insertToken(v, "LOG("));
        if (findViewById(R.id.btn_ln) != null)
            findViewById(R.id.btn_ln).setOnClickListener(v -> insertToken(v, "LN("));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItem_history:
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Restore views and variable's state
     *
     * @param bundle savedInstanceBundle
     */
    private void restoreData(@NonNull Bundle bundle) {
        String textHolderText = bundle.getString(KEY_TEXT_HOLDER);
        String expressionHolderText = bundle.getString(KEY_EXP_HOLDER);
        String tokensCSV = bundle.getString(KEY_TOKENS);
        boolean resetFlag = bundle.getBoolean(KEY_RESET_FLAG);
        if (textHolderText != null)
            textHolder.setText(textHolderText);
        if (expressionHolderText != null)
            expressionHolder.setText(expressionHolderText);
        if (tokensCSV != null)
            tokens.loadCSV(tokensCSV);
        this.resetFlag = resetFlag;
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        restoreData(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_TEXT_HOLDER, textHolder.getText().toString());
        outState.putString(KEY_EXP_HOLDER, expressionHolder.getText().toString());
        outState.putString(KEY_TOKENS, tokens.toCSV());
        outState.putBoolean(KEY_RESET_FLAG, resetFlag);
    }

    /**
     * Clear textHolder, expressionHolder, tokens, resetFlag.
     */
    private void isResetFlagActive() {
        if (resetFlag) {
            textHolder.setText("");
            expressionHolder.setText("");
            tokens.removeAllTokens();
            resetFlag = false;
        }
    }

    /**
     * Insert text to expression holder.
     *
     * @param view             Button which was clicked
     * @param tokenReplacement provided as alternative for button text where required
     */
    private void insertToken(View view, String tokenReplacement) {
        isResetFlagActive();
        if (tokenReplacement == null) {
            String btnText = ((Button) view).getText().toString();
            tokens.addToken(btnText);
        } else {
            tokens.addToken(tokenReplacement);
        }
        expressionHolder.setText(tokens.toExpression());
    }

    /**
     * Insert text to expression holder.
     *
     * @param view Button which was clicked
     */
    private void insertToken(View view) {
        insertToken(view, null);
    }

    /**
     * Remove latest entry in expressionHolder.
     *
     * @param view Button
     */
    private void deleteToken(View view) {
        isResetFlagActive();
        tokens.removeToken();
        expressionHolder.setText(tokens.toExpression());
    }

    /**
     * Clear textHolder, expressionHolder, tokens
     *
     * @param view
     */
    private void clearTokens(View view) {
        textHolder.setText("");
        expressionHolder.setText("");
        tokens.removeAllTokens();
    }

    /**
     * Evaluate the expression after calling Tokens.toExpression().
     *
     * @param view Button
     */
    private void evaluate(View view) {
        String expression = tokens.toExpression();
        textHolder.setText(expression);
        EvaluateExpression ee = new EvaluateExpression(expression);
        double result = 0;
        try {
            result = ee.result();
            expressionHolder.setText(EvaluateExpression.formatDoubleValue(result));
        } catch (Exception e) {
            expressionHolder.setText(String.format("ERR_EVAL [%s]", e.getMessage()));
        }
        resetFlag = true;

        // writing to file
        try {
            (new HistoryFileManagement(getApplicationContext())).addExpressionToFile(
                    new Expression(expression, String.valueOf(result))
            );
        } catch (IOException ioException) {
            Log.e(TAG, "evaluate: " + ioException.toString(), null);
        }
    }


    /**
     * Round off the number up to required decimals.
     *
     * @param view Button
     */
    private void roundOff(View view) {
        double numOfDecimals = 0;
        if (view.getId() == R.id.btn_r0) {
            numOfDecimals = 0;
        } else if (view.getId() == R.id.btn_r2) {
            numOfDecimals = 2;
        }
        this.evaluate(null);    // calculating expression if any
        try {
            String text = expressionHolder.getText().toString();
            if (text.equals(""))
                return;
            double num = Double.parseDouble(text);
            double multiplier = Math.pow(10d, numOfDecimals);
            num = Math.round(num * multiplier) / multiplier;
            expressionHolder.setText(EvaluateExpression.formatDoubleValue(num));
        } catch (Exception e) {
            expressionHolder.setText(String.format("ERR_R0_R1 [%s]", e.getMessage()));
        }
    }
}