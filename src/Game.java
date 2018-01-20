import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

import static java.awt.image.BufferedImage.TYPE_3BYTE_BGR;

/**
 * This example shows how to create animations using Timers in Java.
 */
public class Game extends GameView {
    private Ship ship = null;
    private Background back = null;
    private Timer animationTimer;
    JLabel msg = null;
    
    public Game(Model mod) {
        super(mod);
        
        this.model = mod;
        
        this.addComponentListener(new ResizeListener());
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                Double block_height = 1.0*getThis().getHeight()/model.lvl.height;
                if (key == KeyEvent.VK_SPACE) {
                    model.bar.notifyPause();
                }
                if(model.pause == true || model.gameover == true)
                    return;
                
                if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
                    move(new Double(-1*(block_height/4)*model.speed),0.0);
                }
                else if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
                    move(new Double((block_height/4)*model.speed),0.0);
                }
                else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
                    move(0.0,new Double((block_height/4)*model.speed));
                }
                else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
                    move(0.0,new Double(-1*(block_height/4)*model.speed));
                }
            }
        });
        
        // Add a Timer to handle animation.
		// Your Timer should use this.handleAnimation() and this.repaint().
        this.animationTimer = new Timer(1000/model.FPS, event -> {
            try {
                if(!model.gameover)
                    this.requestFocus();
                
                Double block_height = 1.0*this.getHeight()/model.lvl.height;
                Double block_width = block_height;
                
                this.ship = new Ship(di(model.x), di(model.y));
                
                if(model.pause == false)
                {
                    model.y_percent = model.y/this.getHeight();
                    model.x_percent = model.x/block_width;
                    Double scroll = new Double(-1*model.speed*block_height/model.FPS);
                    this.move(scroll,0.0);
                    model.incScore(model.speed*20/model.FPS);
                    if(this.back != null)
                        back.move(1);
                    ListIterator<Obstacle> iter = model.lvl.obstacles.listIterator();
                    Boolean won = true;
                    while (iter.hasNext()) {
                        Obstacle o = iter.next();
                        o.move(scroll,0.0);
                        if(o.collision(
                                       di(model.x-o.dx),
                                       di(model.y),
                                       di(block_width), di(block_height)))
                        {
                            GameOver(false);
                        }
                        if(o.past(di(model.x-o.dx), di(model.y), di(block_width), di(block_height)) == false)
                            won = false;
                    }
                    if(won)
                    {
                        model.incScore(1000.0);
                        GameOver(true);
                    }
                }
                else
                {
                    model.incScore(0.0);
                }
                
                model.Draw();
                
                if(model.gameover)
                {
                    this.animationTimer.stop();
                }
            } catch (Exception e) {
                this.animationTimer.stop();
            }
        });
        
        this.animationTimer.start();
    }
    
            
    private int di(Double d) {
        return new Double(d).intValue();
    }
            
    public void move(Double dx, Double dy) {
        if(this.getWidth() == 0 || this.getHeight() == 0)
            return;
        Double block_height = 1.0*this.getHeight()/model.lvl.height;
        model.x += dx;
        model.y += dy;
        if (model.x <= 0.0)
            GameOver(false);
        if (di(model.x) > this.getWidth()-block_height/4)
            model.x -= dx;
        if (di(model.y) < block_height/4)
            model.y -= dy;
        if (di(model.y) > this.getHeight()-block_height/4)
            model.y -= dy;
    }
    
    public void GameOver(Boolean victory) {
        Boolean nodisplay = model.gameover;
        model.gameover = true;
        this.setFocusable(false);
        if(nodisplay)
            return;
        if(victory)
        {
            msg = new JLabel("Level Complete!");
        }
        else
        {
            msg = new JLabel("Game Over!");
        }
        msg.setFont(new Font("Serif", Font.BOLD, 40*this.getWidth()/800));
        msg.setForeground(Color.RED);
        this.add(msg);
    }
    
    public Game getThis() {
        return this;
    }
    
    public void halt() {
        this.animationTimer.stop();
    }
    
    public void resize() {
        if(this == null || this.getHeight() == 0)
            return;
        
        this.ship = new Ship(di(model.x),di(model.y));
        Double block_height = 1.0*this.getHeight()/model.lvl.height;
        Double factor = block_height/40;
        model.x = model.x_percent*block_height;
        model.y = this.getHeight()*model.y_percent;
        this.ship.resize(di(block_height),di(block_height));
        ListIterator<Obstacle> iter = model.lvl.obstacles.listIterator();
        while (iter.hasNext()) {
            Obstacle o = iter.next();
            o.dx = block_height*10-block_height*model.score_actual/20;
        }
        
        if(msg != null)
            msg.setFont(new Font("Serif", Font.BOLD, 40*this.getWidth()/800));
    }
    
    class ResizeListener extends ComponentAdapter {
        public void componentResized(ComponentEvent e) {
            resize();
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        if(this == null || this.getWidth() == 0 || this.getHeight() == 0 || this.ship == null)
            return;
        
        // dBuff and gBuff are used for double buffering
        Image dBuff = new BufferedImage(this.getWidth(), this.getHeight(), TYPE_3BYTE_BGR);
        Graphics gBuff = dBuff.getGraphics();
        gBuff.setClip(0, 0, this.getWidth(), this.getHeight());
        if(model.background)
        {
            if(back == null)
                this.back = new Background(this.getWidth(), this.getHeight());
            this.back.paint(gBuff);
        }
        else
        {
            gBuff.setColor(Color.CYAN);
            gBuff.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
        gBuff.setColor(Color.BLACK);
        ListIterator<Obstacle> iter = model.lvl.obstacles.listIterator();
        while (iter.hasNext()) {
            Double block_height = 1.0*this.getHeight()/model.lvl.height;
            Double block_width = block_height;
            Obstacle o = iter.next();
            gBuff.fillRect(di(o.x*block_width+o.dx), di(o.y*block_height), di(o.width*block_width), di(o.height*block_height));
        }
        this.ship.paint(gBuff);
        g.drawImage(dBuff, 0, 0, this.getWidth(), this.getHeight(), null);
    }
}
