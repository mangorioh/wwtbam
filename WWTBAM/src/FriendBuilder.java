
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.text.ParseException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class FriendBuilder extends JPanel {
    private JTextField nameInput, chanceInput;
    private JButton createFriendButton;
    
    public FriendBuilder() {
        setSize(350, 80);
        setLayout(new FlowLayout(FlowLayout.LEADING, 20, 20));
        
        JPanel namePanel = new JPanel(new BorderLayout());
        namePanel.setPreferredSize(new Dimension(150, 40));
        nameInput = new JTextField();
        namePanel.add(new JLabel("Name"), BorderLayout.NORTH);
        namePanel.add(nameInput, BorderLayout.CENTER);
        
        JPanel chancePanel = new JPanel(new BorderLayout());
        chancePanel.setPreferredSize(new Dimension(100, 40));
        chanceInput = new JTextField();
        chancePanel.add(new JLabel("Chance"), BorderLayout.NORTH);
        chancePanel.add(chanceInput, BorderLayout.CENTER);
        
        createFriendButton = new JButton("Add Friend");
        createFriendButton.addActionListener((ActionEvent e) -> {
            if (buildFriend())
            {
                SwingUtilities.getWindowAncestor(this).dispose();
            }
        });
        add(namePanel);
        add(chancePanel);
        add(createFriendButton);
    }
    
    private boolean buildFriend()
    {
        String name = TextUtils.titleCase(nameInput.getText());
        if (name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!chanceInput.getText().matches("^\\d+(\\.\\d{1,2})?$")) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number between 0 and 1 (max 2 decimal places).", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        double chance;
        try {
            double parsedValue = Double.parseDouble(chanceInput.getText());
            double roundedValue = Math.round(parsedValue * 100.0) / 100.0; // Round to 2 decimal places
            chance = roundedValue;

            if (chance < 0.0 || chance > 1.0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number between 0 and 1 (max 2 decimal places).", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        DBManager db = new DBManager();
        //db.addFriend(name, chance);
        db.closeConnections();
        
        return true;
    }
    
}
