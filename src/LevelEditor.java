import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Boolean;
import java.awt.image.BufferedImage;
import java.util.*;
import static java.awt.image.BufferedImage.TYPE_3BYTE_BGR;

public class LevelEditor extends GameView {
    static private int[][] table = null;
    private int xpos;
    private Stack undo;
    private Stack redo;
    static private Boolean save = false;
    
    public LevelEditor(Model mod) {
        super(mod);
        
        model = mod;
        
        xpos = 0;
        undo = new Stack();
        redo = new Stack();
        
        if(table == null || save == false)
        {
            table = new int[100][10];
            for(int i = 0; i < 100; i++)
            {
                for(int j = 0; j < 10; j++)
                {
                    table[i][j] = 0;
                }
            }
        }
        save = false;
        
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_Z && (e.getModifiers() & KeyEvent.CTRL_MASK) != 0) {
                    System.out.println("undo");
                    undo_action();
                }
                else if (key == KeyEvent.VK_R && (e.getModifiers() & KeyEvent.CTRL_MASK) != 0) {
                    System.out.println("redo");
                    redo_action();
                }
            }
        });
        
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //System.out.println("mouse click!");
                int x=e.getX()/(model.editor.getHeight()/10)+xpos;
                int y=e.getY()/(model.editor.getHeight()/10);
                if(x >= 100)
                    return;
                if(table[x][y] == 0)
                    table[x][y] = 1;
                else
                    table[x][y] = 0;
                //System.out.println(x+","+y);
                undo.push(new Point(x,y));
                redo = new Stack();
                model.Draw();
            }
            @Override
            public void mousePressed(MouseEvent e) {
                //System.out.println("mouse press!");
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                //System.out.println("mouse press!");
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                //System.out.println("mouse entered!");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                //System.out.println("mouse exited!");
            }
        });
    }
    
    public void undo_action() {
        if(undo.empty())
            return;
        
        Point p = (Point)undo.pop();
        if(table[p.x][p.y] == 0)
            table[p.x][p.y] = 1;
        else
            table[p.x][p.y] = 0;
        redo.push(p);
        model.Draw();
    }
    
    public void redo_action() {
        if(redo.empty())
            return;
    
        Point p = (Point)redo.pop();
        if(table[p.x][p.y] == 0)
            table[p.x][p.y] = 1;
        else
            table[p.x][p.y] = 0;
        undo.push(p);
        model.Draw();
    }
    
    public void gen_lvl() {
        model.lvl = new Level(null);
        save = true;
        for(int i = 0; i < 100; i++)
        {
            for(int j = 0; j < 10; j++)
            {
                if(table[i][j] == 1)
                {
                    model.lvl.add_obstacle(i+5,j,i+5,j);
                }
            }
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        if(this == null || this.table == null || this.getWidth() == 0 || this.getHeight() == 0)
            return;
    
        Image dBuff = new BufferedImage(this.getWidth(), this.getHeight(), TYPE_3BYTE_BGR);
        Graphics gBuff = dBuff.getGraphics();
        gBuff.setClip(0, 0, this.getWidth(), this.getHeight());
        
        for(int i = 0; i < xpos+this.getWidth()/(this.getHeight()/10); i++)
        {
            for(int j = xpos; j < 10; j++)
            {
                if(i >= 100)
                {
                    gBuff.setColor(Color.GRAY);
                    gBuff.fillRect(i*(this.getHeight()/10), 0, (i+1)*(this.getHeight()/10), this.getHeight());
                    break;
                }
                if(table[i][j] == 0)
                {
                    gBuff.setColor(Color.CYAN);
                }
                else
                {
                    gBuff.setColor(Color.BLACK);
                }
                gBuff.fillRect(i*(this.getHeight()/10), (j-xpos)*(this.getHeight()/10),
                (i+1)*(this.getHeight()/10), (j-xpos+1)*(this.getHeight()/10));
                
            }
        }
        gBuff.setColor(Color.BLACK);
        g.drawImage(dBuff, 0, 0, this.getWidth(), this.getHeight(), null);
    }
}
