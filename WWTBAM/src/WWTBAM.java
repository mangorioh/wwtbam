
import java.io.FileNotFoundException;
import java.io.IOException;


public class WWTBAM {
    public static void main(String[] args) throws FileNotFoundException, IOException {
//        Menu wwtbam = new Menu();
//        
//        wwtbam.run();

        DBManager db = new DBManager();
        db.updateDB("CREATE TABLE FRIENDS (NAME VARCHAR(20), WEIGHT DOUBLE)");
    }
}
