package src;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener {
    private Fundo fundo;  // Criação do JLabel para a imagem de fundo
    private Player player;  // Criação do player
    private ArrayList<Nave> aliens;  // Criação do alien fraco
    private Sons sons;
    private JLabel scoreLabel;
    private int score = 0;

    public Gameplay() {
        sons = new Sons();
        setLayout(null);  // Layout do JPanel nulo
        
        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBounds(10, 10, 100, 20);
        add(scoreLabel);
        fundo = new Fundo(); 

        // Criação do player
        player = new Player(10);

        aliens = new ArrayList<Nave>();
        int espacoHorizontal = 50; 
        int espacoVertical = 50; 
        int padding = 10; 

        for (int linha = 0; linha < 5; linha++) {
            for (int col = 0; col < 10; col++) {
                Nave alien;
                int divisao = linha / 2;
                if (linha <= divisao) {
                    alien = new AlienFraco();
                } else if (linha <= divisao * 2) {
                    alien = new AlienMedio();
                } else {
                    alien = new AlienForte(); 
                }
        
                
                // Calculate the x and y coordinates
                int x = col * (espacoHorizontal + padding) + 600;
                int y = linha * (espacoVertical + padding) + 100;
        
                alien.setX(x);
                alien.setY(y);
        
                aliens.add(alien);
            }
        }

        addKeyListener(this); // Adiciona o KeyListener ao JPanel para capturar os eventos de teclado
        setFocusable(true); 
        
        new Timer(100, movimentoAliens).start();
        new Timer(100, movimentoDisparo).start();
        new Timer(100, checaColisao).start();
        new Timer(100, calculoScore).start();
        new Timer(1000, disparoAliens).start();
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
        for (Nave alien : aliens) {
            for (Disparo disparo : alien.getDisparos()) {
                disparo.draw(g);
            }
        }
        repaint();
        revalidate();
    }

    // Método para capturar os eventos de teclado (seta esquerda, seta direita e espaço)
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_SPACE:
                if (player.atirar(10, 15, 500)) {
                    sons.tocarSom("sounds/shoot.wav");
                }
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
            ArrayList<Disparo> disparosRemover = new ArrayList<>();
            ArrayList<Nave> aliensRemover = new ArrayList<>();

            for (Disparo disparo : player.getDisparos()) {
                if (disparo.seColidiu(aliens)) {
                    disparosRemover.add(disparo);
                    for (Nave alien : aliens) {
                        if (alien.estaMorto()) {
                            if (alien instanceof AlienFraco) score += 10;
                            else if (alien instanceof AlienMedio) score += 20;
                            else if (alien instanceof AlienForte) score += 30;
                            aliensRemover.add(alien);
                            sons.tocarSom("sounds/invaderkilled.wav");
                        }
                        if (alien instanceof AlienMedio) {
                            ((AlienMedio) alien).sofreuDano();
                        } else if (alien instanceof AlienForte) {
                            ((AlienForte) alien).sofreuDano();
                        }
                    }
                    break;
                }
            }

            player.getDisparos().removeAll(disparosRemover);
            aliens.removeAll(aliensRemover);
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

    ActionListener disparoAliens = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            if (!aliens.isEmpty()) {
                // Create a Random object
                Random random = new Random();

                // Get a random index
                int index = random.nextInt(aliens.size());

                // Get the alien at the random index
                Nave alien = aliens.get(index);

                int dano, velocidade;
                if (alien instanceof AlienFraco) {
                    dano = 10;
                    velocidade = 10;
                } else if (alien instanceof AlienMedio) {
                    dano = 20;
                    velocidade = 15;
                } else {
                    dano = 30;
                    velocidade = 20;
                }

                // Make the alien shoot
                if (alien.atirar(dano, velocidade, 1000)) {
                    sons.tocarSom("sounds/shoot.wav");
                }
            }
        }
    };

    ActionListener movimentoDisparo = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            for (Disparo disparo : player.getDisparos()) {
                disparo.moverCima();
            }
            for (Nave alien : aliens) {
                for (Disparo disparo : alien.getDisparos()) {
                    disparo.moverBaixo();
                }
            }
        }
    };

    ActionListener calculoScore = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            scoreLabel.setText("Score: " + score);
        }
    };

    // Métodos não utilizados, mas necessários para poder implementar o KeyListener
    public void keyTyped(KeyEvent e) {}

    public void keyReleased(KeyEvent e) {}
}
