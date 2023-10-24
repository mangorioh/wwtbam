
import java.util.Random;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Aron
 */
public class FiftyFifty extends LifeLine{
    boolean used = false;
    
    /* 
     * params: none
     * returns: boolean on if lifeline is usable
     */
    @Override
    public boolean isUsed() {
        return used;
    }

    /* 
     * takes correct answer, and generates another (incorrect) answer
     * offers user both options, still maintaining original identifier order
     * params: correct answer
     * returns: none
     */
    @Override
    public void use(int correctAnswer) {
        Random rand = new Random();

        System.out.println("The answers have been narrowed down to...");
        
        int wrongAnswer;
        do {
            wrongAnswer = rand.nextInt(Question.getANSQTY());           
        } while (wrongAnswer == correctAnswer);
        
        if (wrongAnswer < correctAnswer) {
            System.out.println(Question.getIDENTIFIERS()[wrongAnswer] + " or " + Question.getIDENTIFIERS()[correctAnswer]);
        }
        else {
            System.out.println(Question.getIDENTIFIERS()[correctAnswer] + " or " + Question.getIDENTIFIERS()[wrongAnswer]);
        }
        
        used = true;
    }
    
    /* 
     * params: none
     * returns: resets used to false
     */
    @Override
    public void resetUses() {
        this.used = false;
    }

    public String toString()
    {
        return "Fifty Fifty\nSingle Use";
    }
    
}
