import javax.swing.*;
import java.awt.*;

public class app {
    public static void main(String []args){
        JFrame frame=new JFrame("Flappy Birds");
        frame.setAlwaysOnTop(true);
       
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(360,640);

        FlappyBirds flappybird=new FlappyBirds();
        frame.add(flappybird);
        frame.pack();
        flappybird.requestFocus(); 
        frame.setVisible(true);          
    }
}
              