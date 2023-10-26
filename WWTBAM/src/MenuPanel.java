
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MenuPanel extends JPanel implements ActionListener{
    
    JPanel centrePanel;
    JButton[] menuOptions;
    private final String ADMIN = "pass";
    private boolean adminMode = false;

    public MenuPanel() {
        setLayout(new BorderLayout());
        
        JLabel title = new JLabel("Who Wants to be a Millionaire!");
        title.setPreferredSize(new Dimension(400, 200));
        title.setVerticalAlignment(JLabel.CENTER);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 30));
        add(title, BorderLayout.NORTH);
        
        JPanel centreContainer = new JPanel(new FlowLayout());
        centrePanel = new JPanel(new GridLayout(5, 1, 10, 10));
        menuOptions = new JButton[5];
        String[] menuOptionsText = new String[] { "Start Game", "View Leaderboard", "Add Question", "Add Friend", "Exit" };
        
        for (int i = 0; i < menuOptions.length; i++)
        {
            menuOptions[i] = new JButton();
            menuOptions[i].setText(menuOptionsText[i]);
            menuOptions[i].addActionListener(this);
            menuOptions[i].setFocusable(false);
            menuOptions[i].setPreferredSize(new Dimension(220, 50));
            centrePanel.setSize(new Dimension(300, 300));
            centrePanel.add(menuOptions[i]);
        }
        
        centreContainer.add(centrePanel);
        add(centreContainer, BorderLayout.CENTER);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < menuOptions.length; i++)
        {
            if (e.getSource() == menuOptions[i])
            {
                switch (i) {
                case 0: // Start Game
                    handleStartGame();
                    break;
                case 1: // View Leaderboard
                    UserScores scores = new UserScores();
                    JOptionPane.showMessageDialog(null, scores.getTopTen(), "Leaderboard", JOptionPane.PLAIN_MESSAGE);
                    break;
                case 2: // Add Question
                    if(checkAdmin())
                    {
                        addQuestion();
                    }
                    break;
                case 3: // Add Friend
                    if (checkAdmin())
                    {
                        addFriend();
                    }
                    break;
                case 4: // Exit
                    System.exit(0);
                    break;
                }
            }
        }
    }
    
    private boolean checkAdmin()
    {
        if (adminMode)
        {
            return true;
        }
        
        String userPass = JOptionPane.showInputDialog(this, "Please input admin password:", "Password Input",  JOptionPane.QUESTION_MESSAGE);

        if (userPass != null && userPass.equals(ADMIN)) {
            JOptionPane.showMessageDialog(this, "Password Correct. Admin Mode Enabled.", "Password Success", JOptionPane.PLAIN_MESSAGE);
            adminMode = true;
        }
        else {
            JOptionPane.showMessageDialog(this, "Incorrect Password. Please try again.", "Incorrect Password", JOptionPane.WARNING_MESSAGE);
        }
        
        return adminMode;
    }
    
    private void addQuestion()
    {
        for (int i = 0; i < menuOptions.length; i++) {
            menuOptions[i].setEnabled(false);
        }

        JDialog dialog = new JDialog(); // Create a new dialog
        QuestionBuilder qb = new QuestionBuilder();
        dialog.getContentPane().add(qb); // Add the QuestionBuilder panel to the dialog
        dialog.pack();
        dialog.setTitle("Question Builder");
        dialog.setSize(450, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
        dialog.setModal(true); // Set the dialog to be modal
        dialog.setVisible(true); // Show the dialog and block further code execution until it's closed
        

        // Continue to "stage 2" after the dialog is closed
        for (int i = 0; i < menuOptions.length; i++) {
            menuOptions[i].setEnabled(true);
        }
    }
    
    private void addFriend()
    {
        for (int i = 0; i < menuOptions.length; i++) {
            menuOptions[i].setEnabled(false);
        }

        JDialog dialog = new JDialog(); // Create a new dialog
        FriendBuilder fb = new FriendBuilder();
        dialog.getContentPane().add(fb); // Add the QuestionBuilder panel to the dialog
        dialog.pack();
        dialog.setTitle("Question Builder");
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
        dialog.setModal(true); // Set the dialog to be modal
        dialog.setVisible(true); // Show the dialog and block further code execution until it's closed
        

        // Continue to "stage 2" after the dialog is closed
        for (int i = 0; i < menuOptions.length; i++) {
            menuOptions[i].setEnabled(true);
        }
    }
    
    private void handleStartGame()
    {
        GamePanel gamePanel = new GamePanel();

        JFrame topFrame = (JFrame) getTopLevelAncestor();
        topFrame.getContentPane().remove(this);
        topFrame.getContentPane().add(gamePanel);
        topFrame.revalidate();
        topFrame.repaint();
    }
}
