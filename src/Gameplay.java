package src;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.lang.NumberFormatException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener {
    private Fundo fundo; // Fundo do jogo
    private Font space_invaders;
    private Player player;  // Player do jogo
    private ArrayList<Alien> aliens;  // ArrayList de aliens do jogo
    private Sons sons;  // Sons do jogo
    private JLabel scoreLabel, vidaLabel, timeLabel;  // JLabels para mostrar o score, vida e tempo de jogo
    private JButton voltar;  // JButton para voltar para a tela inicial ao final do jogo
    private int score, time;  // Variáveis para guardar o score e o tempo de jogo
    private Image win, lose;  // Imagens de vitória e derrota
    private boolean gameRunning;  // Variável para saber se o jogo está rodando ou não
    private Timer movimentoAliensTimer, movimentoDisparoTimer, disparoAliensTimer, checaColisaoTimer, timer;  // Timers para movimentação dos aliens, disparo dos aliens, movimentação dos disparos, checagem de colisão e contagem do tempo de jogo

        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

    public Gameplay(String dificuldade) {
        gameRunning = true;  // Inicia o jogo como rodando

        setLayout(null);  // Layout do JPanel nulo
        
        fundo = new Fundo();  // Criação do fundo 

        // Carregamento da fonte
        try {
            InputStream fontStream1 = getClass().getResourceAsStream("/fonts/SpaceFont.ttf");
            space_invaders = Font.createFont(Font.TRUETYPE_FONT, fontStream1).deriveFont(10f);
            InputStream fontStream2 = getClass().getResourceAsStream("/fonts/SpaceFont.ttf");
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, fontStream2));
        } catch (FontFormatException | IOException e) {
            System.out.println("Erro ao carregar fontes!");
        } catch (Exception e) {
            System.out.println("Erro inesperado!");
        }

        // Inicia o objeto sons e toca a música do jogo
        sons = Sons.getInstance();
        sons.tocarMusica("/sounds/spaceinvaders1.wav");

        // Inicia o botão de voltar, o posiciona e adiciona um ActionListener para voltar para a tela inicial
        voltar = new JButton("Voltar");

        // Define a posição e o tamanho do botão "Voltar" de acordo com as dimensões da tela
        int buttonWidth = 200;
        int buttonHeight = 30;
        int buttonX = (screenWidth - buttonWidth) / 2;  // Centraliza horizontalmente
        int buttonY = (int) (screenHeight * 0.9);  // Coloca perto da parte inferior da tela

        voltar.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);
        voltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                voltarParaTelaInicial();
            }
        });
        voltar.setFont(space_invaders.deriveFont(20f));
        voltar.setForeground(Color.RED);
        voltar.setBackground(Color.YELLOW);
        voltar.setFocusPainted(false);
        voltar.setBorderPainted(false);

        formacaoAliens(dificuldade); // Faz a formação de aliens de acordo com a dificuldade através do método formacaoAliens

        // Inicia o JLabel do score, o posiciona e o adiciona ao JPanel
        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setFont(space_invaders);
        scoreLabel.setForeground(Color.WHITE);
        int scoreLabelX = (int) (screenWidth * 0.02); // Ajuste a posição horizontal conforme necessário
        int scoreLabelY = (int) (screenHeight * 0.02);  // Ajuste a posição vertical conforme necessário
        scoreLabel.setBounds(scoreLabelX, scoreLabelY, 300, 20);
        add(scoreLabel);

        // Inicia o JLabel do tempo de jogo, o posiciona e o adiciona ao JPanel
        timeLabel = new JLabel("Time: 0s");
        int timeLabelX = (int) (screenWidth * 0.02);  // Ajuste a posição horizontal conforme necessário
        int timeLabelY = (int) (screenHeight * 0.06);  // Ajuste a posição vertical conforme necessário
        timeLabel.setBounds(timeLabelX, timeLabelY, 300, 30);
        add(timeLabel);
        timeLabel.setFont(space_invaders);
        timeLabel.setForeground(Color.WHITE);

        // Criação do player e do JLabel da vida, posicionando-o e adicionando-o ao JPanel
        player = new Player(100);
        vidaLabel = new JLabel("Vida: " + player.getVida());
        vidaLabel.setFont(space_invaders);
        vidaLabel.setForeground(Color.WHITE);
        int vidaLabelX = (int) (screenWidth * 0.02);  // Ajuste a posição horizontal conforme necessário
        int vidaLabelY = (int) (screenHeight * 0.04);  // Ajuste a posição vertical conforme necessário
        vidaLabel.setBounds(vidaLabelX, vidaLabelY, 300, 20);
        add(vidaLabel);

        addKeyListener(this); // Adiciona o KeyListener ao JPanel para capturar os eventos de teclado
        setFocusable(true);  // Faz o JPanel focável para poder capturar os eventos de teclado

        // Inicia e começa o timer para contar o tempo de jogo
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                time++;
                timeLabel.setText("Tempo: " + time + "s");
            }
        });
        timer.start();
        
        // Inicia e começa os timers para movimentação dos aliens, movimentação dos disparos, disparo dos aliens e checagem de colisão
        movimentoAliensTimer = new Timer(100, movimentoAliens);
        movimentoDisparoTimer = new Timer(100, movimentoDisparo);
        disparoAliensTimer = new Timer(1000, disparoAliens);
        checaColisaoTimer = new Timer(10, checaColisao);

        movimentoAliensTimer.start();
        movimentoDisparoTimer.start();
        disparoAliensTimer.start();
        checaColisaoTimer.start();
    }

    // Método para desenhar os componentes do JPanel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        fundo.draw(g);
        // Se o jogo não estiver rodando, para todos os timers e adiciona o botão de voltar
        if (!gameRunning) {
            movimentoAliensTimer.stop();
            movimentoDisparoTimer.stop();
            disparoAliensTimer.stop();
            checaColisaoTimer.stop();
            timer.stop();
            if (player.estaMorto()) {
                int loseX = (screenWidth - 800) / 2;  // Centraliza horizontalmente
                int loseY = (screenHeight - 600) / 2;  // Centraliza verticalmente
                g.drawImage(lose, loseX, loseY, 800, 600, null);
            } else if (aliens.isEmpty()) {
                int winX = (screenWidth - 800) / 2;  // Centraliza horizontalmente
                int winY = (screenHeight - 600) / 2;  // Centraliza verticalmente
                g.drawImage(win, winX, winY, 800, 600, null);
            }
            add(voltar);
        } else {
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
        }

        repaint();
        revalidate();
    }

    // Método para capturar os eventos de teclado (seta esquerda, seta direita e espaço)
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_SPACE:
                // Faz com que o player atire apenas enquanto o jogo estiver rodando
                if (gameRunning) {
                    if (player.atirar(10, 15, 500, "/assets/TiroPlayer.png")) {
                        sons.tocarSom("/sounds/shoot.wav");
                    }
                }
                break;
            case KeyEvent.VK_LEFT:
                player.moverEsquerda();
                break;
            case KeyEvent.VK_RIGHT:
                player.moverDireita();
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
        }
        // Redesenha e revalida o JPanel para garantir que os componentes sejam desenhados corretamente
        repaint();
        revalidate();
    }

    // ActionListener para checar a colisão
    ActionListener checaColisao = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            // Executa o bloco apenas se o player não estiver morto e se ainda houverem aliens
            if (!player.estaMorto() && !aliens.isEmpty()) {
                // Cria uma ArrayList de disparos e de Aliens para remover ao final
                ArrayList<Disparo> disparosRemover = new ArrayList<>();
                ArrayList<Alien> aliensRemover = new ArrayList<>();

                // Checa se algum disparo do player colidiu com algum alien
                for (Disparo disparo : player.getDisparos()) {
                    if (disparo.seColidiu(aliens)) {
                        disparosRemover.add(disparo);
                        for (Alien alien : aliens) {
                            if (alien.estaMorto()) {
                                // Se o disparo se colidiu, adiciona o score de acordo com o tipo de alien
                                if (alien instanceof AlienFraco) score += 100;
                                else if (alien instanceof AlienMedio) score += 250;
                                else if (alien instanceof AlienForte) score += 500;
                                aliensRemover.add(alien);
                                sons.tocarSom("/sounds/invaderkilled.wav");
                            }
                            // Se o disparo se colidiu, verifica e troca o sprite do alien médio ou forte de acordo com a vida
                            if (alien instanceof AlienMedio) {
                                ((AlienMedio) alien).sofreuDano();
                            } else if (alien instanceof AlienForte) {
                                ((AlienForte) alien).sofreuDano();
                            }
                        }
                        break;
                    }
                }
                // Checa se algum disparo do alien colidiu com o player
                for (Alien alien : aliens) {
                    for (Disparo disparo : alien.getDisparos()) {
                        if (disparo.seColidiu(player)) {
                            disparosRemover.add(disparo);
                            // Se o disparo se colidiu, verifica e troca o sprite do player de acordo com a vida
                            if (player.getVida() <= 30) player.setSprite("/assets/JogadorDano2.png");
                            else if (player.getVida() < 60) player.setSprite("/assets/JogadorDano1.png");
                        }
                    }
                    // Remove os disparos do alien que colidiram com o player
                    alien.getDisparos().removeAll(disparosRemover);
                    
                    // Checa se o alien colidiu com o player
                    Rectangle hitboxAlien = new Rectangle(alien.getX(), alien.getY(), alien.getWidth(), alien.getHeight());
                    Rectangle hitboxPlayer = new Rectangle(player.getX(), player.getY(), player.getWidth(), player.getHeight());
                    if (hitboxAlien.intersects(hitboxPlayer)) {
                        player.setVida(0);
                    }
                }

                // Atualiza os JLabels de score e vida e remove os disparos e aliens que morreram
                scoreLabel.setText("Score: " + score);
                vidaLabel.setText("Vida: " + player.getVida());
                player.getDisparos().removeAll(disparosRemover);
                aliens.removeAll(aliensRemover);
            }
            // Se o player estiver morto, mostra a imagem de derrota, salva o score e para o jogo
            else if (player.estaMorto()) {
                sons.tocarSom("/sounds/invaderkilled.wav");
                
                try {
                    lose = ImageIO.read(getClass().getResourceAsStream("/assets/GameOver.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
                salvarScore();
                gameRunning = false;
            
            // Se não houverem mais aliens, mostra a imagem de vitória, salva o score e para o jogo
            } else {
                try {
                    win = ImageIO.read(getClass().getResourceAsStream("/assets/YouWin.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
                salvarScore();
                gameRunning = false;
            }
        }  
    };

    // ActionListener para movimentação dos aliens, com flags individuais para saber a direção e se o alien já atingiu a borda
    boolean direcaoFracos = false;
    boolean direcaoMedios = false;
    boolean direcaoFortes = false;
    ActionListener movimentoAliens = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            
            boolean edgeHitFracos = false;
            boolean edgeHitMedios = false;
            boolean edgeHitFortes = false;

            // Move todos os aliens de acordo com a direção e verifica se algum alien atingiu a borda
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

            // Se algum alien atingiu a borda, move todos os aliens para baixo e inverte a direção
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

    // Método para mover o alien de acordo com a direção
    private void moverAlien(Nave alien, boolean direcao) {
        if (direcao) {
            alien.moverDireita();
        } else {
            alien.moverEsquerda();
        }
    }

    // Método para verificar se o alien atingiu a borda
    private boolean verificarDirecao(Nave alien, boolean direcaoAtual) {
        return (alien.getX() >= alien.getScreenWidth() - alien.getWidth()) || (alien.getX() <= 0);
    }

    // ActionListener para disparo dos aliens
    ActionListener disparoAliens = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            if (!aliens.isEmpty()) {
                // Cria um objeto random para escolher um alien aleatório para atirar
                Random random = new Random();
                int index = random.nextInt(aliens.size());
                Alien alien = aliens.get(index);

                // Define o dano, a velocidade e o sprite do tiro de acordo com o tipo de alien
                int dano, velocidade;
                String spriteTiroPath;
                if (alien instanceof AlienFraco) {
                    dano = 10;
                    velocidade = 25;
                    spriteTiroPath = "/assets/TiroFraco.png";
                } else if (alien instanceof AlienMedio) {
                    dano = 20;
                    velocidade = 30;
                    spriteTiroPath = "/assets/TiroMedio.png";
                } else {
                    dano = 30;
                    velocidade = 45;
                    spriteTiroPath = "/assets/TiroForte.png";
                }

                // Faz o alien atirar, tocando o som de disparo
                if (alien.atirar(dano, velocidade, 1000, spriteTiroPath)) {
                    sons.tocarSom("/sounds/shoot.wav");
                }
            }
        }
    };

    // ActionListener para movimentação dos disparos
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

    // Método para fazer a formação dos aliens de acordo com a dificuldade
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
                    if (linha < 1) {
                        alien = new AlienMedio();
                    } else {
                        alien = new AlienForte();
                    }

                    int x = col * (espacoHorizontal + padding) + 600;
                    int y = linha * (espacoVertical + padding) + 100;
            
                    alien.setX(x);
                    alien.setY(y);

                    aliens.add(alien);
                }
            }
        }
    }

    // Método para salvar o score no arquivo highscore.txt
    public void salvarScore() {
        double scoreLose = (time * 10);
        score -= scoreLose;
        String scoreStr = String.format("%d", score);
        scoreLabel.setText("Score: " + scoreStr);
        try {
            File file = new File("highscore.txt");
            if (!file.exists()) {
                file.createNewFile();  // Cria o arquivo, caso não exista
            }
        
            // Lê o highscore do arquivo e o compara com o score atual. Se o score atual for maior, salva o score atual no arquivo
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            int highScore = 0;
            if (line != null) {
                highScore = Integer.parseInt(line);
            }
            reader.close();
        
            if (score > highScore) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(String.valueOf(score));
                writer.close();
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    // Método para voltar para a tela inicial
    private void voltarParaTelaInicial() {      
        sons.pararMusica();

        // Pega a janela pai e faz um dispose, fazendo com que a janela do jogo seja fechada
        Window janelaPai = SwingUtilities.getWindowAncestor(this);
        janelaPai.dispose();
        // Cria uma nova janela inicial   
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SpaceInvaders();
            }
        });
    }

    // Métodos não utilizados, mas necessários para poder implementar o KeyListener
    public void keyTyped(KeyEvent e) {}

    public void keyReleased(KeyEvent e) {}
}