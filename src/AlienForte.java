package src;

public class AlienForte extends Nave {
    private int velocidade;

    public AlienForte() {
        setVida(30);
        this.velocidade = 15;

       setSprite("assets/AlienForte.png");
    }

    public void atirar(int dano, int velocidade, int cooldown) {
        
    }

    public void sofreuDano() {
        if (getVida() < 20) setSprite("assets/alienForteDano2.png");
        else if (getVida() < 30) setSprite("assets/alienForteDano1.png");
    }

    public void moverEsquerda() {
        if (getX() > 0) {
            setX(getX() - velocidade);
        }
    }

    public void moverDireita() {
        if (getX() < getFundoWidth() - getWidth()) {
            setX(getX() + velocidade);  // move 10 units to the right
        }
    }
}
