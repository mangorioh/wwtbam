
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class QuestionBuilder extends JPanel {

    private JTextField promptField;
    private JTextField[] answerFields;
    private JRadioButton[] correctRadioButtons;
    private ButtonGroup radioGroup;

    public QuestionBuilder() {
        setSize(450, 400);
        setLayout(new BorderLayout());

        // Outer panel with BoxLayout to center components vertically
        JPanel outerContainer = new JPanel();
        outerContainer.setLayout(new BoxLayout(outerContainer, BoxLayout.Y_AXIS));
        outerContainer.add(Box.createRigidArea(new java.awt.Dimension(0, 20)));

        // Panel for the question prompt
        JPanel promptPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel promptLabel = new JLabel("Enter the question prompt:");
        promptField = new JTextField(20);
        promptPanel.add(promptLabel);
        promptPanel.add(promptField);
        outerContainer.add(promptPanel);

        // Panel for answers
        JPanel answersPanel = new JPanel();
        answersPanel.setLayout(new BoxLayout(answersPanel, BoxLayout.Y_AXIS));
        JLabel answerLabel = new JLabel("Enter possible answers and check the correct ones:");
        answerFields = new JTextField[4];
        correctRadioButtons = new JRadioButton[4];
        radioGroup = new ButtonGroup();

        for (int i = 0; i < 4; i++) {
            JPanel answerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            answerFields[i] = new JTextField(15);
            correctRadioButtons[i] = new JRadioButton("Correct");
            answerPanel.add(answerFields[i]);
            answerPanel.add(correctRadioButtons[i]);
            answersPanel.add(answerPanel);
            radioGroup.add(correctRadioButtons[i]);
        }
        
        outerContainer.add(answerLabel);
        outerContainer.add(answersPanel);

        // Panel for the "Create Question" button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton createQuestionButton = new JButton("Add Question");
        createQuestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buildQuestion();
            }
        });
        buttonPanel.add(createQuestionButton);
        outerContainer.add(buttonPanel);

        add(outerContainer, BorderLayout.CENTER);
        setVisible(true);
    }

    private void buildQuestion() {
        String[] answers = new String[4];
        int correctAnswerIndex = -1;

        for (int i = 0; i < 4; i++) {
            answers[i] = answerFields[i].getText();
            if (correctRadioButtons[i].isSelected()) {
                correctAnswerIndex = i;
            }
        }

        DBManager db = new DBManager();
        //db.addQuestion(new Question(promptField.getText(), correctAnswerIndex, answers));
    }
}
