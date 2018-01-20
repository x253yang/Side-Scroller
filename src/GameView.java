import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Set attributes common to all views.
 */
class GameView extends JPanel {
    // the model that this view is showing
    public Model model;
    
    public GameView(Model mod) {
        super();
        this.setBackground(Color.GRAY);
        this.model = mod;
    }
    
    // Centre layout
    void CentreLayout(JComponent c) {
        c.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    
    // White text
    void WhiteText(JLabel label) {
        label.setForeground(Color.white);
    }
    
    void update(Model mod)
    {
        this.model = mod;
    }
}
