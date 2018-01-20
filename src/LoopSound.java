import java.net.URL;
import javax.swing.*;
import javax.sound.sampled.*;

public class LoopSound {
    Clip clip;
    
    public LoopSound(URL url) throws Exception {
        this.clip = AudioSystem.getClip();
        AudioInputStream ais = AudioSystem.
        getAudioInputStream( url );
        this.clip.open(ais);
        this.clip.loop(Clip.LOOP_CONTINUOUSLY);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            }
        });
    }
}
