package src;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

// Classe para tocar sons
public class Sons {
    private static Sons instance = null;  // Instância única da classe
    private Clip clip;  // Variável para o som

    private Sons() {}  // Construtor privado para impedir a criação de instâncias

    // Método para obter a instância única da classe
    public static Sons getInstance() {
        if (instance == null) {
            instance = new Sons();
        }
        return instance;
    }

    // Método para tocar música
    public void tocarMusica(String caminho) {
        try {
            if (clip != null && clip.isRunning()) {
                clip.stop();
            }
            File soundFile = new File(caminho);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Erro ao carregar música!");
        }
    }

    // Método para tocar som
    public void tocarSom(String caminho) {
        try {
            File soundFile = new File(caminho); // your shoot sound file here
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            
            Clip clip = AudioSystem.getClip();
            
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            System.out.println("Erro ao carregar som!");
        }
    }

    // Método para parar a música
    public void pararMusica() {
        if (clip != null) {
            clip.stop();
            clip.close();
            clip = null;
        }
    }
}
