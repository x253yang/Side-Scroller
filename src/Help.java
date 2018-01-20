import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.File;
import java.lang.Boolean;
import java.text.*;

public class Help extends JPanel {
    final int NUM_LABELS = 30;
    static JFrame frame = null;
    
    public Help() {
        super();
        if(frame == null || frame.isVisible() == false)
        {
            frame = new JFrame("Help");
            frame.setContentPane(this);
            frame.setMinimumSize(new Dimension(128, 128));
            frame.setSize(480,770);
            frame.setVisible(true);
        }
        
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.WHITE);
        
        JLabel[] h;
        h = new JLabel[NUM_LABELS];
        
        h[0] = new JLabel("<html><p>Welcome to the CS349 Game Help Section!</p></html>");
        h[1] = new JLabel("<html><p>------</p></html>");
        h[2] = new JLabel("<html><p>Basics</p></html>");
        h[3] = new JLabel("<html><p>To begin a new game, press the \"New Game\" button in the main menu.</p></html>");
        h[4] = new JLabel("<html><p>Once you have started a game and quit or returned to the main menu, click the \"Continue\" button in the main menu.</p></html>");
        h[5] = new JLabel("<html><p>To change the options, click the \"Options\" button in the main menu.</p></html>");
        h[6] = new JLabel("<html><p>To quit, either click the \"Quit\" button in the main menu or the exit button at the top of the window.</p></html>");
        
        h[7] = new JLabel("<html><p>------</p></html>");
        h[8] = new JLabel("<html><p>Gameplay</p></html>");
        h[9] = new JLabel("<html><p>The objective of the game is to reach the end of the level, surviving as long as possible. In order to do so you must carefully avoid the obstacles.</p></html>");
        h[10] = new JLabel("<html><p>Use AWSD or the directional keys to move the ship and SPACE to pause/unpause.</p></html>");
        h[11] = new JLabel("<html><p>The score is displayed at the top left and will continuously increase as you move through the level.</p></html>");
        h[12] = new JLabel("<html><p>You lose the game if you fall through the left side of the screen or collide with an obstacle.</p></html>");
        h[13] = new JLabel("<html><p>Buttons at the bottom of the game screen allow you to restart, change the options, or go back to the main menu.</p></html>");
        
        h[14] = new JLabel("<html><p>------</p></html>");
        h[15] = new JLabel("<html><p>Options</p></html>");
        h[16] = new JLabel("<html><p>The speed modified to a value of 0.5, 1.0, 2.0, or 4.0.</p></html>");
        h[17] = new JLabel("<html><p>The music can be turned on or off.</p></html>");
        h[18] = new JLabel("<html><p>The FPS can be changed to 10,20,30,40,50, or 60.</p></html>");
        h[19] = new JLabel("<html><p>The level file can be changed using the Select level file button. Changing the level while a game is active will reset your progress.</p></html>");
        h[20] = new JLabel("<html><p>All default settings (including screen size and level file) can be restored with the Restore Defaults button. Restoring Defaults while a game is active will reset your progress.</p></html>");
        
        h[21] = new JLabel("<html><p>------</p></html>");
        h[22] = new JLabel("<html><p>Editor</p></html>");
        h[23] = new JLabel("<html><p>Click on an empty square to add a block, and click on a square with a block to delete a block.</p></html>");
        h[24] = new JLabel("<html><p>Pressing play will take you to gameplay mode in the level and automatically saves it.</p></html>");
        h[25] = new JLabel("<html><p>Press undo to undo the last action, press redo to redo the last action that has been redone. Multiple actions can be undone or redone. Doing an action after undoing will cause you to lose the ability to redo the action.</p></html>");
        h[26] = new JLabel("<html><p>Press save to save the level, meaning that if you exit the level editor and return your level will be kept. Saving also means that when a new game starts your level will be played.</p></html>");
        h[27] = new JLabel("<html><p>------</p></html>");
        h[28] = new JLabel("<html><p>Credits</p></html>");
        h[29] = new JLabel("<html><p>Created by Xudong (Justin) Yang for CS349</p></html>");
        for(int i = 0; i < NUM_LABELS; i++)
            this.add(h[i]);
    }
}
