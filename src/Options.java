import javax.swing.*;
import java.awt.event.*;
import java.awt.Dimension;
import java.io.File;
import java.lang.Boolean;

public class Options extends GameView {
    final String MUSIC_ON = "Music - ON";
    final String MUSIC_OFF = "Music - OFF";
    
    public Options(Model mod) {
        super(mod);
        
        this.model = mod;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        JButton p;
        if(model.game_view)
        {
            p = new JButton("Return to Game");
            CentreLayout(p);
            p.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    
                    model.switch_to_game();
                }
            });
        }
        else
        {
            p = new JButton("Return to Menu");
            CentreLayout(p);
            p.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    
                    model.switch_to_main();
                }
            });
        }
        
        JButton h = new JButton("Help");
        CentreLayout(h);
        h.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Help h = new Help();
            }
        });
        
        String musictext = null;
        if (model.music)
            musictext = MUSIC_ON;
        else
            musictext = MUSIC_OFF;
        
        JButton m = new JButton(musictext);
        CentreLayout(m);
        m.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
                if(model.music)
                {
                    model.StopMusic();
                    m.setText(MUSIC_OFF);
                    model.music = false;
                }
                else
                {
                    model.PlayMusic();
                    m.setText(MUSIC_ON);
                    model.music = true;
                }
            }
        });
        
        JLabel s_label = new JLabel("Speed: "+String.valueOf(model.speed));
        CentreLayout(s_label);
        WhiteText(s_label);
        
        String[] speeds = {"0.5", "1.0", "2.0", "4.0"};
        JComboBox s_box = new JComboBox(speeds);
        s_box.setSelectedItem(String.valueOf(model.speed));
        s_box.setMaximumSize(new Dimension(200, 50));
        CentreLayout(s_box);
        s_box.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try
                {
                    String selected = (String)s_box.getSelectedItem();
                    Double d = Double.parseDouble(selected);
                    model.speed = d;
                    s_label.setText("Speed: "+selected);
                }
                catch(Exception ex) {}
            }
        });
        
        JLabel fps_label = new JLabel("FPS: "+String.valueOf(model.FPS));
        CentreLayout(fps_label);
        WhiteText(fps_label);
        
        String[] fpss = {"10", "20", "30", "40", "50", "60"};
        JComboBox fps_box = new JComboBox(fpss);
        fps_box.setSelectedItem(String.valueOf(model.FPS));
        fps_box.setMaximumSize(new Dimension(200, 50));
        CentreLayout(fps_box);
        fps_box.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try
                {
                    String selected = (String)fps_box.getSelectedItem();
                    int d = Integer.parseInt(selected);
                    model.FPS = d;
                    fps_label.setText("FPS: "+selected);
                }
                catch(Exception ex) {}
            }
        });
        
        /*String btext;
        if(model.background)
        {
            btext = "Background - ON";
        }
        else
        {
            btext = "Background - OFF";
        }
        
        JButton b = new JButton(btext);
        CentreLayout(b);
        b.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
                if(model.background)
                {
                    b.setText("Background - OFF");
                    model.background = false;
                }
                else
                {
                    b.setText("Background - ON");
                    model.background = true;
                }
            }
        });*/
        
        JButton r = new JButton("Restore Defaults");
        CentreLayout(r);
        r.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
                if(model.music == false)
                {
                    model.PlayMusic();
                    m.setText(MUSIC_ON);
                    model.music = (true);
                }
                model.speed = 1.0;
                s_label.setText("Speed: 1.0");
                s_box.setSelectedItem("1.0");
                fps_label.setText("Speed: 20");
                fps_box.setSelectedItem("20");
                model.ResizeFrame(800,514);
                model.resetGame();
                model.lvl_file = new File("lvl/sample_level.txt");
                model.set_level();
                model.background = false;
            }
        });
        
        JButton fc_button = new JButton("Select level file");
        CentreLayout(fc_button);
        fc_button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                JFrame fc_frame = new JFrame("Select level file");
                JPanel panel = new JPanel();
                
                JFileChooser fc = new JFileChooser("lvl");
                fc.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (e.getActionCommand() == javax.swing.JFileChooser.APPROVE_SELECTION) {
                            File file = fc.getSelectedFile();
                            model.lvl_file = file;
                            model.set_level();
                            model.resetGame();
                            fc_frame.setVisible(false);
                        }
                        else if(e.getActionCommand() == javax.swing.JFileChooser.CANCEL_SELECTION)
                        {
                            fc_frame.setVisible(false);
                        }
                    }
                });
                
                
                fc_frame.setContentPane(panel);
                panel.add(fc);
                fc_frame.setMinimumSize(new Dimension(128, 128));
                fc_frame.setSize(640,640);
                fc_frame.setVisible(true);
            }
        });
        this.add(p);
        this.add(h);
        this.add(m);
        this.add(s_label);
        this.add(s_box);
        this.add(fps_label);
        this.add(fps_box);
        this.add(fc_button);
        //this.add(b);
        this.add(r);
    }
}
