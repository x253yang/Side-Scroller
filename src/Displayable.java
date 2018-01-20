import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static java.lang.Math.*;

public interface Displayable {
    void paint(Graphics g);
    void resize(int x, int y);
}

class Ship implements Displayable {
    private int x, y;
    public static int size = 40;
    
    public Ship(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void paint(Graphics g) {
        if (ShipImage.getImage() != null) {
            Graphics2D g2 = (Graphics2D) g;
            Composite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);
            g2.setComposite(comp);
            int width = size;
            int height = size;
            g2.drawImage(ShipImage.getImage(), this.x - width / 2, this.y - height / 2, size, size, null);
        }
    }
    
    public void resize(int size_x, int size_y) {
        size = size_x;
    }
}

class ShipImage {
    private static BufferedImage theImage;
    
    public static BufferedImage getImage() {
        if (ShipImage.theImage == null) {
            try {
                ShipImage.theImage = ImageIO.read(new File("res/ship.png").toURL());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ShipImage.theImage;
    }
}

class Background implements Displayable {
    public final int length = 3600;
    public final int height = 600;
    public int canvas_height;
    public int canvas_width;
    private int pos = 0;
    
    public Background(int w, int h) {
        this.canvas_height = h;
        this.canvas_width = w;
    }
    
    public void paint(Graphics g) {
        if (BackgroundImage.getImage() != null) {
            Image im = BackgroundImage.getImage().getScaledInstance(canvas_height*6, canvas_height, Image.SCALE_DEFAULT);
            Graphics2D g2 = (Graphics2D) g;
            Composite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);
            g2.setComposite(comp);
            g2.drawImage(im, 0, 0, canvas_width, canvas_height, pos, 0, pos+canvas_width, canvas_height, null);
        }
    }
    
    public void move(int dpos) {
        pos += dpos;
    }
    
    public void resize(int x, int y) {
        
    }
}

class BackgroundImage {
    private static BufferedImage theImage;
    
    public static BufferedImage getImage() {
        if (BackgroundImage.theImage == null) {
            try {
                BackgroundImage.theImage = ImageIO.read(new File("res/cloud.png").toURL());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return BackgroundImage.theImage;
    }
}
