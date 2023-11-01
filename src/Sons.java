package src;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sons {
    private Clip clip;

    public void tocarMusica(String caminho) {
        try {
            // Only start the music if it's not already playing
            if (clip == null || !clip.isRunning()) {
                // Open an audio input stream.
                File soundFile = new File(caminho); // your music file here
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
                
                // Get a sound clip resource.
                clip = AudioSystem.getClip();
                
                // Open audio clip and load samples from the audio input stream.
                clip.open(audioIn);
                clip.start();
            }
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
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
}
