
import java.util.ArrayList;
import java.util.Collections;


public class UserScores {
    private ArrayList<User> users;
    
    public UserScores(ArrayList<User> users)
    {
        this.users = users;
    }
    
    /* 
     * adds user to scores if new, updates if existing and higher, then sorts in descending order
     * params: user to add
     * returns: newest added user
     */
    public User addScore(User user)
    {
        boolean userExists = false;
        
        //edge case if userscores is empty
        if (users.isEmpty())
        {
            users.add(user);
        }
        else
        {
            //find and update any existing user scores
            for (int i = 0; i < users.size(); i++)
            {
                if (user.getUsername().equals(users.get(i).getUsername()))
                {
                    if (user.getScore() > users.get(i).getScore()){
                        users.get(i).setScore(user.getScore());
                    }
                    
                    userExists = true;
                    break;
                }
            }
            
            if (!userExists)
            {
                users.add(user);
            }
        }
        
        //sorts in descending order. maintains top 10
        Collections.sort(users, Collections.reverseOrder());
        
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
        
        if (users.size() == 0)
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
