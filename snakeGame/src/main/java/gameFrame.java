import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class gameFrame extends JFrame {
    gameFrame(){
        this.add(new gamePanel());
        this.setTitle("SnakeGamer");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();//
        this.setVisible(true);
        this.setLocationRelativeTo(null);//center of window
    }


}
