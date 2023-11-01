package src;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Fundo {
    private Image sprite;

    public Fundo() {
        try {
            File file = new File("assets\\Fundo.png");
            sprite = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void draw(Graphics g) {
        g.drawImage(sprite, 0, 0, null);
    }

    public int getWidth() {
        return sprite.getWidth(null);
    }

    public int getHeight() {
        return sprite.getHeight(null);
    }
}
