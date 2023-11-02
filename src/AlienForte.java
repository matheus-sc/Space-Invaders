package src;

public class AlienForte extends Alien {

    public AlienForte() {
        setVida(30);
        setVelocidade(15);

        setSprite("assets/AlienForte.png");
    }

    public void sofreuDano() {
        if (getVida() < 20) setSprite("assets/AlienForteDano2.png");
        else if (getVida() < 30) setSprite("assets/AlienForteDano1.png");
    }
}
