
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
    private ArrayList<Question> questions;
    private UserScores scores;
    private LifeLine[] lifelines;
    private LogProfile log;
    private final int GAME_LENGTH = 12;
    private final int SAFETY_INTERVAL = 4;
    
    private DBManager db;
    
    public Game()
    {
        this.db = new DBManager();

        //sets up and initialises question pool and past user scores
        this.questions = db.getQuestions();
        this.scores = new UserScores();
        
        if (!minQuestions())
        {
            System.out.println("  ADMIN MSG : Warning! Not enough questions to run a game! " + (GAME_LENGTH - this.questions.size()) + " more needed!");
        }
        
        //initialises lifelines
        lifelines = new LifeLine[3];
        lifelines[0] = new AskTheAudience();
        lifelines[1] = new FiftyFifty();
        lifelines[2] = new PhoneAFriend();
        
        this.log = new LogProfile();
        
    }

    /* 
     * resets all variables required for a new game
     * params: correct answer
     * returns: array of integers that sum to 100
     */
    private void resetGameState()
    {
        this.prize = 0;
        this.log.resetLog();
        
        for (LifeLine lifeline : lifelines) {
            lifeline.resetUses();
        }
    }
    
    /* 
     * params: none
     * returns: game's UserScores
     */
    public UserScores getUserScores()
    {
        return scores;
    }
    
    /* 
     * params: cnone
     * returns: game's Questions
     */
    public ArrayList<Question> getQuestions()
    {
        return questions;
    }
    
    /* 
     * adds parameter question object to current question pool
     * params: Question object
     * returns: none
     */
    public void addQuestion(Question q)
    {
        db.addQuestion(q);
        questions.add(q);
    }
    
    /* 
     * checks if there are enough questions to host a game
     * params: none
     * returns: boolean on if minimum questions is met
     */
    public boolean minQuestions()
    {
        return (this.questions.size() >= GAME_LENGTH);
    }
    
    /* 
     * builds a new User object based on a 3 letter input and their current score
     * params: none
     * returns: User object
     */
    private User recordScore()
    {
        Scanner scan = new Scanner(System.in);
        
        String id;
        do 
        {
            System.out.println("Please input a User ID (3 characters, letters only)");
            id = scan.nextLine().toUpperCase();
        } while (id.length() != 3 || !id.matches("[A-Z]+"));
        
        return new User(id, PRIZES[prize]);
    }
    
    /* 
     * prints available lifelines, prompts for and validates input
     * validates if choice is usable, then uses selected lifeline
     * params: correct answer
     * returns: none
     */
    private void useLifeLine(int correctAnswer)
    {
        Scanner scan = new Scanner(System.in);
        String choice = "";
        
        for (int i = 0; i < lifelines.length; i++)
        {
            System.out.print(Question.getIDENTIFIERS()[i] + ". " + lifelines[i] + "  : ");

            if (lifelines[i].isUsed()){
                System.out.print("UN");
            }
            System.out.print("AVAILABLE\n");
        }
        
        boolean running = true;
        while (running)
        {
            System.out.println("Please select your lifeline from the available options:");
            choice = scan.nextLine().toUpperCase();
            
            //creates input whitelist based on question identifiers and number of answers
            String validInputs =  new String(Arrays.copyOfRange(Question.getIDENTIFIERS(), 0, lifelines.length));
            if (TextUtils.validateChoice(choice, validInputs))
            {
                if (lifelines[Question.choiceToIndex(choice.charAt(0))].isUsed()) {
                    System.out.println("Lifeline is unavailable!");
                }
                else {
                    running = false;
                }
            }
        }
        
        lifelines[Question.choiceToIndex(choice.charAt(0))].use(correctAnswer);
    }
    
    /* 
     * prints available options, and allows user to use lifeline
     * params: correct answer
     * returns: user's choice of answer
     */
    private char answerPhase(int correctAnswer)
    {
        Scanner scan = new Scanner(System.in);
        boolean running = true;
        boolean lifelineUsed = false;
        String choice = "";
        //creates input whitelist based on question identifiers and number of answers, plus X for lifeline use 
        String validInputs =  "X" + new String(Arrays.copyOfRange(Question.getIDENTIFIERS(), 0, Question.getANSQTY()));
   
        do {
            Question.questionPrompt();
            if (!lifelineUsed) {
                System.out.println("Or input 'X' to use a Lifeline!");
            }
            choice = scan.nextLine().toUpperCase();
            if (TextUtils.validateChoice(choice, validInputs))
            {
                if (choice.equals("X"))
                {
                    if (!lifelineUsed) {
                        useLifeLine(correctAnswer);
                        lifelineUsed = true;
                    }
                    else {
                        System.out.println("You've already used a lifeline this round!");
                    }
                }
                else
                {
                    running = false;
                }
            }
        } while (running);
        
        return choice.charAt(0);
    }
    
    /* 
     * runs game, records game events, and records user score
     * params: none
     * returns: none
     */
    public void play() throws FileNotFoundException          
    {
        Scanner scan = new Scanner(System.in);
        Random rand = new Random();
        //creates new question pool for use in removing played questions
        ArrayList<Question> questionPool = new ArrayList<>(questions);
        resetGameState();
        boolean running = true;
        
        log.addEvent("Game start.");
        
        while (running)
        {
            for (int i = 1; i <= GAME_LENGTH; i++)
            {
                System.out.println("You've earned $" + PRIZES[prize] + " so far.");
                if (i == GAME_LENGTH)
                {
                    System.out.println("You've reached the last question, for the grand prize of $" + PRIZES[GAME_LENGTH] + "!");
                }
                System.out.println("Question " + i + "...");
                
                //randomly selects available questions, then prints to console + writes to file
                int q = rand.nextInt(questionPool.size());
                System.out.println(questionPool.get(q));
                log.addEvent(questionPool.get(q).toString());
                
                char choice = answerPhase(questionPool.get(q).getCorrectAnswer());
                log.addEvent("User picks: " + choice);
                
                if (questionPool.get(q).checkAnswer(choice))
                {
                    prize++;
                    System.out.println("That's correct! You've now earned $"+ PRIZES[prize]);
                    log.addEvent("Correct. Prize elevated to " + PRIZES[prize]);
                    if (i % SAFETY_INTERVAL == 0 && i != GAME_LENGTH)
                    {
                        System.out.println("You have also reached a new safety net for your earnings!");
                        System.out.println("If you get a question wrong, you can still go home with $" + PRIZES[prize] + "!");
                    }
                    
                    if (i == GAME_LENGTH)
                    {
                        System.out.println("You've answered all " + GAME_LENGTH + " questions correctly and won the grand prize!");
                        running = false;
                    }
                    else
                    {
                        String cont;
                        do {
                            System.out.println("Would you like to continue for more? Or stop and cash in your earnings? (Input 1 to continue, 2 to stop)");
                            cont = scan.nextLine();
                        } while ( !TextUtils.validateChoice(cont, "12") );

                        if (cont.equals("2")) {
                            log.addEvent("Game stopped. Prize cashed in.");
                            running = false;
                            break;
                        }
                        
                        log.addEvent("Game continued. ");
                    }
                }
                else
                {
                    System.out.println("That is incorrect!");
                    log.addEvent("Incorrect.");
                    
                    if (prize < SAFETY_INTERVAL) {
                        prize = 0;
                    }
                    else {
                        log.addEvent("Safety Net used.");
                        prize -= (prize % SAFETY_INTERVAL);
                    }
                    
                    log.addEvent("Prize lowered to " + PRIZES[prize]);
                    running = false;
                    break;
                }
                
                //removes question from game pool
                questionPool.remove(q);
            }
        }
        
        log.addEvent("Game end.");
        System.out.println("And that ends our game! Let's have you record your score now.");
        log.setUser(scores.addScore(recordScore()));
        
//        FileIO.writeLog(log.toString());
    }
    
}
