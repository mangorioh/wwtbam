
import javax.swing.JFrame;


public class WWTBAM {
    public static void main(String[] args) {
       
        JFrame frame = new JFrame();
        frame.setTitle("Who Wants to be a Millionaire!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(800, 700);
        frame.setLocationRelativeTo(null);

        MenuPanel mp = new MenuPanel();

        frame.add(mp);
        frame.setVisible(true);

    }
}
