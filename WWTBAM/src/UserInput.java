
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UserInput extends JFrame {
    private JTextField userField;
    private JButton confirmButton;
    private JLabel requirements;

    public UserInput(ActionListener actionListener) {
        setTitle("Record Your Score");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        requirements = new JLabel("Please input your User ID (3 characters, letters only)");
        panel.add(requirements);
        
        userField = new JTextField("");
        userField.setPreferredSize(new Dimension(200, 30));
        panel.add(userField);

        confirmButton = new JButton("Confirm Choice");
        confirmButton.setPreferredSize(new Dimension(150, 30));
        panel.add(confirmButton);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = userField.getText();
                if (userInput.matches("^[a-zA-Z]{3}$"))
                {
                    actionListener.actionPerformed(new ActionEvent(userInput, ActionEvent.ACTION_PERFORMED, userInput));
                    dispose();
                }
                else
                {
                    JOptionPane.showMessageDialog(UserInput.this,
                            "Invalid input. Please enter a 3-character string with letters only.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                
            }
        });

        add(panel);
        pack();
        setLocationRelativeTo(null); 
    }
    
}
