
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