import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.lang.Boolean;
import java.text.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

public class Bar extends GameView {
    
    private JButton p = null;
    
    public Bar(Model mod) {
        super(mod);
        
        this.model = mod;
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        String p_text = null;
        if(model.pause)
            p_text = " Play";
        else
            p_text = "Pause";
        
        p = new JButton(p_text);
        p.setOpaque(true);
        p.setBorderPainted(false);
        p.setPreferredSize(new Dimension(80, 50));
        
        if(model.pause)
            p.setBackground(Color.GREEN);
        else
            p.setBackground(Color.RED);
        
        p.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(model.game_view == false)
                {
                    model.editor.gen_lvl();
                    model.resetGame();
                    model.switch_to_game();
                    return;
                }
                
                if(model.pause)
                {
                    p.setBackground(Color.RED);
                    p.setText("Pause");
                    model.pause = false;
                }
                else
                {
                    p.setBackground(Color.GREEN);
                    p.setText("Play");
                    model.pause = true;
                }
            }
        });
        
        JButton r;
        JButton o;
        if(model.game_view == true)
        {
            r = new JButton("Restart");
            r.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                
                    p.setBackground(Color.GREEN);
                    p.setText("Play");
                    model.pause = true;
                    model.resetGame();
                    model.switch_to_game();
                }
            });
        
            o = new JButton("Options");
            CentreLayout(o);
            o.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    model.pause = true;
                
                    model.switch_to_options(true);
                }
            });
        }
        else
        {
            r = new JButton("Undo");
            r.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    model.editor.undo_action();
                }
            });
        
            o = new JButton("Redo");
            CentreLayout(o);
            o.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    model.editor.redo_action();
                }
            });
        }
        
        JButton m = new JButton("Return to Menu");
        CentreLayout(m);
        m.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                model.pause = true;
                
                model.switch_to_main();
            }
        });
        
        JButton s = null;
        if(model.game_view == false)
        {
            s = new JButton("Save");
            CentreLayout(s);
            s.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    model.editor.gen_lvl();
                    model.resetGame();
                }
            });
        }
        
        this.add(p);
        this.add(r);
        this.add(o);
        if(model.game_view == false)
            this.add(s);
        this.add(m);
    }
    
    public void notifyPause() {
        for (ActionListener listener : p.getActionListeners()) {
            listener.actionPerformed(null);
        }
    }
}
