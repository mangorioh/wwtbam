
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class GamePanel extends JPanel implements ActionListener{

    JPanel promptPanel, lifelinePanel, optionPanel, audiencePanel, infoPanel, friendPanel, continuePanel;
    JLabel prompt, info, prizes;
    JLabel[] votes;
    JButton[] lifelineButtons, optionButtons;
    JButton continueButton, endButton;
    Game game;
    LifeLine[] lifelines;
    
    public GamePanel()
    {
        game = new Game();
        this.lifelines = game.getLifelines();
        
        setLayout(new BorderLayout(10, 10));
        
        promptPanel = new JPanel();
        promptPanel.setPreferredSize(new Dimension(120, 120));
        prompt = new JLabel("prompt goes here");
        prompt.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 16));
        prompt.setPreferredSize(new Dimension(350, 120));
        promptPanel.add(prompt);
        
        lifelinePanel = new JPanel(new GridLayout(lifelines.length, 1, 10, 10));
        lifelinePanel.setPreferredSize(new Dimension(120, 120));
        lifelineButtons = new JButton[lifelines.length];
        
        for (int i = 0; i < lifelines.length; i++)
        {
            lifelineButtons[i] = new JButton();
            String text = TextUtils.swingText(lifelines[i].toString());
            lifelineButtons[i].setText(text);
            lifelineButtons[i].addActionListener(this);
            lifelineButtons[i].setFocusable(false);
            lifelineButtons[i].setSize(120, 50);
            lifelinePanel.add(lifelineButtons[i]);
        }
        
        optionPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        optionPanel.setPreferredSize(new Dimension(120, 120));
        optionButtons = new JButton[4];
        for (int i = 0; i < optionButtons.length; i++) 
        {
            optionButtons[i] = new JButton();
            optionButtons[i].setText("");
            optionButtons[i].addActionListener(this);
            optionButtons[i].setFocusable(false);
            optionButtons[i].setSize(250, 50);
            optionPanel.add(optionButtons[i]);
        }
        
        audiencePanel = new JPanel(new GridLayout(4, 1, 10, 10));
        audiencePanel.setPreferredSize(new Dimension(120, 120));
        votes = new JLabel[4];        
        for (int i = 0; i < votes.length; i++)
        {
            votes[i] = new JLabel();
            votes[i].setText("shh");
            votes[i].setFocusable(false);
            votes[i].setSize(100, 50);
            audiencePanel.add(votes[i]);
        }
        
        //calls, continues, scores,
        infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setPreferredSize(new Dimension(500, 120));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        // Create an inner panel for the "info" label
        JPanel infoInnerPanel = new JPanel(new BorderLayout());
        
        JPanel continuePanel = new JPanel(new GridLayout(2, 1, 10, 10));
        continueButton = new JButton("Continue");
        continueButton.addActionListener(this);
        continueButton.setFocusable(false);
        continueButton.setVisible(false);
        continuePanel.add(continueButton);
        
        endButton = new JButton("End");
        endButton.addActionListener(this);
        endButton.setFocusable(false);
        endButton.setVisible(false);
        continuePanel.add(endButton);

        info = new JLabel("test!!!!!");
        info.setHorizontalAlignment(JLabel.CENTER);
        info.setVerticalAlignment(JLabel.CENTER);

        infoInnerPanel.add(info, BorderLayout.CENTER);
        infoInnerPanel.add(continuePanel, BorderLayout.EAST);
        infoPanel.add(infoInnerPanel, gbc);

        JPanel prizesInnerPanel = new JPanel(new BorderLayout());

        prizes = new JLabel("");
        prizes.setHorizontalAlignment(JLabel.CENTER);
        prizes.setVerticalAlignment(JLabel.CENTER);

        prizesInnerPanel.add(prizes, BorderLayout.CENTER);
        infoPanel.add(prizesInnerPanel, gbc);

        infoPanel.getComponent(0).setPreferredSize(new Dimension(infoPanel.getPreferredSize().width * 2 / 3, infoPanel.getPreferredSize().height));
        infoPanel.getComponent(1).setPreferredSize(new Dimension(infoPanel.getPreferredSize().width / 3, infoPanel.getPreferredSize().height));

        add(promptPanel, BorderLayout.NORTH);
        add(lifelinePanel, BorderLayout.WEST);
        add(audiencePanel, BorderLayout.EAST);
        add(infoPanel, BorderLayout.SOUTH);
        add(optionPanel, BorderLayout.CENTER);
        
        game.nextQuestion();
        updateQuestion();
        prizes.setText("Score: " + game.getScore());
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == continueButton)
        {
            info.setText("");
            game.nextQuestion();
            updateQuestion();
            continueButton.setVisible(false);
            endButton.setVisible(false);
        }
        else if (e.getSource() == endButton)
        {
            readUser();
        }
        else
        { 
            for (int i = 0; i < optionButtons.length; i++)
            {
                if (e.getSource() == optionButtons[i])
                {
                    prizes.setText("Score: " + game.getScore());
                    if (game.submitAnswer(i))
                    {
                        lockOptions();
                        lockLifelines();
                        continueButton.setVisible(true);
                        endButton.setVisible(true);
                        info.setText("Correct! Continue?");
                    }
                    else
                    {
                        info.setText("Incorrect! Game Over!");
                        lockOptions();
                        lockLifelines();
                        readUser();
                    }
                }
            }

            for (int i = 0; i < lifelineButtons.length; i++)
            {
                if (e.getSource() == lifelineButtons[i])
                {         
                    for(int j = 0; j < lifelineButtons.length; j++)
                    {
                        lifelineButtons[j].setEnabled(false);
                    }
                    useLifeline(i);
                }
            }
        }
    }
    
    private void useLifeline(int index)
    {
        LifeLine lifeline =  lifelines[index];
        
        lifelines[index].use();
        
        if (lifeline instanceof FiftyFifty fiftyFifty){
            int[] options = fiftyFifty.getHint(game.getCurrentQuestion().getCorrectAnswer());
            
            for (int i = 0; i < optionButtons.length; i++)
            {
                if (i != options[0] && i != options[1])
                {
                    optionButtons[i].setEnabled(false);
                }
            }
        }
        else if (lifeline instanceof AskTheAudience askTheAudience)
        {
            int[] voteNums = askTheAudience.getHint(game.getCurrentQuestion().getCorrectAnswer());
            
            for (int i = 0; i < votes.length; i++)
            {
                votes[i].setText(voteNums[i] + "%");
            }
        }
        else if (lifeline instanceof PhoneAFriend phoneAFriend)
        {         
            FriendSelect comboBoxFrame = new FriendSelect(phoneAFriend.getFriends(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) e.getActionCommand();
                phoneAFriend.selectFriend(selectedOption);
                info.setText(TextUtils.swingText(phoneAFriend.getSpeak(game.getCurrentQuestion())));

                unlockOptions();
            }
            });

            lockOptions();
            
            comboBoxFrame.setVisible(true);
        }
    }
    
    private void updateQuestion()
    {
        if (game.getPlaying())
        {
            Question q = game.getCurrentQuestion();

            prompt.setText(TextUtils.swingText(q.getPrompt()));

            for(int i = 0; i < optionButtons.length; i++)
            {
                optionButtons[i].setText(q.getAnswers()[i]);
            }
            
            //reset all hints
            info.setText("");
            unlockLifelines();
            unlockOptions();
            for (int i = 0; i < votes.length; i++)
            {
                votes[i].setText("");
            }
            for(int i = 0; i < lifelineButtons.length; i++)
            {
                String text = TextUtils.swingText(lifelines[i].toString());
                lifelineButtons[i].setText(text);
            }
        }
        else
        {
            lockOptions();
            lockLifelines();
        }
    }
    
    private void readUser()
    {
        UserInput userInput = new UserInput(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = (String) e.getActionCommand();
            game.recordScore(username);
            returnToMenu();
        }
        });

        continueButton.setEnabled(false);
        endButton.setEnabled(false);
        userInput.setVisible(true);
    }
    
    private void lockOptions()
    {
        for(int i = 0; i < optionButtons.length; i++)
        {
            optionButtons[i].setEnabled(false);
        }
    }
    
    private void lockLifelines()
    {
        for(int i = 0; i < lifelineButtons.length; i++)
        {
            lifelineButtons[i].setEnabled(false);
        }
    }
    
    private void unlockLifelines()
    {
        for(int i = 0; i < lifelineButtons.length; i++)
        {
            if (!lifelines[i].isUsed())
            {
                lifelineButtons[i].setEnabled(true);
            }
        }
    }
    
    private void unlockOptions()
    {
        for(int i = 0; i < optionButtons.length; i++)
        {
            optionButtons[i].setEnabled(true);
        }
    }
    
    public void returnToMenu() {
        // Create a new instance of the MenuPanel
        MenuPanel menuPanel = new MenuPanel();
        
        // Get the top-level ancestor, which should be your JFrame
        JFrame topFrame = (JFrame) getTopLevelAncestor();
        
        // Remove the current GamePanel
        topFrame.getContentPane().remove(this);
        
        // Add the MenuPanel to the JFrame
        topFrame.getContentPane().add(menuPanel);
        
        // Validate and repaint the JFrame to update the UI
        topFrame.revalidate();
        topFrame.repaint();
    }
}
