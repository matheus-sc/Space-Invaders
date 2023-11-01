package src;

public class AlienFraco extends Nave {
    private int velocidade;

    public AlienFraco() {
        setVida(10);
        this.velocidade = 5;

       setSprite("assets/AlienFraco.png");
    }

    public boolean atirar(int dano, int velocidade, int cooldown) {
        return false;
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
