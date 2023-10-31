package src;

// Interface para as naves do jogo (player e aliens)
public interface Nave {
    public void setVida(int vida);

    public int getVida();

    public boolean estaMorto();

    public void atirar(int dano, int velocidade, int cooldown);

    public void moverEsquerda();

    public void moverDireita();

    public int getX();

    public int getY();

    public int getWidth();

    public int getHeight();
}
