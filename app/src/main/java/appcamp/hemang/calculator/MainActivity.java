package appcamp.hemang.calculator;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    EditText result;
    TextView status;
    Switch switchForFun;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       result = (EditText)findViewById(R.id.editText);
        status = (TextView) findViewById(R.id.textView);
        switchForFun = (Switch) findViewById(R.id.switch1);
        result.setShowSoftInputOnFocus(false);
    }

    public void calc(View v){
        String expression = result.getText().toString();

        try {
            result.setText(PostEval.convert(InfixCalc.convert(expression)));
            if(switchForFun.isChecked())
            status.setText(Vocab.comment[new Random().nextInt(11)]);

            else {
                status.setText("");
            }
        }catch (Exception e){
            e.printStackTrace();
            status.setText("INVALID OPERATION");
        }

    }

    public void clr(View v){
        result.setText("");
    }

    public void enterDigit(View v){
        result.setText(result.getText().toString() + ((Button)v).getText().toString());
    }
}


class InfixCalc {

    static Stack<String> operatorStack = new Stack<String>();



    public static String convert(String exp){
        exp = exp.replaceAll("  ", " ");
        exp = exp.trim();
        exp ="( " + exp + " )";

        String [] expChar = exp.split(" ");
        String output = "";
        System.out.println(exp);
        for(int i=0;i<expChar.length;i++){
           System.out.println("Iteration" + String.valueOf(i) + " " +  expChar[i]);
            if(isNumeric(expChar[i])){
              //  System.out.println("RUN2!");
                output+= (expChar[i] + " ");
            }
            else if(expChar[i].equals("(")){
            //    System.out.println("RUN3!");
                operatorStack.push(expChar[i]);
            }
            else if(expChar[i].equals(")")){
              //  System.out.println("RUN4!");
                String seeTop;
                while (!((seeTop = seeTop()).equals("("))) {
                    output += (seeTop + " ");
                    popSeeTop();
                }
                operatorStack.pop();
            }

            else {
                System.out.println("SeeTop" + seeTop());
                System.out.println("Pro" + expChar[i]);
                while (priority(expChar[i]) <= priority(seeTop())) {

                    output += (seeTop() + " ");
                    popSeeTop();
                }
                operatorStack.push(expChar[i]);
            }
        }
        return output.trim();
    }

    private static boolean isOperator(String ch) {
        return ch.equals("^") || ch.equals("*") || ch.equals("/") || ch.equals("+") || ch.equals("-");
    }

    private static int priority(String operator) {
        System.out.println("In Priority Operator" + operator);
        char op = operator.charAt(0);

        switch (op) {
            case '^' : return 3;
            case '*' :
            case '/' : return 2;
            case '+' :
            case '-' : return 1;
        }
        return 0;
    }

    public static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    private static String seeTop() {
        if(!operatorStack.empty())
            return  (String)operatorStack.peek();
        else
            return "0";
    }

    private static void popSeeTop() {
        if(!operatorStack.empty())
            operatorStack.pop();
    }
}
class PostEval {

    static Stack<Double> stack = new Stack<Double>();

    public  static String convert(String s) {

        String[] tokens = s.split(" ");

        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];

            if (!"+".equals(token) && !"*".equals(token) && !"-".equals(token) && !"/".equals(token) && !"^".equals(token)) {
                stack.push(Double.parseDouble(token));

            } else {
                String operator = tokens[i];
                double num2 = stack.pop();
                double num1 = stack.pop();
                double result = 0.0;
                if (operator.equals("*")) {
                    result = num2 * num1;
                } else if (operator.equals("+")) {
                    result = num1 + num2;
                } else if (operator.equals("-")) {
                    result = num1 - num2;
                } else if (operator.equals("/")) {
                    result = num1 / num2;
                } else if (operator.equals("^")) {
                    result = Math.pow(num1, num2);
                }

                stack.push(result);
            }

        }
        if(stack.empty()) return "0.0";
        else return  stack.pop().toString();
    }


}