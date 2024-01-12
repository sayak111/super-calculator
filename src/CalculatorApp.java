import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import java.util.function.DoubleUnaryOperator;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors

public class CalculatorApp extends JFrame implements ActionListener {
    
    private JTextField textField;
    private JButton[] buttons;
    private double operand1, operand2, result;
    private char operator;
    private boolean shiftPressed;
    private double[] consts;
    public CalculatorApp() {
        setTitle("Calculator");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        textField = new JTextField();
        textField.setEditable(false);
        
        add(textField, BorderLayout.NORTH);

        buttons = new JButton[]{
                new JButton("7"), new JButton("8"), new JButton("9"), new JButton("/"),
                new JButton("4"), new JButton("5"), new JButton("6"), new JButton("*"),
                new JButton("1"), new JButton("2"), new JButton("3"), new JButton("-"),
                new JButton("0"), new JButton("."), new JButton("="), new JButton("+"),
                new JButton("Delete"), new JButton("Reset"),
                new JButton("x^2"), new JButton("√"),new JButton("x^n"),
                new JButton("%"), new JButton("1/x"), new JButton("π"),
                new JButton("A"), new JButton("B"), new JButton("C"), new JButton("D"),
                new JButton("E"), new JButton("F"),
                new JButton("sin"), new JButton("cos"), new JButton("tan"),
                new JButton("Shift")
        };

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(9, 4));

        for (JButton button : buttons) {
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
        read_const();
    }
    
    private void read_const(){
        try {
            File file = new File("filename.txt");
            Scanner myReader = new Scanner(file);
            int i = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                consts[i] = Integer.parseInt(data);
                i++;
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      consts = new double[]{0,0,0,0,0,0};
    }
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        String buttonText = source.getText();

        if (shiftPressed) {
            handleShiftButton(buttonText);
        } else {
            handleRegularButton(buttonText);
        }
    }

    private void handleRegularButton(String buttonText) {
        switch (buttonText) {
            case "=":
                operand2 = Double.parseDouble(textField.getText());
                calculate();
                break;
            case "/":
            case "*":
            case "-":
            case "+":
                operand1 = Double.parseDouble(textField.getText());
                operator = buttonText.charAt(0);
                textField.setText("");
                break;
            case "Delete":
                String currentText = textField.getText();
                if (!currentText.isEmpty()) {
                    textField.setText(currentText.substring(0, currentText.length() - 1));
                }
                break;
            case "Reset":
                textField.setText("");
                break;
            case "x^2":
                operand1 = Double.parseDouble(textField.getText());
                result = Math.pow(operand1, 2);
                textField.setText(String.valueOf(result));
                break;
            case "√":
                operand1 = Double.parseDouble(textField.getText());
                if (operand1 >= 0) {
                    result = Math.sqrt(operand1);
                    textField.setText(String.valueOf(result));
                } else {
                    textField.setText("Error");
                }
                break;
            case "%":
                operand1 = Double.parseDouble(textField.getText());
                result = operand1 / 100;
                textField.setText(String.valueOf(result));
                break;
            case "1/x":
                operand1 = Double.parseDouble(textField.getText());
                if (operand1 != 0) {
                    result = 1 / operand1;
                    textField.setText(String.valueOf(result));
                } else {
                    textField.setText("Error");
                }
                break;
            case "π":
                textField.setText(String.valueOf(Math.PI));
                break;
            case "sin":
            case "cos":
            case "tan":
                operand1 = Double.parseDouble(textField.getText());
                performTrigonometricOperation(buttonText);
                break;
            case "A":
            case "B":
            case "C":
            case "D":
            case "E":
            case "F":
                textField.setText(textField.getText() + consts[buttonText.charAt(0)-64]);
                break;
            case "x^n":
                operand1 = Double.parseDouble(textField.getText());
                operator = '^';
                textField.setText("");
                break;
            case "Shift":
                shiftPressed = true;
                break;
            default:
                textField.setText(textField.getText() + buttonText);
        }
    }

    private void handleShiftButton(String buttonText) {
        switch (buttonText) {
            case "x^2":
                operand1 = Double.parseDouble(textField.getText());
                result = Math.pow(operand1, 3);
                textField.setText(String.valueOf(result));
                break;
            case "√":
                operand1 = Double.parseDouble(textField.getText());
                result = Math.pow(operand1, 1.0 / 3);
                break;
            case "x^n":
                operand1 = Double.parseDouble(textField.getText());
                operator = '√';
                textField.setText("");
                break;
            
            case "sin":
            case "cos":
            case "tan":
                operand1 = Double.parseDouble(textField.getText());
                performInverseTrigonometricOperation(buttonText);
                break;
            case "A":
            case "B":
            case "C":
            case "D":
            case "E":
            case "F":
                consts[buttonText.charAt(0)-64] = Double.parseDouble(textField.getText());
            case "Shift":
                shiftPressed = false;
                break;
            default:
                // For other buttons, do nothing when Shift is pressed
                break;
        }
    }

    private void calculate() {
        switch (operator) {
            case '/':
                result = operand1 / operand2;
                break;
            case '*':
                result = operand1 * operand2;
                break;
            case '-':
                result = operand1 - operand2;
                break;
            case '+':
                result = operand1 + operand2;
                break;
            case'^':
                result = Math.pow(operand1, operand2);
                break;
            case '√':
                result = Math.pow(operand1, 1/operand2);
            }
        textField.setText(String.valueOf(result));
    }

    private void performTrigonometricOperation(String operation) {
        DoubleUnaryOperator trigFunction;
        switch (operation) {
            case "sin":
                trigFunction = Math::sin;
                break;
            case "cos":
                trigFunction = Math::cos;
                break;
            case "tan":
                trigFunction = Math::tan;
                break;
            default:
                return;
        }
        result = trigFunction.applyAsDouble(Math.toRadians(operand1));
        textField.setText(String.valueOf(result));
    }

    private void performInverseTrigonometricOperation(String operation) {
        DoubleUnaryOperator inverseTrigFunction;
        switch (operation) {
            case "sin":
                inverseTrigFunction = Math::asin;
                break;
            case "cos":
                inverseTrigFunction = Math::acos;
                break;
            case "tan":
                inverseTrigFunction = Math::atan;
                break;
            default:
                return;
        }
        
        result = inverseTrigFunction.applyAsDouble(Math.toRadians(operand1));
        textField.setText(String.valueOf(result));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculatorApp calculator = new CalculatorApp();
            calculator.setVisible(true);
        });
    }
}
