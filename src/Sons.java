package src;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sons {
    private static Sons instance = null;
    private Clip clip;

    private Sons() {}

    public static Sons getInstance() {
        if (instance == null) {
            instance = new Sons();
        }
        return instance;
    }

    public void tocarMusica(String caminho) {
        try {
            if (clip != null && clip.isRunning()) {
                clip.stop();
            }
            File soundFile = new File(caminho);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void tocarSom(String caminho) {
        try {
            // Open an audio input stream.
            File soundFile = new File(caminho); // your shoot sound file here
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            
            // Get a sound clip resource.
            Clip clip = AudioSystem.getClip();
            
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void pararMusica() {
        if (clip != null) {
            clip.stop();
            clip.close();
            clip = null;
        }
    }
}
