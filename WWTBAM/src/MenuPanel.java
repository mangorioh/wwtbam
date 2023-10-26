
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MenuPanel extends JPanel implements ActionListener{
    
    JPanel centrePanel;
    JButton[] menuOptions;
    JButton startButton, leaderboardButton, addQuestionButton, exitButton;
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
        centrePanel = new JPanel(new GridLayout(4, 1, 10, 10));
        menuOptions = new JButton[4];
        String[] menuOptionsText = new String[] { "Start Game", "View Leaderboard", "Add Question", "Exit" };
        
        for (int i = 0; i < 4; i++)
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
        for (int i = 0; i < 4; i++)
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
                    if (adminMode)
                    {
                        addQuestion();
                    }
                    else
                    {
                        String userPass = JOptionPane.showInputDialog(null, "Please input admin password:", "Password Input",  JOptionPane.QUESTION_MESSAGE);
                        
                        if (userPass.equals(ADMIN))
                        {
                            JOptionPane.showMessageDialog(null, "Password Correct. Admin Mode Enabled.", "Password Success", JOptionPane.PLAIN_MESSAGE);
                            addQuestion();
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Incorrect Password. Please try again.", "Incorrect Password", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                    break;
                case 3: // Exit
                    System.exit(0);
                    break;
                }
            }
        }
    }
    
    private void addQuestion()
    {
        //code here
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
