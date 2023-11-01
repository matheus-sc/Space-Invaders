package src;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener {
    private Fundo fundo;  // Criação do JLabel para a imagem de fundo
    private Player player;  // Criação do player
    private ArrayList<Nave> aliens;  // Criação do alien fraco

    public Gameplay() {
        setLayout(null);  // Layout do JPanel nulo

        fundo = new Fundo(); 

        // Criação do player
        player = new Player(10);

        aliens = new ArrayList<Nave>();
        int espacoHorizontal = 50;  // The width of an alien
        int espacoVertical = 50;  // The height of an alien
        int padding = 10;  // The space between aliens

        for (int linha = 0; linha < 5; linha++) {
            for (int col = 0; col < 10; col++) {
                Nave alien;
                int divisao = linha / 2;
                if (linha <= divisao) {
                    alien = new AlienFraco();
                } else if (linha <= divisao * 2) {
                    alien = new AlienMedio();
                } else {
                    alien = new AlienForte();  // Strong aliens are now at the top and every row after the third
                }
        
                // Calculate the x and y coordinates
                int x = col * (espacoHorizontal + padding) + 600;
                int y = linha * (espacoVertical + padding) + 200;
        
                alien.setX(x);
                alien.setY(y);
        
                aliens.add(alien);
            }
        }

        addKeyListener(this); // Adiciona o KeyListener ao JPanel para capturar os eventos de teclado
        setFocusable(true); 
        
        new Timer(100, movimentoAliens).start();
        new Timer(100, movimentoDisparo).start();
        new Timer(10, checaColisao).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        fundo.draw(g);
        player.draw(g);
        for (Nave alien : aliens) {
            alien.draw(g);
        }
        for (Disparo disparo : player.getDisparos()) {
            disparo.draw(g);
        }
        repaint();
        revalidate();
    }

    // Método para capturar os eventos de teclado (seta esquerda, seta direita e espaço)
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_SPACE:
                player.atirar(10, 15, 500);
                break;
            case KeyEvent.VK_LEFT:
                player.moverEsquerda();
                break;
            case KeyEvent.VK_RIGHT:
                player.moverDireita();
                break;
        }
        repaint();
        revalidate();
    }

    ActionListener checaColisao = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            for (Disparo disparo : player.getDisparos()) {
                if (disparo.seColidiu(aliens)) {
                    player.getDisparos().remove(disparo);
                    break;
                }
            }
        }    
    };

    boolean direcaoFracos = false;
    boolean direcaoMedios = false;
    boolean direcaoFortes = false;
    ActionListener movimentoAliens = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            // First move all aliens
            for (Nave alien : aliens) {
                if (alien instanceof AlienFraco) {
                    moverAlien(alien, direcaoFracos);
                } else if (alien instanceof AlienMedio) {
                    moverAlien(alien, direcaoMedios);
                } else {
                    moverAlien(alien, direcaoFortes);
                }
            }
    
            // Then check if any alien has hit the edge and update direcao
            for (Nave alien : aliens) {
                if (alien instanceof AlienFraco) {
                    direcaoFracos = verificarDirecao(alien, direcaoFracos);
                } else if (alien instanceof AlienMedio) {
                    direcaoMedios = verificarDirecao(alien, direcaoMedios);
                } else {
                    direcaoFortes = verificarDirecao(alien, direcaoFortes);
                }
            }
        }
    };

    private void moverAlien(Nave alien, boolean direcao) {
        if (direcao) {
            alien.moverDireita();
        } else {
            alien.moverEsquerda();
        }
    }

    private boolean verificarDirecao(Nave alien, boolean direcaoAtual) {
        if (alien.getX() >= alien.getFundoWidth() - alien.getWidth()) {
            return false;
        } else if (alien.getX() <= 0) {
            return true;
        } else {
            return direcaoAtual;
        }
    }

    ActionListener movimentoDisparo = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            for (Disparo disparo : player.getDisparos()) {
                disparo.mover();
            }
        }
    };

    // Métodos não utilizados, mas necessários para poder implementar o KeyListener
    public void keyTyped(KeyEvent e) {}

    public void keyReleased(KeyEvent e) {}
}
