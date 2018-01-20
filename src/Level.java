import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.*;
import java.lang.Boolean;
import java.text.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;


public class Level {
    public LinkedList<Obstacle> obstacles = new LinkedList<Obstacle>();
    public int height = 0;
    public int width = 0;
    
    public Level(File file) {
        if(file == null)
            return;
    
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // process the line.
                if(line.charAt(0) != '#')
                {
                    String[] obs = line.split(" ");
                    if(obs.length == 2)
                    {
                        width = Integer.parseInt(obs[0]);
                        height = Integer.parseInt(obs[1]);
                    }
                    else
                    {
                        Obstacle o = new Obstacle(Integer.parseInt(obs[0]),
                                                  Integer.parseInt(obs[1]),
                                                  Integer.parseInt(obs[2]),
                                                  Integer.parseInt(obs[3]));
                        this.obstacles.add(o);
                    }
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void add_obstacle(int x1, int y1, int x2, int y2)
    {
        height = 10;
        width = 100;
        Obstacle o = new Obstacle(x1,y1,x2,y2);
        this.obstacles.add(o);
    }
}

class Obstacle {
    public int x,y,width,height;
    public Double dx;
    public Obstacle(int x_1, int y_1, int x_2, int y_2)
    {
        this.dx = 0.0;
        this.x = x_1;
        this.y = y_1;
        this.width = x_2-x_1+1;
        this.height = y_2-y_1+1;
    }
    
    public void move(Double dx, Double dy)
    {
        this.dx += dx;
    }
    
    public Boolean collision(int player_x, int player_y, int block_width, int block_height) {
        if ((player_x+block_width/4)/block_width >= x && (player_x+7*block_width/8)/block_width <= x+width
            && (player_y+block_height/4)/block_height >= y && (player_y+3*block_height/4)/block_height <= y+height)
            return true;
        else
            return false;
    }
    
    public Boolean past(int player_x, int player_y, int block_width, int block_height) {
        if ((player_x+block_width/2)/block_width >= x+width+1)
            return true;
        else
            return false;
    }
}
