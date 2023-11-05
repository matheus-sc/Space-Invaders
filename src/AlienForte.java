package src;

// Classe AlienForte que extende de Alien
public class AlienForte extends Alien {

    // Possui vida e velocidade maiores que os outros aliens
    public AlienForte() {
        setVida(30);
        setVelocidade(15);

        setSprite("/assets/AlienForte.png");
    }

    // Método para checar se sofreu dano e alterar o sprite
    public void sofreuDano() {
        if (getVida() < 20) setSprite("/assets/AlienForteDano2.png");
        else if (getVida() < 30) setSprite("/assets/AlienForteDano1.png");
    }
}