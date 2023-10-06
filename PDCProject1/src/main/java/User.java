
public class User implements Comparable<User>{
    private String username;
    private int score;
    
    public User(String username, int score)
    {
        this.username = username;
        this.score = score;
    }

    /* 
     * params: none
     * returns: username
     */
    public String getUsername() {
        return username;
    }
    
    /* 
     * params: none
     * returns: score
     */
    public int getScore() {
        return score;
    }
    
    /* 
     * sets user score
     * params: new score
     * returns: none
     */
    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString()
    {
        return this.username + " " + this.score;
    }
    
    //compare to for sort in UserScores
    @Override
    public int compareTo(User o) {
        if (this.getScore() < o.getScore()) {
            return -1;
        } else if (this.getScore() > o.getScore()) {
            return 1;
        } else {
            return 0;
        }
    }
    
}
