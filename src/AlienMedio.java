package src;

public class AlienMedio extends Nave {
    private int velocidade;

    public AlienMedio() {
        setVida(20);
        this.velocidade = 10;

       setSprite("assets/AlienMedio.png");
    }

    public boolean atirar(int dano, int velocidade, int cooldown) {
        return false;
    }

    public void sofreuDano() {
        if (getVida() < 20) setSprite("assets/AlienMedioDano.png");
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
