
import org.junit.Test;
import static org.junit.Assert.*;

public class AskTheAudienceTest {
    
    @Test
    public void testIsUsed() {
        System.out.println("isUsed");
        AskTheAudience instance = new AskTheAudience();
        
        assertFalse(instance.isUsed());
        instance.use();
        assertFalse(instance.isUsed());
        instance.use();
        assertTrue(instance.isUsed());
        instance.use();
        assertTrue(instance.isUsed());
        
        instance.resetUses();
        assertFalse(instance.isUsed());
    }

    @Test
    public void pollAddsToOneHundred() {
        System.out.println("resetUses");
        AskTheAudience instance = new AskTheAudience();
        int[] test = instance.getHint(0);
        int sum = 0;
        
        for (int num : test) {
            sum += num;
        }
        
        assertEquals(100, sum);
    }
    
}
