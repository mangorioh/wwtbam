
import java.time.LocalDateTime;
import java.util.Date;

public class LogProfile {
    LocalDateTime initTime;
    User user;
    String events;

    public LogProfile()
    {
        initTime = LocalDateTime.now();  
        this.events = "";
    }
    
    /* 
     * adds given event to log, plus timestamp
     * params: event
     * returns: none
     */
    public void addEvent(String event)
    {
        events += "\n" + LocalDateTime.now() + " - " + event;
    }
    
    /* 
     * sets user of game log
     * params: User to set
     * returns: none
     */
    public void setUser(User user)
    {
        this.user = user;
    }
    
    /* 
     * updates timestamp to new log creation, clears log events
     * params: none
     * returns: none
     */
    public void resetLog()
    {
        initTime = LocalDateTime.now();
        this.events = "";
    }
    
    @Override
    public String toString()
    {
        String out = "";
        
        out += initTime + " " + user + "\n";
        out += events;
        
        return out;
    }
}
