
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Aron
 */
public class PhoneAFriend extends LifeLine{
    private HashMap<String, Double> friends;
    private int uses;
    private DBManager db;
    
    public PhoneAFriend()
    {
        db = new DBManager();
        
        this.friends = db.getFriends();
        this.resetUses();
    }
    
    /* 
     * resets number of uses available
     * params: none
     * returns: none
     */
    @Override
    public void resetUses() {
        this.uses = 3;
    }
    
    /* 
     * creates and prints random line of dialogue for guess
     * params: friend's guess
     * returns: none
     */
    private void speak(int guess)
    {
        Random rand = new Random();
        
        System.out.println("  PHONE : You need help with this question? Hmm...");
        System.out.print("  PHONE : ");
        
        int dialogue = rand.nextInt(4);
        switch (dialogue) {
            case 0 -> System.out.print("It might be ");
            case 1 -> System.out.print("I think it's ");
            case 2 -> System.out.print("It could be ");
            case 3 -> System.out.print("I'm guessing ");
        }
            
        System.out.print(Question.getIDENTIFIERS()[guess]);

        dialogue = rand.nextInt(4);
        switch (dialogue) {
            case 0 -> System.out.print("!");
            case 1 -> System.out.print( "...");
            case 2 -> System.out.print(". Hope I'm right!");
            case 3 -> System.out.print("?");
        }
        
        System.out.print("\n");
    }
    
    /* 
     * prints all uncalled friends, and queries for which friend to call
     * params: none
     * returns: none
     */
    private double selectFriend()
    {      
        System.out.println("Here are your friends available to call:");
        for (Map.Entry<String, Double> entry : friends.entrySet())
        {
            System.out.println("" + entry.getKey());
        }
        
        String friend = queryInput();
        System.out.println("Ringing....");
        
        double out = friends.get(friend);
        //removes friend once called
        friends.remove(friend);
        
        return out;
    }
    
    /* 
     * prompts and validates selected friend is valid
     * params: none
     * returns: selected friend
     */
    public String queryInput() {
        Scanner scan = new Scanner(System.in);
        String friend = "";
        
        while (!friends.containsKey(friend))
        {
            System.out.println("Please input who you'd like to call.");
            friend = TextUtils.titleCase(scan.nextLine());
        }
        
        return friend;
    }
    
    @Override
    public boolean isUsed() {
        return (!(uses > 0));
    }

    /* 
     * selects friend, and presents friend guess based onchance check
     * params: correct answer
     * returns: none
     */
    @Override
    public void use(int cAnswer)
    {
        Random rand = new Random();
        int guess;
        
        double chance = rand.nextDouble();
        
        //if friend's correct chance exceeds randomly generated chance, gives correct answer
        if (selectFriend() > chance){
            guess = cAnswer;
        }
        else {
            do {
                guess = rand.nextInt(Question.getANSQTY());
            } while (guess == cAnswer);
        }
        
        speak(guess);
        
        uses--;
    }
    
    @Override
    public String toString()
    {
        return "Phone A Friend\nUses Left: " + uses;
    }

    
    
}
