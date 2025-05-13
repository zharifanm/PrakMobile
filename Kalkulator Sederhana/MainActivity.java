package com.example.calculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText display;
    private StringBuilder input = new StringBuilder();
    private boolean isOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);

        int[] digitButtons = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3,
                R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7,
                R.id.btn8, R.id.btn9, R.id.btn00, R.id.btnDot
        };

        for (int id : digitButtons) {
            Button btn = findViewById(id);
            btn.setOnClickListener(v -> {
                if (isOn) {
                    input.append(btn.getText());
                    display.setText(input.toString());
                }
            });
        }

        findViewById(R.id.btnOn).setOnClickListener(v -> {
            isOn = true;
            input.setLength(0);
            display.setText("");
        });

        findViewById(R.id.btnC).setOnClickListener(v -> {
            input.setLength(0);
            display.setText("");
        });

        findViewById(R.id.btnExit).setOnClickListener(v -> finish());

        setOperator(R.id.btnAdd, "+");
        setOperator(R.id.btnSub, "-");
        setOperator(R.id.btnMul, "*");
        setOperator(R.id.btnDiv, "/");

        findViewById(R.id.btnEq).setOnClickListener(v -> {
            if (isOn) {
                try {
                    double result = evaluateExpression(input.toString());
                    display.setText(formatResult(result));
                    input.setLength(0);
                } catch (Exception e) {
                    display.setText("Error");
                    input.setLength(0);
                }
            }
        });

        findViewById(R.id.btnSqrt).setOnClickListener(v -> {
            if (isOn) {
                try {
                    double val = Double.parseDouble(display.getText().toString());
                    double result = Math.sqrt(val);
                    display.setText(formatResult(result));
                    input.setLength(0);
                } catch (Exception e) {
                    display.setText("Error");
                }
            }
        });

        findViewById(R.id.btnSin).setOnClickListener(v -> trigFunction("sin"));
        findViewById(R.id.btnCos).setOnClickListener(v -> trigFunction("cos"));
        findViewById(R.id.btnTan).setOnClickListener(v -> trigFunction("tan"));
    }

    private void setOperator(int id, String operator) {
        Button btn = findViewById(id);
        btn.setOnClickListener(v -> {
            if (isOn) {
                input.append(operator);
                display.setText(input.toString());
            }
        });
    }

    private void trigFunction(String func) {
        if (isOn) {
            try {
                double val = Double.parseDouble(display.getText().toString());
                double radians = Math.toRadians(val);
                double result = 0;

                switch (func) {
                    case "sin": result = Math.sin(radians); break;
                    case "cos": result = Math.cos(radians); break;
                    case "tan": result = Math.tan(radians); break;
                }

                display.setText(formatResult(result));
                input.setLength(0);
            } catch (Exception e) {
                display.setText("Error");
            }
        }
    }

    private double evaluateExpression(String expr) {
        char[] tokens = expr.toCharArray();
        double result = 0, num = 0;
        char op = '+';
        for (int i = 0; i < tokens.length; i++) {
            char c = tokens[i];
            if (Character.isDigit(c) || c == '.') {
                StringBuilder sb = new StringBuilder();
                while (i < tokens.length && (Character.isDigit(tokens[i]) || tokens[i] == '.')) {
                    sb.append(tokens[i]);
                    i++;
                }
                i--;
                num = Double.parseDouble(sb.toString());
                result = apply(result, num, op);
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                op = c;
            }
        }
        return result;
    }

    private double apply(double a, double b, char op) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': return b == 0 ? 0 : a / b;
            default: return b;
        }
    }

    private String formatResult(double result) {
        if (result == (long) result)
            return String.format("%d", (long) result);
        else
            return String.valueOf(result);
    }
}
