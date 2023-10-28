package src;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagemFundo extends JPanel {
    private Image imagemDeFundo;

    public ImagemFundo(String nomeArquivo) throws IOException {
        imagemDeFundo = ImageIO.read(new File(nomeArquivo));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(imagemDeFundo, 0, 0, this);
    }
}
