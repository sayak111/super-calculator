import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Main extends JFrame implements ActionListener{
    JLabel label;
    JButton[] buttons;
    Main()
    {
        setSize(500, 500);
        buttons = new JButton[]{new JButton("normal calculator"),new JButton("Trinnom mode")};
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(9, 4));

        for (JButton button : buttons) {
            button.addActionListener(this);
            buttonPanel.add(button);
        }
        label = new JLabel("enter mode please");
        add(label);
        add(buttonPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        String buttonText = source.getText();
        switch (buttonText) {
            case "normal calculator":
                this.setVisible(false);
                CalculatorApp c = new CalculatorApp();
                c.setVisible(true);
                break;
            case "Trinnom mode":
                this.setVisible(false);
                TrinomApp t = new TrinomApp();
                t.setVisible(true);
                break;
            default:
                break;
        }
    }

    public static void main(String[] args) {
         SwingUtilities.invokeLater(() -> {
            Main menu = new Main();
            menu.setVisible(true);
        });
    }
}