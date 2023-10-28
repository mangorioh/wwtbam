
import java.util.ArrayList;

public class UserScores {
    private ArrayList<User> users;
    DBManager db;
    
    public UserScores()
    {
        this.db = new DBManager();
        
        this.users = db.getScores();
    }
    
    /* 
     * adds user to database / updates existing user score
     * params: user to add
     * returns: newest added user
     */
    public User addScore(User user)
    {        
        db.addScore(user);
        
        this.users = db.getScores();
        
        return user;
    }
    
    /* 
     * formats returns 10 highest scores
     * params: none
     * returns: String out of highest scores
     */
    public String getTopTen()
    {
        String out = "\n";
        
        for (int i = 0; i < Math.min(10, users.size()); i++)
        {
            out += "  " + (i + 1) + ". " + users.get(i).getUsername() + "  ";
            out += users.get(i).getScore() + "\n";
        }
        
        if (users.isEmpty())
        {
            out += "No user scores recorded yet!";
        }
        
        return out;
    }

    /* 
     * params: none
     * returns: ArrayList
     */
    public ArrayList<User> getUsers() {
        return users;
    }
}
