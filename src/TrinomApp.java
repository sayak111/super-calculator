import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TrinomApp extends JFrame implements ActionListener{
    private JTextField textField;
    private JButton[] buttons;
    private double[] operands;
    private int i;
    
    public TrinomApp() {
        setTitle("Calculator");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        i=0;
        textField = new JTextField();
        textField.setEditable(false);
        
        add(textField, BorderLayout.NORTH);
        operands = new double[]{0,0,0,0,0};
        buttons = new JButton[]{
                new JButton("7"), new JButton("8"), new JButton("9"),
                new JButton("4"), new JButton("5"), new JButton("6"),
                new JButton("1"), new JButton("2"), new JButton("3"),
                new JButton("0"), new JButton("."), new JButton("="),
                new JButton("Delete"), new JButton("Reset"),
        };
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4));

        for (JButton button : buttons) {
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
}

        public void actionPerformed(ActionEvent e){
                JButton source = (JButton) e.getSource();
                String buttonText = source.getText();
                switch (buttonText) {
                        case "=":
                            if(i==2){
                                calculateQuadraticRoots(operands[0],operands[1],operands[2]);
                                break;
                            }
                            
                            operands[i] = Double.parseDouble(textField.getText());
                            i++;
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
                        default:
                            textField.setText(textField.getText() + buttonText);
                    }
        }
        public  void calculateQuadraticRoots(double a, double b, double c) {
                double discriminant = b * b - 4 * a * c;
        
                if (discriminant < 0) {
                        textField.setText("No real roots.");
                } else if (discriminant == 0) {
                    double root = -b / (2 * a);
                    textField.setText("One real root: " + root);
                } else {
                    double root1 = (-b + Math.sqrt(discriminant)) / (2 * a);
                    double root2 = (-b - Math.sqrt(discriminant)) / (2 * a);
                    textField.setText("the anser is " + root1 +" "+ root2);
                }

            }
        }
