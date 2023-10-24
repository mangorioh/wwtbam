
import java.util.Scanner;


public class Question {
    private final String prompt;
    private final String[] answers;
    private final int correctAnswer;
    private static final int ANS_QTY = 4;
    private static final char[] IDENTIFIERS = {'A', 'B', 'C', 'D'};
    
    public Question(String prompt, int cAnswer, String[] answers){
        this.prompt = prompt;
        this.correctAnswer = cAnswer;
        this.answers = answers;
    }
    
    public Question(Question q)
    {
        this.prompt = q.prompt;
        this.correctAnswer = q.correctAnswer;
        this.answers = q.answers;
    }
    
    /* 
     * params: none
     * returns: question prompt
     */
    public String getPrompt()
    {
        return prompt;
    }
    
    /* 
     * params: none
     * returns: question answers
     */
    public String[] getAnswers()
    {
        return answers;
    }
    
    /* 
     * params: none
     * returns: question correct answer
     */
    public int getCorrectAnswer()
    {
        return correctAnswer;
    }
    
    /* 
     * params: none
     * returns: num of answers in question
     */
    public static int getANSQTY()
    {
        return ANS_QTY;
    }

    /* 
     * params: none
     * returns: question identifiers
     */
    public static char[] getIDENTIFIERS()
    {
        //limit to ansqty passed through
        return IDENTIFIERS;
    }
    
    /* 
     * params: user choice
     * returns: boolean if correct answer matches user choice
     */
    public boolean checkAnswer(int choice)
    {
        return (correctAnswer == choice);
    }
    
    /* 
     * translates user choice to index equivalent in identifiers
     * params: user choice
     * returns: respective index
     */
    public static int choiceToIndex(char choice)
    {
        for (int i = 0; i < IDENTIFIERS.length; i++)
        {
            if (choice == IDENTIFIERS[i])
            {
                return i;
            }
        }
        
        //can never occur
        return -1;
    }
    
    /* 
     * iterates through and prints prompt from available identifier options
     * params: none
     * returns: none
     */
    public static void questionPrompt()
    {
        System.out.print("Please input the correct answer, from the options ");
            for (int i = 0; i < ANS_QTY; i++)
            {
                System.out.print(i == (ANS_QTY - 1) ? "or " : "");
                System.out.print(IDENTIFIERS[i]);
                System.out.print(i < (ANS_QTY - 2) ? ", " : " ");
            }
            System.out.println("");
    }
    
    /* 
     * dedicated method to create new questions for database
     * minimises human error in manually adding questions to file
     * params: none
     * returns: new Question object
     */
    public static Question createQuestion()
    {
        Scanner scan = new Scanner(System.in);
        
        System.out.println("Input question prompt:");
        String promptOut = scan.nextLine();
        
        String[] answersOut = new String[ANS_QTY];
        for (int i = 0; i < ANS_QTY; i++)
        {
            System.out.println("Please input Answer option " + IDENTIFIERS[i]);
            answersOut[i] = scan.nextLine();
        }
        
        String charOut;
        do
        {
            Question.questionPrompt();
            charOut = scan.nextLine().toUpperCase();
        } while (!TextUtils.validateChoice(charOut, new String(IDENTIFIERS)));
        
        int cAnsOut = 0; 
        for (int i = 0; i < ANS_QTY; i++)
        {
            if (charOut.charAt(0) == IDENTIFIERS[i])
            {
                cAnsOut = i;
            }
        }
        
        return new Question(promptOut, cAnsOut, answersOut);
    }
    
    @Override
    public String toString()
    {
        String out = "";
        
        out += prompt + "\n";
        
        for (int i = 0; i < ANS_QTY; i++)
        {
            out += "   " + IDENTIFIERS[i] + ". " + answers[i] + "\n";
        }
        
        return out;
    }
}