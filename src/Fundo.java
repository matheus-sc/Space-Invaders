package src;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.awt.Dimension;

import javax.imageio.ImageIO;

public class Fundo {
    private Image sprite;
    private int screenWidth;
    private int screenHeight;

    public Fundo() {
        try {
            // Carrega o sprite do fundo
            sprite = ImageIO.read(new File("assets/Fundo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Obtém o tamanho da tela em tempo de execução
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = screenSize.width;
        screenHeight = screenSize.height;

        // Redimensiona o sprite do fundo para corresponder ao tamanho da tela
        sprite = sprite.getScaledInstance(screenWidth, screenHeight, Image.SCALE_SMOOTH);
    }

    public void draw(Graphics g) {
        g.drawImage(sprite, 0, 0, null); // Desenha o sprite redimensionado na tela
    }

    public int getWidth() {
        return screenWidth; // Retorna a largura da tela
    }

    public int getHeight() {
        return screenHeight; // Retorna a altura da tela
    }
}