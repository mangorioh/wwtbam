
import javax.swing.JFrame;
import javax.swing.JLabel;


public class GUI{
    
    public GUI()
    {
        JFrame frame = new JFrame();
        frame.setTitle("TEST");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(500, 500);
        
        
        LifeLine[] lifelines;
        lifelines = new LifeLine[3];
        lifelines[0] = new AskTheAudience();
        lifelines[1] = new FiftyFifty();
        lifelines[2] = new PhoneAFriend();
        
        GamePanel gp = new GamePanel(lifelines);
        
        frame.add(gp.getPanel());
        
        //frame.add(new JLabel("TESTSTSTST"));
        frame.setVisible(true);
    }
}
