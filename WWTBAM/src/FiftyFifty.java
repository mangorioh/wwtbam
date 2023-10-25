
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
    public void use() {
        used = true;
    }
    
    public int[] getHint(int correctAnswer)
    {
        Random rand = new Random();
        
        System.out.println("correct" + correctAnswer);
        
        int wrongAnswer;
        do {
            wrongAnswer = rand.nextInt(Question.getANSQTY());           
        } while (wrongAnswer == correctAnswer);
        
        System.out.println("wrong" + wrongAnswer);
        
        return new int[] {correctAnswer, wrongAnswer};
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
