import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.lang.Boolean;
import java.text.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

public class ViewFrame extends JFrame {
    
    public ViewFrame(String title, GameView contents) {
        super(title);
        this.setMinimumSize(new Dimension(200, 200));
        this.setSize(800, 514);
        this.setContentPane(contents);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    
}
