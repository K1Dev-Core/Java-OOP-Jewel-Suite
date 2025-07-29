import javax.sound.sampled.*;
import java.io.File;

public class SoundPlayer {
    private static SoundPlayer instance;
    private Clip clickClip;

    public static SoundPlayer getInstance() {
        if (instance == null) {
            instance = new SoundPlayer();
        }
        return instance;
    }

    private SoundPlayer() {
        try {
            File soundFile = new File("src/res/sounds/click-buttons.wav");
            if (soundFile.exists()) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
                clickClip = AudioSystem.getClip();
                clickClip.open(audioStream);
            }
        } catch (Exception e) {
        }
    }

    public void playClickSound() {
        if (clickClip != null) {
            clickClip.setFramePosition(0);
            clickClip.start();
        }
    }
}