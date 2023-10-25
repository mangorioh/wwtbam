
import java.io.IOException;
import java.util.ArrayList;
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
    private String friend;
    
    public PhoneAFriend()
    {
        db = new DBManager();
        
        this.friends = db.getFriends();
        this.resetUses();
        this.friend = "";
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
    public String getSpeak(Question currentQuestion)
    {
        Random rand = new Random();
               
        int guess;
        double chance = rand.nextDouble();
        
        System.out.println(friend);
        
        if (friends.get(friend) > chance){
            guess = currentQuestion.getCorrectAnswer();
        }
        else {
            do {
                guess = rand.nextInt(Question.getANSQTY());
            } while (guess == currentQuestion.getCorrectAnswer());
        }
        
        String out = "";
        out += "\nPHONE : Hi, this is " + friend + ". Oh, you need help with this question? Hmm...";
        out += "\n  PHONE : ";
        
        int dialogue = rand.nextInt(4);
        switch (dialogue) {
            case 0 -> out += "It might be ";
            case 1 -> out += "I think it's ";
            case 2 -> out += "It could be ";
            case 3 -> out += "I'm guessing ";
        }
            
        out += currentQuestion.getAnswers()[guess];

        dialogue = rand.nextInt(4);
        switch (dialogue) {
            case 0 -> out += "!";
            case 1 -> out +=  "...";
            case 2 -> out += ". Hope I'm right!";
            case 3 -> out += "?";
        }
        
        //removes friend once selected
        friends.remove(friend);
        
        return out;
    }
    
    public String[] getFriends()
    {
        ArrayList<String> friendNames = new ArrayList<>();

        for (String friendName : friends.keySet()) {
            friendNames.add(friendName);
        }

        return friendNames.toArray(new String[0]);
    }
    
    /* 
     * prints all uncalled friends, and queries for which friend to call
     * params: none
     * returns: none
     */
    public void selectFriend(String friend)
    {      
        this.friend = friend;
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
    public void use()
    {
        uses--;
    }
    
    @Override
    public String toString()
    {
        return "Phone A Friend\nUses Left: " + uses;
    }
    
}
