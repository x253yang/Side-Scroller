import javax.swing.*;
import java.awt.event.*;
import java.awt.Dimension;
import java.io.File;
import java.lang.Boolean;
import java.text.*;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.net.URL;
import java.net.MalformedURLException;

public class Model {
    /** The observers that are watching this model for changes. */
    
    /* Data */
    public Double speed = 1.0;
    public Boolean pause = true; // 1 - paused, 0 - play
    public Boolean background = false;
    public Double x = 410.0;
    public Double y = 215.0;
    public Double y_percent = 0.0;
    public Double x_percent = 0.0;
    public int score = 0;
    public Double score_actual = 0.0;
    public Boolean gameover = false;
    public Boolean game_view = false;
    public Boolean music = true;
    public File lvl_file = null;
    public Level lvl = null;
    public int FPS = 20;
    LoopSound loopsound;
    
    ViewFrame frame = null;
    MainMenu mainmenu = null;
    Options options = null;
    Stats stats = null;
    Bar bar = null;
    LevelEditor editor = null;
    Game game = null;
    
    private List<GameView> observers;

    /**
     * Create a new model.
     */
    public Model() {
        this.observers = new ArrayList();
        
        mainmenu = new MainMenu(this);
        frame = new ViewFrame("CS349 Game", mainmenu);
        lvl_file = new File("lvl/sample_level.txt");
        set_level();
        PlayMusic();
        
        addObserver(mainmenu);
        addObserver(stats);
        addObserver(game);
        addObserver(bar);
        addObserver(options);
        addObserver(editor);
    }

    /**
     * Add an observer to be notified when this model changes.
     */
    public void addObserver(GameView observer) {
        this.observers.add(observer);
    }

    public void set_level() {
        lvl = new Level(lvl_file);
    }
    
    /**
     * Remove all observers from this model.
     */
    public void removeObservers() {
        if(game != null)
            game.halt();
        for (GameView observer: this.observers) {
            observer = null;
        }
    }

    /**
     * Notify all observers that the model has changed.
     */
    public void notifyObservers() {
        for (GameView observer: this.observers) {
            observer.update(this);
        }
    }
    
    public void switch_to_main() {
        // reset game stats
        resetGame();
        removeObservers();
        
        mainmenu = new MainMenu(this);
        mainmenu.setSize(frame.getSize());
        frame.setContentPane(mainmenu);
        
        this.game_view = false;
    }
    
    public void switch_to_options(Boolean game_active) {
        if(gameover)
            resetGame();
        removeObservers();
        
        options = new Options(this);
        options.setSize(frame.getSize());
        frame.setContentPane(options);
        this.game_view = false;
    }
    
    public void switch_to_game() {
        removeObservers();
        
        this.game_view = true;
        stats = new Stats(this);
        bar = new Bar(this);
        game = new Game(this);
        
        frame.getContentPane().removeAll();
        frame.getContentPane().add(stats);
        frame.getContentPane().add(game);
        game.setFocusable(true);
        frame.getContentPane().add(bar);
        stats.setMinimumSize(new Dimension(frame.getWidth(),100));
        bar.setMinimumSize(new Dimension(frame.getWidth(),100));
        stats.setMaximumSize(new Dimension(Integer.MAX_VALUE,100));
        bar.setMaximumSize(new Dimension(Integer.MAX_VALUE,100));
        
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        
        frame.revalidate();
        frame.repaint();
        game.resize();
    }
    
    public void switch_to_editor() {
        removeObservers();
        
        this.game_view = false;
        stats = new Stats(this);
        bar = new Bar(this);
        editor = new LevelEditor(this);
        
        frame.getContentPane().removeAll();
        frame.getContentPane().add(stats);
        frame.getContentPane().add(editor);
        editor.setFocusable(true);
        frame.getContentPane().add(bar);
        stats.setMinimumSize(new Dimension(frame.getWidth(),100));
        bar.setMinimumSize(new Dimension(frame.getWidth(),100));
        stats.setMaximumSize(new Dimension(Integer.MAX_VALUE,100));
        bar.setMaximumSize(new Dimension(Integer.MAX_VALUE,100));
        
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        
        frame.revalidate();
        frame.repaint();
    }
    
    public void resetGame()
    {
        score = 0;
        score_actual = 0.0;
        Double block_size = 1.0*(frame.getHeight()-114)/lvl.height;
        x = (lvl.height+0.25)*block_size;
        y = (lvl.height/2+0.375)*block_size;
        y_percent = 215.0/400.0;
        x_percent = (lvl.height+0.25);
        gameover = false;
        ListIterator<Obstacle> iter = this.lvl.obstacles.listIterator();
        while (iter.hasNext()) {
            Obstacle o = iter.next();
            o.dx = (frame.getWidth()/2)*1.0;
        }
    }
    
    public void incScore(Double scroll) {
        if(gameover)
            return;
        score_actual += scroll;
        score = score_actual.intValue();
        if(stats != null)
            stats.notifyScore();
    }
    
    
    public void PlayMusic() {
        File file = new File("res/Mario.mid");
        URL music_URL = null;
        try {
            music_URL = file.toURL();
        }
        catch(MalformedURLException ex)
        {
            System.out.println(ex);
        }
        try {
            this.loopsound = new LoopSound(music_URL);
        }
        catch(Exception ex)
        {
            System.out.println(ex);
        }
    }
    
    public void ResizeFrame(int w, int h) {
        frame.setSize(w,h);
    }
    
    public void Draw() {
        frame.revalidate();
        frame.repaint();
    }
    
    public void StopMusic() {
        loopsound.clip.stop();
    }
}
