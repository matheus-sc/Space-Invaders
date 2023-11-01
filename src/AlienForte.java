package src;

public class AlienForte extends Nave {
    private int velocidade;

    public AlienForte() {
        setVida(30);
        this.velocidade = 15;

       setSprite("assets/AlienForte.png");
    }

    public boolean atirar(int dano, int velocidade, int cooldown) {
        return false;
    }

    public void sofreuDano() {
        if (getVida() < 20) setSprite("assets/AlienForteDano2.png");
        else if (getVida() < 30) setSprite("assets/AlienForteDano1.png");
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
