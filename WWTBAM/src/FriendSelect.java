import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FriendSelect extends JFrame {
    private JComboBox<String> comboBox;
    private JButton confirmButton;

    public FriendSelect(String[] options, ActionListener actionListener) {
        setTitle("Phone A Friend!");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        comboBox = new JComboBox<>(options);
        comboBox.setPreferredSize(new Dimension(200, 30));
        panel.add(comboBox);

        confirmButton = new JButton("Confirm Choice");
        confirmButton.setPreferredSize(new Dimension(150, 30));
        panel.add(confirmButton);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) comboBox.getSelectedItem();
                actionListener.actionPerformed(new ActionEvent(selectedOption, ActionEvent.ACTION_PERFORMED, selectedOption));
                dispose();
            }
        });

        add(panel);
        pack();
        setLocationRelativeTo(null); 
    }
}
