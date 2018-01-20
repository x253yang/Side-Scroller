import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends GameView {
    
    public MainMenu(Model mod) {
        super(mod);
        
        this.model = mod;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        JLabel title = new JLabel("CS349 Game");
        CentreLayout(title);
        WhiteText(title);
        
        JButton p = new JButton("New Game");
        CentreLayout(p);
        p.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                model.resetGame();
                model.switch_to_game();
            }
        });
        
        JButton e = new JButton("Level Editor");
        CentreLayout(e);
        e.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                model.switch_to_editor();
            }
        });
        
        JButton o = new JButton("Options");
        CentreLayout(o);
        o.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                model.switch_to_options(false);
            }
        });
        
        JButton h = new JButton("Help");
        CentreLayout(h);
        h.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Help h = new Help();
            }
        });
        
        JButton q = new JButton("Quit");
        CentreLayout(q);
        q.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });
        
        this.add(title);
        this.add(p);
        this.add(e);
        this.add(o);
        this.add(h);
        this.add(q);
    }
}
