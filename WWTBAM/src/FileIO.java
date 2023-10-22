
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;


public class FileIO {

    /* 
     * reads in historical user_scores file to build a small database of user scores
     * params: ./resources/user_scores.txt
     * returns: UserScores object
     */
//    public static UserScores readScores() throws FileNotFoundException, IOException
//    {
//        ArrayList<User> out = new ArrayList<>();
//        
//        FileReader s = new FileReader("./resources/user_scores.txt");
//        BufferedReader inStream = new BufferedReader(s);
//        
//        String line;
//        while ((line = inStream.readLine()) != null)
//        {
//            StringTokenizer st = new StringTokenizer(line);
//            //users.put(st.nextToken(), Integer.valueOf(st.nextToken()));
//            out.add(new User(st.nextToken(), Integer.parseInt(st.nextToken())));
//        }
//        
//        inStream.close();
//        
//        return new UserScores(out);
//    }
    
    /* 
     * writes to and updates user_scores file
     * params: UserScores to be written
     * returns: none
     */
    public static void writeScores(UserScores out) throws FileNotFoundException
    {
        PrintWriter pw = new PrintWriter(new FileOutputStream("./resources/user_scores.txt"));
        ArrayList<User> users = out.getUsers();
        
        for (int i = 0; i < users.size(); i++)
        {
            pw.println(users.get(i).getUsername() + " " + users.get(i).getScore());
        }
        
        pw.close();
    }
    

    /* 
     * reads in questions from file and builds Question objects to be given to game
     * params: ./resources/questions.txt
     * returns: ArrayList of Question objects
     */
public static ArrayList<Question> readQuestions() throws FileNotFoundException, IOException {
    ArrayList<Question> out = new ArrayList<>();

    FileReader s = new FileReader("./resources/questions.txt");
    BufferedReader inStream = new BufferedReader(s);

    String line;
    while ((line = inStream.readLine()) != null) {
        StringTokenizer st = new StringTokenizer(line, "|");
        if (st.hasMoreTokens()) {
            System.out.println(line);
            String prompt = st.nextToken();
            int correctAnswerIndex = Integer.parseInt(st.nextToken());
            String[] answers = new String[4];
            for (int i = 0; i < 4 && st.hasMoreTokens(); i++) {
                answers[i] = st.nextToken();
            }
            out.add(new Question(prompt, correctAnswerIndex, answers));
        }
    }

    inStream.close();

    return out;
}
    
    /* 
     * writes to and updates questions file
     * params: ArrayList of Question objects to be written
     * returns: none
     */
    public static void writeQuestions(ArrayList<Question> out) throws FileNotFoundException
    {
        PrintWriter pw = new PrintWriter(new FileOutputStream("./resources/questions.txt"));
        
        for (Question q : out)
        {
            pw.print(q.getPrompt());
            pw.print("»"+ q.getCorrectAnswer());
            
            for (String answer : q.getAnswers()) {
                pw.print("»" + answer);
            }
            pw.print("\n");
        }
        
        pw.close();
    }
    
    public static HashMap<String, Double> readFriends() throws IOException
    {
        HashMap<String, Double> out = new HashMap<>();
        
        FileReader s = new FileReader("./resources/friends.txt");
        BufferedReader inStream = new BufferedReader(s);
        
        String line;
        while ((line = inStream.readLine()) != null)
        {
            StringTokenizer st = new StringTokenizer(line, "»");
            out.put(st.nextToken(), Double.valueOf(st.nextToken()));
        }
        
        inStream.close();
        
        return out;
    }
    
    /* 
     * clears and empties any existing gamelog file for new file appending
     * params: none
     * returns: none
     */
    public static void clearLog() throws FileNotFoundException
    {
        PrintWriter pw = new PrintWriter(new FileOutputStream("./resources/gamelog.txt"));
        pw.close();
    
    }
    
    /* 
     * takes String to write to game log file for dev purposes
     * params: String out (from LogProfile object)
     * returns: none
     */
    public static void writeLog(String out) throws FileNotFoundException
    {
        PrintWriter pw = new PrintWriter(new FileOutputStream("./resources/gamelog.txt"), true);

        pw.println(out);
    }
    
    /* 
     * reads in ascii art for 'who wants to be a millionaire!' menu
     * params: ./resources/logo.txt
     * returns: none
     */
    public static String readLogo() throws FileNotFoundException, IOException
    {
        String out = "";
        
        FileReader s = new FileReader("./resources/logo.txt");
        BufferedReader inStream = new BufferedReader(s);
        
        String line;
        while ((line = inStream.readLine()) != null)
        {
            out += line + "\n";
        }
        
        inStream.close();
        
        return out;
    }
    
}

