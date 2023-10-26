
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class QuestionBuilder extends JFrame {

    private JTextField promptField;
    private JTextField[] answerFields;
    private JRadioButton[] correctRadioButtons;
    private ButtonGroup radioGroup;

    public QuestionBuilder() {
        setTitle("Question Builder");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new GridLayout(6, 1));

        JLabel promptLabel = new JLabel("Enter the question prompt:");
        promptField = new JTextField(20);

        JLabel answerLabel = new JLabel("Enter possible answers and check the correct ones:");
        answerFields = new JTextField[4];
        correctRadioButtons = new JRadioButton[4];
        radioGroup = new ButtonGroup();
        
        JPanel answersPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        for (int i = 0; i < 4; i++) {
            JPanel answerPanel = new JPanel();
            answerFields[i] = new JTextField(15);
            correctRadioButtons[i] = new JRadioButton("Correct");
            answerPanel.add(answerFields[i]);
            answerPanel.add(correctRadioButtons[i]);
            answersPanel.add(answerPanel);
            radioGroup.add(correctRadioButtons[i]);
        }

        JButton createQuestionButton = new JButton("Create Question");
        createQuestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buildQuestion();
            }
        });

        add(promptLabel);
        add(promptField);
        add(answerLabel);
        add(answersPanel);
        
        add(createQuestionButton);
        setVisible(true);
    }
    
    private void buildQuestion()
    {
        String[] answers = new String[4];
        int correctAnswerIndex = -1;
        
        for (int i = 0; i < 4; i++) {
            answers[i] = answerFields[i].getText();
            if (correctRadioButtons[i].isSelected()) {
                correctAnswerIndex = i;
            }
        }
        
        System.out.println(new Question(promptField.getText(), correctAnswerIndex, answers));
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuestionBuilder());
    }
}
