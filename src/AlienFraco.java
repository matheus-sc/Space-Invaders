package src;

// Classe AlienFraco que extende de Alien
public class AlienFraco extends Alien {

    // Possui vida e velocidade menores que os outros aliens
    public AlienFraco() {
        setVida(10);
        setVelocidade(5);

       setSprite("assets/AlienFraco.png");
    }
}
