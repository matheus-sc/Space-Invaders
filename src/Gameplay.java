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
    private ArrayList<Alien> aliens;  // Criação do alien fraco
    private Sons sons;
    private JLabel scoreLabel, vidaLabel;
    private int score = 0;

    public Gameplay(String dificuldade) {
        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBounds(10, 10, 100, 20);
        add(scoreLabel);

        sons = new Sons();
        setLayout(null);  // Layout do JPanel nulo
        
        fundo = new Fundo(); 

        // Criação do player
        player = new Player(100);
        vidaLabel = new JLabel("Vida: " + player.getVida());
        vidaLabel.setFont(new Font("Arial", Font.BOLD, 16));
        vidaLabel.setForeground(Color.WHITE);   
        vidaLabel.setBounds(10, 30, 100, 20);
        add(vidaLabel);

        formacaoAliens(dificuldade);

        addKeyListener(this); // Adiciona o KeyListener ao JPanel para capturar os eventos de teclado
        setFocusable(true); 
        
        new Timer(100, movimentoAliens).start();
        new Timer(100, movimentoDisparo).start();
        new Timer(100, checaColisao).start();
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
            for (Nave alien : aliens) {
                for (Disparo disparo : alien.getDisparos()) {
                    if (disparo.seColidiu(player)) {
                        disparosRemover.add(disparo);
                        if (player.estaMorto()) {
                            sons.tocarSom("sounds/invaderkilled.wav");
                            System.exit(0);
                        }
                        else if (player.getVida() <= 30) player.setSprite("assets/JogadorDano2.png");
                        else if (player.getVida() < 60) player.setSprite("assets/JogadorDano1.png");
                    }
                }
                alien.getDisparos().removeAll(disparosRemover);
            }

            scoreLabel.setText("Score: " + score);
            vidaLabel.setText("Vida: " + player.getVida());
            player.getDisparos().removeAll(disparosRemover);
            aliens.removeAll(aliensRemover);
        }    
    };

    boolean direcaoFracos = false;
    boolean direcaoMedios = false;
    boolean direcaoFortes = false;
    ActionListener movimentoAliens = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            boolean edgeHitFracos = false;
            boolean edgeHitMedios = false;
            boolean edgeHitFortes = false;

            // First move all aliens
            for (Nave alien : aliens) {
                if (alien instanceof AlienFraco) {
                    moverAlien(alien, direcaoFracos);
                    edgeHitFracos = edgeHitFracos || verificarDirecao(alien, direcaoFracos);
                } else if (alien instanceof AlienMedio) {
                    moverAlien(alien, direcaoMedios);
                    edgeHitMedios = edgeHitMedios || verificarDirecao(alien, direcaoMedios);
                } else {
                    moverAlien(alien, direcaoFortes);
                    edgeHitFortes = edgeHitFortes || verificarDirecao(alien, direcaoFortes);
                }
            }

            // If any alien has hit the edge, move all aliens down and reverse direction for the type that hit the edge
            if (edgeHitFracos || edgeHitMedios || edgeHitFortes) {
                for (Alien alien : aliens) {
                    alien.moverBaixo();
                }
                if (edgeHitFracos) {
                    direcaoFracos = !direcaoFracos;
                }
                if (edgeHitMedios) {
                    direcaoMedios = !direcaoMedios;
                }
                if (edgeHitFortes) {
                    direcaoFortes = !direcaoFortes;
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
        return (alien.getX() >= alien.getFundoWidth() - alien.getWidth()) || (alien.getX() <= 0);
    }

    ActionListener disparoAliens = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            if (!aliens.isEmpty()) {
                // Create a Random object
                Random random = new Random();

                // Get a random index
                int index = random.nextInt(aliens.size());

                // Get the alien at the random index
                Alien alien = aliens.get(index);

                int dano, velocidade;
                if (alien instanceof AlienFraco) {
                    dano = 10;
                    velocidade = 25;
                } else if (alien instanceof AlienMedio) {
                    dano = 20;
                    velocidade = 30;
                } else {
                    dano = 30;
                    velocidade = 35;
                }

                // Make the alien shoot
                String spriteTiroPath;
                if (alien instanceof AlienFraco) {
                    spriteTiroPath = "assets/TiroFraco.png";
                } else if (alien instanceof AlienMedio) {
                    spriteTiroPath = "assets/TiroMedio.png";
                } else {
                    spriteTiroPath = "assets/TiroForte.png";
                }

                if (alien.atirar(dano, velocidade, 1000, spriteTiroPath)) {
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

    public void formacaoAliens(String dificuldade) {
        aliens = new ArrayList<Alien>();
        int espacoHorizontal = 50; 
        int espacoVertical = 50; 
        int padding = 10; 

        if (dificuldade == "Fácil") {
            for (int linha = 0; linha < 5; linha++) {
                for (int col = 0; col < 10; col++) {
                    Alien alien;
                    if (linha < 2) alien = new AlienMedio();
                    else alien = new AlienFraco();
            
                    // Calculate the x and y coordinates
                    int x = col * (espacoHorizontal + padding) + 600;
                    int y = linha * (espacoVertical + padding) + 100;
            
                    alien.setX(x);
                    alien.setY(y);
            
                    aliens.add(alien);
                }
            }
        } else if (dificuldade == "Médio") {
            for (int linha = 0; linha < 5; linha++) {
                for (int col = 0; col < 10; col++) {
                    Alien alien;
                    if (linha < 1) {
                        alien = new AlienForte();
                    } else if (linha < 3) {
                        alien = new AlienMedio(); 
                    } else {
                        alien = new AlienFraco();
                    }
            
                    // Calculate the x and y coordinates
                    int x = col * (espacoHorizontal + padding) + 600;
                    int y = linha * (espacoVertical + padding) + 100;
            
                    alien.setX(x);
                    alien.setY(y);
            
                    aliens.add(alien);
                }
            }
        } else {
            for (int linha = 0; linha < 5; linha++) {
                for (int col = 0; col < 10; col++) {
                    Alien alien;
                    alien = new AlienForte();
            
                    // Calculate the x and y coordinates
                    int x = col * (espacoHorizontal + padding) + 600;
                    int y = linha * (espacoVertical + padding) + 100;
            
                    alien.setX(x);
                    alien.setY(y);

                    aliens.add(alien);
                }
            }
        }
    }

    // Métodos não utilizados, mas necessários para poder implementar o KeyListener
    public void keyTyped(KeyEvent e) {}

    public void keyReleased(KeyEvent e) {}
}
