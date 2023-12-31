
import java.util.Random;

public class AskTheAudience extends LifeLine{
    private int uses;
    
    public AskTheAudience()
    {
        this.resetUses();
    }
    
    /* 
     * resets number of uses available
     * params: none
     * returns: none
     */
    @Override
    public void resetUses() {
        this.uses = 2;
    }
    
    /* 
     * generates a poll of results for the correct answer
     * weighted towards the correct answer, but audience may still be wrong
     * params: correct answer
     * returns: array of integers that sum to 100
     */
    public int[] getHint(int correctAnswer)
    {
        Random rand = new Random();
        
        int[] votes = new int[Question.getANSQTY()];
        int sum = 0;
        
        while (sum < 100)
        {          
            for (int i = 0; i < Question.getANSQTY(); i++) 
            {
                int add;
                
                if (votes[i] <= 23) {
                    if (i == correctAnswer) {
                        add = rand.nextInt(Math.min(16, (101 - sum)));
                    }
                    else {
                        add = rand.nextInt(Math.min(8, (101 - sum)));
                    }
                    votes[i] += add;
                    sum += add;
                }
                
                if (sum == 100) {
                    break;
                }
            }
        } 
        
        return votes;
    }
    
    /* 
     * override abstract method
     * params: none
     * returns: boolean of if lifeline has uses left
     */
    @Override
    public boolean isUsed()
    {
        return (!(uses > 0));
    }

    /* 
     * detracts available uses
     * params: none
     * returns: none
     */
    @Override
    public void use()
    {
        uses--;
    }
    
    /* 
     * params: none
     * returns: name of lifeline and number of uses left
     */
    public String toString()
    {
        return "Ask the Audience\nUses Left: " + uses;
    }
    
}
