
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Menu {
    private final String ADMIN = "pass";
    
    /* 
     * runs game, records game events, and records user score
     * params: none
     * returns: none
     */
    private void printMenu()
    {
        String[] options = {
            "Play Game",
            "View Top 10 Scores!",
            "Add Question (password required)",
            "End Game" };
        String[] optionIDs = { "A", "B", "C", "D" };
        
        for (int i = 0; i < 4; i++)
        {
            System.out.println(" " + optionIDs[i] + ". " + options[i]);
        }
        
    }
    
    /* 
     * wwtbam main menu, allows user to begin gameplay or add questions
     * params: none
     * returns: none
     */
    public void run() throws FileNotFoundException, IOException
    {
        Scanner scan = new Scanner(System.in);
        Game game = new Game();
        String choice = "";
        String pass = "";
        boolean running = true;
        
//        System.out.println(FileIO.readLogo());
        
        do
        {
            printMenu();
            
            choice = scan.nextLine().toUpperCase();
            TextUtils.validateChoice(choice, "ABCD");
            
            System.out.println("Please select an option!");
            switch (choice) {
                case ("A"):
                    if (!game.minQuestions())
                    {
                        System.out.println("Not enough questions to host a game...");
                    }
                    else
                    {
                        game.play();
                    }
                    //FileIO.writeScores(game.getUserScores());
                    break;
                case ("B"):
                    System.out.println(game.getUserScores().getTopTen());
                    break;
                case ("C"):
                    //user only has to input correct password once
                    if (!pass.equals(ADMIN))
                    {
                        System.out.println("Please enter admin password:");
                        pass = scan.nextLine();
                        
                        if (pass.equals(ADMIN))
                        {
                            System.out.println("Password Accepted. Admin Mode Enabled.");
                            game.addQuestion(Question.createQuestion());
                        }
                        else
                        {
                            System.out.println("Incorrect Password");
                        }
                    }
                    else
                    {
                        game.addQuestion(Question.createQuestion());
                    }
                    //FileIO.writeQuestions(game.getQuestions());
                    break;
                case ("D"):
                    System.out.println("Thank you for playing!");
                    running = false;
                    break;
                default:
                    System.out.println("Please input one of the available options!");
            }
            
        } while (running);
    }
}
