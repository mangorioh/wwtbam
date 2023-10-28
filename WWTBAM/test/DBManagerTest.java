
import java.sql.Connection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DBManagerTest {
    
    private DBManager db;

    @Before
    public void setUp() {
        db = new DBManager();
    }
    
    @After
    public void tearDown() {
        db.closeConnections();
    }
    
    @Test
    public void testEstablishConnection() {
        Connection conn = db.getConnection();
        
        assertNotNull(conn);
    }
    
    @Test
    public void testGetQuestions() {
        assertNotNull("Question list should not be null to populate game", db.getQuestions());
    }

    @Test
    public void meetsMinimumQuestions() {
        assertTrue("Must be have at least questions to run game", db.getQuestions().size() >= 12);
    }
    
    @Test
    public void meetsMinimumFriends() {
        assertTrue("Must be have at least 1 friend for PhoneAFriend lifeline", db.getFriends().size() >= 1);
    }
}
