
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game {
    private static final int[] PRIZES = {0, 500, 1000, 2000, 5000, 10000, 20000, 50000, 75000, 150000, 250000, 500000, 1000000};
    private int prize = 0;
    private LifeLine[] lifelines;
    private final int GAME_LENGTH = 12;
    private final int SAFETY_INTERVAL = 4;
    private Question currentQuestion;
    private ArrayList<Question> questionPool;
    private boolean playing = true;
    
    private DBManager db;
    
    public Game()
    {
        this.db = new DBManager();
        
        questionPool = db.getQuestions();
        
        //initialises lifelines
        lifelines = new LifeLine[3];
        lifelines[0] = new AskTheAudience();
        lifelines[1] = new FiftyFifty();
        lifelines[2] = new PhoneAFriend();
    }

    /* 
     * resets all variables required for a new game
     * params: correct answer
     * returns: array of integers that sum to 100
     */
    private void resetGameState()
    {
        this.prize = 0;
        this.questionPool = db.getQuestions();
        this.playing = true;
        
        for (LifeLine lifeline : lifelines) {
            lifeline.resetUses();
        }
    }
    
    /* 
     * builds a new User object based on a 3 letter input and their current score
     * params: none
     * returns: User object
     */
    public void recordScore(String user)
    {
        db.addScore(new User(user.toUpperCase(), PRIZES[prize]));
    }
    
    public boolean submitAnswer(int i)
    {
        if (currentQuestion.checkAnswer(i))
        {
            prize++;
            if (prize == GAME_LENGTH){
                playing = false;
            }     
            
            return true;
        }
        else
        {
            this.playing = false;
            if (prize < SAFETY_INTERVAL) {
                prize = 0;
            }
            else {
                prize -= (prize % SAFETY_INTERVAL);
            }
            return false;
        }
    }

    public LifeLine[] getLifelines(){
        return lifelines;
    }
    
    public boolean getPlaying()
    {
        return playing;
    }
    
    public int getScore()
    {
        return PRIZES[prize];
    }
    
    public Question getCurrentQuestion()
    {
        return currentQuestion;
    }
    
    public void nextQuestion()
    {
        Random rand = new Random();
        
        int q = rand.nextInt(questionPool.size());
        currentQuestion = questionPool.get(q);
        questionPool.remove(q);
    }
    
}
