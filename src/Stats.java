import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.lang.Boolean;
import java.text.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

public class Stats extends GameView {
    
    private JLabel s_label = null;
    
    private String getString() {
        String s = "Score: ";
        return s = s+String.format("%7s", Integer.toString(model.score)).replace(' ', '0');
    }
    
    public Stats(Model mod) {
        super(mod);
        
        this.model = mod;
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        s_label = new JLabel(getString());
        WhiteText(s_label);
        
        String p = "Speed: ";
        p = p+Double.toString(Math.round(model.speed*100.0)/100.0);
        p = String.format("%20s", p);
        JLabel p_label = new JLabel(p);
        WhiteText(p_label);
        
        String name = "          Level name: ";
        name = name+model.lvl_file.getName();
        JLabel name_label = new JLabel(name);
        WhiteText(name_label);
        
        this.add(s_label);
        this.add(p_label);
        this.add(name_label);
    }
    
    public void notifyScore() {
        s_label.setText(getString());
    }
}
