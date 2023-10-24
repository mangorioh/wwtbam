
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class GamePanel extends JPanel implements ActionListener{

    JPanel mainPanel, promptPanel, lifelinePanel, optionPanel, audiencePanel, infoPanel;
    JLabel prompt;
    JButton[] lifelineButtons, optionButtons;
    Game2 game;
    
    public GamePanel(LifeLine[] lifelines)
    {
        game = new Game2();
        
        mainPanel =  new JPanel(new BorderLayout(10, 10));
        
        promptPanel = new JPanel();
        promptPanel.setBackground(Color.red);
        promptPanel.setPreferredSize(new Dimension(120, 120));
        prompt = new JLabel("prompt goes here");
        prompt.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 16));
        prompt.setPreferredSize(new Dimension(350, 120));
        promptPanel.add(prompt);
        
        lifelinePanel = new JPanel(new GridLayout(lifelines.length, 1, 10, 10));
        lifelinePanel.setBackground(Color.BLUE);
        lifelinePanel.setPreferredSize(new Dimension(120, 120));
        lifelineButtons = new JButton[lifelines.length];
        System.out.println(lifelines.length);
        for (int i = 0; i < lifelines.length; i++)
        {
            lifelineButtons[i] = new JButton();
            String text = "<html>" + lifelines[i].toString().replaceAll("\n", "<br>") + "</html>";
            lifelineButtons[i].setText(text);
            lifelineButtons[i].addActionListener(this);
            lifelineButtons[i].setFocusable(false);
            lifelineButtons[i].setSize(120, 50);
            lifelinePanel.add(lifelineButtons[i]);
        }
        
        optionPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        optionPanel.setBackground(Color.green);
        optionPanel.setPreferredSize(new Dimension(120, 120));
        optionButtons = new JButton[4];
        for (int i = 0; i < optionButtons.length; i++) 
        {
            optionButtons[i] = new JButton();
            optionButtons[i].setText("TO COME");
            optionButtons[i].addActionListener(this);
            optionButtons[i].setFocusable(false);
            optionButtons[i].setSize(250, 50);
            optionPanel.add(optionButtons[i]);
        }
        
        audiencePanel = new JPanel(new GridLayout(4, 1, 10, 10));
        audiencePanel.setBackground(Color.YELLOW);
        audiencePanel.setPreferredSize(new Dimension(120, 120));
        JLabel[] votes = new JLabel[4];        
        for (int i = 0; i < votes.length; i++)
        {
            votes[i] = new JLabel();
            votes[i].setText("shh");
            votes[i].setFocusable(false);
            votes[i].setSize(100, 50);
            audiencePanel.add(votes[i]);
        }
        
        //calls, continues, scores,
        infoPanel = new JPanel();
        infoPanel.setBackground(Color.orange);
        infoPanel.setPreferredSize(new Dimension(120, 120));
        
        
        
        mainPanel.add(promptPanel, BorderLayout.NORTH);
        mainPanel.add(lifelinePanel, BorderLayout.WEST);
        mainPanel.add(audiencePanel, BorderLayout.EAST);
        mainPanel.add(infoPanel, BorderLayout.SOUTH);
        mainPanel.add(optionPanel, BorderLayout.CENTER);
        
        game.nextQuestion();
        updateQuestion();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        for (int i = 0; i < optionButtons.length; i++)
        {
            if (e.getSource() == optionButtons[i])
            {
                if (game.submitAnswer(i))
                {
                    game.nextQuestion();
                }
                updateQuestion();
            }
        }
    }
    
    private void updateQuestion()
    {
        if (game.getPlaying())
        {
            Question q = game.getCurrentQuestion();

            prompt.setText("<html>" + q.getPrompt() + "</html>");

            for(int i = 0; i < optionButtons.length; i++)
            {
                optionButtons[i].setText(q.getAnswers()[i]);
            }
        }
        else
        {
            for(int i = 0; i < optionButtons.length; i++)
            {
                optionButtons[i].setEnabled(false);
            }
        }
    }
    
    public JPanel getPanel()
    {
        return mainPanel;
    }
}
