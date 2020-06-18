package com.example.cal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText result;
    private EditText newNumber;
    private TextView displayOperation;

    private Double operand1 = null;
    private String pendingOperation = "=";
    private static final String STATE_PENDING_OPERATION="PendingOperation";
    private static final String STATE_PENDING_OPERAND1="Operand1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result =  findViewById(R.id.result);
        newNumber =  findViewById(R.id.newNumber);
        displayOperation =findViewById(R.id.displayoperation);

        Button button0 = findViewById(R.id.button0);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 =findViewById(R.id.button8);
        Button button9 =  findViewById(R.id.button9);
        Button buttonDot = findViewById(R.id.buttonDot);

        Button buttonEquals = findViewById(R.id.buttonEquals);
        Button buttonDivide= findViewById(R.id.buttonDivide);
        Button buttonMultiply = findViewById(R.id.buttonMultiply);
        Button buttonPlus = findViewById(R.id.buttonPlus);

        Button buttonMinus = findViewById(R.id.buttonMinus);
        Button buttonNeg = findViewById(R.id.buttonNeg);
        Button buttonClear= findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {

                                               String clear = newNumber.getText().toString();
                                            if(clear.length()!=0)
                                            {
                                               int length = clear.length();
                                               String cl = clear.substring(0, length - 1);
                                               newNumber.setText(cl);
                                               result.setText("");

                                           }else
                                            {
                                                displayOperation.setText("");
                                                newNumber.setText("");
                                                result.setText("");
                                                operand1=null;
                                            }
                                       }}
        );

        buttonClear.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longclick();
                return true;
            }
        });

        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                newNumber.append(b.getText().toString());
            }
        };
        button0.setOnClickListener(Listener);
        button1.setOnClickListener(Listener);
        button2.setOnClickListener(Listener);
        button3.setOnClickListener(Listener);
        button4.setOnClickListener(Listener);
        button5.setOnClickListener(Listener);
        button6.setOnClickListener(Listener);
        button7.setOnClickListener(Listener);
        button8.setOnClickListener(Listener);
        button9.setOnClickListener(Listener);
        buttonDot.setOnClickListener(Listener);


        View.OnClickListener opListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String op = b.getText().toString();
                String value = newNumber.getText().toString();


                try {
                    Double doubleValue = Double.valueOf(value);


                    performOperation(doubleValue, op);

                } catch (NumberFormatException e) {
                    newNumber.setText("");

                }

                pendingOperation = op;
                displayOperation.setText(pendingOperation);


            }
        };
        buttonEquals.setOnClickListener(opListener);
        buttonDivide.setOnClickListener(opListener);
        buttonMultiply.setOnClickListener(opListener);
        buttonMinus.setOnClickListener(opListener);
        buttonPlus.setOnClickListener(opListener);

        buttonNeg.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             String value = newNumber.getText().toString();
                                             if(value.length()==0){
                                                 newNumber.setText("-");

                                             }
                                             else
                                             {
                                                 try {
                                                     Double doubleValue=Double.valueOf(value);
                                                     doubleValue *=-1;
                                                     newNumber.setText(doubleValue.toString());
                                                 }catch (NumberFormatException e)
                                                 {
                                                     newNumber.setText("");
                                                 }
                                             }
                                         }
                                     }
        );


    }
    public void longclick(){
        newNumber.setText("");
        displayOperation.setText("");
        result.setText("");
        operand1=null;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(STATE_PENDING_OPERATION,pendingOperation);
        if(operand1!=null)
        {
            outState.putDouble(STATE_PENDING_OPERAND1, operand1);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pendingOperation=savedInstanceState.getString(STATE_PENDING_OPERATION);
        operand1=savedInstanceState.getDouble(STATE_PENDING_OPERAND1);
        displayOperation.setText(pendingOperation);
    }

    private void performOperation(Double value, String operation) {

            if (operand1 == null) {

                operand1 = value;
            } else {

                if (pendingOperation.equals("=")) {
                    pendingOperation = operation;
                }
                switch (pendingOperation) {
                    case "=":
                        operand1 = value;
                        break;
                    case "/":
                        if (operand1 == 0) {
                            operand1 = null;

                        } else
                            operand1 /= value;
                        break;
                    case "-":
                        operand1 -= value;
                        break;
                    case "*":
                        operand1 *= value;
                        break;
                    case "+":
                        operand1 += value;
                        break;

                }
            }
            result.setText(operand1.toString());
            newNumber.setText("");



    }

}
