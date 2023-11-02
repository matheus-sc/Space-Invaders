package src;

public class AlienMedio extends Alien {

    public AlienMedio() {
        setVida(20);
        setVelocidade(10);

       setSprite("assets/AlienMedio.png");
    }

    public void sofreuDano() {
        if (getVida() < 20) setSprite("assets/AlienMedioDano.png");
    }
}
